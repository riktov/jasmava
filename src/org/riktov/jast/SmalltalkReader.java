package org.riktov.jast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;

public class SmalltalkReader {
	private SmalltalkStreamTokenizer tokenizer ;
	private boolean can_continue_reading = true ;
	
	public SmalltalkReader(BufferedReader r) {
		tokenizer = new SmalltalkStreamTokenizer(r) ;
	}
	
	public SmalltalkReader(String s) {
		this(new BufferedReader(new StringReader(s))) ;
	}
	
	public Evaluable read(String sExp) {
		Reader r = new BufferedReader(new StringReader(sExp));
		tokenizer = new SmalltalkStreamTokenizer(r);
		try {
			return read() ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public Evaluable read() throws IOException  {
		while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
			this.can_continue_reading = true ;
			//System.out.println(tokenizer.sval);

			switch(tokenizer.ttype) {
			case '(':
				return readCompound() ;
			case StreamTokenizer.TT_WORD:
				System.out.println("Read a word: " + tokenizer.sval) ;
				
				/**
				 * We parse numbers ourselves instead of using StreamTokenizer's
				 * number parsing, because it will read an end-of-statement period as a decimal point.
				 * 5.3 -> 5.3
				 * 5. 3 -> 3
				 * 
				 */
				/*
				Atom a ; 

				try {
					a = parseNumber(tokenizer.sval);
				} catch (NumberFormatException e) {
					a = new SymbolAtom(tokenizer.sval);
				}
				return a ;
				*/
				
				return PrimitiveObject.make(tokenizer.sval) ;
			case StreamTokenizer.TT_NUMBER:
				System.out.println("Read a number. " + tokenizer.nval) ;
				return PrimitiveObject.make((int)tokenizer.nval) ;				
			}			
		}	//end of while loop, exited by no more input
		this.can_continue_reading = false ;
		return null ;
	}

	/**
	 * Reads everything up to a closing parenthesis as a message send or cascade
	 * @return the read SmalltalkObject
	 * @throws IOException
	 */
	public CompoundExpression readCompound() throws SmalltalkReaderException, IOException {
		ArrayList<Evaluable> items = new ArrayList<Evaluable>() ;

		//System.out.println("Starting reading list") ;
		while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
			if (tokenizer.ttype == ')') {
				//System.out.println("Closing paren to list") ;
				SmalltalkObject[] arr = new SmalltalkObject[items.size()];
				items.toArray(arr);	//writes into arr

				switch(arr.length) {
				case 0 :
					throw new SmalltalkReaderException("Expression expected") ;
				case 1:
					return arr[0] ;
				default:
					SmalltalkObject receiver = arr[0] ;
					Selector sel = new Selector(arr[1].toString()) ;
					return new CompoundExpression() ;
				}				
			} else {
				tokenizer.pushBack();
				items.add(read()) ;
			}
		}
		
		throw new IOException() ;
	}

	public static SmalltalkObject parseNumber(String st) {
		try {
			return PrimitiveObject.make(Integer.valueOf(st));
		} catch (NumberFormatException e) {
			return PrimitiveObject.make("[unimplemented] New float") ;
//			return PrimitiveObject.make(Float.valueOf(st));
		}
	}
}

class SmalltalkStreamTokenizer extends StreamTokenizer {
	public SmalltalkStreamTokenizer(Reader r) {
		super(r);
		this.initializeSyntax();
	}
	
	void initializeSyntax() {
		whitespaceChars(' ', ' ');
		//commentChar(';');

		resetSyntax();
		whitespaceChars(' ', ' ');
		whitespaceChars(9, 9) ;	//tab
		whitespaceChars('\n', '\n') ;
		wordChars('a', 'z');
		wordChars('A', 'Z');
		wordChars('0', '9');
//		wordChars('.', '.');
		wordChars('!', '!');
		wordChars('*', '*');	//examples: LET*
//		wordChars('-', '-');	//examples: VARIABLE-WITH-HYPHENS
		wordChars('<', '?');	// <=>? examples: STR->LIS
		wordChars(';', ';');	//handle lines that start with?? ;
		//ordinaryChar('+');
		quoteChar('\'');
		//parseNumbers() ;
		// st.ordinaryChars('0', '9') ;
		//commentChar(';') ;
	}
}

class SmalltalkReaderException extends RuntimeException {
	public SmalltalkReaderException(String string) {
		super(string) ;
	}

	private static final long serialVersionUID = 1L;
}
