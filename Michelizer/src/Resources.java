public class Resources
{
	public static final int QUEUES 						= 0;
	public static final int SERVICE_DEMAND				= 1;
	public static final int SYSTEMS						= 2;
	public static final int POISSON						= 3;
	public static final int DISK_ACCESS_TIME			= 4;
	public static final int CLUSTERING					= 5;
	
	public static final int INFINITE_QUEUES				= 0;
	public static final int FINITE_QUEUES				= 1;
	
	public static final int CLOSED_SYSTEM				= 0;
	public static final int GENERAL_SYSTEM				= 1;
	
	public static final String HELP_SERVICE_DEMAND		= "SD";
	public static final String HELP_CLUSTERING			= "C";
	public static final String HELP_DISK_ACCESS_TIME 	= "DAT";
	public static final String HELP_POISSON 			= "P";
	public static final String HELP_INFINITE_QUEUES 	= "IQ";
	public static final String HELP_FINITE_QUEUES 		= "FQ";
	public static final String HELP_CLOSED_SYSTEM 		= "CS";
	public static final String HELP_GENERAL_SYSTEM 		= "General systems can be used to calculate a queue size on an open system.\n\nNumber of Terms (k) can be thought as the number of processors or how many\npeople can be doing something at once arival rate (Lambda) is in seconds and is\nsimply the amount of people/jobs are comming into the system per second.\n\nService rate (mu) is also in seconds and is the rate at which people/jobs are leaving\nthe system. it can be variable, if the processing speed is 10 people per second and\nk=4, then mu changes. if only 1 person is in the system then mu is 10. if there are 2\npeople in the system (for k=2) then mu=2*10, for 3 people mu=3*10 and so on.\n\nExample input:\nk: 5\nLambda: 10\nmu: 10, 20, 30, 40, 50\n\n*PLEASE HELP WITH THIS HELP PAGE. ANYTHING YOU CAN THINK OF THAT MAY\nHELP WOULD BE APPRICIATED";
}