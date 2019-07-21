package org.riktov.jast;

import java.util.ArrayList;

/**
 * A Selector is an identifier that uniquely identifies a method on an object
 * @author riktov@freeshell.org (Paul Richter)
 * 
 * http://www.objs.com/x3h7/smalltalk.htm
 *
 * "updateWith:on:" is a selector, or at least the text representation of it
 * 
 * */
class Selector {
	private String str ;
	//private int hash ;
	
	//constructor
	Selector(String str) {
		this.str = str ;
	//	this.hash = str.hashCode() ;
	}
	Selector(String[] strs) {	
		this(String.join(":", strs) + ":") ;			
	}
	@Override public String toString() { return this.str ; };
	//int key() { return hash ;}
	//String key() { return str ;}//alternate implementation
}

/**
 * A Keyword is a component of a selector that can create a keyword message.
 * @author riktov@freeshell.org (Paul Richter)
 *
 */
class Keyword {
	String str ;
}

/**
 * A Smalltalk message is "a selector and its argument values". (Quoted from Squeak)
 * 
 * @author riktov@freeshell.org (Paul Richter)
 *
 */
class Message /* extends SmalltalkObject */
{
	Selector selector ;	//identical to Smalltalk
	SmalltalkObject[] args ;	//identical to Smalltalk
	
	public Selector getSelector() { return selector; }
	public SmalltalkObject[] getArgs() { return args ;}

	Message(Selector sel, SmalltalkObject[] args) {
		this.selector = sel ;
		this.args = args ;
	}
	
	Message(String str, SmalltalkObject[] args) {
		this(new Selector(str), args) ;
	}
	
	@Override public String toString() { return selector.toString() ; }
}

/**
 * The <code>UnaryMessage</code> class is a <code>Message</code> which
 * has no arguments.
 * 
 * @author riktov@freeshell.org (Paul Richter)
 *
 */
final class UnaryMessage extends Message {
	UnaryMessage(Selector sel) { super(sel, new SmalltalkObject[0]) ; }
	UnaryMessage(String str) { this(new Selector(str)) ;}
}

final class BinaryMessage extends Message {	
	BinaryMessage(Selector sel, SmalltalkObject arg) {
		super(sel, new SmalltalkObject[] { arg } ) ;
	}
	BinaryMessage(String str, SmalltalkObject arg) {
		this(new Selector(str), arg) ;
	}
}

final class KeywordMessage extends Message {
	KeywordMessage(String str, SmalltalkObject[] args) {
		super(str, args) ;
	}
}

/**
 * The <code>MessageForm</code> class represents a <code>Message</code>'s 
 * selector and its named formal parameters, if present. It is what appears
 * as the first line in a method definition in the Smalltalk class browser.
 * 
 * @author riktov@freeshell.org (Paul Richter)
 * 
 * 
 */
/* Designing this will require some thought about the difference between a 
 * Smalltalk method and a Lisp procedure, since I have not yet implemented a 
 * Lisp with named parameters, only positional parameters. The named parameters
 * will be saved as part of a method, while the selectors are used as the key
 * in the method table. 
 */
class MessageForm {
	private Selector sel ;
	ArrayList<String> params = new ArrayList<String>();
	
	MessageForm(String s) {
		//normalize it to something like:
		//	  + aNumber
		//    add:wordingString help:helpString action:aSymbol
		// though in the browser it appears as add: wordingString help: helpString action: aSymbol
		// reduce all multiple whitespace to single
		String normStr = s.trim().replaceAll(" +", " ") ;
		// remove all whitespace after colons
		normStr = normStr.replaceAll(": ", ":") ;
		
		if(normStr.contains(" ")) {
			if(normStr.contains(":")) {
				String[] pairs = normStr.split(" ") ;
				String selStr = "" ;
				for(String pair : pairs) {
					String[] keyAndName = pair.split(":") ;
					selStr += keyAndName[0] + ":" ;
					params.add(keyAndName[1]) ;
				}
				sel = new Selector(selStr) ;				
			} else {
				//binary message
				String[] pair = normStr.split(" ") ;
				sel = new Selector(pair[0]) ;
				params.add(pair[1]) ;
			}
		} else {
			sel = new Selector(normStr) ;	//unary message
		}
	}

	public Selector selector() { return sel ;}
}

