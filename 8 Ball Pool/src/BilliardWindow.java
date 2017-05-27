import javax.swing.JFrame;
import javax.swing.Timer;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BilliardWindow extends JFrame implements ActionListener {
	// Members
	private Billiard content;
	private Overlay overlay;
	
	// Constructor
	public BilliardWindow () {
		super ();
		
		setTitle ("Billiard");
		setResizable (false);
		pack ();
		
		setSize (Billiard.WIDTH + getInsets ().left + getInsets ().right,
		         Billiard.HEIGHT + getInsets ().top + getInsets ().bottom);
		
		content = new Billiard ();
		setContentPane (content);
		
		overlay = new Overlay ();
		setGlassPane (overlay);
		getGlassPane ().setVisible (true);

		Timer timer = new Timer (20, this);
		timer.start ();
	}
	
	// New frame
	public void actionPerformed (ActionEvent e) {
		repaint ();
	}
}
