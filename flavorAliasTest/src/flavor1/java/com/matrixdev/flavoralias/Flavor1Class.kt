package com.matrixdev.flavoralias

/**
 * @author Rostyslav.Lesovyi
 */
@GenerateTypeAlias("MyClass", priority = 2)
class Flavor1Class : MainClass() {
	override val flavor: String = "flavor1"
}
