# Flight Reservation System

## Local chat model

The chat API uses [Ollama](https://ollama.com/) locally and does not require an OpenAI API key or API credits.

1. Install Ollama and make sure it is running.
2. Download the default model in PowerShell:

   ```powershell
   ollama pull qwen3:1.7b
   ```

3. Start the Spring Boot backend from the repository root:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

The backend connects to Ollama at `http://localhost:11434` by default. To use another Ollama URL or model, set `OLLAMA_BASE_URL` or `OLLAMA_MODEL` before starting the backend. The model must already be available in Ollama.

Chat answers are grounded in the current database records for flights, passengers, and reservations. The assistant is instructed to use only that data and to say when the database does not contain an answer. Passenger email addresses and phone numbers are included in the local model context, but should only be returned when explicitly requested.
