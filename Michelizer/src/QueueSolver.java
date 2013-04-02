import java.util.ArrayList;

public class QueueSolver
{
	// To add a formula to the solve function(s):
	//   1.) Check if ALL the formula's parameters are NOT null
	//   2.) If everything checks out, evaluate

	public final int INFINITE	= 0;
	public final int FINITE		= 1;
	
	public final int QUEUE_TYPE	= 0;
	public final int LAMBDA		= 1;
	public final int MU			= 2;
	public final int S0			= 3;
	public final int P0			= 4;
	public final int U			= 5;
	public final int N			= 6;
	public final int X			= 7;
	public final int R			= 8;

	ArrayList<Double> data;
	ArrayList<Double> pks = new ArrayList<Double>();
	boolean somethingWasSolved = false;
	boolean solved = true;

	public QueueSolver(ArrayList<Double> GUIData)
	{
		data = GUIData;
		
		while(data.contains(null))
		{
			somethingWasSolved = false;
			
			if(data.get(LAMBDA) == null)
				solveLambda();
			if(data.get(MU) == null)
				solveMu();
			if(data.get(S0) == null)
				solveS0();
			if(data.get(P0) == null)
				solveP0();
			if(data.get(U) == null)
				solveU();
			if(data.get(N) == null)
				solveN();
			if(data.get(X) == null)
				solveX();
			if(data.get(R) == null)
				solveR();
			
			if(!somethingWasSolved)
			{
				solved = false;
				break;
			}
		}
	}
	
	public void solveLambda()
	{
		
	}
	
	public void solveMu()
	{
		// MU = 1 / S0
		if(data.get(S0) != null)
			data.set(MU, (1/data.get(S0)));
	}
	
	public void solveS0()
	{
		// S0 = 1 / MU
		if(data.get(MU) != null)
			data.set(S0, (1/data.get(MU)));
	}
	
	public void solveP0()
	{
		// P0 = 1 - U
		if(data.get(U) != null)
			data.set(P0, (1-data.get(U)));
		
		
		
		
		if(data.get(P0) != null)
			solvePks();
	}
	
	public void solvePks()
	{
		if(data.get(QUEUE_TYPE) == INFINITE)
		{
			
		}
		else
		{
			
		}
	}
	
	public void solveU()
	{
		// U = 1 - P0
		if(data.get(P0) != null)
			data.set(U, (1-data.get(P0)));
	}

	public void solveN()
	{
		if(data.get(QUEUE_TYPE) == INFINITE)
		{
			
		}
		else
		{
			
		}
	}
	
	public void solveX()
	{
		if(data.get(QUEUE_TYPE) == INFINITE)
		{
			
		}
		else
		{
			
		}
	}
	
	public void solveR()
	{
		if(data.get(QUEUE_TYPE) == INFINITE)
		{
			
		}
		else
		{
			
		}
	}
}