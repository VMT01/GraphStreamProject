package Algo;

import GUI.Clicks;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import static GUI.Platform.graph;
import static GUI.Platform.maxNode;

public class BFS {
    private final String startNode;
    private final String endNode;
    private ArrayList<String> path = new ArrayList<>();
    String[] back = new String[maxNode + 1];

    public BFS(String s, String e) {
        this.startNode = s;
        this.endNode = e;
    }

    public void init() {
        System.out.println("BFS Algorithm Path: ");
        graph.getNode(startNode).setAttribute("ui.class", "algo1");
        BFSAlgo(startNode, endNode);
        resetDefault();
    }

    private void BFSAlgo(String startNode, String endNode) {
        Queue<String> q = new LinkedList<>();
        boolean[] visited = new boolean[maxNode + 1];

        q.add(startNode);
        visited[Integer.parseInt(startNode)] = true;

        graph.getNode(startNode).addAttribute("ui.class", "algo1");

        while(!q.isEmpty()) {
            String last = q.peek();
            q.remove();
            if (last.equals(endNode)) {
                graph.getNode(endNode).addAttribute("ui.class", "algo1");
                break;
            }
            for (Edge edge: graph.getNode(last).getEachLeavingEdge()) {
                if (!visited[Integer.parseInt(edge.getNode1().getId())]) {
                    sleep(600);
                    edge.addAttribute("ui.class", "algo1");
                    sleep(300);
                    edge.getNode1().addAttribute("ui.class", "algo2");

                    visited[Integer.parseInt(edge.getNode1().getId())] = true;
                    q.add(edge.getNode1().getId());
                    back[Integer.parseInt(edge.getNode1().getId())] = last;
                }
            }
        }

        sleep(600);
        showPath();
    }

    private void showPath() {
        String _node = endNode;
        while (!_node.equals(startNode)) {
            path.add(_node);
            _node = back[Integer.parseInt(_node)];
        }
        path.add(startNode);
        System.out.println(path);

        for (Node node: graph) {
            node.addAttribute("ui.class", "algo3");
        }
        for (Edge edge: graph.getEachEdge()) {
            edge.addAttribute("ui.class", "algo2");
        }
        graph.getNode(startNode).addAttribute("ui.class", "algo1");
        for (int i = path.size() - 2; i >= 0; i--) {
            graph.getNode(path.get(i)).addAttribute("ui.class", "algo2");
            graph.getEdge(path.get(i + 1) + path.get(i)).addAttribute("ui.class", "algo1");
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
