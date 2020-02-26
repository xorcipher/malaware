package graph;
import java.util.*;

/**
 * <b>Graph</b> represents a mutable set of vertices connected by directed, labelled edges,
 * where singular edges are not bidirectional. Vertices may have edges connected to themselves.
 * <p>Examples of Graphs include the "Empty Graph", "Node1", "Node1(5) Node2",
 *      "Node1(6) Node2
 *       Node2(3) Node1" and
 *
 *      "Node1(1) Node2
 *      Node1(2) Node2"
 */
public class Graph<N, E> {

    // Abstraction Function:
    // Every Graph, g, represents a finite set of vertices with no duplicates.

    // Representation Invariant for all graphs g:
    // g != null && g.graph != null &&
    // forall Maps m in g.graph.keySet(), m != null &&
    // forall Strings s in m.keySet(), s != null &&
    // forall Sets q in m.get(s), q != null &&
    // forall Strings r in q, r != null

    /**
     * Holds all the nodes as keys in this graph, as well as all children of parent nodes as keys,
     * and all edges as values in a Set.
     */
    private Map<N, Map<N, Set<E>>> graph;
    /**
     * Debug flag. Set to true to debug extensively.
     */
    private static final boolean DEBUG_FLAG = false;

    /**
     * @spec.effects Constructs a new Graph with no nodes "Empty Graph".
     */
    public Graph() {
        graph = new HashMap<>();
        checkRep();
    }


    /**
     * Adds a new node to the graph by specified name.
     *
     * @param node the new node that will be added to the graph.
     * @return true if and only if the specified node does not already exist in the graph.
     * @spec.modifies this
     * @spec.effects Adds 'node' to this, unconnected to any other node in this.
     * @spec.requires this != null
     */
    public boolean add(N node) {
        checkRep();
        if (this.graph.get(node) != null) {
            return false;
        } else {
            this.graph.put(node, new HashMap<>());
            return true;
        }
    }

    /**
     * Adds a new directed edge, or replaces the current edge, between two nodes in the graph, by specified name.
     *
     * @param parent the node which the new edge will be connected from.
     * @param child the node which the new edge will be connected to.
     * @param label the label which the edge extending from parent to child will have.
     * @return true if and only if any edge does not already exist between parent and child.
     * @spec.modifies this
     * @spec.effects adds an edge and stores it as a value in the Set belonging to the HashMap belonging to parent.
     * @spec.requires this != null AND parent != null AND child != null
     * @throws IllegalArgumentException if this.contains(parent) = false OR this.contains(child) = false
     */
    public boolean addEdge(N parent, N child, E label) {
        checkRep();
        if (!this.contains(parent) || !this.contains(child)) {
            throw new IllegalArgumentException();
        }
        boolean res = true;
        Map<N, Set<E>> tempMap = this.graph.get(parent);
        Set<E> tempSet = tempMap.get(child);
        Set<E> labels = new HashSet<>(){{add(label);}};
        if (tempSet != null) {
            res = false;
            labels = tempMap.get(child);
            labels.add(label);
        }
        tempMap.put(child, labels);
        return res;
    }

    /**
     * Returns all existing edges extending from a specified parent node to a specified child node.
     *
     * @param parent the parent node which the edges extend from.
     * @param child the child node which the edges extend to.
     * @return a Set of all the names of the edges extending between parent and child.
     * @spec.requires this != null
     * @throws IllegalArgumentException if this.contains(parent) = false OR this.contains(child) = false
     */
    public Set<E> getEdges(N parent, N child) {
        if (!this.contains(parent) || !this.contains(child)) {
            throw new IllegalArgumentException();
        }
        if (this.graph.get(parent).get(child) == null) {
            return new HashSet<>();
        }
        return this.graph.get(parent).get(child);
    }

    /**
     * Returns the size of the graph, equal to the number of nodes currently in the graph.
     *
     * @return the number of nodes in this graph.
     * @spec.requires this != null
     */
    public int size() {
        return this.graph.size();
    }

    /**
     * Returns true if this graph contains the specified node.
     *
     * @param node the specified name of the node that the graph will be checked to contain.
     * @return true if and only if this graph contains a node with the specified name.
     * @spec.requires this != null
     */
    public boolean contains(N node) {
        return this.graph.get(node) != null;
    }

    /**
     * Returns a string representation of this Graph. Valid toString outputs are sorted by node name.
     * Valid example outputs include
     *      "Node1(6) Node2
     *       Node2(3) Node1",
     *
     *      "Node1"
     *      "Node2", and
     *
     *      "Node1(5) Node2
     *      Node1(8) Node2"
     *
     * @spec.requires this != null
     * @return a String representation of the graph represented by this.
     */
    public String toString() {
        if (this.graph.isEmpty()) {
            return "Empty Graph";
        }
        StringBuilder res = new StringBuilder();
        for (N parent : graph.keySet()) {
            Map<N, Set<E>> childrenToEdges = graph.get(parent);
            if (childrenToEdges.isEmpty()) { // no children
                res.append(parent);
                res.append(System.lineSeparator());
            } else {
                for (N child : childrenToEdges.keySet()) {
                    for (E label : childrenToEdges.get(child)) {
                        res.append(parent)
                        .append("(")
                        .append(label)
                        .append(") ")
                        .append(child)
                        .append(System.lineSeparator());
                    }
                }
            }
        }
        res = new StringBuilder(res.toString().trim());
        return res.toString();
    }

    /**
     * Returns the children of a given node in the graph.
     *
     * @param parent the Node who's children will be returned.
     * @return a Set of Strings representing all children extending from the specified parent node.
     * @spec.requires this != null
     * @throws IllegalArgumentException if this.contains(node) = false
     */
    public Set<N> getChildren(N parent) {
        if (!this.contains(parent)) {
            throw new IllegalArgumentException();
        }
        return graph.get(parent).keySet();
    }

    /**
     * Returns a set of the all nodes currently in the graph, sorted by node name.
     *
     * @return a Set of Strings representing all nodes in this.
     * @spec.requires this != null
     */
    public Set<N> getNodes() {
        return graph.keySet();
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        assert (this != null);
        assert (this.graph != null);
        if (DEBUG_FLAG) {
            for (N parent : graph.keySet()) {
                assert(parent != null);
                for (N child : graph.get(parent).keySet()) {
                    for (E label : graph.get(parent).get(child)) {
                        assert(label != null);
                    }
                    assert(child != null);
                }
            }
        }
    }
}