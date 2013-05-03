import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;


public class Jam {
	public static void main(String[] args) {
		OutputStream noop = new OutputStream() {
			@Override
			public void write(int arg0) throws IOException {
				// NOOP
			}
		};
		System.setOut(new PrintStream(noop));
		
		if( args.length == 1 ) {
			RushHour myGame = RushHour.loadFile(args[0]);
			new RushHourViewController( myGame );
		} else {
			System.out.println("usage: java Jam input-file");
		}
	}
}
