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
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class GUI extends JFrame implements ActionListener
{
	GridLayout gl = new GridLayout(4,1);
	JPanel p = new JPanel();
	JPanel spPanel;
	JButton calculate = new JButton("Calculate");
	JScrollPane sp = new JScrollPane();
	ArrayList<JLabel> labels;
	ArrayList<JComboBox> comboBoxes;
	ArrayList<JSpinner> spinners;

	String[] labelStrings 		= {"Algorithm", "Distance Type", "Dimensions", "Clusters"};
	String[] algorithmStrings	= {"MST", "K-Means", "Z-Score"};
	String[] distanceStrings	= {"Manhatten", "Euclidian"};

	GUI()
	{
		super("Michelizer");
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.LEFT);
		setLayout(fl);

		labels = new ArrayList<JLabel>();
		comboBoxes = new ArrayList<JComboBox>();
		spinners = new ArrayList<JSpinner>();
		p = new JPanel();
		p.setLayout(gl);

		for(int i = 0; i < labelStrings.length; i++)
			labels.add(new JLabel(labelStrings[i] + ":"));
		
		comboBoxes.add(new JComboBox(algorithmStrings));
		comboBoxes.add(new JComboBox(distanceStrings));
		
		spinners.add(new JSpinner(new SpinnerNumberModel(3, 1, 10, 1)));
		spinners.add(new JSpinner(new SpinnerNumberModel(4, 1, 100, 1)));
		
		spinners.get(0).setValue(3);
		spinners.get(1).setValue(4);
		
		for(int i = 0; i < labels.size(); i++)
		{
			p.add(labels.get(i));
			p.add((i > 1)?spinners.get(i-2):comboBoxes.get(i));
		}

		sp.setPreferredSize(new Dimension(285, 200));
		for(int i = 0; i < 4; i++)
			sp.add(new JLabel("Hi: " + i));

		add(p);
		add(sp);
		
		JPanel pCalculate = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pCalculate.setPreferredSize(new Dimension(289, 50));
		pCalculate.add(calculate);
		add(pCalculate);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		
	}
}