import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

public class RushHour extends Observable implements Puzzle {

	private ArrayList<RushHourCar> cars;
	private int width, height;
	private RushHourCar keyCar;
	private Point exit;
	
	public static RushHour originalConfig = null; // For reseting
	
	/**
	 * Initialized a new RushHour that is copy of the one given
	 * @param oldrh the old RushHour to copy
	 */
	public RushHour( RushHour oldrh ) {
		changeTo( oldrh );
	}
	
	/**
	 * Changes the current RushHour to match the given one
	 * 	This is to be used for reseting the game to the initial
	 * 	configuration, creating a copy of the board
	 * @param oldrh
	 */
	public void changeTo( RushHour oldrh ) {
		System.out.println("Copying");
		this.cars = new ArrayList<RushHourCar>();
		for( RushHourCar c : oldrh.getCars() ) {
			System.out.println("Copying " + c);
			RushHourCar new_car = new RushHourCar(c);
			this.cars.add( new_car );
			if( c.getCarnum() 
					== oldrh.getKeyCar().getCarnum() ) {
				this.keyCar = new_car;
			}
		}
		this.setExit(oldrh.getExit());
		this.setWidth(oldrh.getWidth());
		this.setHeight(oldrh.getHeight());
		System.out.println("Done Copying");
	}
	
	/**
	 * Creates a new instance of RushHour with the information given
	 * @param cars ArrayList<RushHourCar> the cars that are in the new board
	 * @param width Width of the board
	 * @param height Height of the board
	 * @param exitx Exit position x of the board
	 * @param exity Exit position y of the board
	 */
	public RushHour( ArrayList<RushHourCar> cars, int width, int height, int exitx, int exity ) {
		this.cars = cars;
		this.width = width;
		this.height = height;
		setExit(new Point( exitx, exity ));
		if( cars.size() > 0 ) {
			keyCar = cars.get(cars.size() - 1);
		}
		if( originalConfig == null ) {
			setOriginal();
		}
	}
	
	/**
	 * Creates a new instance of RushHour with the information given
	 * @param width The width of the board
	 * @param height The height of the board
	 * @param exitx The exit x position of the board
	 * @param exity The exit y position of the board
	 */
	public RushHour( int width, int height, int exitx, int exity ) {
		this( new ArrayList<RushHourCar>(), width, height, exitx, exity );
	}
	
	/**
	 * Sets the current config to the original config
	 * The original config is used when people request a game reset
	 */
	public void setOriginal() {
		originalConfig = new RushHour(this);
	}
	
	/**
	 * Adds a car to car list
	 * @param car
	 */
	public void addCar(RushHourCar car) {
		cars.add(car);
	}
	
	/**
	 * Sets the key car, key car is the car that is trying to get to
	 * the exit
	 * @param car The car that is the keyCar. NOTE: This car should also exist in the cars list
	 */
	public void setKeyCar( RushHourCar car) {
		setKeyCar( car, false );
	}
	
	/**
	 * Returns the key car, the car that is trying to get to the exit
	 * @return Returns the keyCar
	 */
	public RushHourCar getKeyCar() {
		return keyCar;
	}
	
	/**
	 * Sets the key car and sets the goal to the far right of the key car
	 * @param car Car to change the key car to
	 * @param changeGoal Whether or not to change the goal
	 */
	public void setKeyCar( RushHourCar car, boolean changeGoal ) {
		keyCar = car;
		if( changeGoal ) {
			if( car.isHorizontal() ) {
				int gy = car.getBlocks().get(0).y;
				// Just copy the y position of the car, and put it on the
				// Far right of the board
				Point new_exit = new Point( this.width-1, gy );
				this.setExit(new_exit);
			}
		}
	}
	
	/**
	 * Checks if the current configuration matches the goal
	 */
	public Boolean isGoal() {
		ArrayList< Point > cBlocks = keyCar.getBlocks();
		for( Point p : cBlocks ) {
			if( p.equals(getExit()) ) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get's the neighbors of the current config.
	 * This get neighbors goes through each car and asks
	 * it to return it's valid moves then adds them to the
	 * list
	 */
	public ArrayList<Puzzle> getNeighbors() {
		ArrayList<Puzzle> new_configs = new ArrayList<Puzzle>(); // New List of configs
		for( RushHourCar c : cars ) { // For each car
			
			// Make a copy of all the cars (So you can remove the current one for validation)
			ArrayList< RushHourCar > current_cars = new ArrayList<RushHourCar>(getCars());
			
			current_cars.remove( c ); // Remove THIS car from the cars (So it can be moved)
			
			// Get valid moves for this car from this car
			ArrayList<RushHourCar> validMoves = c.getValidMoves(current_cars, width, height);
			
			// For each valid move, add another neighbor
			for( RushHourCar validMove : validMoves ) {
				//Make a new one
				RushHour newConfig = new RushHour( new ArrayList<RushHourCar>(current_cars), width, height, getExit().x, getExit().y );
				
				//Reset the keycar
				newConfig.setKeyCar( keyCar );
				
				//Add the current moving car back in
				newConfig.addCar( validMove );
				
				// If the current car WAS the keyCar, then make it the keyCar again in the config
				if( c == keyCar ) {
					newConfig.setKeyCar(validMove);
				}
				
				// Add it to the neighbors list				
				new_configs.add( newConfig );
			}
		}
		return new_configs;
	}
	
	/**
	 * Gets the config for later use
	 */
	public Object getConfig() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Gets the car list
	 * @return ArrayList<RushHourCar> cars
	 */
	public ArrayList<RushHourCar> getCars() {
		return cars;
	}
	
	/**
	 * Prints the config in a grid representation
	 */
	public void printConfig() {
		char[][] array = getCharRepresentation();
		for( int i = 0; i < width; i++ ) {
			for( int j = 0; j < height; j++ ) {
				System.out.print( array[j][i] );
			}
			System.out.println("");
		}
	}
	
	/**
	 * Returns a char[][] that represents the board
	 * @return char[][] Representation of the board
	 */
	private char[][] getCharRepresentation() {
		char[][] array = new char[width][height];
		for( int i = 0; i < width; i++ ) {
			for( int j = 0; j < height; j++ ) {
				array[i][j] = '.';
			}
		}
		for( RushHourCar c : cars ) {
			if( c == keyCar ) {
				c.printOnArray(array, '#');
			} else {
				c.printOnArray(array);
			}
		}
		return array;
	}
	
	/**
	 * Returns the char representation as a string
	 */
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
	
	/**
	 * checks if the two boards are equal by checking if their string representations are equal
	 * This won't work if the boards have different cars in the same places
	 */
	public boolean equals( Object o ) {
		if( o instanceof RushHour ) {
			RushHour r = (RushHour)o;
			if( r.toString().equals(this.toString()) ) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns hashCode for someone up top to use for storing this in a hash map/set
	 */
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	/**
	 * Moves the given car num to the given position, IFF the new position is valid
	 * @param carnum The car to move
	 * @param newx The new x position of the car
	 * @param newy The new y position of the car
	 */
	public void move( int carnum, int newx, int newy ) {
		RushHourCar carToMove = null; 
		for( RushHourCar car : cars ) {
			if( car.getCarnum() == carnum ) {
				carToMove = car;
				break;
			}
		}
		if( carToMove != null ) {
			cars.remove( carToMove );
			RushHourCar oldcar = new RushHourCar( carToMove );
			carToMove.setX1( newx );
			carToMove.setY1( newy );
			carToMove.setX2( newx );
			carToMove.setY2( newy );
			if( carToMove.isHorizontal() ) {
				carToMove.setX2( newx + carToMove.getBlocks().size() - 1 );
			} else {
				carToMove.setY2( newy + carToMove.getBlocks().size() - 1 );
			}

			carToMove.reassesBlocks();
			if( RushHourCar.isValid(carToMove, cars, width, height) ) {
				cars.add( carToMove );
			} else {
				if( carToMove == keyCar ) {
					keyCar = oldcar;
				}
				cars.add( oldcar );
			}
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * Resets the board to original
	 */
	public void reset() {
		changeTo( originalConfig );
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Moves the board one step closer to the goal
	 */
	public void cheat() {
		this.printConfig();
		ArrayList<Puzzle> solution = Solver.solve( this );
		for( Puzzle i : solution ) {
			((RushHour) i).printConfig();
		}
		if( solution != null && solution.size() > 1 ) {
			this.cars.clear();
			this.changeTo( (RushHour) solution.get(1) );
			
		} else {
			System.out.println( "Already Solved" );
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Sets the cars list to the given list
	 * @param cars The list to set the cars to
	 */
	public void setCars(ArrayList<RushHourCar> cars) {
		this.cars = cars;
	}

	/**
	 * Returns the width of the board
	 * @return int width of board
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width of the board to the given width
	 * @param width The width to change the width to
	 */
	private void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Gets the height of the board
	 * @return int the height of the board
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height of the board to the given height
	 * @param height The height of the board
	 */
	private void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * Gets the exit position of the board	
	 * @return Point the exit (Goal) of the board
	 */
	public Point getExit() {
		return exit;
	}

	/**
	 * Sets the exit to the given point
	 * @param exit Exit
	 */
	public void setExit(Point exit) {
		this.exit = exit;
	}

	/**
	 * Loads a board config from the given filename
	 * @param filename The file to load the board from
	 * @return RushHour a new board 
	 * @throws IllegalArgumentException
	 */
	public static RushHour loadFile(String filename) throws IllegalArgumentException {
        BufferedReader inputStream = null;

        RushHour new_board = new RushHour(0,0,0,0);
        
        try {
            inputStream = 
                new BufferedReader( new FileReader( filename ));

            String l;
            // Read first line for board size
            if( ( l = inputStream.readLine() ) != null ) {
            	String[] splitString = l.split(" ");
            	if( splitString.length != 2 ) {
            		throw new IllegalArgumentException("First line of file incorrect");
            	}
            	try {
            	new_board.setWidth( Integer.parseInt(splitString[0]) );
            	new_board.setHeight( Integer.parseInt(splitString[1]) );
            	} catch( NumberFormatException nfe ) {
            		throw new IllegalArgumentException("First line of file formatted incorectly");
            	}
            }
            
            int numCars = 0;
            // Read the number of cars line (Second line)
            if( ( l = inputStream.readLine() ) != null ) {
            	try {
            		numCars = Integer.parseInt( l );
            	} catch( NumberFormatException nfe ) {
            		throw new IllegalArgumentException("Second line of file formatted incorrectly");
            	}
            }
            
            int i = 0;
            try {
	            for( i = 0; i < numCars; i++ ) {
	            	if( (l = inputStream.readLine()) == null ) {
	            		throw new IllegalArgumentException("Incorrect number of lines in file");
	            	}
	            	String[] splitString = l.split(" ");
	            	if( splitString.length != 4 ) {
	            		throw new IllegalArgumentException( Integer.toString(i) + " line of file incorrect");
	            	}
	            	try {
	            		RushHourCar new_car = new RushHourCar( 	Integer.parseInt(splitString[0]),
	            												Integer.parseInt(splitString[1]),
	            												Integer.parseInt(splitString[2]), 
	            												Integer.parseInt(splitString[3]) );
		            	new_board.addCar( new_car );
		            	new_board.setKeyCar( new_car, true ); // Changes keyCar every time so the last one is the key car
	            	} catch( NumberFormatException nfe ) {
	            		throw new NumberFormatException(Integer.toString(i) + " line of file formatted incorectly");
	            	}
	            }
            } catch( NumberFormatException nfe ) {
            	throw new IllegalArgumentException(Integer.toString(i) + " line of file formatted incorrectly");
            }
            
        } catch( FileNotFoundException fnfe ) {
            throw new IllegalArgumentException("File not found");
        } catch( IOException ioe ) {
        	throw new IllegalArgumentException(ioe.getMessage());
        } finally {
            try {
                if ( inputStream != null ) inputStream.close();
            }
            catch( IOException ioe ) {
                System.out.println( ioe.getMessage() );
            }
        }
        new_board.setOriginal();
        return new_board;
	}
	
	/**
	 * Takes arguments and solves puzzle based on it
	 * @param args file
	 */
	public static void main(String[] args) {		
		if(args.length > 0) {
			try {
				Puzzle myPuzzle = loadFile( args[0] );	
				ArrayList< Puzzle > solution = Solver.solve(myPuzzle);
				
				if(solution != null) {
					int i = 0;
					for( Puzzle s : solution ) {
						System.out.println("Step " + Integer.toString(i++) + ": ");
						((RushHour) s).printConfig();
						System.out.println("");
					}
				} else {
					System.out.println("No solution.");	
				}
			} catch( IllegalArgumentException iae ) {
				System.out.println( iae.getMessage() );
			}
			
		} else {
			System.out.println("Usage: file");
		}
	}
}
