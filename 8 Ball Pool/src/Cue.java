import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by Thomas on 2017-06-02.
 */
public class Cue
{
    private static final int CUE_WIDTH = 400;
    private static final int CUE_HEIGHT = 10;

    private int xPos;
    private int yPos;

    private Color color = Color.BLACK;

    public Cue()
    {
        xPos = Main.WIDTH / 2;
        yPos = Main.HEIGHT / 2;
    }

    public void render(Graphics2D g)
    {
        g.setColor(this.color);
        g.fillRect(xPos, yPos, CUE_WIDTH, CUE_HEIGHT);
    }
}
