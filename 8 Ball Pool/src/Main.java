import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 * Main class; handles all initial (main menu) graphics and loads up anything
 * pre-game, inlcuding but not limited to
 *
 * <p> ActionListener listens for button presses on the "Play", "Help", Settings",
 * "Load" and "Exit" buttons </p>
 */
public class Main implements ActionListener {

	// Instance of a game
	public static Level content;
	
	// Text for help menu
	String helpString = "\n -There are 7 solid, and 7 striped balls, "
			+ "a black 8-ball, and a white cue-ball \n The first player to sink a ball gets to play for the ball he sunk "
			+ "ie. if player 1 sinks a striped ball first, then player 1 is stripes, and player 2 is solids"
			+ " \n -A player is randomly chosen to break\n -If a ball is sunk, the player keeps playing until they miss "
			+ "\n -Once they miss, it's the next player's turn \n -Sink all of the designated balls, and then shoot"
			+ " at the 8-ball last to win \n-The 8-ball must be sunk last-sinking it before then will result"
			+ " in an automatic loss \n -If the cue ball is sunk, the next player gets their turn with the ball in hand \n"
			+ " -The cue ball must touch that player's type of ball (striped or solid), and the coloured ball that was hit"
			+ " or the cue ball must touch a side of the table\n\nUse the left and right arrow keys to rotate the cue\n"
			+ "Click and hold the mouse to power up the cue. The longer you hold, the more power in your shot\nLet go to release the cue";

	// Screen dimensions
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = (int) screenSize.getWidth();
	public static final int HEIGHT = (int) screenSize.getHeight();

	// All JFrames
	private final JFrame splashScreen = new JFrame();
	private final JFrame mainScreen = new JFrame();
	private final JFrame playScreen = new JFrame();

	private Input input = new Input(playScreen);

	/**
	 * Called when program first runs, initiates Main constructor
	 *
	 * @param args string data used to start program
     */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				@SuppressWarnings("unused")
				Main start = new Main();
			}
		});
	}

	/**
	 * Button method gives buttons all the same attributes ie. colour, size,
	 * font, etc...
	 * 
	 * @param name
	 *            -text written in the button
	 * @return - jbutton you created
	 */
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
		
		// Code for setting up JFrames
		splashScreen.setSize(WIDTH, HEIGHT);
		splashScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainScreen.setSize(WIDTH, HEIGHT);
		playScreen.setVisible(false);

		mainScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainScreen.setUndecorated(true);
		mainScreen.setVisible(false);

		try {
			splashScreen.setContentPane(new JLabel(
					new ImageIcon(ImageIO.read(new File("8 Ball Pool/resource/Images/8 Ball Pool SplashScreen.jpg"))
							.getScaledInstance((int) WIDTH, (int) HEIGHT, Image.SCALE_SMOOTH))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		splashScreen.getContentPane().setLayout(new BorderLayout(5, 5));
		splashScreen.setUndecorated(true);
		splashScreen.setVisible(true);
		
		// Blinking label animation for splashscreen
		BlinkLabel anyKeyLabel = new BlinkLabel("PRESS ANY KEY TO CONTINUE...");
		anyKeyLabel.setForeground(Color.WHITE);
		anyKeyLabel.setFont(new Font("Impact", Font.PLAIN, 40));
		splashScreen.getContentPane().add(anyKeyLabel, BorderLayout.PAGE_END);
		anyKeyLabel.startBlinking();

		// Background music in the game
		final Sound bgMusic = new Sound();
		bgMusic.setVolume(-10);

		try {
			bgMusic.loadSound("8 Ball Pool/resource/Music/This City Prod. David Wud.wav");
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Throwable e1) {
			e1.printStackTrace();
		}

		splashScreen.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				splashScreen.dispose();
				mainScreen.setVisible(true);

				try {
					bgMusic.playSound();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}
		});
		
		//setting up main menu
		try {
			mainScreen.setContentPane(new JLabel(
					new ImageIcon(ImageIO.read(new File("8 Ball Pool/resource/Images/main menu background.jpg"))
							.getScaledInstance((int) WIDTH, (int) HEIGHT, Image.SCALE_SMOOTH))));
		} catch (IOException e) {
			e.printStackTrace();
		}

		mainScreen.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//panel for all the menu buttons
		JPanel menuPane = new JPanel();
		menuPane.setBackground(new Color(0, 0, 0, 100));
		menuPane.setPreferredSize(new Dimension((int) WIDTH / 3, (int) HEIGHT - (int) HEIGHT / 5));
		menuPane.setLayout(new BoxLayout(menuPane, BoxLayout.Y_AXIS));
		menuPane.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

		//panel for help screeen and settings
		final JPanel helpPane = new JPanel();
		helpPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		helpPane.setLayout(new BoxLayout(helpPane, BoxLayout.Y_AXIS));
		helpPane.setPreferredSize(new Dimension((int) WIDTH / 3, (int) HEIGHT - (int) HEIGHT / 5));
		helpPane.setBackground(new Color(0, 0, 0, 125));
		helpPane.setOpaque(false);

		JLabel logoLabel;
		try {
			logoLabel = new JLabel(new ImageIcon(ImageIO.read((new File("8 Ball Pool/resource/Images/logo.png")))
					.getScaledInstance((int) WIDTH / 3 - 30, (int) HEIGHT / 5, Image.SCALE_SMOOTH)));
			logoLabel.setBackground(new Color(0, 0, 0, 0));
			helpPane.add(logoLabel);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 0, 125);
		mainScreen.getContentPane().add(menuPane, c);
		c.insets = new Insets(0, 125, 0, 0);
		c.gridx = 1;
		mainScreen.getContentPane().add(helpPane, c);

		JButton playButton = button("Play");
		playButton.setAlignmentX(menuPane.CENTER_ALIGNMENT);
		menuPane.add(Box.createRigidArea(new Dimension(0, 50)));
		menuPane.add(playButton);
		
		//you can click enter on the mainscreen to play
		menuPane.getRootPane().setDefaultButton(playButton);
		
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Removes shutter issue (check method header for details)
				startBalls();

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
		
		JButton loadButton=button("Load");//button for thomas
		loadButton.setAlignmentX(menuPane.CENTER_ALIGNMENT);
		menuPane.add(loadButton);
		menuPane.add(Box.createRigidArea(new Dimension(0, 75)));

		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startBalls();
				content.loadGame("8 Ball Pool/resource/saveFile.txt");
			}
		});

		JButton exitButton = button("Exit");
		exitButton.setAlignmentX(menuPane.CENTER_ALIGNMENT);
		menuPane.add(exitButton);

		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				helpPane.removeAll();
				helpPane.setOpaque(true);
				JTextArea helpText = new JTextArea();
				JLabel helpTitle = new JLabel("How to Play 8-Ball Pool");
				helpTitle.setFont(new Font("High Tower Text", Font.BOLD, 24));
				helpTitle.setForeground(Color.white);
				helpTitle.setAlignmentX(helpPane.CENTER_ALIGNMENT);
				helpText.setText(helpString);
				helpText.setEditable(false);
				helpText.setFont(new Font("High Tower Text", Font.PLAIN, 20));
				helpText.setForeground(Color.WHITE);
				helpText.setHighlighter(null);
				helpText.setBackground(Color.BLACK);
				helpText.setLineWrap(true);
				helpText.setWrapStyleWord(true);
				JScrollPane helpScroll = new JScrollPane(helpText);
				helpScroll.setOpaque(false);
				helpPane.add(helpTitle);
				helpPane.add(helpScroll);

				mainScreen.revalidate();
				mainScreen.repaint();
			}
		});


		final JCheckBox checkMusic = new JCheckBox("Music");
		checkMusic.setSelected(true);

		settingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				helpPane.removeAll();
				helpPane.setOpaque(true);
				
				checkMusic.setBackground(Color.black);
				checkMusic.setForeground(Color.white);
				checkMusic.setFont(new Font("Impact", Font.PLAIN, 28));
				checkMusic.setAlignmentX(helpPane.CENTER_ALIGNMENT);
				checkMusic.setFocusPainted(false);
				checkMusic.setMargin(new Insets(0, 20, 0, 20));

				helpPane.add(Box.createRigidArea(new Dimension(0, 75)));
				helpPane.add(checkMusic);
				mainScreen.revalidate();
				mainScreen.repaint();
			}
		});
		
		
		//check button to turn on/off music
		checkMusic.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (checkMusic.isSelected() == true) {
					try {
						bgMusic.playSound();
					} catch (Throwable e1) {
						e1.printStackTrace();
					}
				} else if (checkMusic.isSelected() == false) {
					bgMusic.stopSound();
				}
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
	 * Method is used to avoid shutter when code inside of this method is placed
	 * inside of the addActionListener of the play button
	 */
	public void startBalls() {
		// Initialize playscreen
		playScreen.setUndecorated(true);
		playScreen.setVisible(true);
		playScreen.setSize((int) WIDTH, (int) HEIGHT);

		// Create instance of the game
		content = new Level();

		// Sets the game pane (level) to the playScreen JFrame
		playScreen.setContentPane(content);
		playScreen.getGlassPane().setVisible(true);

		Timer timer = new Timer(20, this);
		timer.start();
	}
	
	// Added when Lazar added "implements ActionListener" to this class
	@Override
	public void actionPerformed(ActionEvent e) {
		playScreen.repaint();
	}
}