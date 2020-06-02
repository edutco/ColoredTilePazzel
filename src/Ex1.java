import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Ex1 {
	static String fileName="input.txt";


	public static  void play(String pathFile) {
		ArrayList<String> lines= new ArrayList<String>();

		try {
			FileInputStream fstream = new FileInputStream(pathFile);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				lines.add(strLine);
			}
			br.close();
		}
		catch (FileNotFoundException e){

			System.err.println("Error: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();

		}
		boolean time=false, openList=false;
		int m,n;
		ArrayList<Integer> Black =new ArrayList<Integer>();
		ArrayList<Integer> Red =new ArrayList<Integer>();
		if(lines.get(1).contains("with time") )   //time
			time=true;
		else{ 
			if(lines.get(1).contains("no time") )
				time=false;
			else System.out.println("wrong input"); 
		}
		if(lines.get(2).contains("with open")) //open list
			openList=true;
		else{ 
			if(lines.get(2).contains("no open")) 
				openList=false;
			else System.out.println("wrong input");
		}
		String [] line3= lines.get(3).split("x");
		if(line3.length!=2) {
			System.out.println("wrong input"); 
			throw new IllegalArgumentException() ;
		}
		n=Integer.parseInt(line3[0]);
		m=Integer.parseInt(line3[1]);
		String blacks=lines.get(4);
		String [] temp= blacks.replaceAll(" ", "").split(":");
		if(temp.length==2) {
			blacks= blacks.split(":")[1];
			blacks=blacks.replace(" ", "");
			String BlackString []= blacks.split(",");
			for (int j = 0; j < BlackString.length; j++) {
				Black.add(Integer.parseInt(BlackString[j]));
			}
		}

		else Black=null;
		String reds=lines.get(5);
		temp= reds.split(":");
		if(temp.length==2) {
			reds= reds.split(":")[1];
			reds=reds.replace(" ", "");
			String RedString []= reds.split(",");
			for (int j = 0; j < RedString.length; j++) {
				Red.add(Integer.parseInt(RedString[j]));
			}
		}
		else Red=null;
		ArrayList <String> matrix=new ArrayList<String>();// (ArrayList<String>) lines.subList( 5, lines.size()); 
		for (int i = 6; i < lines.size(); i++) {
			matrix.add(lines.get(i));
		}
		Board b= new Board(n, m, Red, Black, matrix);
		Algorithm algo=null;
		switch(lines.get(0)){
		case "BFS":
			algo=new BFS();
			break;
		case "DFID":
			algo= new DFID();
			break;
		case "A*":
			algo= new Astar();
			break;
		case "IDA*":
			algo= new IDAstar();
			break;
		case "DFBnB":
			algo= new DFBnB();
			break;
		}
		Node root= new Node(b, null, "init", 0);
		System.out.println(algo.solve(root, time, openList));
	}

	public static void main(String[] args) {
		play(fileName);

	}

}