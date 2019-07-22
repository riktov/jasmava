package org.riktov.jast;

/**
 * A Smalltalk method
 * @author riktov@freeshell.org (Paul Richter)
 *
 */


abstract class Method {
	public SmalltalkObject apply(SmalltalkObject receiver, SmalltalkObject[] argVals) { 
		return StringObject.make("[unimplemented] Method.apply()") ; 
	}
	
}

/**
 * The Smalltalk class "BlockClosure"
 * @author riktov@freeshell.org (Paul Richter)
 *
 */
class Block {
	String text = "" ;
	
	Block() {
		
	}
	Block(String text) { this.text = text ; }
	
}