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
//        Log.e(TAG,"-------------------");
//        Log.e(TAG,messageId);
//        for(Map.Entry<String,Double> entry : data.entrySet()){
//            Log.e(TAG,entry.getKey()+" ------- "+entry.getValue());
//        }

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

//                    if(cha == 0){
//                        int tempLength = getStart(start)+1 - getStart(end);
//                        sb.replace(getStart(end) +tempLength -valueString.length(),getStart(start)+1,valueString);
//                    }else if (cha == 1){
//                        sb.replace(indexStart*8,getStart(start)+1,valueString.substring(getStart(start)+1-indexStart*8));
//                        sb.replace(getStart(end),indexEnd*8+8,valueString.substring(0,indexEnd*8+8 - getStart(end)));
//
//                    }else{
//                        sb.replace(getStart(end),indexEnd*8+8,valueString.substring(0,indexEnd*8+8 - getStart(end)));
//                        for(int j = 1;j < cha;j++){
//                            sb.replace((indexEnd-i)*8,(indexEnd-i)*8+8,)
//                            sb.append(binaryData.substring((indexEnd-i)*8,(indexEnd-i)*8+8));
//                        }
//                        sb.replace(indexStart*8,getStart(start)+1,valueString.substring(getStart(start)+1-indexStart*8));
//                    }
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

    private static void parseForExtendFrame(String msg) {

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
            if(count * 2 + 10 == 26){
                have = true;
            }
        }else{
            int count = Integer.parseInt(msg.substring(9,10));
            if(count * 2 + 15 == 31){
                have = true;
            }
        }
        return have;
    }


    public static void main(String[] args) {
//        signal s1 = new signal("a",23,12,0,1.0,0,1.0,1.0,"a");
//        signal s2 = new signal("b",16,12,1,1.0,0,1.0,1.0,"b");
//        System.out.println(getValue(s1,parseHexToBinary("0000010800000000")));
//        System.out.println(getValue(s2,parseHexToBinary("0000010800000000")));
//        System.out.println(getValue(s1,parseHexToBinary("0000801000000000")));
//        System.out.println(getValue(s2,parseHexToBinary("0000801000000000")));
//        System.out.println(Integer.toBinaryString(255));
//        System.out.println(Integer.toHexString(255).toUpperCase());
//        System.out.println(parseBinaryToHex("0010110011110001"));

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


//        signal s1 = new signal("HVAC_AirCompressorSt",2,3,0,1.0,0.0,0.0,1.0,"");
//        signal s2 = new signal("HVAC_CorrectedExterTempVD",3,1,0,1.0,0.0,0.0,1.0,"");
//        signal s3 = new signal("HVAC_RawExterTempVD",4,1,0,1.0,0.0,0.0,1.0,"");
//        signal s4 = new signal("HVAC_EngIdleStopProhibitReq",5,1,0,1.0,0.0,0.0,1.0,"");
//        signal s5 = new signal("HVAC_ACSt",6,1,0,1.0,0.0,0.0,1.0,"");
//        signal s6 = new signal("HVAC_ACmaxSt",7,1,0,1.0,0.0,0.0,1.0,"");
//        signal s7 = new signal("HVAC_CorrectedExterTemp",15,8,0,0.5,-40.0,-40.0,87.5,"°C");
//        signal s8 = new signal("HVAC_RawExterTemp",23,8,0,0.5,-40.0,-40.0,87.5,"°C");
//        signal s9 = new signal("HVAC_TempSelect",28,5,0,0.5,18.0,18.0,32.0,"°C");
//        signal s10 = new signal("HVAC_DualSt",29,1,0,1.0,0.0,0.0,1.0,"");
//        signal s11 = new signal("HVAC_AutoSt",30,1,0,1.0,0.0,0.0,1.0,"");
//        signal s12 = new signal("HVAC_Type",31,1,0,1.0,0.0,0.0,1.0,"");
//        signal s13 = new signal("HVAC_WindExitMode",34,3,0,1.0,0.0,0.0,7.0,"");
//        signal s14 = new signal("HVAC_SpdFanReq",36,2,0,1.0,0.0,0.0,1.0,"");
//        signal s15 = new signal("HVAC_TelematicsSt",42,3,0,1.0,0.0,0.0,7.0,"");
//        signal s16 = new signal("HVAC_AirCirculationSt",46,2,0,1.0,0.0,0.0,3.0,"");
//        signal s17 = new signal("HVAC_PopUpDisplayReq",47,1,0,1.0,0.0,0.0,1.0,"");
//        signal s18 = new signal("HVAC_DriverTempSelect",53,5,0,0.5,18.0,18.0,32.0,"°C");
//        signal s19 = new signal("HVAC_IonMode",55,2,0,1.0,0.0,0.0,3.0,"");
//        signal s20 = new signal("HVAC_WindExitSpd",59,4,0,1.0,0.0,0.0,15.0,"");
//        signal s21 = new signal("HVAC_PsnTempSelect",48,5,0,0.5,18.0,18.0,32.0,"");
//        System.out.println(getValue(s1,parseHexToBinary("F92B31EA026508C2")));
//        System.out.println(getValue(s2,parseHexToBinary("F92B31EA026508C2")));
//        System.out.println(getValue(s3,parseHexToBinary("F92B31EA026508C2")));
//        System.out.println(getValue(s4,parseHexToBinary("F92B31EA026508C2")));
//        System.out.println(getValue(s5,parseHexToBinary("F92B31EA026508C2")));
//        System.out.println(getValue(s6,parseHexToBinary("F92B31EA026508C2")));
//        System.out.println(getValue(s7,parseHexToBinary("F92B31EA026508C2")));
//        System.out.println(getValue(s8,parseHexToBinary("F92B31EA026508C2")));
//        System.out.println(getValue(s9,parseHexToBinary("F92B31EA026508C2")));
//        System.out.println(getValue(s10,parseHexToBinary("F92B31EA026508C2")));
//        System.out.println(getValue(s11,parseHexToBinary("F92B31EA026508C2")));
//        System.out.println(getValue(s12,parseHexToBinary("F92B31EA026508C2")));
//        System.out.println(getValue(s13,parseHexToBinary("F92B31EA026508C2")));
//        System.out.println(getValue(s14,parseHexToBinary("F92B31EA026508C2")));
//        System.out.println(getValue(s15,parseHexToBinary("F92B31EA026508C2")));
//        System.out.println(getValue(s16,parseHexToBinary("F92B31EA026508C2")));
//        System.out.println(getValue(s17,parseHexToBinary("F92B31EA026508C2")));
//        System.out.println(getValue(s18,parseHexToBinary("F92B31EA026508C2")));
//        System.out.println(getValue(s19,parseHexToBinary("F92B31EA026508C2")));
//        System.out.println(getValue(s20,parseHexToBinary("F92B31EA026508C2")));
//        System.out.println(getValue(s21,parseHexToBinary("F92B31EA026508C2")));


//        signal s1 = new signal("HVAC_AirCompressorSt",2,3,0,1.0,0.0,0.0,1.0,"");
//        signal s2 = new signal("HVAC_CorrectedExterTempVD",3,1,0,1.0,0.0,0.0,1.0,"");
//        signal s3 = new signal("HVAC_RawExterTempVD",4,1,0,1.0,0.0,0.0,1.0,"");
//        signal s4 = new signal("HVAC_EngIdleStopProhibitReq",5,1,0,1.0,0.0,0.0,1.0,"");
//        signal s5 = new signal("HVAC_ACSt",6,1,0,1.0,0.0,0.0,1.0,"");
//        signal s6 = new signal("HVAC_ACmaxSt",7,1,0,1.0,0.0,0.0,1.0,"");
//        signal s7 = new signal("HVAC_CorrectedExterTemp",15,8,0,0.5,-40.0,-40.0,87.5,"°C");
//        signal s8 = new signal("HVAC_RawExterTemp",23,8,0,0.5,-40.0,-40.0,87.5,"°C");
//        signal s9 = new signal("HVAC_TempSelect",28,5,0,0.5,18.0,18.0,32.0,"°C");
//        signal s10 = new signal("HVAC_DualSt",29,1,0,1.0,0.0,0.0,1.0,"");
//        signal s11 = new signal("HVAC_AutoSt",30,1,0,1.0,0.0,0.0,1.0,"");
//        signal s12 = new signal("HVAC_Type",31,1,0,1.0,0.0,0.0,1.0,"");
//        signal s13 = new signal("HVAC_WindExitMode",34,3,0,1.0,0.0,0.0,7.0,"");
//        signal s14 = new signal("HVAC_SpdFanReq",36,2,0,1.0,0.0,0.0,1.0,"");
//        signal s15 = new signal("HVAC_TelematicsSt",42,3,0,1.0,0.0,0.0,7.0,"");
//        signal s16 = new signal("HVAC_AirCirculationSt",46,2,0,1.0,0.0,0.0,3.0,"");
//        signal s17 = new signal("HVAC_PopUpDisplayReq",47,1,0,1.0,0.0,0.0,1.0,"");
//        signal s18 = new signal("HVAC_DriverTempSelect",53,5,0,0.5,18.0,18.0,32.0,"°C");
//        signal s19 = new signal("HVAC_IonMode",55,2,0,1.0,0.0,0.0,3.0,"");
//        signal s20 = new signal("HVAC_WindExitSpd",59,4,0,1.0,0.0,0.0,15.0,"");
//        signal s21 = new signal("HVAC_PsnTempSelect",48,5,0,0.5,18.0,18.0,32.0,"");
//        System.out.println(getValue(s1,parseHexToBinary("80478C2F05A1D29A")));
//        System.out.println(getValue(s2,parseHexToBinary("80478C2F05A1D29A")));
//        System.out.println(getValue(s3,parseHexToBinary("80478C2F05A1D29A")));
//        System.out.println(getValue(s4,parseHexToBinary("80478C2F05A1D29A")));
//        System.out.println(getValue(s5,parseHexToBinary("80478C2F05A1D29A")));
//        System.out.println(getValue(s6,parseHexToBinary("80478C2F05A1D29A")));
//        System.out.println(getValue(s7,parseHexToBinary("80478C2F05A1D29A")));
//        System.out.println(getValue(s8,parseHexToBinary("80478C2F05A1D29A")));
//        System.out.println(getValue(s9,parseHexToBinary("80478C2F05A1D29A")));
//        System.out.println(getValue(s10,parseHexToBinary("80478C2F05A1D29A")));
//        System.out.println(getValue(s11,parseHexToBinary("80478C2F05A1D29A")));
//        System.out.println(getValue(s12,parseHexToBinary("80478C2F05A1D29A")));
//        System.out.println(getValue(s13,parseHexToBinary("80478C2F05A1D29A")));
//        System.out.println(getValue(s14,parseHexToBinary("80478C2F05A1D29A")));
//        System.out.println(getValue(s15,parseHexToBinary("80478C2F05A1D29A")));
//        System.out.println(getValue(s16,parseHexToBinary("80478C2F05A1D29A")));
//        System.out.println(getValue(s17,parseHexToBinary("80478C2F05A1D29A")));
//        System.out.println(getValue(s18,parseHexToBinary("80478C2F05A1D29A")));
//        System.out.println(getValue(s19,parseHexToBinary("80478C2F05A1D29A")));
//        System.out.println(getValue(s20,parseHexToBinary("80478C2F05A1D29A")));
//        System.out.println(getValue(s21,parseHexToBinary("80478C2F05A1D29A")));


        signal ss1 = new signal("Voltage",48,10,1,0.1,0.0,0.0,102.3,"V");
        signal ss2 = new signal("CarSpeed",8,16,1,0.5,0.0,0.0,300.0,"");
        signal ss3 = new signal("WN_Position",0,8,1,1.0,0.0,0.0,100.0,"");
        signal ss4 = new signal("Light",0,1,1,1.0,0.0,0.0,1.0,"");
        signal ss5 = new signal("WN_right_up",30,1,1,1.0,0.0,0.0,0.0,"");
        System.out.println(getValue(ss1,parseHexToBinary("00D9010000005300")));
        System.out.println(getValue(ss2,parseHexToBinary("00D9010000005300")));
        System.out.println(getValue(ss3,parseHexToBinary("54")));
        System.out.println(getValue(ss4,parseHexToBinary("0100")));
        System.out.println(getValue(ss5,parseHexToBinary("00000000")));

        Map<String, Double> data1 = new HashMap<>();
        data1.put("Voltage",8.3);
        test(ss1,8,data1);
        Map<String, Double> data2 = new HashMap<>();
        data2.put("CarSpeed",236.5);
        test(ss2,8,data2);
        Map<String, Double> data3 = new HashMap<>();
        data3.put("WN_Position",84.0);
        test(ss3,1,data3);
        Map<String, Double> data4 = new HashMap<>();
        data4.put("Light",1.0);
        test(ss4,2,data4);
        Map<String, Double> data5 = new HashMap<>();
        data5.put("WN_right_up",0.0);
        test(ss5,4,data5);



//        Map<String, Double> data = new HashMap<>();
//        data.put("HVAC_AirCompressorSt", (double) 0);
//        data.put("HVAC_CorrectedExterTempVD", (double) 0);
//        data.put("HVAC_RawExterTempVD", (double) 0);
//        data.put("HVAC_EngIdleStopProhibitReq", (double) 0);
//        data.put("HVAC_ACSt", (double) 0);
//        data.put("HVAC_ACmaxSt", (double) 1);
//        data.put("HVAC_CorrectedExterTemp",-4.5);
//        data.put("HVAC_RawExterTemp", (double) 30);
//        data.put("HVAC_TempSelect",25.5);
//        data.put("HVAC_DualSt", (double) 1);
//        data.put("HVAC_AutoSt", (double) 0);
//        data.put("HVAC_Type", (double) 0);
//        data.put("HVAC_WindExitMode", (double) 5);
//        data.put("HVAC_SpdFanReq", (double) 0);
//        data.put("HVAC_TelematicsSt", (double) 1);
//        data.put("HVAC_AirCirculationSt", (double) 1);
//        data.put("HVAC_PopUpDisplayReq", (double) 1);
//        data.put("HVAC_DriverTempSelect",22.5);
//        data.put("HVAC_IonMode", (double) 3);
//        data.put("HVAC_WindExitSpd", (double) 10);
//        data.put("HVAC_PsnTempSelect",22.5);
//
//        MessageStringify("800",data);

    }


    public static void test(signal s,int count,Map<String, Double> data){
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < count;i++){
            sb.append("00000000");
        }
        double a = s.getA();
        double offset = s.getOffset();
        double realvalue = data.get(s.getName());
        int value = (int) ((realvalue - offset) / a);
        String valueString = Integer.toBinaryString(value);

        int start = s.getStart();
        int end = start + s.getLength() - 1;
        int indexStart = start / 8;
        int indexEnd = end / 8;
        int cha = indexEnd - indexStart;

//                    if(cha == 0){
//                        int tempLength = getStart(start)+1 - getStart(end);
//                        sb.replace(getStart(end) +tempLength -valueString.length(),getStart(start)+1,valueString);
//                    }else if (cha == 1){
//                        sb.replace(indexStart*8,getStart(start)+1,valueString.substring(getStart(start)+1-indexStart*8));
//                        sb.replace(getStart(end),indexEnd*8+8,valueString.substring(0,indexEnd*8+8 - getStart(end)));
//
//                    }else{
//                        sb.replace(getStart(end),indexEnd*8+8,valueString.substring(0,indexEnd*8+8 - getStart(end)));
//                        for(int j = 1;j < cha;j++){
//                            sb.replace((indexEnd-i)*8,(indexEnd-i)*8+8,)
//                            sb.append(binaryData.substring((indexEnd-i)*8,(indexEnd-i)*8+8));
//                        }
//                        sb.replace(indexStart*8,getStart(start)+1,valueString.substring(getStart(start)+1-indexStart*8));
//                    }
        int k = 0;
        for(int j = valueString.length() - 1;j >=0;j--){
            sb.replace(getStart(start+k),getStart(start+k)+1, String.valueOf(valueString.charAt(j)));
            k++;
        }

        System.out.println(parseBinaryToHex(sb.toString()));
    }

}
