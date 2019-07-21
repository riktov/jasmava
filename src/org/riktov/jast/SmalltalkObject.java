package org.riktov.jast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A Smalltalk object
 * 
 * @author riktov@freeshell.org (Paul Richter)
 *
 */
class SmalltalkObject {
	SmalltalkClassObject classObject ;
	HashMap<String, SmalltalkObject> instanceVariables = new HashMap<String, SmalltalkObject>();
//	String name = "[unimplemented] String representation of an Object." ;
	PrimitiveData primitiveData = null ;
	
	@Override public String toString() { 
		if(primitiveData != null) {
			return primitiveData.toString() ;
		} else {
			return "a " + classObject.name + "(" + this.hashCode() + ")" ; 
		}
	}

    SmalltalkObject receiveMessage(Message msg) {
    	Selector sel = msg.getSelector() ;
    	SmalltalkObject[] argVals = msg.getArgs() ;
		SmalltalkClassObject cls = this.stClass() ;

		Method meth = cls.lookupMethod(sel);
    	
		return meth.apply(this, argVals);
    	//return StringObject.make("[unimplemented] The result of SmalltalkObject.receiveMessage()") ;
    }

	/**
	 * 
	 * @return the SmalltalkClassObject
	 */
	public SmalltalkClassObject stClass() {	return this.classObject; }

	/* super is the parent of this object's class object */
	public SmalltalkObject stSuper() { return this.stClass().getParentClass() ; }
}

/**
 * A Smalltalk class object
 * 
 * @author Paul Richter
 *
 */
class SmalltalkClassObject extends SmalltalkObject {
	SmalltalkClassObject parentClass = null ;
	MethodDictionary methodDictionary = new MethodDictionary();
	ArrayList<String> instanceVariableNames = new ArrayList<String>() ;
	PrimitiveData primitiveDataTemplate = null ;
	String name = "" ;
	
	/* constructor */
	SmalltalkClassObject() {
		this.initializeMethods();
	}
	SmalltalkClassObject(String name) {
		this() ;
		this.name = name ;
	}
	
	/* accessors */
	public SmalltalkClassObject getParentClass() { return this.parentClass ;}
	
	@Override
	public String toString() { return (name + " class"); }
	public Method lookupMethod(Selector sel) {
		System.out.println("lookupMethod() [" +  sel.toString() + "] on class for " + this.name) ;
		MethodDictionary md = this.methodDictionary ;
		Method meth = md.getDict().get(sel.toString()) ;
		if(meth != null) {
			return meth ;
		} else {
			SmalltalkClassObject parent = this.getParentClass() ;
			if(parent != null) {
				return parent.lookupMethod(sel) ;
			} else {
				return new Method() {
					@Override
					public SmalltalkObject apply(SmalltalkObject receiver, SmalltalkObject[] args) {
						return PrimitiveObject.make("The Message [" + sel.toString() + "] was not understood.") ;
					}
				};
			}
		}
	}

	SmalltalkObject createInstance() {
		SmalltalkObject instance = new SmalltalkObject() ;
		instance.classObject = this ;
		//instance.name = "A " + this.name ;
		try {
			if(this.primitiveDataTemplate != null) {
				instance.primitiveData = (PrimitiveData)(this.primitiveDataTemplate.clone()) ;
			}
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(String s: this.instanceVariableNames) {
			instance.instanceVariables.put(s, new SmalltalkObject()) ;
		}
		return instance ;
	}
	
	static public SmalltalkObject createInstanceOfClassNamed(String stClassname, Environment env) {
		SmalltalkClassObject stco = (SmalltalkClassObject) env.get(stClassname) ;
		return stco.createInstance() ;
	}
	/**
	 * 
	 * @param sel a Selector
	 * @param meth a Method
	 */
	void registerInstanceMethod(Selector sel, Method meth) {
		this.methodDictionary.getDict().put(sel.toString(), meth) ;
	}

	public void initializeMethods() {
		registerInstanceMethod(new Selector("class"), new PrimitiveMethod() {
			@Override
			public SmalltalkObject apply(SmalltalkObject receiver, SmalltalkObject[] args) {
				return receiver.stClass() ;
			}
		}) ;
	}	
}

/**
 * PrimitiveData is an adapter for handling primitive types in Java
 * @author riktov@freeshell.org (Paul Richter)
 *
 */
abstract class PrimitiveData implements Cloneable {
	//abstract SmalltalkObject get() ;
	abstract void set(String str) ;
	//We implement a method here, so this must be an abstract class
	//rather than an interface
	@Override
	protected Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}
}

class IntegerData extends PrimitiveData {
	int intData ;
	@Override void set(String str) { this.intData = Integer.parseInt(str) ; }
	@Override public String toString() { return String.valueOf(this.intData) ; }
	int getIntegerData() { return this.intData ; }
	void setIntegerData(int i) { this.intData = i ;}
}

class StringData extends PrimitiveData {
	String stringData ;
	@Override void set(String str) { this.stringData = str ; }
	@Override public String toString() { return this.stringData ; }
	String getStringData() { return this.stringData ; }
	void setStringData(String str) { this.stringData = str ;}
}

class MethodDictionary /* extends SmalltalkObject */ 
{
	private HashMap<String, Method> dict = new HashMap<String, Method>() ;
	HashMap<String, Method> getDict() { return dict ;}
}

