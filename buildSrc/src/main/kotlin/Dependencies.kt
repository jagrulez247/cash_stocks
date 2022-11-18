import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

    private const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"

    //Google Dependencies
    private const val playServicesBase =
        "com.google.android.gms:play-services-base:${Versions.playServicesBase}"
    const val playServicesLocation =
            "com.google.android.gms:play-services-location:${Versions.playServicesLocation}"
    private const val playServicesMaps =
        "com.google.android.gms:play-services-maps:${Versions.playServicesMaps}"
    private const val mapsKtx =
        "com.google.maps.android:maps-ktx:${Versions.mapsKtx}"
    private const val material = "com.google.android.material:material:${Versions.material}"

    //Hilt dependencies
    private const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    private const val hiltKapt = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    //Android Dependencies
    const val splashScreen = "androidx.core:core-splashscreen:${Versions.splashScreen}"
    const val multiDex = "androidx.multidex:multidex:${Versions.multiDex}"
    private const val appcompat = "androidx.appcompat:appcompat:${Versions.androidAppcompat}"
    private const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    private const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtx}"
    private const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
    private const val legacySupport =
        "androidx.legacy:legacy-support-v4:${Versions.androidLegacyV4Support}"
    private const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    private const val lifecycleCommon = "androidx.lifecycle:lifecycle-common:${Versions.androidLifecycle}"
    private const val lifecycleCommonJava8 =
        "androidx.lifecycle:lifecycle-common-java8:${Versions.androidLifecycle}"
    private const val lifecycleLivedataKtx =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.androidLifecycle}"
    private const val lifecycleRuntime =
        "androidx.lifecycle:lifecycle-runtime:${Versions.androidLifecycle}"
    private const val lifecycleRuntimeKtx =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidLifecycle}"
    private const val lifecycleViewmodelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidLifecycle}"
    private const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    private const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    private const val roomKapt = "androidx.room:room-compiler:${Versions.room}"
    private const val sqliteXerial = "org.xerial:sqlite-jdbc:${Versions.sqLiteSerial}"

    //Coroutine Dependencies
    private const val kotlinxCoroutineAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinxCoroutinesAndroid}"
    private const val kotlinxCoroutineCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutinesAndroid}"
    private const val kotlinxCoroutinePlayServices =
        "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.kotlinxCoroutinesAndroid}"
    private const val kotlinxCoroutineTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinxCoroutinesAndroid}"
    private const val coroutineRetrofitAdapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitCoroutinesAdapter}"

    //Image Library Dependencies
    private const val coil = "io.coil-kt:coil:${Versions.coil}"
    private const val coilGif = "io.coil-kt:coil-gif:${Versions.coil}"

    //Network Dependencies
    private const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    private const val okhttp3LoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3LoggingInterceptor}"
    private const val retrofitGsonConverter =
        "com.squareup.retrofit2:converter-gson:${Versions.retrofitGsonConverter}"
    private const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    private const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    private const val moshiKapt = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    private const val retrofitMoshiConverter =
        "com.squareup.retrofit2:converter-moshi:${Versions.retrofitMoshiConverter}"
    private const val retrofitScalarConverter =
        "com.squareup.retrofit2:converter-scalars:${Versions.retrofitScalarConverter}"

    //Test Dependencies
    private const val junit = "junit:junit:${Versions.junit}"
    private const val mockK = "io.mockk:mockk:${Versions.mockK}"
    private const val kotlinxCoroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutinesTest}"
    private const val archCoreTest = "androidx.arch.core:core-testing:${Versions.archCoreTesting}"
    private const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"

    //AndroidTest Dependencies
    private const val androidJunit = "androidx.test.ext:junit:${Versions.androidJunit}"
    private const val extJunitKtx = "androidx.test.ext:junit-ktx:${Versions.androidJunit}"
    private const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    private const val testCoreKtx = "androidx.test:core-ktx:${Versions.testCoreKtx}"
    private const val androidMockK = "io.mockk:mockk-android:${Versions.mockK}"
    private const val roomAndroidTest = "androidx.room:room-testing:${Versions.room}"

    private val coreAppDependencies = arrayListOf<String>().apply {
        add(kotlinStdLib)
        add(coreKtx)
        add(appcompat)
        add(legacySupport)
        add(hilt)
    }

    private val coreUiDependencies = arrayListOf<String>().apply {
        add(material)
        add(constraintLayout)
        add(recyclerview)
        add(coil)
    }

    private val coroutineDependencies = arrayListOf<String>().apply {
        add(kotlinxCoroutineAndroid)
        add(kotlinxCoroutineCore)
        add(kotlinxCoroutinePlayServices)
        add(kotlinxCoroutineTest)
        add(coroutineRetrofitAdapter)
    }

    private val lifecycleDependencies = arrayListOf<String>().apply {
        add(lifecycleCommon)
        add(lifecycleCommonJava8)
        add(lifecycleLivedataKtx)
        add(lifecycleRuntime)
        add(lifecycleRuntimeKtx)
        add(lifecycleViewmodelKtx)
        add(activityKtx)
        add(fragmentKtx)
    }

    val gMapsDependencies = arrayListOf<String>().apply {
        add(playServicesBase)
        add(playServicesMaps)
        add(mapsKtx)
    }

    private val networkDependencies = arrayListOf<String>().apply {
        add(retrofit)
        add(okhttp3LoggingInterceptor)
        add(moshi)
        add(moshiKotlin)
        add(retrofitMoshiConverter)
        add(retrofitScalarConverter)
    }

    private val dbDependencies = arrayListOf<String>().apply {
        add(roomKtx)
        add(roomRuntime)
    }

    private val dbKaptDependencies = arrayListOf<String>().apply {
        add(roomKapt)
        add(sqliteXerial)
    }

    private val unitTestDependencies = arrayListOf<String>().apply {
        add(junit)
        add(mockK)
        add(kotlinxCoroutinesTest)
        add(archCoreTest)
        add(robolectric)
    }

    private val androidTestDependencies = arrayListOf<String>().apply {
        add(androidJunit)
        add(extJunitKtx)
        add(espressoCore)
        add(testCoreKtx)
        add(androidMockK)
        add(roomAndroidTest)
    }

    //util functions for adding the different type dependencies from build.gradle file
    fun DependencyHandler.implementation(list: List<String>) {
        list.forEach { dependency ->
            add("implementation", dependency)
        }
    }

    fun DependencyHandler.kaptLocal(list: List<String>) {
        list.forEach { dependency ->
            add("kapt", dependency)
        }
    }

    fun DependencyHandler.annotationProcessor(list: List<String>) {
        list.forEach { dependency ->
            add("annotationProcessor", dependency)
        }
    }

    fun DependencyHandler.addCoreModuleDependencies() {
        implementation(coreAppDependencies)
        implementation(coroutineDependencies)
        kaptLocal(mutableListOf(hiltKapt))
    }

    fun DependencyHandler.addCoreUiDependencies() {
        implementation(coreUiDependencies)
        implementation(lifecycleDependencies)
    }

    fun DependencyHandler.addDomainDependencies() {
        implementation(networkDependencies)
        implementation(dbDependencies)
        kaptLocal(dbKaptDependencies)
        kaptLocal(mutableListOf(hiltKapt))
    }

    fun DependencyHandler.addTestDependencies() {
        unitTestDependencies.forEach { add("testImplementation", it) }
        androidTestDependencies.forEach { add("androidTestImplementation", it) }
    }
}