package GraphStream;

import DataStructure.Edge_Imp;
import DataStructure.Graph_Imp;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.view.Viewer;

import java.util.ArrayList;

public class MainGraph {
    public static Graph graph;
    public static Graph_Imp implementedGraph;
    public static int lastId;
    public static int cntEdge;
    public static FxViewer fxViewer;

    ArrayList<String[]> graphText = new ArrayList<>();

    String graphNodeTextArea;
    String attr =   "node { shape: circle; fill-color: yellow; size: 35px; text-alignment: center; text-size: 17px; size-mode: dyn-size; fill-mode: dyn-plain; text-mode: normal; text-color: #2b1c1a; }" +           // default node
                    "edge { fill-color: black; }" +                                                                                                                                                                   // default edge
                    "node.algo1 { shape: circle; fill-color: #FE4D00; size: 35px; text-alignment: center; text-size: 17px; size-mode: dyn-size; fill-mode: dyn-plain; text-mode: normal; text-color: #2b1c1a; }" +    // start & end
                    "node.algo2 { shape: circle; fill-color: #FA9200; size: 35px; text-alignment: center; text-size: 17px; size-mode: dyn-size; fill-mode: dyn-plain; text-mode: normal; text-color: #2b1c1a; }" +    // on the way
                    "node.algo3 { shape: circle; fill-color: #696969; size: 8px; text-size: 0px; size-mode: dyn-size; fill-mode: dyn-plain; text-mode: normal; text-color: #ffffff; }" +                              // not on the way
                    "edge.algo1 { fill-color: #ECC181; size: 5px; arrow-size: 10px; }" +                                                                                                                              // way
                    "edge.algo2 { fill-color: #d3d3d3; }";                                                                                                                                                            // not the way

    public MainGraph (String graphNodeTextArea) {
        this.graphNodeTextArea = graphNodeTextArea;
        lastId = -1;
        cntEdge = 0;
        init();
    }

    private void init() {
        for (String string: graphNodeTextArea.split("\\n")) {
            graphText.add(string.split(" "));
        }

        implementedGraph = new Graph_Imp(graphText.size());
        graph = new MultiGraph("Graph Visualization");
        setStyleSheet();

        fxViewer = new FxViewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        fxViewer.enableAutoLayout();

        Clicks ct = new Clicks();
        ct.start();

        for (int i = 0; i < graphText.size(); i++) {
            graph.addNode(Integer.toString(i + 1));
        }
        for (Node node: graph) {
            node.setAttribute("ui.label", node.getId());
            lastId++;
        }

        for (String[] nodes: graphText) {
            for (int i = 1; i < nodes.length; i++) {
                implementedGraph.getNode(Integer.parseInt(nodes[0]) - 1).addChild(new Edge_Imp(implementedGraph.getNode(Integer.parseInt(nodes[i]) - 1)));
                graph.addEdge(Integer.toString(++cntEdge), nodes[0], nodes[i], true);
            }
        }
    }

    private void setStyleSheet() {
        graph.setStrict(false);
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");
        graph.setAttribute("ui.stylesheet", attr);
    }
}
