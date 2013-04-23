import java.awt.Point;
import java.util.ArrayList;


public class RushHour implements Puzzle {

	private ArrayList<RushHourCar> cars;
	private int width, height;
	private RushHourCar keyCar;
	private Point exit;
	
	public RushHour( ArrayList<RushHourCar> cars, int width, int height, int exitx, int exity ) {
		this.cars = cars;
		this.width = width;
		this.height = height;
		exit = new Point( exitx, exity );
		if( cars.size() > 0 ) {
			keyCar = cars.get(cars.size() - 1);
		}
	}
	
	public RushHour( int width, int height, int exitx, int exity ) {
		this( new ArrayList<RushHourCar>(), width, height, exitx, exity );
	}
	
	/**
	 * Adds a car to car list
	 * @param car
	 */
	public void addCar(RushHourCar car) {
		cars.add(car);
	}
	
	public void setKeyCar( RushHourCar car ) {
		keyCar = car;
	}
	
	@Override
	public Boolean isGoal() {
		ArrayList< Point > cBlocks = keyCar.getBlocks();
		for( Point p : cBlocks ) {
			if( p.equals(exit) ) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ArrayList<Puzzle> getNeighbors() {
		ArrayList<Puzzle> new_configs = new ArrayList<Puzzle>();
		for( RushHourCar c : cars ) { // For each car
			ArrayList< RushHourCar > current_cars = new ArrayList<RushHourCar>(getCars());
			current_cars.remove( c ); // Remove THIS car from the cars (So it can be moved)
			ArrayList<RushHourCar> validMoves = c.getValidMoves(current_cars, width, height);
			for( RushHourCar validMove : validMoves ) {
				RushHour newConfig = new RushHour( current_cars, width, height, exit.x, exit.y );
				newConfig.setKeyCar( keyCar );
				newConfig.addCar( validMove );
				new_configs.add( newConfig );
				if( c == keyCar ) {
					newConfig.setKeyCar(validMove);
				}
			}
		}
		return new_configs;
	}

	@Override
	public Object getConfig() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ArrayList<RushHourCar> getCars() {
		return cars;
	}
	
	public void printConfig() {
		char[][] array = getCharRepresentation();
		for( int i = 0; i < width; i++ ) {
			for( int j = 0; j < height; j++ ) {
				System.out.print( array[j][i] );
			}
			System.out.println("");
		}
	}
	
	private char[][] getCharRepresentation() {
		char[][] array = new char[width][height];
		for( int i = 0; i < width; i++ ) {
			for( int j = 0; j < height; j++ ) {
				array[i][j] = 'x';
			}
		}
		char letter = 'a';
		for( RushHourCar c : cars ) {
			c.printOnArray(array, letter);
			letter++;
		}
		return array;
	}
	
	public String toString() {
		String new_string = "";
		char[][] array = getCharRepresentation();
		for( int i = 0; i < width; i++ ) {
			for( int j = 0; j < height; j++ ) {
				new_string += Character.toString( array[j][i] );
			}
			new_string += "\n";
		}
		return new_string;
	}
	
	public boolean equals( Object o ) {
		if( o instanceof RushHour ) {
			RushHour r = (RushHour)o;
			if( r.toString().equals(this.toString()) ) {
				return true;
			}
		}
		return false;
	}
	
	public int hashCode() {
		return this.toString().hashCode();
	}

	public void setCars(ArrayList<RushHourCar> cars) {
		this.cars = cars;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Takes arguments and solves puzzle based on it
	 * @param args file
	 */
	public static void main(String[] args) {		
		if(args.length > 2) {
			Integer goal = Integer.parseInt(args[0]);
			ArrayList<Integer> buckets = new ArrayList<Integer>(); // Bucket's sizes
			for( int i = 1; i < args.length; i++ ) {
				buckets.add( Integer.parseInt(args[i]) );
			}
			try {
				//Assign args
			} catch(Throwable t) {
				System.out.println("One of the arguments was not a number");
			}
			boolean bound_check = false;
			for( Integer bucket : buckets ) {
				if( bucket >= goal ) {
					bound_check = true;
				}
			}
			if(goal > 0 && buckets.size() > 0 && bound_check) { // Bound check args
			
				Puzzle myPuzzle = (Puzzle) new Water(goal, buckets);	
				ArrayList< Puzzle > solution = Solver.solve(myPuzzle);
				
				if(solution != null) {
					for( int i = 0; i < solution.size(); i++ ) {
						System.out.println("Step " + Integer.toString(i) + ": " + solution.get(i));
					}
				} else {
					System.out.println("No solution.");	
				}
			
			} else {
				System.out.println("No solution.");
			}
		} else {
			System.out.println("Usage: file");
		}
	}

}
