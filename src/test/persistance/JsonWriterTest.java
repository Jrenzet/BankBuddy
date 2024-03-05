package persistance;

import model.FinancialProjection;
import model.FinancialStatement;
import model.Loan;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Source Credits: JsonSerializationDemo project from CPSC 210 repository
public class JsonWriterTest extends JsonTest{

    private JsonWriter writer;
    private JsonReader reader;
    private FinancialProjection emptyProjection;
    private FinancialProjection testProjection;
    private FinancialProjection readProjection;
    private Loan testLoan1;
    private Loan testLoan2;
    private FinancialStatement testStatement1;
    private FinancialStatement testStatement2;

    @Test
    public void testWriterInvalidFile() {
        writer = new JsonWriter("./data/invalidFileName\0:|?<>");
        try {
            writer.open();
            fail("File not found exception expected");
        } catch (FileNotFoundException f) {
            // exception expected
        }
    }


    @Test
    public void testWriterBlankFile() {
        writer = new JsonWriter("./data/testWriterEmptyProjection.json");
        emptyProjection = new FinancialProjection();
        try {
            writer.open();
            writer.write(emptyProjection);
            writer.close();

            reader = new JsonReader("./data/testWriterEmptyProjection.json");
            readProjection = reader.read();
            assertEquals(0, readProjection.getLoans().size());
            assertEquals(0, readProjection.getStatements().size());

        } catch (IOException i) {
            fail("unexpected IOException");
        }
    }

    @Test
    public void testWriterGeneralFile() {
        writer = new JsonWriter("./data/testWriterGeneralProjection.json");
        testProjection = new FinancialProjection();
        makeTestProjection(testProjection);
        try {
            writer.open();
            writer.write(testProjection);
            writer.close();

            reader = new JsonReader("./data/testWriterGeneralProjection.json");
            readProjection = reader.read();
            List<Loan> loans = readProjection.getLoans();
            List<FinancialStatement> statements = readProjection.getStatements();
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
            fail("unexpected IOException");
        }
    }

    //MODIFIES: fc
    //EFFECTS: creates test projection with 2 loans and statements
    public void makeTestProjection(FinancialProjection fc) {
        testLoan1 = new Loan("work truck");
        testLoan1.setRemainingTerm(120);
        testLoan1.setInterestRate(5.5);
        testLoan1.setCurrentBalance(100000.00);
        testLoan1.setProjection(false);

        testLoan2 = new Loan("new house");
        testLoan2.setRemainingTerm(240);
        testLoan2.setInterestRate(6.3);
        testLoan2.setCurrentBalance(350000.00);
        testLoan2.setProjection(true);

        testStatement1 = new FinancialStatement(2022);
        testStatement1.setNetInc(50000.00);
        testStatement1.setDepExp(20000.00);
        testStatement1.setIntExp(3000.00);
        testStatement1.setTaxExp(2000.00);
        testStatement1.setPrincipleRepaid(20000.00);

        testStatement2 = new FinancialStatement(2023);
        testStatement2.setNetInc(60000.00);
        testStatement2.setDepExp(25000.00);
        testStatement2.setIntExp(4000.00);
        testStatement2.setTaxExp(2500.00);
        testStatement2.setPrincipleRepaid(22000.00);

        fc.addLoan(testLoan1);
        fc.addLoan(testLoan2);
        fc.addStatement(testStatement1);
        fc.addStatement(testStatement2);
    }
}
