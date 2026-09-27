# Cuewise

**Practice the conversation before it counts.**

Cuewise is a mobile rehearsal coach for students and early-career people who want to handle high-stakes conversations with more clarity and confidence. It turns an anxious moment into a short, repeatable practice loop:

1. Choose a real-life conversation.
2. Write what you would say.
3. Get structured coaching on clarity, empathy, boundaries, and next steps.
4. Try again with one small upgrade.

## Shipaton / Next Gen

Cuewise is being prepared for RevenueCat Shipaton 2026's Next Gen Award. The repository is intentionally public and includes an MIT license. The app uses RevenueCat for the premium rehearsal pack; development can use RevenueCat Test Store without a live Google Play account.

## Run locally

1. Install Android Studio with Android SDK 35 and JDK 17.
2. Open this folder in Android Studio.
3. Create a RevenueCat project and a Test Store configuration.
4. Add the Test Store API key to `~/.gradle/gradle.properties`:

   ```properties
   REVENUECAT_API_KEY=your_test_store_api_key
   ```

5. Sync Gradle and run the `app` configuration on an Android device or emulator.

If no key is supplied, the app remains in local demo mode so the core experience can still be explored. The purchase integration is present in `RevenueCatManager.kt`; configure a real Test Store key before recording the monetization portion of the demo.

> Never commit an API key. Never submit a production store build with a RevenueCat Test Store key. For a real store release, switch to the appropriate platform key and configure products in that store.

## Repository map

- `app/src/main/java/com/cuewise/app/MainActivity.kt` — polished Compose prototype and core rehearsal loop
- `app/src/main/java/com/cuewise/app/RevenueCatManager.kt` — purchase, restore, and entitlement wrapper
- `app/src/main/java/com/cuewise/app/ui/theme/` — visual system
- `docs/DEVPOST_DRAFT.md` — submission copy and demo narrative
- `marketing/` — launch asset notes

## Product principles

- Give users one actionable improvement, not a wall of generic advice.
- Make practice feel safe, private, and repeatable.
- Monetize depth and repetition, not anxiety.
- Keep the first success under two minutes.
