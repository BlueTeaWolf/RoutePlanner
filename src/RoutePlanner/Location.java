package RoutePlanner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BlueTeaWolf (Ole)
 */
public class Location implements Comparable<Location>{

    private String locationName;
    private int x;
    private int y;
    private List<String> connections = new ArrayList<>();
    private double distance = Double.POSITIVE_INFINITY;

    public Location(String locationName, int x, int y) {
        this.locationName = locationName;
        this.x = x;
        this.y = y;
    }

    public void addConnection(String location) {
        connections.add(location);
    }

    public String getLocationName() {
        return locationName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<String> getConnections() {
        return connections;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Location other) {
        return Double.compare(distance, other.distance);
    }

}
