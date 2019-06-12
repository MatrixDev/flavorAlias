package com.matrixdev.flavoralias

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.StandardLocation

/**
 * @author matrixdev
 */
class GenerateTypeAliasProcessor : AbstractProcessor() {

	data class ClassInfo(val element: TypeElement, val annotation: GenerateTypeAlias, val packageName: String)

	override fun getSupportedSourceVersion() = SourceVersion.latestSupported()!!
	override fun getSupportedAnnotationTypes() = setOf(GenerateTypeAlias::class.java.name)

	override fun process(annotations: MutableSet<out TypeElement>, roundEnvironment: RoundEnvironment): Boolean {
		val classes = roundEnvironment.getElementsAnnotatedWith(GenerateTypeAlias::class.java)
				.filterIsInstance<TypeElement>()

		val map = HashMap<String, ClassInfo>()
		for (cls in classes) {
			val annotation = cls.getAnnotation(GenerateTypeAlias::class.java)
			val packageName = processingEnv.elementUtils.getPackageOf(cls).toString()
			val className = "$packageName.${annotation.name}"

			val info = map[className]
			if (info != null) {
				if (info.annotation.priority == annotation.priority) {
					throw Exception("identical priorities for ${info.element.asType().asTypeName()} amd ${cls.asType().asTypeName()}")
				}
				if (info.annotation.priority > annotation.priority) {
					continue
				}
			}

			map[className] = ClassInfo(cls, annotation, packageName)
		}

		for (info in map.values) {
			val className = info.element.simpleName.toString()
			val fileSpec = FileSpec.builder(info.packageName, className)
					.addTypeAlias(TypeAliasSpec.builder(info.annotation.name, info.element.asClassName()).build())
					.build()

			processingEnv.filer.createResource(StandardLocation.SOURCE_OUTPUT, info.packageName, "$className.kt")
					.openWriter().use {
						fileSpec.writeTo(it)
					}
		}

		return true
	}

}
