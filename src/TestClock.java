import java.util.ArrayList;

public class TestClock extends Test {
	
		public void test_smoke() {
			assertTrue( true, "True is not true" );
		}
	
		public void test_01ClockStandard() {
			Puzzle clock = new Clock(12, 1, 5);
			
			ArrayList< Puzzle > results = Solver.solve(clock);
			
			assertEqual( results.get(0).getConfig(), 1, "First result is wrong" );
			assertEqual( results.get(1).getConfig(), 2, "Second result is wrong" );
			assertEqual( results.get(2).getConfig(), 3, "Third results is wrong" );
			assertEqual( results.get(3).getConfig(), 4, "Fourth results is wrong" );
			assertEqual( results.get(4).getConfig(), 5, "Fifth results is wrong" );
		}
		
		public void test_02ClockPast0() {
			Puzzle clock = new Clock(12, 1, 11);
			
			ArrayList< Puzzle > results = Solver.solve(clock);
			
			assertEqual( results.get(0).getConfig(), 1, "First result is wrong" );
			assertEqual( results.get(1).getConfig(), 12, "Second result is wrong" );
			assertEqual( results.get(2).getConfig(), 11, "Third results is wrong" );
		}
	
		public static void main( String[] args ) {
			Test tester = new TestClock();
			tester.run();
		}
}
