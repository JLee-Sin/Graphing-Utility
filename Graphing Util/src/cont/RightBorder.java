package cont;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//The class which creates the right border of the screen responsible for specifying the details of a selected line type
public class RightBorder {

    //The combo used to select a formula given the type of line
    private ComboBox<String> form = new ComboBox<>();

    //The button that graphs the specified line
    private Button graph = new Button("Graph");

    //The vbox containing all of the sidebar's elements
    private VBox box = new VBox(20,form);

    //The type of function
    private static String RBtype;

    //All the possible variables in a linear equation
    private double linA, linB, linC, linm, linb, liny, linx;

    //All the possible variables in a quadratic equation
    private double QuadA, QuadB, QuadC, QuadH, QuadK, Quadp, Quadq;

    //All the possible variables in a cubic equation
    private double CubeA, CubeB, CubeC, CubeD;

    //All the possible variables in  the equation of a sine function
    private double SinA, SinB, SinC, SinD;

    //All the possible variables in  the equation of a sine function
    private double CosA, CosB, CosC, CosD;

    //The graph that lines will be added to
    private Graph g;

    //Creates the border
    public RightBorder(String function, Graph g) {
        this.g = g;
        form.setPromptText("Formula");
        graph.setDefaultButton(true);
        if(function == null){
            box = new VBox();
            box.setBorder(new Border(new BorderStroke(Color.rgb(0,0,0), BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(2))));
        }
        else if(function.equals("Linear")){
            RBtype = "Linear";
            form.getItems().add("Standard Form");
            form.getItems().add("Slope Intercept Form");
            form.getItems().add("Point-Slope Form");
            form.setOnAction(event -> {
                int selected = form.getSelectionModel().getSelectedIndex();
                switch(selected) {
                    case(0):
                        System.out.println("called stan lin");
                        StandardLine();
                        break;
                    case(1):
                        SlopeInter();
                        break;
                    case(2):
                        PtSlope();
                        break;
                    default:
                        break;
                }
            });
        }
        else if (function.equals("Quadratic")){
            RBtype = "Quadratic";
            form.getItems().add("Standard Form");
            form.getItems().add("Intercept Form");
            form.getItems().add("Vertex Form");
            form.setOnAction(event -> {
                int selected = form.getSelectionModel().getSelectedIndex();
                switch(selected) {
                    case(0):
                        StandardPara();
                        break;
                    case(1):
                        Inter();
                        break;
                    case(2):
                        Vertex();
                        break;
                    default:
                        break;
                }
            });
        }
        else if (function.equals("Cubic")){
            RBtype = "Cubic";
            form.getItems().add("Standard Form");
            form.getItems().add("Intercept Form");
            form.setOnAction(event -> {
                int selected = form.getSelectionModel().getSelectedIndex();
                switch(selected) {
                    case(0):
                        StandardCubic();
                        break;
                    case(1):
                        CubicInter();
                        break;
                    default:
                        break;
                }
            });
        }
        else if (function.equals("Sine")){
            RBtype = "Sine";

            TextField A = new TextField();
            Label Alabel = new Label("A: ");
            HBox hboxA = new HBox(Alabel, A);

            TextField B = new TextField();
            Label Blabel = new Label("B: ");
            HBox hboxB = new HBox(Blabel, B);

            TextField C = new TextField();
            Label Clabel = new Label("C: ");
            HBox hboxC = new HBox(Clabel, C);

            TextField D = new TextField();
            Label Dlabel = new Label("D: ");
            HBox hboxD = new HBox(Dlabel, D);

            box.getChildren().clear();
            box.getChildren().addAll(hboxA, hboxB, hboxC, hboxD, graph);

            graph.setOnAction(e -> {

                if(A.getText() != null) {
                    SinA = Double.parseDouble(A.getText());
                }
                else {
                    SinA = 1.0;
                }

                if(B.getText() != null) {
                    SinB = Double.parseDouble(B.getText());
                }
                else {
                    SinB = 1.0;
                }

                if(C.getText() != null) {
                    SinC = Double.parseDouble(C.getText());
                }
                else {
                    SinC = 0.0;
                }

                if(D.getText() != null) {
                    SinD = Double.parseDouble(D.getText());
                }
                else {
                    SinD = 0.0;
                }

                g.plotLine(x -> SinA*Math.sin((SinB*x) - SinC) + SinD, "Sin");
            });

        }
        else if (function.equals("Cosine")){
            RBtype = "Cosine";

            TextField A = new TextField();
            Label Alabel = new Label("A: ");
            HBox hboxA = new HBox(Alabel, A);

            TextField B = new TextField();
            Label Blabel = new Label("B: ");
            HBox hboxB = new HBox(Blabel, B);

            TextField C = new TextField();
            Label Clabel = new Label("C: ");
            HBox hboxC = new HBox(Clabel, C);

            TextField D = new TextField();
            Label Dlabel = new Label("D: ");
            HBox hboxD = new HBox(Dlabel, D);

            box.getChildren().clear();
            box.getChildren().addAll(hboxA, hboxB, hboxC, hboxD, graph);

            graph.setOnAction(e -> {

                if(A.getText() != null) {
                    CosA = Double.parseDouble(A.getText());
                }
                else {
                    CosA = 1.0;
                }

                if(B.getText() != null) {
                    CosB = Double.parseDouble(B.getText());
                }
                else {
                    CosB = 1.0;
                }

                if(C.getText() != null) {
                    CosC = Double.parseDouble(C.getText());
                }
                else {
                    CosC = 0.0;
                }

                if(D.getText() != null) {
                    CosD = Double.parseDouble(D.getText());
                }
                else {
                    CosD = 0.0;
                }

                g.plotLine(x -> CosA*Math.cos((CosB*x) - CosC) + CosD, "Cos");
            });


        }
    }

    //Sets the border for the input of a cubic function using intercept form
    private void CubicInter() {
        TextField A = new TextField();
        Label Alabel = new Label("A: ");
        HBox hboxA = new HBox(Alabel, A);

        TextField B = new TextField();
        Label Blabel = new Label("B: ");
        HBox hboxB = new HBox(Blabel, B);

        TextField C = new TextField();
        Label Clabel = new Label("C: ");
        HBox hboxC = new HBox(Clabel, C);

        TextField D = new TextField();
        Label Dlabel = new Label("D: ");
        HBox hboxD = new HBox(Dlabel, D);

        box.getChildren().clear();
        box.getChildren().addAll(form, hboxA, hboxB, hboxC, hboxD, graph);

        graph.setOnAction(e -> {
            if(A.getText() != null) {
                CubeA = Double.parseDouble(A.getText());
            }
            else {
                CubeA = 1.0;
            }

            if(B.getText() != null) {
                CubeB = Double.parseDouble(B.getText());
            }
            else {
                CubeB = 1.0;
            }

            if(C.getText() != null) {
                CubeC = Double.parseDouble(C.getText());
            }
            else {
                CubeC = 0.0;
            }

            if(D.getText() != null) {
                CubeD = Double.parseDouble(D.getText());
            }
            else {
                CubeD = 0.0;
            }

            g.plotLine(x -> CubeA*(x-CubeB)*(x-CubeC)*(x-CubeD), "Cubic");
        });
    }

    //Sets the border for the input of a cubic function using the standard form
    private void StandardCubic() {
        TextField A = new TextField();
        Label Alabel = new Label("A: ");
        HBox hboxA = new HBox(Alabel, A);

        TextField B = new TextField();
        Label Blabel = new Label("B: ");
        HBox hboxB = new HBox(Blabel, B);

        TextField C = new TextField();
        Label Clabel = new Label("C: ");
        HBox hboxC = new HBox(Clabel, C);

        TextField D = new TextField();
        Label Dlabel = new Label("D: ");
        HBox hboxD = new HBox(Dlabel, D);

        box.getChildren().clear();
        box.getChildren().addAll(form, hboxA, hboxB, hboxC, hboxD, graph);

        graph.setOnAction(e -> {
            if(A.getText() != null) {
                CubeA = Double.parseDouble(A.getText());
            }
            else {
                CubeA = 1.0;
            }

            if(B.getText() != null) {
                CubeB = Double.parseDouble(B.getText());
            }
            else {
                CubeB = 0.0;
            }

            if(C.getText() != null) {
                CubeC = Double.parseDouble(C.getText());
            }
            else {
                CubeC = 0.0;
            }

            if( D.getText() != null) {
                CubeD = Double.parseDouble(D.getText());
            }
            else {
                CubeD = 0.0;
            }

            g.plotLine(x -> CubeA*Math.pow(x,3) + CubeB*Math.pow(x,2) + CubeC*x + CubeD, "Cubic");
        });


    }

    //Sets the border for the input of a quadratic function using vertex form
    private void Vertex() {
        TextField A = new TextField();
        Label ALabel = new Label("A: ");
        HBox hboxA = new HBox(ALabel, A);

        TextField h = new TextField();
        Label hLabel = new Label("h: ");
        HBox hboxh = new HBox(hLabel, h);

        TextField k = new TextField();
        Label kLabel = new Label("k: ");
        HBox hBoxk = new HBox(kLabel, k);

        box.getChildren().clear();
        box.getChildren().addAll(form, hboxA, hboxh, hBoxk, graph);

        graph.setOnAction(e -> {
            if(A.getText() != null) {
                QuadA = Double.parseDouble(A.getText());
            }
            else {
                QuadA = 1.0;
            }

            if(h.getText() != null) {
                QuadH = Double.parseDouble(h.getText());
            }
            else {
                QuadH = 0.0;
            }

            if(k.getText() != null) {
                QuadK = Double.parseDouble(k.getText());
            }
            else {
                QuadK = 0.0;
            }
            
            g.plotLine(x -> QuadA*Math.pow(x - QuadH, 2) + QuadK, "Quadratic");
        });
        

    }

    //Sets the border for the input of a quadratic function using intercept form
    private void Inter() {
        TextField A = new TextField();
        Label ALabel = new Label("A: ");
        HBox hboxA = new HBox(ALabel, A);

        TextField p = new TextField();
        Label pLabel = new Label("p: ");
        HBox hboxp = new HBox(pLabel, p);

        TextField q = new TextField();
        Label qLabel = new Label("q: ");
        HBox hboxq = new HBox(qLabel, q);

        box.getChildren().clear();
        box.getChildren().addAll(form, hboxA,hboxq,hboxp,graph);

        graph.setOnAction(e -> {
            if(A.getText() != null) {
                QuadA = Double.parseDouble(A.getText());
            }
            else {
                QuadA = 1.0;
            }

            if(p.getText() != null) {
                Quadp = Double.parseDouble(p.getText());
            }
            else {
                Quadp = 0.0;
            }

            if(q.getText() != null) {
                Quadq = Double.parseDouble(q.getText());
            }
            else {
                Quadq = 0.0;
            }

            g.plotLine(x -> QuadA * (x-Quadq) * (x-Quadq), "Quadratic");
        });


    }

    //Sets the border for the input of a quadratic function using standard form
    private void StandardPara() {
        TextField A = new TextField();
        Label ALabel = new Label("A: ");
        HBox hboxA = new HBox(ALabel, A);

        TextField B = new TextField();
        Label BLabel = new Label("B: ");
        HBox hboxB = new HBox(BLabel, B);

        TextField C = new TextField();
        Label CLabel = new Label("C: ");
        HBox hboxC = new HBox(CLabel, C);

        box.getChildren().clear();
        box.getChildren().addAll(form, hboxA, hboxB, hboxC, graph);

        graph.setOnAction(e -> {
            if(A.getText() != null) {
                QuadA = Double.parseDouble(A.getText());
            }
            else {
                QuadA = 1.0;
            }

            if(B.getText() != null) {
                QuadB = Double.parseDouble(B.getText());
            }
            else {
                QuadB = 0.0;
            }

            if(C.getText() != null) {
                QuadC = Double.parseDouble(C.getText());
            }
            else {
                QuadC = 0.0;
            }

            g.plotLine(x -> QuadA*(Math.pow(x,2))+(QuadB*x)+QuadC, "Quadratic");
        });


    }

    //Sets the border for the input of a quadratic function using point-slope form
    private void PtSlope() {
        TextField M = new TextField();
        Label Mlabel = new Label("m: ");
        HBox hboxM = new HBox(Mlabel, M);

        TextField X = new TextField();
        Label Xlabel = new Label("X1: ");
        HBox hboxX = new HBox(Xlabel, X);

        TextField Y = new TextField();
        Label Ylabel = new Label("Y: ");
        HBox hboxY = new HBox(Ylabel, Y);

        box.getChildren().clear();
        box.getChildren().addAll(form,hboxM, hboxX, hboxY, graph);

        graph.setOnAction(e -> {
            if(M.getText() != null) {
                linm = Double.parseDouble(M.getText());
            }
            else {
                linm = 1.0;
            }

            if(X.getText() != null) {
                linx = Double.parseDouble(X.getText());
            }
            else {
                linx = 0.0;
            }

            if(Y.getText() != null) {
                liny = Double.parseDouble(Y.getText());
            }
            else {
                liny = 0.0;
            }

           g.plotLine(x -> linm*(x+linx)+liny, "Linear");
        });


    }

    //Sets the border for the input of a quadratic function using slope intercept form
    private void SlopeInter() {
        TextField M = new TextField();
        Label Mlabel = new Label("m: ");
        HBox hboxM = new HBox(Mlabel, M);

        TextField b = new TextField();
        Label blabel = new Label("b: ");
        HBox hboxb = new HBox(blabel, b);

        box.getChildren().clear();
        box.getChildren().addAll(form, hboxM, hboxb, graph);

        graph.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //System.out.println("button pushed");
                //JOptionPane.showMessageDialog(null,"PLEASE KILL ME I WANT TO DIE");
                if(M.getText() == null) {
                    linm = 1.0;
                }
                else {
                    linm = Double.parseDouble(M.getText());
                }

                if(b.getText() == null) {
                    linb = 0.0;
                }
                else {
                    linb = Double.parseDouble(b.getText());
                }

                //System.out.println("doubles designed");
                //JOptionPane.showMessageDialog(null,linm + linb + "bruh this shit works");
                g.plotLine(x -> (linm*x)+linb, "Linear");
            }
        });

        //graph.setOnAction(event -> {
          //
        //});
    }

    //Sets the border for the input of a linear function using standard form
    private void StandardLine() {
        TextField A = new TextField();
        Label Alabel = new Label("A: ");
        HBox hboxA = new HBox(Alabel, A);

        TextField B = new TextField();
        Label Blabel = new Label("B: ");
        HBox hboxB = new HBox(Blabel, B);

        TextField C = new TextField();
        Label Clabel = new Label("C: ");
        HBox hboxC = new HBox(Clabel, C);

        box.getChildren().clear();
        box.getChildren().addAll(form, hboxA, hboxB, hboxC, graph);
        graph.setOnAction(e -> {

            if(A.getText() != null) {
                linA = Double.parseDouble(A.getText());
            }
            else {
                linA = 1.0;
            }

            if(B.getText() != null) {
                linB = Double.parseDouble(B.getText());
            }
            else {
                linB = 1.0;
            }

            if(linB == 0) {
                Stage errStage = new Stage(StageStyle.UTILITY);

                Label err = new Label("Error B can not be 0");

                Button close = new Button("Close");
                close.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        errStage.close();
                    }
                });

                VBox errBox = new VBox(30, err, close);
                errBox.setAlignment(Pos.TOP_CENTER);
                errBox.setMargin(err, new Insets(60.0,20.0,10.0,20.0));

                Scene sc = new Scene(errBox,480, 144);
                errStage.setScene(sc);
                errStage.show();
            }

            if(C.getText() != null) {
                linC = Double.parseDouble(C.getText());
            }
            else {
                linC = 0.0;
            }

            g.plotLine(x -> ((-linA*x)+linC)/linB, "Linear");
        });


    }

    //Sets the right border if the type of function hasn't been selected yet
    public RightBorder() {
        box = new VBox();
        box.setBorder(new Border(new BorderStroke(Color.rgb(0,0,0),BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(2))));
    }

    public VBox getLayout() {
        return box;
    }

    public static String getRBtype() {
        return RBtype;
    }
}
