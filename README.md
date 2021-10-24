# Kontent
Kontent is an Demo Content Browsing application using ITunesService

<img src="/assets/kontent_demo.gif" width="25%" height="25%"/>

## Project Structure
The project is structured with [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) in mind.

The project currently only contains two Module:
1) Android App Module
2) Common Module (KMM)

The `common` module contains all the application logic which could be shared across multiple platforms.

> TODO: Create iOS App Module to use the common module

## Libraries
Most Libraries used in the Project are KMM compatible:

1) [Ktor](https://github.com/ktorio/ktor) for Networking
2) [Kotlin Serializationx](https://github.com/Kotlin/kotlinx.serialization) for Serialization
3) [Koin](https://github.com/InsertKoinIO/koin) for Dependency Injection
4) [SqlDelight](https://github.com/cashapp/sqldelight) for Database Caching
5) [MultiplatformSettings](https://github.com/russhwolf/multiplatform-settings) for SharedPreferences/NSUserDefaults

The project also heavily utilizes `Coroutines` and `Flow` for handling states and fetching data.

## Architecture
The project uses MVVM architecture which utilizes `Flow` instead of either `LiveData` or `RxJava` for its reactive components.

The project follows the recommended Android Architecture.

## UI
The Android Application is fully created with [Jetpack Compose](https://developer.android.com/jetpack/compose?gclid=Cj0KCQjwiNSLBhCPARIsAKNS4_eUg2ZRMSjiYkSdM-PP_gCqZrUmbgNFwTKvcJV6mZ2PQ5EhOwXLIuwaAn8LEALw_wcB&gclsrc=aw.ds) which simplifies alot of UI components and allows alot of components/composable to be much more reusable and flexible.

> TODO: Create iOS App with Swift UI

## Other Things
1. Add Unit Test, UI and Integration Test
2. Review existing compose components
3. Create custom theme (currently uses default theme)
4. Add BuildTypes and Variants
5. Create the iOS Application

## APK
A Signed APK could be retrieved [here](https://github.com/archiegq21/Kontent/blob/master/androidApp/release/kontent_app.apk).

Note: The apk is signed with a debug key.
