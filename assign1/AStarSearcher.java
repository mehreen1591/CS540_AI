import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * A* algorithm search
 * 
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze
	 *            initial maze.
	 */
	public AStarSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main a-star search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];

		for (int i = 0; i < explored.length; i++) {
			for (int j = 0; j < explored[i].length; j++) {
				explored[i][j] = false;
			}
		}

		for (int i = 0; i < maze.getNoOfRows(); i++) {
			for (int j = 0; j < maze.getNoOfCols(); j++) {
				if (maze.getSquareValue(i, j) == 'S') {
					break;
				}
			}
		}
		PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>();

		frontier.add(new StateFValuePair(new State(maze.getPlayerSquare(), null, 0, 0),
				0 + getHueristicValue(maze.getPlayerSquare(), maze.getGoalSquare())));

		maxSizeOfFrontier = 1;

		StateFValuePair expandedStateFValuePair = null;
		while (!frontier.isEmpty()) {
			expandedStateFValuePair = frontier.poll();
			
			noOfNodesExpanded++;
			cost = expandedStateFValuePair.getState().getGValue();
			
			explored[expandedStateFValuePair.getState().getX()][expandedStateFValuePair.getState().getY()] = true;
			ArrayList<State> childrenStates = expandedStateFValuePair.getState().getSuccessors(explored, maze);
			for (State child : childrenStates) {
				updateStateFValuePair(child, frontier);
			}
			
			maxDepthSearched = Math.max(expandedStateFValuePair.getState().getDepth(), maxDepthSearched);
			maxSizeOfFrontier = Math.max(frontier.size(), maxSizeOfFrontier);
			
			if (expandedStateFValuePair.getState().isGoal(maze)) {
				State parentGoal = expandedStateFValuePair.getState().getParent();
				while (parentGoal != null && parentGoal.getGValue() != 0) {
					maze.setOneSquare(parentGoal.getSquare(), '.');
					parentGoal = parentGoal.getParent();
				}
				return true;
			}
		}
		return false;
	}

	private void updateStateFValuePair(State child, PriorityQueue<StateFValuePair> frontier) {
		Object[] frontArr = frontier.toArray();
		boolean found = false;
		for (int i = 0; i < frontArr.length; i++) {
			StateFValuePair pair = (StateFValuePair) frontArr[i];
			if(pair.getState().getX() == child.getX() && pair.getState().getY() == child.getY()){
				found = true;
				if (child.getGValue() < pair.getState().getGValue()) {
					StateFValuePair childPair = new StateFValuePair(child,
							child.getGValue() + getHueristicValue(child.getSquare(), maze.getGoalSquare()));
					frontier.remove(pair);
					frontier.add(childPair);
					break;
				}
			}
		}
		if(!found){
			StateFValuePair childPair = new StateFValuePair(child,
					child.getGValue() + getHueristicValue(child.getSquare(), maze.getGoalSquare()));
			frontier.add(childPair);
		}
	}

	private int getHueristicValue(Square playerSquare, Square goalSquare) {
		int cost = 0;
		cost = Math.abs(playerSquare.X - goalSquare.X) + Math.abs(playerSquare.Y - goalSquare.Y);
		return cost;
	}
}
