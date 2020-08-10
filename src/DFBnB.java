import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
/**
 * this class represents the DFBnB algorithm used to find cheapest path to a goal Node, 
 * by finding goal Nodes and decreasing search range. 
 * @author Edut Cohen
 *
 */
public class DFBnB implements Algorithm{
	int nodeCounter=1;
	long time=0;
	/**
	 * after finding goal state and path to it, save the result to file 
	 */
	@Override
	public void saveToFile(boolean toTime,  Node g, int Num) {
		try 
		{
			if(g!=null) {//a path was found
				PrintWriter pw = new PrintWriter(new File("output.txt"));
				StringBuilder sb = new StringBuilder();
				sb=sb.append(g.path().substring(0, g.path().length()-1));
				sb=sb.append("\nNum: "+Num);
				sb=sb.append("\nCost: "+g.getCost());
				if(toTime) {
					String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
					sb=sb.append("\n"+timeTemp+" seconds");
				}
				pw.write(sb.toString());
				pw.close();
			}
			else {//no path was found
				PrintWriter pw = new PrintWriter(new File("output.txt"));
				StringBuilder sb = new StringBuilder();
				sb=sb.append("no path");
				sb=sb.append("\nNum: "+Num);
				if(toTime) {
					String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
					sb=sb.append("\n"+timeTemp+" seconds");
				}
				pw.write(sb.toString());
				pw.close();
			}

		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}

	}


	/**
	 * find cheapest path to goal state using DFBnB algorithm
	 */
	@Override
	public String solve(Node root, boolean toTime, boolean openListPrint) {
		long start=System.currentTimeMillis();
		if(!root.getState().colorSolvable()) {
			long end=System.currentTimeMillis();
			time=end-start;
			saveToFile(toTime, null, 1);
			return "no path";
		}
		root.setKey(nodeCounter); //used in sorting the nodes
		Stack<Node> openListStuck= new Stack<Node>(); //open list stack
		ArrayList<Node> sons;
		HashMap<String, Node> openListHash= new HashMap<String, Node>(); //open list follow-hash table
		openListStuck.add(root);
		openListHash.put(root.getDesc(), root);
		Node result=null;
		int size=root.getState().width*root.getState().height; //number of board tiles
		int blacks=root.getState().countBlacks(); 
		int notBlacks=size -blacks -1; //non black tiles
		int t=1;
		if(notBlacks<=12) { //Threshold- upper bound by Noam definition 
			while(notBlacks<=12 && notBlacks>1) {
				t=t*notBlacks;
				notBlacks--;
			}
		}
		else
			t=Integer.MAX_VALUE;
				while(!openListStuck.isEmpty()) {
					if(openListPrint) {
						System.out.println("*********************************************************");
						for (Node node : openListStuck) {
							System.out.println(node.getState().toString());
							System.out.println("            ----------------------");
						}
						System.out.println("*********************************************************");
					}
					Node curr=openListStuck.pop();
					if(openListHash.containsKey(curr.getDesc()) && openListHash.get(curr.getDesc()).getmark().contentEquals("out"))//if this Node is on the current path and was explored
						openListHash.remove(curr.getDesc());
					else {
						curr.setmark("out"); //mark as explored
						openListStuck.add(curr);
						sons= curr.expendAll();
						for (Node node : sons) {
							node.setKey(++nodeCounter); //set key in order to use while sorting
						}
						sons.sort(new NodeComp()); //sort sons by f value
						Node [] sonarr= new Node[4]; 
						for (int i = 0; i < sons.size(); i++) {//initiate sons array
							sonarr[i]=sons.get(i);
						}
						for (int i = sons.size(); i < 4; i++) {
							sonarr[i]=null;
						}
						Node son;
						for (int i = 0; i < sons.size(); i++) {
							son=sonarr[i];
							if(son!=null) { //if son's f is greater than threshold, the branch needs to be cut
								if(son.fcalculate()>= t) {
									for (int j = i; j < sonarr.length; j++) {
										sonarr[j]=null;
									}
								}
								else {
									if(openListHash.containsKey(son.getDesc())) { //such Node was discovered before
										Node identicalSon=openListHash.get(son.getDesc());
										if(identicalSon.getmark().contentEquals("out")) //this privies son was explored
											sonarr[i]=null;
										else {
											if(!identicalSon.getmark().contentEquals("out")) { //the privies son was not explored
												if(identicalSon.fcalculate() <= son.fcalculate() ) //if privies son is cheaper, remove this son
													sonarr[i]=null;
												else {
													openListHash.remove(son.getDesc());
													openListStuck.remove(identicalSon);
												}

											}
										}
									}

									else {
										if(son.isGoal()) { //is a goal Node was found
											t=son.fcalculate(); //update threshold
											result=son;
											for (int j = i; j < sonarr.length; j++) { //cut the rest of the branch
												sonarr[j]=null;
											}
										}
									}
								}
							}
						}
						for (int k=3; k>=0 ; k--) { //enter sons by reveres order
							if(sonarr[k]!=null) {
								openListHash.put(sonarr[k].getDesc(), sonarr[k]);
								openListStuck.add(sonarr[k]);
							}
						}
					}
				}
		//save result to file		
		time= System.currentTimeMillis()-start;
		String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
		saveToFile(toTime, result, nodeCounter);
		return "DFBnB result is\n"+ result.path().substring(0, result.path().length()-1)+"\n"+"num:"+nodeCounter+"\ncost: "+result.getCost()+"\n"+timeTemp+" milliseconds";

	}

}
