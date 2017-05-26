import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Main start = new Main();
			}
		});

	}
	
	public static JButton button(String name) {
        JButton newButton = new JButton(name);
        newButton.setFocusPainted(false);
        newButton.setFont(new Font("Arial", Font.BOLD, 16));
        newButton.setBackground(Color.white);
        newButton.setMaximumSize(new Dimension(300, 30));
        return newButton;
    }

	public Main() {
		final JFrame splashScreen = new JFrame();
		splashScreen.setSize(1366, 768);
		splashScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
		final JFrame mainScreen = new JFrame();
		mainScreen.setSize(1366, 768);
		mainScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainScreen.setUndecorated(true);
		mainScreen.setVisible(false);
		try {
			splashScreen
					.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("8 Ball Pool SplashScreen.jpg")))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		splashScreen.getContentPane().setLayout(new BorderLayout(5, 5));
		splashScreen.setUndecorated(true);
		splashScreen.setVisible(true);
		JLabel anyKeyLabel = new JLabel("PRESS ANY KEY TO CONTINUE...");
		anyKeyLabel.setFont(new Font("Roboto", Font.PLAIN, 40));
		anyKeyLabel.setForeground(Color.WHITE);
		splashScreen.getContentPane().add(anyKeyLabel, BorderLayout.PAGE_END);

		splashScreen.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				splashScreen.dispose();
				mainScreen.setVisible(true);
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}
		});

		splashScreen.pack();

		try {
			mainScreen.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("pool-2.jpg")))));
		} catch (IOException e) {
			e.printStackTrace();
		}

		mainScreen.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JLabel pressExit=new JLabel("Press 'e' to Exit");
		pressExit.setFont(new Font("Impact",Font.PLAIN,22));
		pressExit.setForeground(Color.WHITE);
		mainScreen.getContentPane().add(pressExit,c);
		mainScreen.setFocusable(true);
		mainScreen.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar()== 'e') {
					if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						System.exit(0);
					}
				}
			}
			
			public void keyTyped(KeyEvent e) {
			}
			public void keyReleased(KeyEvent e) {
			}
		});
		
		JPanel menuPane=new JPanel();
		menuPane.setBackground(Color.black);
		menuPane.setLayout(new BoxLayout(menuPane,BoxLayout.Y_AXIS));
		c.gridx=10;
		c.gridy=10;
		c.weightx=1.0;
		c.weighty=1.0; 
		menuPane.setPreferredSize(new Dimension(400,550));
		mainScreen.getContentPane().add(menuPane,c);
		JButton play=button("Play");
		menuPane.add(play);
		
	}

}