import javax.swing.*;
import java.awt.event.*;

/**
 * Created by Thomas on 2017-06-02.
 */
public class Input implements MouseListener, MouseMotionListener, KeyListener {

    private JFrame frame;

    public static int MOUSE_X_POS;
    public static int MOUSE_Y_POS;

    public static boolean MOUSE_CLICKED;

    public Input(JFrame frame)
    {
        this.frame = frame;

        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);
        frame.addKeyListener(this);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        MOUSE_CLICKED = !MOUSE_CLICKED;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

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
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
