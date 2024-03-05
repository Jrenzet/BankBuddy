package persistance;

import org.json.JSONObject;

// Source Credits: JsonSerializationDemo project from CPSC 210 repository
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
