import java.awt.*;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Thomas on 2017-06-17.
 *
 * Class is a representation of the data stored in a save file
 */
public class SaveFile
{
    private RandomAccessFile raf;
    private String path;

    /**
     * Constructor: Creates a random access file to write to
     *
     * @param game
     * @param path
     */
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

    /**
     * Writes game data to save file
     *
     * @param game
     */
    public void write(Level game)
    {

        try
        {
            // Writes bet amounts to file
            raf.writeInt(game.player1.betAmount);
            raf.writeInt(game.player2.betAmount);

            // Writes point amounts to file
            raf.writeInt(game.player1.getPoints());
            raf.writeInt(game.player1.getPoints());

            // Writes character turn to file
            raf.writeBoolean(game.player1.isTurn());
            raf.writeBoolean(game.player1.isTurn());

            for (int i = 0; i < 16; i++)
            {
                raf.writeDouble(game.getBall(i).getX());
                raf.writeDouble(game.getBall(i).getY());
                System.out.println("X(" + i + ") = " + game.getBall(i).getX() + " Y(" + i + ") = " + game.getBall(i).getY());
            }
        }
        catch (IOException e)
        {
            System.out.println("Error writing to binary file");
            e.printStackTrace();
        }
    }

    /**
     * Reads game data related to character when loading in new game
     *
     * @param path takes in the path of the file being read
     * @return saved Character data
     */
    public static Character[] readCharacterInfo(String path)
    {
        Character players[] = new Character[2];
        int betAmount[] = new int[2];
        int pointAmount[] = new int[2];
        boolean characterTurn[] = new boolean[2];

        try
        {
            RandomAccessFile raf = new RandomAccessFile(path, "rw");

            // Reads bet amounts to file
            betAmount[0] = raf.readInt();
            betAmount[1] = raf.readInt();

            // Reads point amounts to file
            pointAmount[0] = raf.readInt();
            pointAmount[1] = raf.readInt();

            // Reads character turn to file
            characterTurn[0] = raf.readBoolean();
            characterTurn[1] = raf.readBoolean();
        }
        catch (IOException e)
        {
            System.err.println("Error reading in save file");
            e.printStackTrace();
        }

        players[0] = new Character("Player 1", characterTurn[0], pointAmount[0], true, betAmount[0]);
        players[1] = new Character("Player 2", characterTurn[1], pointAmount[1], true, betAmount[1]);

        return players;
    }

    /**
     * Reads game data related to balls when loading in new game
     *
     * @param path takes in the path of the file being read
     * @return saved Ball data
     */
    public static Ball[] readBallInfo(String path)
    {
        Ball[] ball = new Ball[16];
        double[] xPos = new double[16];
        double[] yPos = new double[16];

        try
        {
            RandomAccessFile raf = new RandomAccessFile(path, "rw");

            // Reads bet amounts to file
            raf.readInt();
            raf.readInt();

            // Reads point amounts to file
            raf.readInt();
            raf.readInt();

            // Reads character turn to file
            raf.readBoolean();
            raf.readBoolean();

            for (int i = 0; i < 16; i++)
            {
                xPos[i] = raf.readDouble();
                yPos[i] = raf.readDouble();
                System.out.println("New X(" + i + ") = " + xPos[i] + " New Y(" + i + ") = " + yPos[i]);
            }
        }
        catch (IOException e)
        {
            System.err.println("Error reading in save file");
            e.printStackTrace();
        }

        ball[0] = new Ball(xPos[0], yPos[0], Level.INIT_RADIUS, Level.INIT_MASS, new Speed(0, 0), Level.WHITE, true, 0);
        ball[1] = new Ball(xPos[1], yPos[1], Level.INIT_RADIUS, Level.INIT_MASS, new Speed(0, 0), Level.YELLOW, false, 9);
        ball[2] = new Ball(xPos[2], yPos[2], Level.INIT_RADIUS, Level.INIT_MASS, new Speed(0, 0), Level.RED, true, 7);
        ball[3] = new Ball(xPos[3], yPos[3], Level.INIT_RADIUS, Level.INIT_MASS, new Speed(0, 0), Level.PURPLE, false, 12);
        ball[4] = new Ball(xPos[4], yPos[4], Level.INIT_RADIUS, Level.INIT_MASS, new Speed(0, 0), Level.RED, false, 15);
        ball[5] = new Ball(xPos[5], yPos[5], Level.INIT_RADIUS, Level.INIT_MASS, new Speed(0, 0), Level.BLACK, true, 8);
        ball[6] = new Ball(xPos[6], yPos[6], Level.INIT_RADIUS, Level.INIT_MASS, new Speed(0, 0), Level.YELLOW, true, 1);
        ball[7] = new Ball(xPos[7], yPos[7], Level.INIT_RADIUS, Level.INIT_MASS, new Speed(0, 0), Level.GREEN, true, 6);
        ball[8] = new Ball(xPos[8], yPos[8], Level.INIT_RADIUS, Level.INIT_MASS, new Speed(0, 0), Level.BLUE, false, 10);
        ball[9] = new Ball(xPos[9], yPos[9], Level.INIT_RADIUS, Level.INIT_MASS, new Speed(0, 0), Level.RED, true, 3);
        ball[10] = new Ball(xPos[10], yPos[10], Level.INIT_RADIUS, Level.INIT_MASS, new Speed(0, 0), Level.GREEN, false, 14);
        ball[11] = new Ball(xPos[11], yPos[11], Level.INIT_RADIUS, Level.INIT_MASS, new Speed(0, 0), Level.RED, false, 11);
        ball[12] = new Ball(xPos[12], yPos[12], Level.INIT_RADIUS, Level.INIT_MASS, new Speed(0, 0), Level.BLUE, true, 2);
        ball[13] = new Ball(xPos[13], yPos[13], Level.INIT_RADIUS, Level.INIT_MASS, new Speed(0, 0), Level.ORANGE, false, 13);
        ball[14] = new Ball(xPos[14], yPos[14], Level.INIT_RADIUS, Level.INIT_MASS, new Speed(0, 0), Level.PURPLE, true, 4);
        ball[15] = new Ball(xPos[15], yPos[15], Level.INIT_RADIUS, Level.INIT_MASS, new Speed(0, 0), Level.ORANGE, true, 5);

        return ball;
    }
}
