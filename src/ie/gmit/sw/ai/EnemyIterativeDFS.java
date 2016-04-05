package ie.gmit.sw.ai;


import java.util.*;

import ie.gmit.sw.maze.Node;

public class EnemyIterativeDFS implements AI
{
	private Node[][] maze;
	private Node goal;
	private boolean keepRunning = true;
	private List<Node> finalList = new ArrayList<Node>();
	private List<Node> newList = new ArrayList<Node>();
	private Node finalNode = new Node(0, 0);

	public void traverse(Node[][] maze, Node start) 
	{
		
		this.maze = maze;
		int limit = 2;
		
		while(keepRunning)
		{
			dfs(start, 0, limit);
			
			if (keepRunning)
			{
	      		limit++;       		
	      		unvisit();			
			}
      	}
		
		go();
	}

	private void dfs(Node node, int depth, int limit)
	{
		if (!keepRunning || depth > limit) 
		{
			return;		
		}
		node.setVisited(true);	
		
		if (node == goal)
		{
			goal = node;
	        keepRunning = false;
			return;
		}
		
		ArrayList<Node> children = node.adjacentNodes(maze);
		
		for (Node child : children) 
		{
			if(child.getNodeType() == ' '  ||  child.getNodeType() == 'G' || child.getNodeType() == 'V'  ||  child.getNodeType() == 'E')
			{
				//System.out.println("VALID NODE, IT'S WORKING?");
				if (child != null && !child.isVisited())
				{
					child.setParent(node);
					finalList.add(child);
					dfs(child, depth + 1, limit);
				}
			}
		}
	} 
	public void go()
	{
		finalNode = null;
		Node oldNode;
		Node currentNode = goal;
		while(currentNode != null)
		{
			newList.add(currentNode);
			currentNode = currentNode.getParent();
		}
		for(int i = newList.size() -1 ; i >= 0 ; i--)
		{
			currentNode = newList.get(i);
			if(currentNode != null)
			{
				
				if(currentNode.getNodeType() == 'E' || currentNode.getNodeType() == 'F')
				{
					if(currentNode.getNodeType() == 'E')
					{
						currentNode.setPlayerHere(true);
					}
					finalNode = currentNode;
					break;
				}
				currentNode.setNodeType('V');
			}

			try 
			{ 
				//Simulate processing each expanded node
				Thread.sleep(500);
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			oldNode = currentNode;
			if(oldNode.getNodeType() != 'X' && oldNode.getNodeType() != 'G' && oldNode.getNodeType() != 'C' && oldNode.getNodeType() != 'F')
			{
				oldNode.setNodeType(' ');
			}
		}
		
		unvisit();
	}
		
	private void unvisit()
	{
		newList.clear();
		finalList.clear();
		for (int i = 0; i < maze.length; i++)
		{
			for (int j = 0; j < maze[i].length; j++)
			{
				maze[i][j].setVisited(false);
				maze[i][j].setParent(null);
			}
		}
	}
	public Node returnFinalNode()
	{
		return finalNode;	
	}
	@Override
	public void updateGoalNode(Node goal)
	{
		this.goal = goal;
		
	}
}
