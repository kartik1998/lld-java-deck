package emergent_lld;

public class Main {

    public static void main(String[] args) {
        ModelArbiter arbiter = new ModelArbiter();
        Router router = new Router(arbiter);

        for (int i = 0; i < 100; i++) {
            LLMResponse response = router.serveLLMRequest("What is the capital of France?");
            System.out.println(response);
        }
    }
}