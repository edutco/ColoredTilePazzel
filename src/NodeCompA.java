import java.util.Comparator;

public class NodeCompA implements Comparator<Node>{

	@Override
	public int compare(Node o1, Node o2) {
		if(o1.fcalculateA() > o2.fcalculateA())
			return 1;
		if(o1.fcalculateA() < o2.fcalculateA())
			return -1;
		return 0;
	}

}
