import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class SpreadSheet extends JPanel implements KeyListener
{
	private static final long serialVersionUID = 1L;
	JTable jT;

	public SpreadSheet(String[][] data)
	{
		super(new GridLayout(1,0));
		String[] emptyLabels = new String[data[0].length];
		for(int i = 0; i < emptyLabels.length; i++)
			emptyLabels[i] = " ";
		
		jT = new JTable(data, emptyLabels);
		TableModel model = new DefaultTableModel(data, emptyLabels)
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		
		jT.setModel(model);
		jT.setTableHeader(null);
		jT.setPreferredScrollableViewportSize(new Dimension(500,70));
		jT.setFillsViewportHeight(true);

		add(new JScrollPane(jT));

		jT.addKeyListener(this);
		addKeyListener(this);
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
				JComponent j = jT;
				while(!(j.getParent() instanceof JFrame))
					j = (JComponent)j.getParent();
					
				Rectangle r = new Rectangle(j.getParent().getX(), j.getParent().getY(), jT.getWidth(), jT.getHeight());
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