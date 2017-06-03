import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Thomas on 2017-06-02.
 */
public class Input implements MouseListener, MouseMotionListener {

    private JFrame frame;

    public static int MOUSE_X_POS;
    public static int MOUSE_Y_POS;

    public static boolean MOUSE_CLICKED;

    public Input(JFrame frame)
    {
        this.frame = frame;

        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);
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
}
