package emergent_lld;

public class LLMResponse {

    private final String response;
    private final String error;
    private final String model;

    private LLMResponse(String response, String error, String model) {
        this.response = response;
        this.error = error;
        this.model = model;
    }

    public static LLMResponse success(String response, String model) {
        return new LLMResponse(response, null, model);
    }

    public static LLMResponse failure(String error, String model) {
        return new LLMResponse(null, error, model);
    }

    public String getResponse() { return response; }
    public String getError()    { return error; }
    public String getModel()    { return model; }
    public boolean isSuccess()  { return error == null; }

    @Override
    public String toString() {
        return isSuccess()
            ? "{ response: \"" + response + "\", model: \"" + model + "\" }"
            : "{ error: \"" + error + "\", model: \"" + model + "\" }";
    }
}