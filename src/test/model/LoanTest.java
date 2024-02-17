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

    @BeforeEach
    public void setup() {
        testLoan1 = new Loan("property");
        testLoan2 = new Loan("almost paid");
        testLoan3 = new Loan("vehicle");
        testLoan4 = new Loan("projected machine");
        testLoan5 = new Loan("old loan");

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
    public void testCalculateMonthlyPayment() {
        assertEquals(617.0, testLoan1.calculateMonthlyPayment());
        assertEquals(514.0, testLoan2.calculateMonthlyPayment());
        assertEquals(5154.0, testLoan3.calculateMonthlyPayment());
        assertEquals(1485.0, testLoan4.calculateMonthlyPayment());
        assertEquals(3788.0, testLoan5.calculateMonthlyPayment());
    }

    @Test
    public void testCalculateAnnualPaymentLongerTerm() {
        assertEquals(617.0 * 12, testLoan1.calculateAnnualPayment());
        assertEquals(1485.0 * 12, testLoan4.calculateAnnualPayment());

    }

    @Test
    public void testCalculateAnnualPaymentShorterTerm() {
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
