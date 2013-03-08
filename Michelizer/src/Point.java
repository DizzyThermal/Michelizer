import java.util.ArrayList;

public class Point
{
	private ArrayList<Double> dimensions = new ArrayList<Double>();
	
	public void addDimension(double value)						{ dimensions.add(value);					}
	public void setValueAtDimension(int index, double value)	{ dimensions.set(index, value);				}
	public void removeDimensionAt(int index)					{ dimensions.remove(index);					}
	public void removeDimension()								{ dimensions.remove(dimensions.size()-1);	}
	public double getValueAtDimension(int index)				{ return dimensions.get(index); 			}
	public int getDimensionSize()								{ return dimensions.size();					}
	
	public String printPoint(boolean includeDimensionLabels)
	{
		String pointStr = null;
		for(int  i = 0; i < dimensions.size(); i++)
			pointStr += (includeDimensionLabels)?"D" + (i+1) + ": " + dimensions.get(i) + ", ":dimensions.get(i) + ", ";
		
		return pointStr.substring(0, pointStr.length() - 3);	// Trim last comma and space ( -3 )
	}
}