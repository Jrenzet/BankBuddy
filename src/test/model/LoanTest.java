package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoanTest {

    private Loan testLoan1;
    private Loan testLoan2;
    private Loan testLoan3;
    private Loan testLoan4;
    private Loan testLoan5;
    private Loan testLoan6;
    private Loan testLoan7;

    @BeforeEach
    public void setup() {
        testLoan1 = new Loan("property");
        testLoan1.setInterestRate(5.55);
        testLoan1.setRemainingTerm(300);
        testLoan1.setCurrentBalance(100000);

        testLoan2 = new Loan("almost paid");
        testLoan2.setInterestRate(6.0);
        testLoan2.setRemainingTerm(10);
        testLoan2.setCurrentBalance(5000);

        testLoan3 = new Loan("vehicle");
        testLoan3.setInterestRate(2.20);
        testLoan3.setRemainingTerm(240);
        testLoan3.setCurrentBalance(1000000);

        testLoan4 = new Loan("projected machine");
        testLoan4.setInterestRate(7);
        testLoan4.setRemainingTerm(60);
        testLoan4.setCurrentBalance(75000);

        testLoan5 = new Loan("old loan");
        testLoan5.setInterestRate(3.43);
        testLoan5.setRemainingTerm(6);
        testLoan5.setCurrentBalance(22500);

        testLoan6 = new Loan("high interest");
        testLoan6.setInterestRate(40);
        testLoan6.setRemainingTerm(120);
        testLoan6.setCurrentBalance(75000);

        testLoan7 = new Loan("no interest");
        testLoan7.setInterestRate(0);
        testLoan7.setRemainingTerm(60);
        testLoan7.setCurrentBalance(200000);
    }

    @Test
    // test monthly payment calculator for loans, all test values are from online loan calculator
    public void testCalculateLoanPayment() {
        // standard loans
        assertEquals(617, testLoan1.calculateMonthlyPayment());
        assertEquals(1485, testLoan4.calculateMonthlyPayment());
        // load with < 1 year remaining
        assertEquals(514, testLoan2.calculateMonthlyPayment());
        assertEquals(3788, testLoan5.calculateMonthlyPayment());
        // loan with
        assertEquals(5154, testLoan3.calculateMonthlyPayment());
        // unrealistically high interest rate
        assertEquals(2550, testLoan6.calculateMonthlyPayment());
        // interest free loan
        assertEquals(3333, testLoan7.calculateMonthlyPayment());
    }

    @Test
    public void testCalculateAnnualPaymentLongerThan1YearTerm() {
        assertEquals(617.0 * 12, testLoan1.calculateAnnualPayment());
        assertEquals(1485.0 * 12, testLoan4.calculateAnnualPayment());

    }

    @Test
    public void testCalculateAnnualPaymentLessThan1YearTerm() {
        assertEquals(514.0 * 10, testLoan2.calculateAnnualPayment());
        assertEquals(3788.0 * 6, testLoan5.calculateAnnualPayment());
    }

    @Test
    public void testDescription() {
        assertEquals("property", testLoan1.getDescription());
        testLoan1.setDescription("house");
        assertEquals("house", testLoan1.getDescription());
    }

    @Test
    public void testGetters() {
        assertEquals(5.55, testLoan1.getInterestRate());
        assertEquals(300, testLoan1.getRemainingTerm());
        assertEquals(100000, testLoan1.getCurrentBalance());
    }

    @Test
    public void testIsProjection() {
        testLoan1.setProjection(true);
        testLoan2.setProjection(false);
        assertTrue(testLoan1.isProjection());
        assertFalse(testLoan2.isProjection());
    }
}
