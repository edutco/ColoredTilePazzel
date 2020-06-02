import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Stack;

public class IDAstar implements Algorithm{
	private long time=0;
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
		long start= System.currentTimeMillis();
		Stack<Node> stack=new Stack<Node>();
		HashMap<String, Node> h= new HashMap<String, Node>();
		int t= root.heuristicA();
		int nodesnum=1;
		while(t!=Integer.MAX_VALUE) {
			int minF=Integer.MAX_VALUE;
			stack.add(root);
			h.put(root.getDesc(), root);
			while(!stack.isEmpty()) {
				if(openList) {
					System.out.println("*********************************************************");
					for (Node curr : stack) {
						System.out.println(curr.getState().toString());
						System.out.println("            ----------------------");
					}
					System.out.println("*********************************************************");
				}
				Node curr=stack.pop();
				String currDesc=curr.getDesc();
				if(h.containsKey(currDesc) &&
						h.get(currDesc).getLuz().contentEquals("out"))
					h.remove(currDesc, curr);
				else {
					curr.setLuz("out");
					stack.add(curr);
					String sonDesc;
					Node l= curr.exploreLeft();
					if(l!=null) {
						nodesnum++;
						sonDesc=l.getDesc();
						if(l.fcalculateA()> t) {
							minF=Math.min(minF, l.fcalculateA());
							;
						}
						else {
							if(h.containsKey(sonDesc) ) {
								Node prevIdenticalSon=h.get(sonDesc);
								if(prevIdenticalSon.getLuz().contentEquals("out"))
									;
								else {
									if(prevIdenticalSon.fcalculateA() > l.fcalculateA())
										prevIdenticalSon=l;
									else
										;
								}
							}
							else {
								if(l.isGoal()) {
									time=System.currentTimeMillis()-start;
									String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
									saveToFile(toTime, l, nodesnum);
									return "IDA* result is\n"+ l.path().substring(0, l.path().length()-1)+"\n"+"num: "+nodesnum+"\ncost: "+l.getCost()+"\n"+timeTemp+" milliseconds";
								}
								stack.add(l);
								h.put(sonDesc, l);
							}
						}
					}



					Node u= curr.exploreUp();
					if(u!=null) {
						nodesnum++;
						sonDesc=u.getDesc();
						if(u.fcalculateA()> t) {
							minF=Math.min(minF, u.fcalculateA());
							;
						}
						else {
							if(h.containsKey(sonDesc) ) {
								Node prevIdenticalSon=h.get(sonDesc);
								if(prevIdenticalSon.getLuz().contentEquals("out"))
									;
								else {
									if(prevIdenticalSon.fcalculateA() > u.fcalculateA())
										prevIdenticalSon=u;
									else
										;
								}
							}
							else {
								if(u.isGoal()) {
									time=System.currentTimeMillis()-start;
									String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
									saveToFile(toTime, u, nodesnum);
									return "IDA* result is\n"+ u.path().substring(0, u.path().length()-1)+"\n"+"num: "+nodesnum+"\ncost: "+u.getCost()+"\n"+timeTemp+" milliseconds";

								}
								stack.add(u);
								h.put(sonDesc, u);
							}
						}
					}




					Node r= curr.exploreRight();
					if(r!=null) {
						nodesnum++;
						sonDesc=r.getDesc();
						if(r.fcalculateA()> t) {
							minF=Math.min(minF, r.fcalculateA());
							;
						}
						else {
							if(h.containsKey(sonDesc) ) {
								Node prevIdenticalSon=h.get(sonDesc);
								if(prevIdenticalSon.getLuz().contentEquals("out"))
									;
								else {
									if(prevIdenticalSon.fcalculateA() > r.fcalculateA())
										prevIdenticalSon=r;
									else
										;
								}
							}
							else {
								if(r.isGoal()) {
									time=System.currentTimeMillis()-start;
									saveToFile(toTime, r, nodesnum);
									String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
									return "IDA* result is\n"+ r.path().substring(0, r.path().length()-1)+"\n"+"num: "+nodesnum+"\ncost: "+r.getCost()+"\n"+timeTemp+" milliseconds";
								}
								stack.add(r);
								h.put(sonDesc, r);
							}
						}
					}



					Node d=curr.exploreDown();
					if(d!=null) {
						nodesnum++;
						sonDesc=d.getDesc();
						if(d.fcalculateA()> t) {
							minF=Math.min(minF, d.fcalculateA());
							;
						}
						else {
							if(h.containsKey(sonDesc) ) {
								Node prevIdenticalSon=h.get(sonDesc);
								if(prevIdenticalSon.getLuz().contentEquals("out"))
									;
								else {
									if(prevIdenticalSon.fcalculateA() > d.fcalculateA())
										prevIdenticalSon=d;
									else
										;
								}
							}
							else {
								if(d.isGoal()) {
									time=System.currentTimeMillis()-start;
									saveToFile(toTime, d, nodesnum);
									String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
									return "IDA* result is\n"+ d.path().substring(0, d.path().length()-1)+"\n"+"num: "+nodesnum+"\ncost: "+d.getCost()+"\n"+timeTemp+" milliseconds";
								}
								stack.add(d);
								h.put(sonDesc, d);
							}
						}
					}

				}
			}
			
			
			t=minF;
			root.setLuz("");
		}
		time=System.currentTimeMillis()-start;
		saveToFile(toTime, null, nodesnum);
		String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
		return "no path\n"+timeTemp+" seconds";
	}
}
