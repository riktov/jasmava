package org.riktov.jast;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrimitiveObjectTest {

	private SmalltalkObject twentysix ;
	private SmalltalkObject fooSym ;
	@BeforeEach
	void setUp() throws Exception {
		this.twentysix = PrimitiveObject.make(26) ;
		this.fooSym = PrimitiveObject.make("#Foo") ;
	}

	/*
	 * This test is not needed, as we do not need to expose the underlying value 
	 * of PrimitiveObjects to Java
	@Test
	void integerObjectValueTest() {
		IntegerObject stoInteger = (IntegerObject) PrimitiveObject.make(26) ;
		assertTrue(stoInteger.primitiveValue() == 26) ;
		assertFalse(stoInteger.primitiveValue() == 153) ;
	}
	*/

	@Test
	void shouldRepresentInteger() {
		assertEquals("26", this.twentysix.toString()) ;
	}

	@Test
	void shouldBeIntegerClass() {
		assertEquals(IntegerClassObject.getInstance(), this.twentysix.stGetClass()) ;
	}

	@Test
	void shouldRepresentIntegerClass() {
		assertEquals("Integer", this.twentysix.stGetClass().toString()) ;
	}
	@Test
	void shouldRepresentSymbol() {
		assertEquals("#Foo", this.fooSym.toString()) ;
	}

	@Test
	void shouldBeSymbolClass() {
		assertEquals(SymbolClassObject.getInstance(), this.fooSym.stGetClass()) ;
	}

	@Test
	void shouldRepresentSymbolClass() {
		assertEquals("Symbol", this.fooSym.stGetClass().toString()) ;
	}
}
