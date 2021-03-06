import java.awt.Point;
import java.util.ArrayList;


public class RushHourCar extends Object {
	
	private int x1;
	private int x2;
	private int y1;
	private int y2;
	private ArrayList< Point > blocks; 	// Stores every coordinate the car has a
									   	// "block" on
	private boolean horizontal;
	private int carnum;
	private static int numcars = 0;
	
	
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
		setCarnum(numcars);
		numcars++;
		
		if( x1 != x2 && y1 != y2 ) {
			throw new IllegalArgumentException("New car was not linear");
		}
		
		this.setX1(x1);
		this.setX2(x2);
		this.setY2(y2);
		this.setY1(y1);
		horizontal = false;
		if( y1 == y2 ) {
			horizontal = true;
		}
		
		this.blocks = new ArrayList< Point >();
		reassesBlocks();
	}
	
	/**
	 * Creates a new car that is a copy of the car given
	 * @param oldc Car to create a copy of
	 */
	public RushHourCar( RushHourCar oldc ) {
		this( oldc.getX1(), oldc.getY1(), oldc.getX2(), oldc.getY2(), oldc.getCarnum() );
	}
	
	/**
	 * Creates a new car at the given positions, with the given number
	 * @param x1 Coordinate of car
	 * @param y1 Coordinate of car
	 * @param x2 Coordinate of car
	 * @param y2 Coordinate of car
	 * @param numcar The car's number
	 * @throws IllegalArgumentException If car is not linear throws exception
	 */
	public RushHourCar(int x1, int y1, int x2, int y2, int numcar) throws IllegalArgumentException {
		this( x1, y1, x2, y2 );
		setCarnum(numcar);
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
			RushHourCar left = new RushHourCar(getX1() -1, getY1(), getX2()-1, getY2(), getCarnum());
			RushHourCar right = new RushHourCar(getX1() + 1, getY1(), getX2() + 1, getY2(), getCarnum());
			if( isValid( left, cars, width, height ) ) {
				new_list.add( left );
			}
			if( isValid( right, cars, width, height ) ) {
				new_list.add( right );
			}
		} else {
			RushHourCar up = new RushHourCar(getX1(), getY1() - 1, getX2(), getY2() - 1, getCarnum() );
			RushHourCar down = new RushHourCar(getX1(), getY1() + 1, getX2(), getY2() + 1, getCarnum() );
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
	 * @param cars An ArrayList of cars that the new car is to be checked against
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
	
	/**
	 * Equals method for car, checks if they contain the same blocks (Blocks may accidentally be in the wrong order)
	 */
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
	
	/**
	 * Returns a string representation of a cars points, (x,y)(x,y)(x,y) where each point is the location of
	 * one of the cars blocks
	 */
	public String toString() {
		String new_string = "";
		for( Point p : blocks ) {
			new_string += "(" + Integer.toString( p.x ) + ", " + Integer.toString( p.y ) + ") ";
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
	
	public void setBlocks(ArrayList< Point > blocks) {
		this.blocks = blocks;
	}
	
	public boolean isHorizontal() {
		return horizontal;
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
	/**
	 * Puts this car onto the given array of chars
	 * @param array the array to print onto
	 */
	public void printOnArray( char[][] array ) {
		ArrayList< Point > myBlocks = this.getBlocks();
		for( Point p : myBlocks ) {
			array[p.x][p.y] = Integer.toString( getCarnum() ).charAt(0);
		}
	}
	
	/**
	 * Resasses the blocks for the car based on the cars xy coordinates
	 */
	public void reassesBlocks() {
		int x1 = this.x1;
		int x2 = this.x2;
		int y1 = this.y1;
		int y2 = this.y2;
		getBlocks().clear();
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
	 * Gets the cars xposition
	 * @return x1
	 */
	public int getX1() {
		return x1;
	}

	/**
	 * Sets the cars x position
	 * @param x1 the new x position
	 */
	public void setX1(int x1) {
		this.x1 = x1;
	}

	/**
	 * Gets the cars y1 position
	 * @return y1 the y1 of the car
	 */
	public int getY1() {
		return y1;
	}

	/**
	 * Sets the cars y position
	 * @param y1 the new y position
	 */
	public void setY1(int y1) {
		this.y1 = y1;
	}

	/**
	 * Gets the cars x2 position
	 * @return x2 int the x2 position of the car (Lower right hand corner)
	 */
	public int getX2() {
		return x2;
	}

	/**
	 * Sets the x2 position of the car
	 * @param x2 the new x2
	 */
	public void setX2(int x2) {
		this.x2 = x2;
	}

	/**
	 * Gets the cars y2 position
	 * @return y2 int the y2 position of the car
	 */
	public int getY2() {
		return y2;
	}

	/**
	 * Sets the cars y2 position
	 * @param y2
	 */
	public void setY2(int y2) {
		this.y2 = y2;
	}
	
	/**
	 * Gets the cars number
	 * @return gets the car number
	 */
	public int getCarnum() {
		return carnum;
	}

	/**
	 * Sets the cars number
	 * @param carnum sets the carnum
	 */
	public void setCarnum(int carnum) {
		this.carnum = carnum;
	}
}
