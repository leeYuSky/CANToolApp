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

    }

}