package org.riktov.jast;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SmalltalkReaderStringTest {
	private SmalltalkReader reader ;
	private SmalltalkObject result ;

	@BeforeEach
	void setUp() throws Exception {
        reader = new SmalltalkReader("Foo") ;
	}

    @Test public void shouldReadString() {
        SmalltalkObject foobar = (SmalltalkObject)reader.read("'Foobar'") ;
       	assertEquals(PrimitiveObject.make("banana").stGetClass(), foobar.stGetClass()) ;
    }

}
