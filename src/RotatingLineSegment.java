import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class RotatingLineSegment extends JPanel {

    private double angle; // Angle of rotation
    private Color lineColor; // Color of the line

    public RotatingLineSegment() {
        angle = 0;
        lineColor = Color.RED;

        // Start a timer to update the rotation and color
        Timer timer = new Timer(20, e -> {
            angle += 0.02; // Adjust the rotation speed as desired
            lineColor = getNextColor(lineColor);
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Enable anti-aliasing for smoother rendering
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set the center point of rotation
        int centerX = getWidth() / 2  + 150;
        int centerY = getHeight() / 2;

        // Set the line segment length and endpoint coordinates
        int lineLength = 300;
        int x1 = centerX - lineLength / 2;
        int y1 = centerY;
        int x2 = centerX + lineLength / 2;
        int y2 = centerY;

        // Apply rotation transformation around (x1, y1)
        AffineTransform transform = new AffineTransform();
        transform.rotate(angle, x1, y1);
        g2d.setTransform(transform);

        // Draw the line segment with the current color
        g2d.setColor(lineColor);
        g2d.drawLine(x1, y1, x2, y2);
    }

    private Color getNextColor(Color currentColor) {
        // Change the color based on the current color
        if (currentColor == Color.RED) {
            return Color.BLUE;
        } else if (currentColor == Color.BLUE) {
            return Color.GREEN;
        } else {
            return Color.RED;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Rotating Line Segment");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            RotatingLineSegment lineSegment = new RotatingLineSegment();
            lineSegment.setPreferredSize(new Dimension(1280, 720));

            frame.getContentPane().add(lineSegment);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
