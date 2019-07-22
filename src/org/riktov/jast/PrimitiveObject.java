package org.riktov.jast;

/**
 * This module defines classes that take their value from Java primitives
 * 
 * @author Paul Richter
 * 
 * We are trying two different models for primitive classes
 * 
 * 1 - Java derived classes. The Java abstract subclass PrimitiveObject and its
 * subclasses which have Java primitives as members.
 * 
 * 2 - A PrimitiveData member in SmalltalkObject. If present, it holds a Java
 * primitive member and provides the interface to its use.
 *
 */


abstract class PrimitiveClassObject extends SmalltalkClassObject {
	
}

/**
 * 
 * @author riktov@freeshell.org (Paul Richter)
 *
 */
class PrimitiveObject extends SmalltalkObject {
    //factory methods for each of the PrimitiveObjects
	static PrimitiveObject make(int i) {
		return new IntegerObject(i) ;
	}

	static PrimitiveObject make(String s) {
		if(s.charAt(0) == '#') {
			return new SymbolObject(s.substring(1)) ;
		} else {
			return new StringObject(s) ;	
		}
	}
}

class NumberClassObject extends PrimitiveClassObject {
}

class IntegerClassObject extends NumberClassObject {
	//This is a singleton
	private static IntegerClassObject INSTANCE ;
	public static IntegerClassObject getInstance() {
		if(IntegerClassObject.INSTANCE == null) {
			INSTANCE = new IntegerClassObject() ;
		}
		return INSTANCE ;
	}
	@Override public String toString() { return "Integer" ;}

	@Override
	public void initializeMethods() {
		//defineInstanceMethod(new Selector("increment"), new IntegerIncrementMethod()) ; 
		
		registerInstanceMethod(new Selector("increment"), new PrimitiveMethod() {
			@Override
			public SmalltalkObject apply(SmalltalkObject receiver, SmalltalkObject[] args) {
				int val = ((IntegerObject)receiver).getValue() ;
				return PrimitiveObject.make(val + 1) ;
			}
		}) ;
		
		registerInstanceMethod(new Selector("+"), new PrimitiveMethod() {
			@Override
			public SmalltalkObject apply(SmalltalkObject receiver, SmalltalkObject[] args) {
				int recVal = ((IntegerObject)receiver).getValue() ;
				int argVal = ((IntegerObject)args[0]).getValue() ;
				
				return PrimitiveObject.make(recVal + argVal) ;
			}
		}) ;
	}	
}

class IntegerObject extends PrimitiveObject {
	IntegerObject(int i) {
		this.classObject = IntegerClassObject.getInstance() ;
		this.val = i ;
	}
	private int val ;
//	int primitiveValue() { return this.val ;}
	@Override public String toString() { return String.valueOf(this.val) ; }
	int getValue() { return val ; } 
}

class SymbolClassObject extends PrimitiveClassObject {
	private SymbolClassObject() {}
	private static SymbolClassObject INSTANCE ;
	public static SymbolClassObject getInstance() {
		if(SymbolClassObject.INSTANCE == null) {
			INSTANCE = new SymbolClassObject() ;
		}
		return INSTANCE ;
	}
	@Override public String toString() { return "Symbol" ;}
}

class SymbolObject extends PrimitiveObject {
	SymbolObject(String s) {
		this.classObject = SymbolClassObject.getInstance() ;
		this.val = s ;
	}
	private String val ;
	@Override public String toString() { return "#" + this.val ; }	
}

class StringClassObject extends PrimitiveClassObject {
	private StringClassObject() {}
	private static StringClassObject INSTANCE ;
	public static StringClassObject getInstance() {
		if(StringClassObject.INSTANCE == null) {
			INSTANCE = new StringClassObject() ;
		}
		return INSTANCE ;
	}
	@Override public String toString() { return "String" ;}
}

class StringObject extends PrimitiveObject {
	private String val ;
	StringObject(String s) {
		this.classObject = StringClassObject.getInstance() ;
		this.val = s ;
	}
	@Override public String toString() { return this.val ; }	
}

class BooleanClassObject extends PrimitiveClassObject {
	
}
