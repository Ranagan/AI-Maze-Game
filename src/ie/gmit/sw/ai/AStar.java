package ie.gmit.sw.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import ie.gmit.sw.maze.Node;

public class AStar implements AI
{
	
	private Node goal;
	private List<Node> closed = new ArrayList<Node>();
	private List<Node> finalList = new ArrayList<Node>();
	private List<Node> allNodes = new ArrayList<Node>();
	private PriorityQueue<Node> open = new PriorityQueue<Node>(20, (Node current, Node next)-> (current.getPathCost() + current.getHeuristic(goal)) - (next.getPathCost() + next.getHeuristic(goal)));
	
	public AStar(Node goal)
	{
		updateGoalNode(goal);
	}
	public void traverse(Node[][] maze, Node node)
	{	
        maze = maze.clone();
		open.offer(node);
		node.setPathCost(0);			
		while(!open.isEmpty())
		{
			node = open.poll();		
			closed.add(node);
			node.setVisited(true);	
			
			if (node.equals(goal))
			{
				System.out.println("GOAL NODE FOUND");
				break;
			}
			//Process next nodes
			ArrayList<Node> children = node.adjacentNodes(maze);
			for (Node child : children) 
			{
				allNodes.add(child);
				if(child.getNodeType() != 'X' && child.getNodeType() != 'W' && child.getNodeType() != 'B' && child.getNodeType() != 'H'&& child.getNodeType() != '?')//(child.getNodeType() == ' '  ||  child.getNodeType() == 'G' ||  child.getNodeType() == 'V' ||  child.getNodeType() == 'E')
				{
					int score = node.getPathCost() + 1 + child.getHeuristic(goal);
					int existing = child.getPathCost() + child.getHeuristic(goal);
					if ((open.contains(child) || closed.contains(child)) && existing < score)
					{
						continue;
					}
					else
					{
						open.remove(child);
						closed.remove(child);
						child.setParent(node);
						finalList.add(child);
						child.setPathCost(node.getPathCost() + 1);
						open.add(child);
					}
				}		
			}
		}
	}
	
	public void clearAll()
	{
		for(Node n : allNodes)
		{
			n.setParent(null);
			n.setVisited(false);
		}
		closed.clear();
		finalList.clear();
		open.clear();
	}
	public List<Node> returnList()
	{
		return finalList;
	}
	@Override
	public void updateGoalNode(Node goal) {
		this.goal = goal;
		
	}
	@Override
	public Node returnFinalNode() {
		// TODO Auto-generated method stub
		return null;
	}
}
