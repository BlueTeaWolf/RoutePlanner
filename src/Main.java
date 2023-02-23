import UI.GraphVisualizer;
import RoutePlanner.Location;
import RoutePlanner.TourCalc;

import java.awt.*;
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
//        FileReader fileReader = new FileReader(new File("src/resources/TestMap"));

        tourCalc.makeGraph(fileReader);

        System.out.println("made graph");

        HashMap<String, Location> locations = tourCalc.getLocations();
        HashMap<Location, HashMap<Location, Double>> graph = tourCalc.getGraph();


//        Location loc = locations.get("E");
//        System.out.println(loc.getLocationName() + " " + loc.getX() + " " + loc.getY());
//
//        List<String> connections = loc.getConnections();
//
//        for (String s : connections) {
//            System.out.println(s);
//        }
//
//        System.out.println(graph.get(loc));
//
//        List<Location> path = tourCalc.findShortestPath("A", "F");
//
//        System.out.println("aaaaaaaa");
//
//        System.out.println(tourCalc.getEuclideanDistance("A", "B"));
//
//        System.out.println("aaaaaaaa");
//
//        System.out.println("Path: ");
//        for (Location location : path) {
//            System.out.println(location.getLocationName());
//        }

        GraphVisualizer graphVisualizer = new GraphVisualizer(locations, graph);

        String s = "A";
        String b = "F";

        List<Location> shortestPath = tourCalc.findShortestPath(s, b);
        Graphics g = graphVisualizer.getGraphics();
        g.setColor(Color.RED);
        for (int i = 0; i < shortestPath.size() - 1; i++) {
            Location curr = shortestPath.get(i);
            Location next = shortestPath.get(i + 1);
            g.drawLine(curr.getX(), curr.getY(), next.getX(), next.getY());
        }

        graphVisualizer.updateShortestPath(shortestPath);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//
//        double distance = 0;
//        int i = 0;
//        for (Location location : path) {
//
//            for (Location loca : path) {
//                if(i <= 0) {
//                    continue;
//                }
//                distance += graph.get(location).get(loca);
//                System.out.println(distance);
//            }
//            i++;
//        }
//        System.out.printf("Distance: %f%n", distance);



//
//        System.out.println(graph.get(locations.get("A")).get(locations.get("B")));
//        List<String> test = locations.get("C").getConnections();
//
//        for (String s:test) {
//            System.out.println(s);
//        }

        System.out.println("END");
    }

}
