# 在主项目中集成SubModuleDemo的完整示例

## 主项目的settings.gradle.kts配置示例

```kotlin
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MainProject"
include(":app")
include(":SubModuleDemo:submodule")  // 添加这行
```

## 主项目的app/build.gradle.kts配置示例

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.mainproject"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mainproject"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // 添加SubModuleDemo依赖
    implementation(project(":SubModuleDemo:submodule"))
    
    // 其他依赖
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
```

## 在MainActivity中使用SubModuleDemo的完整示例

```kotlin
package com.example.mainproject

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.common.SubModuleDemoAPI

class MainActivity : AppCompatActivity() {
    
    companion object {
        private const val TAG = "MainActivity"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // 初始化SubModuleDemo
        SubModuleDemoAPI.initialize()
        Log.d(TAG, "SubModuleDemo initialized")
        
        // 使用SubModuleDemo的API
        demonstrateSubModuleUsage()
    }
    
    private fun demonstrateSubModuleUsage() {
        // 获取问候语
        val greeting = SubModuleDemoAPI.getGreeting()
        Log.d(TAG, "Greeting from submodule: $greeting")
        
        // 获取版本信息
        val version = SubModuleDemoAPI.getVersion()
        Log.d(TAG, "SubModule version: $version")
        
        // 使用计算功能
        val num1 = 10
        val num2 = 25
        val result = SubModuleDemoAPI.add(num1, num2)
        Log.d(TAG, "$num1 + $num2 = $result")
        
        // 在UI中显示结果
        // 这里可以更新UI组件显示这些信息
    }
}
```

## 集成步骤总结

1. **添加Submodule到主项目**：
   ```bash
   git submodule add <SubModuleDemo的git地址> SubModuleDemo
   git submodule update --init --recursive
   ```

2. **修改主项目的settings.gradle.kts**：
   添加 `include(":SubModuleDemo:submodule")`

3. **修改主项目app模块的build.gradle.kts**：
   添加 `implementation(project(":SubModuleDemo:submodule"))`

4. **在代码中使用**：
   导入 `com.common.SubModuleDemoAPI` 并调用相应方法

5. **构建和运行**：
   ```bash
   ./gradlew build
   ./gradlew installDebug  # 或直接在Android Studio中运行
   ```

## 注意事项

- 确保主项目和submodule的compileSdk、minSdk等版本兼容
- 如果有依赖冲突，可能需要在主项目中使用exclude排除某些依赖
- 定期更新submodule：`git submodule update --remote`
