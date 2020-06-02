import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

public class DFID implements Algorithm{

	private int l;
	long time=0;
	boolean isCutOff;
	int num=0;

	@Override
	public void saveToFile(boolean toTime, Node g, int Num) {
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
		for (l=1 ; l < 1000; l++) { //allows up to 1000 movements in order to not drive my computer crazy
			if(openList)
				System.out.println("Iteration number "+l);
			HashMap<String, Node> H= new HashMap<String, Node>();
			Object[] result=this.limitDFS(root, l, H, openList);
			if(!((String) result[0]).equalsIgnoreCase("cutOff")) {	
				Node goal=(Node) result[1];
				time=System.currentTimeMillis()-start;
				saveToFile(toTime,goal , num);
				String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
				return "DFID result is:\n"+ goal.path().substring(0, goal.path().length()-1)+"\n"+"num:"+i+"\ncost: "+goal.getCost()+"\n"+timeTemp+" seconds";
			}
		}
		time=System.currentTimeMillis()-start;
		saveToFile(toTime,null , num);
		String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
		return "no path"+timeTemp;
	}

	int i=1;
	private Object[] limitDFS(Node n, int limit, HashMap<String, Node> h, boolean openList) {
		
		if(n.isGoal()) {
			Object[] ans=new Object[2];
			ans[0]=n.path();
			ans[1]=n;
			return ans;
		}
		if(limit==0) {
			Object[] ans = {"cutOff", null};
			return ans;
		}
		h.put(n.getState().StringDesc(), n);
		num++;
		isCutOff=false;
		Node l=n.exploreLeft();
		if(l!=null) {
			i++;
			if(!h.containsKey(l.getState().StringDesc())) {//new Node
				Object[] result=limitDFS(l, limit-1, h, openList);
				if(((String) result[0]).equalsIgnoreCase("cutOff"))
					isCutOff=true;
				else {
					if (!((String) result[0]).equalsIgnoreCase("fail"))
						return result;
				}
			}
		}
		Node u=n.exploreUp();
		if(u!=null) {
			i++;
			if(!h.containsKey(u.getState().StringDesc())) { //new Node
				Object[] result=limitDFS(u, limit-1, h, openList);
				if(((String) result[0]).equalsIgnoreCase("cutOff"))
					isCutOff=true;
				else {
					if (!((String) result[0]).equalsIgnoreCase("fail"))
						return result;
				}
			}
		}
		Node r=n.exploreRight();
		if(r!=null) {
			i++;
			if(!h.containsKey(r.getState().StringDesc())) { //new Node
				Object[] result=limitDFS(r, limit-1, h, openList);
				if(((String) result[0]).equalsIgnoreCase("cutOff"))
					isCutOff=true;
				else {
					if (!((String) result[0]).equalsIgnoreCase("fail"))
						return result;
				}
			}
		}
		Node d=n.exploreDown();
		if(d!=null) {
			i++;
			if(!h.containsKey(d.getState().StringDesc())) { //new Node
				Object[] result=limitDFS(d, limit-1, h, openList);
				if(((String) result[0]).equalsIgnoreCase("cutOff"))
					isCutOff=true;
				else {
					if (!((String) result[0]).equalsIgnoreCase("fail"))
						return result;
				}
			}
		}
		if(openList && !h.isEmpty()) {
			System.out.println("*********************************************************");
			for (Node node : h.values()) {
				System.out.println(node.getState().toString());
				System.out.println("            ----------------------");
			}
			System.out.println("*********************************************************");
		}
		h.remove(n.getState().StringDesc());
		if(isCutOff==true) {
			Object[] ans= {"cutOff", null};
			return ans;
		}
		else {
			Object[] ans= {"fail", null};
			return ans;
		}
	}

}
