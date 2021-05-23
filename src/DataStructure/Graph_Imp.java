package DataStructure;

import java.util.ArrayList;

public class Graph_Imp {
    private ArrayList<Node_Imp> vertices;

    public Graph_Imp() {
        Node_Imp.setNodeId(0);
        vertices = new ArrayList<>();
    }

    public Graph_Imp(int noVertices) {
        Node_Imp.setNodeId(0);
        vertices = new ArrayList<>(noVertices);
        for (int i = 0; i < noVertices; i++) {
            vertices.add(new Node_Imp(true));
        }
    }

    public ArrayList<Node_Imp> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Node_Imp> vertices) {
        this.vertices = vertices;
    }

    public int getNoVertices() {
        return vertices.size();
    }

    public Node_Imp getNode(int index) {
        return vertices.get(index);
    }

    public void addNode(Node_Imp _node) {
        vertices.add(_node);
    }

    public void removeNode(Node_Imp _node) {
        for (Node_Imp node: vertices) {
            if (node.getID() != _node.getID()) {
                node.getChildren().removeIf(edge -> edge.getChild().getID() == _node.getID());
            }
        }
        vertices.remove(_node);
    }

    public void removeAllNode(){
        vertices.clear();
    }

    public int getNodeIndex(Node_Imp _node) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).getID() == _node.getID()) {
                return i;
            }
        }
        return -1;
    }

    public void print() {
        for (Node_Imp vertex : vertices) {
            vertex.printNodeChildren();
        }
    }
}
