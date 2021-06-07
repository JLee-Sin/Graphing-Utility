package cont;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.ArrayList;
import java.util.function.Function;

//The class responsible for creating the graph and plotting lines/points
public class Graph {

    //The line chart which holds all of the data
    private LineChart<Double, Double> _graph;

    //The range of the graph
    private double _range;

    //The list of lines on the graph
    private static ArrayList<Line> listOfLines = new ArrayList<Line>();

    //The type of line selected
    private String _type;

    //The number of lines
    private int lnCnt = 0;

    //The original name given to a line
    private String Oldname;

    //The current name of a line
    private String _name;

    //A dropshadow effect placed on each line
    private DropShadow ds = new DropShadow();

    //The options to be placed into the context menu
    private MenuItem checkForPoint = new MenuItem("Check for a point");
    private MenuItem getInfo = new MenuItem("Get Info");
    private MenuItem edit = new MenuItem("Edit");
    private MenuItem remove = new MenuItem("Remove");

    //The context menu that appears upon right clicking a line
    private ContextMenu contextMenu = new ContextMenu(checkForPoint, getInfo, edit, remove);

    //The overlaying chart that highlights intersects
    private XYChart.Series<Double, Double> intersects = new XYChart.Series<>();

    public Graph(final LineChart<Double, Double> graph , double range) {
        _graph = graph;
        //_graph.getXAxis().setAutoRanging(true);
        //_graph.getYAxis().setAutoRanging(true);
        _graph.setLegendSide(Side.RIGHT);
        _graph.setCursor(Cursor.CROSSHAIR);
        _graph.setCache(true);
        _graph.setAnimated(true);
        _range = range;
        //_graph.getData().add(intersects);

    }

    //plots a single point on the graph
    protected void plotPoint(final double x, final double y, final XYChart.Series<Double, Double> series) {
        series.getData().add(new XYChart.Data<Double, Double>(x,y));
    }

    //plots a line on the graph
    protected void plotLine(final Function<Double, Double> function, String type) {
        _type = type;
        final XYChart.Series<Double, Double> series = new XYChart.Series<>();
        for(double i = -_range; i <= _range; i = i + 1) {
            plotPoint(i, function.apply(i), series);
        }
        lnCnt++;
        _name = _type + " " + lnCnt;
        if(Oldname != null) {
            series.setName(Oldname);
            Oldname = null;
        }
        else {
            series.setName(_name);
        }
        listOfLines.add(new Line(function,_name,_range,_type));
        _graph.getData().add(series);
        applyMouseEvents(series);
    }

    //adds the ability to interact with lines on the graph using the mouse
    private void applyMouseEvents(XYChart.Series<Double, Double> series) {
        final Node node = series.getNode();


        node.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                node.setEffect(ds);
                node.setCursor(Cursor.HAND);
            }
        });

        node.setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                node.setEffect(null);
                node.setCursor(Cursor.DEFAULT);
            }
        });

        node.setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                    contextMenu.show(node, mouseEvent.getScreenX() + 1, mouseEvent.getScreenY() - 1);
                    double locx = mouseEvent.getScreenX() + 1;
                    double locy = mouseEvent.getScreenY() + 1;
                    XYChart.Series<Double, Double> selection = series;

                    getInfo.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            for(int i =0; i < listOfLines.size(); i++) {
                                if(series.getName().equalsIgnoreCase(listOfLines.get(i).getname())) {
                                   Stage infoStage = new Stage(StageStyle.UTILITY);
                                   infoStage.setAlwaysOnTop(true);

                                    Label l = listOfLines.get(i).getInfo();

                                    Button close = new Button("Close");
                                    close.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            infoStage.close();
                                        }
                                    });

                                    VBox vBox = new VBox(20, l, close);
                                    vBox.setAlignment(Pos.CENTER);

                                    Scene sc = new Scene(vBox,250,250);

                                    infoStage.setScene(sc);
                                    infoStage.show();
                                }
                            }
                        }
                    });

                    remove.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            _graph.getData().remove(selection);
                        }
                    });

                    edit.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            Stage infoStage = new Stage(StageStyle.UTILITY);
                            infoStage.setAlwaysOnTop(true);

                            Label l = new Label("please make changes to your line by redefining the function in the right panel, the name will remain the same although the color may change" + "\n\n" + "Your line WILL temporarily disappear, this is intentional");

                            Button close = new Button("Close");
                            close.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    infoStage.close();
                                    Oldname = series.getName();
                                    _graph.getData().remove(series);
                                }
                            });

                            VBox vBox = new VBox(20, l, close);
                            vBox.setAlignment(Pos.CENTER);

                            Scene sc = new Scene(vBox,800,100);

                            infoStage.setScene(sc);
                            infoStage.show();
                        }
                    });

                    checkForPoint.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            Stage ptStage = new Stage(StageStyle.UTILITY);
                            ptStage.setAlwaysOnTop(true);

                            TextField x = new TextField();
                            Label xLabel = new Label("X: ");
                            HBox xBox = new HBox(10,xLabel,x);
                            xBox.setAlignment(Pos.CENTER);

                            TextField y = new TextField();
                            Label yLabel = new Label("Y: ");
                            HBox yBox = new HBox(10, yLabel, y);
                            yBox.setAlignment(Pos.CENTER);

                            Button check = new Button("Check");
                            check.setDefaultButton(true);
                            check.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    for(int i =0; i < listOfLines.size(); i++) {
                                        if(series.getName().equalsIgnoreCase(listOfLines.get(i).getname())) {
                                            ptStage.close();
                                            //JOptionPane.showMessageDialog(null,"works but double parsing is being a retard");
                                            double xC = Double.parseDouble(x.getText());
                                            double yC = Double.parseDouble(y.getText());
                                            //JOptionPane.showMessageDialog(null,"works but yl parse is bad");
                                            double yL = listOfLines.get(i).findPoint(xC);
                                            //JOptionPane.showMessageDialog(null,yL);
                                            if(yC == yL) {
                                                //JOptionPane.showMessageDialog(null,"works but javafx is being a dick");
                                                Label r = new Label("The point (" + xC + "," + yC + ") is on the line " + listOfLines.get(i).getname());

                                                Button close = new Button("Close");
                                                close.setOnAction(new EventHandler<ActionEvent>() {
                                                    @Override
                                                    public void handle(ActionEvent event) {
                                                        ptStage.close();
                                                    }
                                                });

                                                VBox res = new VBox(20,r, close);
                                                res.setAlignment(Pos.CENTER);
                                                res.setMargin(close, new Insets(10,0,0,0));

                                                Scene result = new Scene(res, 250, 100);
                                                ptStage.setScene(result);
                                                ptStage.show();
                                            }
                                            else if(yC > yL) {
                                                Label r = new Label("The point (" + xC + "," + yC + ") is above the line " + listOfLines.get(i).getname());

                                                Button close = new Button("Close");
                                                close.setOnAction(new EventHandler<ActionEvent>() {
                                                    @Override
                                                    public void handle(ActionEvent event) {
                                                        ptStage.close();
                                                    }
                                                });

                                                VBox res = new VBox(20,r, close);
                                                res.setAlignment(Pos.CENTER);
                                                res.setMargin(close, new Insets(10,0,0,0));

                                                Scene result = new Scene(res, 250, 100);
                                                ptStage.setScene(result);
                                                ptStage.show();
                                            }
                                            else {
                                                Label r = new Label("The point (" + xC + "," + yC + ") is below the line " + listOfLines.get(i).getname());

                                                Button close = new Button("Close");
                                                close.setOnAction(new EventHandler<ActionEvent>() {
                                                    @Override
                                                    public void handle(ActionEvent event) {
                                                        ptStage.close();
                                                    }
                                                });

                                                VBox res = new VBox(20,r, close);
                                                res.setAlignment(Pos.CENTER);
                                                res.setMargin(close, new Insets(10,0,0,0));

                                                Scene result = new Scene(res, 250, 100);
                                                ptStage.setScene(result);
                                                ptStage.show();
                                            }
                                        }
                                    }
                                }
                            });

                            VBox vbox = new VBox(10, xBox,yBox,check);
                            vbox.setAlignment(Pos.CENTER);
                            vbox.setMargin(check, new Insets(20,0,0,0));

                            Scene sc = new Scene(vbox,400,200);
                            ptStage.setScene(sc);
                            ptStage.show();
                        }
                    });
                }
            }
        });

    }

    //clears the graph
    public void reset() {
        _graph.getData().clear();
    }

    public static ArrayList<Line> getListOfLines() {
        return listOfLines;
    }
}
