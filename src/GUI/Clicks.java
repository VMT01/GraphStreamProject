package GUI;

import Algo.BFS;
import Algo.DFS;
import org.graphstream.graph.Edge;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

import java.util.logging.Level;
import java.util.logging.Logger;

import static GUI.Platform.*;

public class Clicks extends Thread implements ViewerListener {
    private final ViewerPipe fromViewer;
    private int clickCount = 0;
    private String lastStartNode;
    private String lastEndNode;

    public static boolean loop;

    public Clicks() {
        loop = true;
        fromViewer = viewer.newViewerPipe();
        fromViewer.addViewerListener(this);
        fromViewer.addSink(graph);
    }

    @Override
    public void run() {
        while (loop) {
            fromViewer.pump();

            if (clicked) {
                System.out.println(choose);
                if (choose.equals("DFS")) {
                    new DFS(start, end).init();
                } else if (choose.equals("BFS")) {
                    new BFS(start, end).init();
                }
                clicked = false;
            }
        }
        loop = true;
    }

    @Override
    public void viewClosed(String viewName) {

    }

    @Override
    public void buttonPushed(String id) {

//        graph.getNode(start).addAttribute("ui.class", "algo1");
//        for (Edge edge: graph.getNode(start).getEachLeavingEdge()) {
//            sleep(1000);
//            edge.addAttribute("ui.class", "algo");
//        }

//        clickCount = (clickCount + 1) % 2;
//        if (Platform.graph.getNode(id).getAttribute("ui.class") == null) {
//            if (clickCount == 1) {                                  // Start point
//                Platform.graph.getNode(id).addAttribute("ui.class", "start");
//                if (lastStartNode != null) {
//                    Platform.graph.getNode(lastStartNode).removeAttribute("ui.class");
//                }
//                lastStartNode = id;
//                start = id;
//            } else {                                                // End point
//                Platform.graph.getNode(id).addAttribute("ui.class", "end");
//                if (lastEndNode != null) {
//                    Platform.graph.getNode(lastEndNode).removeAttribute("ui.class");
//                }
//                lastEndNode = id;
//                end = id;
//            }
//        } else {                                                    // Remove duplicate node
//            Platform.graph.getNode(id).removeAttribute("ui.class");
//            if (clickCount == 1) {
//                start = null;
//            } else {
//                end = null;
//            }
//        }
//
//        System.out.println("Start node: " + start + ", End node: " + end);
    }

    @Override
    public void buttonReleased(String id) {

    }

    protected void sleep(int x) {
        try {
            Thread.sleep(x);
        } catch (InterruptedException ex) {
            Logger.getLogger(Clicks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
