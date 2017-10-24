package com.example.liyuze.cantoolapp.mvp.utils;

import com.example.liyuze.cantoolapp.mvp.model.signal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by magic on 2017/10/23.
 */
public class CANMessageUtilTest {
    private CANMessageUtil canMessageUtil;
    @Before
    public void setUp() throws Exception {
        canMessageUtil = new CANMessageUtil();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void messageParse() throws Exception {

    }

    @Test
    public void messageStringify() throws Exception {

    }

    @Test
    public void getStart() throws Exception {

    }

    @Test
    public void getId() throws Exception {

    }

    @Test
    public void main() throws Exception {

    }
    @Test
    public void getValue() throws Exception {

        signal s1 = new signal("HVAC_AirCompressorSt",2,3,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("80478C2F05A1D29A")),0,0);
        s1 = new signal("HVAC_CorrectedExterTempVD",3,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("80478C2F05A1D29A")),0,0);
        s1 = new signal("CDU_HVACOffButtonSt",0,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4B10B06F17100000")),1,0);
        s1 = new signal("CDU_HVACOffButtonStVD",1,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4B10B06F17100000")),1,0);
        s1 = new signal("CDU_HVACFDefrostButtonSt",6,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4B10B06F17100000")),1,0);
        s1 = new signal("CDU_HVACFDefrostButtonStVD",7,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4B10B06F17100000")),0,0);
        s1 = new signal("CDU_HVACFDefrostButtonSt",6,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4B10B06F17100000")),1,0);
        s1 = new signal("CDU_HVACFDefrostButtonStVD",7,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4B10B06F17100000")),0,0);
        s1 = new signal("CDU_HVACDualButtonSt",10,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4B10B06F17100000")),0,0);
        s1 = new signal("CDU_HVACDualButtonStVD",11,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4B10B06F17100000")),0,0);
        s1 = new signal("CDU_HVACIonButtonSt",12,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4B10B06F17100000")),1,0);
        s1 = new signal("CDU_HVACIonButtonStVD",13,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4B10B06F17100000")),0,0);
        s1 = new signal("CDU_HVACCirculationButtonSt",18,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4B10B06F17100000")),0,0);
        s1 = new signal("CDU_HVACCirculationButtonStVD",19,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4B10B06F17100000")),0,0);
        s1 = new signal("CDU_HVACACButtonSt",20,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4B10B06F17100000")),1,0);
        s1 = new signal("CDU_HVACACButtonStVD",21,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4B10B06F17100000")),1,0);
        s1 = new signal("CDU_HVACACMaxButtonSt",22,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4B10B06F17100000")),0,0);
        s1 = new signal("CDU_HVACACMaxButtonStVD",23,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4B10B06F17100000")),1,0);
        s1 = new signal("CDU_HVACModeButtonSt",26,3,0,1.0,0.0,0.0,7.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4B10B06F17100000")),7,0);
        s1 = new signal("HVAC_WindExitSpd",30,4,0,1.0,0.0,0.0,15.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4B10B06F17100000")),13,0);
        s1 = new signal("CDU_HVAC_DriverTempSelect",36,5,0,0.5,18.0,18.0,32.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4B10B06F17100000")),29.5,0);
s1 = new signal("HVAC_PsnTempSelect",44,5,0,0.5,18.0,18.0,32.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4B10B06F17100000")),26,0);
        s1 = new signal("CDU_HVACCtrlModeSt",54,3,0,1.0,0.0,0.0,7.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4B10B06F17100000")),0,0);
        s1 = new signal("CDU_ControlSt",55,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4B10B06F17100000")),0,0);
        s1 = new signal("CDU_HVACACCfg",1,2,0,1.0,0.0,0.0,3.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0D00000000000000")),1,0);
        s1 = new signal("CDU_HVACAirCirCfg",3,2,0,1.0,0.0,0.0,3.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0D00000000000000")),3,0);
        s1 = new signal("CDU_HVACComfortCfg",5,2,0,1.0,0.0,0.0,3.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0D00000000000000")),0,0);
        s1 = new signal("CDU_NMDestAddress",7,8,0,1.0,0.0,0.0,255.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4215640000000001")),66,0);
        s1 = new signal("CDU_NMAlive",8,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4215640000000001")),1,0);
        s1 = new signal("CDU_NMRing",9,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4215640000000001")),0,0);
        s1 = new signal("DU_NMLimpHome",10,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4215640000000001")),1,0);
        s1 = new signal("CDU_NMSleepInd",12,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4215640000000001")),1,0);
        s1 = new signal("CDU_NMSleepAck",13,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4215640000000001")),0,0);
        s1 = new signal("CDU_NMWakeupOrignin",23,8,0,1.0,0.0,0.0,255.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4215640000000001")),100,0);
        s1 = new signal("CDU_NMDataField",31,40,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("4215640000000001")),1,0);
        s1 = new signal("BCM_NMDestAddress",7,8,0,1.0,0.0,0.0,255.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0332510000000000")),3,0);
        s1 = new signal("BCM_NMAlive",8,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0332510000000000")),0,0);
        s1 = new signal("BCM_NMRing",9,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0332510000000000")),1,0);
        s1 = new signal("BCM_NMLimpHome",10,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0332510000000000")),0,0);
        s1 = new signal("BCM_NMSleepInd",12,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0332510000000000")),1,0);
        s1 = new signal("BCM_NMSleepAck",13,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0332510000000000")),1,0);
        s1 = new signal("BCM_NMWakeupOrignin",23,8,0,1.0,0.0,0.0,255.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0332510000000000")),81,0);
        s1 = new signal("BCM_NMDataField",31,40,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0332510000000000")),0,0);
        s1 = new signal("BCM_KeySt",1,2,0,1.0,0.0,1.0,3.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0300000000000000")),3,0);
        s1 = new signal("ESC_VehSpdVD",37,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0000000006CA0000")),0,0);
//        s1 = new signal("ESC_VehSpd",36,13,0,0.1,0.0,0.0,240.0,"");
//        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0000000006CA0000")),97.7625,1);
        s1 = new signal("VCU_CompressorPwrLimit",21,6,0,100.0,0.0,0.0,6000.0,"");


    }

}