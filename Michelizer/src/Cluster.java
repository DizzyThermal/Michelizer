import java.util.ArrayList;

public class Cluster
{
	private ArrayList<Point> points = new ArrayList<Point>();
	private ArrayList<Double> centoid = new ArrayList<Double>();
	private String name;
	
	public String getName(){return name;}
	public void setName(String n){name=n;}
	public Point getPointAt(int index)		{ return points.get(index); 		}
	public Point removePointAt(int index)	{ return points.remove(index);		}
	public boolean removeAllPoints()		{ return points.removeAll(points);	}
	public void addPoint(Point p){points.add(p);}
}