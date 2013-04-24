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
	
	public RushHour( RushHour oldrh ) {
		changeTo( oldrh );
	}
	
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
	
	public void setOriginal() {
		originalConfig = new RushHour(this);
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
	
	public void setKeyCar( RushHourCar car) {
		setKeyCar( car, false );
	}
	
	public RushHourCar getKeyCar() {
		return keyCar;
	}
	
	public void setKeyCar( RushHourCar car, boolean changeGoal ) {
		keyCar = car;
		if( changeGoal ) {
			if( car.isHorizontal() ) {
				int gy = car.getBlocks().get(0).y;
				Point new_exit = new Point( this.width-1, gy );
				this.setExit(new_exit);
			}
		}
	}
	
	@Override
	public Boolean isGoal() {
		ArrayList< Point > cBlocks = keyCar.getBlocks();
		for( Point p : cBlocks ) {
			if( p.equals(getExit()) ) {
				return true;
			}
		}
		return false;
	}

	@Override
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

	public void reset() {
		changeTo( originalConfig );
		setChanged();
		notifyObservers();
	}
	
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
	
	public Point getExit() {
		return exit;
	}

	public void setExit(Point exit) {
		this.exit = exit;
	}

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
