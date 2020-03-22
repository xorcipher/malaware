package graph.implTest;

import graph.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.*;

import static org.junit.Assert.*;

public class GraphTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(30); // 10 seconds max per method tested

    // some simple Graphs
    public Graph<String, String> graph1 = new Graph<>();
    public Graph<String, String> graph2 = new Graph<>();
    public Graph<String, String> graph3 = new Graph<>();
    public Graph<String, String> graph4 = new Graph<>();
    public Graph<String, String> graph5 = new Graph<>();
    public Graph<String, String> graph6 = new Graph<>();
    public Graph<String, String> graph8 = new Graph<>();
    public Graph<String, String> graph9 = new Graph<>();
    public Graph<String, String> graph10 = new Graph<>();
    public Graph<String, String> graph11 = new Graph<>();
    public Graph<String, String> graph12 = new Graph<>();
    public Graph<String, String> graph13 = new Graph<>();
    public Graph<String, String> graph14 = new Graph<>();
    public Graph<String, String> graph15 = new Graph<>();
    public Graph<String, String> graph16 = new Graph<>();
    public Graph<String, String> graph17 = new Graph<>();
    public Graph<String, String> graph18 = new Graph<>();
    public Graph<String, String> graph19 = new Graph<>();
    public Graph<String, String> graph20 = new Graph<>();

    @Test
    public void checkRepTest() {
        //Node node1 = new Node("");
        try {
            graph1.add("node1");
        } catch (AssertionError e) {
            assertEquals(0, graph1.size());
        }
    }

    @Test
    public void addNodeThatAlreadyExists() {
        //Node node1 = new Node("A");
        graph2.add("node1");
        graph2.add("node1");
        String s = "node1";
        assertEquals(graph2.toString(), s);
        assertEquals(1, graph2.size());
    }

    @Test
    public void assertSizeIsMoreThanOne() {
        for (int i = 0; i < 20; i++) {
            graph3.add("" + i);
        }
        assertEquals(20, graph3.size());
    }

    @Test
    public void assertSizeIsOne() {
        graph4.add("A");
        assertEquals(1, graph4.size());
        assertFalse(graph4.size() != 1);
    }

    @Test
    public void addEdgeToInvalidNodes() {
        graph5.add("A");
        graph5.add("B");
        String s = "A" + System.lineSeparator() + "B";
        Set<String> set = new HashSet<>();
        set.add("A"); set.add("B");
        try {
            graph5.addEdge("A", "C", "5");
        } catch(IllegalArgumentException e) {
            assertEquals(graph5.toString(), s);
            assertEquals(graph5.getNodes(), set);
        }
    }

    @Test
    public void addEdgeToSelf() {
        graph6.add("A");
        graph6.addEdge("A", "A", "2");
        String s = "A(2) A";
        //System.err.println(graph6.getChildren("A"));
        //System.err.println(graph6.toString());
        assertEquals(graph6.toString(), s);
    }

    @Test
    public void addChildToNode() {
        graph8.add("A");
        graph8.add("B");
        graph8.addEdge("A", "B", "5");
        String s = "A(5) B" + System.lineSeparator() + "B";
        assertEquals(graph8.toString(), s);
        assertEquals(2, graph8.size());
    }

    @Test
    public void testContainsOnNonexistentNode() {
        graph9.add("A");
        assertFalse(graph9.contains("B"));
    }

    @Test
    public void testContainsOnRealNode() {
        graph10.add("A");
        assertTrue(graph10.contains("A"));
    }

    @Test
    public void testToStringEmpty() {
        assertEquals("Empty Graph", graph11.toString());
    }

    @Test
    public void testToStringOneNode() {
        graph12.add("A");
        assertEquals("A", graph12.toString());
    }

    @Test
    public void testToStringMultipleNodes() {
        graph13.add("A");
        graph13.add("B");
        String compare = "A" + System.lineSeparator() + "B";
        assertEquals(graph13.toString(), compare);
        assertEquals(2, graph13.size());
    }

    @Test
    public void testToStringWithEdges() {
        graph14.add("A");
        graph14.add("B");
        graph14.add("C");
        graph14.addEdge("A", "B", "5");
        graph14.addEdge("A", "B", "6");
        graph14.addEdge("A", "B", "7");
        graph14.addEdge("A", "B", "8");
//        System.err.println(graph14.getEdges("A", "B"));
//        System.err.println(graph14.getChildren("A"));
//        System.err.println(graph14.toString());
        String res = "A(5) B" + System.lineSeparator();
        res += "A(6) B" + System.lineSeparator();
        res += "A(7) B" + System.lineSeparator() + "A(8) B" + System.lineSeparator() + "B" + System.lineSeparator() + "C";
        assertEquals(graph14.toString(), res);
    }

    @Test
    public void testGetChildrenOnZeroChildrenNode() {
        graph15.add("A");
//        System.err.println(graph15.getChildren("A"));
        assertEquals("[]", graph15.getChildren("A").toString());
    }

    @Test
    public void testGetChildrenOnNodeWithChildren() {
        graph16.add("A");
        graph16.add("B");
        graph16.addEdge("A", "B", "5");
        String children = "[B]";
        assertEquals(graph16.getChildren("A").toString(), children);
    }


    @Test
    public void testGetChildrenWithManyChildren() {
        graph20.add("A");
        graph20.add("B");
        graph20.add("C");
        graph20.add("D");
        graph20.add("E");
        graph20.add("F");
        graph20.add("G");
        graph20.add("H");
        graph20.add("I");
        graph20.add("J");
        graph20.add("K");
        graph20.add("L");
        graph20.add("M");
        graph20.add("N");
        graph20.add("O");
        graph20.add("P");
        int x = 0;
        int y = 100;
        for (int i = 66; i < 81; i++) {
            graph20.addEdge("A", "" + (char)(i), Integer.toString(x));
            x++;
        }
        for (int i = 80; i > 65; i--) {
            graph20.addEdge("P", "" + (char)(i), Integer.toString(y));
            y++;
        }

        String expectedChildren1 = "[B, C, D, E, F, G, H, I, J, K, L, M, N, O, P]";
        String expectedChildren2 = "[B, C, D, E, F, G, H, I, J, K, L, M, N, O, P]";
//        System.err.println(actualChildren1);
//        System.err.println(actualChildren2);
//        System.err.println(expectedChildren1);
//        System.err.println(expectedChildren2);
        assertEquals(graph20.getChildren("A").toString(), expectedChildren2);
        assertEquals(graph20.getChildren("P").toString(), expectedChildren1);
    }

    @Test
    public void testVariousStringMethods() {
        Graph<String, String> graph21 = new Graph<>();
        graph21.add("A");
        assertTrue(graph21.contains("A"));
        graph21.add("B");
        graph21.addEdge("A", "B", "3");
        String expected = "[B]";
        String res = graph21.getChildren("A").toString();
        assertEquals(res, expected);
        assertEquals("[A, B]", graph21.getNodes().toString());
    }

    @Test
    public void testGetNodes() {
        graph17.add("A");
        graph17.add("B");
        graph17.addEdge("A", "B", "5");
        Set<String> newSet = new HashSet<>();
        newSet.add("A");
        newSet.add("B");
        assertEquals(graph17.getNodes(), newSet);
    }

    @Test
    public void testAddSameNode() {
        graph18.add("A");
        assertFalse(graph18.add("A"));
        assertTrue(graph18.add("B"));
        assertEquals(2, graph18.size());
    }

    @Test
    public void testHugeGraph() {
        Graph<String, String> bigGraph = new Graph<>();
        for (int i = 0; i < 5000; i++) {
            bigGraph.add("Node" + i);
        }

        for (int i = 0; i < 4999; i++) {
            int res = i+1;
            int label = i-3;
            String firstNode = "Node" + i;
            String secondNode = "Node" + res;
            bigGraph.addEdge(firstNode, secondNode, Integer.toString(label));
        }

        //System.out.println(bigGraph.toString());
    }

    @Test
    public void testBiDirectionalToString() {
        graph19.add("A");
        graph19.add("B");
        graph19.add("C");
        graph19.addEdge("A", "B", "5");
        graph19.addEdge("A", "B", "3");
        graph19.addEdge("B", "A", "19");
        graph19.addEdge("C", "B", "6");
        String s = "A(3) B" + System.lineSeparator() + "A(5) B" + System.lineSeparator() + "B(19) A" + System.lineSeparator() + "C(6) B";
        //System.err.println(graph19.toString());
        assertEquals(graph19.toString(), s);
        assertEquals(3, graph19.size());
    }

    @Test
    public void checkIsEmpty() {
        Graph<String, String> graph22 = new Graph<>();
        assertEquals(0, graph22.size());
        graph22.add("A");
        assertFalse(graph22.add("A"));
        graph22.add("B");
        assertEquals(graph22.size(), 2);
    }

    @Test
    public void testAddNullNode() {
        Graph<String, String> graph23 = new Graph<>();
        try {
            graph23.add(null);
        } catch (AssertionError ignored) {

        }
    }

    @Test
    public void testMoreNullStuff() {
        Graph<String, String> graph24 = new Graph<>();
        graph24.add("A");
        graph24.add("B");
        String s = null;
        try {
            graph24.addEdge("A", "B", null);
        } catch (AssertionError e) {
            try {
                graph24.contains(s);
            } catch (AssertionError g) {
                try {
                    graph24.getChildren(s);
                } catch (AssertionError ignored) {

                }
            }
        }
    }

      @Test
      public void hugeTestAndManyEdges() {
        Graph<String, String> bigGraph = new Graph<>();
        for (int i = 1; i < 100000; i++) {
            bigGraph.add("Node" + i);
            bigGraph.add("Node" + i*-1);
        }

        for (int i = 1; i < 99999; i++) {
          //  if (i % 100 == 0) {
            //    System.err.println(i);
            //}
            bigGraph.addEdge("Node" + i, "Node" + i*-1, "edge" + i);
            int next = i +1;
            bigGraph.addEdge("Node" + i, "Node" + next, "edge" + i*-1);
            int nextEdge = i - 10000000;
            bigGraph.addEdge("Node" + i*-1, "Node" + i, "edge" + nextEdge);
            bigGraph.addEdge("Node1", "Node" + i, "edgeOne");
            bigGraph.addEdge("Node2", "Node" + i, "edgeTwo");
            bigGraph.addEdge("Node3", "Node" + i, "edgeThree");
            bigGraph.addEdge("Node4", "Node" + i, "edgeFour");

        }
        //System.out.println(bigGraph.toString());
          //System.err.println(bigGraph.size());
          //System.err.println(bigGraph.toString());
      }

      @Test
      public void variousFunctions() {
        Graph<String, String> graph = new Graph<>();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        graph.addEdge("A", "B", "3");
        graph.addEdge("A", "B", "4");
        graph.addEdge("A", "B", "5");
        //System.err.println(graph.getEdges("A", "B"));
      }

      @Test
      public void genericsTest() {
          List<Object> oList = new ArrayList<>();
          oList.add(new Object());
          List<Object> oList2 = new ArrayList<>();
          Graph<List<Object>, Map<String, String>> genericGraph = new Graph<>();
          genericGraph.add(oList);
          genericGraph.add(oList2);
          Map<String, String> mapEdge = new HashMap<>();
          mapEdge.put("This is a test", "Edge!");
          genericGraph.addEdge(oList, oList2, mapEdge);
          assertEquals(2, genericGraph.size());
          Set<Map<String, String>> expectedChildren = new HashSet<>();
          Map<String, String> expectedEdge = new HashMap<>();
          expectedEdge.put("This is a test", "Edge!");
          expectedChildren.add(expectedEdge);
          assertEquals(expectedChildren, genericGraph.getEdges(oList, oList2));
      }
}