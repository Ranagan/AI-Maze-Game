package ie.gmit.sw.characters;

import ie.gmit.sw.maze.Node;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class Player 
{
	private Node currentNode;
	private int health = 100;
	private boolean isArmed;
	private int weaponStrength;
	
	public Node getCurrentNode()
	{
		return currentNode;
	}
	public void setCurrentNode(Node currentNode) 
	{
		this.currentNode = currentNode;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public boolean isArmed() {
		return isArmed;
	}
	public void setArmed(boolean isArmed) {
		this.isArmed = isArmed;
	}
	public int getWeaponStrength() {
		return weaponStrength;
	}
	public void setWeaponStrength(int weaponStr) {
		this.weaponStrength = weaponStr;
	}
	public void fight()
	{
		String fileName = "FCL/fightsOutcome.fcl";
        FIS fis = FIS.load(fileName,true);

        //if something wrong with file
        if( fis == null )
        { 
            System.err.println("Can't load file: '" + fileName + "'");
            return;
        }
        FunctionBlock functionBlock = fis.getFunctionBlock("fight");

        // Set inputs
        fis.setVariable("health", this.health);
        fis.setVariable("weaponStrength", this.weaponStrength);

        // Evaluate the outcome, hopefully things go well
        fis.evaluate();
        
        // Retrieve the damage output
        Variable damage = functionBlock.getVariable("damage");
        
        
        
        this.health -= damage.getValue();
        //If the health reaches 0, you have died
        if(health <= 0)
        {
        	System.out.println("YOU HAVE DIED");
        	System.exit(0);
        }
        System.out.println(health);
        this.weaponStrength = 0;
        setArmed(false);
	}
}
