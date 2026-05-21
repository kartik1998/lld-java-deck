package emergent_lld;

public class Router {

    private final ModelArbiter arbiter;

    public Router(ModelArbiter arbiter) {
        this.arbiter = arbiter;
    }

    /**
     * Routes the prompt to the best available model.
     * Returns a success response with the responding model name,
     * or a failure response if no model can serve the request.
     */
    public LLMResponse serveLLMRequest(String prompt) {
        // TODO:
        //   1. ask arbiter to select a model
        //   2. call model.handlePrompt(prompt)
        //   3. on success -> model.toggleSuccess(true); return LLMResponse.success(...)
        //   4. on exception -> model.toggleSuccess(false); try next model or return LLMResponse.failure(...)
        IModel model = arbiter.selectModel();
        if (model == null) {
            return LLMResponse.failure("REJECTED", "none");
        }
        return model.handlePrompt(prompt);
    }
}