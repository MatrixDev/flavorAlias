# flavorAlias

[![Release](https://jitpack.io/v/MatrixDev/flavorAlias.svg)](https://jitpack.io/#MatrixDev/flavorAlias)

# Overview

Normally when working with multiple flavors and only one of those needs different implementation, you'd have to copy same implementation to each other flavor. For example if you had 100 flavors and some class had different behavior for one of those, you'd still need 100 copies of that class in each flavor with only one beeing different.

This is caused by gradle's limitation which forbids class files replacement through flavors (yet you still can replace other resources).

There are few possible ways to overcome this limitation:
- move all affected classes from `main` to `flavor` folder - leads to tons of duplicated code
- use reflections to dynamically load classes from flavors - proguard will see those as unused which requires additional rules
- use code generation to switch classes at compile time

This library uses later solution to generate Kotlin aliases when needed.

# Example

Lets assume that we have 3 flavors and `MyObject` class which we need to have different implementation only for flavor3.

### Step 1

Create base `MyObject` implementation in `main` flavor:

```kotlin
@GenerateTypeAlias("MyObject", priority = 1)
open class MyObjectMain {
    open fun doSomething() {}
}
```

Actual class name must be unique for each flavor and also must be different from alias name.

`@GenerateTypeAlias` is the only annotation provided by the library and it takes only two arguments:
1. name - this will be the name of the generated alias
2. priority - class annotated with the highest available priority wins and will be aliased

### Step 2

Create base `MyObject` implementation in `flavor3` flavor:

```kotlin
@GenerateTypeAlias("MyObject", priority = 2)
class MyObjectFlavor3 : MyObjectMain() {
    override fun doSomething() {}
}
```

`MyObjectFlavor3` extends `MyObjectMain` just to keep some consistency. There is no actual requirement to do this.

### Step 3

Thats it, there is no step 3 :)

At this time library will generate alias with name `MyObject` which can be used everywhere in the code:

```kotlin
val myObject = MyObject()
myObject.doSomething()
```

Alias will be pointing to `MyObjectFlavor3` when building project with `flavor3` variant:

```kotlin
typealias MyObject = MyObjectFlavor3
```

And for all other build variants it will point to `MyObjectMain`:

```kotlin
typealias MyObject = MyObjectMain
```

Project structure should look something like this:

```
main
  -> MyObjectMain.kt
  
flavorM
  -> MyObjectFlavorM.kt

generated
  -> MyObject.kt
```

# How to add dependencies?

To use this library in your project just add following lines:

### Step 1

Add JitPack repository in your root `build.gradle` file:

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2

Add actual library and compiler dependencies:

```gradle
dependencies {
    implementation 'com.github.MatrixDev.flavorAlias:flavorAliasLib:1.0'
    
    kapt 'com.github.MatrixDev.flavorAlias:flavorAliasCompiler:1.0'
}
```

More info can be found at https://jitpack.io/#MatrixDev/flavorAlias

# License

```
MIT License

Copyright (c) 2018 Rostyslav Lesovyi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
