import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Depth-First Search (DFS)
 * 
 * You should fill the search() method of this class.
 */
public class DepthFirstSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public DepthFirstSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main depth first search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
		// FILL THIS METHOD

		// explored list is a 2D Boolean array that indicates if a state associated with a given position in the maze has already been explored.
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];

		for(int i=0; i<explored.length; i++){
			for(int j=0; j<explored[i].length; j++){
				explored[i][j] = false;
			}
		}
		
		for(int i=0; i<maze.getNoOfRows(); i++){
			for(int j=0; j<maze.getNoOfCols(); j++){
				if(maze.getSquareValue(i, j) == 'S'){
					break;
				}
			}
		}
		// ...

		// Stack implementing the Frontier list
		LinkedList<State> stack = new LinkedList<State>();
		
		stack.add(new State(maze.getPlayerSquare(), null, 0, 0));

		State expandedState = null;
		while (!stack.isEmpty()) {
			expandedState = stack.pop();
			
			explored[expandedState.getX()][expandedState.getY()] = true;
			ArrayList<State> childrenStates = expandedState.getSuccessors(explored, maze);
			
			for(State child : childrenStates){
				stack.add(child);
			}
			// TODO return true if find a solution
			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			// TODO update the maze if a solution found

			// use stack.pop() to pop the stack.
			// use stack.push(...) to elements to stack
		}
		return false;

		// TODO return false if no solution
	}
}
