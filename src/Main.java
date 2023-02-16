import RoutePlanner.Location;
import RoutePlanner.TourCalc;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author BlueTeaWolf (Ole)
 */
public class Main {

    public static void main(String[] args) throws IOException {

        TourCalc tourCalc = new TourCalc();
        FileReader fileReader = new FileReader(new File("src/resources/Map"));

        tourCalc.makeGraph(fileReader);

        HashMap<String, Location> locations = tourCalc.getLocations();
        HashMap<Location, HashMap<Location, Double>> graph = tourCalc.getGraph();


        Location loc = locations.get("E");
        System.out.println(loc.getLocationName() + " " + loc.getX() + " " + loc.getY());

        List<String> connections = loc.getConnections();

        for (String s: connections) {
            System.out.println(s);
        }

        System.out.println(graph.get(loc));

        List<Location> path = tourCalc.findShortestPath("E","F");

        System.out.println("Path: ");
        for (Location location : path) {
            System.out.println("1");
            System.out.println(location.getLocationName());
        }
        System.out.println("END");
    }

}
