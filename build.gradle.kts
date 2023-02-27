import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

buildscript {
	val springBootVersion = "2.7.3"
	repositories {
		mavenCentral()
		maven {
			url = uri("https://plugins.gradle.org/m2/")
		}
	}
	dependencies {
		classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
		classpath("gradle.plugin.com.palantir.gradle.docker:gradle-docker:0.22.1")
	}
}

plugins {
	id("org.springframework.boot") version "2.7.3"
	id("io.spring.dependency-management") version "1.0.13.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	id("com.palantir.docker") version "0.22.1"
}

group = "dougiesvitor"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

val bootJar = tasks.getByName<BootJar>("bootJar") {
	baseName = "aws-microservice-consumer"
	version = "1.2.0"
}

val unpack = tasks.create(name = "unpack", type = Copy::class) {
	dependsOn(bootJar)
	from(zipTree(tasks.bootJar.get().outputs.files.singleFile))
	into("build/dependency")
}

docker {
	name = "${project.group}/${bootJar.baseName}"
	tags("${bootJar.version}")
	copySpec.from(unpack.outputs).into("dependency")
	buildArgs(mapOf("DEPENDENCY" to "dependency"))
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("com.amazonaws:aws-java-sdk-sqs:1.12.376")
	implementation("org.springframework:spring-jms:5.2.9.RELEASE")
	implementation("com.amazonaws:amazon-sqs-java-messaging-lib:1.0.8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
