import java.util.ArrayList;

public class Point
{
	private ArrayList<Double> dimensions = new ArrayList<Double>();
	
	public void addDimension(double coordinate)				{ dimensions.add(coordinate);			}
	public void setDimension(int index, double coordinate)	{ dimensions.set(index, coordinate);	}
	
	public double getValueAtDimension(int index)			{ return dimensions.get(index);			}
	public int getDimensionSize()							{ return dimensions.size();				}
}