plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
	id("org.graalvm.buildtools.native") version "0.10.2"
}

group = "com.faisaldev"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2023.0.3"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.postgresql:r2dbc-postgresql:1.0.5.RELEASE")
	implementation("org.liquibase:liquibase-core:4.29.2")
	implementation("com.google.code.gson:gson:2.11.0")
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc:3.3.3")
	implementation("org.springframework.boot:spring-boot-starter-jdbc:3.3.3")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.3.3")
	implementation("io.r2dbc:r2dbc-pool:1.0.1.RELEASE")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	//Kafka depedencies
	implementation("org.springframework.kafka:spring-kafka:3.2.3")
	implementation("org.springframework.cloud:spring-cloud-stream:4.1.3")
	implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka:4.1.3")

	//JWT
	implementation("io.jsonwebtoken:jjwt-api:0.12.6")
	implementation("io.jsonwebtoken:jjwt-impl:0.12.6") // Replace with the latest version
	implementation("io.jsonwebtoken:jjwt-jackson:0.12.6")

	//SECURITY
	implementation("org.springframework.boot:spring-boot-starter-security")
	testImplementation("org.springframework.security:spring-security-test")


	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
