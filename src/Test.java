import java.lang.reflect.Method;
import java.util.ArrayList;
import java.io.PrintStream;
import java.io.OutputStream;

/*
 * Simple Java test Framework
 *
 * Usage:
 * 	Create your own Test___.java that extends Test
 *  In make a main that creates an instance of 
 *  Test___ and then calls Test___.run()
 *  .run() will call all methods with the prefix
 *  "test"
 *  
 *  So far the aserts that exist are:
 *   assertEqual( Object o1, object o2, String message )
 *   assertTrue( Object o, String message )
 *
 * Created by Michael Lyons <mdl0394@gmail.com>
 */


public abstract class Test {
	private ArrayList<ArrayList<String>> totalErrorMessages;
	private int failCount; //How many tests have failed

	private Boolean currentTest; //Whether the current test is passing or not, changed by asserts
	private ArrayList<String> currentMessages; //current tests messages

	public static Boolean suppress_prints = true;

	/**
	 * Creates an instance of Test
	 */
	public Test() {
		totalErrorMessages = new ArrayList< ArrayList<String> >();
		currentMessages = new ArrayList<String>();
		failCount = 0;
	}

	/**
	 * An assert for a test, checks if two objects are equal using .equal
	 * 
	 * The return value is not seen by you, but buy .run(), if it fails it adds
	 * a message to a list to be printed. And calls an error
	 * 
	 * @param object1 - First item to be compared
	 * @param object2 - Second item to be compared
	 * @param errorMessage - Error message to print on fail
	 */
	public void assertEqual(Object object1, Object object2, String errorMessage) {
		if( object1.equals(object2) ) {

		} else {
			this.currentTest = false;
			String new_message = "\tassertEqual failed\n";
			new_message += "\t\t" + object1.toString() + " != " + object2.toString() + "\n";
			new_message += "\t\tUser Message: " + errorMessage;
			addMessage(new_message);
		}
	}

	/**
	 * An assert for a test, checks if an object is a Boolean, and if it's true
	 * 
	 * The return value is not seen by you, but buy .run(), if it fails it adds
	 * a message to a list to be printed. And calls an error 
	 * 
	 * @param object1 - Item to test if it is true
	 * @param errorMessage - Message to print on fail
	 */
	public void assertTrue(Object object1, String errorMessage) {
		if( object1 instanceof Boolean ) {
			if( (Boolean)object1 ) {

			} else {
				this.currentTest = false;
				String new_message = "\tassertTrue failed\n";
				new_message += "\t\t" + object1.toString() + " != true\n";
				new_message += "\t\tUser Message: " + errorMessage;
				addMessage(new_message);
			}
		} else {
			this.currentTest = false;
		}
	}

	/**
	 * Adds a message to the current message
	 * 
	 * @param message - The message to be added
	 */
	private void addMessage( String message ) {
		this.currentMessages.add( message );
	}
	
	/**
	 * To be called after every test is run, finalized it by adding it
	 * 		to total message if it has anything
	 * 
	 * @param methodName - The name of the method the last set of messages
	 * 		was for
	 */
	private void finalizeMessages(String methodName) {
		if( !currentMessages.isEmpty() ) {
			currentMessages.add(0, methodName);
			this.totalErrorMessages.add(currentMessages);
		}
		this.currentMessages = new ArrayList<String>();
	}

	/**
	 * Prints all of the messages in total messages
	 */
	private void printMessages() {
		for( ArrayList<String> errorMessages : this.totalErrorMessages ) {
			System.out.println("-------------------------------------------");
			for( String message : errorMessages ) {
				System.out.println( message );
			}
		}
	}

	/**
	 * Goes through every function in the class, and runs the ones that
	 * 		start with test. For each test, if it passes it prints  . if
	 * 		it failes because the values were wrong it prints and F, and
	 * 		the information, and if it throws an exception (Any Throwable)
	 * 		it prints an E and it's information
	 * 
	 * @return boolean if all of the tests passed
	 */
	public boolean run() {
		PrintStream original = System.out;
		PrintStream noPrint = new PrintStream(new OutputStream() {
												public void write(int b) {}
											});


		try {
			Method m[] = this.getClass().getDeclaredMethods(); // Get all methods
			for( Method i : m ) {
				if( i.getName().startsWith("test") ) { // If it starts with the prefix test
					currentTest = true;

					Boolean thrown = false; // If the test threw an exception
					try {
						if( suppress_prints ) {
							System.setOut(noPrint);
						}

						i.invoke(this);

						System.setOut(original);
					} catch( Throwable t ) {
						System.setOut(original);
						thrown = true;
						System.out.print("E");
						String new_message = "\t" + i.getName() + " threw an exception\n";
						new_message += "\t\t" + t.toString() + "\n";

						Throwable cause = t.getCause();
						while( cause != null ) {
							new_message += "\t\t\t" + cause.toString() + "\n";
							cause = cause.getCause();
						}

						addMessage(new_message);
						this.failCount++;
					}

					if( !this.currentTest ) { // If the current test failed
						System.out.print("F");
						this.failCount++;
					} else if(!thrown) {
						System.out.print(".");
					}
					this.finalizeMessages(i.getName());
				}
			}
			System.out.println("");
			if( this.failCount > 0 ) {
				printMessages();
				System.out.println("");
				System.out.println("[FAILED COUNT=" + this.failCount + "]");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
