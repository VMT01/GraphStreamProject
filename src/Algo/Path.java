package Algo;

import java.util.ArrayList;

public class Path {
    int id;
    StringBuilder path;
    ArrayList<Integer> pathForGraph;

    public Path(int id, ArrayList<Integer> pathForGraph) {
        this.id = id;
        this.pathForGraph = pathForGraph;

        path = new StringBuilder();
        path.append("[").append(pathForGraph.get(0) + 1).append("]");
        for (int i = 1; i < pathForGraph.size(); i++) {
            path.append("  ->  [").append(pathForGraph.get(i) + 1).append("]");
        }
    }

    public ArrayList<Integer> getPath() {
        return pathForGraph;
    }

    @Override
    public String toString() {
        return this.id + ". " + this.path.toString();
    }
}
