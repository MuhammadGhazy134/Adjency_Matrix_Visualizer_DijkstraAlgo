import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class GraphPanel extends JPanel {
    private Graph graph;
    private Node draggedNode = null;
    private ArrayList<Integer> shortestPath = null;

    public GraphPanel(Graph graph) {
        this.graph = graph;
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);

        MouseAdapter mouseHandler = new MouseAdapter() {
            private int offsetX, offsetY;

            public void mousePressed(MouseEvent e) {
                for (Node node : graph.getNodes()) {
                    if (node.contains(e.getX(), e.getY())) {
                        draggedNode = node;
                        offsetX = e.getX() - node.getX();
                        offsetY = e.getY() - node.getY();
                        break;
                    }
                }
            }

            public void mouseReleased(MouseEvent e) { draggedNode = null; }

            public void mouseDragged(MouseEvent e) {
                if (draggedNode != null) {
                    draggedNode.setX(e.getX() - offsetX);
                    draggedNode.setY(e.getY() - offsetY);
                    repaint();
                }
            }
        };

        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    public void setShortestPath(ArrayList<Integer> path) {
        this.shortestPath = path;
        repaint();
    }

    public void clearShortestPath() {
        this.shortestPath = null;
        repaint();
    }

    private boolean isEdgeInPath(int sourceId, int targetId) {
        if (shortestPath == null || shortestPath.size() < 2) return false;
        for (int i = 0; i < shortestPath.size() - 1; i++) {
            if (shortestPath.get(i) == sourceId && shortestPath.get(i + 1) == targetId) {
                return true;
            }
        }
        return false;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw edges
        for (Edge edge : graph.getEdges()) {
            Node src = edge.getSource(), tgt = edge.getTarget();
            boolean inPath = isEdgeInPath(src.getId(), tgt.getId());

            g2d.setColor(inPath ? Color.RED : Color.GRAY);
            g2d.setStroke(new BasicStroke(inPath ? 4 : 2));
            g2d.drawLine(src.getX(), src.getY(), tgt.getX(), tgt.getY());

            drawArrow(g2d, src.getX(), src.getY(), tgt.getX(), tgt.getY());

            // Draw weight
            int midX = (src.getX() + tgt.getX()) / 2;
            int midY = (src.getY() + tgt.getY()) / 2;
            g2d.setColor(inPath ? Color.RED : new Color(100, 100, 100));
            g2d.setFont(new Font("Arial", inPath ? Font.BOLD : Font.PLAIN, inPath ? 14 : 12));
            g2d.drawString(String.valueOf(edge.getWeight()), midX, midY);
        }

        // Draw nodes
        for (Node node : graph.getNodes()) {
            boolean inPath = shortestPath != null && shortestPath.contains(node.getId());
            g2d.setColor(inPath ? new Color(255, 100, 100) : new Color(100, 150, 255));
            g2d.fillOval(node.getX() - node.getRadius(), node.getY() - node.getRadius(),
                    node.getRadius() * 2, node.getRadius() * 2);

            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(node.getX() - node.getRadius(), node.getY() - node.getRadius(),
                    node.getRadius() * 2, node.getRadius() * 2);

            // Draw label
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            String label = graph.getLabel()[node.getId()];
            FontMetrics fm = g2d.getFontMetrics();
            g2d.drawString(label, node.getX() - fm.stringWidth(label) / 2,
                    node.getY() + fm.getAscent() / 2);
        }
    }

    private void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        double angle = Math.atan2(y2 - y1, x2 - x1);
        int arrowX = x2 - (int)(25 * Math.cos(angle));
        int arrowY = y2 - (int)(25 * Math.sin(angle));

        int[] xPoints = {
                arrowX,
                arrowX - (int)(10 * Math.cos(angle - Math.PI / 6)),
                arrowX - (int)(10 * Math.cos(angle + Math.PI / 6))
        };
        int[] yPoints = {
                arrowY,
                arrowY - (int)(10 * Math.sin(angle - Math.PI / 6)),
                arrowY - (int)(10 * Math.sin(angle + Math.PI / 6))
        };

        g2d.fillPolygon(xPoints, yPoints, 3);
    }
}