package GUI;

import Input.ReadFile;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Platform extends JFrame {
    public static Graph graph;
    public static Viewer viewer;

    private JFrame frame;

    private JTextField textField1;
    private JTextField textField2;

    private JButton startButton;
    private JButton chooseFileButton;
    private JButton takeScreenshotButton;
    private JButton closeGraphButton;

    private JPanel mainPanel;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;

    private JLabel StartPoint;
    private JLabel EndPoint;
    private JLabel ChooseAlgo;

    private JComboBox<ComboItem> comboBox1;

    private JFileChooser jFileChooser1;
    private JFileChooser jFileChooser2;

    public static String start;
    public static String end;
    public static boolean clicked = false;
    public static int maxNode;
    public static String choose;

    public Platform() {
        init();
        initFileChooser();
    }

    private void initFileChooser() {
        jFileChooser1.setDialogTitle("Open Source File");
        jFileChooser1.setAcceptAllFileFilterUsed(false);
        jFileChooser1.addChoosableFileFilter(new FileNameExtensionFilter(".txt files", "txt"));

        jFileChooser2.setDialogTitle("Save Screenshot");
        jFileChooser2.setAcceptAllFileFilterUsed(false);
        jFileChooser2.addChoosableFileFilter(new FileNameExtensionFilter(".png files", "png"));
    }

    private void init() {
        frame = new JFrame();
        jFileChooser1 = new JFileChooser();
        jFileChooser2 = new JFileChooser();

        add(mainPanel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(new Color(0, 255, 255));
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        setForeground(new Color(255, 51, 102));
        setSize(new Dimension(640, 320));
        setResizable(false);

        chooseFileButton.setBackground(new Color(20, 160, 210));
        chooseFileButton.setForeground(new Color(255, 255, 255));
        chooseFileButton.setMaximumSize(new Dimension(180, 40));
        chooseFileButton.setMinimumSize(new Dimension(180, 40));
        chooseFileButton.setFont(new Font("Serif", Font.BOLD, 16));
        chooseFileButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        chooseFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    loadFromFile();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Platform.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        takeScreenshotButton.setBackground(new Color(20, 160, 210));
        takeScreenshotButton.setForeground(new Color(255, 255, 255));
        takeScreenshotButton.setMaximumSize(new Dimension(180, 40));
        takeScreenshotButton.setMinimumSize(new Dimension(180, 40));
        takeScreenshotButton.setFont(new Font("Serif", Font.BOLD, 16));
        takeScreenshotButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        takeScreenshotButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    screenshot(e);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        closeGraphButton.setBackground(new Color(20, 160, 210));
        closeGraphButton.setForeground(new Color(255, 255, 255));
        closeGraphButton.setMaximumSize(new Dimension(180, 40));
        closeGraphButton.setMinimumSize(new Dimension(180, 40));
        closeGraphButton.setFont(new Font("Serif", Font.BOLD, 16));
        closeGraphButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeGraphButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                closeGraph();
            }
        });

        StartPoint.setFont(new Font("Serif", Font.PLAIN, 16));
        textField1.setBackground(new Color(100, 100, 200));
        textField1.setForeground(new Color(255, 255, 255));
        textField1.setMaximumSize(new Dimension(150, 30));
        textField1.setMinimumSize(new Dimension(150, 30));
        textField1.setFont(new Font("Serif", Font.BOLD, 16));
        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start = textField1.getText();
            }
        });

        EndPoint.setFont(new Font("Serif", Font.PLAIN, 16));
        textField2.setBackground(new Color(100, 100, 200));
        textField2.setForeground(new Color(255, 255, 255));
        textField2.setMaximumSize(new Dimension(150, 30));
        textField2.setMinimumSize(new Dimension(150, 30));
        textField2.setFont(new Font("Serif", Font.BOLD, 16));
        textField2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                end = textField2.getText();
            }
        });

        ChooseAlgo.setFont(new Font("Serif", Font.PLAIN, 16));
        comboBox1.addItem(new ComboItem("DFS", "1"));
        comboBox1.addItem(new ComboItem("BFS", "2"));
        comboBox1.setMaximumSize(new Dimension(150, 40));
        comboBox1.setMinimumSize(new Dimension(150, 40));
        comboBox1.setFont(new Font("Serif", Font.BOLD, 16));

        startButton.setBackground(new Color(20, 160, 210));
        startButton.setForeground(new Color(255, 255, 255));
        startButton.setMaximumSize(new Dimension(180, 40));
        startButton.setMinimumSize(new Dimension(180, 40));
        startButton.setFont(new Font("Serif", Font.BOLD, 16));
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startAlgo(e);
            }
        });

    }

    private void startAlgo(MouseEvent e) {
        choose = Objects.requireNonNull(comboBox1.getSelectedItem()).toString();
        start = textField1.getText();
        end = textField2.getText();
        clicked = true;
    }

    private void closeGraph() {
        frame.dispose();
        viewer.close();
        Clicks.loop = false;
    }

    private void loadFromFile() throws FileNotFoundException {
        int r = jFileChooser1.showOpenDialog(null);
        File file = null;
        if (r == JFileChooser.APPROVE_OPTION) {
            file = jFileChooser1.getSelectedFile();

            ArrayList<ArrayList<String>> graphText = new ReadFile(file).Graph();
            graph = new MultiGraph("Graph Visualization");
            visualGraph();
            setStylesheet();
            Clicks ct = new Clicks();
            ct.start();

            for (ArrayList<String> nodes: graphText) {
                maxNode = Math.max(maxNode, Integer.parseInt(nodes.get(0)));
                for (int i = 1; i < nodes.size(); i++) {
                    graph.addEdge(nodes.get(0) + nodes.get(i), nodes.get(0), nodes.get(i), true);
                    maxNode = Math.max(maxNode, Integer.parseInt(nodes.get(i)));
                }
            }

            for (Node _node: graph) {
                _node.addAttribute("ui.label", _node.getId());
            }
        }
    }

    private void screenshot(MouseEvent e) throws IOException {
        int r = jFileChooser2.showSaveDialog(null);
        if (r == JFileChooser.APPROVE_OPTION) {
            BufferedImage img = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
            frame.paint(img.getGraphics());
            File outputFile = new File(jFileChooser2.getSelectedFile().getAbsolutePath() + ".png");
            ImageIO.write(img, "png", outputFile);
        }
    }

    private void setStylesheet() {
        graph.setStrict(false);
        graph.setAutoCreate(true);

        /* --- style sheet --- */
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");

        /* --- style sheet node --- */
        graph.addAttribute("ui.stylesheet", "node { " +
                "shape: circle;" +
                "fill-color: yellow;" +
                "size: 35px;" +
                "text-alignment: center;" +
                "text-size: 17px;" +
                "size-mode: dyn-size;" +
                "fill-mode: dyn-plain;" +
                "text-mode: normal;" +
                "text-color: #2b1c1a;" +
                " }");

        /* --- style sheet edge --- */
        graph.addAttribute("ui.stylesheet", "edge { fill-color: black; } ");

        /* --- style sheet algo ---
        * node.algo1: start - end node
        * node.algo2: path node
        * node.algo3: not path node
        * edge.algo1: path edge
        * edge.algo2: not path edge
        * */
        graph.addAttribute("ui.stylesheet", "node.algo1 { " +
                "shape: circle;" +
                "fill-color: #FE4D00;" +
                "size: 35px;" +
                "text-alignment: center;" +
                "text-size: 17px;" +
                "size-mode: dyn-size;" +
                "fill-mode: dyn-plain;" +
                "text-mode: normal;" +
                "text-color: #2b1c1a;" +
                " }");
        graph.addAttribute("ui.stylesheet", "node.algo2 { " +
                "shape: circle;" +
                "fill-color: #FA9200;" +
                "size: 35px;" +
                "text-alignment: center;" +
                "text-size: 17px;" +
                "size-mode: dyn-size;" +
                "fill-mode: dyn-plain;" +
                "text-mode: normal;" +
                "text-color: #2b1c1a;" +
                " }");
        graph.addAttribute("ui.stylesheet", "node.algo3 { " +
                "shape: circle;" +
                "fill-color: #696969;" +
                "size: 8px;" +
//                "text-alignment: center;" +
                "text-size: 0px;" +
                "size-mode: dyn-size;" +
                "fill-mode: dyn-plain;" +
                "text-mode: normal;" +
                "text-color: #ffffff;" +
                " }");

        graph.addAttribute("ui.stylesheet", "edge.algo1 { " +
                "fill-color: #ECC181;" +
                "size: 8px;" +
                " }");
        graph.addAttribute("ui.stylesheet", "edge.algo2 { " +
                "fill-color: #D3D3D3;" +
                " }");
    }

    private void visualGraph() {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout()) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(1280, 720);
            }
        };

        viewer  = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        ViewPanel viewPanel = viewer.addDefaultView(false);
        panel.add(viewPanel);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        View view = viewer.getDefaultView();
        view.getCamera().setAutoFitView(true);
        viewer.enableAutoLayout();
    }

    public static void initialize() {
        try {
            for (UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (UnsupportedLookAndFeelException e) {
            Logger.getLogger(Platform.class.getName()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException e) {
            Logger.getLogger(Platform.class.getName()).log(Level.SEVERE, null, e);
        } catch (InstantiationException e) {
            Logger.getLogger(Platform.class.getName()).log(Level.SEVERE, null, e);
        } catch (IllegalAccessException e) {
            Logger.getLogger(Platform.class.getName()).log(Level.SEVERE, null, e);
        }

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Platform().setVisible(true);
            }
        });
    }
}