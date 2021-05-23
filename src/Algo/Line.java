package Algo;

import java.util.ArrayList;
import java.util.Arrays;

import static GraphStream.MainGraph.implementedGraph;

public class Line {
    int start;
    int end;
    int id;
    Integer[] path;
    ArrayList<Path> returnPath = new ArrayList<>();

    public Line(int s, int e) {
        start = s;
        end = e;
        id = 0;
        init();
    }

    private void init() {
        boolean[] visited = new boolean[implementedGraph.getNoVertices()];
        path = new Integer[implementedGraph.getNoVertices()];
        Arrays.fill(path, -1);
        try {
            lineAlgo(start, 0, visited);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException ignore) { }
    }

    private void lineAlgo(int start, int index, boolean[] visited) {
        visited[start] = true;
        path[index] = start;

        if (start == end) {
            getResult();
        } else {
            for (int i = 0; i < implementedGraph.getNode(start).getNoChildren(); i++) {
                int idd = implementedGraph.getNode(start).getChildrenByIndex(i).getChild().getID();
                if (!visited[idd]) {
                    visited[idd] = true;
                    lineAlgo(idd, index + 1, visited);
                }
            }
        }

        path[index] = -1;
        visited[start] = false;
    }

    private void getResult() {
        Object[] converted = Arrays.stream(path).distinct().toArray();
        ArrayList<Integer> pathConverted = new ArrayList<>();
        for (int i = 0; i < converted.length - 1; i++) {
            pathConverted.add(Integer.parseInt(converted[i].toString()));
        }
        returnPath.add(new Path(++id, pathConverted));
    }

    public ArrayList<Path> getPath() {
        return returnPath;
    }
}
