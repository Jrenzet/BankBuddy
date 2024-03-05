package persistance;

import model.FinancialProjection;
import model.FinancialStatement;
import model.Loan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    public void testNonExistentFile() {

    }

    @Test
    public void testEmptyFile() {

    }

    @Test
    public void testGeneralFile() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralProjection.json");
        try {
            FinancialProjection testProjection = reader.read();
            List<Loan> loans = testProjection.getLoans();
            List<FinancialStatement> statements = testProjection.getStatements();
            assertEquals(2, loans.size());
            assertEquals(2, statements.size());
            checkLoan(120, 5.5, 100000.00,
                    false, "work truck", loans.get(0));
            checkLoan(240, 6.3, 350000.00,
                    true, "new house", loans.get(1));
            checkStatement(2022, 50000.00, 20000.00, 3000.00,
                    2000.00, 20000.00, statements.get(0));
            checkStatement(2023, 60000.00, 25000.00, 4000.00,
                    2500.00, 22000.00, statements.get(1));
        } catch (IOException i) {
            fail("IO exception ocurred");
        }
    }

}
