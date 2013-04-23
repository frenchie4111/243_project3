/* 
 * Puzzle.java
 * 
 * Version:
 * $Id: Puzzle.java,v 1.2 2013/04/10 19:39:04 p243-04d Exp $
 * 
 * Revisions:
 * $Log: Puzzle.java,v $
 * Revision 1.2  2013/04/10 19:39:04  p243-04d
 * Formatted print statements and added some comments.
 *
 * Needs to have some more comments added and some try submissions to be made.
 *
 * Revision 1.1  2013/04/10 16:59:48  p243-04d
 * Initial Commit
 *
 * Revision 1.1  2013/04/06 18:30:15  mdl7240
 * has errors
 *
 * Revision 1.1  2013/03/27 16:26:28  mdl7240
 * Added code needs comments and bounds checking
 *
 *
 */

public interface Puzzle {
	
	/**
	 * Returns whether this config is the goal
	 * 
	 * @return - whether this config is goal
	 */
	public Boolean isGoal();

	/**
	 * Returns the neighbors list for this puzzle given a config
	 * 
	 * @param config config to find neighbors of
	 * @return list of new configs
	 */
	public java.util.ArrayList<Puzzle> getNeighbors();

	/**
	 * Returns the objects config. Implemented per puzzle.
	 * @return Object the objects config
	 */
	public Object getConfig();
}