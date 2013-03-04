import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class GUI extends JFrame implements ActionListener
{
	// Initial Settings
	private static int dimensionCount = 3;
	private static int clusterCount = 4;

	// Parameter GridLayout and Panel
	GridLayout gl = new GridLayout(4,1);
	JPanel p = new JPanel(gl);
	
	// Point Field GridLayout, Panel, and ScrollPane
	GridLayout spGridLayout = new GridLayout(5, 3);
	JPanel spPanel = new JPanel(spGridLayout);
	JScrollPane sp = new JScrollPane(spPanel);

	// Calculate Button
	JButton calculateButton = new JButton("Calculate");
	JButton addButton = new JButton("+");
	JButton removeButton = new JButton("-");

	// Labels and Fields
	ArrayList<JLabel> labels = new ArrayList<JLabel>();
	ArrayList<JComboBox> comboBoxes = new ArrayList<JComboBox>();
	ArrayList<JSpinner> spinners = new ArrayList<JSpinner>();

	// Label Strings
	String[] labelStrings 		= {"Algorithm", "Distance Type", "Dimensions", "Clusters"};
	String[] algorithmStrings	= {"MST", "K-Means", "Z-Score"};
	String[] distanceStrings	= {"Manhatten", "Euclidian"};

	GUI()
	{
		super("Michelizer");
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.LEFT);
		setLayout(fl);

		for(int i = 0; i < labelStrings.length; i++)
			labels.add(new JLabel(labelStrings[i] + ":"));
		
		comboBoxes.add(new JComboBox(algorithmStrings));
		comboBoxes.add(new JComboBox(distanceStrings));
		
		spinners.add(new JSpinner(new SpinnerNumberModel(3, 1, 10, 1)));
		spinners.add(new JSpinner(new SpinnerNumberModel(4, 1, 100, 1)));
		
		spinners.get(0).setValue(dimensionCount);
		spinners.get(1).setValue(clusterCount);
		
		for(int i = 0; i < labels.size(); i++)
		{
			p.add(labels.get(i));
			p.add((i > 1)?spinners.get(i-2):comboBoxes.get(i));
		}

		sp.setPreferredSize(new Dimension(285, 200));
		add(p);
		add(sp);
		
		JPanel pCalculate = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		pCalculate.add(addButton);
		pCalculate.add(removeButton);
		pCalculate.add(calculateButton);
		add(pCalculate);
		
		updateScrollPanelDimensions(10, dimensionCount);
	}
	
	public void updateScrollPanelDimensions(int points, int dimensions)
	{
		spPanel.removeAll();
		spGridLayout.setRows(points+1); // Plus Labels
		spGridLayout.setColumns(dimensions);
		for(int i = 0; i < dimensions; i++)
			spPanel.add(new JLabel("D" + (i+1)), JLabel.CENTER);
			
		for(int i = 0; i < points; i++)
			for(int j = 0; j < dimensions; j++)
				spPanel.add(new JTextField());
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		
	}
}