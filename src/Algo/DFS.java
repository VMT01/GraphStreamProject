package Algo;

import DataStructure.Node_Imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static GraphStream.MainGraph.implementedGraph;

public class DFS {
    private final int startNode;
    private final Map<Integer, ArrayList<Integer>> path = new HashMap<>();

    public DFS(int s) {
        this.startNode = s;
        init();
    }

    private void init() {
        boolean[] visited = new boolean[implementedGraph.getNoVertices()];
        try {
            visited[startNode] = true;
            path.put(startNode, new ArrayList<>());
            DfsAlgo(startNode, visited);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException ignore) { }
    }

    private void DfsAlgo(int startNode, boolean[] visited) {
        Node_Imp _node = new Node_Imp(false);
        _node.setID(startNode);
        startNode = implementedGraph.getNodeIndex(_node);

        for (int i = 0; i < implementedGraph.getNode(startNode).getNoChildren(); i++) {
            int idd = implementedGraph.getNode(startNode).getChildrenByIndex(i).getChild().getID();
            if (!visited[idd]) {
                path.computeIfAbsent(startNode, k -> new ArrayList<>());
                path.get(startNode).add(idd);
                visited[idd] = true;
                DfsAlgo(idd, visited);
            }
        }
    }

    public Map<Integer, ArrayList<Integer>> getPath() {
        return path;
    }
}
