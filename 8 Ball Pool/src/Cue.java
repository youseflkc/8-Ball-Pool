import javafx.scene.transform.NonInvertibleTransformException;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

/**
 * Created by Thomas on 2017-06-02.
 */
public class Cue
{
    private static final int CUE_WIDTH = 400;
    private static final int CUE_HEIGHT = 10;

    private int xPos;
    private int yPos;

    private Rectangle cue;

    // Value between 0 and 360
    private static int angle;

    private Color color = Color.BLACK;

    public Cue()
    {
        xPos = Main.WIDTH / 2;
        yPos = Main.HEIGHT / 2;
    }

    public void updatePosition(int xPos, int yPos)
    {
        this.xPos = xPos + 20;
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

    public void render(Graphics2D g)
    {
        g.setColor(this.color);
        cue = new Rectangle(xPos, yPos, CUE_WIDTH, CUE_HEIGHT);

        g.rotate(Math.toRadians(angle));

        g.draw(cue);
        g.fill(cue);

        g.rotate(Math.toRadians(-angle));



//        Path2D.Double path = new Path2D.Double();
//        path.append(cue, false);
//        AffineTransform at = new AffineTransform();
//
//        at.rotate(Math.toRadians(45), cue.getX() + cue.width/2, cue.getY() + cue.height/2);
//
//        double x1 = cue.getX() - cue.getCenterX();
//        double y1 = cue.getY();
    }
}
