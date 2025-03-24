import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Path2D;

public class GripperPanel extends JPanel {
    // actualState[1] represents the desired width in mm between the two top tips.
    private int[] actualState;
    
    // These dimensions will be set at paint time.
    private int fingerLength;
    private int fingerWidth;
    
    // Colors and stroke for the gripper fingers.
    private static final Color LEFT_COLOR = Color.MAGENTA;
    private static final Color RIGHT_COLOR = Color.ORANGE;
    private static final float STROKE_WIDTH = 2.0f;
    
    // Defines the rotation per mm (e.g., 1.5° per mm converted to radians).
    private static final double ROTATION_PER_MM = Math.toRadians(1.5);
    
    // Constructor starts an animation timer (20ms delay ~50 FPS).
    public GripperPanel(int[] actualState) {
        this.actualState = actualState;
        new Timer(20, e -> repaint()).start();
    }
    
    public void setActualState(int[] actualState) {
        this.actualState = actualState;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Enable anti-aliasing for smooth rendering.
        Graphics2D g2d = (Graphics2D) g;
        //g2d.setRenderingHint(Graphics2D.KEY_ANTIALIASING, Graphics2D.VALUE_ANTIALIAS_ON);
        
        // Calculate panel dimensions and central body.
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int centerX = panelWidth / 2;
        int bodyWidth = panelWidth / 10;
        int bodyHeight = panelHeight / 5;
        int bodyX = centerX - bodyWidth / 2;
        int bodyY = panelHeight / 6;
        
        // Draw the central body.
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(bodyX, bodyY, bodyWidth, bodyHeight);
        
        // Set the common pivot for the fingers at the midpoint of the body’s bottom edge.
        int pivotX = centerX;
        int pivotY = bodyY + bodyHeight;
        
        // Define finger dimensions based on panel size.
        fingerLength = panelHeight / 4;  // Finger length (L)
        fingerWidth = panelWidth / 40;     // Finger thickness
        
        // Compute the rotation angle (θ) linearly: θ = ROTATION_PER_MM * desiredWidth.
        double desiredWidth = actualState[1]; // Width in mm
        double theta = ROTATION_PER_MM * desiredWidth;
        
        // Create the finger shape in local coordinates using curved edges.
        Path2D fingerShape = new Path2D.Double();
        fingerShape.moveTo(-fingerWidth / 2.0, 0);
        fingerShape.lineTo(fingerWidth / 2.0, 0);
        // Right edge goes upward.
        fingerShape.lineTo(fingerWidth / 2.0, -fingerLength * 0.75);
        // Top edge is curved via a quadratic curve.
        fingerShape.quadTo(0, -fingerLength, -fingerWidth / 2.0, -fingerLength * 0.75);
        fingerShape.closePath();
        
        // Create the transformed shapes for each finger.
        Shape leftFinger = createFingerShape(pivotX, pivotY, -theta, fingerShape);
        Shape rightFinger = createFingerShape(pivotX, pivotY, theta, fingerShape);
        
        // Draw the filled fingers.
        g2d.setColor(LEFT_COLOR);
        g2d.fill(leftFinger);
        g2d.setColor(RIGHT_COLOR);
        g2d.fill(rightFinger);
        
        // Draw the outlines.
        g2d.setStroke(new BasicStroke(STROKE_WIDTH));
        g2d.setColor(Color.BLACK);
        g2d.draw(leftFinger);
        g2d.draw(rightFinger);
    }
    
    // Returns a transformed finger shape by translating to the pivot point and rotating.
    private Shape createFingerShape(int pivotX, int pivotY, double angle, Path2D fingerShape) {
        AffineTransform transform = new AffineTransform();
        transform.translate(pivotX, pivotY);
        transform.rotate(angle);
        return transform.createTransformedShape(fingerShape);
    }
    
    // Helper to transform a point using an AffineTransform.
    private Point transformPoint(AffineTransform at, double x, double y) {
        double[] src = {x, y};
        double[] dst = new double[2];
        at.transform(src, 0, dst, 0, 1);
        return new Point((int)Math.round(dst[0]), (int)Math.round(dst[1]));
    }
}
