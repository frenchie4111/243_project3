public class TestAll {
	public static void main( String[] args ) {
		Test clock = new TestClock();
		clock.run();

		Test water = new TestWater();
		water.run();
	}
}