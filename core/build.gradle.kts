import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jmailen.gradle.kotlinter.tasks.FormatTask
import org.jmailen.gradle.kotlinter.tasks.LintTask

plugins {
    id("com.google.devtools.ksp") version libs.versions.ksp.get()
    id("io.ktor.plugin") version libs.versions.ktor.get()
    id("org.graalvm.buildtools.native") version libs.versions.graalvm.get()
    id("org.jmailen.kotlinter") version libs.versions.kotlinter.get()
    id("org.openapi.generator") version libs.versions.openapiGenerator.get()
    idea
    kotlin("jvm") version libs.versions.kotlin.get()
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
}

group = "de.wtl"
version = "0.0.1"

application {
    mainClass.set("de.wtl.core.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain {
        vendor = JvmVendorSpec.GRAAL_VM
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.withType<ShadowJar>().configureEach {
    mergeServiceFiles()
}

dependencies {
    // logging
    runtimeOnly("ch.qos.logback:logback-classic:${libs.versions.logback.get()}")
    implementation("io.github.microutils:kotlin-logging-jvm:${libs.versions.klogging.get()}")

    // validation
    implementation("io.konform:konform-jvm:${libs.versions.konform.get()}")

    // ktor
    implementation("io.ktor:ktor-client-apache-jvm")
    implementation("io.ktor:ktor-client-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-serialization-kotlinx-protobuf")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auto-head-response-jvm")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-compression-jvm")
    implementation("io.ktor:ktor-server-conditional-headers-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-host-common-jvm")
    implementation("io.ktor:ktor-server-metrics-micrometer-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-partial-content-jvm")
    implementation("io.ktor:ktor-server-request-validation")
    implementation("io.ktor:ktor-server-status-pages")
    implementation("io.ktor:ktor-server-websockets")

    // metrics
    implementation("io.micrometer:micrometer-registry-prometheus:${libs.versions.prometeus.get()}")

    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${libs.versions.coroutines.get()}")

    // mongodb
    implementation("org.mongodb:bson-kotlinx:${libs.versions.mongodb.get()}")
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:${libs.versions.mongodb.get()}")

    // koin
    implementation("io.insert-koin:koin-annotations:${libs.versions.koinKsp.get()}")
    implementation("io.insert-koin:koin-ktor:${libs.versions.koin.get()}")
    implementation("io.insert-koin:koin-logger-slf4j:${libs.versions.koin.get()}")
    ksp("io.insert-koin:koin-ksp-compiler:${libs.versions.koinKsp.get()}")

    testImplementation("io.hosuaby:inject-resources-junit-jupiter:${libs.versions.injectTestResources.get()}")
    testImplementation("io.kotest:kotest-assertions-core-jvm:${libs.versions.kotest.get()}")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("io.mockk:mockk:${libs.versions.mockk.get()}")
    testImplementation(kotlin("test"))

    // testcontainers
    testImplementation(platform("org.testcontainers:testcontainers-bom:${libs.versions.testcontainers.get()}"))
    testImplementation("org.testcontainers:mongodb")
    testImplementation("org.testcontainers:junit-jupiter")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>().configureEach {
    dependsOn("openApiGenerate")

    kotlinOptions {
        freeCompilerArgs += listOf("-Xjsr305=strict", "-Xcontext-receivers")
    }
}

graalvmNative {
    agent {
        defaultMode.set("standard")
    }
    toolchainDetection.set(false)
    binaries {
        all {
            resources {
                autodetect()
                autodetection {
                    ignoreExistingResourcesConfigFile = true
                }
            }
            buildArgs("--strict-image-heap", "--install-exit-handlers")
        }

        named("main") {
            // enable mostly static compile on non-MacOS targets
            if (!DefaultNativePlatform.getCurrentOperatingSystem().isMacOsX) {
                buildArgs.addAll("-H:+UnlockExperimentalVMOptions", "-H:+StaticExecutableWithDynamicLibC")
            }
            if (project.hasProperty("ci")) {
                // limit resource usage in ci builds
                buildArgs.addAll("--gc=G1", "-march=native")
            } else {
                // on local builds enable debug info and disable optimizations
                quickBuild = true
                buildArgs.addAll("-g")
            }
        }
    }
    metadataRepository {
        enabled.set(true)
    }
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

tasks.withType<LintTask>().configureEach {
    exclude { fileTreeElement -> "generated/" in fileTreeElement.file.path }
}

tasks.withType<FormatTask>().configureEach {
    exclude { fileTreeElement -> "generated/" in fileTreeElement.file.path }
}

openApiGenerate {
    generatorName = "kotlin"
    inputSpec = "${projectDir.parent}/docs/api/openapi.yaml"
    outputDir = layout.buildDirectory.dir("generated").get().asFile.absolutePath
    skipValidateSpec = true
    globalProperties = mapOf("models" to "")
    additionalProperties = mapOf("serializationLibrary" to "kotlinx_serialization", "enumPropertyNaming" to "UPPERCASE")
    modelPackage = "$group.api.model"
    generateAliasAsModel = false
    generateApiDocumentation = false
    generateApiTests = false
    generateModelDocumentation = false
    generateModelTests = false
    cleanupOutput = true
}

tasks.named("openApiGenerate") {
    // Workaround for any json in generated code as it is not natively supported by openapi-generator for kotlin serialization
    // see https://github.com/OpenAPITools/openapi-generator/pull/7353
    doLast {
        for (file in fileTree(layout.buildDirectory.dir("generated").get().asFile.absolutePath)) {
            if (file.extension == "kt") {
                var content = file.readText()
                content = content.replace("kotlin.collections.Map<kotlin.String, kotlin.Any>", "kotlinx.serialization.json.JsonObject")
                file.writeText(content)
            }
        }
    }
}

sourceSets {
    val main by getting {
        kotlin {
            // Include the generated sources directory in the Kotlin source set
            srcDir(layout.buildDirectory.dir("generated/src/main/kotlin").get().asFile.absolutePath)
            srcDir(layout.buildDirectory.dir("generated/ksp/main/kotlin").get().asFile.absolutePath)
        }
    }
}
