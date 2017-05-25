import java.awt.BorderLayout;
import java.awt.Color;
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
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {

			}
		});
		try{
			for (int x = 0; x < 6; x++) {
				if (x % 2 == 0) {
					anyKeyLabel.setVisible(true);
				}else{
					anyKeyLabel.setVisible(false);
				}
				Thread.sleep(1000);
			}
		}catch(InterruptedException e){
			
		}
		splashScreen.pack();

	}

}