# Count Words Indexing Service (Spring Boot 2.7.6, Java 17)

A small, production-style service that **indexes an uploaded text file** and returns:

- `countStartingWithM` — number of words starting with **M/m** (case-insensitive)
- `wordsLongerThan5` — all words with **length > 5** (order preserved)

It’s designed to be **extensible**: each business rule is a pluggable Strategy (`Rule<?>`) bean.
We use **one custom exception** (`IndexingProcessingException`) for all error paths and map it to JSON via a global handler.

---

## Tech Stack

- Java **17** (required for this project)
- Spring Boot **2.7.6** (Web, Validation, Test)
- Maven 3.x
- JUnit 5 + MockMvc

---

## Project Structure

```
src/main/java/org/example/indexer
├── IndexerApplication.java                  # @SpringBootApplication (root package for component scan)
├── api
│   └── controller
│       └── IndexController.java             # POST /index (multipart upload)
├── exception
│   ├── GlobalExceptionHandler.java         # Maps IndexingProcessingException to JSON
│   └── IndexingProcessingException.java    # One exception carrying HttpStatus + domain code
├── model
│   └── ErrorResponse.java                   # error payload DTO (record)
├── service
│   ├── Rule.java                            # Strategy interface (Stream-based)
│   ├── CountStartsWithMRule.java            # counts words starting with a configurable set of letters (default: m)
│   ├── WordsLongerThanRule.java             # returns words with length > min (default: 5)
│   └── IndexService.java                    # stores upload to temp file; supplies a fresh Stream per rule
└── util
    └── WordTokenizer.java                   # splits on non-letters (Unicode-aware)
```

---

## Build & Run

```bash
# Ensure Java 17 for BOTH Maven and runtime
java -version
mvn -v

1. **Clone the repository** or **extract the `count-words-api.zip`** archive

   ```bash
   git clone https://github.com/Sunnyday16/count-words-api.git
   or extract the `count-words-api.zip 
   cd count-words-api (go to the count-words-api directory)
   ```


2. **Build with Maven**

   ```bash
   mvn clean package
   ```

3. **Run the application**

   ```bash
   java -jar target/count-words-api-1.0-SNAPSHOT.jar
 
```

App listens on `http://localhost:8080`.

---

## API

### `POST /index`

- **Consumes**: `multipart/form-data` with a part named **`file`**
- **Produces**: `application/json`

**cURL:**
```bash
curl -X POST http://localhost:8080/index   -H "Accept: application/json"   -F "file=@/path/to/input.txt"
- example input.txt to upload and test is there in resources directory of this project.
```

**Example success response (defaults):**
```json
{
  "countStartingWithM": 7,
  "wordsLongerThan5": ["modern","systems","microservices","metrics","matter"]
}
```
---

## Configuration

Rule names and parameters are configurable via `src/main/resources/application.yml`.

```yaml
rules:
  count-starts-with:
    # JSON key (default: countStartingWithM)
    name: countStartingWithM
    # Set of starting letters (case-insensitive). "mp" means starts with m OR p.
    letters: "m"
  longer-than:
    # JSON key (default: wordsLongerThan5)
    name: wordsLongerThan5
    # Include words strictly longer than this value
    min: 5
```


---

## Error Handling (single exception)

We use **one** exception across the app: `IndexingProcessingException` — it **carries** the desired HTTP status and a domain error code.

- **Missing/empty file** → throw `IndexingProcessingException.badRequest("INDEX_MISSING_FILE", "...")`
- **I/O problems** → throw `IndexingProcessingException.internal("INDEX_IO_ERROR", "...", cause)`

The `GlobalExceptionHandler` converts it to a consistent JSON body:
```json
{
  "error": "Bad Request",
  "code": "INDEX_MISSING_FILE",
  "message": "Required part 'file' is missing or empty",
  "path": "/index",
  "timestamp": "2025-08-15T12:34:56Z"
}
```
## OpenAPI Documentation

The API is documented using Springdoc OpenAPI. After starting the application, navigate to:

- OpenAPI JSON: `/v3/api-docs`
- Swagger UI: `/swagger-ui.html`, `/swagger-ui/index.html`, or `/swagger-ui/index.htm`
- eg: http://localhost:8080/swagger-ui/index.html
- example input.txt to upload and test is there in resources directory of this project.

---

## Tests

Run:
```bash
mvn clean test
```

What’s covered (recommended set):
- **Rule tests**: logic + property-like overrides via setters
- **Tokenizer test**: punctuation, hyphen, email
- **Service integration**: realistic sample text → expected keys/values
- **Controller tests**:
  - Valid upload → 200 + JSON body
  - Missing/empty file → 400 + error JSON (from `IndexingProcessingException`)

---


