package com.example.liyuze.cantoolapp.mvp.utils;

import com.example.liyuze.cantoolapp.mvp.constants.Constants;
import com.example.liyuze.cantoolapp.mvp.model.signal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by magic on 17/10/14.
 */

public class CANMessageUtil {

    /**
     * @Author : liyuze
     * @Time : 17/10/15 下午8:32
     * @Description :
     * 解析CAN Message
     * */
    public static Map<String,Double> MessageParse(String msg){
        Map<String,Double> result = null;

        if(msg.charAt(0) == 't') {
            result = parseForStandardFrame(msg);
        }else if(msg.charAt(0) == 'T'){
            parseForExtendFrame(msg);
        }else{
            // TODO
        }

        return result;

    }

    /**
     * @Author : liyuze
     * @Time : 17/10/15 下午8:55
     * @Description :
     * 生成CAN Message
     * */
    public static void MessageStringify(Map<String,Double> data ){

    }

    /**
     * @Author : liyuze
     * @Time : 17/10/15 下午8:26
     * @Description :
     * 解析标准帧
     * */
    private static Map<String,Double> parseForStandardFrame(String msg){
        int id = Integer.parseInt(msg.substring(1,4),16);
        int byteCount =  Integer.parseInt(msg.substring(4,5));
        String data = msg.substring(5);
        Map<String,Double> result = new HashMap<>();

        if(data.length() != byteCount * 2){
            // TODO
        }else{
            String binaryData = parseHexToBinary(data);
            String idS = String.valueOf(id);
            if(Constants.DATATABLE.containsKey(idS)){
                List<signal> list = Constants.DATATABLE.get(idS);
                for(signal s : list){
                    double realValue = getValue(s,binaryData);
                    result.put(s.getName(),realValue);
                }
            }else{
                // TODO
            }


        }

        return result;
    }

    private static void parseForExtendFrame(String msg){

    }

    /**
     * @Author : liyuze
     * @Time : 17/10/15 下午6:33
     * @Description :
     * 获得一个信号的物理值
     * */
    private static double getValue(signal signal,String binaryData){
        double result = Double.MIN_VALUE;
        if(signal.getType() == 0){
            // 数据按Motorola的bit顺序排列

//            int row = signal.getLength() / 8;
//            int remaining = signal.getLength() % 8;
//            StringBuilder sb = new StringBuilder();
//            int currentRow = signal.getStart() / 8;
//            for(int i = 0;i < row;i++){
//                sb.append(new StringBuilder(binaryData.substring(currentRow*8,currentRow*8+8)).reverse().toString());
//                currentRow++;
//            }
//
//            sb.append(new StringBuilder(binaryData.substring(currentRow*8 + 8 - remaining,currentRow*8 + 8)).reverse());
//            System.out.println("类型为0："+sb.toString());
//            int intValue = Integer.parseInt(sb.toString(),2);
//            double realValue = signal.getA() * intValue + signal.getOffset();
//            return realValue;

            int realStart = getStart(signal.getStart());
            int intValue = Integer.parseInt(binaryData.substring(realStart,realStart + signal.getLength()),2);
            double realValue = signal.getA() * intValue + signal.getOffset();
            return realValue;


        } else if (signal.getType() == 1){
            // 数据按Intel的bit顺序排列

//            System.out.println("类型为1："+new StringBuilder(binaryData.substring(signal.getStart(),signal.getStart()+signal.getLength())).reverse().toString());
//            int intValue = Integer.parseInt(
//                    new StringBuilder(binaryData.substring(signal.getStart(),signal.getStart()+signal.getLength())).reverse().toString(),2);
//            double realValue = signal.getA() * intValue + signal.getOffset();
//            return realValue;

            int realStart = getStart(signal.getStart());
            int row = signal.getLength() / 8;
            int remaining = signal.getLength() % 8;
            int currentRow = signal.getStart() / 8 + row;
            StringBuilder sb = new StringBuilder();
            sb.append(binaryData.substring(currentRow*8 + (8-remaining),currentRow*8+8));
            for(int i =0;i < row;i++){
                currentRow--;
                sb.append(binaryData.substring(currentRow*8,currentRow*8+8));
            }
            int intValue = Integer.parseInt(sb.toString(),2);
            double realValue = signal.getA() * intValue + signal.getOffset();
            return realValue;





        }
        return result;
    }

    public static int getStart(int start){
        return (start / 8) * 8 + ((start / 8 + 1) * 8 - 1 - start);
    }


    /**
     * @Author : liyuze
     * @Time : 17/10/14 下午5:07
     * @Description :
     * 将十六进制字符串转为二进制字符串
     * */
    private static String parseHexToBinary(String msg){
        msg = msg.toUpperCase();
        StringBuilder result = new StringBuilder();
        for(int i =0;i < msg.length();i++){
            char value = msg.charAt(i);
            switch (value){
                case '0':
                    result.append("0000");
                    break;
                case '1':
                    result.append("0001");
                    break;
                case '2':
                    result.append("0010");
                    break;
                case '3':
                    result.append("0011");
                    break;
                case '4':
                    result.append("0100");
                    break;
                case '5':
                    result.append("0101");
                    break;
                case '6':
                    result.append("0110");
                    break;
                case '7':
                    result.append("0111");
                    break;
                case '8':
                    result.append("1000");
                    break;
                case '9':
                    result.append("1001");
                    break;
                case 'A':
                    result.append("1010");
                    break;
                case 'B':
                    result.append("1011");
                    break;
                case 'C':
                    result.append("1100");
                    break;
                case 'D':
                    result.append("1101");
                    break;
                case 'E':
                    result.append("1110");
                    break;
                case 'F':
                    result.append("1111");
                    break;
            }
        }

        return result.toString();
    }


    public static void main(String[] args){
        signal s1 = new signal("a",23,12,0,1.0,0,1.0,1.0,"a");
        signal s2 = new signal("b",16,12,1,1.0,0,1.0,1.0,"b");
        System.out.println(getValue(s1,parseHexToBinary("0000010800000000")));
        System.out.println(getValue(s2,parseHexToBinary("0000010800000000")));
        System.out.println(getValue(s1,parseHexToBinary("0000801000000000")));
        System.out.println(getValue(s2,parseHexToBinary("0000801000000000")));

    }

}
