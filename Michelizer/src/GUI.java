import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GUI extends JFrame implements ChangeListener, ActionListener, KeyListener
{
	private static final long serialVersionUID = 1L;

	private static int dimensionCount = 3;
	private static int clusterCount = 4;
	
	private static final int COMP_COMPLETE		= 0;
	private static final int ERROR_NUMERIC		= 1;
	private static final int WARNING_OVERWRITE	= 2;
	
	private static final int DISK_NOTE			= 0;
	private static final int DISK_DATA_ERROR	= 1;
	private static final int DISK_RANDOM_ERROR	= 2;
	
	// Message Strings
	String[] commonStrings = {	"Computation Completed Successfully!",
								"Error: All Values MUST be Numeric!",
								"File Exists!\n\nOverwrite?"};

	String DoubleWarning = "One or more entries were not numeric!  Reset to \"0.0\", be careful next time!";
	String poissonNote = "*For a K Range, put '..' between two integers";

	String[] diskAccessTimeMessages = {	"*Input Data Locations Separated by Commas: \",\"",
									"Data Locations must be Integers!",
									"Seek Random Time must be an Integer!" };

	// Overall Tabbed Pane
	JTabbedPane jTP = new JTabbedPane();
	JPanel pane_serviceDemand = new JPanel(new FlowLayout());
	JPanel pane_diskAccessTime = new JPanel(new FlowLayout());
	JPanel pane_clustering = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel pane_poisson = new JPanel(new FlowLayout());
	JPanel pane_queue = new JPanel(new FlowLayout());
	
	JTabbedPane jTPQueue = new JTabbedPane();
	JPanel pane_infiniteQueue = new JPanel(new FlowLayout());
	JPanel pane_finiteQueue = new JPanel(new FlowLayout());
	
	// GUI Panels
	JPanel p_serviceDemand = new JPanel(new GridLayout(9,1));
	JPanel p_diskAccessTime = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel p_clustering = new JPanel(new GridLayout(4,1));
	JPanel p_poisson = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel p_queue = new JPanel(new GridLayout(9,1));
	
	JPanel p_infiniteQueue = new JPanel(new GridLayout(9,1));
	JPanel p_finiteQueue = new JPanel(new GridLayout(9,1));
	
	// Point Field
	JPanel pointPanel = new JPanel(new GridLayout(5,3));
	JScrollPane pointScrollPane = new JScrollPane(pointPanel);
	
	// Service Demand Output
	JPanel serviceDemandOutputPanel = new JPanel(new GridLayout(8,1));
	JScrollPane serviceDemandOutputPane = new JScrollPane(serviceDemandOutputPanel);

	// Service Demand Button Panel and Buttons
	JButton serviceDemandCalculateButton = new JButton("Calculate");
	JButton serviceDemandClearButton = new JButton("Clear");
	
	// Poisson Probability Button Panel and Buttons
	JButton diskAccessTimeCalculateButton = new JButton("Calculate");
	JButton diskAccessTimeClearButton = new JButton("Clear");
	
	// Clustering Button Panel and Buttons
	JPanel clusteringButtonPanel1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	JPanel clusteringButtonPanel2 = new JPanel();
	JButton clusteringCalculateButton = new JButton("Calculate");
	JButton clusteringClearButton = new JButton("Clear Fields");
	JButton clusteringAddButton = new JButton("+");
	JButton clusteringRemoveButton = new JButton("-");
	
	// Poisson Probability Button Panel and Buttons
	JButton poissonCalculateButton = new JButton("Calculate");
	JButton poissonClearButton = new JButton("Clear");

	// Infinite Queue Button Panel and Buttons
	JButton infiniteQueueCalculateButton = new JButton("Calculate");
	JButton infiniteQueueClearButton = new JButton("Clear");
	
	// Infinite Queue Button Panel and Buttons
	JButton finiteQueueCalculateButton = new JButton("Calculate");
	JButton finiteQueueClearButton = new JButton("Clear");
	
	// Infinite Queue Output
	JPanel infiniteQueueOutputPanel = new JPanel(new GridLayout(8,1));
	JScrollPane infiniteQueueOutputPane = new JScrollPane(infiniteQueueOutputPanel);
	
	// Finite Queue Output
	JPanel finiteQueueOutputPanel = new JPanel(new GridLayout(8,1));
	JScrollPane finiteQueueOutputPane = new JScrollPane(finiteQueueOutputPanel);
	
	// Clustering Fields
	ArrayList<JComboBox<String>> comboBoxes = new ArrayList<JComboBox<String>>();
	ArrayList<JSpinner> spinners = new ArrayList<JSpinner>();

	// Label Strings
	String[] serviceDemandInputLabelStrings = {	"Lambda", "Random %",
			"Block Size (Bytes)", "Run Length",
			"RPM", "Seek-Random (ms)",
			"Transfer Rate (MB/s", "Controller Time (ms)",
			"Iterations"									};
	
	String[] diskAccessTimeLabelStrings = { "Data Locations*", "Seek Random Time (ms)" };
	
	String[] clusteringLabelStrings 	= {	"Algorithm", "Distance Type",
											"Dimensions", "Clusters"		};
	String[] clusteringAlgorithmStrings	= {	"MST", "K-Means", "Z-Score"		};
	String[] clusteringDistanceStrings	= {	"Manhatten", "Euclidean"		};

	String[] poissonLabelStrings 	= {	"\u03BB", "K*", "t" };

	String[] queueLabelStrings	= { "Jobs per Second (\u03BB)", "S0", "\u00B5", "w" };
	
	GUI()
	{
		super("Michelizer - The All-In-One ECE 460 Solver");
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.LEFT);
		setLayout(fl);
		
		createPane1();
		createPane2();
		createPane3();
		createPane4();
		createPane5();
		
		jTP.setPreferredSize(new Dimension(300, 480));
		jTP.addTab("Clustering", pane_clustering);
		jTP.addTab("Poisson", pane_poisson);
		jTP.addTab("Queues", pane_queue);
		jTP.addTab("Service Demand", pane_serviceDemand);
		jTP.addTab("Disk Access Time", pane_diskAccessTime);
		add(jTP);
		jTP.setSelectedIndex(3);
		recursivelyAddKeyListener((JComponent)((JComponent)((JComponent)this.getComponent(0)).getComponent(1)).getComponent(0));
	}
	
	public void createPane1()
	{
		for(int i = 0; i < serviceDemandInputLabelStrings.length; i++)
		{
			JLabel jL = new JLabel();
			jL.setFont(new Font("Arial", Font.PLAIN, 12));
			jL.setText(serviceDemandInputLabelStrings[i] + ":");
			p_serviceDemand.add(jL);
			p_serviceDemand.add(new JTextField());
		}
		
		p_serviceDemand.setPreferredSize(new Dimension(275, 200));
		serviceDemandCalculateButton.addActionListener(this);
		serviceDemandClearButton.addActionListener(this);
		
		pane_serviceDemand.add(p_serviceDemand);
		pane_serviceDemand.add(serviceDemandCalculateButton);
		pane_serviceDemand.add(serviceDemandClearButton);
		pane_serviceDemand.add(serviceDemandOutputPane);
		serviceDemandOutputPane.setPreferredSize(new Dimension(285, 150));
	}
	
	public void createPane2()
	{
		for(int i = 0; i < diskAccessTimeLabelStrings.length; i++)
		{
			JLabel jL = new JLabel();
			jL.setFont(new Font("Arial", Font.PLAIN, 14));
			jL.setText(diskAccessTimeLabelStrings[i] + ":");
			JTextField jTF = new JTextField();
			jTF.setPreferredSize(new Dimension(175, 25));
			p_diskAccessTime.add(jL);
			p_diskAccessTime.add(jTF);
		}
		
		p_diskAccessTime.setPreferredSize(new Dimension(210, 125));
		diskAccessTimeCalculateButton.addActionListener(this);
		diskAccessTimeClearButton.addActionListener(this);
		
		pane_diskAccessTime.add(p_diskAccessTime);
		pane_diskAccessTime.add(diskAccessTimeCalculateButton);
		pane_diskAccessTime.add(diskAccessTimeClearButton);
		
		JLabel jL = new JLabel();
		jL.setFont(new Font("Arial", Font.BOLD, 10));
		jL.setText(diskAccessTimeMessages[DISK_NOTE]);
		pane_diskAccessTime.add(jL);
	}
	
	public void createPane3()
	{
		comboBoxes.add(new JComboBox<String>(clusteringAlgorithmStrings));
		comboBoxes.add(new JComboBox<String>(clusteringDistanceStrings));
		
		comboBoxes.get(0).addActionListener(this);
		
		spinners.add(new JSpinner(new SpinnerNumberModel(3, 1, 10, 1)));
		spinners.add(new JSpinner(new SpinnerNumberModel(4, 1, 100, 1)));
		
		spinners.get(0).setValue(dimensionCount);
		spinners.get(0).addChangeListener(this);
		spinners.get(1).setValue(clusterCount);
		
		for(int i = 0; i < clusteringLabelStrings.length; i++)
		{
			p_clustering.add(new JLabel(clusteringLabelStrings[i] + ":"));
			p_clustering.add((i > 1)?spinners.get(i-2):comboBoxes.get(i));
		}

		pointScrollPane.setPreferredSize(new Dimension(285, 200));
		pane_clustering.add(p_clustering);
		pane_clustering.add(pointScrollPane);
		
		clusteringButtonPanel1.add(clusteringAddButton);
		clusteringButtonPanel1.add(clusteringRemoveButton);
		clusteringButtonPanel2.add(clusteringCalculateButton);
		clusteringButtonPanel2.add(clusteringClearButton);
		clusteringAddButton.addActionListener(this);
		clusteringRemoveButton.addActionListener(this);
		clusteringCalculateButton.addActionListener(this);
		clusteringClearButton.addActionListener(this);
		
		pane_clustering.add(clusteringButtonPanel1);
		pane_clustering.add(clusteringButtonPanel2);
		
		updateScrollPane(5, dimensionCount, null);
	}
	
	public void createPane4()
	{
		for(int i = 0; i < poissonLabelStrings.length; i++)
		{
			JLabel jL = new JLabel();
			jL.setFont(new Font("Arial", Font.PLAIN, 14));
			jL.setText(poissonLabelStrings[i] + ":");
			jL.setPreferredSize(new Dimension(22, 25));
			JTextField jTF = new JTextField();
			jTF.setPreferredSize(new Dimension(175, 25));
			p_poisson.add(jL);
			p_poisson.add(jTF);
		}
		
		p_poisson.setPreferredSize(new Dimension(210, 100));
		poissonCalculateButton.addActionListener(this);
		poissonClearButton.addActionListener(this);
		
		pane_poisson.add(p_poisson);
		pane_poisson.add(poissonCalculateButton);
		pane_poisson.add(poissonClearButton);
		
		JLabel jL = new JLabel();
		jL.setFont(new Font("Arial", Font.BOLD, 10));
		jL.setText(poissonNote);
		pane_poisson.add(jL);
	}
	
	public void createPane5()
	{
		createInfiniteQueuePane();
		createFiniteQueuePane();
		
		jTPQueue.setPreferredSize(new Dimension(290, 420));
		jTPQueue.addTab("Infinite", pane_infiniteQueue);
		jTPQueue.addTab("Finite", pane_finiteQueue);
		pane_queue.add(jTPQueue);
	}
	
	public void createInfiniteQueuePane()
	{
		JLabel jL = new JLabel();
		jL.setFont(new Font("Arial", Font.PLAIN, 12));
		jL.setText(queueLabelStrings[0] + ":");
		p_infiniteQueue.add(jL);
		p_infiniteQueue.add(new JTextField());

		JComboBox jCB = new JComboBox();
		jCB.addItem(queueLabelStrings[1]);
		jCB.addItem(queueLabelStrings[2]);

		p_infiniteQueue.add(jCB);
		p_infiniteQueue.add(new JTextField());
		
		p_infiniteQueue.setPreferredSize(new Dimension(275, 200));
		infiniteQueueCalculateButton.addActionListener(this);
		infiniteQueueClearButton.addActionListener(this);
		
		pane_infiniteQueue.add(p_infiniteQueue);
		pane_infiniteQueue.add(infiniteQueueCalculateButton);
		pane_infiniteQueue.add(infiniteQueueClearButton);
		pane_infiniteQueue.add(infiniteQueueOutputPane);
		infiniteQueueOutputPane.setPreferredSize(new Dimension(285, 150));
	}
	
	public void createFiniteQueuePane()
	{
		JLabel jL = new JLabel();
		jL.setFont(new Font("Arial", Font.PLAIN, 12));
		jL.setText(queueLabelStrings[0] + ":");
		p_finiteQueue.add(jL);
		p_finiteQueue.add(new JTextField());

		JComboBox jCB = new JComboBox();
		jCB.addItem(queueLabelStrings[1]);
		jCB.addItem(queueLabelStrings[2]);

		p_finiteQueue.add(jCB);
		p_finiteQueue.add(new JTextField());
		
		JLabel jL2 = new JLabel();
		jL2.setFont(new Font("Arial", Font.PLAIN, 12));
		jL2.setText(queueLabelStrings[3] + ":");
		p_finiteQueue.add(jL2);
		p_finiteQueue.add(new JTextField());
		
		p_finiteQueue.setPreferredSize(new Dimension(275, 200));
		finiteQueueCalculateButton.addActionListener(this);
		finiteQueueClearButton.addActionListener(this);
		
		pane_finiteQueue.add(p_finiteQueue);
		pane_finiteQueue.add(finiteQueueCalculateButton);
		pane_finiteQueue.add(finiteQueueClearButton);
		pane_finiteQueue.add(finiteQueueOutputPane);
		finiteQueueOutputPane.setPreferredSize(new Dimension(285, 150));
	}
	
	public void updateScrollPane(int points, int dimensions, ArrayList<String> savedPoints)
	{
		pointPanel.removeAll();
		((GridLayout)pointPanel.getLayout()).setRows(points+1);
		((GridLayout)pointPanel.getLayout()).setColumns(dimensions);
		for(int i = 0; i < dimensions; i++)
		{
			JLabel jL = new JLabel("D" + (i+1));
			jL.setHorizontalAlignment(JLabel.CENTER);
			pointPanel.add(jL);
		}

		if(savedPoints == null)
		{
			for(int i = 0; i < points; i++)
			{
				for(int j = 0; j < dimensions; j++)
				{
					pointPanel.add(new JTextField("0.0"));
				}
			}
		}
		else
		{
			int index = 0;
			for(int i = 0; i < points; i++)
			{
				for(int j = 0; j < dimensions; j++)
				{
					try { pointPanel.add(new JTextField(savedPoints.get(index++))); }
					catch (Exception e) { pointPanel.add(new JTextField("0.0")); }
				}
			}
		}

		revalidate();
		repaint();
		pointScrollPane.getVerticalScrollBar().setValue(pointScrollPane.getVerticalScrollBar().getMaximum());
	}
	
	public int getGUIPointCount(int dimToUse)
	{
		return pointPanel.getComponentCount() / dimToUse - 1;
	}
	
	public ArrayList<ClusterPoint> getPointsFromGUI(int dimensionCount)
	{
		ArrayList<ClusterPoint> points = new ArrayList<ClusterPoint>();
		boolean someValuesReset = false;

		for(int i = 1; i <= getGUIPointCount(dimensionCount); i++) // Line to Check
		{
			ClusterPoint p = new ClusterPoint();
			for(int j = 0; j < dimensionCount; j++)
			{
				String val = ((JTextField)pointPanel.getComponent((i*dimensionCount) + j)).getText().trim();
				Double dValue;
				try
				{
					// See if the value can be parse as a Double
 					dValue = Double.parseDouble(val);
				}
				catch (Exception e)
				{
					// If not reset and warn the user to be careful
					if(!val.equals(""))
						someValuesReset = true;

					dValue = 0.0;
				}
				p.addDimension(dValue);
			}
			points.add(p);
		}
		
		if(someValuesReset)
			JOptionPane.showMessageDialog(this, DoubleWarning);

		return points;
	}

	public ArrayList<String> getDimensionsFromPoints(ArrayList<ClusterPoint> p)
	{
		ArrayList<String> pointDimensionValues = new ArrayList<String>();
		for(int i = 0; i < p.size(); i++)
		{
			for(int j = 0; j < dimensionCount; j++)
			{
				try { pointDimensionValues.add(Double.toString(p.get(i).getValueAtDimension(j))); }
				catch (Exception e) { pointDimensionValues.add("0.0"); }
			}
		}
		
		return pointDimensionValues;
	}
	@Override
	public void stateChanged(ChangeEvent e)
	{
		if(e.getSource() == spinners.get(0))
		{
			int oldDim = dimensionCount;
			dimensionCount = (Integer)spinners.get(0).getValue();
			updateScrollPane(getGUIPointCount(oldDim), dimensionCount, getDimensionsFromPoints(getPointsFromGUI(oldDim)));
		}
		else if(e.getSource() == comboBoxes.get(0))
		{
			switch(comboBoxes.get(0).getSelectedIndex())
			{
				case Functions.ZSCORE:
					comboBoxes.get(1).setEditable(false);
					spinners.get(1).setEnabled(false);
					break;
				default:
					comboBoxes.get(1).setEditable(true);
					spinners.get(1).setEnabled(true);
					break;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == serviceDemandCalculateButton)
		{
			ArrayList<Double> parameters = new ArrayList<Double>();
			boolean allValid = true;
			for(int i = 0; i < p_serviceDemand.getComponentCount()/2; i++)
			{
				try { parameters.add(Double.parseDouble(((JTextField)p_serviceDemand.getComponent(i*2 + 1)).getText().trim())); }
				catch (Exception exception) { allValid = false; }
			}
			
			if(!allValid)
				JOptionPane.showMessageDialog(this, commonStrings[ERROR_NUMERIC]);
			else
			{
				ArrayList<String> output = Functions.serviceDemand(parameters);
				serviceDemandOutputPanel.removeAll();
				((GridLayout)serviceDemandOutputPanel.getLayout()).setRows(output.size());
				for(int i = 0; i < output.size(); i++)
				{
					JLabel jL = new JLabel();
					jL.setFont(new Font("Arial", (output.get(i).charAt(14) == ':')?Font.BOLD:Font.PLAIN, 12));
					jL.setText(" " + output.get(i));
					serviceDemandOutputPanel.add(jL);
				}
				
				revalidate();
				repaint();
				serviceDemandOutputPane.getVerticalScrollBar().setValue(serviceDemandOutputPane.getVerticalScrollBar().getMaximum());
			}
		}
		else if(e.getSource() == serviceDemandClearButton)
		{
			for(int i = 0; i < p_serviceDemand.getComponentCount()/2; i++)
				((JTextField)p_serviceDemand.getComponent(i*2 + 1)).setText("");
			
			serviceDemandOutputPanel.removeAll();
			revalidate();
			repaint();
		}
		else if(e.getSource() == diskAccessTimeCalculateButton)
		{
			ArrayList<Integer> locations = new ArrayList<Integer>();
			String output = "";
			int randomSeekTime = 0;

			try { randomSeekTime = Integer.parseInt(((JTextField)p_diskAccessTime.getComponent(3)).getText()); }
			catch(Exception exception) { output = diskAccessTimeMessages[DISK_RANDOM_ERROR]; };
			
			String[] vals = ((JTextField)p_diskAccessTime.getComponent(1)).getText().split(",");
			for(int i = 0; i < vals.length; i++)
			{
				try { locations.add(Integer.parseInt(vals[i].trim())); }
				catch (Exception exception) { output = diskAccessTimeMessages[DISK_DATA_ERROR]; }
			}
			
			if(output.equals(""))
				output = Functions.diskAccessTime(locations, randomSeekTime);
			
			JOptionPane.showMessageDialog(this, output);
		}
		else if(e.getSource() == diskAccessTimeClearButton)
		{
			for(int i = 0; i < p_diskAccessTime.getComponentCount()/2; i++)
				((JTextField)p_diskAccessTime.getComponent(i*2 + 1)).setText("");
		}
		else if(e.getSource() == clusteringCalculateButton)
		{
			ArrayList<ClusterPoint> points = getPointsFromGUI(dimensionCount);
			int distanceType = (((String)comboBoxes.get(0).getSelectedItem()).equals("Manhatten"))?Functions.MANHATTEN:Functions.EUCLIDEAN;
			int clusterCount = (Integer)spinners.get(1).getValue();

			boolean result = false;
			switch(comboBoxes.get(0).getSelectedIndex())
			{
				case Functions.MST:
					try { result = Functions.MST(points, clusterCount, distanceType); }
					catch (IOException exception) { exception.printStackTrace(); }
					break;
				case Functions.KMEANS:
					try { result = Functions.K_Means(points, clusterCount, distanceType); }
					catch (IOException exception) { exception.printStackTrace(); }
					break;
				case Functions.ZSCORE:
					try { result = Functions.Z_Score(points); }
					catch (IOException exception) { exception.printStackTrace(); }
					break;
			}
			
			//JOptionPane.showMessageDialog(this, (result)?clusteringSuccessfulComputation:clusteringErrornousComputation);
		}
		else if(e.getSource() == clusteringAddButton)
			updateScrollPane(getGUIPointCount(dimensionCount) + 1, dimensionCount, getDimensionsFromPoints(getPointsFromGUI(dimensionCount)));
		else if(e.getSource() == clusteringRemoveButton)
		{
			if(getGUIPointCount(dimensionCount) > 1)
				updateScrollPane(getGUIPointCount(dimensionCount) - 1, dimensionCount, getDimensionsFromPoints(getPointsFromGUI(dimensionCount)));
		}
		else if(e.getSource() == clusteringClearButton)
		{
			for(int i = dimensionCount; i < getGUIPointCount(dimensionCount)*dimensionCount+dimensionCount; i++)
				((JTextField)pointPanel.getComponent(i)).setText("");
		}
		else if(e.getSource() == poissonCalculateButton)
		{
			ArrayList<String> parameters = new ArrayList<String>();
			boolean allValid = true;
			for(int i = 0; i < p_poisson.getComponentCount()/2; i++)
				parameters.add(((JTextField)p_poisson.getComponent(i*2 + 1)).getText().trim());
			
			try { JOptionPane.showMessageDialog(this, (allValid)?Functions.poisson(parameters):commonStrings[ERROR_NUMERIC]); }
			catch (HeadlessException e1)	{ e1.printStackTrace(); }
			catch (ScriptException e1)		{ e1.printStackTrace(); }
		}
		else if(e.getSource() == poissonClearButton)
		{
			for(int i = 0; i < p_poisson.getComponentCount()/2; i++)
				((JTextField)p_poisson.getComponent(i*2 + 1)).setText("");
		}
		else if(e.getSource() == infiniteQueueCalculateButton)
		{
			ArrayList<Double> parameters = new ArrayList<Double>();
			boolean allValid = true;
			
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("JavaScript");
			for(int i = 0; i < p_infiniteQueue.getComponentCount()/2; i++)
			{
				try { parameters.add(Double.parseDouble(((JTextField)p_infiniteQueue.getComponent(i*2 + 1)).getText().trim())); }
				catch (Exception exception)
				{
					try
					{
						double val = (double)engine.eval(((JTextField)p_infiniteQueue.getComponent(i*2 + 1)).getText());
						parameters.add(val);
					}
					catch (ScriptException sException) { allValid = false; }
				}
			}
			
			if(!allValid)
				JOptionPane.showMessageDialog(this, commonStrings[ERROR_NUMERIC]);
			else
			{
				ArrayList<String> output = Functions.infiniteQueue(parameters, ((JComboBox)p_infiniteQueue.getComponent(2)).getSelectedIndex());
				infiniteQueueOutputPanel.removeAll();
				((GridLayout)infiniteQueueOutputPanel.getLayout()).setRows(output.size());
				for(int i = 0; i < output.size(); i++)
				{
					JLabel jL = new JLabel();
					jL.setFont(new Font("Arial", Font.PLAIN, 12));
					jL.setText(" " + output.get(i));
					infiniteQueueOutputPanel.add(jL);
				}
				
				revalidate();
				repaint();
				infiniteQueueOutputPane.getVerticalScrollBar().setValue(infiniteQueueOutputPane.getVerticalScrollBar().getMaximum());
			}			
		}
		else if(e.getSource() == infiniteQueueClearButton)
		{
			((JTextField)p_infiniteQueue.getComponent(1)).setText("");
			((JComboBox)p_infiniteQueue.getComponent(2)).setSelectedIndex(0);
			((JTextField)p_infiniteQueue.getComponent(3)).setText("");
			
			finiteQueueOutputPanel.removeAll();
			revalidate();
			repaint();	
			
			infiniteQueueOutputPanel.removeAll();
			revalidate();
			repaint();		
		}
		else if(e.getSource() == finiteQueueCalculateButton)
		{
			ArrayList<Double> parameters = new ArrayList<Double>();
			boolean allValid = true;
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("JavaScript");
			for(int i = 0; i < p_finiteQueue.getComponentCount()/2; i++)
			{
				try { parameters.add(Double.parseDouble(((JTextField)p_finiteQueue.getComponent(i*2 + 1)).getText().trim())); }
				catch (Exception exception)
				{
					try
					{
						double val = (double)engine.eval(((JTextField)p_finiteQueue.getComponent(i*2 + 1)).getText());
						parameters.add(val);
					}
					catch(ScriptException sException) { allValid = false; }
				}
			}
			
			if(!allValid)
				JOptionPane.showMessageDialog(this, commonStrings[ERROR_NUMERIC]);
			else
			{
				ArrayList<String> output = Functions.finiteQueue(parameters, ((JComboBox)p_finiteQueue.getComponent(2)).getSelectedIndex());
				finiteQueueOutputPanel.removeAll();
				((GridLayout)finiteQueueOutputPanel.getLayout()).setRows(output.size());
				for(int i = 0; i < output.size(); i++)
				{
					JLabel jL = new JLabel();
					jL.setFont(new Font("Arial", Font.PLAIN, 12));
					jL.setText(" " + output.get(i));
					finiteQueueOutputPanel.add(jL);
				}
				
				revalidate();
				repaint();
				finiteQueueOutputPane.getVerticalScrollBar().setValue(finiteQueueOutputPane.getVerticalScrollBar().getMaximum());
			}			
		}
		else if(e.getSource() == finiteQueueClearButton)
		{
			((JTextField)p_finiteQueue.getComponent(1)).setText("");
			((JComboBox)p_finiteQueue.getComponent(2)).setSelectedIndex(0);
			((JTextField)p_infiniteQueue.getComponent(3)).setText("");
			((JTextField)p_finiteQueue.getComponent(5)).setText("");
			
			finiteQueueOutputPanel.removeAll();
			revalidate();
			repaint();		
		}
		else if(e.getSource() == comboBoxes.get(0))
		{
			switch(comboBoxes.get(0).getSelectedIndex())
			{
				case Functions.ZSCORE:
					comboBoxes.get(1).setEnabled(false);
					spinners.get(1).setEnabled(false);
					break;
				default:
					comboBoxes.get(1).setEnabled(true);
					spinners.get(1).setEnabled(true);
					break;
			}
		}
	}
	
	public void createScreenShot()
	{
		File screenshotFile = null;
		JFileChooser chooser = new JFileChooser();
		UIManager.put("FileChooser.saveDialogTitleText", "Save Michelizer Screenshot");
		SwingUtilities.updateComponentTreeUI(chooser);
		chooser.setSelectedFile(new File("Michelizer_Output.jpg"));

		screenshotFile = chooser.getSelectedFile();
	
		if(JFileChooser.APPROVE_OPTION == chooser.showSaveDialog(this))
		{
			// Wait for JFileChooser to close!
			try { Thread.sleep(750); }
			catch (InterruptedException ie) { ie.printStackTrace(); }

			screenshotFile = chooser.getSelectedFile();
			String path = screenshotFile.getAbsolutePath().substring(0, screenshotFile.getAbsolutePath().length() - screenshotFile.getName().length());
			String fileName = screenshotFile.getName().substring(0, screenshotFile.getName().length()-4);
			String extension = ".jpg";

			if(screenshotFile.exists())
			{
				int counter = 1;
				while(screenshotFile.exists())
					screenshotFile = new File(path + fileName + " (" + counter++ + ")" + extension);
			}
			
			try
			{
				Rectangle r = new Rectangle(getX(), getY(), getWidth(), getHeight());
				BufferedImage bi = ScreenImage.createImage(r);
				ScreenImage.writeImage(bi, screenshotFile.getAbsolutePath());
			}
			catch(Exception exception) { exception.printStackTrace(); }
		}
	}
	
	public void copyScreenShot()
	{
		Rectangle r = new Rectangle(getX(), getY(), getWidth(), getHeight());
		Image image = null;
		try { image = ScreenImage.createImage(r); }
		catch(AWTException awte) { awte.printStackTrace(); }

		ImageTransferable transferable = new ImageTransferable( image );
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(transferable, null);
	}
	
	static class ImageTransferable implements Transferable
    {
        private Image image;

        public ImageTransferable (Image image)
        {
            this.image = image;
        }

        public Object getTransferData(DataFlavor flavor)
            throws UnsupportedFlavorException
        {
            if (isDataFlavorSupported(flavor))
            {
                return image;
            }
            else
            {
                throw new UnsupportedFlavorException(flavor);
            }
        }

        public boolean isDataFlavorSupported (DataFlavor flavor)
        {
            return flavor == DataFlavor.imageFlavor;
        }

        public DataFlavor[] getTransferDataFlavors ()
        {
            return new DataFlavor[] { DataFlavor.imageFlavor };
        }
    }
	
	public void recursivelyAddKeyListener(JComponent jC)
	{
		if(jC == null)
			return;
		
		for(int i = 0; i < jC.getComponentCount(); i++)
		{
			if((jC instanceof JPanel) || (jC instanceof JTabbedPane) || (jC instanceof JScrollPane))
				((JComponent)jC.getComponent(i)).addKeyListener(this);
			else
				break;
			recursivelyAddKeyListener((JComponent)jC.getComponent(i));
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e.isControlDown() && e.getKeyChar() != 's' && e.getKeyCode() == KeyEvent.VK_S)
			createScreenShot();
		else if(e.isControlDown() && e.getKeyChar() != 'c' && e.getKeyCode() == KeyEvent.VK_C)
			copyScreenShot();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
}