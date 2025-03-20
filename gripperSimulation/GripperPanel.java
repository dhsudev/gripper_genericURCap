import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class GripperPanel extends JPanel {
    private int[] actualState;

    public GripperPanel(int[] actualState) {
        this.actualState = actualState;
    }

    public void setActualState(int[] actualState) {
        this.actualState = actualState;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = actualState[1];
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int gripperLength = getWidth() / 8;
        int gripperHeight = getHeight() / 20;
        int gripperTipLength = getHeight() / 30;
        int bodyWidth = getWidth() / 10;
        int bodyHeight = getHeight() / 5;

        // Cuerpo central
        g2d.setColor(Color.GRAY);
        g2d.fillRect(centerX - bodyWidth / 2, centerY - bodyHeight / 2, bodyWidth, bodyHeight);

        if (width == 0) {
            // Gripper tocándose
            actualState[0] = 0; // Modo 0
            g2d.setColor(Color.BLUE);
            g2d.drawLine(centerX, centerY - gripperHeight / 2, centerX, centerY + gripperHeight / 2);
            g2d.setColor(Color.RED);
            g2d.drawLine(centerX, centerY - gripperHeight / 2, centerX, centerY + gripperHeight / 2);
        } else {
            // Calcular el ángulo de rotación basado en el ancho
            double angle = Math.toRadians((width - 42) * 2); // Ajusta el factor de escala según sea necesario

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

            // Aplicar rotación
            AffineTransform leftRotation = AffineTransform.getRotateInstance(angle, centerX - bodyWidth / 2, centerY);
            leftGripper.transform(leftRotation);

            AffineTransform rightRotation = AffineTransform.getRotateInstance(-angle, centerX + bodyWidth / 2, centerY);
            rightGripper.transform(rightRotation);

            g2d.setColor(Color.BLUE);
            g2d.fill(leftGripper);
            g2d.setColor(Color.RED);
            g2d.fill(rightGripper);

            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(leftGripper);
            g2d.draw(rightGripper);
        }
    }
}
