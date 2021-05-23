package Algo;

import DataStructure.Node_Imp;

import java.util.*;

import static GraphStream.MainGraph.implementedGraph;

public class BFS {
    private final int startNode;
    private final Map<Integer, ArrayList<Integer>> path = new HashMap<>();

    public BFS(int s) {
        this.startNode = s;
        init();
    }

    private void init() {
        boolean[] visited = new boolean[implementedGraph.getNoVertices()];
        try {
            BfsAlgo(startNode, visited);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException ignore) { }
    }

    private void BfsAlgo(int startNode, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<>();

        queue.add(startNode);
        visited[startNode] = true;

        while (!queue.isEmpty()) {
            int last = queue.peek();
            queue.remove();

            Node_Imp _node = new Node_Imp(false);
            _node.setID(last);
            last = implementedGraph.getNodeIndex(_node);

            for (int i = 0; i < implementedGraph.getNode(last).getNoChildren(); i++) {
                int idd = implementedGraph.getNode(last).getChildrenByIndex(i).getChild().getID();
                if (!visited[idd]) {
                    path.computeIfAbsent(last, k -> new ArrayList<>());
                    path.get(last).add(idd);
                    visited[idd] = true;
                    queue.add(idd);
                }
            }
        }
    }

    public Map<Integer, ArrayList<Integer>> getPath() {
        return path;
    }
}
