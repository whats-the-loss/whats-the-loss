import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.tasks.KtLintCheckTask

plugins {
    id("io.ktor.plugin") version libs.versions.ktor.get()
    id("org.graalvm.buildtools.native") version libs.versions.graalvm.get()
    id("org.jetbrains.kotlin.plugin.serialization") version libs.versions.kotlin.get()
    id("org.jlleitschuh.gradle.ktlint") version libs.versions.ktlint.get()
    id("org.openapi.generator") version libs.versions.openapiGenerator.get()
    idea
    kotlin("jvm") version libs.versions.kotlin.get()
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

dependencies {
    implementation("ch.qos.logback:logback-classic:${libs.versions.logback.get()}")
    implementation("io.ktor:ktor-client-apache-jvm")
    implementation("io.ktor:ktor-client-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-serialization-kotlinx-protobuf")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auto-head-response-jvm")
    implementation("io.ktor:ktor-server-caching-headers-jvm")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-compression-jvm")
    implementation("io.ktor:ktor-server-conditional-headers-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-forwarded-header-jvm")
    implementation("io.ktor:ktor-server-host-common-jvm")
    implementation("io.ktor:ktor-server-metrics-micrometer-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-partial-content-jvm")
    implementation("io.micrometer:micrometer-registry-prometheus:${libs.versions.prometeus.get()}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${libs.versions.coroutines.get()}")
    implementation("org.mongodb:bson-kotlinx:${libs.versions.mongodb.get()}")
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:${libs.versions.mongodb.get()}")
    testImplementation("io.hosuaby:inject-resources-junit-jupiter:${libs.versions.injectTestResources.get()}")
    testImplementation("io.kotest:kotest-assertions-core-jvm:${libs.versions.kotest.get()}")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("io.mockk:mockk:${libs.versions.mockk.get()}")
    testImplementation(kotlin("test"))
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
            buildArgs("--initialize-at-build-time=org.slf4j.LoggerFactory,ch.qos.logback")
        }

        named("main") {
            // enable mostly static compile on non-MacOS targets
            if (!DefaultNativePlatform.getCurrentOperatingSystem().isMacOsX) {
                buildArgs.addAll("-H:+UnlockExperimentalVMOptions", "-H:+StaticExecutableWithDynamicLibC")
            }
            if (project.hasProperty("ci")) {
                // limit resource usage in ci builds
                buildArgs.addAll("-O3", "-march=native")
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

tasks.withType<KtLintCheckTask>().configureEach {
    mustRunAfter("openApiGenerate")
    source.removeAll { "generated" in it.path }
}

openApiGenerate {
    generatorName = "kotlin"
    inputSpec = "${projectDir.parent}/doc/api/openapi.yaml"
    outputDir = layout.buildDirectory.dir("generated").get().asFile.absolutePath
    skipValidateSpec = true
    globalProperties = mapOf("models" to "")
    additionalProperties = mapOf("serializationLibrary" to "kotlinx_serialization")
    modelPackage = "$group.api.model"
    generateAliasAsModel = false
    generateApiDocumentation = false
    generateApiTests = false
    generateModelDocumentation = false
    generateModelTests = false
    cleanupOutput = true
}

sourceSets {
    val main by getting {
        kotlin {
            // Include the generated sources directory in the Kotlin source set
            srcDir(layout.buildDirectory.dir("generated/src/main/kotlin").get().asFile.absolutePath)
        }
    }
}
