import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Stack;
/**
* this class represents the IDA* algorithm used to find the cheapest goal Node
* using iterative deepening A*
* @author Edut Cohen
*
*/
public class IDAstar implements Algorithm{
	private long time=0;
	int nodeCounter=1;
	
	/**
	 * after finding goal state and path to it, save the result to file 
	 */
	@Override
	public void saveToFile(boolean toTime,  Node g, int Num) {
		try 
		{
			if(g!=null) {
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
			else {
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
	 * find the cheapest path to goal state using IDA* algorithm
	 */
	@Override
	public String solve(Node root, boolean toTime, boolean openList) {
		long start= System.currentTimeMillis();
		if(!root.getState().colorSolvable()) {
			long end=System.currentTimeMillis();
			time=end-start;
			saveToFile(toTime, null, 1);
			return "no path";
		}
		Stack<Node> openListStuck=new Stack<Node>();
		HashMap<String, Node> openListHash= new HashMap<String, Node>();
		int t= root.heuristic();
		while(t!=Integer.MAX_VALUE) {
			int minF=Integer.MAX_VALUE;
			openListStuck.add(root); //add root to open list- queue and hash map
			openListHash.put(root.getDesc(), root);
			while(!openListStuck.isEmpty()) {
				if(openList) {
					System.out.println("*********************************************************");
					for (Node curr : openListStuck) {
						System.out.println(curr.getState().toString());
						System.out.println("            ----------------------");
					}
					System.out.println("*********************************************************");
				}
				Node curr=openListStuck.pop(); //get current Node from stuck
				String currDesc=curr.getDesc();
				if(openListHash.containsKey(currDesc) &&
						openListHash.get(currDesc).getmark().contentEquals("out")) //if such state exists already and was explored
					openListHash.remove(currDesc, curr); //remove from hash
				else {
					curr.setmark("out");
					openListStuck.add(curr);
					String sonDesc;
					Node l= curr.exploreLeft(); //creates left son
					if(l!=null) {
						nodeCounter++;
						sonDesc=l.getDesc();
						if(l.fcalculate()> t) { //is h(son)+g(son) is greater than threshold
							minF=Math.min(minF, l.fcalculate()); //update threshold
							;
						}
						else {
							if(openListHash.containsKey(sonDesc) ) { //such state is in the open list
								Node prevIdenticalSon=openListHash.get(sonDesc);
								if(prevIdenticalSon.getmark().contentEquals("out")) //and mark as out- ignore
									;
								else {
									if(prevIdenticalSon.fcalculate() > l.fcalculate()) //if current son is cheaper replace
										prevIdenticalSon=l;
									else
										;
								}
							}
							else {
								if(l.isGoal()) { //goal Node is found, return result
									time=System.currentTimeMillis()-start;
									String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
									saveToFile(toTime, l, nodeCounter);
									return "IDA* result is\n"+ l.path().substring(0, l.path().length()-1)+"\n"+"num: "+nodeCounter+"\ncost: "+l.getCost()+"\n"+timeTemp+" milliseconds";
								}
								openListStuck.add(l);
								openListHash.put(sonDesc, l);
							}
						}
					}



					Node u= curr.exploreUp(); //creates up son
					if(u!=null) {
						nodeCounter++;
						sonDesc=u.getDesc();
						if(u.fcalculate()> t) { //is h(son)+g(son) is greater than threshold
							minF=Math.min(minF, u.fcalculate());
							;
						}
						else {
							if(openListHash.containsKey(sonDesc) ) { //such state is in the open list
								Node prevIdenticalSon=openListHash.get(sonDesc);
								if(prevIdenticalSon.getmark().contentEquals("out")) //and mark as out- ignore
									;
								else {
									if(prevIdenticalSon.fcalculate() > u.fcalculate()) //if current son is cheaper replace
										prevIdenticalSon=u;
									else
										;
								}
							}
							else {
								if(u.isGoal()) { //goal Node is found, return result
									time=System.currentTimeMillis()-start;
									String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
									saveToFile(toTime, u, nodeCounter);
									return "IDA* result is\n"+ u.path().substring(0, u.path().length()-1)+"\n"+"num: "+nodeCounter+"\ncost: "+u.getCost()+"\n"+timeTemp+" milliseconds";

								}
								openListStuck.add(u);
								openListHash.put(sonDesc, u);
							}
						}
					}




					Node r= curr.exploreRight(); //creates right son
					if(r!=null) {
						nodeCounter++;
						sonDesc=r.getDesc();
						if(r.fcalculate()> t) { //is h(son)+g(son) is greater than threshold
							minF=Math.min(minF, r.fcalculate());
							;
						}
						else {
							if(openListHash.containsKey(sonDesc) ) { //such state is in the open list
								Node prevIdenticalSon=openListHash.get(sonDesc);
								if(prevIdenticalSon.getmark().contentEquals("out")) //and mark as out- ignore
									;
								else {
									if(prevIdenticalSon.fcalculate() > r.fcalculate()) //if current son is cheaper replace
										prevIdenticalSon=r;
									else
										;
								}
							}
							else {
								if(r.isGoal()) { //goal Node is found, return result
									time=System.currentTimeMillis()-start;
									saveToFile(toTime, r, nodeCounter);
									String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
									return "IDA* result is\n"+ r.path().substring(0, r.path().length()-1)+"\n"+"num: "+nodeCounter+"\ncost: "+r.getCost()+"\n"+timeTemp+" milliseconds";
								}
								openListStuck.add(r);
								openListHash.put(sonDesc, r);
							}
						}
					}



					Node d=curr.exploreDown(); //creates down son
					if(d!=null) {
						nodeCounter++;
						sonDesc=d.getDesc();
						if(d.fcalculate()> t) { //is h(son)+g(son) is greater than threshold
							minF=Math.min(minF, d.fcalculate());
							;
						}
						else {
							if(openListHash.containsKey(sonDesc) ) { //such state is in the open list
								Node prevIdenticalSon=openListHash.get(sonDesc);
								if(prevIdenticalSon.getmark().contentEquals("out")) //and mark as out- ignore
									;
								else {
									if(prevIdenticalSon.fcalculate() > d.fcalculate())//if current son is cheaper replace
										prevIdenticalSon=d;
									else
										;
								}
							}
							else {
								if(d.isGoal()) { //goal Node is found, return result
									time=System.currentTimeMillis()-start;
									saveToFile(toTime, d, nodeCounter);
									String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
									return "IDA* result is\n"+ d.path().substring(0, d.path().length()-1)+"\n"+"num: "+nodeCounter+"\ncost: "+d.getCost()+"\n"+timeTemp+" milliseconds";
								}
								openListStuck.add(d);
								openListHash.put(sonDesc, d);
							}
						}
					}

				}
			}
			
			t=minF;
			root.setmark("");
		}
		//no goal Node was found
		time=System.currentTimeMillis()-start;
		saveToFile(toTime, null, nodeCounter);
		String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
		return "no path\n"+timeTemp+" seconds";
	}
}
