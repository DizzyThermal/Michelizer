import java.util.ArrayList;

public class Point
{
	private ArrayList<Double> dimensions = new ArrayList<Double>();
	
	public void addDimensionValue(double value)					{ dimensions.add(value);		}
	public void setValueAtDimension(int index, double value)	{ dimensions.set(index, value);	}
	public double getValueAtDimension(int index)				{ return dimensions.get(index); }
	public int getDimensionSize()								{ return dimensions.size();		}
}