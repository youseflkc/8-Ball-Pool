import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Main {

	private Overlay overlay;
	private Billiard content;
	String helpString = "\t        How to Play 8 Ball Pool\n\n -There are 7 solid, and 7 striped balls, "
			+ "a black 8-ball, and a white cue-ball \n The first player to sink a ball gets to play for the ball he sunk "
			+ "ie. if player 1 sinks a striped ball first, then player 1 is stripes, and player 2 is solids"
			+ " \n -A player is randomly chosen to break\n -If a ball is sunk, the player keeps playing until they miss "
			+ "\n -Once they miss, it's the next player's turn \n -Sink all of the designated balls, and then shoot"
			+ " at the 8-ball last to win \n\n -The 8-ball must be sunk last�sinking it before then will result"
			+ " in an automatic loss \n -If the cue ball is sunk, the next player gets their turn with the ball in hand \n"
			+ " -The cue ball must touch that player's type of ball (striped or solid), and the coloured ball that was hit"
			+ " or the cue ball must touch a side of the table";
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double width = screenSize.getWidth();
	double height = screenSize.getHeight();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				@SuppressWarnings("unused")
				Main start = new Main();
			}
		});

	}

	public static JButton button(String name) {
		JButton newButton = new JButton(name);
		newButton.setFocusPainted(false);
		newButton.setFont(new Font("Magneto", Font.BOLD, 26));
		newButton.setFocusable(false);
		newButton.setBackground(new Color(30, 175, 30));
		newButton.setMaximumSize(new Dimension(225, 50));
		return newButton;
	}

	public Main() {
		final JFrame splashScreen = new JFrame();
		splashScreen.setSize((int) width, (int) height);
		splashScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
		final JFrame mainScreen = new JFrame();
		mainScreen.setSize((int) width, (int) height);

		mainScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainScreen.setUndecorated(true);
		mainScreen.setVisible(false);

		try {
			splashScreen.setContentPane(new JLabel(
					new ImageIcon(ImageIO.read(new File("8 Ball Pool/resource/Images/8 Ball Pool SplashScreen.jpg")))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		splashScreen.getContentPane().setLayout(new BorderLayout(5, 5));
		splashScreen.setUndecorated(true);
		splashScreen.setVisible(true);

		BlinkLabel anyKeyLabel = new BlinkLabel("PRESS ANY KEY TO CONTINUE...");
		anyKeyLabel.setForeground(Color.WHITE);
		anyKeyLabel.setFont(new Font("Impact", Font.PLAIN, 40));
		splashScreen.getContentPane().add(anyKeyLabel, BorderLayout.PAGE_END);
		anyKeyLabel.startBlinking();

		splashScreen.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				splashScreen.dispose();
				mainScreen.setVisible(true);
				try {
					playMusic("8 Ball Pool/resource/Music/This City Prod. David Wud.wav");
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}
		});

		try {
			mainScreen.setContentPane(new JLabel(
					new ImageIcon(ImageIO.read(new File("8 Ball Pool/resource/Images/main menu background.jpg")))));
		} catch (IOException e) {
			e.printStackTrace(); 
		}

		mainScreen.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JPanel menuPane = new JPanel();
		menuPane.setBackground(new Color(0, 0, 0, 100));
		menuPane.setPreferredSize(new Dimension(400, 600));
		menuPane.setLayout(new BoxLayout(menuPane, BoxLayout.Y_AXIS));
		menuPane.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

		final JPanel helpPane = new JPanel();
		helpPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		helpPane.setLayout(new BoxLayout(helpPane, BoxLayout.Y_AXIS));
		helpPane.setBackground(new Color(0, 0, 0, 0));
		helpPane.setPreferredSize(new Dimension(600, 600));

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 0, 150);
		mainScreen.getContentPane().add(menuPane, c);
		c.insets = new Insets(0, 150, 0, 0);
		c.gridx = 1;
		mainScreen.getContentPane().add(helpPane, c);

		JButton playButton = button("Play");
		playButton.setAlignmentX(menuPane.CENTER_ALIGNMENT);
		menuPane.add(Box.createRigidArea(new Dimension(0, 50)));
		menuPane.add(playButton);

		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Starts the ball physics
				JFrame window = new BilliardWindow ();
				window.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
				window.setVisible (true);
				
			}
		});

		menuPane.add(Box.createRigidArea(new Dimension(0, 75)));

		JButton helpButton = button("Help");
		helpButton.setAlignmentX(menuPane.CENTER_ALIGNMENT);
		menuPane.add(helpButton);

		menuPane.add(Box.createRigidArea(new Dimension(0, 75)));

		JButton settingsButton = button("Settings");
		settingsButton.setAlignmentX(menuPane.CENTER_ALIGNMENT);
		menuPane.add(settingsButton);

		menuPane.add(Box.createRigidArea(new Dimension(0, 75)));

		JButton exitButton = button("Exit");
		exitButton.setAlignmentX(menuPane.CENTER_ALIGNMENT);
		menuPane.add(exitButton);

		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				helpPane.removeAll();
				helpPane.setBackground(new Color(0, 0, 0, 150));
				JTextArea helpText = new JTextArea();
				helpText.setText(helpString);
				helpText.setEditable(false);
				helpText.setFont(new Font("High Tower Text", Font.PLAIN, 20));
				helpText.setForeground(Color.WHITE);
				helpText.setBackground(new Color(0, 0, 0, 0));
				helpText.setLineWrap(true);
				helpText.setWrapStyleWord(true);
				helpPane.add(helpText);

				mainScreen.revalidate();
				mainScreen.repaint();
			}
		});
		
		settingsButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				helpPane.removeAll();
				helpPane.setBackground(new Color(0,0,0,125));
				
				
				JCheckBox checkMusic= new JCheckBox("Music");
				checkMusic.setOpaque(false);
				checkMusic.setForeground(Color.white);
				checkMusic.setFont(new Font("Impact",Font.PLAIN, 28));
				checkMusic.setAlignmentX(helpPane.CENTER_ALIGNMENT);
				
				helpPane.add(checkMusic);
				mainScreen.revalidate();
				mainScreen.repaint();
				
			}
		});
		
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
	}

	/**
	 * Plays music in the background
	 * 
	 * @param filePath
	 *            -file path of the audio file you want to play
	 * @throws Throwable
	 *             
	 * @throws IOException
	 *             - song isn't found
	 */
	public void playMusic(String filePath) throws Throwable, IOException {
		Clip clip = AudioSystem.getClip();
		AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(filePath));
		clip.open(inputStream);
		FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		volume.setValue(-10); // increase or decrease volume in decibals
		clip.start();
	}
}