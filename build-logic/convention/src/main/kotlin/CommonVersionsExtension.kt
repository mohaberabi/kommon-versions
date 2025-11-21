import model.AndroidConfig
import model.IosConfig
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

abstract class CommonVersionsExtension @Inject constructor(
    objects: ObjectFactory
) {
    val ios: Property<IosConfig?> = objects.property(IosConfig::class.java)
    val android: Property<AndroidConfig?> = objects.property(AndroidConfig::class.java)
}