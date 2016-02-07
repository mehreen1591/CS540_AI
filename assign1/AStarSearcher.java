import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
	 * @param maze initial maze.
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

		// FILL THIS METHOD

		// explored list is a Boolean array that indicates if a state associated with a given position in the maze has already been explored. 
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

		PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>();

		// TODO initialize the root state and add
		// to frontier list
		// ...
		frontier.add(new StateFValuePair(new State(maze.getPlayerSquare(), null, 0, 0), 0+getHueristicValue(maze.getPlayerSquare(), maze.getGoalSquare())));
		StateFValuePair expandedStateFValuePair = null;
		State prevState = null;
		while (!frontier.isEmpty()) {
			expandedStateFValuePair = frontier.poll();
			noOfNodesExpanded++;
			
			if(prevState!=null && expandedStateFValuePair.getState().getDepth() <= prevState.getDepth()){
				while(prevState!=null && prevState.getDepth()>=expandedStateFValuePair.getState().getDepth()){
					cost-=1;
					maze.setOneSquare(prevState.getSquare(), ' ');
					prevState = prevState.getParent();
				}
			}else{
				cost=expandedStateFValuePair.getState().getGValue();
			}
			if(expandedStateFValuePair.getState().getGValue()!=0 && !expandedStateFValuePair.getState().isGoal(maze)){
				maze.setOneSquare(expandedStateFValuePair.getState().getSquare(), '.');
			}
			
			explored[expandedStateFValuePair.getState().getX()][expandedStateFValuePair.getState().getY()] = true;
			ArrayList<State> childrenStates = expandedStateFValuePair.getState().getSuccessors(explored, maze);
			//Collections.reverse(childrenStates);
			for(State child : childrenStates){
				if(child!=null){
					updateStateFValuePair(child, frontier);
				}
			}
			maxDepthSearched = Math.max(expandedStateFValuePair.getState().getDepth(), maxDepthSearched);
			if(expandedStateFValuePair.getState().isGoal(maze)){
				return true;
			}
			
			maxSizeOfFrontier = Math.max(maxSizeOfFrontier, frontier.size());
			// TODO return true if a solution has been found
			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			// TODO update the maze if a solution found

			// use frontier.poll() to extract the minimum stateFValuePair.
			// use frontier.add(...) to add stateFValue pairs
			prevState = expandedStateFValuePair.getState();
		}
		return false;
	}

	private void updateStateFValuePair(State child, PriorityQueue<StateFValuePair> frontier) {
		StateFValuePair pooledPair = frontier.poll();
		ArrayList<StateFValuePair> pool = new ArrayList<StateFValuePair>();
		boolean found = false;
		while(pooledPair!=null){
			if(pooledPair.getState().getX() == child.getX() && pooledPair.getState().getY() == child.getY()){
				found = true;
				if(child.getGValue() < pooledPair.getState().getGValue()){
					pooledPair = new StateFValuePair(child, child.getGValue()+getHueristicValue(child.getSquare(), maze.getGoalSquare()));
					frontier.add(pooledPair);
					break;
				}
			}else{
				pool.add(pooledPair);
			}
			pooledPair = frontier.poll();
		}
		if(!found){
			frontier.add(new StateFValuePair(child, child.getGValue()+getHueristicValue(child.getSquare(), maze.getGoalSquare())));
		}
		for(int i=0; i<pool.size(); i++){
			frontier.add(pool.get(i));
		}
	}

	private int getHueristicValue(Square playerSquare, Square goalSquare) {
		int cost = 0;
		cost = Math.abs(playerSquare.X - goalSquare.X) + Math.abs(playerSquare.Y - goalSquare.Y);
		return cost;
	}

}
