package com.example.liyuze.cantoolapp.mvp.utils;

import android.util.Log;

import com.example.liyuze.cantoolapp.mvp.constants.Constants;
import com.example.liyuze.cantoolapp.mvp.model.canmessage;
import com.example.liyuze.cantoolapp.mvp.model.signal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by magic on 17/10/14.
 */

public class CANMessageUtil {

    public static final String TAG = CANMessageUtil.class.toString();

    /**
     * @Author : magic
     * @Time : 17/10/15 下午8:32
     * @Description :
     * 解析CAN Message
     */
    public static Map<String, Double> MessageParse(String msg) {
        Map<String, Double> result = null;

        if (msg.charAt(0) == 't') {
            result = parseForStandardFrame(msg);
        } else if (msg.charAt(0) == 'T') {
            parseForExtendFrame(msg);
        } else {
            // TODO
        }

        return result;

    }

    /**
     * @Author : magic
     * @Time : 17/10/15 下午8:55
     * @Description :
     * 生成CAN Message
     */
    public static String MessageStringify(String messageId,Map<String, Double> data) {


        List<signal> signals = Constants.DATATABLE.get(messageId);
        canmessage messgae = Constants.MESSAGETABLE.get(messageId);
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < messgae.getByteCount();i++){
            sb.append("00000000");
        }

        for(int i = 0;i < signals.size();i++){
            if(data.containsKey(signals.get(i).getName())){
                signal s = signals.get(i);
                if(s.getType() == 0) {
                    int start = getStart(s.getStart());
                    int length = s.getLength();
                    double a = s.getA();
                    double offset = s.getOffset();
                    double realvalue = data.get(signals.get(i).getName());
                    int value = (int) ((realvalue - offset) / a);
                    String valueString = Integer.toBinaryString(value);
                    sb.replace(start + length - valueString.length(), start + length, valueString);
                }else{
                    double a = s.getA();
                    double offset = s.getOffset();
                    double realvalue = data.get(signals.get(i).getName());
                    int value = (int) ((realvalue - offset) / a);
                    String valueString = Integer.toBinaryString(value);

                    int start = s.getStart();
                    int end = start + s.getLength() - 1;
                    int indexStart = start / 8;
                    int indexEnd = end / 8;
                    int cha = indexEnd - indexStart;


                    int k = 0;
                    for(int j = valueString.length() - 1;j >=0;j--){
                        sb.replace(getStart(start+k),getStart(start+k)+1, String.valueOf(valueString.charAt(j)));
                        k++;
                    }
                }

            }
        }

        String resultString = parseBinaryToHex(sb.toString());

        StringBuilder result = new StringBuilder("t");
        StringBuilder id = new StringBuilder("000000000000");
        String binaryId = Integer.toBinaryString(Integer.parseInt(messageId));
        id.replace(id.length()-binaryId.length(),id.length(),binaryId);
        String hexId = parseBinaryToHex(id.toString());
        result.append(hexId);
        result.append(messgae.getByteCount());
        result.append(resultString);

        Log.e(TAG,"Result ： " + result.toString());

        return result.toString();


    }

    /**
     * @Author : magic
     * @Time : 17/10/15 下午8:26
     * @Description :
     * 解析标准帧
     */
    private static Map<String, Double> parseForStandardFrame(String msg) {
        int id = Integer.parseInt(msg.substring(1, 4), 16);
        int byteCount = Integer.parseInt(msg.substring(4, 5));
        String data = msg.substring(5);
        Map<String, Double> result = new HashMap<>();

        if (data.length() != byteCount * 2 + 1) {
            // TODO
        } else {
            String binaryData = parseHexToBinary(data);
            String idS = String.valueOf(id);
            if (Constants.DATATABLE.containsKey(idS)) {
                List<signal> list = Constants.DATATABLE.get(idS);
                for (signal s : list) {
                    double realValue = getValue(s, binaryData);
                    result.put(s.getName(), realValue);
                }
            } else {
                // TODO
            }


        }

        return result;
    }

    private static String parseForExtendFrame(String msg) {
        return null;
    }

    /**
     * @Author : magic
     * @Time : 17/10/15 下午6:33
     * @Description :
     * 获得一个信号的物理值
     */
    public static double getValue(signal signal, String binaryData) {
        double result = Double.MIN_VALUE;
        if (signal.getType() == 0) {
            // 数据按Motorola的bit顺序排列

            int realStart = getStart(signal.getStart());

            int intValue = Integer.parseInt(binaryData.substring(realStart, realStart + signal.getLength()), 2);
//            System.out.println(intValue);
            double realValue = signal.getA() * intValue + signal.getOffset();
            return realValue;


        } else if (signal.getType() == 1) {
            // 数据按Intel的bit顺序排列

            int start = signal.getStart();
            int end = start + signal.getLength() - 1;
            int indexStart = start / 8;
            int indexEnd = end / 8;
            int cha = indexEnd - indexStart;
            StringBuilder sb = new StringBuilder();
            if(cha == 0){
                sb.append(binaryData.substring(getStart(end),getStart(start)+1));
            }else if (cha == 1){
                sb.append(binaryData.substring(getStart(end),indexEnd*8+8));
                sb.append(binaryData.substring(indexStart*8,getStart(start)+1));
            }else{
                sb.append(binaryData.substring(getStart(end),indexEnd*8+8));
                for(int i = 1;i < cha;i++){
                    sb.append(binaryData.substring((indexEnd-i)*8,(indexEnd-i)*8+8));
                }
                sb.append(binaryData.substring(indexStart*8,getStart(start)+1));
            }


            int intValue = Integer.parseInt(sb.toString(), 2);
            double realValue = signal.getA() * intValue + signal.getOffset();
            return realValue;

        }
        return result;
    }

    public static int getStart(int start) {
        int result = (start / 8) * 8 + ((start / 8 + 1) * 8 - 1 - start);
        return result;
    }


    /**
     * @Author : magic
     * @Time : 17/10/14 下午5:07
     * @Description :
     * 将十六进制字符串转为二进制字符串
     */
    public static String parseHexToBinary(String msg) {
        msg = msg.toUpperCase();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < msg.length(); i++) {
            char value = msg.charAt(i);
            switch (value) {
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

    /**
     * @Author : liyuze
     * @Time : 17/10/24 下午1:38
     * @Description : 将二进制字符串转换为十六进制
     * */
    public static String parseBinaryToHex(String data){
        StringBuilder result = new StringBuilder();
        int count = data.length() / 4;
        for(int i = 0;i < count;i++){
            String temp = data.substring(i*4,i*4+4);
            switch (temp){
                case "0000":
                    result.append("0");
                    break;
                case "0001":
                    result.append("1");
                    break;
                case "0010":
                    result.append("2");
                    break;
                case "0011":
                    result.append("3");
                    break;
                case "0100":
                    result.append("4");
                    break;
                case "0101":
                    result.append("5");
                    break;
                case "0110":
                    result.append("6");
                    break;
                case "0111":
                    result.append("7");
                    break;
                case "1000":
                    result.append("8");
                    break;
                case "1001":
                    result.append("9");
                    break;
                case "1010":
                    result.append("A");
                    break;
                case "1011":
                    result.append("B");
                    break;
                case "1100":
                    result.append("C");
                    break;
                case "1101":
                    result.append("D");
                    break;
                case "1110":
                    result.append("E");
                    break;
                case "1111":
                    result.append("F");
                    break;
            }
        }
        return result.toString();

    }

    public static int getId(String msg) {
        if (msg.charAt(0) == 't') {
            return Integer.parseInt(msg.substring(1, 4), 16);
        } else if (msg.charAt(0) == 'T') {
            return Integer.parseInt(msg.substring(1, 9), 16);
        } else {
            return Integer.MIN_VALUE;
        }
    }


    public static boolean isHaveSpeed(String msg){
        boolean have = false;
        if(msg.charAt(0) == 't'){
            int count = Integer.parseInt(msg.substring(4,5));
            if(count * 2 + 10 == msg.length()){
                have = true;
            }
        }else{
            int count = Integer.parseInt(msg.substring(9,10));
            if(count * 2 + 15 == msg.length()){
                have = true;
            }
        }
        return have;
    }


    public static void main(String[] args) {


        signal sss1 = new signal("ESC_VehSpd",36,13,0,0.05625,0.0,0.0,240.0,"");
        System.out.println(getValue(sss1,parseHexToBinary("000000002DC30000")));





        Map<String, Double> data = new HashMap<>();
        data.put("HVAC_AirCompressorSt", (double) 0);
        data.put("HVAC_CorrectedExterTempVD", (double) 0);
        data.put("HVAC_RawExterTempVD", (double) 0);
        data.put("HVAC_EngIdleStopProhibitReq", (double) 0);
        data.put("HVAC_ACSt", (double) 0);
        data.put("HVAC_ACmaxSt", (double) 1);
        data.put("HVAC_CorrectedExterTemp",-4.5);
        data.put("HVAC_RawExterTemp", (double) 30);
        data.put("HVAC_TempSelect",25.5);
        data.put("HVAC_DualSt", (double) 1);
        data.put("HVAC_AutoSt", (double) 0);
        data.put("HVAC_Type", (double) 0);
        data.put("HVAC_WindExitMode", (double) 5);
        data.put("HVAC_SpdFanReq", (double) 0);
        data.put("HVAC_TelematicsSt", (double) 1);
        data.put("HVAC_AirCirculationSt", (double) 1);
        data.put("HVAC_PopUpDisplayReq", (double) 1);
        data.put("HVAC_DriverTempSelect",22.5);
        data.put("HVAC_IonMode", (double) 3);
        data.put("HVAC_WindExitSpd", (double) 10);
        data.put("HVAC_PsnTempSelect",22.5);

//        MessageStringify("800",data);

    }


//    public static void test(signal s,int count,Map<String, Double> data){
//        StringBuilder sb = new StringBuilder();
//        for(int i = 0;i < count;i++){
//            sb.append("00000000");
//        }
//        double a = s.getA();
//        double offset = s.getOffset();
//        double realvalue = data.get(s.getName());
//        int value = (int) ((realvalue - offset) / a);
//        String valueString = Integer.toBinaryString(value);
//
//        int start = s.getStart();
//        int end = start + s.getLength() - 1;
//        int indexStart = start / 8;
//        int indexEnd = end / 8;
//        int cha = indexEnd - indexStart;
//        int k = 0;
//        for(int j = valueString.length() - 1;j >=0;j--){
//            sb.replace(getStart(start+k),getStart(start+k)+1, String.valueOf(valueString.charAt(j)));
//            k++;
//        }
//
//        System.out.println(parseBinaryToHex(sb.toString()));
//    }

}
