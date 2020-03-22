package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;

import java.util.*;

/**
 * CampusPaths is a helper class to find the minimum cost path between two nodes in a specified graph
 * using Dijkstra's path-finding algorithm. A path between two nodes exists when there exists a commonly
 * held edge between one node to the next, all the way from the start node to the destination node.
 */
public class CampusPaths<N> {

    // Abstraction Function:
    // CampusPaths is not an ADT.

    // Representation Invariant for all CampusPaths p:
    // CampusPaths is not an ADT.

    /** Returns the minimum cost path between two nodes in a graph.
     *
     * @param graph the graph who's nodes will be checked.
     * @param start the start node in the graph.
     * @param dest the destination node in the graph
     * @return A Path of type N representing the path between two nodes, containing node names that connect each node
     * by a commonly held label from each node to the next.
     */
    public Path<N> cheapestPath(Graph<N, Double> graph, N start, N dest) {
        PathComparator p = new PathComparator();
        Queue<Path<N>> active = new PriorityQueue<>(p);

        Set<N> finished = new HashSet<>(); // set of minimum cost Paths from start Node to another Node that we know of
        Path<N> path = new Path<>(start); // TBD: path from start to itself (0 cost)
        path.extend(start, 0.0); // extend path from start to itself
        active.add(path);

        while (!active.isEmpty()) {
            Path<N> minPath = active.remove();
            N minDest = minPath.getEnd();
            if (minDest.equals(dest)) {
                return minPath;
            }
            if (finished.contains(minDest)) {
                continue;
            }
            for (N child : graph.getChildren(minDest)) {
                if (!finished.contains(child)) {
                    List<Double> list = new ArrayList<>(graph.getEdges(minDest, child));
                    if (!list.isEmpty()) {
                        double nextCost = list.get(0);
                        Path<N> newPath = minPath.extend(child, nextCost);
                        active.add(newPath);
                    }
                }
            }
            finished.add(minDest);
        }
        return null;
    }

    /**
     *
     * @param graph the graph who's contents will be parsed into a String representation of a path between nodes in a graph.
     * @param start the start node in the graph.
     * @param dest the destination node in the graph.
     * @return A String representing the path between two nodes in the graph, displayed as such:
     * "path from start to dest:
     * start to dest with weight w1"
     *
     * If a hero does not exist in the graph, the String representation will return "unknown node start/dest" for
     * each specified node that does not exist.
     *
     * If start.equals(dest), a string of the following form will be returned:
     * "path from start to dest:
     * total cost: 0.000"
     *
     * If no path exists between two nodes, a string of the following form will be returned:
     * "path from start to dest:".
     * @throws IllegalArgumentException iff graph == null
     */
    public String pathToString(Graph<N, Double> graph, N start, N dest) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null.");
        }

        StringBuilder res = new StringBuilder();
        boolean invalid = false;

        if (!graph.contains(start)) {
            res.append("unknown node ")
                    .append(start);
            invalid = true;
        }
        if (!graph.contains(dest)) {
            if (invalid) {
                res.append(System.lineSeparator());
            }
            res.append("unknown node ")
               .append(dest);
            invalid = true;
        }
        if (invalid) {
            return res.toString();
        }

        Path<N> path = cheapestPath(graph, start, dest);

        res.append("path from ").append(start).append(" to ").append(dest).append(":");

        if (path != null) {
            for (Path<N>.Segment segment : path) {
                res.append(System.lineSeparator()).append(segment.getStart())
                        .append(" to ")
                        .append(segment.getEnd())
                        .append(String.format(" with weight %.3f", segment.getCost()));
            }
        } else {
            return "path from " + start + (" to ") + dest + (":");
        }
        res.append(System.lineSeparator())
           .append(String.format("total cost: %.3f", path.getCost()));
        return res.toString();
    }

    /**
     * The following class is responsible for testing equality between two paths.
     * One path is considered less than another if its cost is less than the others.
     * Paths with the same cost are considered equal.
     */
    public class PathComparator implements Comparator<Path<N>> {
        @Override
        public int compare(Path<N> path1, Path<N> path2) {
            return Double.compare(path1.getCost(), path2.getCost());
        }
    }
}
