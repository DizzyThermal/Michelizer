import java.util.ArrayList;

public class Point
{
	private ArrayList<Double> dimensions = new ArrayList<Double>();
	
	public double getValueAtDimension(int index)	{ return dimensions.get(index); }
	public int getDimensionSize()					{ return dimensions.size();		}
}