plugins {
    id("com.android.application")
    id("jacoco")
}

android {
    namespace = "com.se.tasklist"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.se.tasklist"
        minSdk = 26
        targetSdk = 33
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
            enableAndroidTestCoverage = true
        }
        debug {
            enableAndroidTestCoverage = true
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }

    testOptions{
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.5.3")
    implementation("androidx.navigation:navigation-ui:2.5.3")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("org.greenrobot:eventbus:3.3.1")
    implementation("androidx.room:room-runtime:2.5.0-alpha02")
    annotationProcessor("androidx.room:room-compiler:2.5.0-alpha02")

    androidTestImplementation ("com.android.support.test:runner:1.0.2")
    androidTestImplementation ("com.android.support.test.espresso:espresso-core:3.0.2")
    testImplementation ("junit:junit:4.13")
}

tasks.register("generateAndroidTestCoverageReport", Exec::class) {
    group = "Reporting"
    description = "Generate Jacoco coverage report for androidTest."

    val coverageFile = "${buildDir}/outputs/code_coverage/debugAndroidTest/connected/Pixel_3a_API_34_extension_level_7_x86_64(AVD) - 14/coverage.ec"
    val reportDir = "${buildDir}/reports/coverage"

    doFirst {
        // 确保输出目录存在
        file(reportDir).mkdirs()
    }

    commandLine(
        "java", "-jar",
        "${buildDir}/jacoco/jacococli.jar",
        "report", coverageFile,
        "--classfiles", "${buildDir}/intermediates/javac/debug/classes/",
        "--sourcefiles", "${projectDir}/src/main/java/",
        "--html", reportDir
    )
}
