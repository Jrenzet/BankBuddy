package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FinancialProjectionTest {

    private FinancialProjection testProj1;

    private FinancialStatement testStmt1;
    private FinancialStatement testStmt2;
    private FinancialStatement testStmt3;
    private FinancialStatement testStmt4;
    private FinancialStatement testStmt5;
    private FinancialStatement testStmt6;

    private Loan testLoan1;
    private Loan testLoan2;
    private Loan testLoan3;
    private Loan testLoan4;
    private Loan testLoan5;

    @BeforeEach
    public void setup() {

        testProj1 = new FinancialProjection("Test Projection");

        testStmt1 = new FinancialStatement(2024);
        testStmt1.setNetInc(10000);
        testStmt1.setDepExp(5000);
        testStmt1.setIntExp(1000);
        testStmt1.setTaxExp(4000);
        testStmt1.setPrincipleRepaid(14000);

        testStmt2 = new FinancialStatement(2023);
        testStmt2.setNetInc(19999);
        testStmt2.setDepExp(10000.55);
        testStmt2.setIntExp(10000);
        testStmt2.setTaxExp(10000);
        testStmt2.setPrincipleRepaid(15001);

        testStmt3 = new FinancialStatement(2022);
        testStmt3.setNetInc(20000);
        testStmt3.setDepExp(10000);
        testStmt3.setIntExp(10000);
        testStmt3.setTaxExp(10000);
        testStmt3.setPrincipleRepaid(23331);

        testStmt4 = new FinancialStatement(2021);
        testStmt4.setNetInc(20000);
        testStmt4.setDepExp(10000);
        testStmt4.setIntExp(10000);
        testStmt4.setTaxExp(10000);
        testStmt4.setPrincipleRepaid(23336);

        testStmt5 = new FinancialStatement(2021);
        testStmt5.setNetInc(7774);
        testStmt5.setDepExp(0);
        testStmt5.setIntExp(0);
        testStmt5.setTaxExp(0);

        testStmt6 = new FinancialStatement(2021);
        testStmt6.setNetInc(8515);
        testStmt6.setDepExp(0);
        testStmt6.setIntExp(0);
        testStmt6.setTaxExp(0);

        testLoan1 = new Loan("property", false);
        testLoan2 = new Loan("almost paid", false);
        testLoan3 = new Loan("vehicle", true);
        testLoan4 = new Loan("projected machine", true);
        testLoan5 = new Loan("old loan", false);

        testLoan1.setInterestRate(5.55);
        testLoan2.setInterestRate(6.0);
        testLoan3.setInterestRate(2.20);
        testLoan4.setInterestRate(7);
        testLoan5.setInterestRate(3.43);

        testLoan1.setRemainingTerm(300);
        testLoan2.setRemainingTerm(10);
        testLoan3.setRemainingTerm(240);
        testLoan4.setRemainingTerm(60);
        testLoan5.setRemainingTerm(6);

        testLoan1.setCurrentBalance(100000);
        testLoan2.setCurrentBalance(5000);
        testLoan3.setCurrentBalance(1000000);
        testLoan4.setCurrentBalance(75000);
        testLoan5.setCurrentBalance(22500);

    }

    @Test
    public void testAddLoan() {
        testProj1.addLoan(testLoan1);
        assertTrue(testProj1.getLoans().contains(testLoan1));
        testProj1.addLoan(testLoan2);
        assertTrue(testProj1.getLoans().contains(testLoan2));
    }

    @Test
    public void testAddStatement() {
        testProj1.addStatement(testStmt1);
        assertTrue(testProj1.getStatements().contains(testStmt1));
        testProj1.addStatement(testStmt2);
        assertTrue(testProj1.getStatements().contains(testStmt2));
    }

    @Test
    public void testRemoveLoan() {
        testProj1.addLoan(testLoan1);
        testProj1.addLoan(testLoan2);
        testProj1.removeLoan(testLoan2);
        assertFalse(testProj1.getLoans().contains(testLoan2));
        assertTrue(testProj1.getLoans().contains(testLoan1));
        testProj1.removeLoan(testLoan1);
        assertFalse(testProj1.getLoans().contains(testLoan1));
    }

    @Test
    public void testRemoveStatement() {
        testProj1.addStatement(testStmt1);
        testProj1.addStatement(testStmt2);
        testProj1.removeStatement(testStmt2);
        assertFalse(testProj1.getStatements().contains(testStmt2));
        assertTrue(testProj1.getStatements().contains(testStmt1));
        testProj1.removeStatement(testStmt1);
        assertFalse(testProj1.getStatements().contains(testStmt1));
    }

    @Test
    public void testTotalPaymentOneLoan() {
        testProj1.addLoan(testLoan1);
        assertEquals(testLoan1.calculateAnnualPayment(), testProj1.totalPayment());
    }

    @Test
    public void testTotalPaymentMultiLoan() {
        testProj1.addLoan(testLoan1);
        testProj1.addLoan(testLoan2);
        testProj1.addLoan(testLoan3);
        testProj1.addLoan(testLoan4);
        double pmt1 = testLoan1.calculateAnnualPayment();
        double pmt2 = testLoan2.calculateAnnualPayment();
        double pmt3 = testLoan3.calculateAnnualPayment();
        double pmt4 = testLoan4.calculateAnnualPayment();
        assertEquals(pmt1 + pmt2 + pmt3 + pmt4, testProj1.totalPayment());
    }

    @Test
    public void testAverageEbitdaOneYear() {
        testProj1.addStatement(testStmt1);
        assertEquals(testStmt1.calcEbitda(), testProj1.averageEbitda());
    }

    @Test
    public void testAverageEbitdaMultiYear() {
        testProj1.addStatement(testStmt1);
        testProj1.addStatement(testStmt2);
        testProj1.addStatement(testStmt3);
        double ebitda1 = testStmt1.calcEbitda();
        double ebitda2 = testStmt2.calcEbitda();
        double ebitda3 = testStmt3.calcEbitda();
        assertEquals((ebitda1 + ebitda2 + ebitda3) / 3, testProj1.averageEbitda());

        testProj1.addStatement(testStmt4);
        double ebitda4 = testStmt4.calcEbitda();
        assertEquals((ebitda1 + ebitda2 + ebitda3 + ebitda4) / 4, testProj1.averageEbitda());
    }

    @Test
    public void testProjectedDSC() {
        testProj1.addStatement(testStmt1);
        testProj1.addStatement(testStmt2);
        testProj1.addStatement(testStmt3);

        testProj1.addLoan(testLoan1);
        testProj1.addLoan(testLoan2);
        testProj1.addLoan(testLoan5);

        assertEquals(1.13, testProj1.projectedDSC());
    }

    @Test
    public void testProjectionRatingGreen() {
        testProj1.addStatement(testStmt1);
        testProj1.addStatement(testStmt2);
        testProj1.addLoan(testLoan4);
        assertEquals("Green", testProj1.projectionRating()); //DSC of 1.96
    }

    @Test
    public void testProjectionRatingRed() {
        testProj1.addStatement(testStmt1);
        testProj1.addLoan(testLoan1);
        testProj1.addLoan(testLoan4);

        assertEquals("Red", testProj1.projectionRating()); //DSC of 0.79
    }

    @Test
    public void testProjectionRatingYellow() {
        testProj1.addStatement(testStmt1);
        testProj1.addStatement(testStmt2);
        testProj1.addStatement(testStmt3);
        testProj1.addLoan(testLoan1);
        testProj1.addLoan(testLoan2);
        testProj1.addLoan(testLoan5);

        assertEquals("Yellow", testProj1.projectionRating()); //DSC of 1.13
    }

    @Test
    public void testProjectionRatingEdgeCaseLow() {
        testProj1.addStatement(testStmt5);
        testProj1.addLoan(testLoan1);

        assertEquals("Yellow", testProj1.projectionRating()); //DSC of 1.05
    }

    @Test
    public void testProjectionRatingEdgeCaseHigh() {
        testProj1.addStatement(testStmt6);
        testProj1.addLoan(testLoan1);

        assertEquals("Yellow", testProj1.projectionRating()); //DSC of 1.15
    }


}
