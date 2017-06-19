import java.awt.*;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Thomas on 2017-06-17.
 */
public class SaveFile
{
    private RandomAccessFile raf;
    private String path;

    public SaveFile(Level game, String path)
    {
        this.path = path;

        try
        {
            raf = new RandomAccessFile(path, "rw");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        write(game);
    }

    public void write(Level game)
    {

        try
        {
            for (int i = 0; i < 16; i++)
            {
                raf.writeDouble(game.getBall(i).getX());
                raf.writeDouble(game.getBall(i).getX());
                raf.writeInt(game.getBall(i).getBallNumber());
            }
        }
        catch (IOException e)
        {
            System.out.println("Error writing to binary file");
            e.printStackTrace();
        }
    }

    public static Ball[] read(String path)
    {
        Ball[] balls = new Ball[16];
        double xPos;
        double yPos;
        int ballNum;

        try
        {
            RandomAccessFile raf = new RandomAccessFile(path, "rw");

            for (int i = 0; i < 16; i++)
            {
                xPos = raf.readDouble();
                yPos = raf.readDouble();
                ballNum = raf.readInt();

                balls[i] = new Ball(xPos, yPos, Level.INIT_RADIUS, Level.INIT_MASS, new Speed(0, 0), Color.WHITE, true, ballNum);
            }
        }
        catch (IOException e)
        {
            System.err.println("Error reading in save file");
            e.printStackTrace();
        }

        return balls;
    }
}
