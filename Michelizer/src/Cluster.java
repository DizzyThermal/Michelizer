import java.util.ArrayList;

public class Cluster
{
	private ArrayList<Point> points = new ArrayList<Point>();
	
	public Point getPointAt(int index)		{ return points.get(index); 		}
	public Point removePointAt(int index)	{ return points.remove(index);		}
	public boolean removeAllPoints()		{ return points.removeAll(points);	}
}