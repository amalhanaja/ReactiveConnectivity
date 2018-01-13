# ReactiveConnectivity
ReactiveConnectivity - a library for Listen Connectivity Change on Android

[![API](https://img.shields.io/badge/API-15%2B-red.svg?style=flat)](https://android-arsenal.com/api?level=15)
[![](https://jitpack.io/v/amalhanaja/ReactiveConnectivity.svg)](https://jitpack.io/#amalhanaja/ReactiveConnectivity)
[![codebeat badge](https://codebeat.co/badges/f6612a30-4467-49b5-860f-98f9b34b8975)](https://codebeat.co/projects/github-com-amalhanaja-reactiveconnectivity-master)

ReactiveConnectivity is an Android Library to Listening NetworkConnectivity with RxJava Observables, It's written with Reactive Programming Approach. Library supports both new and legacy network monitoring.

## Installation

-  Add the following to your project level `build.gradle`:
 
```gradle
allprojects {
	repositories {
		maven { url "https://jitpack.io" }
	}
}
```
  -  Add this to your app `build.gradle`:
 
```gradle
dependencies {
	implementation 'com.github.amalhanaja:ReactiveConnectivity:1.0'
}
```
## Permission
Add the `ACCESS_NEWORK_STATE` permission to `AndroidManifest.xml`

```xml
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```
## Using RxJava2
  -  Getting Observbale:
```kotlin
private val observable by lazy {
        ReactiveConnectivity.buildObserver(this)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }
private var disposable: Disposable? = null  // Lazy Initialization
```
  -  Initialize disposable on `onStart()` for `Activity` `OR` `onResume()` for `Fragment` method :
```kotlin
disposable = observable.subscribe({ onChange ->
            // TODO : DO SOMETHING ON NETWORK CHANGE
        }, { error ->
            // TODO : HANDLE ERROR HERE
        })
```
  -  Dispose disposable on `onStop()` for `Activity` `OR` `onPause()` for `Fragment` method to avoid Memory Leaks :
```kotlin
disposable?.dispose()
```
## Without RxJava2
  -  Initialize Observer:
```kotlin
private val observer by lazy {
        ReactiveConnectivity(
                context = this,
                onError = { it.printStackTrace() },
                onChange = {
                    Toast.makeText(this, "MAIN ACTIVITY : ${it.name}", Toast.LENGTH_SHORT).show()
                }
        )
}
```
  -  Subscribe observer on `onStart()` for `Activity` `OR` `onResume()` for `Fragment` method :
```kotlin
observer.subscribe()
```
  -  Dispose observer on `onStop()` for `Activity` `OR` `onPause()` for `Fragment` method to avoid Memory Leaks :
```kotlin
disposable.dispose()
```
License
=======
Copyright 2018 Alfian Akmal Hanantio

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

**Special thanks** to [pwittchen](https://github.com/pwittchen), [ReactiveX](https://github.com/ReactiveX), [JetBrains](https://github.com/JetBrains) and [jitpack.io](https://github.com/jitpack-io) for their contributions to this project.
