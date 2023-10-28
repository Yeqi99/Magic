
plugins {
    `java-library`
    `maven-publish`
}

//repositories {
//    mavenLocal()
//    mavenCentral()
//    maven {
////        url = uri("https://repo.fastmcmirror.org/content/repositories/releases/")
//    }
//
//}
//
//dependencies {
////    implementation("dev.rgbmc:FastExpression:1.0.0-a0aa2c1")
//}

group = "cn.originmc.magic"
version = "1.1.1-SNAPSHOT"
description = "Magic"
java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}
