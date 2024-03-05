package persistance;

import model.FinancialStatement;
import model.Loan;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Source Credits: JsonSerializationDemo project from CPSC 210 repository
public class JsonTest {

    //EFFECTS: tests if expected fields of a loan are equal to the actual fields of the object
    protected void checkLoan
    (int remainingTerm, double interestRate, double currentBalance,
     boolean isProjection, String description, Loan loan) {
        assertEquals(remainingTerm, loan.getRemainingTerm());
        assertEquals(interestRate, loan.getInterestRate());
        assertEquals(currentBalance, loan.getCurrentBalance());
        assertEquals(isProjection, loan.isProjection());
        assertEquals(description, loan.getDescription());
    }

    //EFFECTS: tests if expected fields of a financial statement are equal to the actual fields of the object
    protected void checkStatement
    (int fiscalYear, double netInc, double depExp, double intExp,
     double taxExp, double principleRepaid, FinancialStatement statement) {
        assertEquals(fiscalYear, statement.getFiscalYear());
        assertEquals(netInc, statement.getNetInc());
        assertEquals(depExp, statement.getDepExp());
        assertEquals(intExp, statement.getIntExp());
        assertEquals(taxExp, statement.getTaxExp());
        assertEquals(principleRepaid, statement.getPrincipleRepaid());
    }
}
