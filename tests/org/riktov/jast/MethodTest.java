package org.riktov.jast;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MethodTest {
	Environment env = Environment.getInstance() ;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void shouldGetGlobalClassByName() {
		SmalltalkObject objClass = env.get("Object") ;
		assertEquals("Object class", objClass.toString()) ;
	}

	@Test
	void shouldNotGetValidGlobalClassByName() {
		SmalltalkObject objClass = env.get("XXXObject") ;
		assertNull(objClass) ;
	}
	
	@Test
	void shouldUnderstandMessageClass() {
		SmalltalkObject objClass = env.get("Object") ;
		objClass.receiveMessage(new UnaryMessage("class")) ;
		
	}
	
	@Test
	void shouldCreateNewSubclass() {
		SmalltalkObject objClass = env.get("Object") ;
		SmalltalkObject newClassName = PrimitiveObject.make("#Umbrella") ;
		SmalltalkObject result = objClass.receiveMessage(new BinaryMessage("subclass:", newClassName)) ;	//should be a keyword message
		System.out.println(result.toString()) ;
		SmalltalkObject objUmbrellaClass = env.get("Umbrella") ;
		assertNotNull(objUmbrellaClass) ;
	}
}
