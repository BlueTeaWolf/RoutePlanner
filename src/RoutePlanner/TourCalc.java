package RoutePlanner;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author BlueTeaWolf (Ole)
 */
public class TourCalc {

    private HashMap<Location, HashMap<Location, Double>> graph = new HashMap<>();
    //    private List<Location> locations;
    private HashMap<String, Location> locations = new HashMap<>();
    private HashMap<Location, Double> path;

    public void makeGraph(FileReader fileReader) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;

        while ((line = bufferedReader.readLine()) != null) {

            line = line.replace("(", ",");
            line = line.replace(")", "");

            line = line.replaceAll(" ","");

            String[] values = line.split(",");

            Location location = new Location(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2]));
            locations.put(location.getLocationName(), location);
            for (int i = 3; i < values.length; i++) {
                location.addConnection(values[i]);
            }
        }

        calculateDistance();

    }

    private void calculateDistance() {
        double x1, x2, y1, y2, distance;

        for (String s : locations.keySet()) {
            Location location = locations.get(s);
            List<String> connections = location.getConnections();
            x1 = location.getX();
            y1 = location.getY();
            //Change name of connections
            for (String locName : connections) {
                Location loc = locations.get(locName);
                x2 = loc.getX();
                y2 = loc.getY();
                distance = Point2D.distance(x1,y1,x2,y2);
                HashMap<Location, Double> innerMap = new HashMap<>();
                innerMap.put(loc,distance);
                graph.put(location,innerMap);
            }
        }
    }

    //With the use of the dijkstra algorithm
    public List<Location> findShortestPath(String startName, String destName) {
        // Find the starting and destination locations
        Location start = locations.get(startName);
        Location dest = locations.get(destName);

        // Create a priority queue to keep track of the next closest Location
        Map<Location, Double> distances = new HashMap<>();
        PriorityQueue<Location> queue = new PriorityQueue<>();
        queue.offer(start);

        // Create a map to keep track of the shortest distances to each Location
        distances.put(start, 0.0);

        // Create a map to keep track of the previous Location in the shortest path
        Map<Location, Location> previous = new HashMap<>();

        // Loop through the queue until it's empty or the destination is found
        while (!queue.isEmpty()) {
            Location current = queue.poll();
            if (current == dest) {
                break;
            }

            // Loop through the neighbors of the current Location
            for (Map.Entry<Location, Double> entry : graph.get(current).entrySet()) {
                Location neighbor = entry.getKey();
                double distance = entry.getValue();
                double totalDistance = distances.get(current) + distance;

                // Update the distance to the neighbor if it's shorter than the current distance
                if (!distances.containsKey(neighbor) || totalDistance < distances.get(neighbor)) {
                    distances.put(neighbor, totalDistance);
                    previous.put(neighbor, current);
                    queue.offer(neighbor);
                }
            }
        }

        // Build the shortest path by following the previous map from the destination to the start
        List<Location> path = new ArrayList<>();
        Location current = dest;
        while (current != null) {
            path.add(0, current);
            current = previous.get(current);
        }

        // If there's no path to the destination, return an empty list
        if (path.isEmpty() || path.get(0) != start) {
            return new ArrayList<>();
        }

        return path;
    }

    private double getEuclideanDistance(Location a, Location b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public HashMap<Location, HashMap<Location, Double>> getGraph() {
        return graph;
    }

    public HashMap<String, Location> getLocations() {
        return locations;
    }
}
