package ie.gmit.sw.maze;

public class BinaryTreeMaze implements MazeGenerator
{
	//Tested using binary tree maze
	// The maze generated was far too spread out and didn't really look like a maze
	// Not good for this task
	private Node [][] maze;
	public BinaryTreeMaze() 
	{

	}

	public void createMaze(int rows, int cols)
	{
		maze = new Node[rows][cols];
		init();
		
		int featureNumber = (int)((rows * cols) * 0.01);	
		addFeature('W', 'X', featureNumber);
		addFeature('?', 'X', featureNumber);
		addFeature('B', 'X', featureNumber);
		addFeature('H', 'X', featureNumber);
		
		for (int row = 1; row < maze.length -1 ; row ++)
		{
			for (int col = 1; col < maze[row].length -1; col+= 2)
			{
				int num = (int) (Math.random() * 10);
				
				if (col > 0 && (num >= 5))
				{
					maze[row][col].addPath(Node.Direction.West);
					maze[row + 1][col].setNodeType(' ');
				}
				else
				{
					maze[row][col].addPath(Node.Direction.North);
					maze[row][col].setNodeType(' ');
					maze[row][col + 1].setNodeType(' ');
					maze[row][col - 1].setNodeType(' ');
				}
			}
		}
	}
	
	private void init()
	{
		for (int row = 0; row < maze.length; row++)
		{
			for (int col = 0; col < maze[row].length; col++)
			{
				maze[row][col] = new Node(row, col);
				maze[row][col].setNodeType('X');
			}
		}
	}

	private void addFeature(char feature, char replace, int number)
	{
		int counter = 0;
		while (counter < number)
		{
			int row = (int)(maze.length * Math.random());
			int col = (int) (maze[0].length * Math.random());
			
			if (maze[row][col].getNodeType() != ' ')
			{
				maze[row][col].setNodeType(feature);
				counter++;
			}
		}
	}
	public Node[][] getMaze()
	{
		return this.maze;
	}

	@Override
	public Node getGoalNode() {
		// TODO Auto-generated method stub
		return null;
	}
}