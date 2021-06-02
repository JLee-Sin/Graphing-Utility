package cont;

import javafx.scene.control.Label;
import java.util.HashMap;
import java.util.function.Function;

//The class representing a line on the graph
public class Line {

    //The function the line represents
    private Function<Double, Double> _function;

    //The name of the line
    private String _name;

    //The range of the line
    private double _range;

    //A hashmap containg the points on the line
    private HashMap<Double, Double> points;

    //The type of line
    private String _type;

    //The label containg the line's information
    private Label info;

    //Any points that intersect with other lines
    private HashMap<Double, Double> intersects;

    //The slope of the line
    private double slope;

    public Line(Function<Double, Double> function, String name, double range, String type) {
        _function = function;
        _name = name;
        _range = range;
        _type = type;
    }

    public String getType() {
        return _type;
    }

    public double getSlope(){return slope;}

    public double getYInter() {
        return _function.apply(0.0);
    }

    public double findPoint(double x) {
        double y = _function.apply(x);
        return y;
    }

    private void checkInfo() {
        if(_type.equalsIgnoreCase("Linear")) {
            double x1 = 1;
            double x2 = 2;
            double y1 = _function.apply(1.0);
            double y2 = _function.apply(2.0);
            slope = (y2 - y1)/(x2 - x1);
            double linA = (y1 - y2);
            double linB = (x2-x1);
            double linC = ((x1*y2)-(x2*y1));
            info = new Label("" +
                    "Type: " + _type +" \n" +
                    "Name: " + _name + "\n" +
                    "Slope:" + slope + "\n" +
                    "A: " + linA + "\n" +
                    "B: " + linB + "\n" +
                    "C: " + linC);
        }
        else if(_type.equalsIgnoreCase("Quadratic")) {
            info = new Label("" +
                    "Type: " + _type +" \n" +
                    "Name: " + _name + "\n");
        }
        else if(_type.equalsIgnoreCase("Cubic")) {
            info = new Label("" +
                    "Type: " + _type +" \n" +
                    "Name: " + _name + "\n" );
        }
        else if(_type.equalsIgnoreCase("Sin") || _type.equalsIgnoreCase("Cos")) {
            info = new Label("" +
                    "Type: " + _type +" \n" +
                    "Name: " + _name + "\n");
        }
    }

    public HashMap<Double, Double> getPoints() {
        return points;
    }

    public Label getInfo() {
        checkInfo();
        return info;
    }

    public double apply(double d) {
        return _function.apply(d);
    }

    public String getname() {return _name;}

    public HashMap<Double, Double> getIntersects(Line line) {
        double y1, y2;
        for(double i = -_range; i < _range; i++) {
            y1 = _function.apply(i);
            y2 = line.apply(i);
            if(y1 == y2) {
                intersects.put(i,y1);
            }
        }
        return intersects;
    }
}
