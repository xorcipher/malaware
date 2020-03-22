/*
 * Copyright (C) 2020 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2020 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.*;

/**
 * CampusMap represents an immutable set of unique, named buildings on a given campus, connected by paths
 * that represent distance between buildings.
 */
public class CampusMap implements ModelAPI {

    // Abstraction Function:
    // Every CampusMap, cm, represents a "map", or graph, of vertices, which represent
    // buildings and points on a given campus, with no duplicates.

    // Representation Invariant for all CampusMaps cm:
    // cm != null && cm.campusBuildingList != null &&
    // cm.campusPathList != null && cm.nameToPoint != null &&
    // cm.shortToLong != null
    // forall CampusBuildings b in campusBuildingList, b != null &&
    // forall CampusPaths p in campusPathList, p != null &&
    // forall Strings s in nameToPoint.keySet(), s != null &&
    // nameToPoint.get(s) != null && forall Strings r in shortToLong,
    // r != null && shortToLong.get(r) != null

    /**
     * Holds all of the CampusBuildings in this CampusMap.
     */
    private static final List<CampusBuilding> campusBuildingList = CampusPathsParser.parseCampusBuildings("campus_buildings.tsv");
    /**
     * Holds all of the CampusPaths in this CampusMap.
     */
    private static final List<CampusPath> campusPathList = CampusPathsParser.parseCampusPaths("campus_paths.tsv");
    /**
     * Represents the underlying structure of the CampusMap.
     */
    private Graph<Point, Double> graph;
    /**
     * Maps the abbreviated building names to the full building names.
     */
    private static final Map<String, String> shortToLong = new HashMap<>();
    /**
     * Maps the abbreviated building names to the exact point on campus.
     */
    private static final Map<String, Point> nameToPoint = new HashMap<>();

    /**
     *
     */
    private static final HashMap<String, String> longToShort = new HashMap<>();

    public CampusMap() {
        this.graph = buildGraph();
    }

    @Override
    public boolean shortNameExists(String shortName) {
        return shortToLong.containsKey(shortName);
    }

    // helper method to check whether a longname exists in the campus map list
    public boolean longNameExists(String longName) {
        return longToShort.get(longName) != null;
    }

    public String shortNameForLong(String longName) {
        if (!longToShort.containsKey(longName)) {
            throw new IllegalArgumentException();
        }
        return longToShort.get(longName);
    }

    @Override
    public String longNameForShort(String shortName) {
        if (!shortToLong.containsKey(shortName)) {
            throw new IllegalArgumentException();
        }
        return shortToLong.get(shortName);
    }

    @Override
    public Map<String, String> buildingNames() {
        for (CampusBuilding building : campusBuildingList) {
            shortToLong.put(building.getShortName(), building.getLongName());
            longToShort.put(building.getLongName(), building.getShortName()); // add for safekeeping
            System.err.println(building.getLongName());
        }
        return shortToLong;
    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        if (startShortName == null || endShortName == null) {
            throw new IllegalArgumentException();
        }
        CampusPaths<Point> campusPathGenerator = new CampusPaths<>();
        return campusPathGenerator.cheapestPath(this.graph, nameToPoint.get(startShortName), nameToPoint.get(endShortName));
    }

    /**
     * Helper method to return a graph representing the CampusBuildings and CampusPaths in this CampusMap.
     * @return a graph of type (Point, Double) representing the CampusMap.
     */
    private Graph<Point, Double> buildGraph() {
        Graph<Point, Double> graph = new Graph<>(); // res graph
        for (CampusBuilding building : campusBuildingList) {
            Point point = new Point(building.getX(), building.getY());
            nameToPoint.put(building.getShortName(), point);
            graph.add(point); // add campus building ShortNames
        }
        for (CampusPath path : campusPathList) {
            Point point1 = new Point(path.getX1(), path.getY1());
            Point point2 = new Point(path.getX2(), path.getY2());
            graph.add(point1);
            graph.add(point2);
            graph.addEdge(point1, point2, path.getDistance());
        }
        return graph;
    }
}
