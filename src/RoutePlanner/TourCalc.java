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
    private HashMap<String, Location> locations = new HashMap<>();

    public void makeGraph(FileReader fileReader) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            line = line.replace("(", ",");
            line = line.replace(")", "");

            line = line.replaceAll(" ", "");

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
            HashMap<Location, Double> innerMap = new HashMap<>();
            for (String locName : connections) {
                Location loc = locations.get(locName);
                try {
                    x2 = loc.getX();
                    y2 = loc.getY();
                } catch (NullPointerException nullPointerException) {
                    System.out.println("There is a problem with the Graph!\n " +
                            "Maybe their is no connection to the asked points or a problem in the graph");
                    break;
                }
                distance = Point2D.distance(x1, y1, x2, y2);
                innerMap.put(loc, distance);
                System.out.println("Loc1: " + location.getLocationName() + " Loc2: " + loc.getLocationName() + " distance: " + distance);
            }
            graph.put(location, innerMap);
        }
    }

    //With the use of the dijkstra algorithm
    public List<Location> findShortestPath(String startName, String destName) {

        Location start = locations.get(startName);
        Location dest = locations.get(destName);

        Map<Location, Double> distances = new HashMap<>();
        PriorityQueue<Location> queue = new PriorityQueue<>();

        try {
            queue.offer(start);
        } catch (NullPointerException nullPointerException) {
            System.out.println("Location does not exist: " + nullPointerException + "in findShortestPath - TourCalc");
        }

        distances.put(start, 0.0);

        Map<Location, Location> previous = new HashMap<>();

        while (!queue.isEmpty()) {
            Location current = queue.poll();
            if (current == dest) {
                break;
            }

            for (Map.Entry<Location, Double> entry : graph.get(current).entrySet()) {
                Location neighbor = entry.getKey();
                double distance = entry.getValue();
                double totalDistance = distances.get(current) + distance;

                if (!distances.containsKey(neighbor) || totalDistance < distances.get(neighbor)) {
                    distances.put(neighbor, totalDistance);
                    previous.put(neighbor, current);
                    queue.offer(neighbor);
                }
            }
        }

        List<Location> path = new ArrayList<>();
        Location current = dest;
        while (current != null) {
            path.add(0, current);
            current = previous.get(current);
        }

        if (path.isEmpty() || path.get(0) != start) {
            return new ArrayList<>();
        }

        Location lastLoc = path.get(0);
        double distance = 0;
        for (int i = 1; i < path.size(); i++) {
            Location currentLoc = path.get(i);
            distance += graph.get(lastLoc).get(currentLoc);
            lastLoc = currentLoc;
        }
        System.out.printf("Distance to destination: %fkm \n", distance);
        return path;
    }



    public HashMap<Location, HashMap<Location, Double>> getGraph() {
        return graph;
    }

    public HashMap<String, Location> getLocations() {
        return locations;
    }
}
