package Algo;

import GUI.Clicks;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static GUI.Platform.*;

public class DFS {
    private final String startNode;
    private final String endNode;
    private ArrayList<String> path = new ArrayList<>();

    public DFS(String s, String e) {
        this.startNode = s;
        this.endNode = e;
    }

    public void init() {
        System.out.println("DFS Algorithm Path: ");
        boolean[] visited = new boolean[maxNode + 1];
        graph.getNode(startNode).setAttribute("ui.class", "algo1");
        DFSAlgo(startNode, endNode, visited);
        resetDefault();
    }

    private void DFSAlgo(String startNode, String endNode, boolean[] visited) {
        visited[Integer.parseInt(startNode)] = true;
        path.add(startNode);

        if (startNode.equals(endNode)) {
            reset();
        } else {
            for (Edge edge: graph.getNode(startNode).getEachLeavingEdge()) {
                if (!visited[Integer.parseInt(edge.getNode1().getId())]) {
                    DFSAlgo(edge.getNode1().getId(), endNode, visited);
                }
            }
        }

        path.remove(path.size() - 1);
        visited[Integer.parseInt(startNode)] = false;
    }

    private void reset() {
        System.out.println(path);

        graph.getNode(startNode).addAttribute("ui.class", "algo1");
        for (int i = 1; i < path.size(); i++) {
            sleep(200);
            graph.getEdge(path.get(i - 1) + path.get(i)).addAttribute("ui.class", "algo1");
            sleep(100);
            graph.getNode(path.get(i)).addAttribute("ui.class", "algo2");
        }
        graph.getNode(endNode).addAttribute("ui.class", "algo1");
        sleep(2000);

        resetDefault();
    }

    private void resetDefault() {
        for (Node node: graph) {
            node.addAttribute("ui.class", "default");
        }
        for (Edge edge: graph.getEachEdge()) {
            edge.addAttribute("ui.class", "default");
        }
    }

    protected void sleep(int x) {
        try {
            Thread.sleep(x);
        } catch (InterruptedException ex) {
            Logger.getLogger(Clicks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
