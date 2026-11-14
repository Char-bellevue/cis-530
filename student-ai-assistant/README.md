# Student AI Assistant (CIS 530, Assignment 10.2)

A Spring Boot project that uses Spring AI to answer student questions four different ways: a basic prompt, structured output, prompt stuffing with course notes, and an introductory RAG endpoint.

## Tech stack

- Java 25, Maven
- Spring Boot 4.1.2 and Spring AI 2.0.x
- Dependencies: Spring Web, Validation, Lombok, OpenAI, `spring-ai-vector-store`, `spring-ai-vector-store-advisor`
- Models: `gpt-4o-mini` (chat) and `text-embedding-3-small` (embeddings)

## Note about the Spring AI version

The assignment was written for an older Spring AI release. In the version this project uses, the dependency `spring-ai-advisors-vector-store` could not be resolved, so I used `spring-ai-vector-store-advisor` instead. Everything else follows the assignment.

## How to run

1. Set your OpenAI API key as an environment variable in the same terminal you will run the app from. The key is not stored in the code.

   ```powershell
   $env:OPENAI_API_KEY="your_key_here"
   ```

2. Start the application:

   ```powershell
   .\mvnw spring-boot:run
   ```

3. Wait for these lines in the console:
   - `Tomcat started on port 8080`
   - `RAG documents loaded into the vector store.`

## Endpoints

All endpoints use `POST` and accept JSON. The sample requests are also in `requests.http`.

| Endpoint | What it shows | Request body |
|---|---|---|
| `/api/ai/ask` | Basic prompt | `{"question": "..."}` |
| `/api/ai/study-plan` | Structured output mapped to a Java object | `{"studentName": "...", "topic": "...", "problem": "...", "goal": "..."}` |
| `/api/ai/context-help` | Prompt stuffing with `week10_notes.txt` | `{"question": "..."}` |
| `/api/ai/rag-help` | RAG with a vector store and `QuestionAnswerAdvisor` | `{"question": "..."}` |

Example:

```
POST http://localhost:8080/api/ai/ask
Content-Type: application/json

{
  "question": "Explain Spring AI in simple terms for a student learning Spring Boot."
}
```

## Project structure

```
src/main/java/edu/bellevue/cis530/studentai/
    StudentAiAssistantApplication.java
    config/StudentAiConfig.java
    controller/AiController.java
    dto/ (AiQuestionRequest, AiTextResponse, ContextHelpRequest,
          RagHelpRequest, StudyPlanRequest, StudyPlanResponse)
    service/AiService.java
    service/DocumentLoaderService.java
    service/impl/AiAssistantService.java
src/main/resources/
    application.properties
    docs/ (week10_notes.txt, spring_ai_faq.txt, rag_basics.txt)
```

## How it works

- **ask:** sends the question to the model and returns the text.
- **study-plan:** asks the model for a summary, three action items, key terms, and encouragement, and maps the result into `StudyPlanResponse`.
- **context-help:** reads `week10_notes.txt` and puts it into the prompt before the question.
- **rag-help:** at startup the three text files are embedded into a `SimpleVectorStore`. For each question, `QuestionAnswerAdvisor` retrieves the most relevant documents and sends them to the model with the question.

## Testing

I tested all four endpoints in Postman, and each one returned `200 OK`. The screenshots are in the submitted Word document.
