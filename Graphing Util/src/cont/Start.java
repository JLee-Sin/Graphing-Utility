package cont;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//The class that initializes the program and sets up the gui
public class Start {

    //The stage displaying the gui
    private Stage stage;

    //The scene the stage is displaying
    private Scene _scene;

    //The top border of the gui
    private TopBorder _top;

    //The area of the gui responsible for contating the graph
    private static GraphArea _graphap;

    //The right border of the gui
    private RightBorder _insert;

    //An event used for passing information from the different pannels
    private ActionEvent event = new ActionEvent();

    //The selected type of function
    private String type;

    //A separate thread used for updating the gui
    private Thread t2;

    //Sets up the gui
    public Start(Stage stage) {
        this.stage = stage;
        this.stage.setResizable(false);
        this.stage.setTitle("Graphing Utility");
        this.stage.initStyle(StageStyle.DECORATED);
        this.stage.setAlwaysOnTop(false);

        _top = new TopBorder();
        _graphap = new GraphArea();
        _insert = new RightBorder(_top.getInsert(), _graphap.getGraph());

        setScene();
        this.stage.setScene(_scene);
        this.stage.show();
        _top.callHelp(event);
        checkForChanges();
    }

    //Checks for user input
    private void checkForChanges() {
        System.out.println("check method started");
        t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    System.out.println(_top.getInsert());
                    if(_top.getInsert() != null) {
                        System.out.println("b4 new rb");
                        _insert = new RightBorder(_top.getInsert(), _graphap.getGraph());
                        type = _top.getInsert();
                        System.out.println("made new rb");
                        Platform.runLater(() -> Next());
                        break;
                    }
                }
            }
        });
        t2.setDaemon(true);
        t2.start();
        System.out.println("Check started");
    }

    //relays user input
    private void Next() {
        updateGUI();
        checkforsecondchange();
    }

    //A helper method for checking for user input
    private void checkforsecondchange() {
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    System.out.println(type);
                    if(_top.getInsert() != type) {
                        _insert = new RightBorder(_top.getInsert(), _graphap.getGraph());
                        type = _top.getInsert();
                        Platform.runLater(() -> Next());
                    }
                }
            }
        });
        t3.setDaemon(true);
        t3.start();
    }

    //starts the process of updating the gui
    protected void updateGUI() {
        setScene();
        stage.setScene(_scene);
        //stage.close();
        //stage.show();
    }

    //updates the gui
    private void setScene() {
        AnchorPane large = new AnchorPane(_top.getLayout(), _graphap.getLayout(), _insert.getLayout());

        large.setTopAnchor(_top.getLayout(), 0.0);
        large.setRightAnchor(_top.getLayout(), 0.5);
        large.setLeftAnchor(_top.getLayout(), 0.5);
        large.setBottomAnchor(_top.getLayout(), 420.0);

        large.setTopAnchor(_graphap.getLayout(), 60.0);
        large.setRightAnchor(_graphap.getLayout(), 200.0);
        large.setLeftAnchor(_graphap.getLayout(), 0.5);
        large.setBottomAnchor(_graphap.getLayout(), 0.5);

        large.setTopAnchor(_insert.getLayout(), 60.0);
        large.setRightAnchor(_insert.getLayout(), 0.5);
        large.setLeftAnchor(_insert.getLayout(), 520.0);
        large.setBottomAnchor(_insert.getLayout(), 0.5);

        _scene = new Scene(large, 720, 480);
    }

    //clears the graph area
    public static void reset() {
        _graphap.getGraph().reset();
    }

    public static GraphArea getGraphAp() {return _graphap;}
}
