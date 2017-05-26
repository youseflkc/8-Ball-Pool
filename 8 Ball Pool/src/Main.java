import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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
		newButton.setFont(new Font("Harrington", Font.BOLD, 26));
		newButton.setFocusable(false);
		newButton.setBackground(Color.green);
		newButton.setMaximumSize(new Dimension(225, 50));
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
			splashScreen.setContentPane(
					new JLabel(new ImageIcon(ImageIO.read(new File("8 Ball Pool/Pictures/8 Ball Pool SplashScreen.jpg")))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		splashScreen.getContentPane().setLayout(new BorderLayout(5, 5));
		splashScreen.setUndecorated(true);
		splashScreen.setVisible(true);

		JLabel anyKeyLabel = new JLabel("PRESS ANY KEY TO CONTINUE...");
		anyKeyLabel.setFont(new Font("Roboto", Font.PLAIN, 40));
		anyKeyLabel.setForeground(Color.white);
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

		try {
			mainScreen.setContentPane(
					new JLabel(new ImageIcon(ImageIO.read(new File("8 Ball Pool/Pictures/main menu background.jpg")))));
		} catch (IOException e) {
			e.printStackTrace();
		}

		mainScreen.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();


		JPanel menuPane = new JPanel();
		menuPane.setBackground(new Color(0, 0, 0, 90));
		menuPane.setPreferredSize(new Dimension(400, 600));
		menuPane.setLayout(new BoxLayout(menuPane, BoxLayout.Y_AXIS));
		menuPane.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

		final JPanel helpPane = new JPanel();
		helpPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		helpPane.setLayout(new BoxLayout(helpPane, BoxLayout.Y_AXIS));
		helpPane.setBackground(new Color(0, 0, 0, 25));
		helpPane.setPreferredSize(new Dimension(400, 600));

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 0, 200);
		mainScreen.getContentPane().add(menuPane, c);
		c.insets = new Insets(0, 200, 0, 0);
		c.gridx = 1;
		mainScreen.getContentPane().add(helpPane, c);

		JButton playButton = button("Play");
		playButton.setAlignmentX(menuPane.CENTER_ALIGNMENT);
		menuPane.add(playButton);

		menuPane.add(Box.createRigidArea(new Dimension(0, 75)));

		JButton helpButton = button("Help");
		helpButton.setAlignmentX(menuPane.CENTER_ALIGNMENT);
		menuPane.add(helpButton);

		menuPane.add(Box.createRigidArea(new Dimension(0, 75)));

		JButton settingsButton = button("Settings");
		settingsButton.setAlignmentX(menuPane.CENTER_ALIGNMENT);
		menuPane.add(settingsButton);
		
		menuPane.add(Box.createRigidArea(new Dimension(0, 75)));
		
		JButton exitButton =button("Exit");
		exitButton.setAlignmentX(menuPane.CENTER_ALIGNMENT);
		menuPane.add(exitButton);

		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextArea helpText = new JTextArea();
				helpText.setText("How to Play 8 Ball Pool\n\n There are seven\nsolid-colored balls numbered 1 through 7,"
						+ "\nseven striped balls numbered 9 through 15,\nan 8 ball, and a cue ball. One of the playersis "
						+ "\nrandomly chosen to break (hit the cue ball against the setup of balls) at the start.\nA player (or team)"
						+ "\nwill continue to shoot until committing a foul"
						+ ",\nor failing to legally pocket an object ball (whether intentionally or not). \nThereupon it is the turn of the opposing "
						+ "\nplayer(s). Play alternates in this manner for the remainder of the game. Following a foul, the incoming"
						+ "\nplayer has ball-in-hand anywhere on the table, \nunless the foul occurred on the break shot, as noted previously.");
				
				helpText.setEditable(false);
				helpText.setFont(new Font("Roboto",Font.PLAIN,16));
				helpText.setForeground(Color.WHITE);
				helpText.setBackground(Color.BLACK);
				helpPane.add(helpText);
				mainScreen.revalidate();
			}	
		});
		
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(JOptionPane.showConfirmDialog(null,"Are you sure you want to exit?","Exit",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					System.exit(0);
				}
			}
		});
	}

}