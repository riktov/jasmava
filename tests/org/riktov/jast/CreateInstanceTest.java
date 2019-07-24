package org.riktov.jast;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateInstanceTest {
	Environment env = Environment.getInstance() ;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	/// these two tests use the primitiveData model
	/*
	@Test
	void shouldCreateInstanceInteger() {
		SmalltalkClassObject stco_Integer = (SmalltalkClassObject) env.get("Integer") ;
		SmalltalkObject instance = stco_Integer.createInstance() ;
		instance.primitiveData.set("54");
		assertEquals("54", instance.toString()) ;
	}

	@Test
	void shouldCreateInstanceIntegerFromClassName() {
		SmalltalkObject instance = SmalltalkClassObject.createInstanceOfClassNamed("Integer", env) ;
		instance.primitiveData.set("54");
		assertEquals("54", instance.toString()) ;
	}
	*/

	@Test
	void shouldCreateInstanceObject() {
		SmalltalkClassObject stco_Object = (SmalltalkClassObject) env.get("Object") ;
		SmalltalkObject instance = SmalltalkClassObje ;
		assertEquals("a Object", instance.toString().substring(0, "a Object".length())) ;
//		assertEquals("A Object", instance.toString()) ;
	}
}
