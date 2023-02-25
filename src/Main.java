import UI.GraphVisualizer;
import RoutePlanner.Location;
import RoutePlanner.TourCalc;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

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

        GraphVisualizer graphVisualizer = new GraphVisualizer(locations, graph);
        graphVisualizer.updateTourCalcObject(tourCalc);
//
//        Scanner scanner = new Scanner(System.in);
//
//        boolean isListening = true;

//        while(isListening) {
//            System.out.println("Programmende einfach 'END' eingeben");
//            System.out.println("Startpunkt: ");
//
//            String firstLcoation = scanner.next();
//
//            if(firstLcoation.equals("END") || firstLcoation.equals("ENDE")) {
//                System.out.println("END");
//                System.exit(1);
//            }
//
//            System.out.println("Zielpunkt: ");
//            String secondLocation = scanner.next();

//            List<Location> shortestPath = tourCalc.findShortestPath(firstLcoation, secondLocation);
//            Graphics g = graphVisualizer.getGraphics();
//            g.setColor(Color.RED);
//            for (int i = 0; i < shortestPath.size() - 1; i++) {
//                Location curr = shortestPath.get(i);
//                Location next = shortestPath.get(i + 1);
//                g.drawLine(curr.getX(), curr.getY(), next.getX(), next.getY());
//            }
//
//            graphVisualizer.updateShortestPath(shortestPath);
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

}
