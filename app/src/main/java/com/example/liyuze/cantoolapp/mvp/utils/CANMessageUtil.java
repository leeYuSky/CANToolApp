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
     * @Author : magic
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
     * @Author : magic
     * @Time : 17/10/15 下午8:55
     * @Description :
     * 生成CAN Message
     * */
    public static void MessageStringify(Map<String,Double> data ){

    }

    /**
     * @Author : magic
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
     * @Author : magic
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
            System.out.println(intValue);
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
     * @Author : magic
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
//        signal s1 = new signal("a",23,12,0,1.0,0,1.0,1.0,"a");
//        signal s2 = new signal("b",16,12,1,1.0,0,1.0,1.0,"b");
//        System.out.println(getValue(s1,parseHexToBinary("0000010800000000")));
//        System.out.println(getValue(s2,parseHexToBinary("0000010800000000")));
//        System.out.println(getValue(s1,parseHexToBinary("0000801000000000")));
//        System.out.println(getValue(s2,parseHexToBinary("0000801000000000")));


//        signal s1 = new signal("CDU_HVACOffButtonSt",0,1,0,1.0,0.0,0.0,1.0,"");
//        signal s2 = new signal("CDU_HVACOffButtonStVD",1,1,0,1.0,0.0,0.0,1.0,"");
//        signal s3 = new signal("CDU_HVACAutoModeButtonSt",2,1,0,1.0,0.0,0.0,1.0,"");
//        signal s4 = new signal("CDU_HVACAutoModeButtonStVD",3,1,0,1.0,0.0,0.0,1.0,"");
//        signal s5 = new signal("CDU_HVACFDefrostButtonSt",6,1,0,1.0,0.0,0.0,1.0,"");
//        signal s6 = new signal("CDU_HVACFDefrostButtonStVD",7,1,0,1.0,0.0,0.0,1.0,"");
//        signal s7 = new signal("CDU_HVACDualButtonSt",10,1,0,1.0,0.0,0.0,1.0,"");
//        signal s8 = new signal("CDU_HVACDualButtonStVD",11,1,0,1.0,0.0,0.0,1.0,"");
//        signal s9 = new signal("CDU_HVACIonButtonSt",12,1,0,1.0,0.0,0.0,1.0,"");
//        signal s10 = new signal("CDU_HVACIonButtonStVD",13,1,0,1.0,0.0,0.0,1.0,"");
//        signal s11 = new signal("CDU_HVACCirculationButtonSt",18,1,0,1.0,0.0,0.0,1.0,"");
//        signal s12 = new signal("CDU_HVACCirculationButtonStVD",19,1,0,1.0,0.0,0.0,1.0,"");
//        signal s13 = new signal("CDU_HVACACButtonSt",20,1,0,1.0,0.0,0.0,1.0,"");
//        signal s14 = new signal("CDU_HVACACButtonStVD",21,1,0,1.0,0.0,0.0,1.0,"");
//        signal s15 = new signal("CDU_HVACACMaxButtonSt",22,1,0,1.0,0.0,0.0,1.0,"");
//        signal s16 = new signal("CDU_HVACACMaxButtonStVD",23,1,0,1.0,0.0,0.0,1.0,"");
//        signal s17 = new signal("CDU_HVACModeButtonSt",26,3,0,1.0,0.0,0.0,7.0,"");
//        signal s18 = new signal("HVAC_WindExitSpd",30,4,0,1.0,0.0,0.0,15.0,"");
//        signal s19 = new signal("CDU_HVAC_DriverTempSelect",36,5,0,0.5,18.0,18.0,32.0,"°C");
//        signal s20 = new signal("HVAC_PsnTempSelect",44,5,0,0.5,18.0,18.0,32.0,"");
//        signal s21 = new signal("CDU_HVACCtrlModeSt",54,3,0,1.0,0.0,0.0,7.0,"");
//        signal s22 = new signal("CDU_ControlSt",55,1,0,1.0,0.0,0.0,1.0,"");
//        System.out.println(getValue(s1,parseHexToBinary("4D10600C131A7000")));
//        System.out.println(getValue(s2,parseHexToBinary("4D10600C131A7000")));
//        System.out.println(getValue(s3,parseHexToBinary("4D10600C131A7000")));
//        System.out.println(getValue(s4,parseHexToBinary("4D10600C131A7000")));
//        System.out.println(getValue(s5,parseHexToBinary("4D10600C131A7000")));
//        System.out.println(getValue(s6,parseHexToBinary("4D10600C131A7000")));
//        System.out.println(getValue(s7,parseHexToBinary("4D10600C131A7000")));
//        System.out.println(getValue(s8,parseHexToBinary("4D10600C131A7000")));
//        System.out.println(getValue(s9,parseHexToBinary("4D10600C131A7000")));
//        System.out.println(getValue(s10,parseHexToBinary("4D10600C131A7000")));
//        System.out.println(getValue(s11,parseHexToBinary("4D10600C131A7000")));
//        System.out.println(getValue(s12,parseHexToBinary("4D10600C131A7000")));
//        System.out.println(getValue(s13,parseHexToBinary("4D10600C131A7000")));
//        System.out.println(getValue(s14,parseHexToBinary("4D10600C131A7000")));
//        System.out.println(getValue(s15,parseHexToBinary("4D10600C131A7000")));
//        System.out.println(getValue(s16,parseHexToBinary("4D10600C131A7000")));
//        System.out.println(getValue(s17,parseHexToBinary("4D10600C131A7000")));
//        System.out.println(getValue(s18,parseHexToBinary("4D10600C131A7000")));
//        System.out.println(getValue(s19,parseHexToBinary("4D10600C131A7000")));
//        System.out.println(getValue(s20,parseHexToBinary("4D10600C131A7000")));
//        System.out.println(getValue(s21,parseHexToBinary("4D10600C131A7000")));
//        System.out.println(getValue(s22,parseHexToBinary("4D10600C131A7000")));



        signal s1 = new signal("HVAC_AirCompressorSt",2,3,0,1.0,0.0,0.0,1.0,"");
        signal s2 = new signal("HVAC_CorrectedExterTempVD",3,1,0,1.0,0.0,0.0,1.0,"");
        signal s3 = new signal("HVAC_RawExterTempVD",4,1,0,1.0,0.0,0.0,1.0,"");
        signal s4 = new signal("HVAC_EngIdleStopProhibitReq",5,1,0,1.0,0.0,0.0,1.0,"");
        signal s5 = new signal("HVAC_ACSt",6,1,0,1.0,0.0,0.0,1.0,"");
        signal s6 = new signal("HVAC_ACmaxSt",7,1,0,1.0,0.0,0.0,1.0,"");
        signal s7 = new signal("HVAC_CorrectedExterTemp",15,8,0,0.5,-40.0,-40.0,87.5,"°C");
        signal s8 = new signal("HVAC_RawExterTemp",23,8,0,0.5,-40.0,-40.0,87.5,"°C");
        signal s9 = new signal("HVAC_TempSelect",28,5,0,0.5,18.0,18.0,32.0,"°C");
        signal s10 = new signal("HVAC_DualSt",29,1,0,1.0,0.0,0.0,1.0,"");
        signal s11 = new signal("HVAC_AutoSt",30,1,0,1.0,0.0,0.0,1.0,"");
        signal s12 = new signal("HVAC_Type",31,1,0,1.0,0.0,0.0,1.0,"");
        signal s13 = new signal("HVAC_WindExitMode",34,3,0,1.0,0.0,0.0,7.0,"");
        signal s14 = new signal("HVAC_SpdFanReq",36,2,0,1.0,0.0,0.0,1.0,"");
        signal s15 = new signal("HVAC_TelematicsSt",42,3,0,1.0,0.0,0.0,7.0,"");
        signal s16 = new signal("HVAC_AirCirculationSt",46,2,0,1.0,0.0,0.0,3.0,"");
        signal s17 = new signal("HVAC_PopUpDisplayReq",47,1,0,1.0,0.0,0.0,1.0,"");
        signal s18 = new signal("HVAC_DriverTempSelect",53,5,0,0.5,18.0,18.0,32.0,"°C");
        signal s19 = new signal("HVAC_IonMode",55,2,0,1.0,0.0,0.0,3.0,"");
        signal s20 = new signal("HVAC_WindExitSpd",59,4,0,1.0,0.0,0.0,15.0,"");
        signal s21 = new signal("HVAC_PsnTempSelect",48,5,0,0.5,18.0,18.0,32.0,"");
        System.out.println(getValue(s1,parseHexToBinary("F92B31EA026508C2")));
        System.out.println(getValue(s2,parseHexToBinary("F92B31EA026508C2")));
        System.out.println(getValue(s3,parseHexToBinary("F92B31EA026508C2")));
        System.out.println(getValue(s4,parseHexToBinary("F92B31EA026508C2")));
        System.out.println(getValue(s5,parseHexToBinary("F92B31EA026508C2")));
        System.out.println(getValue(s6,parseHexToBinary("F92B31EA026508C2")));
        System.out.println(getValue(s7,parseHexToBinary("F92B31EA026508C2")));
        System.out.println(getValue(s8,parseHexToBinary("F92B31EA026508C2")));
        System.out.println(getValue(s9,parseHexToBinary("F92B31EA026508C2")));
        System.out.println(getValue(s10,parseHexToBinary("F92B31EA026508C2")));
        System.out.println(getValue(s11,parseHexToBinary("F92B31EA026508C2")));
        System.out.println(getValue(s12,parseHexToBinary("F92B31EA026508C2")));
        System.out.println(getValue(s13,parseHexToBinary("F92B31EA026508C2")));
        System.out.println(getValue(s14,parseHexToBinary("F92B31EA026508C2")));
        System.out.println(getValue(s15,parseHexToBinary("F92B31EA026508C2")));
        System.out.println(getValue(s16,parseHexToBinary("F92B31EA026508C2")));
        System.out.println(getValue(s17,parseHexToBinary("F92B31EA026508C2")));
        System.out.println(getValue(s18,parseHexToBinary("F92B31EA026508C2")));
        System.out.println(getValue(s19,parseHexToBinary("F92B31EA026508C2")));
        System.out.println(getValue(s20,parseHexToBinary("F92B31EA026508C2")));
        System.out.println(getValue(s21,parseHexToBinary("F92B31EA026508C2")));

    }

}
