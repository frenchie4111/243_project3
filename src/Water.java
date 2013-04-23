import java.util.ArrayList;
import java.util.Arrays;

/* 
 * Java.java
 * 
 * Version:
 * $Id: Water.java,v 1.4 2013/04/12 16:27:54 p243-04d Exp $
 * 
 * Revisions:
 * $Log: Water.java,v $
 * Revision 1.4  2013/04/12 16:27:54  p243-04d
 * Code Complete. Needs design.txt and testing
 *
 * Revision 1.3  2013/04/10 19:39:04  p243-04d
 * Formatted print statements and added some comments.
 *
 * Needs to have some more comments added and some try submissions to be made.
 *
 * Revision 1.2  2013/04/10 17:42:08  p243-04d
 * Solving works. Needs testing and arguments. And print formating.
 *
 * Revision 1.1  2013/04/10 16:59:49  p243-04d
 * Initial Commit
 *
 *
 */

public class Water implements Puzzle {

	private int goal;		// Number of hours on clock
	private ArrayList<Integer> buckets; // Max capacity of each bucket
	private ArrayList<Integer> config; //  Current config

	/**
	 * Creates an instance of water
	 */
	public Water() {
		this.goal = 0;
		buckets = new ArrayList<Integer>();
		config = new ArrayList<Integer>();
	}
	
	/**
	 * Creates an instance of water
	 * @param goal the goal
	 * @param buckets the size of the buckets
	 */
	public Water(int goal, ArrayList<Integer> buckets) {
		this();
		this.goal = goal;
		this.buckets = buckets;
		for( int i = 0; i < buckets.size(); i++ ) {
			config.add( new Integer(0) );
		}
	}
	
	/**
	 * Creates an instance of water
	 * @param goal The goal fill
	 * @param buckets The size of the buckets
	 * @param config The new config (Fill ammount for each bucket)
	 */
	public Water(int goal, ArrayList<Integer> buckets, ArrayList<Integer> config) {
		this(goal, buckets);
		this.config = config;
	}
	
	/**
	 * Returns whether this config is the goal
	 * 
	 * @return - whether this config is goal
	 */
	public Boolean isGoal() {
		if( config.size() > 0 ) {
			for( Integer i : config ) {
				if( i == goal ) {
					return true;
				}
			}
		}
		return false;
	}

	
	/**
	 * Get's the neighbors of the given config
	 *  
	 * @param int config the current config to get the neighbors of
	 * @return  java.util.ArrayList<java.lang.Integer> List of neighbors
	 */
	public java.util.ArrayList<Puzzle> getNeighbors() {		
		ArrayList< ArrayList< Integer > > new_configs = new ArrayList< ArrayList< Integer > >();
		
		for( int current_bucket = 0; current_bucket < buckets.size(); current_bucket++ ) {
			if( config.get(current_bucket) > 0 ) { // If bucket has water	
				// Empty current bucket
				ArrayList<Integer> empty_config = new ArrayList<Integer>(config);
				empty_config.set( current_bucket, 0 );
				new_configs.add( empty_config );
				
				// Fill current bucket
				ArrayList<Integer> fill_config = new ArrayList<Integer>(config);
				fill_config.set( current_bucket, buckets.get(current_bucket) );
				new_configs.add( fill_config );
				
				// Empty into other ones
				for( int i = 0; i < buckets.size(); i++ ) {
					if( i != current_bucket ) {
						Integer free_space = buckets.get(i)-config.get(i); // Free space of new bucket
						ArrayList<Integer> pour_config = new ArrayList<Integer>(config);
						
						if( free_space >= config.get(current_bucket) ) {
							pour_config.set( current_bucket, 0 ); //Take out of old buckets
							pour_config.set( i, config.get(current_bucket) + config.get(i) ); //Put in new bucket
						} else {
							pour_config.set( current_bucket, config.get(current_bucket) - free_space ); //Take out of old buckets
							pour_config.set( i, buckets.get(i) ); //Put in new bucket
						}
						
						new_configs.add( pour_config );
					}
				}
			} else {
				// Fill current bucket
				ArrayList<Integer> fill_config = new ArrayList<Integer>(config);
				fill_config.set( current_bucket, buckets.get(current_bucket) );
				new_configs.add( fill_config );
			}
		}
		
		ArrayList<Puzzle> new_neighbors = new ArrayList<Puzzle>();
		for( ArrayList<Integer> new_config : new_configs ) {
			new_neighbors.add( new Water( goal, buckets, new_config ));
		}
		return new_neighbors;
	}
	
	/**
	 * Returns string representation of config
	 */
	public String toString() {
		String new_string = "";
		
		for( Integer i: config ) {
			new_string += i.toString() + " ";
		}
		
		return new_string;
	}

	/**
	 * Returns hashcode of to string for visited list
	 */
	public int hashCode() {
		return this.toString().hashCode();
	}

	/**
	 * Equals if arrays are equivelent
	 */
	public boolean equals( Object o ) {
		if( o instanceof Water ) {
			Water w2 = (Water) o;
			return this.config.equals( w2.getConfig() );
		}
		return false;
	}

	/**
	 * Get's the current config
	 */
	public ArrayList<Integer> getConfig() {
		return config;
	}
	
	/**
	 * Takes arguments and solves puzzle based on it
	 * @param args amount jug1 jug2
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
			System.out.println("Usage: java Water amount jug1 jug2 ...");
		}
	}
}
