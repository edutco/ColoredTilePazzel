
public class piece {
	public int num; 
	public String color;
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
}
