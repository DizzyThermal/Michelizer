import java.util.ArrayList;

public class Cluster
{
	private ArrayList<Point> points;
	private Point centoid;
	private String name;
	
	public Cluster()
	{
		points = new ArrayList<Point>();
		centoid = new Point();
		name = null;
	}
	
	public Cluster(Point point, String name)
	{
		points = new ArrayList<Point>();
		points.add(point);
		centoid = point;
		this.name = name;
	}
	
	// Cluster Name
	public String getName()					{ return name;						}
	public void setName(String name)		{ this.name = name;					}
	
	// Points in Cluster
	public ArrayList<Point> getPoints()		{ return points;					}
	public Point getPointAt(int index)		{ return points.get(index); 		}
	public Point removePointAt(int index)	{ return points.remove(index);		}
	public boolean removeAllPoints()		{ return points.removeAll(points);	}
	public void addPoint(Point point)		{ points.add(point);				}
	public int getNumberOfPoints()				{ return points.size();				}
	
	// Centoid
	public Point getCentoid()				{ return centoid;					}
	public void calculateCentoid()
	{
		Point centoid = new Point();
		for(int i = 0; i < points.get(0).getDimensionSize(); i++)
		{
			Double sum = 0.0;
			for(Point p : points)
				sum += p.getValueAtDimension(i);

			centoid.addDimension(sum/points.size());
		}
		this.centoid = centoid;
	}
	
	public boolean isEqual(Cluster c)
	{
		if(!name.equals(c.getName()))
			return false;
		if(c.getNumberOfPoints() != points.size())
			return false;
		for(int i = 0; i < points.size(); i++)
		{
			if(!points.get(i).isEqual(c.getPointAt(i)))
				return false;
		}
		if(!centoid.isEqual(c.getCentoid()))
			return false;

		return true;
	}
}