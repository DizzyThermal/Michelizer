import javax.swing.JFrame;

public class Main extends JFrame
{
	public static void main(String[] args)
	{
		GUI go = new GUI();

		go.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		go.setSize(300, 380);
		go.setResizable(false);
		go.setVisible(true);
	}
}