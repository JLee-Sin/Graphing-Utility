package cont;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//The class used to represent the top border of the gui
public class TopBorder {

    //The label explaining the combo box
    private Label _function = new Label("Function: ");

    //The combo box used used to select the type of function
    private ComboBox<String> selectFunction = new ComboBox<>();

    //The button used to reset the graph
    private Button _reset = new Button("Reset");

    //The button used to bring up the health screen
    private Button _help = new Button("Help");

    //The right border of the gui, used for updating the type of function
    RightBorder _rp;

    //The Hbox used to contain the elements in the top border
    HBox box;

    //The string representing the type of function
    String type;

    public TopBorder() {
        selectFunction.setPromptText("...");
        selectFunction.getItems().add("Linear");
        selectFunction.getItems().add("Quadratic");
        selectFunction.getItems().add("Cubic");
        selectFunction.getItems().add("Sine");
        selectFunction.getItems().add("Cosine");
        //selectFunction.getItems().add("Point");
        selectFunction.setOnAction(event -> {
            int selected = selectFunction.getSelectionModel().getSelectedIndex();
            switch(selected) {
                case(0):
                    type = "Linear";
                    System.out.println("type change");
                    //_rp = new RightBorder("Linear");
                    break;
                case(1):
                    type = "Quadratic";
                    //_rp = new RightBorder("Quadratic");
                    break;
                case(2):
                    type = "Cubic";
                    //_rp = new RightBorder("Cubic");
                    break;
                case(3):
                    type = "Sine";
                    //_rp = new RightBorder("Sine");
                    break;
                case(4):
                    type = "Cosine";
                    //_rp = new RightBorder("Cosine");
                    break;
                default:
                    //_rp = new RightBorder();
                    break;
            }
        });

        _reset.setTextAlignment(TextAlignment.CENTER);
        _reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Start.reset();
            }
        });

        _help.setTextAlignment(TextAlignment.CENTER);
        _help.setOnAction(this:: callHelp);

        box = new HBox(_function, selectFunction, _reset, _help);
        box.setPadding(new Insets(0.0,20,0.0,20));
        box.setSpacing(10);
        box.setMargin(selectFunction, new Insets(0,375,0,0));
        box.setBorder(new Border(new BorderStroke(Color.rgb(0,0,0),BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(2))));
        box.setAlignment(Pos.CENTER);
        //_reset.setPadding(new Insets(0.0,30,0.0,0.0));
        //_help.setPadding(new Insets(0.0,0.0,0.0,30));
    }

    public HBox getLayout() {
        return box;
    }

    protected void callHelp(ActionEvent actionEvent) {
        Stage helpStage = new Stage(StageStyle.UTILITY);
        helpStage.setAlwaysOnTop(true);
        helpStage.setTitle("Help");


        Label help = new Label("Welcome to graphing utility. To graph a function first select a type of function, " +
                "the currently supported ones functions are linear, quadratic, cubic, sine, and cosine, with more to be " +
                "added soon. Then in the right panel choose what formula you would like to use to graph the function, then " +
                "input the variables as asked and hit the graph button you may enter a decimal or a whole number but not a fraction." +
                "Your function will then be graphed in the left hand panel, lines will be displayed on top of one another to show intersections. To clear the graph simply hit reset in the top right hand corner." +
                "To edit, remove, get information on a line, or check for a point simply hover over it until it is highlighted and then right click and select which option you would like to use." + "\n\n" + "Warning: A glitch has been found that requires you to click a text-field before switching which formula is being used to graph a function, take note of this when switching formulas" + "\n\n" + "Note: The close button stops all threads");
        help.setWrapText(true);

        Button close = new Button("Close");
        close.setOnAction(e -> {
            helpStage.close();
        });

        VBox vb = new VBox(help, close);
        vb.setSpacing(30);
        vb.setAlignment(Pos.TOP_CENTER);
        vb.setMargin(help, new Insets(40.0, 20.0, 10.0,20.0));

        Scene sc = new Scene(vb,500,400);
        helpStage.setScene(sc);
        helpStage.show();

    }

    public String getInsert() {
        return type;
    }
}
