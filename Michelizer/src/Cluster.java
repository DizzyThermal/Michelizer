import java.util.ArrayList;

public class Cluster
{
	private ArrayList<Point> points = new ArrayList<Point>();
	private Point centoid = new Point();
	private String name;
	
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
}