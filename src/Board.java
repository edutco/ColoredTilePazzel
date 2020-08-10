import java.util.ArrayList;
/**
 * This class represents a specific state of the game board.
 * @author Edut Cohen
 *
 */
public class Board {
	public int width;
	public int height;
	public piece[][] matrix;
	public int hBlank, wBlank; //blank piece spot in this specific board

	/**
	 * constructor
	 * @param h
	 * @param w
	 * @param reds
	 * @param blacks
	 * @param myState
	 */
	public Board(int h, int w, ArrayList<Integer> reds, ArrayList<Integer> blacks, ArrayList<String> myState) {
		width=w;
		height=h;
		matrix = new piece[h][w];
		for (int i = 0; i < myState.size(); i++) {
			myState.set(i, myState.get(i).replace(" ", ""));
			String line[] = myState.get(i).split(",");
			if(line.length!=width)
				System.out.println("error size width");
			else {
				for (int j = 0; j < line.length; j++) {
					if(!line[j].contains("_") ){
						int num= Integer.parseInt(line[j]);
						String color;
						if(reds !=null && reds.contains(num))
							color="red";
						else {
							if(blacks!=null && blacks.contains(num))
								color="black";
							else 
								color="green";
						}
						matrix[i][j]= new piece(num, color);
					}
					else {
						matrix[i][j]=new piece(0, " ");
						hBlank=i;
						wBlank=j;
					}
				}
			}
		}
	}

	/**
	 * copy constructor- deep copy
	 * @param other
	 */
	public Board (Board other) {
		this.hBlank=other.hBlank;
		this.wBlank=other.wBlank;
		this.height=other.height;
		this.width=other.width;
		this.matrix=new piece[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j <width; j++) {
				this.matrix[i][j]=new piece(other.matrix[i][j]);
			}
		}
	}


	/**
	 * sweep two pieces in the board
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void sweep(int x1, int y1, int x2, int y2) {
		piece temp=new piece(this.matrix[x1][y1]);
		this.matrix[x1][y1]=this.matrix[x2][y2];
		this.matrix[x2][y2]=temp;
	}

	/**
	 * make sure the piece to move is neighbor with blank piece
	 * if true, sweep the two pieces
	 * @param i
	 * @param j
	 */
	public void movePiece(int i, int j) {
		if((i==hBlank && j==wBlank-1) || (i==hBlank && j==wBlank+1)|| (i==hBlank+1 && j==wBlank) || (i==hBlank-1 && j==wBlank))
			this.sweep(i,j, hBlank,wBlank);
		else System.out.println("not neigbors!");
	}


	/**
	 * prints the board state
	 */
	public String toString() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				System.out.print(matrix[i][j].toString()+"\t");
			}
			System.out.println();
		}
		return "";
	}
	
	/**
	 * checks whether two boards are state-equals
	 * @param other
	 * @return
	 */
	public boolean equals(Board other) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if(!this.matrix[i][j].equals(other.matrix[i][j]))
					return false;
			}
		}
		return true;
	}


	public void moveRight() {
		this.movePiece(hBlank,wBlank-1);
	}
	public void moveLeft() {
		this.movePiece(hBlank,wBlank+1);
	}
	public void moveUp() {
		this.movePiece(hBlank+1,wBlank);
	}
	public void moveDown() {
		this.movePiece(hBlank-1,wBlank);
	}

	/**
	 * gets a number and returns the spot of this number in a goal board
	 * @param val
	 * @return
	 */
	public int[] getValLoc(int val) {
		int [] ans= {0,0};
		if(val>=width*height) {
			System.out.println("error");
			return ans;
		}
		else {
			ans[0]=(val-1)/width;
			ans[1]=(val-1)%width;
		}
		return ans;
	}

	/**
	 * checks whether all the black pieces are in place, if not the board is not solvable
	 * @return
	 */
	public boolean colorSolvable() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if(matrix[i][j].getNum()!= width*i+j+1 && matrix[i][j].getColor().contentEquals("black")) 
					return false;
			}
		}
		return true;
	}


	/**
	 * Manhattan distance heuristic function
	 * @return
	 */
	public int heuristic() {
		int sum=0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if(matrix[i][j].getNum() != width*i+j+1) {
					if(matrix[i][j].getNum()!=0) {
						int[] valLoc=getValLoc(matrix[i][j].getNum());
						int differ=Math.abs(valLoc[0]-i)+Math.abs(valLoc[1]-j);
						int stepCost=1;
						if(matrix[i][j].getColor().contentEquals("red"))
							stepCost=30;
						sum+=differ*stepCost;
					}
				}
			}
		}
		return sum;
	}
	/**
	 * returns a string represents the specific state of the board- numbers only.
	 * this String would be the key in hashmaps 
	 * @return
	 */
	public String StringDesc(){
		String s="";
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				s=s+matrix[i][j].getNum()+",";
			}
		}
		return s;
	}

	/**
	 * counts the blacks tile in the board
	 * used to calculate threshold in DFBnB
	 * @return
	 */
	public int countBlacks() {
		int sum=0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if(matrix[i][j].getColor().contentEquals("black"))
					sum++;
			}
		}
		return sum;
	}

}