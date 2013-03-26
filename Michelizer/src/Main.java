import javax.swing.JFrame;

public class Main
{
	public static void main(String[] args)
	{
		GUI go = new GUI();

		go.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		go.setSize(315, 540);
		go.setResizable(false);
		go.setVisible(true);
	}
}