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
	
	public SmalltalkObject read(String sExp) {
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
	
	public SmalltalkObject read() throws IOException  {
		while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
			this.can_continue_reading = true ;
			//System.out.println(tokenizer.sval);

			if (tokenizer.ttype == '(') {
				return readList() ;
			} else if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
				return PrimitiveObject.make(tokenizer.sval) ;
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
	public SmalltalkObject readList() throws SmalltalkReaderException, IOException {
		ArrayList<SmalltalkObject> items = new ArrayList<SmalltalkObject>() ;

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

				}
				
				if(arr.length == 0) {
					return PrimitiveObject.make("Expression expected") ;
				} else if(arr.length == 1) {
					return arr[0] ;
				} else {
					//test assume 
					SmalltalkObject receiver = arr[0] ;
					Selector sel = new Selector(arr[1].toString()) ;
					return PrimitiveObject.make("A list:" + arr) ;
				}
			} else {
				tokenizer.pushBack();
				items.add(read()) ;
			}
		}
		
		throw new IOException() ;
	}

	
}

class SmalltalkStreamTokenizer extends StreamTokenizer {
	public SmalltalkStreamTokenizer(Reader r) {
		super(r);
		this.initializeSyntax();
	}
	
	void initializeSyntax() {
		whitespaceChars(' ', ' ');
		commentChar(';');

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
		wordChars('-', '-');	//examples: VARIABLE-WITH-HYPHENS
		wordChars('<', '?');	// <=>? examples: STR->LIS
		wordChars(';', ';');	//handle lines that start with?? ;
		//ordinaryChar('+');
		quoteChar('\'');
		// st.parseNumbers() ;
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