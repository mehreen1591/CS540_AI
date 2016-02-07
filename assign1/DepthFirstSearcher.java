import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

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
		LinkedList<State> stack = new LinkedList<State>();
		
		stack.add(new State(maze.getPlayerSquare(), null, 0, 0));
		maxSizeOfFrontier = Math.max(maxSizeOfFrontier, stack.size());

		State expandedState = null;
		while (!stack.isEmpty()) {
			expandedState = stack.pop();
			noOfNodesExpanded++;
			cost=expandedState.getGValue();
			maxDepthSearched = Math.max(expandedState.getDepth(), maxDepthSearched);
			explored[expandedState.getX()][expandedState.getY()] = true;
			if(expandedState.isGoal(maze)){
				State parentGoal = expandedState.getParent();
				while (parentGoal != null && parentGoal.getGValue() != 0) {
					maze.setOneSquare(parentGoal.getSquare(), '.');
					parentGoal = parentGoal.getParent();
				}
				return true;
			}
			
			ArrayList<State> childrenStates = expandedState.getSuccessors(explored, maze);
			
			for(State child : childrenStates){
				int index = getIndexOfChild(child, stack);
				if(index==-1){
					stack.push(child);
				}
				
			}
			maxSizeOfFrontier = Math.max(maxSizeOfFrontier, stack.size());
		}
		return false;
	}

	private int getIndexOfChild(State child, LinkedList<State> stack) {
		for(int i=0; i<stack.size(); i++){
			if(child.getX() == stack.get(i).getX() && child.getY() == stack.get(i).getY()){
				return i;
			}
		}
		return -1;
	}
}
