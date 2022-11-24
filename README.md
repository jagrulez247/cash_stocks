# Cash Stocks App

Modern Android architecture which showcases Kotlin, MVVM, Hilt, Coroutines, Flow, LiveData, Retrofit, Moshi, Room, ViewBinding, Unit testing and Kotlin Gradle DSL.

## Features

* Single activity, multi fragment architecture.
* MVVM + Repository design Pattern.
* Jetpack Libraries and Architecture Components.
* Kotlin Gradle DSL.

## Structure

* Networking via Retrofit and OkHttp with interceptor.
* Data parsing via Moshi.
* Dependency injection via Hilt.
* Abstracted `LocalCoroutineDispatcher.kt` to control context switching for main vs tests.
* Remote data source backed up via local db for quick loads.
* Repository using Kotlin flows to update ViewModel on data load.
* UI updates via LiveData and sealed class UI state.

## Testing

Local unit testing is done for ViewModel and Repository via JUnit, Mockk and coroutine test dependencies

