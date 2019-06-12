package com.matrixdev.flavoralias

/**
 * @author Rostyslav.Lesovyi
 */
@GenerateTypeAlias("MyClass", priority = 2)
class Flavor2Class : MainClass() {
	override val flavor: String = "flavor2"
}
