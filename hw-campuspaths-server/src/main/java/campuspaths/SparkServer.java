package campuspaths;

import campuspaths.utils.CORSFilter;

import com.google.gson.Gson;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import graph.*;
import pathfinder.*;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        Spark.get("/find-path", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                CampusMap map = new CampusMap();
                String building1 = request.queryParams("building1");
                String building2 = request.queryParams("building2");
                Map<String, String> buildingNames = map.buildingNames();
                if (building1 == null || building2 == null) {
                    System.err.println("Buildings entered are null!"); // change this
                    return "200";
                }
                building1 = building1.trim();
                building2 = building2.trim();
                if (map.longNameExists(building1)) {
                    building1 = map.shortNameForLong(building1);
                } else if (!map.shortNameExists(building1)) {
                    return "100";
                }
                if (map.longNameExists(building2)) {
                    building2 = map.shortNameForLong(building2);
                } else if (!map.shortNameExists(building2)) {
                    return "100";
                }
                Path<Point> result = map.findShortestPath(building1, building2);
                if (result == null) {
                    return "300";
                }
                String points = "";
                for (Path<Point>.Segment p : result) {
                    points += p.getStart().getX() + " ";
                    points += p.getStart().getY() + " ";
                    points += p.getEnd().getX() + " ";
                    points += p.getEnd().getY() + " ";
                }
                System.err.println(points);
                return points.trim();
            }
        });
    }

}
