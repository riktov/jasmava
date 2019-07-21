/*
 * This module contains the definition of the primitive methods
 * used in the Smalltalk class library.
 * 
 http://wiki.squeak.org/squeak/5716
 
 A primitive method is a Method that invokes behavior in the Virtual Machine (Overview) or a plugin. 
 Primitive methods provide functionality that is not available from normal Smalltalk methods.
 
For example, there are primitive methods for I/O For example:
file access (See FileStream)
network sockets (see Socket)
mouse and keyboard events (see Sensor)

Additionally, there are methods to implement parts of Smalltalk semantics. For example:
#basicNew creates new objects
#class returns the class of an object
#at: and #at:put: access the indexed variables of an array-like object
 */
package org.riktov.jast;

import java.util.HashMap;

abstract class PrimitiveMethod extends Method {
	int installedIndex = -1;
	
	PrimitiveMethod() {
	}
	
	PrimitiveMethod(int index) {
		this.installedIndex = index ;
	}
}

class PrimitiveMethodTable extends HashMap<String, PrimitiveMethod> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Return a HashMap for primitive methods
	 * @param env
	 * @return
	 */
	static PrimitiveMethodTable initialDataPrimitives(final Environment env) {
		PrimitiveMethodTable dataPrimitives = new PrimitiveMethodTable();

		dataPrimitives.put("+<Integer>", new PrimitiveMethod(dataPrimitives.size()) {
			@Override
			public
			SmalltalkObject apply(SmalltalkObject receiver, SmalltalkObject[] args) {
				IntegerData dataReceiver = (IntegerData)receiver.primitiveData ;
				int argReceiver = dataReceiver.getIntegerData() ;
				IntegerData dataOperand = (IntegerData)args[0].primitiveData ;
				int argOperand = dataOperand.getIntegerData() ;
				
				int result = argReceiver + argOperand ;
				
				SmalltalkClassObject resultClass = (SmalltalkClassObject)Environment.getInstance().lookup("Integer") ;
				
				IntegerData dataResult = (IntegerData)resultClass.primitiveDataTemplate ;
				dataResult.setIntegerData(result); //this needs to be then channeled to the instance, from the class
				return resultClass ;
			}
		}) ;

		dataPrimitives.put("*<Integer>", new PrimitiveMethod(dataPrimitives.size()) {
			@Override
			public
			SmalltalkObject apply(SmalltalkObject receiver, SmalltalkObject[] args) {
				IntegerData dataReceiver = (IntegerData)receiver.primitiveData ;
				int argReceiver = dataReceiver.getIntegerData() ;
				IntegerData dataOperand = (IntegerData)args[0].primitiveData ;
				int argOperand = dataOperand.getIntegerData() ;
				
				int result = argReceiver * argOperand ;
				
				SmalltalkClassObject resultClass = (SmalltalkClassObject)Environment.getInstance().lookup("Integer") ;
				
				IntegerData dataResult = (IntegerData)resultClass.primitiveDataTemplate ;
				dataResult.setIntegerData(result); //this needs to be then channeled to the instance, from the class
				return resultClass ;
			}
		}) ;

		return dataPrimitives ;
	}

	static PrimitiveMethodTable initialPrimitives(final Environment env) {
		PrimitiveMethodTable primitives = new PrimitiveMethodTable();
		
		primitives.put("subclass:", new PrimitiveMethod(primitives.size()) {
			@Override public SmalltalkObject apply(SmalltalkObject receiver, SmalltalkObject[] args) {
				SmalltalkClassObject subclass = new SmalltalkClassObject(args[0].toString()) ;
				
				subclass.parentClass = (SmalltalkClassObject) receiver ;
				subclass.classObject = (SmalltalkClassObject) env.get("Class") ;
				env.put(subclass.name, subclass) ;
				return subclass ;
			}
		}) ;
		
		primitives.put("new", new PrimitiveMethod(primitives.size()) {
			@Override public SmalltalkObject apply(SmalltalkObject receiver, SmalltalkObject[] args ) {
				return ((SmalltalkClassObject)receiver).createInstance() ;
 			}
		});
		
		return primitives ;
	}
}


/*
class IntegerIncrementMethod extends PrimitiveMethod {
	@Override
	public SmalltalkObject apply(SmalltalkObject receiver, SmalltalkObject[] args) {
		int val = ((IntegerObject)receiver).getValue() ;
		return PrimitiveObject.make(val + 1) ;		
	}
}
*/

