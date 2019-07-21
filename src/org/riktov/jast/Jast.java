package org.riktov.jast;

/**
 * 
 * The six keywords in Smalltalk are:
 * true, false, nil, self, super, thisContext
 *
A Smalltalk object can do exactly three things:

Hold state (references to other objects).
Receive a message from itself or another object.
In the course of processing a message, send messages to itself or another object.

http://wiki.c2.com/?HeartOfSmalltalk

You can do the following seven things
Send a message -- invoke the method found by looking up the message selector in the class of the receiver
Read the value of a variable. Variables hold references to objects.
Read the value of a literal, i.e. 'hello' or 3.
Assign a variable -- store a reference to an object in a variable (could be a temporary (method scoped) variable or 
	an instance variable)
Return a value -- push an object on the stack to be found by the invoker of this method
Create a block -- an object that refers to some code you can evaluate. 
	It is a closure, like in Scheme, and is used to make control structures. 
	It is the only thing on this list that is complicated.
 */

public class Jast {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out. println("Started JAST.\n") ;
	}

}
