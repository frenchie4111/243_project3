import java.util.ArrayList;

/* 
 * Clock.java
 * 
 * Version:
 * $Id: Clock.java,v 1.2 2013/04/10 19:39:04 p243-04d Exp $
 * 
 * Revisions:
 * $Log: Clock.java,v $
 * Revision 1.2  2013/04/10 19:39:04  p243-04d
 * Formatted print statements and added some comments.
 *
 * Needs to have some more comments added and some try submissions to be made.
 *
 * Revision 1.1  2013/04/10 16:59:49  p243-04d
 * Initial Commit
 *
 * Revision 1.1  2013/04/06 18:30:16  mdl7240
 * has errors
 *
 * Revision 1.6  2013/04/02 23:23:43  mdl7240
 * Fixed getNeighbors
 *
 * Revision 1.5  2013/04/02 23:23:06  mdl7240
 * Fixed output
 *
 * Revision 1.4  2013/04/02 23:19:42  mdl7240
 * My main method was in the wrong function, like a dumby
 *
 * Revision 1.3  2013/04/02 23:12:35  mdl7240
 * Docstrings should be done
 *
 * Revision 1.2  2013/04/02 23:10:36  mdl7240
 * Code needs a few more docstrings. Otherwise good
 *
 * Revision 1.1  2013/03/27 16:26:28  mdl7240
 * Added code needs comments and bounds checking
 *
 *
 */

public class Clock implements Puzzle {

	private int hours;		// Number of hours on clock
	private int startTime;	// Start time of clock
	private int goalTime;	// Goal time of clock
	private Integer config; //Current config

	public Clock() {
		this.hours = 12;
		this.startTime = 1;
		this.goalTime = 8;
		this.config = this.startTime;
	}
	
	public Clock(int hours, int startTime, int goalTime) {
		this();
		this.hours = hours;
		this.startTime = startTime;
		this.goalTime = goalTime;
		this.config = this.startTime;
	}
	
	public Clock(int hours, int startTime, int goalTime, Integer config) {
		this();
		this.hours = hours;
		this.startTime = startTime;
		this.goalTime = goalTime;
		this.config = config;
	}
	
	/**
	 * Get the goal config for this puzzle.
	 * 
	 * 
	 * @return: the goal config
	 */
	public int getGoal() {
		return goalTime;
	}

	/**
	 * Returns whether this config is the goal
	 * 
	 * @return - whether this config is goal
	 */
	public Boolean isGoal() {
		return (this.config.equals(this.getGoal()));
	}

	public Object getConfig() {
		return config;
	}
	
	/**
	 * Get's the neighbors of the given config
	 *  
	 * @param int config the current config to get the neighbors of
	 * @return  java.util.ArrayList<java.lang.Integer> List of neighbors
	 */
	public java.util.ArrayList<Puzzle> getNeighbors() {
		ArrayList<Puzzle> new_neighbors = new ArrayList<Puzzle>();
		
		int config = this.config;

		int new_left_neighbor = config - 1;
		int new_right_neighbor = config + 1;
		if(new_left_neighbor < 1) {
			new_left_neighbor = this.hours;
		}
		if(new_right_neighbor > this.hours) {
			new_right_neighbor = 1;
		}

		new_neighbors.add(new Clock(this.hours, this.startTime, this.goalTime, new_left_neighbor));
		new_neighbors.add(new Clock(this.hours, this.startTime, this.goalTime, new_right_neighbor));
		
		return new_neighbors;
	}
	
	/**
	 * Returns string representation of object
	 */
	public String toString() {
		return Integer.toString(config);
	}
	
	public static void main(String[] args) {
		if(args.length == 3) {
			int hours = -1, start = -1, end = -1;
			try {
				hours = Integer.parseInt(args[0]);
				start = Integer.parseInt(args[1]);
				end = Integer.parseInt(args[2]);
			} catch(Throwable t) {
				System.out.println("One of the arguments was not a number");
			}
			if(hours > 0 && start <= hours && start > 0 && end <= hours && end > 0) {
			
				Puzzle myPuzzle = (Puzzle) new Clock(hours, start, end);	
				ArrayList< Puzzle > solution = Solver.solve(myPuzzle);
				
				if(solution != null) {
					for( int i = 0; i < solution.size(); i++ ) {
						System.out.println("Step " + Integer.toString(i) + ": " + solution.get(i));
					}
				} else {
					System.out.println("No Solution...");
				}
			
			} else {
				System.out.println("Arguments invalid");
			}
		} else {
			System.out.println("Usage: java Clock hours start goal");
		}
	}
}
