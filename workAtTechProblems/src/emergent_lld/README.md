# Emergent 

 -> Design an LLM gateway or an LLM router
 -> Route incoming requests to the right LLM
 -> Router (P0)
 -> Number of requests -> (Priority -> (claude, openai, gemini etc.)
 -> based on priority
 -> Requests are coming -> (try for claude, if x number of consecutive requests fail, then LLM is degraded, which means go to P2 LLM)
 -> Y number of requests succeed then enable that LLM
 -> Still send 5 percent of traffic to claude (as soon as y number of requests succeed) -flag_1
 
## 

Router
 -> serveLLMRequest(String prompt)
    { response: "", model: "" }
    { error: "internal server error", model: ""}

ModelArbiter 
 -> registers LLM models -> (by default it'll be claude, openai, gemini)
 -> selecting the model based on priority and availbity

IModel 
 -> model_healthy_state
 -> handlePrompt(String prompt) -> if toggled to false then throw a exception
 -> toggleSuccess() -> true or false will return a success response

-> FIFO based priority and percentage based routing only in case of healthy_state being false



 