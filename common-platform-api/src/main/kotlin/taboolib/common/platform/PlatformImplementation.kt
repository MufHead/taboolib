package taboolib.common.platform

import taboolib.common.inject.ClassVisitorHandler
import taboolib.common.io.getInstance
import taboolib.common.reflect.getAnnotationIfPresent

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class PlatformImplementation(val platform: Platform)

/**
 * 搜索该类的当前平台实现
 */
@Suppress("UNCHECKED_CAST")
fun <T> Class<T>.findImplementation(): T? {
    return ClassVisitorHandler.getClasses().firstOrNull { isAssignableFrom(it) && it != this }?.getInstance(true)?.get() as? T
}

/**
 * 判断平台实现
 */
fun checkPlatform(cls: Class<*>): Boolean {
    val platformSide = cls.getAnnotationIfPresent(PlatformSide::class.java)
    return platformSide == null || platformSide.value.any { i -> i == Platform.CURRENT }
}