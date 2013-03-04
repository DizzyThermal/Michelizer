public class Functions
{
	public static double manhatten(Point p1, Point p2)
	{
		double sum = 0;
		for(int i = 0; i < p1.getDimensionSize(); i++)
			sum += Math.abs(p2.getValueAtDimension(i) - p1.getValueAtDimension(i));
		
		return sum;
	}

	public static double euclidian(Point p1, Point p2)
	{
		double sum = 0;
		for(int i = 0; i < p1.getDimensionSize(); i++)
			sum += Math.pow(p2.getValueAtDimension(i) - p1.getValueAtDimension(i), 2);
		
		return Math.sqrt(sum);
	}
}