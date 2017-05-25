import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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

	public Main() {
		JFrame splashScreen = new JFrame();
		splashScreen.setSize(1366, 768);
		splashScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
		JFrame mainScreen = new JFrame();
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

		mainScreen.getContentPane().setLayout(new BorderLayout(5, 5));
		JLabel pressExit=new JLabel("Press 'e' to Exit");
		pressExit.setFont(new Font("Impact",Font.PLAIN,22));
		pressExit.setForeground(Color.WHITE);
		mainScreen.getContentPane().add(pressExit,BorderLayout.PAGE_START);
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
		menuPane.setBackground(Color.white);
		menuPane.setMaximumSize(new Dimension(400,500));
		mainScreen.getContentPane().add(menuPane, BorderLayout.CENTER);

	}

}