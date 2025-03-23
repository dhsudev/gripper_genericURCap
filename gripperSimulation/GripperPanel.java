import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class GripperPanel extends JPanel {
    private int[] actualState;
    private double angle;
    private static final float STROKE_WIDTH = 2.0f;
    private static final Color RIGHT_GRIPPER_COLOR = Color.ORANGE;
    private static final Color LEFT_GRIPPER_COLOR = Color.MAGENTA;

    public GripperPanel(int[] actualState) {
        this.actualState = actualState;
    }

    public void setActualState(int[] actualState) {
        this.actualState = actualState;
        this.angle = Math.toRadians((actualState[1] - 42) * 2); // Precompute the angle
    }

   

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int gripperLength = getWidth() / 8;
        int gripperHeight = getHeight() / 20;
        int gripperTipLength = getHeight() / 30;
        int bodyWidth = getWidth() / 10;
        int bodyHeight = getHeight() / 5;

        // Cuerpo central
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(centerX - bodyWidth / 2, centerY - bodyHeight / 2, bodyWidth, bodyHeight);

        // Calcular el ángulo de rotación basado en el ancho
        // double angle = Math.toRadians((width - 42) * 2); // Adjust the scale factor as needed

        // Pata izquierda
        Path2D leftGripper = new Path2D.Double();
        leftGripper.moveTo(centerX - gripperTipLength, centerY - gripperHeight / 2);
        leftGripper.lineTo(centerX - gripperLength - gripperTipLength, centerY - gripperHeight / 2 - gripperTipLength);
        leftGripper.lineTo(centerX - gripperLength, centerY + gripperHeight / 2 - gripperTipLength);
        leftGripper.lineTo(centerX, centerY + gripperHeight / 2);
        leftGripper.closePath();

        // Pata derecha
        Path2D rightGripper = new Path2D.Double();
        rightGripper.moveTo(centerX + gripperTipLength, centerY - gripperHeight / 2);
        rightGripper.lineTo(centerX + gripperLength + gripperTipLength, centerY - gripperHeight / 2 - gripperTipLength);
        rightGripper.lineTo(centerX + gripperLength, centerY + gripperHeight / 2 - gripperTipLength);
        rightGripper.lineTo(centerX, centerY + gripperHeight / 2);
        rightGripper.closePath();

        g2d.setColor(LEFT_GRIPPER_COLOR);
        AffineTransform leftRotation = AffineTransform.getRotateInstance(-angle, centerX - bodyWidth / 2, centerY);
        leftGripper.transform(leftRotation);

        g2d.setColor(RIGHT_GRIPPER_COLOR);
        AffineTransform rightRotation = AffineTransform.getRotateInstance(angle, centerX + bodyWidth / 2, centerY);
        rightGripper.transform(rightRotation);

        g2d.setColor(Color.MAGENTA);
        g2d.fill(leftGripper);
        g2d.setColor(Color.ORANGE);
        g2d.fill(rightGripper);
        g2d.setStroke(new BasicStroke(STROKE_WIDTH));
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(leftGripper);
        g2d.draw(rightGripper);

    }
}
