import java.util.ArrayList;

public class TestWater extends Test {
	
		public void test_smoke() {
			assertTrue( true, "True is not true" );
		}
	
		public void test_01WaterStandard() {
			ArrayList<Integer> buckets = new ArrayList<Integer>();
			buckets.add(15);
			buckets.add(2);
			buckets.add(5);
			Puzzle water = new Water(12, buckets);
			
			ArrayList< Puzzle > results = Solver.solve(water);
						
			assertEqual( ((Water)results.get(0)).getConfig().get(0), 0, "First step first bucket");
			assertEqual( ((Water)results.get(0)).getConfig().get(1), 0, "First step second bucket");
			assertEqual( ((Water)results.get(0)).getConfig().get(2), 0, "First step third bucket");

			assertEqual( ((Water)results.get(4)).getConfig().get(0), 12, "Last step first bucket");
			assertEqual( ((Water)results.get(4)).getConfig().get(1), 0, "Last step second bucket");
			assertEqual( ((Water)results.get(4)).getConfig().get(2), 5, "Last step third bucket");
		}
		
		public void test_02WaterNoSolution() {
			ArrayList<Integer> buckets = new ArrayList<Integer>();
			buckets.add(11);
			buckets.add(2);
			buckets.add(5);
			Puzzle water = new Water(12, buckets);
			
			ArrayList< Puzzle > results = Solver.solve(water);
						
			assertTrue( (results == null), "No solution not found");
		}
	
		public static void main( String[] args ) {
			Test tester = new TestWater();
			tester.run();
		}
}
