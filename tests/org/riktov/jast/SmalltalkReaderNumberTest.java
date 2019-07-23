package org.riktov.jast;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SmalltalkReaderTest {
	private SmalltalkReader reader ;
	private SmalltalkObject result ;
	
	@BeforeEach
	void setUp() throws Exception {
        reader = new SmalltalkReader("Foo") ;
    }
	
	
    @Test public void shouldReadInteger() {
        SmalltalkObject thirteen = (SmalltalkObject)reader.read("13") ;
       	assertEquals(IntegerObject.make(5).stGetClass(), thirteen.stGetClass()) ;
    }
    

    @Test public void shouldReadIntegerAsCorrectValue() {
        SmalltalkObject fiftyfour = (SmalltalkObject)reader.read("54 24") ;
        assertEquals("54", fiftyfour.toString());
    }

    @Test public void shouldReadFloat() {
        SmalltalkObject threePointOneFour = (SmalltalkObject)reader.read("3.14 barzle") ;
        assertEquals("3.14", threePointOneFour.toString()) ;
    }
    
    @Test public void shouldReadPointAsPeriod() {
    	SmalltalkObject fiveDot = (SmalltalkObject)reader.read("5.") ;
    	assertEquals((PrimitiveObject.make(5)).stGetClass(), fiveDot.stGetClass()) ;
    }

    @Test public void shouldReadSymbols() {
        SmalltalkObject foobar = (SmalltalkObject)reader.read("foobar bazint") ;
        assertEquals("foobar", foobar.toString()) ;
    }
    
    @Test public void shouldReadParenExpr() {
        SmalltalkObject sixPlusSeven = (SmalltalkObject)reader.read("(6 + 2)") ;
        assertEquals(3, sixPlusSeven) ;
//        assertEquals("5", fivePlusSeven[0].toString());

        
/*
        System.out.println("testReadAtoms() : read 54 as " + fiftyfour.toString()) ;
        assertTrue(fiftyfour.toString().equals("54")) ;
        

        LispObject foosym = lr.read("foosym") ;
        System.out.println("testReadAtoms() : read foosym as " + foosym.toString()) ;
        assertTrue(foosym.toString().equals("FOOSYM")) ;
        
        LispObject plus = lr.read("+") ;
        System.out.println("testReadAtoms() : read + as " + plus.toString()) ;
        assertTrue(plus.toString().equals("+")) ;
        
        LispObject minus = lr.read("-") ;
        System.out.println("testReadAtoms() : read + as " + minus.toString()) ;
        assertTrue(minus.toString().equals("-")) ;
        
        LispObject hello = lr.read("\"Hello\"") ;
        System.out.println("testReadAtoms() : read \"Hello\" as " + hello.toString()) ;
        assertTrue(hello.toString().equals("\"Hello\"")) ;
        */
        
        /*
  
        LispObject nilly = r.read("nil") ;
        //System.out.println("Is nil primitive? " + nilly.isPrimitive()) ;
        assertTrue(nilly.isNull()) ;
        */
    }
    
    @Test
    public void shouldThrowExceptionOnEmptyParens() {
    	Assertions.assertThrows(SmalltalkReaderException.class, () -> {
    		SmalltalkObject emptyParens = (SmalltalkObject)reader.read("()") ; 
    	  });
    }
    
    @Test public void shouldParseCascade() {
        SmalltalkObject sevenPlusFiveCommaPlusSix = (SmalltalkObject)reader.read("7 + 5 ; 6") ;
        assertEquals("12", sevenPlusFiveCommaPlusSix.toString());
    }
}

