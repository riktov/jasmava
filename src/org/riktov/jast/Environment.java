package org.riktov.jast;

import java.util.HashMap;

/**
 * the SystemDictionary named Smalltalk (see the Squeak description of Metaclass)
 * 
 * @author riktov@freeshell.org (Paul Richter)
 *
 */
class Environment extends HashMap<String, SmalltalkObject> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1229L;
	private PrimitiveMethodTable primitiveMethods ; 

	void initialize() {
		createRootClasses() ;
		createCoreClasses() ;
		createPrimitiveDataClasses() ;
		
		primitiveMethods = PrimitiveMethodTable.initialPrimitives(this) ;
		
		installMethods(this) ;
	}
	
	SmalltalkObject lookup(String str) {
		return this.get(str);
	}

	//this is a Singleton
	private Environment() {
		this.initialize();
	}
	private static Environment INSTANCE ;
	public static Environment getInstance() {
		if(Environment.INSTANCE == null) {
			INSTANCE = new Environment() ;
		}
		return INSTANCE ;
	}
	
	void createRootClasses() {
		SmalltalkClassObject objectClass = new SmalltalkClassObject("Object") ;
		this.put(objectClass.name, objectClass) ;		
		//we can not set the object class since Class class has not been created
		//we will do this in createCoreClasses() ;
	}

	void createCoreClasses() {
		SmalltalkClassObject classClass = new SmalltalkClassObject("Class") ;//this can be replaced with Smalltalk to bootstrap
		classClass.classObject = classClass ;
		this.put(classClass.name, classClass) ;		

		SmalltalkObject objectClass = this.get("Object") ;
		objectClass.classObject = classClass ;
	}
	
	void createPrimitiveDataClasses() {
		//The primitive data classes
		//Integer
		SmalltalkClassObject integerClass = new SmalltalkClassObject("Integer") ;
		integerClass.classObject = (SmalltalkClassObject) this.get("Class") ;
		integerClass.primitiveDataTemplate = new IntegerData();
		this.put(integerClass.name, integerClass) ;		
	}
	
	void installMethods(Environment env) {
		SmalltalkClassObject objectClass = (SmalltalkClassObject)this.get("Object") ;
		SmalltalkClassObject classClass = (SmalltalkClassObject) this.get("Class") ;		

		objectClass.registerInstanceMethod(new Selector("subclass:"), primitiveMethods.get("subclass:")) ;		

		objectClass.registerInstanceMethod(new Selector("basicNew"), new Method() {
			@Override public SmalltalkObject apply(SmalltalkObject receiver, SmalltalkObject[] args ) {
				return ((SmalltalkClassObject)receiver).createInstance() ;
 			}
		});

	}

}
