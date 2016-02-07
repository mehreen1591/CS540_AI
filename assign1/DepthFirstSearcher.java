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
		State prevState = null;
		while (!stack.isEmpty()) {
			expandedState = stack.pop();
			noOfNodesExpanded++;
			if(prevState!=null && expandedState.getDepth() <= prevState.getDepth()){
				while(prevState!=null && prevState.getDepth()>=expandedState.getDepth()){
					cost-=1;
					maze.setOneSquare(prevState.getSquare(), ' ');
					prevState = prevState.getParent();
				}
			}else{
				cost=expandedState.getGValue();
			}
			if(expandedState.getGValue()!=0 && !expandedState.isGoal(maze)){
				maze.setOneSquare(expandedState.getSquare(), '.');
			}
			maxDepthSearched = Math.max(expandedState.getDepth(), maxDepthSearched);
			explored[expandedState.getX()][expandedState.getY()] = true;
			if(expandedState.isGoal(maze)){
				return true;
			}
			
			ArrayList<State> childrenStates = expandedState.getSuccessors(explored, maze);
			
			for(State child : childrenStates){
				int index = getIndexOfChild(child, stack);
				if(index!=-1){
					/*State oldChild = stack.get(index);
					if(child.getGValue() < oldChild.getGValue()){
						stack.set(index, child);
					}*/
				}else{
					stack.push(child);
				}
				
			}
			maxSizeOfFrontier = Math.max(maxSizeOfFrontier, stack.size());
			// TODO return true if find a solution
			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			// TODO update the maze if a solution found

			// use stack.pop() to pop the stack.
			// use stack.push(...) to elements to stack
			prevState = expandedState;
		}
		return false;

		// TODO return false if no solution
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
