package ie.gmit.sw.playeritems;

import java.util.*;

import ie.gmit.sw.maze.Node;

public class Bomb
{
	//The bomb class uses a DFS to kill all enemies with in the set amount of nodes
	// Amount is set in the Runner
	private Node[][] maze;
	private int limit;
	private boolean keepRunning = true;
	private List<Node> finalList = new ArrayList<Node>();
	
	public Bomb(int limit){
		this.limit = limit;
	}
	
	public void traverse(Node[][] maze, Node node) {
		this.maze = maze;
		System.out.println("Search with limit " + limit);
		dfs(node, 1);
		unvisit();
	}
	
	private void dfs(Node node, int depth)
	{
		if (!keepRunning || depth > limit) 
		{
			return;
		}
		
		node.setVisited(true);	

		ArrayList<Node> children = node.adjacentNodes(maze);
		
		for (Node child : children) 
		{
			if(child.getNodeType() == ' '  ||  child.getNodeType() == 'G' || child.getNodeType() == 'V'  ||  child.getNodeType() == 'E')
			{
				if (child != null && !child.isVisited())
				{	
					child.setParent(node);
					finalList.add(child);

					dfs(child, limit + 1);
				}
			}
		}
	}
	private void unvisit()
	{
		
		for(Node n : finalList)
		{
			System.out.println(n.toString());
			n.setNodeType('F');
		}
		try 
		{ 
			//Simulate processing each expanded node
			Thread.sleep(250);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		for(Node n : finalList)
		{
			
			n.setNodeType(' ');
		}
		for (int i = 0; i < maze.length; i++)
		{
			for (int j = 0; j < maze[i].length; j++)
			{
				maze[i][j].setVisited(false);
				maze[i][j].setParent(null);
			}
		}
		finalList.clear();
	}
}
