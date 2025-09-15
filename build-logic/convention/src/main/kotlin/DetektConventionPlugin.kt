import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType

class DetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("io.gitlab.arturbosch.detekt")

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("detektPlugins", libs.findLibrary("detekt.formatting").get())
                add("detektPlugins", libs.findLibrary("detekt.compose.rules").get())
            }

            extensions.configure<DetektExtension> {
                buildUponDefaultConfig = true
                config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
                baseline = file("$rootDir/config/detekt/baseline.xml")
                autoCorrect = true
                parallel = true
            }

            tasks.withType<Detekt>().configureEach {
                reports {
                    html.required.set(true)
                    xml.required.set(true)
                    txt.required.set(true)
                    sarif.required.set(true)
                    md.required.set(true)
                }
            }

            tasks.withType<DetektCreateBaselineTask>().configureEach {
                description = "Create detekt baseline for the project"
                group = "verification"
            }
        }
    }
}