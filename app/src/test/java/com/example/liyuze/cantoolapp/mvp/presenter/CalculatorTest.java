package com.example.liyuze.cantoolapp.mvp.presenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by magic on 2017/10/23.
 */
public class CalculatorTest {
    private Calculator calculator;
    @Before
    public void setUp() throws Exception {
         calculator = new Calculator();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void sum() throws Exception {
        assertEquals(calculator.sum(3,4),7);
        assertEquals(calculator.sum(2,4),6);
    }

}