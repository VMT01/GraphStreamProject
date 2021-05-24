package GUI;

import Algo.BFS;
import Algo.DFS;
import Algo.Line;
import Algo.Path;
import DataStructure.Edge_Imp;
import DataStructure.Node_Imp;
import GraphStream.MainGraph;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.graphstream.graph.Edge;
import org.graphstream.ui.fx_viewer.FxViewPanel;
import org.graphstream.ui.javafx.FxGraphRenderer;
import org.graphstream.ui.view.View;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static GraphStream.MainGraph.*;

public class Controller implements Initializable {
    public static String mode;

    MainGraph mainGraph;

    int lineFirstNode = 0;
    int lineLastNode = 0 ;
    int DFSFirstNode = 0 ;
    int BFSFirstNode = 0;
    File openFile ;
    File saveFile ;

    Button yesButton = new Button("Yes");
    Button noButton = new Button("No");
    Stage eventStage = new Stage();
    Label eventLabel = new Label();

    boolean drawGraph = false;
    boolean modified = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mode = "none";

        inputLabel.setFont(Font.font(14));
        lineFirstNodeTF.setFont(Font.font(14));
        lineFirstNodeTF.setPrefWidth(50);

        lineLastNodeTF.setFont(Font.font(14));
        lineLastNodeTF.setPrefWidth(50);

        firstLabel.setFont(Font.font(14));
        lastLabel.setFont(Font.font(14));
        setNodeButton.setFont(Font.font(14));
        resetNodeButton.setFont(Font.font(14));
        inputNodeHBox.getChildren().addAll(firstLabel, lineFirstNodeTF, lastLabel, lineLastNodeTF, setNodeButton, resetNodeButton);

        firstLabel.setVisible(false);
        lastLabel.setVisible(false);
        lineFirstNodeTF.setVisible(false);
        lineLastNodeTF.setVisible(false);
        setNodeButton.setVisible(false);
        resetNodeButton.setVisible(false);

        openFile =  new File(".temp.txt");

        lockGraphTooltip.setText("Lock Graph");
        lockGraphTooltip.setShowDelay(Duration.millis(10));
        lockGraphTooltip.setShowDuration(Duration.seconds(1));
        lockGraphButton.setSelected(true);
        lockGraphButton.setTooltip(lockGraphTooltip);

        addNodeTooltip.setText("Add New Node");
        addNodeTooltip.setShowDelay(Duration.millis(10));
        addNodeTooltip.setShowDuration(Duration.seconds(1));
        addNodeButton.setTooltip(addNodeTooltip);

        deleteNodeTooltip.setText("Delete Node");
        deleteNodeTooltip.setShowDelay(Duration.millis(10));
        deleteNodeTooltip.setShowDuration(Duration.seconds(1));
        deleteNodeButton.setTooltip(deleteNodeTooltip);

        addEdgeTooltip.setText("Add New Edge");
        addEdgeTooltip.setShowDelay(Duration.millis(10));
        addEdgeTooltip.setShowDuration(Duration.seconds(1));
        addEdgeButton.setTooltip(addEdgeTooltip);

        deleteEdgeTooltip.setText("Delete Edge");
        deleteEdgeTooltip.setShowDelay(Duration.millis(10));
        deleteEdgeTooltip.setShowDuration(Duration.seconds(1));
        deleteEdgeButton.setTooltip(deleteEdgeTooltip);

        findNodeTooltip.setText("Find a Node");
        findNodeTooltip.setShowDelay(Duration.millis(10));
        findNodeTooltip.setShowDuration(Duration.seconds(1));
        findNodeButton.setTooltip(findNodeTooltip);
    }

    @FXML BorderPane leftRoot = new BorderPane();
    @FXML BorderPane mainPane = new BorderPane();
    @FXML BorderPane topLeftRootPane = new BorderPane();
    @FXML MenuBar menuBar = new MenuBar();
    @FXML Menu file = new Menu();
    @FXML Menu edit = new Menu();
    @FXML Menu graphAnimation = new Menu();
    @FXML Menu help = new Menu();
    @FXML MenuItem newGraphMenuItem = new MenuItem();
    @FXML MenuItem openGraphMenuItem = new MenuItem();
    @FXML MenuItem saveAsGraphMenuItem = new MenuItem();
    @FXML MenuItem saveGraphMenuItem = new MenuItem();
    @FXML MenuItem exitProgramMenuItem = new MenuItem();
    @FXML MenuItem exitGraphMenuItem = new MenuItem();
    @FXML MenuItem deleteGraphMenuItem = new MenuItem();
    @FXML MenuItem resetGraphMenuItem = new MenuItem();
    @FXML MenuItem aboutGraphMenuItem = new MenuItem();
    @FXML Label inputLabel = new Label();
    @FXML HBox inputNodeHBox = new HBox();
    @FXML Button drawGraphButton = new Button();
    @FXML Button repairGraphButton = new Button();
    @FXML Button showDFSButton = new Button();
    @FXML Button showBFSButton = new Button();
    @FXML Button showLineButton = new Button();
    @FXML Button screenShotButton = new Button();
    @FXML TextArea graphNodeTextArea = new TextArea();
    @FXML ListView<Path> showLineListView = new ListView<>();
    @FXML BorderPane displayGraph = new BorderPane();
    @FXML ToggleButton lockGraphButton = new ToggleButton();
    @FXML Button addNodeButton = new Button();
    @FXML Button deleteNodeButton = new Button();
    @FXML Button addEdgeButton = new Button();
    @FXML Button deleteEdgeButton = new Button();
    @FXML Button findNodeButton = new Button();

    Tooltip lockGraphTooltip = new Tooltip();
    Tooltip addNodeTooltip = new Tooltip();
    Tooltip deleteNodeTooltip = new Tooltip();
    Tooltip addEdgeTooltip = new Tooltip();
    Tooltip deleteEdgeTooltip = new Tooltip();
    Tooltip findNodeTooltip = new Tooltip();

    TextField lineFirstNodeTF = new TextField();
    TextField lineLastNodeTF  = new TextField();
    Label firstLabel = new Label();
    Label lastLabel = new Label();
    Button setNodeButton = new Button("Set");
    Button resetNodeButton = new Button("Reset");

    /* Menu Item event */
    @FXML private void addPane() {
        eventDialog("New");
        eventLabel.setText("Are you want to take a new window?");

        noButton.setOnAction(event -> {
            firstLabel.setVisible(false);
            lastLabel.setVisible(false);
            lineFirstNodeTF.setVisible(false);
            lineLastNodeTF.setVisible(false);
            setNodeButton.setVisible(false);
            resetNodeButton.setVisible(false);

            graphNodeTextArea.setEditable(true);
            graphNodeTextArea.setText("");
            eventStage.close();
        });

        yesButton.setOnAction(event -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("GUI.fxml")));
            } catch (IOException ignore) { }
            assert root != null;
            Scene scene = new Scene(root);

            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Object Oriented Programming Project - Team 1B");
            primaryStage.show();
            eventStage.close();
        });
    }

    @FXML private void openPane() throws IOException {
        Stage stage = (Stage) mainPane.getScene().getWindow();
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose a input graph file");
        FileChooser.ExtensionFilter inputFilter = new FileChooser.ExtensionFilter("input(*.txt)","*.txt");
        fc.getExtensionFilters().add(inputFilter);
        openFile = fc.showOpenDialog(stage);
        if (openFile != null) {
            readOpenFile(openFile);
        }
        graphNodeTextArea.setEditable(false);
    }

    @FXML private void deletePane() {
        eventDialog("Delete");

        yesButton.setOnAction(event ->{
            if (openFile != null) {
                if (!openFile.equals(new File(".temp.txt"))) {
                    openFile.delete();
                }
            }
            eventStage.close();
            showLineListView.getItems().clear();
            DFSFirstNode = 0;
            BFSFirstNode = 0;
            lineFirstNode = 0;
            lineLastNode = 0;

            inputLabel.setText("        Graph has been deleted!");
            firstLabel.setVisible(false);
            lastLabel.setVisible(false);
            lineFirstNodeTF.setVisible(false);
            lineLastNodeTF.setVisible(false);
            setNodeButton.setVisible(false);
            resetNodeButton.setVisible(false);
        });
        noButton.setOnAction(event -> eventStage.close());
    }

    @FXML private void saveAs() {
        Stage stage = (Stage) mainPane.getScene().getWindow();
        FileChooser fc = new FileChooser();
        fc.setTitle("Save Dialog");
        fc.setInitialFileName("newInput");
        FileChooser.ExtensionFilter inputFilter = new FileChooser.ExtensionFilter("input(*.txt)","*.txt");
        fc.getExtensionFilters().add(inputFilter);
        try {
            saveFile  = fc.showSaveDialog(stage);
            fc.setInitialDirectory(saveFile.getParentFile());
            String s = graphNodeTextArea.getText();
            FileWriter out = new FileWriter(saveFile);
            out.write(s);
            out.close();
        } catch (Exception ignored) { }
    }

    @FXML private void save() throws IOException {
        String s = graphNodeTextArea.getText();
        FileWriter out = new FileWriter(openFile);
        out.write(s);
        out.close();
    }

    @FXML private void exitProgram() {
        eventDialog("Exit Program");
        eventLabel.setText("Are you want to exit this program ?");
        yesButton.setOnAction(event -> {
            eventStage.close();
            System.exit(0);
        });
        noButton.setOnAction(event -> eventStage.close());
    }

    @FXML private void resetPane() {
        eventDialog("Reset");
        yesButton.setOnAction(event -> {
            showLineListView.getItems().clear();
            DFSFirstNode = 0;
            BFSFirstNode = 0;
            lineFirstNode = 0;
            lineLastNode = 0;

            inputLabel.setText("        Graph has been reset!");
            firstLabel.setVisible(false);
            lastLabel.setVisible(false);
            lineFirstNodeTF.setVisible(false);
            lineLastNodeTF.setVisible(false);
            setNodeButton.setVisible(false);
            resetNodeButton.setVisible(false);

            eventStage.close();
        });
        noButton.setOnAction(event -> eventStage.close());
    }

    @FXML private void exitPane() {
        eventDialog("Exit");
        yesButton.setOnAction(event -> {
            graphNodeTextArea.setText("");
            showLineListView.getItems().clear();
            DFSFirstNode = 0;
            BFSFirstNode = 0;
            lineFirstNode = 0;
            lineLastNode = 0;

            inputLabel.setText("        Graph has been exited!");
            firstLabel.setVisible(true);
            firstLabel.setText(" Open new Graph : ");
            lastLabel.setVisible(false);
            lastLabel.setText("");
            lineFirstNodeTF.setVisible(false);
            lineLastNodeTF.setVisible(false);
            setNodeButton.setVisible(true);
            setNodeButton.setText("Open");
            setNodeButton.setOnAction(ev -> {
                try {
                    openPane();
                } catch (IOException ignore) { }
                setNodeButton.setText("Set");
                inputLabel.setVisible(false);
                firstLabel.setVisible(false);
                setNodeButton.setVisible(false);
            });
            resetNodeButton.setVisible(false);
            eventStage.close();
        });

        noButton.setOnAction(event -> eventStage.close());
    }

    @FXML private void aboutPane() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("aboutPane.fxml")));
        Scene scene = new Scene(root);
        Stage aboutStage = new Stage();
        aboutStage.setScene(scene);
        aboutStage.setTitle("About our Program ");
        aboutStage.show();
    }

    @FXML private void dfsPane() {
        if (!drawGraph) {
            warningDialog("You must Draw Graph first!");
            return;
        }

        if (modified) {
            warningDialog("You must Re-draw Graph first!");
            return;
        }

        graph.edges().forEach(e -> e.setAttribute("ui.class", "default"));
        graph.nodes().forEach(e -> e.setAttribute("ui.class", "default"));
        showLineListView.getItems().clear();

        inputLabel.setText("   Input the first node in Depth First Search algorithm here :");
        inputLabel.setVisible(true);
        firstLabel.setText("The first node");
        firstLabel.setVisible(true);
        lineFirstNodeTF.setText(String.valueOf(DFSFirstNode));
        lineFirstNodeTF.setEditable(true);
        lineFirstNodeTF.setVisible(true);
        setNodeButton.setVisible(true);
        resetNodeButton.setVisible(true);
        lastLabel.setText("");
        lastLabel.setVisible(false);
        lineLastNodeTF.setVisible(false);

        setNodeButton.setOnAction(event -> {
            DFSFirstNode = Integer.parseInt(lineFirstNodeTF.getText());
            lineFirstNodeTF.setEditable(false);
            DFS dfs = new DFS(DFSFirstNode - 1);
            Map<Integer, ArrayList<Integer>> ctPath = dfs.getPath();
            graph.nodes().forEach(e -> e.setAttribute("ui.class", "algo3"));
            graph.edges().forEach(e -> e.setAttribute("ui.class", "algo2"));
            renderPathDFS(DFSFirstNode - 1, ctPath);
        });
        resetNodeButton.setOnAction(event -> {
            graph.edges().forEach(e -> e.setAttribute("ui.class", "default"));
            graph.nodes().forEach(e -> e.setAttribute("ui.class", "default"));
            lineFirstNodeTF.setEditable(true);
        });
    }

    @FXML private void bfsPane() {
        if (!drawGraph) {
            warningDialog("You must Draw Graph first!");
            return;
        }

        if (modified) {
            warningDialog("You must Re-draw Graph first!");
            return;
        }

        graph.edges().forEach(e -> e.setAttribute("ui.class", "default"));
        graph.nodes().forEach(e -> e.setAttribute("ui.class", "default"));
        showLineListView.getItems().clear();

        inputLabel.setText("   Input the first node in Breadth First Search algorithm here :");
        inputLabel.setVisible(true);
        firstLabel.setText("The first node");
        firstLabel.setVisible(true);
        lineFirstNodeTF.setText(String.valueOf(BFSFirstNode));
        lineFirstNodeTF.setEditable(true);
        lineFirstNodeTF.setVisible(true);
        setNodeButton.setVisible(true);
        resetNodeButton.setVisible(true);
        lastLabel.setText("");
        lastLabel.setVisible(false);
        lineLastNodeTF.setVisible(false);
        setNodeButton.setOnAction(event -> {
            BFSFirstNode = Integer.parseInt(lineFirstNodeTF.getText());
            lineFirstNodeTF.setEditable(false);
            BFS bfs = new BFS(BFSFirstNode - 1);
            Map<Integer, ArrayList<Integer>> ctPath = bfs.getPath();
            graph.nodes().forEach(e -> e.setAttribute("ui.class", "algo3"));
            graph.edges().forEach(e -> e.setAttribute("ui.class", "algo2"));
            renderPathBFS(ctPath);
        });
        resetNodeButton.setOnAction(event -> {
            graph.edges().forEach(e -> e.setAttribute("ui.class", "default"));
            graph.nodes().forEach(e -> e.setAttribute("ui.class", "default"));
            lineFirstNodeTF.setEditable(true);
        });
    }

    @FXML private void showLinePane(){
        if (!drawGraph) {
            warningDialog("You must Draw Graph first!");
            return;
        }

        if (modified) {
            warningDialog("You must Re-draw Graph first!");
            return;
        }

        graph.edges().forEach(e -> e.setAttribute("ui.class", "default"));
        graph.nodes().forEach(e -> e.setAttribute("ui.class", "default"));

        inputLabel.setText("   Input the first node and last node in the path here :");
        inputLabel.setVisible(true);
        firstLabel.setText("First");
        firstLabel.setVisible(true);
        lineFirstNodeTF.setText(String.valueOf(lineFirstNode));
        lineFirstNodeTF.setEditable(true);
        lineFirstNodeTF.setVisible(true);
        lastLabel.setText("  Last");
        lastLabel.setVisible(true);
        lineLastNodeTF.setText(String.valueOf(lineLastNode));
        lineLastNodeTF.setEditable(true);
        lineLastNodeTF.setVisible(true);

        setNodeButton.setVisible(true);
        resetNodeButton.setVisible(true);
        setNodeButton.setOnAction(event -> {
            lineFirstNode = Integer.parseInt(lineFirstNodeTF.getText());
            lineLastNode = Integer.parseInt(lineLastNodeTF.getText());
            Line line = new Line(lineFirstNode - 1, lineLastNode - 1);
            showLineListView.getItems().clear();
            showLineListView.getItems().addAll(line.getPath());
            showLineListView.getSelectionModel().selectedItemProperty().addListener((observableValue, path, t1) -> renderPathLine(t1));
            lineFirstNodeTF.setEditable(false);
            lineLastNodeTF.setEditable(false);


        });
        resetNodeButton.setOnAction(event -> {
            lineFirstNodeTF.setEditable(true);
            lineLastNodeTF.setEditable(true);
            graph.edges().forEach(e -> e.setAttribute("ui.class", "default"));
            graph.nodes().forEach(e -> e.setAttribute("ui.class", "default"));
            showLineListView.getItems().clear();
        });
    }

    @FXML public void drawGraph() {
        if (graphNodeTextArea.getText().equals("")) {
            warningDialog("You must enter a Graph first!");
            return;
        }

        drawGraph = true;
        modified = false;
        showLineListView.getItems().clear();
        lockGraphButton.setSelected(true);
        graphNodeTextArea.setEditable(false);

        mainGraph = new MainGraph(graphNodeTextArea.getText());
        displayGraph.setCenter((FxViewPanel) fxViewer.addDefaultView(false, new FxGraphRenderer()));

        View view = fxViewer.getDefaultView();
        view.getCamera().setAutoFitView(true);
        displayGraph.setOnScroll(e -> {
            if (e.getDeltaY() > 0) {
                view.getCamera().setViewPercent(view.getCamera().getViewPercent() - 0.1);
            } else if (e.getDeltaY() < 0) {
                view.getCamera().setViewPercent(view.getCamera().getViewPercent() + 0.1);
            }
        });
    }

    @FXML public void repairGraph() {
        graphNodeTextArea.setEditable(true);
    }

    @FXML public void screenShot() {
        if (!drawGraph) {
            warningDialog("You must Draw Graph first!");
            return;
        }

        Image image = displayGraph.getCenter().snapshot(new SnapshotParameters(), null);
        ImageView displayImage = new ImageView();
        displayImage.setImage(image);

        HBox bottomScreenshot = new HBox();
        bottomScreenshot.setAlignment(Pos.CENTER_RIGHT);
        bottomScreenshot.setSpacing(50);
        bottomScreenshot.setPadding(new Insets(0, 40, 10, 0));

        VBox centerScreenshot = new VBox();
        centerScreenshot.setSpacing(10);
        centerScreenshot.setPadding(new Insets(10, 10, 10, 10));

        Button saveImageButton = new Button();
        saveImageButton.setText("Save Image");
        saveImageButton.setFont(Font.font(14));

        Button okImageButton = new Button();
        okImageButton.setText("Cancel");
        okImageButton.setFont(Font.font(14));

        bottomScreenshot.getChildren().addAll(okImageButton, saveImageButton);
        centerScreenshot.getChildren().addAll(displayImage, bottomScreenshot);

        BorderPane displayPane = new BorderPane();
        displayPane.setCenter(centerScreenshot);

        Scene scene = new Scene(displayPane, 810, 530);
        eventStage.setScene(scene);
        eventStage.setTitle("Screen Shot Graph");
        eventStage.show();

        okImageButton.setOnAction(e -> eventStage.close());
        saveImageButton.setOnAction(e -> {
            File outputFile = new File("newScrShot.png");
            BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
            try {
                ImageIO.write(bImage, "png", outputFile);
            } catch (IOException ignore) { }
            eventStage.close();
        });
    }

    public void readOpenFile(File openFile) throws FileNotFoundException {
        StringBuilder s = new StringBuilder();
        if (openFile != null) {
            Scanner sc = new Scanner(openFile.getAbsoluteFile());
            while (sc.hasNextLine()) {
                s.append(sc.nextLine()).append("\n");
            }
        }
        graphNodeTextArea.setText(s.toString().trim());
    }

    public void eventDialog (String s) {
        eventLabel.setText("Are you want to " + s.toLowerCase() + " this opening graph ?");
        eventLabel.setFont(Font.font(14));
        yesButton.setFont(Font.font(14));
        noButton.setFont(Font.font(14));
        HBox hBox = new HBox(yesButton, noButton);
        VBox vBox = new VBox(eventLabel, hBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(40);

        Scene scene = new Scene(vBox,280,130);
        eventStage.setScene(scene);
        eventStage.setTitle( s + " Graph ");
        eventStage.show();
    }

    @FXML public void lockGraph() {
        if (!drawGraph) {
            lockGraphButton.setSelected(true);
            warningDialog("You must Draw Graph first!");
            return;
        }

        Image lock = new Image("ImagePackage/lockIconButton.png");
        ImageView lockImageView = new ImageView();
        lockImageView.setFitHeight(30);
        lockImageView.setFitWidth(30);
        lockImageView.setImage(lock);

        Image unlock = new Image("ImagePackage/unlockIcon.jpg");
        ImageView unlockImageView = new ImageView();
        unlockImageView.setFitHeight(30);
        unlockImageView.setFitWidth(30);
        unlockImageView.setImage(unlock);

        if (lockGraphButton.isSelected()) {
            fxViewer.enableAutoLayout();
            lockGraphTooltip.setText("Lock Graph");
            lockGraphButton.setGraphic(unlockImageView);
        } else {
            fxViewer.disableAutoLayout();
            lockGraphTooltip.setText("Unlock Graph");
            lockGraphButton.setGraphic(lockImageView);
        }
    }

    @FXML public void addNode() {
        graph.nodes().forEach(e -> e.setAttribute("ui.class", "default"));
        graph.edges().forEach(e -> e.setAttribute("ui.class", "default"));
        showLineListView.getItems().clear();
        inputLabel.setVisible(false);
        firstLabel.setVisible(false);
        lastLabel.setVisible(false);
        lineFirstNodeTF.setVisible(false);
        lineLastNodeTF.setVisible(false);
        setNodeButton.setVisible(false);
        resetNodeButton.setVisible(false);

        if (!drawGraph) {
            warningDialog("You must Draw Graph first!");
            return;
        }

        String x = Integer.toString(++lastId + 1);
        graph.addNode(x);
        graph.getNode(x).setAttribute("ui.label", x);

        Node_Imp _node = new Node_Imp(true);
        _node.setID(lastId);
        implementedGraph.addNode(_node);

        updateTextField();
        modified = true;
    }

    @FXML public void deleteNode() {
        graph.nodes().forEach(e -> e.setAttribute("ui.class", "default"));
        graph.edges().forEach(e -> e.setAttribute("ui.class", "default"));
        showLineListView.getItems().clear();
        inputLabel.setVisible(false);
        firstLabel.setVisible(false);
        lastLabel.setVisible(false);
        lineFirstNodeTF.setVisible(false);
        lineLastNodeTF.setVisible(false);
        setNodeButton.setVisible(false);
        resetNodeButton.setVisible(false);

        if (!drawGraph) {
            warningDialog("You must Draw Graph first!");
            return;
        }

        BorderPane deleteNodePane = new BorderPane();

        Label deleteLabel = new Label("Input the node you want to delete here : ");
        deleteLabel.setFont(Font.font(14));

        TextField deleteTextField = new TextField();
        deleteTextField.setPrefWidth(50);

        HBox deleteHBox = new HBox(deleteLabel, deleteTextField);
        deleteHBox.setSpacing(20);
        deleteHBox.setAlignment(Pos.CENTER);
        deleteHBox.setPadding(new Insets(5,5,5,5));

        Button deleteButton = new Button("Delete");
        deleteButton.setFont(Font.font(14));

        VBox findVBox = new VBox(deleteHBox, deleteButton );
        findVBox.setSpacing(10);
        findVBox.setAlignment(Pos.CENTER);
        deleteNodePane.setCenter(findVBox);

        Scene scene = new Scene(deleteNodePane,350,150);
        eventStage.setScene(scene);
        eventStage.setTitle("Delete Node");
        eventStage.show();

        deleteButton.setOnAction(e -> {
            if (!deleteTextField.getText().equals("")) {
                eventStage.close();

                String deleteNode = deleteTextField.getText();
                if (graph.getNode(deleteNode) == null) {
                    warningDialog("Cannot find node " + deleteNode);
                } else {
                    for (int i = 0; i < graph.getNode(deleteNode).getOutDegree(); i++) {
                        Edge edge = graph.getNode(deleteNode).getLeavingEdge(i);
                        int srcIndex = Integer.parseInt(deleteNode) - 1;

                        for (int j = 0; j < implementedGraph.getNode(srcIndex).getNoChildren(); j++) {
                            int idd = implementedGraph.getNode(srcIndex).getChildrenByIndex(j).getChild().getID();
                            if (idd == Integer.parseInt(edge.getNode1().getId()) - 1) {
                                Edge_Imp removeEdge = implementedGraph.getNode(srcIndex).getChildrenByIndex(j);
                                implementedGraph.getNode(srcIndex).removeChild(removeEdge);
                                break;
                            }
                        }
                    }
                    graph.removeNode(deleteNode);
                    modified = true;

                    Node_Imp _node = new Node_Imp(false);
                    _node.setID(Integer.parseInt(deleteNode) - 1);
                    Node_Imp remove_node = implementedGraph.getNode(implementedGraph.getNodeIndex(_node));
                    implementedGraph.removeNode(remove_node);

                    updateTextField();
                }
            }
        });
    }

    @FXML public void addEdge() {
        graph.nodes().forEach(e -> e.setAttribute("ui.class", "default"));
        graph.edges().forEach(e -> e.setAttribute("ui.class", "default"));
        showLineListView.getItems().clear();
        inputLabel.setVisible(false);
        firstLabel.setVisible(false);
        lastLabel.setVisible(false);
        lineFirstNodeTF.setVisible(false);
        lineLastNodeTF.setVisible(false);
        setNodeButton.setVisible(false);
        resetNodeButton.setVisible(false);

        if (!drawGraph) {
            warningDialog("You must Draw Graph first!");
            return;
        }

        BorderPane addEdgePane = new BorderPane();
        Label addEdgePaneLabel = new Label("Input the edge you want to add here :");
        addEdgePaneLabel.setFont(Font.font(14));

        Label addHeadLabel = new Label("Head : ");
        addHeadLabel.setFont(Font.font(14));
        TextField headTextField = new TextField();
        headTextField.setPrefWidth(50);
        Label addTailLabel = new Label("Tail : ");
        addTailLabel.setFont(Font.font(14));
        TextField tailTextField = new TextField();
        tailTextField.setPrefWidth(50);

        HBox addEdgeHBox = new HBox(addHeadLabel, headTextField, addTailLabel, tailTextField);
        addEdgeHBox.setSpacing(20);
        addEdgeHBox.setAlignment(Pos.CENTER);
        addEdgeHBox.setPadding(new Insets(5,5,5,5));

        Button addEdgePaneButton = new Button("Add");
        addEdgePaneButton.setFont(Font.font(14));

        VBox addEdgeVBox = new VBox(addEdgePaneLabel, addEdgeHBox, addEdgePaneButton );
        addEdgeVBox.setSpacing(30);
        addEdgeVBox.setAlignment(Pos.CENTER);
        addEdgePane.setCenter(addEdgeVBox);

        Scene scene = new Scene(addEdgePane,400,180);
        eventStage.setScene(scene);
        eventStage.setTitle("Add New Edge");
        eventStage.show();

        addEdgePaneButton.setOnAction(e -> {
            if (!headTextField.getText().equals("") && !tailTextField.getText().equals("")) {
                eventStage.close();
                String head = headTextField.getText();
                String tail = tailTextField.getText();
                if (graph.getNode(head) == null) {
                    warningDialog("Cannot find node " + head);
                } else if (graph.getNode(tail) == null) {
                    warningDialog("Cannot find node " + tail);
                } else if (!graph.getNode(head).hasEdgeToward(tail)) {
                    modified = true;
                    graph.addEdge(Integer.toString(++cntEdge), head, tail, true);
                    implementedGraph.getNode(Integer.parseInt(head) - 1).addChild(new Edge_Imp(implementedGraph.getNode(Integer.parseInt(tail) - 1)));
                    updateTextField();
                }
            }
        });
    }

    @FXML public void deleteEdge() {
        graph.nodes().forEach(e -> e.setAttribute("ui.class", "default"));
        graph.edges().forEach(e -> e.setAttribute("ui.class", "default"));
        showLineListView.getItems().clear();
        inputLabel.setVisible(false);
        firstLabel.setVisible(false);
        lastLabel.setVisible(false);
        lineFirstNodeTF.setVisible(false);
        lineLastNodeTF.setVisible(false);
        setNodeButton.setVisible(false);
        resetNodeButton.setVisible(false);

        if (!drawGraph) {
            warningDialog("You must Draw Graph first!");
            return;
        }

        BorderPane deleteEdgePane = new BorderPane();
        Label deleteEdgePaneLabel = new Label("Input the edge you want to delete here :");
        deleteEdgePaneLabel.setFont(Font.font(14));

        Label deleteHeadLabel = new Label("Head : ");
        deleteHeadLabel.setFont(Font.font(14));
        TextField deleteHeadTextField = new TextField();
        deleteHeadTextField.setPrefWidth(50);
        Label deleteTailLabel = new Label("Tail : ");
        deleteTailLabel.setFont(Font.font(14));
        TextField deleteTailTextField = new TextField();
        deleteTailTextField.setPrefWidth(50);

        HBox deleteEdgeHBox = new HBox(deleteHeadLabel, deleteHeadTextField, deleteTailLabel, deleteTailTextField);
        deleteEdgeHBox.setSpacing(20);
        deleteEdgeHBox.setAlignment(Pos.CENTER);
        deleteEdgeHBox.setPadding(new Insets(5,5,5,5));

        Button deleteEdgePaneButton = new Button("Delete");
        deleteEdgePaneButton.setFont(Font.font(14));

        VBox deleteEdgeVBox = new VBox(deleteEdgePaneLabel, deleteEdgeHBox, deleteEdgePaneButton );
        deleteEdgeVBox.setSpacing(30);
        deleteEdgeVBox.setAlignment(Pos.CENTER);
        deleteEdgePane.setCenter(deleteEdgeVBox);

        Scene scene = new Scene(deleteEdgePane,400,180);
        eventStage.setScene(scene);
        eventStage.setTitle("Delete Edge");
        eventStage.show();

        deleteEdgePaneButton.setOnAction(e -> {
            if (!deleteHeadTextField.getText().equals("") && !deleteTailTextField.getText().equals("")) {
                eventStage.close();
                String head = deleteHeadTextField.getText();
                String tail = deleteTailTextField.getText();
                if (graph.getNode(head) == null) {
                    warningDialog("Cannot find node " + head);
                } else if (graph.getNode(tail) == null) {
                    warningDialog("Cannot find node " + tail);
                } else if (graph.getNode(head).hasEdgeToward(tail)) {
                    modified = true;
                    graph.removeEdge(graph.getNode(head).getEdgeToward(tail));
                    implementedGraph.getNode(Integer.parseInt(head) - 1).removeChild(implementedGraph.getNode(Integer.parseInt(head) - 1).getChildrenByID(Integer.parseInt(tail) - 1));
                    updateTextField();
                }
            }
        });
    }

    @FXML public void findNode() {
        graph.nodes().forEach(e -> e.setAttribute("ui.class", "default"));
        graph.edges().forEach(e -> e.setAttribute("ui.class", "default"));
        showLineListView.getItems().clear();
        inputLabel.setVisible(false);
        firstLabel.setVisible(false);
        lastLabel.setVisible(false);
        lineFirstNodeTF.setVisible(false);
        lineLastNodeTF.setVisible(false);
        setNodeButton.setVisible(false);
        resetNodeButton.setVisible(false);

        if (!drawGraph) {
            warningDialog("You must Draw Graph first!");
            return;
        }

        if (modified) {
            warningDialog("You must Re-draw Graph first!");
            return;
        }

        BorderPane findPane = new BorderPane();
        Label findLabel = new Label("Input the node you want to find here : ");
        findLabel.setFont(Font.font(14));

        TextField findTextField = new TextField();
        findTextField.setPrefWidth(50);

        HBox findHBox = new HBox(findLabel, findTextField);
        findHBox.setSpacing(20);
        findHBox.setAlignment(Pos.CENTER);
        findHBox.setPadding(new Insets(5,5,5,5));

        Button findButton = new Button("Find");
        findButton.setFont(Font.font(14));

        VBox findVBox = new VBox(findHBox, findButton );
        findVBox.setSpacing(10);
        findVBox.setAlignment(Pos.CENTER);
        findPane.setCenter(findVBox);

        Scene scene = new Scene(findPane,350,150);
        eventStage.setScene(scene);
        eventStage.setTitle("Find Node");
        eventStage.show();

        findButton.setOnAction(e -> {
            String input = findTextField.getText();
            if (input != null && graph.getNode(input) != null) {
                graph.getNode(input).setAttribute("ui.class", "algo2");
                eventStage.close();
            } else {
                eventStage.close();
                warningDialog("Cannot find node " + input);
            }
        });
    }

    private void updateTextField() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < implementedGraph.getNoVertices() - 1; i++) {
            Node_Imp _node = implementedGraph.getNode(i);
            stringBuilder.append(_node.getID() + 1);
            ArrayList<Integer> nodes = new ArrayList<>();
            for (Edge_Imp _edge: _node.getChildren()) {
                nodes.add(_edge.getChild().getID());
            }
            Collections.sort(nodes);
            for (Integer x: nodes) {
                stringBuilder.append(" ").append(x + 1);
            }
            stringBuilder.append("\n");
        }
        Node_Imp _node = implementedGraph.getNode(implementedGraph.getNoVertices() - 1);
        stringBuilder.append(_node.getID() + 1);
        ArrayList<Integer> nodes = new ArrayList<>();
        for (Edge_Imp _edge: _node.getChildren()) {
            nodes.add(_edge.getChild().getID());
        }
        Collections.sort(nodes);
        for (Integer x: nodes) {
            stringBuilder.append(" ").append(x + 1);
        }
        graphNodeTextArea.setText(stringBuilder.toString());
    }

    private void warningDialog (String s) {
        Label warningLabel = new Label("WARNING : " + s + "!");
        warningLabel.setFont(Font.font(14));
        ImageView warningImageView = new ImageView();
        warningImageView.setImage(new Image("ImagePackage/warningButton.jpg"));
        warningImageView.setFitWidth(30);
        warningImageView.setFitHeight(30);

        HBox warningHBox = new HBox(warningImageView, warningLabel);
        warningHBox.setSpacing(10);
        warningHBox.setAlignment(Pos.CENTER);

        Button warningButton = new Button("OK");
        warningButton.setFont(Font.font(14));

        VBox warningVBox = new VBox(warningHBox, warningButton);
        warningVBox.setAlignment(Pos.CENTER);
        warningVBox.setSpacing(20);


        Scene scene = new Scene(warningVBox,450,150);
        Stage warningStage = new Stage();
        warningStage.setScene(scene);
        warningStage.setTitle("WARNING DIALOG");
        warningStage.show();
        warningButton.setOnAction( eventAction -> warningStage.close());
    }

    private void renderPathDFS(int startNode, Map<Integer, ArrayList<Integer>> ctPath) {
        graph.getNode(Integer.toString(startNode + 1)).setAttribute("ui.class", "algo2");

        if (ctPath.get(startNode) == null) {
            return;
        }

        for (Integer value: ctPath.get(startNode)) {
            graph.getNode(Integer.toString(startNode + 1)).getEdgeBetween(Integer.toString(value + 1)).setAttribute("ui.class", "algo1");
            renderPathDFS(value, ctPath);
        }
    }

    private void renderPathBFS(Map<Integer, ArrayList<Integer>> ctPath) {
        for (Integer key: ctPath.keySet()) {
            graph.getNode(Integer.toString(key + 1)).setAttribute("ui.class", "algo2");
            for (Integer value: ctPath.get(key)) {
                graph.getNode(Integer.toString(key + 1)).getEdgeBetween(Integer.toString(value + 1)).setAttribute("ui.class", "algo1");
                graph.getNode(Integer.toString(value + 1)).setAttribute("ui.class", "algo2");
            }
        }
    }

    private void renderPathLine(Path path) {
        if (path == null) {
            graph.nodes().forEach(e -> e.setAttribute("ui.class", "default"));
            graph.edges().forEach(e -> e.setAttribute("ui.class", "default"));
            return;
        }

        graph.nodes().forEach(e -> e.setAttribute("ui.class", "algo3"));
        graph.edges().forEach(e -> e.setAttribute("ui.class", "algo2"));
        ArrayList<Integer> render = path.getPath();
        graph.getNode(render.get(0)).setAttribute("ui.class", "algo1");
        for (int i = 1; i < render.size(); i++) {
            graph.getNode(render.get(i - 1)).getEdgeBetween(render.get(i)).setAttribute("ui.class", "algo1");
            graph.getNode(render.get(i)).setAttribute("ui.class", "algo2");
        }
        graph.getNode(render.get(render.size() - 1)).setAttribute("ui.class", "algo1");
    }
}