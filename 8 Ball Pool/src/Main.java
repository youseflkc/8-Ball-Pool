import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
 
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
 
public class Main {
    JFrame splashScreen = new JFrame();
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Main start = new Main();
            }
        });
 
    }
 
    public Main(){
         
        splashScreen.setSize(1366, 768);
        splashScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
        try {
            splashScreen.setContentPane
            (new JLabel(new ImageIcon(ImageIO.read(new File("8 Ball Pool SplashScreen.jpg")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        splashScreen.setUndecorated(true);
        splashScreen.setVisible(true);
         
 
    }
     
    public void keyPressed(KeyEvent e){
        System.exit(0);
    }
 
}