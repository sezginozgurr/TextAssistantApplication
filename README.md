# Text Assistant

Single-screen Compose app: generate text from a fake service and share the result.

## Run & test
- Run: `./gradlew installDebug` or run `app` from Android Studio.
- Test: `./gradlew testDebugUnitTest`

## Tool versions
Android Studio Rabbit 1 (2026.2.1), JDK 25 (Gradle daemon toolchain), AGP 9.4.1, Kotlin 2.2.10, Gradle 9.6.0, compileSdk/targetSdk 37, minSdk 26.

## Time spent
About 2.5 hours.

## Operation lifecycle
`TextAssistantViewModel` owns every request in `viewModelScope` and exposes a single `StateFlow<UiState>`, collected with `collectAsStateWithLifecycle`. Generate cancels the previous job, increments a request ID and sets `Loading`; Cancel cancels the job, increments the ID and returns to `Idle` with the input kept. The ViewModel survives rotation, so a running request is never restarted.

**Late results:** each coroutine captures its request ID and writes state only if it still equals the latest ID, so results from replaced or canceled requests are dropped even though the service ignores cancellation.

## Technical choice
Errors are mapped to a typed `TextAssistantError` and rendered from string resources, so raw exception messages never reach the UI.

## Known limitations
- No restoration after process death (out of scope).
- The fake service runs on the main dispatcher because `delay` does not block; a real blocking SDK would be wrapped in `withContext(Dispatchers.IO)`.

## Exposing the service to Flutter
- **Request ID:** Dart generates a unique `requestId` per call and every native response echoes it back.
- **Latest wins:** Dart tracks the latest ID and ignores responses for any other ID, mirroring the Android logic.
- **Cancellation:** `cancel(requestId)` is a separate call; native cancels its job and drops any late result for that ID, and cancellation never surfaces as an error.
- **Errors:** native returns stable error codes (for example `SERVICE_UNAVAILABLE`, `UNKNOWN`) with an optional message; Dart maps codes to UI text and never shows raw exception messages.
- **Results:** a typed result `{ requestId, status: success | error, value | errorCode }` keeps the contract explicit on both sides.

## AI usage
AI was used to generate the project from a written rule set describing the requirements, architecture and constraints. I reviewed every change, verified the behaviour with the unit tests and the case's manual verification flow on an emulator, and adjusted the code where needed.
