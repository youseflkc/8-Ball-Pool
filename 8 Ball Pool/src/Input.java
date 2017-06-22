import javax.swing.*;
import java.awt.event.*;

/**
 * Created by Thomas on 2017-06-02.
 */
public class Input implements MouseMotionListener, KeyListener {

    private JFrame frame;

    SaveFile saveFile;

    public static int MOUSE_X_POS;
    public static int MOUSE_Y_POS;

    public static boolean MOUSE_PRESSED;
    public static boolean MOUSE_RELEASED;
    public static long MOUSE_HOLD_DOWN_TIME;

    public static boolean MOUSE_CLICKED;

    public Input(JFrame frame)
    {
        this.frame = frame;

        frame.addMouseMotionListener(this);
        frame.addKeyListener(this);

        MOUSE_PRESSED = false;
        MOUSE_RELEASED = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        MOUSE_X_POS = e.getX();
        MOUSE_Y_POS = e.getY();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //Right arrow key code
        if (e.getKeyCode() == 39)
        {
            Cue.updateAngle(1);
        }
        //Left arrow key code
        else if (e.getKeyCode() == 37)
        {
            Cue.updateAngle(-1);
        }
        else if (e.getKeyCode() == KeyEvent.VK_0)
        {

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
