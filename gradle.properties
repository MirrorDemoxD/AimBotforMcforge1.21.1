plugins {
    id 'net.minecraftforge.gradle' version '6.0.19'
}

version = '1.0.0'
group = 'com.example.aimbot'
archivesBaseName = 'aimbotmod'

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

minecraft {
    mappings channel: 'official', version: '1.21.1'
    runs {
        client {
            workingDirectory project.file('run')
            property 'forge.logging.markers', 'REGISTRIES'
            property 'forge.logging.console.level', 'debug'
            mods {
                aimbotmod {
                    source sourceSets.main
                }
            }
        }
    }
}

dependencies {
    minecraft 'net.minecraftforge:forge:1.21.1-41.0.0'
}

jar {
    manifest {
        attributes([
            "Specification-Title": "aimbotmod",
            "Specification-Version": "1",
            "Implementation-Title": project.name,
            "Implementation-Version": project.version
        ])
    }
}
