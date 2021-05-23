package DataStructure;

public class Edge_Imp {
    private Node_Imp child;

    public Edge_Imp() {
        child = null;
    }

    public Edge_Imp(Node_Imp _child) {
        child = _child;
    }

    public Node_Imp getChild() {
        return child;
    }

    public void setChild(Node_Imp child) {
        this.child = child;
    }

    public void copy(Edge_Imp _edge) {
        child = _edge.getChild();
    }
}
