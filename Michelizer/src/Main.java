import javax.swing.JFrame;

public class Main
{
	public static void main(String[] args)
	{
		GUI go = new GUI();

		go.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		go.setSize(310, 450);
		go.setResizable(false);
		go.setVisible(true);
	}
}