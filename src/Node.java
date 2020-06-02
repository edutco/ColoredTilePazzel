import java.util.ArrayList;

public class Node {

	private Board state;
	private Node parent;
	private int depth;
	private String step;
	private int h,g,f, cost;
	private String luz;
	
	public Node (Board s, Node p, String st, int price) {
		cost=price;
		state=s;
		parent=p;
		if(p!=null) 
			depth=p.getDepth()+1;
		else depth=0;
		step=st;
		heuristicA();
		fcalculateA();
		luz="";
	}
	
	public boolean isGoal(){
		for(int i=0; i<this.state.height; i++) {
			for (int j = 0; j < this.state.width; j++) {
				if((i!=this.state.height-1 || j!=this.state.width-1)
						&& (this.state.matrix[i][j].num != (this.state.width*i+j+1)))
					return false;
			}
		}
		return true;
	}
	

	public Node exploreDown() {
		if(this.canBeExploredD()) {
			int val= this.state.matrix[this.state.hBlock-1][this.state.wBlock].num;
			int stepCost;
			if(this.state.matrix[this.state.hBlock-1][this.state.wBlock].color=="red")
				stepCost=30;
			else stepCost=1;
			Board myState=new Board(this.state);
			myState.moveDown();
			Node n= new Node(myState, this, val+"D-", this.cost+stepCost);
			n.state.hBlock--;
			return n;
		}
		return null;
	}

	private boolean canBeExploredD() {
		if(this.state.hBlock==0)
			return false;
		if(this.state.matrix[this.state.hBlock-1][this.state.wBlock].color=="black")
			return false;
		if(this.parent!=null && this.step.contains("U"))
			return false;
		return true;
	}

	public Node exploreRight() {
		if(this.canBeExploredR()) {
			
			if(depth==1);
			int val= this.state.matrix[this.state.hBlock][this.state.wBlock-1].num;
			int stepCost;
			if(this.state.matrix[this.state.hBlock][this.state.wBlock-1].color=="red")
				stepCost=30;
			else stepCost=1;
			Board myState=new Board(this.state);
			myState.moveRight();
			Node n= new Node(myState, this, val+"R-",this.cost+stepCost);
			n.state.wBlock--;
			return n;
		}
		return null;
	}

	private boolean canBeExploredR() {
		if(this.state.wBlock==0)
			return false;
		if(this.state.matrix[this.state.hBlock][this.state.wBlock-1].color=="black")
			return false;
		if(this.parent!=null && this.step.contains("L"))
			return false;
		return true;
	}

	public Node exploreUp() {
		if(this.canBeExploredU()) {
			int val= this.state.matrix[this.state.hBlock+1][this.state.wBlock].num;
			int stepCost;
			if(this.state.matrix[this.state.hBlock+1][this.state.wBlock].color=="red")
				stepCost=30;
			else stepCost=1;
			Board myState=new Board(this.state);
			myState.moveUp();
			Node n= new Node(myState, this, val+"U-", this.cost+stepCost);
			n.state.hBlock++;
			return n;
		}
		return null;
	}

	private boolean canBeExploredU() {
		if(this.state.hBlock==this.state.height-1)
			return false;
		if(this.state.matrix[this.state.hBlock+1][this.state.wBlock].color=="black")
			return false;
		if(this.parent!=null && this.step.contains("D"))
			return false;
		return true;
	}

	public Node exploreLeft() {
		if(this.canBeExploredL()) {
			int val= this.state.matrix[this.state.hBlock][this.state.wBlock+1].num;
			int stepCost;
			if(this.state.matrix[this.state.hBlock][this.state.wBlock+1].color=="red")
				stepCost=30;
			else stepCost=1;
			Board myState=new Board(this.state);
			myState.moveLeft();
			Node n= new Node(myState, this, val+"L-", this.cost+stepCost);
			n.state.wBlock++;
			return n;
		}
		return null;
	}

	private boolean canBeExploredL() {
		if(this.state.wBlock==this.state.width-1)
			return false;
		if(this.state.matrix[this.state.hBlock][this.state.wBlock+1].color=="black")
			return false;
		if(this.parent!=null && this.step.contains("R")) {
			return false;
		}
		return true;
	}

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



	public int heuristicA() {
		h=this.state.heuristicA();
		return h;
	}
	
	public int fcalculateA() {
		f=h+cost;
		return f;
	}
	
	public int getCost() {
		return cost;
	}
	boolean equals(Node other) {
		if(other!=null && this.state.equals(other.state))
			return true;
		return false;
	}
	
	public String toString() {
		String ans="state:"+state+"\n step: "+step+"\n depth: "+depth;
		return ans;
	}

	public String path() {
		if(this.parent==null)
			return "";
		String s=this.step;
			return this.parent.path()+s;
	//	else return s.substring(0, 1);
	}

	public String getLuz() {
		return luz;
	}

	public void setLuz(String luz) {
		this.luz = luz;
	}

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

	public String getDesc() {
		return this.state.StringDesc();
	}
}
