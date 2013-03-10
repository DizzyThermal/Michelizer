import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class Functions
{
	// Static Variables
	public static int MANHATTEN	= 0;
	public static int EUCLIDEAN	= 1;

	public static double manhatten(Point p1, Point p2)
	{
		double sum = 0;
		for(int i = 0; i < p1.getDimensionSize(); i++)
			sum += Math.abs(p2.getValueAtDimension(i) - p1.getValueAtDimension(i));
		
		return sum;
	}

	public static double euclidean(Point p1, Point p2)
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
        
        //start of actual algorithm
        Double distance=0.0;
        while (C.size() > NumClusters)
        {
            //Display distance matrix

            //display headers
            for (int i = 0; i < C.size(); i++)
            {
                file.append(" ," + C.get(i).getName());
            }
            file.append("\n");
            
            int index1 = 0;
            int index2 = 0;
            double min = Double.MAX_VALUE;
            for (int i = 0; i < C.size(); i++)
            {
                file.append(C.get(i).getName() + ",");
                for (int j = 0; j < C.size(); j++)
                {
                	if(dtype==0)
                		distance = manhatten(C.get(i).getCentoid() ,C.get(j).getCentoid());
                	else
                		distance = euclidean(C.get(i).getCentoid(), C.get(j).getCentoid());
                	
                    file.append(distance + ",");
                    if (i != j && distance < min)
                    {
                        min = distance;
                        index1 = i;
                        index2 = j;
                    }
                }
                file.append("\n");
            }
            file.append("\n");
            file.append("\n");
            //the index of the two clusters that are the closest to each other is held in index1 & index2;
            for (int i = 0; i < C.get(index2).getNumberOfPoints(); i++)
                C.get(index1).addPoint(C.get(index2).getPointAt(i));
            C.get(index1).setName(C.get(index1).getName() + " | " + C.get(index2).getName());
            C.remove(index2);

        }

        //display headers
        for (int i = 0; i < C.size(); i++)
        {
            file.append(" ," + C.get(i).getName());
        }
        file.append("\n");

        for (int i = 0; i < C.size(); i++)
        {
            file.append(C.get(i).getName() + ",");
            for (int j = 0; j < C.size(); j++)
            {
            	
            	if(dtype==0)
            		distance = manhatten(C.get(i).getCentoid(), C.get(j).getCentoid());
            	else
            		distance = euclidean(C.get(i).getCentoid(),C.get(j).getCentoid());
            	            	
               
                file.append(distance + ",");
            }
            file.append("\n");
        }
        
		
		
		file.close();
		return true;
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
        
        
        
        
        ArrayList<Cluster> C2 = new ArrayList<Cluster>();
        ArrayList<Cluster> C2old = new ArrayList<Cluster>();
        //initilize the cluster matrix to the number of the desired clusters
        //this distributes all the points out like cards.
        for (int i = 0; i < NumClusters; i++)
        {
            Cluster tempClust = new Cluster();
            Cluster tempClust2 = new Cluster();
            tempClust2.setName(tempClust2.getName() + Integer.toString(i));
            tempClust2.addPoint(pValues.get(i));
            tempClust.setName(tempClust2.getName() + Integer.toString(i));
            tempClust.addPoint(pValues.get(i));
            C2.add(tempClust);
            C2old.add(tempClust2);


        }

        while (true)
        {
        	double distance=0.0;
            ArrayList<Integer> index1 = new ArrayList<Integer>();
            ArrayList<Integer> index2 = new ArrayList<Integer>();
            ArrayList<Double> mins = new ArrayList<Double>();
            for (int i = 0; i < C.size(); i++)
            {

                double min = Double.MAX_VALUE;
                int index11 = 0;
                int index22 = 0;
                for (int j = 0; j < C2.size(); j++)
                {
                	if(dtype==0)
                		distance = manhatten(C.get(i).getCentoid(), (C2.get(j).getCentoid()));
                	else
                		distance = euclidean(C.get(i).getCentoid() ,(C2.get(j).getCentoid()));
                    if (distance <= min)
                    {
                        min = distance;
                        index11 = i;
                        index22 = j;
                    }
                }
                mins.add(min);
                index1.add(index11);
                index2.add(index22);

            }

            //print out
            for (int i = 0; i < C.size(); i++)
            {
                file.append(" ," + C.get(i).getName());
            }
            file.append("\n");

            for (int i = 0; i < C2.size(); i++)
            {
                file.append(C2.get(i).getName() + ",");
                for (int j = 0; j < C.size(); j++)
                {
                	
                	if(dtype==0)
                		distance = manhatten(C2.get(i).getCentoid(), (C.get(j).getCentoid()));
                	else
                		distance = euclidean(C2.get(i).getCentoid(), (C.get(j).getCentoid()));
                	                   
                    file.append(distance + ",");
                }
                file.append("\n");
            }
            file.append("\n");
            file.append("\n");
            //whipe out all points in each cluster

            for (int i = 0; i < C2.size(); i++)
            {
                C2old.get(i).removeAllPoints();
                C2old.get(i).setName( C2.get(i).getName());
                for (int k = 0; k < C2.get(i).getNumberOfPoints(); k++)
                {
                    C2old.get(i).addPoint(C2.get(i).getPointAt(k));
                }

                C2.get(i).removeAllPoints();
                C2.get(i).setName("");
            }
            //re-assign points to correct clusters
            for (int i = 0; i < mins.size(); i++)
            {
                C2.get(index2.get(i)).addPoint(C.get(index1.get(i)).getPointAt(0));
                C2.get(index2.get(i)).setName(C2.get(index2.get(i)).getName() + C.get(index1.get(i)).getName() + " | ");
            }
            int counter = 0;
            for (int i = 0; i < C2.size(); i++)
            {
                if (C2old.get(i) == C2.get(i))
                {
                    counter++;
                }
            }
            //check if we are finished
            if (counter == C2.size())
            {
                //print out
                for (int i = 0; i < C.size(); i++)
                {
                    file.append(" ," + C.get(i).getName());
                }
                file.append("\n");

                for (int i = 0; i < C2.size(); i++)
                {
                    file.append(C2.get(i).getName() + ",");
                    for (int j = 0; j < C.size(); j++)
                    {
                    	
                    	if(dtype==0)
                    		distance = manhatten(C2.get(i).getCentoid(), (C.get(j).getCentoid()));
                    	else
                    		distance = euclidean(C2.get(i).getCentoid(), (C.get(j).getCentoid()));
                    	
                 
                        file.append(distance + ",");
                    }
                    file.append("\n");
                }
                file.append("\n");
                for (int i = 0; i < C2.size(); i++)
                {
                	file.append(C2.get(i).getName());
                	for(int j=0;j<C2.get(i).getPointAt(0).getDimensionSize();j++)
                    	file.append(",CenterD"+(i+1)+"= " + C2.get(i).getCentoid().getValueAtDimension(j));
                    file.append("\n");
                }
                file.append("\n");
                break;
            }

        }
        
        
		file.close();
		return false;
	}
	
	public static boolean Z_Score(ArrayList<Point> pValues)throws IOException
	{
		//variables
		
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
        
        //start of actual algorithm
        
        
        
        
        file.close();
		return false;
	}
}