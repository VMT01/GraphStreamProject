package DataStructure;

import java.util.ArrayList;

public class Node_Imp {
    private static int NODE_ID = 0;

    private int ID;
    private ArrayList<Edge_Imp> children;

    public Node_Imp(boolean isGraphNode) {
        if (isGraphNode) {
            ID = NODE_ID;
            NODE_ID++;
        } else {
            ID = -1;
        }
        children = new ArrayList<>();
    }

    public Node_Imp(boolean isGraphNode, int noChildren) {
        if (isGraphNode) {
            ID = NODE_ID;
            NODE_ID++;
        } else {
            ID = -1;
        }
        children = new ArrayList<>(noChildren);
    }

    public void addChild(Edge_Imp _child) {
        children.add(_child);
    }

    public void addChildren(ArrayList<Edge_Imp> _children) {
        children.addAll(_children);
    }

    public void removeChild(Edge_Imp _child) {
        children.remove(_child);
    }

    public void removeAllChildren() {
        children.clear();
    }

    public static int getNodeId() {
        return NODE_ID;
    }

    public static void setNodeId(int aNODE_ID) {
        NODE_ID = aNODE_ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ArrayList<Edge_Imp> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Edge_Imp> children) {
        this.children = children;
    }

    public int getNoChildren() {
        return children.size();
    }

    public Edge_Imp getChildrenByIndex(int index) {
        return children.get(index);
    }

    public Edge_Imp getChildrenByID(int id) {
        for (int i = 0; i < getNoChildren(); i++) {
            if (children.get(i).getChild().getID() == id) {
                return children.get(i);
            }
        }
        return null;
    }

    public void printNode() {
        System.out.print(ID + 1);
    }

    public void printNodeChildren() {
        System.out.print("[" + (ID + 1) + "]");
        for (Edge_Imp child : children) {
            System.out.print(" -> [");
            child.getChild().printNode();
            System.out.print("]");
        }
        System.out.println();
    }
}
