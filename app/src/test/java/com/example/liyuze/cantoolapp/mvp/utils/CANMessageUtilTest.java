package com.example.liyuze.cantoolapp.mvp.utils;

import com.example.liyuze.cantoolapp.mvp.model.signal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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
        assertEquals(canMessageUtil.getId("T000000FFF"),255);
        assertEquals(canMessageUtil.getId("t111800D9010000005300"),273);
        assertEquals(canMessageUtil.getId("11180"),-2147483648);
    }

    @Test
    public void main() throws Exception {
        canMessageUtil.main(null);
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

        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0000381403000000")),5600,0);
s1 = new signal("VCU_CompressorPwrLimitAct",32,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0000381403000000")),1,0);
        s1 = new signal("VCU_PTCPwrLimit",29,6,0,100.0,0.0,0.0,6000.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0000381403000000")),2000,0);
        s1 = new signal("VCU_PTCrPwrLimitAct",33,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0000381403000000")),1,0);
        s1 = new signal("VCU_AirCompressorReq",36,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0000381403000000")),0,0);
        s1 = new signal("VCU_AirCompressorReqVD",37,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0000381403000000")),0,0);

        s1 = new signal("HVAC_AirCompressorSt",2,3,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("094697860945675D")),1,0);
        s1 = new signal("HVAC_CorrectedExterTempVD",3,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("094697860945675D")),1,0);
        s1 = new signal("HVAC_RawExterTempVD",4,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("094697860945675D")),0,0);
        s1 = new signal("HVAC_EngIdleStopProhibitReq",5,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("094697860945675D")),0,0);
        s1 = new signal("HVAC_ACSt",6,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("094697860945675D")),0,0);
        s1 = new signal("HVAC_ACmaxSt",7,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("094697860945675D")),0,0);
        s1 = new signal("HVAC_CorrectedExterTemp",15,8,0,0.5,-40.0,-40.0,87.5,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("094697860945675D")),-5,0);
        s1 = new signal("HVAC_RawExterTemp",23,8,0,0.5,-40.0,-40.0,87.5,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("094697860945675D")),35.5,0);
        s1 = new signal("HVAC_TempSelect",28,5,0,0.5,18.0,18.0,32.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("094697860945675D")),21,0);
        s1 = new signal("HVAC_DualSt",29,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("094697860945675D")),0,0);
        s1 = new signal("HVAC_AutoSt",30,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("094697860945675D")),0,0);
        s1 = new signal("HVAC_Type",31,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("094697860945675D")),1,0);
        s1 = new signal("HVAC_WindExitMode",34,3,0,1.0,0.0,0.0,7.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("094697860945675D")),1,0);
        s1 = new signal("HVAC_SpdFanReq",36,2,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("094697860945675D")),1,0);
        s1 = new signal("HVAC_TelematicsSt",42,3,0,1.0,0.0,0.0,7.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("094697860945675D")),5,0);
        s1 = new signal("HVAC_AirCirculationSt",46,2,0,1.0,0.0,0.0,3.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("094697860945675D")),2,0);
        s1 = new signal("HVAC_PopUpDisplayReq",47,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("094697860945675D")),0,0);
        s1 = new signal("HVAC_DriverTempSelect",53,5,0,0.5,18.0,18.0,32.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("094697860945675D")),27.5,0);
        s1 = new signal("HVAC_IonMode",55,2,0,1.0,0.0,0.0,3.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("094697860945675D")),1,0);
        s1 = new signal("HVAC_WindExitSpd",59,4,0,1.0,0.0,0.0,15.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("094697860945675D")),13,0);
        s1 = new signal("HVAC_PsnTempSelect",48,5,0,0.5,18.0,18.0,32.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("094697860945675D")),28.5,0);

        s1 = new signal("HVAC_RawCabinTemp",7,8,0,0.5,-40.0,-40.0,87.5,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0F23042701722000")),-32.5,0);
        s1 = new signal("HVAC_CorrectedCabinTemp",15,8,0,0.5,-40.0,-40.0,87.5,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0F23042701722000")),-22.5,0);
        s1 = new signal("HVAC_RawCabinTempVD",19,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0F23042701722000")),0,0);
        s1 = new signal("HVAC_CompressorComsumpPwr",17,10,0,10.0,0.0,0.0,8000.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0F23042701722000")),390,0);
        s1 = new signal("HVAC_PTCPwrAct",33,10,0,10.0,0.0,0.0,8000.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0F23042701722000")),3700,0);
        s1 = new signal("HVAC_stPTCAct",55,3,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0F23042701722000")),1,0);

        s1 = new signal("HVAC_CorrectedCabinTempVD",18,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("AF090E1602850000")),1,0);

        s1 = new signal("HVAC_ACCfgSt",0,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0200000000000000")),0,0);
        s1 = new signal("HVAC_AirCirCfgSt",1,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0200000000000000")),1,0);
        s1 = new signal("HVAC_ComfortCfgSt",3,2,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0200000000000000")),0,0);

        s1 = new signal("HVAC_ACPCommandVD",0,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("07462D1EC7000000")),1,0);
        s1 = new signal("HVAC_ACPCommand",2,2,0,1.0,0.0,0.0,3.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("07462D1EC7000000")),3,0);
        s1 = new signal("HVAC_ACPSpeedSet",14,7,0,100.0,0.0,0.0,8600.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("07462D1EC7000000")),7000,0);
        s1 = new signal("HVAC_ACPHighSidePress",21,6,0,0.5,0.0,0.0,31.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("07462D1EC7000000")),22.5,0);
        s1 = new signal("HVAC_PTCPowerRatio",31,8,0,1.0,0.0,0.0,100.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("07462D1EC7000000")),30,0);
        s1 = new signal("HVAC_Checksum",39,8,0,1.0,0.0,155.0,255.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("07462D1EC7000000")),199,0);

        s1 = new signal("ACP_Speed",6,7,0,100.0,0.0,0.0,8600.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("19A7406546008012")),2500,0);
        s1 = new signal("ACPComsumpPwr",15,10,0,10.0,0.0,0.0,8000.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("19A7406546008012")),6690,0);
        s1 = new signal("ACP_Current",16,9,0,0.1,0.0,0.0,51.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("19A7406546008012")),10.1,1);
        s1 = new signal("ACP_MotorTemp",39,8,0,1.0,-40.0,-40.0,140.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("19A7406546008012")),30,0);
        s1 = new signal("ACP_HearBeat",55,4,0,1.0,0.0,0.0,15.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("19A7406546008012")),8,0);
        s1 = new signal("ACP_ExtState",58,3,0,1.0,0.0,0.0,7.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("19A7406546008012")),2,0);
        s1 = new signal("ACP_FailGrade",60,2,0,1.0,0.0,0.0,3.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("19A7406546008012")),2,0);
        s1 = new signal("ACP_BaseState",63,3,0,1.0,0.0,0.0,7.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("19A7406546008012")),0,0);

        s1 = new signal("PTC_ElementError",7,4,0,1.0,0.0,0.0,15.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0453270200000000")),0,0);
        s1 = new signal("PTC_TemperatureOver",3,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0453270200000000")),0,0);
        s1 = new signal("PTC_VoltageFault",2,1,0,1.0,0.0,0.0,1.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0453270200000000")),1,0);
        s1 = new signal("PTC_InternalError",1,2,0,1.0,0.0,0.0,3.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0453270200000000")),0,0);
        s1 = new signal("PTC_Current",15,8,0,0.2,0.0,0.0,25.4,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0453270200000000")),16.6,0);
        s1 = new signal("PTCPwrAct",23,10,0,10.0,0.0,0.0,8000.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0453270200000000")),1560,0);
        s1 = new signal("PTCActst",26,3,0,1.0,0.0,0.0,7.0,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("0453270200000000")),2,0);

        s1 = new signal("Voltage",48,10,1,0.1,0.0,0.0,102.3,"");
        assertEquals(canMessageUtil.getValue(s1, canMessageUtil.parseHexToBinary("00D9010000005300")),8.3,0);



    }
    @Test
    public void parseBinaryToHex() throws Exception {

        assertEquals(canMessageUtil.parseBinaryToHex("0000"),"0");
        assertEquals(canMessageUtil.parseBinaryToHex("0001"),"1");
        assertEquals(canMessageUtil.parseBinaryToHex("0010"),"2");
        assertEquals(canMessageUtil.parseBinaryToHex("0011"),"3");
        assertEquals(canMessageUtil.parseBinaryToHex("0100"),"4");
        assertEquals(canMessageUtil.parseBinaryToHex("0101"),"5");
        assertEquals(canMessageUtil.parseBinaryToHex("0110"),"6");
        assertEquals(canMessageUtil.parseBinaryToHex("0111"),"7");
        assertEquals(canMessageUtil.parseBinaryToHex("1000"),"8");
        assertEquals(canMessageUtil.parseBinaryToHex("1001"),"9");
        assertEquals(canMessageUtil.parseBinaryToHex("1010"),"A");
        assertEquals(canMessageUtil.parseBinaryToHex("1011"),"B");
        assertEquals(canMessageUtil.parseBinaryToHex("1100"),"C");
        assertEquals(canMessageUtil.parseBinaryToHex("1101"),"D");
        assertEquals(canMessageUtil.parseBinaryToHex("1110"),"E");
        assertEquals(canMessageUtil.parseBinaryToHex("1111"),"F");



    }
    @Test
    public void MessageParse() throws Exception {

//        Map<String, Double> result = null;
        assertNotNull(canMessageUtil.MessageParse("t111800D9010000005300"));
        assertNull(canMessageUtil.MessageParse("T111800D9010000005300"));


    }
    @Test
    public void isHaveSpeed() throws Exception {
        assertTrue(canMessageUtil.isHaveSpeed("t111800D90100000053000000\t"));
        assertTrue(canMessageUtil.isHaveSpeed("T11100000800D90100000053000000\t"));
    }


}