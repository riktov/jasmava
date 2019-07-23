/**
 * This module contains tests that use the provisional
 * Java class library than the actual Smalltalk class library
 */
package org.riktov.jast ;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JavaMethodTest {
	@BeforeEach
	void setUp() throws Exception {
	}

	/*
	 * @Test void test() { fail("Not yet implemented"); }
	 */

	@Test
	void shouldIncrementInteger() {
		SmalltalkObject five = PrimitiveObject.make(5) ;
		SmalltalkObject result = five.receiveMessage(new UnaryMessage("increment")) ;
		System.out.println("The result of applying increment to 5 is " + result.toString()) ;
		assertEquals("6", result.toString()) ;
	}

	@Test
	void shouldAddIntegers() {
		SmalltalkObject five = PrimitiveObject.make(5) ;
		SmalltalkObject seven = PrimitiveObject.make(7) ;
		SmalltalkObject result = five.receiveMessage(new BinaryMessage("+", seven)) ;
		System.out.println("The result of adding 5 and 7 is " + result.toString()) ;
		assertEquals("12", result.toString()) ;
	}
	
	@Test
	void shouldReturnClassOfInstance() {
		SmalltalkObject five = PrimitiveObject.make(5) ;
		SmalltalkObject result = five.receiveMessage(new UnaryMessage("class")) ;
		assertEquals("Integer", result.toString()) ;		
	}
}


