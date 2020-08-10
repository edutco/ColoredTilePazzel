/**
 * this class represents a tile in a board. It has a color and a number. The blank piece initialize to be color=" " and number=0.
 * @author Edut Cohen 6.2020
 *
 */
public class piece {
	private int num; 
	private String color;
	
	public piece(int n, String c){
		num=n;
		color=c;
	}
	public piece(piece other){
		num=other.num;
		color=other.color;
	}
	public String toString() {
		String ans= num+" "+color.charAt(0);
		if(num==0) {
			ans= "|_|";
			color=" ";
		}
		while(ans.length() < 8)
			ans=ans+" ";
		return ans;
	}
	public boolean equals(piece other) {
		if(this.num==other.num)
			return true;
		return false;
	}
	
	public  int getNum() {
		return num;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setNum(int n) {
		num=n;
	}
	
	public void setColor(String c) {
		color=c;
	}
}
