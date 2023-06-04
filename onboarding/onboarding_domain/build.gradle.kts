apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    // Be able to access API in core Module
    "implementation"(project(Modules.core))
}
