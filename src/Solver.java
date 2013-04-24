import java.util.ArrayList;
import java.util.HashSet;

/* 
 * Clock.java
 * 
 * Version:
 * $Id: Solver.java,v 1.1 2013/04/10 16:59:50 p243-04d Exp $
 * 
 * Revisions:
 * $Log: Solver.java,v $
 * Revision 1.1  2013/04/10 16:59:50  p243-04d
 * Initial Commit
 *
 * Revision 1.1  2013/04/06 18:30:16  mdl7240
 * has errors
 *
 * Revision 1.3  2013/04/02 23:19:42  mdl7240
 * My main method was in the wrong function, like a dumby
 *
 * Revision 1.2  2013/04/02 23:10:36  mdl7240
 * Code needs a few more docstrings. Otherwise good
 *
 * Revision 1.1  2013/03/27 16:26:28  mdl7240
 * Added code needs comments and bounds checking
 *
 *
 */

public class Solver {
	
	public static HashSet<Puzzle> visited = new HashSet<Puzzle>();

	/**
	 * Solves the give puzzle using a generic backtracking algorithm
	 * 
	 * @param myPuzzle - Puzzle - The puzzle to solve
	 * @return ArrayList<Integer> - Solution to puzzle
	 */
	public static ArrayList< Puzzle > solve(Puzzle myPuzzle) {
		visited.clear();
		ArrayList< ArrayList< Puzzle > > queue = new ArrayList< ArrayList< Puzzle > >();
		ArrayList< Puzzle > current = new ArrayList< Puzzle >();
		current.add(myPuzzle);
		queue.add(current);
		//Boolean variable to tell if the problem has been solved yet or not
		boolean found = myPuzzle.isGoal();
		
		while( !queue.isEmpty() && !found) {
			current = queue.remove(0);
			ArrayList<Puzzle> neighbors = current.get( current.size() - 1 ).getNeighbors();

			for(Puzzle newConfig : neighbors) {

				if( !visited.contains(newConfig) ) {
					visited.add(newConfig);
					ArrayList<Puzzle> newCurrent = new ArrayList<Puzzle>(current);
					newCurrent.add(newConfig);

					if( newConfig.isGoal() ) {
						current = newCurrent;
						found = true;
						break;
					}
					else {
						queue.add(newCurrent);
					}
				}
			}
		}
		if(found) {
			return current;
		}
		return null;
	}
}
