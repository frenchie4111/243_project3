import java.awt.Point;
import java.util.ArrayList;

public class TestRushHour extends Test {
	
		public void test_smoke() {
			assertTrue( true, "True is not true" );
		}
	
		public void test_01Neighbors() {
			RushHour testRH = new RushHour(3, 3, 0, 0);
			
			RushHourCar testCar1 = new RushHourCar( 1,0,2,0 );
			testRH.addCar( testCar1 );
			testRH.setKeyCar(testCar1);
			
			ArrayList<Puzzle> neigh = testRH.getNeighbors();

			assertEqual( neigh.size(), 1, "Neighbors: Wrong size" );
		}
		
		public void test_02BasicSolve() {			
			RushHour testRH = new RushHour(3, 3, 0, 0);
			
			RushHourCar testCar1 = new RushHourCar( 1,0,2,0 );
			testRH.addCar( testCar1 );
			testRH.setKeyCar(testCar1);
			
			ArrayList<Puzzle> solution =  Solver.solve( testRH );

			assertEqual( solution.size(), 2, "Basic Solve: Soltion wrong size" );
		}
		
		public void test_03NoSolution() {
			RushHour testRH = new RushHour(3, 3, 0, 0);
			
			RushHourCar testCar1 = new RushHourCar( 1,1,2,1 );
			testRH.addCar( testCar1 );
			testRH.setKeyCar(testCar1);
			
			ArrayList<Puzzle> solution =  Solver.solve( testRH );
			
			assertTrue( (solution == null), "Basic Solve: Soltion wrong size" );
		}
		
		public void test_04TwoCarSolve() {
			RushHour testRH = new RushHour(3, 3, 0, 0);
			
			RushHourCar testCar1 = new RushHourCar( 1,0,2,0 );
			testRH.addCar( testCar1 );
			testRH.setKeyCar(testCar1);
			testRH.addCar( new RushHourCar(0,0, 0, 1) );
			
			ArrayList<Puzzle> solution =  Solver.solve( testRH );

			assertEqual( solution.size(), 3, "Two Car Solve: Soltion wrong size" );

		}
		
		public void test_05LoadFile() {
			RushHour testRH = RushHour.loadFile( "test.txt" );
			
			// Board should look like this:
			//  abb
			//  axx
			//  xxx
			
			testRH.printConfig();
			
			ArrayList<Puzzle> solution =  Solver.solve( testRH );
			
			assertEqual( solution.size(), 1, "Load File Solve: Soltion wrong size" );
		}
	
		public static void main( String[] args ) {
			Test tester = new TestRushHour();
			//suppress_prints = false;
			tester.run();
		}
}