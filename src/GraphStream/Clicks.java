package GraphStream;

import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

import static GraphStream.MainGraph.*;

public class Clicks extends Thread implements ViewerListener {
    public static boolean loop;

    ViewerPipe viewerPipe;
    boolean edgeConnectFirstTime;

    public Clicks() {
        loop = true;
        edgeConnectFirstTime = true;
        viewerPipe = fxViewer.newViewerPipe();
        viewerPipe.addViewerListener(this);
    }

    @Override
    public void run() {
        while (loop) {
            viewerPipe.pump();
        }
    }

    @Override public void viewClosed(String s) { }

    @Override public void buttonPushed(String s) { }

    @Override public void buttonReleased(String s) { }

    @Override public void mouseOver(String s) { }

    @Override public void mouseLeft(String s) { }
}
