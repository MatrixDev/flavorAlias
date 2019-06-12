# flavorAlias

[![Release](https://jitpack.io/v/MatrixDev/flavorAlias.svg)](https://jitpack.io/#MatrixDev/flavorAlias)

# Add to your project

To get flavorAlias into your build:

### Step 1

Add JitPack in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2

Add actual flavorAlias library and compiler:

```groovy
dependencies {
    implementation 'com.github.MatrixDev.flavorAlias:flavorAliasLib:1.0'
    kapt 'com.github.MatrixDev.flavorAlias:flavorAliasCompiler:1.0'
}
```

More info can be found at https://jitpack.io/#MatrixDev/flavorAlias

# What does it do?

For example project has following structure:

```
main
  -> MyClass.kt
  
flavor1
...
flavorN
```

And for one and only one flavor implementation of Class1 must be different:

```
main
  -> MyClass.kt
  
flavor1
...
flavorN

flavorM
  -> MyClass.kt (with different implementation)
```

Android build system will not allow this kind of replacement. For some or another reason only resources can be replaced with flavors.

This library allows replacement (with some limitations) of any class by any number flavor (even one).

# How does it work?

Lets get back to previous example.

### Step 1

Rename main/MyClass.kt -> main/MyClassMain.kt

Final name doesn't matter but it must be unique between flavors.

### Step 2

Rename flavorM/MyClass.kt -> flavorM/MyClassFlavorM.kt

Final name doesn't matter but it must be unique between flavors.

### Step 3

Add **@GenerateTypeAlias** annotation to **main/MyClassMain.kt**:

```kotlin
@GenerateTypeAlias("MyClass", priority = 1)
open class MyClassMain {
}
```

This annotation takes two arguments:
1. name - name of original class
2. priority - annotation processor will select final class based on priority (higher wins)

### Step 4

Add **@GenerateTypeAlias** annotation to **flavorM/MyClassFlavorM.kt**:

```kotlin
@GenerateTypeAlias("MyClass", priority = 2)
class MyClassFlavorM : MyClassMain() {
}
```

Only difference from previous step is in **priority** argument.

### Step 5

Thats it, there is no step 5 :)

At this time library will generate alias with name **MyClass** which will point to annotated class with highest priority.

When **flavorM** is selected alias will point to **MyClassFlavorM**:

```kotlin
typealias MyClass = MyClassFlavorM
```

And for all other flavors it will point to **MyClassMain**:

```kotlin
typealias MyClass = MyClassMain
```

Project structure should look like this:

```
main
  -> MyClassMain.kt
  
flavor1
...
flavorN

flavorM
  -> MyClassFlavorM.kt

generated
  -> MyClass.kt
```

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
