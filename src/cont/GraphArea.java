package cont;

import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

//The class that creates the area that will lay beneath the graph
public class GraphArea {

    //Thr x and y axis
    private final Axis xAxis;
    private final Axis yAxis;

    //The line graph scaled using doubles
    private LineChart<Double, Double> lineGraph;

    //The graph itself
    private Graph graph;

    //The anchor pane holding each element
    private AnchorPane ap = new AnchorPane();

    //The type of line being graphed
    private String type;

    public GraphArea() {
        ap.setBorder(new Border(new BorderStroke(Color.rgb(0,0,0), BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(2))));
        type = RightBorder.getRBtype();
        xAxis = new NumberAxis(null, -10, 10, 1);
        yAxis = new NumberAxis(null, -10, 10, 1);
        //xAxis.setAutoRanging(false);
        //yAxis.setAutoRanging(false);
        lineGraph = new LineChart<Double, Double>(yAxis, xAxis);
        lineGraph.setCreateSymbols(true);
        graph = new Graph(lineGraph,10);

        setAp();
    }

    private void setAp() {
        ap.getChildren().add(lineGraph);

        ap.setTopAnchor(lineGraph,15.0);
        ap.setBottomAnchor(lineGraph,15.0);
        ap.setLeftAnchor(lineGraph,15.0);
        ap.setRightAnchor(lineGraph, 15.0);
    }

    public void setType() {
        type = RightBorder.getRBtype();
    }

    public Graph getGraph() {
        return graph;
    }

    public AnchorPane getLayout() {
        return ap;
    }
}
