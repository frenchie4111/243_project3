import java.awt.Point;
import java.util.ArrayList;

public class TestRushHourCar extends Test {
	
		public void test_smoke() {
			assertTrue( true, "True is not true" );
		}
	
		public void test_01CreateCarSize3() {			
			RushHourCar testCar1 = new RushHourCar( 0, 0, 0, 2 );
			
			ArrayList<Point> testCar1Blocks = testCar1.getBlocks();
			
			assertEqual( testCar1Blocks.size(), 3, "Test Car 1: Size not equal to 3");
			assertTrue( testCar1Blocks.contains( new Point(0,0) ), "Test Car 1: Doesnt contain 0 0" );
			assertTrue( testCar1Blocks.contains( new Point(0,1) ), "Test Car 1: Doesnt contain 0 1" );
			assertTrue( testCar1Blocks.contains( new Point(0,2) ), "Test Car 1: Doesnt contain 0 2" );			
		}
		
		public void test_02CreateCarSize2() {
			RushHourCar testCar2 = new RushHourCar( 0, 0, 0, 1 );
			
			ArrayList<Point> testCar2Blocks = testCar2.getBlocks();
			
			assertEqual( testCar2Blocks.size(), 2, "Test Car 2: Size not equal to 2");
			assertTrue( testCar2Blocks.contains( new Point(0,0) ), "Test Car 2: Doesnt contain 0 0" );
			assertTrue( testCar2Blocks.contains( new Point(0,1) ), "Test Car 2: Doesnt contain 0 1" );			
		}
		
		public void test_03CarIsValidWhenValid() {
			ArrayList<RushHourCar> testBoard = new ArrayList<RushHourCar>();
			
			testBoard.add( new RushHourCar(0,0,2,0) );
			
			assertTrue( RushHourCar.isValid( new RushHourCar(0, 1, 0, 2), testBoard, 3, 3), "Not Valid: (0, 1, 0, 2)" );
			assertTrue( RushHourCar.isValid( new RushHourCar(1, 1, 1, 2), testBoard, 3, 3), "Not Valid: (1, 1, 1, 2)" );
			assertTrue( RushHourCar.isValid( new RushHourCar(2, 1, 2, 2), testBoard, 3, 3), "Not Valid: (2, 1, 2, 2)" );
			assertTrue( RushHourCar.isValid( new RushHourCar(0, 1, 2, 1), testBoard, 3, 3), "Not Valid: (0, 1, 2, 1)" );
		}
		
		public void test_03CarIsValidWhenNotValid() {
			ArrayList<RushHourCar> testBoard = new ArrayList<RushHourCar>();
			
			testBoard.add( new RushHourCar(0,1,2,1) );
			
			assertTrue( !RushHourCar.isValid( new RushHourCar(0, 1, 0, 2), testBoard, 3, 3), "Is Valid: (0, 1, 0, 2)" );
			assertTrue( !RushHourCar.isValid( new RushHourCar(1, 1, 1, 2), testBoard, 3, 3), "Is Valid: (1, 1, 1, 2)" );
			assertTrue( !RushHourCar.isValid( new RushHourCar(2, 1, 2, 2), testBoard, 3, 3), "Is Valid: (2, 1, 2, 2)" );
			assertTrue( !RushHourCar.isValid( new RushHourCar(0, 1, 2, 1), testBoard, 3, 3), "Is Valid: (0, 1, 2, 1)" );
		}
		
		public void test_04equals() {
			Point p1 = new Point(0,0);
			Point p2 = new Point(0,0);
			
			assertEqual( p1, p2, "Points not equal" );
			assertTrue( p1.equals(p2), "Points not equal");
			
			RushHourCar car1 = new RushHourCar(0, 0, 3, 0);
			RushHourCar car2 = new RushHourCar(0, 0, 3, 0);
			
			assertEqual( car1, car2, "Cars not equal" );
			assertTrue( car1.equals(car2), "Cars not equal");
		}
		
		public void test_05getValidMovesHorizontal() {
			ArrayList<RushHourCar> emptyBoard = new ArrayList<RushHourCar>();
			
			ArrayList<RushHourCar> noMovesBoard = new ArrayList<RushHourCar>();
			RushHourCar noMoveCar = new RushHourCar(0, 0, 3, 0);
			assertEqual( noMovesBoard, noMoveCar.getValidMoves(emptyBoard, 3, 3), "No Moves Car Failed" );
			
			ArrayList<RushHourCar> moveLeftBoard = new ArrayList<RushHourCar>();
			moveLeftBoard.add( new RushHourCar( 0,0,1,0 ) ); // Expected results
			
			RushHourCar moveLeftCar = new RushHourCar(1, 0, 2, 0);
			
			ArrayList<RushHourCar> moveLeftResults = moveLeftCar.getValidMoves(emptyBoard, 3, 3);
			assertEqual( moveLeftBoard.size(), 1, "Move left Board: Not the correct size" );
			assertEqual( moveLeftResults, moveLeftBoard, "Move Left: Found the wrong results" );
		}
		
		public void test_05getValidMovesVerticle() {
			ArrayList<RushHourCar> emptyBoard = new ArrayList<RushHourCar>();
			
			ArrayList<RushHourCar> noMovesBoard = new ArrayList<RushHourCar>();
			RushHourCar noMoveCar = new RushHourCar(0, 0, 0, 3);
			assertEqual( noMovesBoard, noMoveCar.getValidMoves(emptyBoard, 3, 3), "No Moves Car Failed" );
			
			ArrayList<RushHourCar> moveLeftBoard = new ArrayList<RushHourCar>();
			moveLeftBoard.add( new RushHourCar( 0,0,0,1 ) ); // Expected results
			
			RushHourCar moveLeftCar = new RushHourCar(0, 1, 0, 2);
			
			ArrayList<RushHourCar> moveLeftResults = moveLeftCar.getValidMoves(emptyBoard, 3, 3);
			assertEqual( moveLeftBoard.size(), 1, "Move left Board: Not the correct size" );
			assertEqual( moveLeftResults, moveLeftBoard, "Move Left: Found the wrong results" );
		}
	
		public static void main( String[] args ) {
			Test tester = new TestRushHourCar();
			suppress_prints = false;
			tester.run();
		}
}