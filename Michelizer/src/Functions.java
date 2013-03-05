import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

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
	
	public static boolean MST(ArrayList<Point> pValues, int NumClusters,int dtype) throws IOException
	{
		
		String fileName = "results.csv";
		FileWriter file = new FileWriter(fileName);
		ArrayList<Cluster> C = new ArrayList<Cluster>();
		
		//initilize the cluster matrix to the size of the data
        for (int i = 0; i < pValues.size(); i++)
        {
            Cluster tempClust = new Cluster();
            tempClust.setName( Integer.toString(i));
            tempClust.addPoint(pValues.get(i));
            C.add(tempClust);
        }
        
        
		
		/*file.append("DisplayName");
	    file.append(',');
	    file.append("Age");
	    file.append('\n');*/
		file.close();
		return false;
	}
	public static boolean K_Means(ArrayList<Point> pValues, int NumClusters,int dtype)throws IOException
	{
		String fileName = "results.csv";
		FileWriter file = new FileWriter(fileName);
		ArrayList<Cluster> C = new ArrayList<Cluster>();
		
		//initilize the cluster matrix to the size of the data
        for (int i = 0; i < pValues.size(); i++)
        {
            Cluster tempClust = new Cluster();
            tempClust.setName( Integer.toString(i));
            tempClust.addPoint(pValues.get(i));
            C.add(tempClust);
        }
		file.close();
		return false;
	}
	public static boolean Z_Score(ArrayList<Point> pValues)throws IOException
	{
		String fileName = "results.csv";
		FileWriter file = new FileWriter(fileName);
		ArrayList<Cluster> C = new ArrayList<Cluster>();
		
		//initilize the cluster matrix to the size of the data
        for (int i = 0; i < pValues.size(); i++)
        {
            Cluster tempClust = new Cluster();
            tempClust.setName( Integer.toString(i));
            tempClust.addPoint(pValues.get(i));
            C.add(tempClust);
        }
        
        file.close();
		return false;
	}
}