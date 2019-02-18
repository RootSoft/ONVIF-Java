pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace!!.startsWith("org.jetbrains.kotlin")) {
                useVersion(requested.version)
            }
        }
    }
}

rootProject.name = "ONVIF-Java"