import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Thomas on 2017-05-30.
 */
public class Level extends JPanel
{
    private BufferedImage wooden_tile;
    private BufferedImage wooden_tile_rotated90;
    private BufferedImage black_dot;
    private BufferedImage table_grass;

    private JButton exitButton = new JButton();

    public Level()
    {
        setLayout(new FlowLayout(FlowLayout.RIGHT));

        add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    SwingUtilities.getWindowAncestor(Level.this).dispose();
                }
            }
        });

        wooden_tile = loadTextures("8 Ball Pool/resource/Images/wooden_tile.png");
        wooden_tile_rotated90 = loadTextures("8 Ball Pool/resource/Images/wooden_tile_rotated90.png");
        black_dot = loadTextures("8 Ball Pool/resource/Images/black_dot.png");
        table_grass = loadTextures("8 Ball Pool/resource/Images/table_grass.png");

        repaint();
    }

    public BufferedImage loadTextures(String path)
    {
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(new File(path));
        }
        catch (IOException ex)
        {
            System.err.println("Error; cannot read in file");
            ex.printStackTrace();
        }

        return image;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        // Borders
        g.drawImage(wooden_tile, 0, 0, Main.WIDTH, 100, this);
        g.drawImage(wooden_tile, 0, Main.HEIGHT - 100, Main.WIDTH, 100, this);
        g.drawImage(wooden_tile_rotated90, 0, 0, 100, Main.HEIGHT, this);
        g.drawImage(wooden_tile_rotated90, Main.WIDTH - 100, 0, 100, Main.HEIGHT, this);

        // Table Grass
        g.drawImage(table_grass, 100, 100, Main.WIDTH - 200, Main.HEIGHT - 200, this);

        // Top Holes
        g.drawImage(black_dot, 50, 50, 100, 100, this);
        g.drawImage(black_dot, (Main.WIDTH / 2) - 50, 50, 100, 100, this);
        g.drawImage(black_dot, Main.WIDTH - 150, 50, 100, 100, this);

        // Bottom Holes
        g.drawImage(black_dot, 50, Main.HEIGHT - 150, 100, 100, this);
        g.drawImage(black_dot, (Main.WIDTH / 2) - 50, Main.HEIGHT - 150, 100, 100, this);
        g.drawImage(black_dot, Main.WIDTH - 150, Main.HEIGHT - 150, 100, 100, this);
    }
}
