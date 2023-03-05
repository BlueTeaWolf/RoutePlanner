package UI;

import RoutePlanner.Location;
import RoutePlanner.TourCalc;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;

/**
 * @author BlueTeaWolf (Ole)
 */
public class GraphVisualizer extends JPanel {

    private HashMap<Location, HashMap<Location, Double>> graph;
    private HashMap<String, Location> locations;
    private List<Location> shortestPath = new ArrayList<>();
    private HashMap<String, List<Location>> allPaths = new HashMap<>();
    private TourCalc tourCalc;

    private JTextField startLocation;
    private JTextField endLocation;
    private String startingPoint;
    private String destinationPoint;

    public GraphVisualizer(HashMap<String, Location> locations,
                           HashMap<Location, HashMap<Location, Double>> graph) {
        this.locations = locations;
        this.graph = graph;

        JFrame frame = new JFrame("Graph Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1920, 1080));
        frame.add(this);

        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        startLocation = new JTextField(10);
        endLocation = new JTextField(10);
        JButton okButton = new JButton("OK");

        okButton.addActionListener(e -> {
            startingPoint = startLocation.getText();
            destinationPoint = endLocation.getText();

            handleNewpath();

        });

        jPanel.add(startLocation);
        jPanel.add(endLocation);
        jPanel.add(okButton);

        frame.getContentPane().add(jPanel, BorderLayout.SOUTH);

        frame.add(this);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        for (Location location : locations.values()) {
            int x1 = location.getX();
            int y1 = location.getY();

            HashMap<Location, Double> connections = graph.get(location);
            for (Location connection : connections.keySet()) {
                int x2 = connection.getX();
                int y2 = connection.getY();

                if (shortestPath != null && shortestPath.contains(location) && shortestPath.contains(connection)) {
                    graphics.setColor(Color.RED);
                } else {
                    graphics.setColor(Color.BLACK);
                }

                graphics.drawLine(x1, y1, x2, y2);

                double distance = connections.get(connection);
                String distanceString = String.format("%.2f", distance);
                int labelX = (x1 + x2) / 2;
                int labelY = (y1 + y2) / 2;
                graphics.drawString(distanceString, labelX, labelY);
            }
        }

        for (Location location : locations.values()) {
            int x = location.getX();
            int y = location.getY();

            graphics.setColor(Color.WHITE);
            graphics.fillOval(x - 10, y - 10, 20, 20);

            graphics.setColor(Color.BLACK);
            graphics.drawOval(x - 10, y - 10, 20, 20);

            graphics.drawString(location.getLocationName(), x - 10, y - 20);
        }
    }

    private void handleNewpath() {
        String input = startingPoint + destinationPoint;

        if(allPaths.get(input) != null) {
            updateShortestPath(allPaths.get(input));
            System.out.println("Path already calculated");
            return;
        }
        System.out.println("Calculated new path");
        updateShortestPath(tourCalc.findShortestPath(startingPoint,destinationPoint));
    }

    public void updateTourCalcObject(TourCalc tourCalc) {
        this.tourCalc = tourCalc;
    }

    public void updateShortestPath(List<Location> shortestPath) {
        this.shortestPath = shortestPath;
        try {
            this.allPaths.put(shortestPath.get(0)
                    .getLocationName() + shortestPath
                    .get(shortestPath.size() - 1)
                    .getLocationName(), shortestPath);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            System.out.println(indexOutOfBoundsException +
                    " in updateShortestPath - GraphVisualizer.\n" +
                    "This is because there is no path or no connection to the points");
        }
            repaint();
    }

}