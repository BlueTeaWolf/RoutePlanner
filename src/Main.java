import UI.GraphVisualizer;
import RoutePlanner.Location;
import RoutePlanner.TourCalc;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author BlueTeaWolf (Ole)
 */
public class Main {

    public static void main(String[] args) throws IOException {

        TourCalc tourCalc = new TourCalc();
        FileReader fileReader = new FileReader("src/resources/StadtPunkte");
//        FileReader fileReader = new FileReader(new File("src/resources/TestMap"));

        tourCalc.makeGraph(fileReader);

        System.out.println("made graph");

        HashMap<String, Location> locations = tourCalc.getLocations();
        HashMap<Location, HashMap<Location, Double>> graph = tourCalc.getGraph();

        GraphVisualizer graphVisualizer = new GraphVisualizer(locations, graph);
        graphVisualizer.updateTourCalcObject(tourCalc);
    }

}
