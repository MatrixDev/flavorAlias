package com.matrixdev.flavoralias

/**
 * @author matrixdev
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class GenerateTypeAlias(val name: String, val priority: Int = 0)
