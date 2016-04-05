package ie.gmit.sw.maze;

public interface MazeGenerator 
{
	public Node[][] getMaze();
	public void createMaze(int rows , int cols);
	public Node getGoalNode();
}