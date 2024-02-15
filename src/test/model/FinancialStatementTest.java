package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FinancialStatementTest {

    private FinancialStatement testStmt1;
    private FinancialStatement testStmt2;
    private FinancialStatement testStmt3;
    private FinancialStatement testStmt4;

    @BeforeEach
    public void setup() {
        testStmt1 = new FinancialStatement(2024);
        testStmt1.setNetInc(10000);
        testStmt1.setDepExp(5000);
        testStmt1.setIntExp(1000);
        testStmt1.setTaxExp(4000);
        testStmt1.setDebtRepaid(15000);

        testStmt2 = new FinancialStatement(2024);
        testStmt2.setNetInc(19999);
        testStmt2.setDepExp(10000.55);
        testStmt2.setIntExp(10000.05);
        testStmt2.setTaxExp(10000);
        testStmt2.setDebtRepaid(25001);

        testStmt3 = new FinancialStatement(2024);
        testStmt3.setNetInc(20000);
        testStmt3.setDepExp(10000);
        testStmt3.setIntExp(10000);
        testStmt3.setTaxExp(10000);
        testStmt3.setDebtRepaid(33331);

        testStmt4 = new FinancialStatement(2024);
        testStmt4.setNetInc(20000);
        testStmt4.setDepExp(10000);
        testStmt4.setIntExp(10000);
        testStmt4.setTaxExp(10000);
        testStmt4.setDebtRepaid(33336);
    }

    @Test
    public void testEbitda() {
        assertEquals(20000.0, testStmt1.calcEbitda());
        assertEquals(50000.0, testStmt2.calcEbitda());
        assertEquals(50000.0, testStmt3.calcEbitda());
    }

    @Test
    public void testDSC() {
        assertEquals(1.33, testStmt1.calcDSC()); //test with repeating decimals
        assertEquals(2.00, testStmt2.calcDSC()); //round up from 1.999
        assertEquals(1.50, testStmt3.calcDSC()); //round down from 1.5001
        assertEquals(1.50, testStmt4.calcDSC()); //round up from 1.499
    }

    @Test
    public void testFreeCashFLow() {
        assertEquals(5000.0, testStmt1.calcFreeCashFlow());
        assertEquals(24999.0, testStmt2.calcFreeCashFlow());
        assertEquals(16669.0, testStmt3.calcFreeCashFlow());
        assertEquals(16664.0, testStmt4.calcFreeCashFlow());

    }

}