package pathfinder.implTest;
import graph.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import pathfinder.CampusMap;
import pathfinder.CampusPaths;

import java.util.*;

import static org.junit.Assert.*;

public class PathfinderTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(30); // 10 seconds max per method tested

    private final Graph<String, Double> simpleGraph = buildSimpleGraph();

    @Test
    public void testFindPathSameNode() {
        Graph<String, Double> graph = new Graph<String, Double>();
        graph.add("HUB");
        graph.add("ECS");
        graph.add("JHN");
        graph.add("KNE");
        graph.addEdge("HUB", "HUB", 0.0);
        CampusPaths<String> campusPaths = new CampusPaths<>();
        assert(graph.size() == 4);
    }

    @Test
    public void testFindPathVerySimple() {
        assert(simpleGraph.size() == 5);
        CampusPaths<String> campusPaths = new CampusPaths<>();
        String expected = "path from HUB to ECS:" + System.lineSeparator() +
                "HUB to KNE with weight 0.500" + System.lineSeparator() +
                "KNE to ECS with weight 0.700" + System.lineSeparator() +
                "total cost: 1.200";
        assertEquals(campusPaths.pathToString(simpleGraph, "HUB", "ECS"), expected);
    }

    @Test
    public void testInvalidArguments() {
        Graph<String, Double> graph = new Graph<>();
        CampusPaths<String> campusPaths = new CampusPaths<>();
        try {
            campusPaths.pathToString(null, "HUB", "ECS");
        } catch (IllegalArgumentException ignore) {
        }

        String expected = "unknown node null" + System.lineSeparator() +
                "unknown node null";
        assertEquals(campusPaths.pathToString(simpleGraph, null, null), expected);
    }

    private Graph<String, Double> buildSimpleGraph() {
        Graph<String, Double> graph = new Graph<String, Double>();
        graph.add("HUB");
        graph.add("ECS");
        graph.add("KNE");
        graph.add("JHN");
        graph.add("TEST");
        graph.addEdge("HUB", "HUB", 0.0);
        graph.addEdge("HUB", "ECS", 5.0);
        graph.addEdge("ECS", "KNE", 1.0);
        graph.addEdge("KNE", "JHN", 4.0);
        graph.addEdge("HUB", "KNE", 0.5);
        graph.addEdge("KNE", "ECS", 0.7);
        graph.addEdge("ECS", "JHN", 2.0);
        graph.addEdge("JHN", "TEST", 2.0);
        return graph;
    }

    @Test
    public void testNoPathFound() {
        Graph<String, Double> graph = new Graph<>();
        CampusMap campusMap = new CampusMap();
        CampusPaths<String> campusPaths = new CampusPaths<>();
        try {
            campusPaths.pathToString(null, "HUB", "ECS");
        } catch (IllegalArgumentException ignore) {
        }
        Map<String, String> buildingNames = campusMap.buildingNames();
        for (String name : buildingNames.keySet()) {
            for (String name2 : buildingNames.keySet()) {
                if (campusMap.findShortestPath(name, name2).toString().length() < 100 && !name.equals(name2)) {
                    System.err.println(name);
                    System.err.println(name2);
                    System.err.println(campusMap.findShortestPath(name, name2).toString());
                }
            }
        }

        String expected = "unknown node null" + System.lineSeparator() +
                "unknown node null";
        assertEquals(campusPaths.pathToString(simpleGraph, null, null), expected);
    }
}