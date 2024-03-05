package persistance;

import model.FinancialProjection;
import model.FinancialStatement;
import model.Loan;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads a financial projection from JSON data stored in file
// Source Credits: JsonSerializationDemo project from CPSC 210 repository
public class JsonReader {

    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads financial projection from file and returns it;
    // throws IOException if an error occurs reading data from file
    public FinancialProjection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseFinancialProjection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses financial projection from JSON object and returns it
    private FinancialProjection parseFinancialProjection(JSONObject jsonObject) {
        FinancialProjection fp = new FinancialProjection();
        addLoans(fp, jsonObject);
        addStatements(fp, jsonObject);
        return fp;
    }

    // MODIFIES: fp
    // EFFECTS: parses loans from JSON object and adds them to financial projection
    private void addLoans(FinancialProjection fp, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("loans");
        for (Object json : jsonArray) {
            JSONObject nextLoan = (JSONObject) json;
            addLoan(fp, nextLoan);
        }
    }

    // MODIFIES: fp
    // EFFECTS: parses loan from JSON object and adds it to financial projection
    private void addLoan(FinancialProjection fp, JSONObject jsonObject) {
        int remainingTerm = jsonObject.getInt("remainingTerm");
        double interestRate = jsonObject.getDouble("interestRate");
        double currentBalance = jsonObject.getDouble("currentBalance");
        boolean isProjection = jsonObject.getBoolean("isProjection");
        String description = jsonObject.getString("description");
        Loan loan = new Loan(description);
        loan.setRemainingTerm(remainingTerm);
        loan.setInterestRate(interestRate);
        loan.setCurrentBalance(currentBalance);
        loan.setProjection(isProjection);
        fp.addLoan(loan);
    }


    // MODIFIES: fp
    // EFFECTS: parses financial statements from JSON object and adds them to financial projection
    private void addStatements(FinancialProjection fp, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("statements");
        for (Object json : jsonArray) {
            JSONObject nextStatement = (JSONObject) json;
            addStatement(fp, nextStatement);
        }
    }

    // MODIFIES: fp
    // EFFECTS: parses financial statement from JSON object and adds it to financial projection
    private void addStatement(FinancialProjection fp, JSONObject jsonObject) {
        int fiscalYear = jsonObject.getInt("fiscalYear");
        double netInc = jsonObject.getDouble("netInc");
        double depExp = jsonObject.getDouble("depExp");
        double intExp = jsonObject.getDouble("intExp");
        double taxExp = jsonObject.getDouble("taxExp");
        double principleRepaid = jsonObject.getDouble("principleRepaid");
        FinancialStatement statement = new FinancialStatement(fiscalYear);
        statement.setNetInc(netInc);
        statement.setDepExp(depExp);
        statement.setIntExp(intExp);
        statement.setTaxExp(taxExp);
        statement.setPrincipleRepaid(principleRepaid);
        fp.addStatement(statement);
    }
}
