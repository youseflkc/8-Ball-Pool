import javafx.scene.transform.NonInvertibleTransformException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

/**
 * Created by Thomas on 2017-06-02.
 */
public class Cue implements MouseListener
{
    private static final int CUE_WIDTH = 400;
    private static final int CUE_HEIGHT = 10;

    private int xPos;
    private int yPos;

    private int drawBack_xPos;
    private int drawBack_yPos;

    private boolean drawnBack;

    private int power;

    private static boolean MOUSE_RIGHT_CLICK;
    private static boolean MOUSE_HELD_DOWN;

    private Rectangle cue;

    // Value between 0 and 360
    private static int angle;

    private Color color = Color.BLACK;

    public Cue(JPanel frame)
    {
        MOUSE_HELD_DOWN = false;
        frame.addMouseListener(this);

        drawnBack = false;

        drawBack_xPos = 0;
        drawBack_yPos = 0;
    }

    public void updatePosition(int xPos, int yPos)
    {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public static void updateAngle(int increment)
    {
        angle += increment;

        if (angle > 360)
        {
            int remainder = (angle - 360);
            angle = 0 + remainder;
        }
        else if (angle < 0)
        {
            int remainder = Math.abs(angle - 0);
            angle = 360 - remainder;
        }


    }

    public void drawBack()
    {

        if (MOUSE_HELD_DOWN)
        {
            drawBack_xPos += 3;

            if (drawBack_xPos >= 150)
                drawBack_xPos = 150;

            drawnBack = true;
        }
        else
        {
            if (drawBack_xPos > power)
                power = drawBack_xPos;

            if (drawBack_xPos > 0)
            {
                if (drawBack_xPos < 16)
                    drawBack_xPos -= 25;
                else
                    drawBack_xPos -= 15;
            }
            else
                drawBack_xPos = 0;
        }

        if (drawnBack == true && drawBack_xPos <= 0)
        {
            System.out.println("Ran");

            Speed cueSpeed = new Speed(-5, -5);

            Ball cue = new Ball(xPos + 20, yPos, 15, Level.INIT_MASS, cueSpeed, Color.WHITE, true, 0);
            Ball cueBall = Main.content.getBall(0);

            cue.collide(cueBall, 15);

            drawnBack = false;
            power = 0;
        }
    }

    public void render(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setColor(this.color);
        cue = new Rectangle(xPos + 20 + drawBack_xPos, yPos, CUE_WIDTH, CUE_HEIGHT);

        g2d.rotate(Math.toRadians(angle), xPos, yPos);

        g2d.draw(cue);
        g2d.fill(cue);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3)
            drawBack_xPos = 0;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        MOUSE_HELD_DOWN = true;
        System.out.println("Pressed - " + MOUSE_HELD_DOWN);
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        MOUSE_HELD_DOWN = false;
        System.out.println("Released - " + MOUSE_HELD_DOWN);
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
