import java.util.ArrayList;
/**
 * This class represents a node in every search algorithm
 * @author Edut Cohen
 *
 */
public class Node {

	private Board state;
	private Node parent;
	private int depth; //for bfs
	private int key; //for comparator
	private String step;
	private int h,g,f, cost;
	private String mark;

	/**
	 * constructor
	 * @param s
	 * @param p
	 * @param st
	 * @param price
	 */
	public Node (Board s, Node p, String st, int price) {
		cost=price;
		state=s;
		parent=p;
		if(p!=null) 
			depth=p.getDepth()+1;
		else depth=0;
		step=st;
		heuristic();
		fcalculate();
		mark="";
	}

	/** 
	 * checks whether the node is a goal state
	 * @return
	 */
	public boolean isGoal(){
		for(int i=0; i<this.state.height; i++) {
			for (int j = 0; j < this.state.width; j++) {
				if((i!=this.state.height-1 || j!=this.state.width-1)
						&& (this.state.matrix[i][j].getNum() != (this.state.width*i+j+1)))
					return false;
			}
		}
		return true;
	}

	/**
	 * if possible returns a Node created by main Node by a down movement
	 * @return
	 */
	public Node exploreDown() {
		if(this.canBeExploredD()) {
			int val= this.state.matrix[this.state.hBlank-1][this.state.wBlank].getNum();
			int stepCost;
			if(this.state.matrix[this.state.hBlank-1][this.state.wBlank].getColor()=="red")
				stepCost=30;
			else stepCost=1;
			Board myState=new Board(this.state);
			myState.moveDown();
			Node n= new Node(myState, this, val+"D-", this.cost+stepCost);
			n.state.hBlank--;
			return n;
		}
		return null;
	}

	/**
	 * checks if down son is legal
	 * @return
	 */
	private boolean canBeExploredD() {
		if(this.state.hBlank==0)
			return false;
		if(this.state.matrix[this.state.hBlank-1][this.state.wBlank].getColor()=="black")
			return false;
		if(this.parent!=null && this.step.contains("U"))
			return false;
		return true;
	}

	/**
	 * if possible returns a Node created by main Node by a right movement
	 * @return
	 */
	public Node exploreRight() {
		if(this.canBeExploredR()) {

			if(depth==1);
			int val= this.state.matrix[this.state.hBlank][this.state.wBlank-1].getNum();
			int stepCost;
			if(this.state.matrix[this.state.hBlank][this.state.wBlank-1].getColor()=="red")
				stepCost=30;
			else stepCost=1;
			Board myState=new Board(this.state);
			myState.moveRight();
			Node n= new Node(myState, this, val+"R-",this.cost+stepCost);
			n.state.wBlank--;
			return n;
		}
		return null;
	}

	/**
	 * checks if right son is legal
	 * @return
	 */
	private boolean canBeExploredR() {
		if(this.state.wBlank==0)
			return false;
		if(this.state.matrix[this.state.hBlank][this.state.wBlank-1].getColor()=="black")
			return false;
		if(this.parent!=null && this.step.contains("L"))
			return false;
		return true;
	}

	/**
	 * if possible returns a Node created by main Node by an up movement
	 * @return
	 */
	public Node exploreUp() {
		if(this.canBeExploredU()) {
			int val= this.state.matrix[this.state.hBlank+1][this.state.wBlank].getNum();
			int stepCost;
			if(this.state.matrix[this.state.hBlank+1][this.state.wBlank].getColor()=="red")
				stepCost=30;
			else stepCost=1;
			Board myState=new Board(this.state);
			myState.moveUp();
			Node n= new Node(myState, this, val+"U-", this.cost+stepCost);
			n.state.hBlank++;
			return n;
		}
		return null;
	}

	/**
	 * checks if up son is legal
	 * @return
	 */
	private boolean canBeExploredU() {
		if(this.state.hBlank==this.state.height-1)
			return false;
		if(this.state.matrix[this.state.hBlank+1][this.state.wBlank].getColor()=="black")
			return false;
		if(this.parent!=null && this.step.contains("D"))
			return false;
		return true;
	}

	/**
	 * if possible returns a Node created by main Node by a left movement
	 * @return
	 */
	public Node exploreLeft() {
		if(this.canBeExploredL()) {
			int val= this.state.matrix[this.state.hBlank][this.state.wBlank+1].getNum();
			int stepCost;
			if(this.state.matrix[this.state.hBlank][this.state.wBlank+1].getColor()=="red")
				stepCost=30;
			else stepCost=1;
			Board myState=new Board(this.state);
			myState.moveLeft();
			Node n= new Node(myState, this, val+"L-", this.cost+stepCost);
			n.state.wBlank++;
			return n;
		}
		return null;
	}

	/**
	 * checks if left son is legal
	 * @return
	 */
	private boolean canBeExploredL() {
		if(this.state.wBlank==this.state.width-1)
			return false;
		if(this.state.matrix[this.state.hBlank][this.state.wBlank+1].getColor()=="black")
			return false;
		if(this.parent!=null && this.step.contains("R")) {
			return false;
		}
		return true;
	}

	/**
	 * returns a list of all legal sons of the main node by the given order
	 * @return
	 */
	public ArrayList<Node> expendAll(){
		ArrayList<Node> sons= new ArrayList<Node>();
		Node l=this.exploreLeft();
		if (l!=null)
			sons.add(l);
		Node u=this.exploreUp();
		if (u!=null)
			sons.add(u);
		Node r=this.exploreRight();
		if (r!=null)
			sons.add(r);
		Node d=this.exploreDown();
		if (d!=null)
			sons.add(d);
		return sons;
	}

	/**
	 * returns a string that represents the path from root node to this node
	 * @return
	 */
	public String path() {
		if(this.parent==null)
			return "";
		String s=this.step;
		return this.parent.path()+s;
	}


	/*
	 * getters & setters
	 */
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public Board getState() {
		return state;
	}
	public void setState(Board state) {
		this.state = state;
	}
	public int getF() {
		return f;
	}
	public void setF(int f) {
		this.f = f;
	}
	public int getG() {
		return g;
	}
	public void setG(int g) {
		this.g = g;
	}
	public int getH() {	
		return h;
	}
	public int heuristic() {
		h=this.state.heuristic();
		return h;
	}
	public int fcalculate() {
		f=h+cost;
		return f;
	}
	public int getCost() {
		return cost;
	}

	public String getmark() {
		return mark;
	}
	public void setmark(String mark) {
		this.mark = mark;
	}
	public String getDesc() {
		return this.state.StringDesc();
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	/**
	 * deep compare to another node
	 * @param other
	 * @return
	 */
	boolean equals(Node other) {
		if(other!=null && this.state.equals(other.state))
			return true;
		return false;
	}

	public String toString() {
		String ans="state:"+state+"\n step: "+step+"\n depth: "+depth;
		return ans;
	}
}
