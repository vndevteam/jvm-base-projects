import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.jpa") version "1.9.20"
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
    implementation("org.springframework.boot:spring-boot-starter-actuator:3.1.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.4")
    implementation("org.springframework.boot:spring-boot-starter-security:3.0.4")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:3.0.4")
    implementation("org.springframework.boot:spring-boot-starter-web:3.1.0")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.0.4")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6:3.1.1.RELEASE")
    developmentOnly("org.springframework.boot:spring-boot-devtools:3.0.4")
    runtimeOnly("org.postgresql:postgresql:42.5.4")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")
    testImplementation("org.springframework.security:spring-security-test:6.0.2")
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
