package ie.gmit.sw.characters;

import ie.gmit.sw.ai.AI;
import ie.gmit.sw.ai.AStarEnemy;
import ie.gmit.sw.ai.EnemyIterativeDFS;
import ie.gmit.sw.maze.Node;

public class Enemy 
{
	public enum SearchType {ASTAR, ITERDFS};
	private boolean isAlive = true;
	private Player player;
	private SearchType search;
	private Node currentPos;
	private Node endNode;
	private Node[][] maze;
	private AI hunter = null;
	
	public Enemy(Player player, SearchType search, Node startNode, Node[][] maze)
	{
		setPlayer(player);
		endNode = player.getCurrentNode();
		setSearchType(search);
		currentPos = startNode;
		this.maze = maze.clone();
	}
	
	public Node getCurrentPos() {
		return currentPos;
	}
	public void setCurrentPos(Node currentPos)
	{
		this.currentPos = currentPos;
		
		if(currentPos.isPlayerHere())
		{
			player.fight();
			Thread.currentThread().interrupt();
		}
		else if(currentPos.getNodeType() == 'F')
		{
			Thread.currentThread().interrupt();
		}
		hunt();
	}
	public boolean isAlive() 
	{
		return isAlive;
	}
	public void setAlive(boolean isAlive) 
	{
		this.isAlive = isAlive;
	}
	public Player getPlayer() 
	{
		return player;
	}
	public void setPlayer(Player playerPos) 
	{
		this.player = playerPos;
	}
	public SearchType getSearchType() 
	{
		return search;
	}
	public void setSearchType(SearchType searchType) 
	{
		this.search = searchType;
	}
	public void initHunterEnemy()
	{
		if(search == SearchType.ASTAR)
		{
			hunter = new AStarEnemy(player.getCurrentNode());
		}
		else if(search == SearchType.ITERDFS)
		{
			hunter = new EnemyIterativeDFS();
		}
		hunt();
	}
	public void hunt()
	{
		updatePlayerPos();
		hunter.traverse(maze, currentPos);
		setCurrentPos(hunter.returnFinalNode());
	}
	public void updatePlayerPos()
	{
		//Retrieves the player position and sets it as the enemy's goal node, so we can use the same AI for the player
		// And the enemy
		hunter.updateGoalNode(player.getCurrentNode());
	}
}