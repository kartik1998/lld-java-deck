package emergent_lld;

public interface IModel {

    String getName();

    int getPriority();

    boolean isHealthy();

    LLMResponse handlePrompt(String prompt);

    void toggleSuccess();
}