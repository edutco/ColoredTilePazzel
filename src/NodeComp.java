import java.util.Comparator;
/**
 * Campers two node by the given assignment rules
 * @author Edut Cohen
 *
 */
public class NodeComp implements Comparator<Node>{

	@Override
	public int compare(Node o1, Node o2) {
		if(o1.fcalculate() > o2.fcalculate()) //o2 is cheaper so return 1
			return 1;
		if(o1.fcalculate() < o2.fcalculate())
			return -1;
		if(o1.fcalculate() == o2.fcalculate()) {
			if(o1.getKey() > o2.getKey()) //o2 is younger so return 1
				return 1;
			else return -1;
		}
		return 0;
	}

}
