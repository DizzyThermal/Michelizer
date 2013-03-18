import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class SpreadSheet extends JPanel
{
	private static final long serialVersionUID = 1L;

	public SpreadSheet(String[][] data)
	{
		super(new GridLayout(1,0));
		String[] emptyLabels = new String[data[0].length];
		for(int i = 0; i < emptyLabels.length; i++)
			emptyLabels[i] = " ";
		
		JTable jT = new JTable(data, emptyLabels);
		jT.setTableHeader(null);
		jT.setPreferredScrollableViewportSize(new Dimension(500,70));
		jT.setFillsViewportHeight(true);

		add(new JScrollPane(jT));
	}
}