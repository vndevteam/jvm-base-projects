import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
    id("com.diffplug.spotless") version "6.25.0"
    id("com.google.cloud.tools.jib") version "3.4.0"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.21"
    kotlin("plugin.jpa") version "1.9.23"
}

group = "com.vndevteam"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator:3.2.3")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.3")
    implementation("org.springframework.boot:spring-boot-starter-security:3.2.3")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:3.2.3")
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.3")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.2.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6:3.1.2.RELEASE")
    developmentOnly("org.springframework.boot:spring-boot-devtools:3.2.3")
    runtimeOnly("org.postgresql:postgresql:42.7.3")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.3")
    testImplementation("org.springframework.security:spring-security-test:6.2.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
}

ext {
    // Fix vulnerabilities (CVE-2022-41854, CVE-2022-1471) from org.springframework.boot:spring-boot-starter-actuator:3.1.5
    set("snakeyaml.version", "2.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

spotless {
    kotlin {
        ktfmt("0.46").kotlinlangStyle()
    }
}

