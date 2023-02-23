package UI;

import RoutePlanner.Location;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author BlueTeaWolf (Ole)
 */
public class GraphVisualizer extends JPanel {

    private HashMap<Location, HashMap<Location, Double>> graph;
    private HashMap<String, Location> locations;
    private List<Location> shortestPath;

    public GraphVisualizer(HashMap<String, Location> locations,
                           HashMap<Location, HashMap<Location, Double>> graph) {
        this.locations = locations;
        this.graph = graph;

        JFrame frame = new JFrame("Graph Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1280, 720));
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Location location : locations.values()) {
            int x1 = location.getX();
            int y1 = location.getY();

            HashMap<Location, Double> connections = graph.get(location);
            for (Location connection : connections.keySet()) {
                int x2 = connection.getX();
                int y2 = connection.getY();

                if (shortestPath != null && shortestPath.contains(location) && shortestPath.contains(connection)) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.BLACK);
                }

                g.drawLine(x1, y1, x2, y2);

                double distance = connections.get(connection);
                String distanceStr = String.format("%.2f", distance);
                int labelX = (x1 + x2) / 2;
                int labelY = (y1 + y2) / 2;
                g.drawString(distanceStr, labelX, labelY);
            }
        }

        for (Location location : locations.values()) {
            int x = location.getX();
            int y = location.getY();

            g.setColor(Color.WHITE);
            g.fillOval(x - 10, y - 10, 20, 20);

            g.setColor(Color.BLACK);
            g.drawOval(x - 10, y - 10, 20, 20);

            g.drawString(location.getLocationName(), x - 10, y - 20);
        }
    }

    public void updateShortestPath(List<Location> shortestPath) {
        this.shortestPath = shortestPath;
        repaint();
    }

}