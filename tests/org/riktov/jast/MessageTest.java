package org.riktov.jast;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//https://stackoverflow.com/questions/155436/unit-test-naming-best-practices

class MessageTest {
	SmalltalkObject fourteen = PrimitiveObject.make(14) ; 
	SmalltalkObject hello = PrimitiveObject.make("Hello") ; 
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void shouldRepresentUnaryMessage() {
		UnaryMessage msgUnary = new UnaryMessage("inspect") ;
		assertEquals("inspect", msgUnary.toString()) ;
	}

	@Test
	void shouldRepresentBinaryMessage() {
		BinaryMessage msgBinary = new BinaryMessage("+", fourteen) ;
		assertEquals("+", msgBinary.toString()) ;
	}

	@Test
	void shouldRepresentKeywordMessage() {
		SmalltalkObject[] args = {fourteen, hello} ;
		KeywordMessage msgKeyword = new KeywordMessage("fumbleWith:and:", args) ;
		System.out.println(msgKeyword.toString()) ;
		assertEquals("fumbleWith:and:", msgKeyword.toString()) ;
	}
	
	@Test
	void shouldTokenizeUnaryMessageForm() {
		MessageForm mf = new MessageForm("inspect") ;
		assertEquals("inspect", mf.selector().toString()) ;
	}
	@Test
	void shouldTokenizeBinaryMessageForm() {
		MessageForm mf = new MessageForm("+ 23") ;
		assertEquals("+", mf.selector().toString()) ;
	}
	@Test
	void shouldTokenizeKeywordMessageForm() {
		MessageForm mf = new MessageForm("fumbleWith:buttons and:knobs") ;
		assertEquals("fumbleWith:and:", mf.selector().toString()) ;
	}

	@Test
	void shouldTokenizeKeywordMessageFormWithSpaces() {
		MessageForm mf = new MessageForm("fumbleWith: buttons and:  knobs") ;
		assertEquals("fumbleWith:and:", mf.selector().toString()) ;
	}

}
	
