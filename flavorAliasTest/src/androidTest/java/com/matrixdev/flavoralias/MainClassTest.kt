package com.matrixdev.flavoralias

import org.junit.Test

import org.junit.Assert.*

/**
 * @author Rostyslav.Lesovyi
 */
class MainClassTest {
	@Test
	fun getFlavor() {
		val value = MyClass()
		when (BuildConfig.FLAVOR) {
			"flavor1" -> assertEquals(value.flavor, "flavor1")
			"flavor2" -> assertEquals(value.flavor, "flavor2")
			"flavor3" -> assertEquals(value.flavor, "main")
		}
	}
}
