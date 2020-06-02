import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class DFBnB implements Algorithm{
	int i=1;
	long time=0;
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
	
	

	@Override
	public String solve(Node root, boolean toTime, boolean openList) {
		long start=System.currentTimeMillis();
		Stack<Node> s= new Stack<Node>();
		ArrayList<Node> sons;
		HashMap<String, Node> hm= new HashMap<String, Node>();
		s.add(root);
		hm.put(root.getDesc(), root);
		Node result=null;
		int t=200;//Integer.MAX_VALUE;
		while(!s.isEmpty()) {
			if(openList) {
				System.out.println("*********************************************************");
				for (Node node : s) {
					System.out.println(node.getState().toString());
					System.out.println("            ----------------------");
				}
				System.out.println("*********************************************************");
			}
			Node curr=s.pop();
			if(hm.containsKey(curr.getDesc()) && hm.get(curr.getDesc()).getLuz().contentEquals("out"))
				hm.remove(curr.getDesc());
			else {
				curr.setLuz("out");
				s.add(curr);
				sons= curr.expendAll();
				i+=sons.size();
				sons.sort(new NodeCompA());
				Node [] sonarr= new Node[4];
				for (int i = 0; i < sons.size(); i++) {
					sonarr[i]=sons.get(i);
				}
				for (int i = sons.size(); i < 4; i++) {
					sonarr[i]=null;
				}
				Node son;
				for (int i = 0; i < sons.size(); i++) {
					son=sonarr[i];
					if(son!=null) {
						if(son.fcalculateA()>= t) {
							for (int j = i; j < sonarr.length; j++) {
								sonarr[j]=null;
							}
						}
						else {
							if(hm.containsKey(son.getDesc())) {
								Node identicalSon=hm.get(son.getDesc());
								if(identicalSon.getLuz().contentEquals("out")) 
									sonarr[i]=null;


								else {
									if(!identicalSon.getLuz().contentEquals("out")) {
										if(identicalSon.fcalculateA() <= son.fcalculateA() )
											sonarr[i]=null;
										else {
											hm.remove(son.getDesc());//maybe change???
											s.remove(identicalSon);
										}

									}
								}
							}

							else {
								if(son.isGoal()) {
									t=son.fcalculateA();
									result=son;
									for (int j = i; j < sonarr.length; j++) {
										sonarr[j]=null;
									}
								}
							}
						}
					}
				}



				for (int k=3; k>=0 ; k--) {
					if(sonarr[k]!=null) {
						hm.put(sonarr[k].getDesc(), sonarr[k]);
						s.add(sonarr[k]);
					}
				}
			}
		}
		time= System.currentTimeMillis()-start;
		String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
		saveToFile(toTime, result, i);
		return "DFBnB result is\n"+ result.path().substring(0, result.path().length()-1)+"\n"+"num:"+i+"\ncost: "+result.getCost()+"\n"+timeTemp+" milliseconds";

	}

}
