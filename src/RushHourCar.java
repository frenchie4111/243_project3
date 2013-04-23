import java.awt.Point;
import java.util.ArrayList;


public class RushHourCar extends Object {
	
	int x1, x2, y1, y2;
	private ArrayList< Point > blocks; 	// Stores every coordinate the car has a
									   	// "block" on
	private boolean horizontal;
	
	
	/**
	 * Creates a new instance of RushHourCar with the given start and end
	 * coordinates.
	 * Also adds blocks. Note this will fail if a car is not linear
	 * 
	 * Although it may not matter in most cases, please try to keep the arguments
	 * x1y1 up and to the left of x2y2
	 * 
	 * @param x1 x coordinate 1
	 * @param y1 t coordinate 1
	 * @param x2 x coordinate 2
	 * @param y2 y coordinate 2
	 */
	public RushHourCar(int x1, int y1, int x2, int y2) throws IllegalArgumentException {
		if( x1 != x2 && y1 != y2 ) {
			throw new IllegalArgumentException("New car was not linear");
		}
		
		this.x1 = x1;
		this.x2 = x2;
		this.y2 = y2;
		this.y1 = y1;
		horizontal = false;
		if( y1 == y2 ) {
			horizontal = true;
		}
		
		this.blocks = new ArrayList< Point >();
		getBlocks().add( new Point( x1, y1 ) );
		while( x1 < x2 ) {
			x1++;
			getBlocks().add( new Point( x1, y1 ) );
		}
		while( x1 > x2 ) {
			x2++;
			getBlocks().add( new Point( x2, y2 ) );
		}
		while( y1 < y2 ) {
			y1++;
			getBlocks().add( new Point( x1, y1 ) );
		}
		while( y1 > y2 ) {
			y2++;
			getBlocks().add( new Point( x2, y2 ) );
		}
	}
	
	/**
	 * Get's valid moves for this car, given a board
	 * First it checks if the car is horizontal or vertical, then depending on
	 * which it is, it makes new car one block in each possible direction.
	 * It then checks both of these new cars to see if they are valid moves on 
	 * the given board and if so adds them to the return list
	 * 
	 * @param cars The current cars on the board, for checking if the new move is valid
	 * @param width The width of the board
	 * @param height The height of the board
	 * @return List of RushHourCar that is the valid moves for this car
	 */
	public ArrayList<RushHourCar> getValidMoves( ArrayList<RushHourCar> cars, int width, int height ) {
		
		ArrayList<RushHourCar> new_list = new ArrayList<RushHourCar>();
		
		if( horizontal ) {
			RushHourCar left = new RushHourCar(x1 -1, y1, x2-1, y2);
			RushHourCar right = new RushHourCar(x1 + 1, y1, x2 + 1, y2);
			if( isValid( left, cars, width, height ) ) {
				new_list.add( left );
			}
			if( isValid( right, cars, width, height ) ) {
				new_list.add( right );
			}
		} else {
			RushHourCar up = new RushHourCar(x1, y1 - 1, x2, y2 - 1 );
			RushHourCar down = new RushHourCar(x1, y1 + 1, x2, y2 + 1);
			if( isValid( up, cars, width, height ) ) {
				new_list.add( up );
			}
			if( isValid( down, cars, width, height ) ) {
				new_list.add( down );
			}
		}
		
		return new_list;
	}
	
	/**
	 * Checks if a given car is valid on the given board
	 * 
	 * @param car The car to check if valid
	 * @param cars An ArrayList of cars that the new car is to be checked agaisnt
	 * @param width Width of board
	 * @param height Height of board
	 * @return if the new car is valid or not
	 */
	public static boolean isValid( RushHourCar car, ArrayList<RushHourCar> cars, int width, int height ) {
		ArrayList<Point> carBlocks = car.getBlocks(); 
		for( Point carBlock : carBlocks ) {
			if( carBlock.x >= width || carBlock.x < 0 || carBlock.y >= height || carBlock.y < 0 ) {
				return false;
			}
			for( RushHourCar currentCar : cars ) {
				ArrayList<Point> currentCarBlocks = currentCar.getBlocks();
				for( Point currentCarBlock : currentCarBlocks ) {
					if( carBlock.equals(currentCarBlock) ) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean equals(Object o) {
		if( o instanceof RushHourCar ) {
			RushHourCar c = (RushHourCar) o;
			// Make a copy of c's blocks
			ArrayList< Point > cBlocks = new ArrayList< Point >( c.getBlocks() );
			// Make a copy of my blocks
			ArrayList< Point > myBlocks = new ArrayList< Point >( this.getBlocks() );
			
			// Remove all of my blocks from c
			for( Point p : myBlocks ) {
				cBlocks.remove(p);
			}
			if( cBlocks.size() == 0 ) {
				return true; // If c has no more blocks then they are equal
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		String new_string = "";
		for( Point p : blocks ) {
			new_string += p.toString();
		}
		return new_string;
	}

	/**
	 * Returns the blocks
	 * @return ArrayList<Point> the blocks of this car
	 */
	public ArrayList< Point > getBlocks() {
		return blocks;
	}
	
	/**
	 * Puts this car onto the given array
	 * @param array the array to print onto
	 * @param letter the letter to print as "this car"
	 */
	public void printOnArray( char[][] array, char letter ) {
		ArrayList< Point > myBlocks = this.getBlocks();
		for( Point p : myBlocks ) {
			array[p.x][p.y] = letter;
		}
	}
}
