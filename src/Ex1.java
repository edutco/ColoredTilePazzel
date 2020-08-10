import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Ex1 {
	static String fileName="input.txt";
	static boolean edutDebugging =true;
	/**
	 * main function to start the game
	 * @param args
	 */
	public static void main(String[] args) {
		play(fileName);

	}
	
	
	
	/**
	 * This method reads the data from file and starts solving using the matching algorithm
	 * @param pathFile
	 */
	public static  void play(String pathFile) {
		ArrayList<String> lines= new ArrayList<String>();
		//reading all lines
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
		String [] line3= lines.get(3).split("x"); //matrix size
		if(line3.length!=2) {
			System.out.println("wrong input"); 
			throw new IllegalArgumentException() ;
		}
		n=Integer.parseInt(line3[0]);
		m=Integer.parseInt(line3[1]);
		String blacks=lines.get(4).replaceAll(" ", "");
		String [] temp= blacks.split(":");
		if(temp.length==2) {
			blacks= blacks.split(":")[1];
			String BlackString []= blacks.split(",");
			for (int j = 0; j < BlackString.length; j++) {
				Black.add(Integer.parseInt(BlackString[j])); //initiate black list
			}
		}

		else Black=null;
		String reds=lines.get(5).replaceAll(" ", "");
		temp= reds.split(":");
		if(temp.length==2) {
			reds= reds.split(":")[1];
			String RedString []= reds.split(",");
			for (int j = 0; j < RedString.length; j++) {
				Red.add(Integer.parseInt(RedString[j])); //initiate red list
			}
		}
		else Red=null;
		ArrayList <String> matrix=new ArrayList<String>();
		for (int i = 6; i < lines.size(); i++) {
			matrix.add(lines.get(i)); //initiate matrix lines
		}
		Board b= new Board(n, m, Red, Black, matrix); //creates a board using all info- number and colors
		Algorithm algo=null;
		//finds an algorithm to use
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
		if(edutDebugging)
			System.out.println(algo.solve(root, time, openList));
		else
			algo.solve(root, time, openList);
	}

	

}