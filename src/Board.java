import java.util.ArrayList;

public class Board {
	public int width;
	public int height;
	public piece[][] matrix;
	public int hBlock, wBlock;


	public Board (Board other) {
		this.hBlock=other.hBlock;
		this.wBlock=other.wBlock;
		this.height=other.height;
		this.width=other.width;
		this.matrix=new piece[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j <width; j++) {
				this.matrix[i][j]=new piece(other.matrix[i][j]);
			}
		}
	}


	//sweep two pieces in the board
	public void sweep(int x1, int y1, int x2, int y2) {
		piece temp=new piece(this.matrix[x1][y1]);
		this.matrix[x1][y1]=this.matrix[x2][y2];
		this.matrix[x2][y2]=temp;
	}

	//make sure the piece to move is neighbor with blank piece.
	//if true, sweep the two pieces
	public void movePiece(int i, int j) {
		if((i==hBlock && j==wBlock-1) || (i==hBlock && j==wBlock+1)|| (i==hBlock+1 && j==wBlock) || (i==hBlock-1 && j==wBlock))
			this.sweep(i,j, hBlock,wBlock);
		else System.out.println("not neigbors!");
	}
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
						matrix[i][j]=new piece(0, "null");
						hBlock=i;
						wBlock=j;
					}
				}
			}
		}
	}
	//	public void initFromString (ArrayList<String> s) {
	//		for (int i = 0; i < s.size(); i++) {
	//			s.set(i, s.get(i).replace(" ", ""));
	//			String line[] = s.get(i).split(",");
	//			if(line.length!=width)
	//				System.out.println("error size");
	//			else {
	//				for (int j = 0; j < line.length; j++) {
	//					if(!line[j].contains("_") ){
	//						int num= Integer.parseInt(line[j]);
	//						String color;
	//						if(red !=null && red.contains(num))
	//							color="red";
	//						else {
	//							if(black!=null && black.contains(num))
	//								color="black";
	//							else 
	//								color="green";
	//						}
	//						matrix[i][j]= new piece(num, color);
	//					}
	//					else {
	//						matrix[i][j]=new piece(0, "null");
	//						hBlock=i;
	//						wBlock=j;
	//					}
	//				}
	//			}
	//		}
	//	}

	public String toString() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				System.out.print(matrix[i][j].toString()+"\t");
			}
			System.out.println();
		}
		return "";
	}

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
		this.movePiece(hBlock,wBlock-1);
	}
	public void moveLeft() {
		this.movePiece(hBlock,wBlock+1);
	}
	public void moveUp() {
		this.movePiece(hBlock+1,wBlock);
	}
	public void moveDown() {
		this.movePiece(hBlock-1,wBlock);
	}

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
	public int heuristicA() {
		int sum=0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if(matrix[i][j].num != width*i+j+1) {
					if(matrix[i][j].num!=0) {
					int[] valLoc=getValLoc(matrix[i][j].num);
					int differ=Math.abs(valLoc[0]-i)+Math.abs(valLoc[1]-j);
					int stepCost=1;
					if(matrix[i][j].color.contentEquals("red"))
						stepCost=30;
					sum+=differ*stepCost;
					}
				}
			}
		}
		return sum;
	}
	
	public String StringDesc(){
		String s="";
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				s=s+matrix[i][j].num+",";
			}
	}
	return s;
	}
	
}