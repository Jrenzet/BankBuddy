package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FinancialStatementTest {

    private FinancialStatement testStmt1;
    private FinancialStatement testStmt2;
    private FinancialStatement testStmt3;
    private FinancialStatement testStmt4;
    private FinancialStatement testStmt5;
    private FinancialStatement testStmt6;
    private FinancialStatement testStmt7;

    @BeforeEach
    public void setup() {
        testStmt1 = new FinancialStatement(2024);
        testStmt1.setNetInc(10000);
        testStmt1.setDepExp(5000);
        testStmt1.setIntExp(1000);
        testStmt1.setTaxExp(4000);
        testStmt1.setPrincipleRepaid(14000);

        testStmt2 = new FinancialStatement(2024);
        testStmt2.setNetInc(19999);
        testStmt2.setDepExp(10000.55);
        testStmt2.setIntExp(10000);
        testStmt2.setTaxExp(10000);
        testStmt2.setPrincipleRepaid(15001);

        testStmt3 = new FinancialStatement(2024);
        testStmt3.setNetInc(20000);
        testStmt3.setDepExp(10000);
        testStmt3.setIntExp(10000);
        testStmt3.setTaxExp(10000);
        testStmt3.setPrincipleRepaid(23331);

        testStmt4 = new FinancialStatement(2024);
        testStmt4.setNetInc(20000);
        testStmt4.setDepExp(10000);
        testStmt4.setIntExp(10000);
        testStmt4.setTaxExp(10000);
        testStmt4.setPrincipleRepaid(23336);

        // Negative FCF
        testStmt5 = new FinancialStatement(2024);
        testStmt5.setNetInc(20000);
        testStmt5.setDepExp(0);
        testStmt5.setIntExp(0);
        testStmt5.setTaxExp(0);
        testStmt5.setPrincipleRepaid(25000);

        //zero EBITDA & FCF
        testStmt6 = new FinancialStatement(2024);
        testStmt6.setNetInc(0);
        testStmt6.setDepExp(0);
        testStmt6.setIntExp(0);
        testStmt6.setTaxExp(0);
        testStmt6.setPrincipleRepaid(0);

        //negative EBITDA
        testStmt7 = new FinancialStatement(2024);
        testStmt7.setNetInc(-1000);
        testStmt7.setDepExp(0);
        testStmt7.setIntExp(0);
        testStmt7.setTaxExp(0);
        testStmt7.setPrincipleRepaid(0);
    }

    @Test
    public void testCalcEbitda() {
        // test positive EBITDA
        assertEquals(20000.0, testStmt1.calcEbitda());
        assertEquals(50000.0, testStmt2.calcEbitda());
        assertEquals(50000.0, testStmt3.calcEbitda());
        //negative EBITDA
        assertEquals(-1000.0, testStmt7.calcEbitda());
        //zero EBITDA
        assertEquals(0, testStmt6.calcEbitda());
    }

    @Test
    // tests for possible rounding errors, ensures rounding is done as expected
    public void testCalcDSC() {
        assertEquals(1.33, testStmt1.calcDSC()); //test with repeating decimals
        assertEquals(2.00, testStmt2.calcDSC()); //round up from 1.999
        assertEquals(1.50, testStmt3.calcDSC()); //round down from 1.5001
        assertEquals(1.50, testStmt4.calcDSC()); //round up from 1.499
    }

    @Test
    public void testFreeCashFlow() {
        // positive free cash flow
        assertEquals(5000.0, testStmt1.calcFreeCashFlow());
        assertEquals(24999.0, testStmt2.calcFreeCashFlow());
        assertEquals(16669.0, testStmt3.calcFreeCashFlow());
        assertEquals(16664.0, testStmt4.calcFreeCashFlow());
        //negative free cash flow
        assertEquals(-5000.0, testStmt5.calcFreeCashFlow());
        //zero free cash flow
        assertEquals(0, testStmt6.calcFreeCashFlow());

    }

    @Test
    public void testFiscalYear() {
        testStmt1.setFiscalYear(2001);
        assertEquals(2001, testStmt1.getFiscalYear());
    }

    @Test
    public void testGetters() {
        assertEquals(10000, testStmt1.getNetInc());
        assertEquals(5000, testStmt1.getDepExp());
        assertEquals(1000, testStmt1.getIntExp());
        assertEquals(4000, testStmt1.getTaxExp());
        assertEquals(14000, testStmt1.getPrincipleRepaid());
    }
}