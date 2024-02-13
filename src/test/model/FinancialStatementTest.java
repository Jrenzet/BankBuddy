package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FinancialStatementTest {

    private FinancialStatement testStmt1;

    @BeforeEach
    public void setup() {
        testStmt1 = new FinancialStatement(2024);
        testStmt1.setAssets(50000);
        testStmt1.setLiabil(25000);
        testStmt1.setCurAssets(20000);
        testStmt1.setCurLiabilities(10000);
        testStmt1.setNetInc(10000);
        testStmt1.setDepEx(5000);
        testStmt1.setIntEx(1000);
        testStmt1.setTaxEx(4000);
        testStmt1.setDebtRepaid(15000);
    }

    @Test
    public void testEbitda() {
        double result = testStmt1.getNetInc() + testStmt1.getDepEx() + testStmt1.getIntEx() + testStmt1.getTaxEx();
        assertEquals(result, testStmt1.calcEbitda());
    }

    @Test
    public void testDSC() {
        assertEquals(testStmt1.calcEbitda() / testStmt1.getDebtRepaid(), testStmt1.calcDSC());
    }

    @Test
    public void testFreeCashFLow() {
        double result = testStmt1.calcEbitda() - testStmt1.getDebtRepaid();
        assertEquals(result, testStmt1.calcFreeCashFlow());
    }

    @Test
    public void testCurrentRatio() {
        assertEquals(testStmt1.getCurAssets() / testStmt1.getCurLiabilities(), testStmt1.calcCurrentRatio());
    }

    @Test
    public void testLeverage() {
        assertEquals(testStmt1.getLiabil() / testStmt1.calcEquity(), testStmt1.calcLeverage());
    }

    @Test
    public void testEquity() {
        assertEquals(testStmt1.getAssets() - testStmt1.getLiabil(), testStmt1.calcEquity());
    }


}