# flavorAlias

[![Release](https://jitpack.io/v/MatrixDev/flavorAlias.svg)](https://jitpack.io/#MatrixDev/flavorAlias)

# Add to your project

To get flavorAlias into your build:

Step 1. Add JitPack in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add actual flavorAlias library and compiler:

```groovy
dependencies {
    implementation 'com.github.MatrixDev.flavorAlias:flavorAliasLib:1.0'
    kapt 'com.github.MatrixDev.flavorAlias:flavorAliasCompiler:1.0'
}
```

More info can be found at https://jitpack.io/#MatrixDev/flavorAlias
