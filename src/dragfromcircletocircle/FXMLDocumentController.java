/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dragfromcircletocircle;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.*;
import javafx.beans.property.*;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

/**
 *
 * @author blj0011
 */
public class FXMLDocumentController implements Initializable
{
    @FXML private AnchorPane apMain;
    @FXML private Label label;
    @FXML private Circle circle1, circle2;
    
    
    @FXML
    private void handleButtonAction(ActionEvent event)
    {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        EventHandler<MouseEvent> handler = (MouseEvent event) -> 
        {
            final Anchor start;
            final Anchor end; 
            Line line;
            
            
            System.out.println(event.getEventType());
            if(event.getEventType().equals(MouseEvent.MOUSE_CLICKED))
            {
                Bounds boundsInScene = circle1.localToScene(circle1.getBoundsInLocal());
                double centerX = (boundsInScene.getMinX() + boundsInScene.getMaxX()) / 2;
                double centerY = (boundsInScene.getMinY() + boundsInScene.getMaxY()) / 2;
                
                
                
                DoubleProperty startX = new SimpleDoubleProperty(centerX);
                DoubleProperty startY = new SimpleDoubleProperty(centerY);
                DoubleProperty endX   = new SimpleDoubleProperty(event.getSceneX());
                DoubleProperty endY   = new SimpleDoubleProperty(event.getSceneY());
                
                start = new Anchor(Color.BLUE, startX, startY);
                end = new Anchor(Color.TOMATO, endX, endY);
                line = new BoundLine(startX, startY, endX, endY);
                
                apMain.getChildren().addAll(start, end, line);
                
                new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        Bounds boundsInScene2 = circle2.localToScene(circle2.getBoundsInLocal());
                        Bounds boundsInScene3 = end.localToScene(end.getBoundsInLocal());

                        Rectangle tempRectangle = new Rectangle();
                        tempRectangle.setX(boundsInScene3.getMinX());
                        tempRectangle.setY(boundsInScene3.getMinY());
                        tempRectangle.setWidth(boundsInScene3.getWidth());
                        tempRectangle.setHeight(boundsInScene3.getHeight());

                        Shape intersect = Shape.intersect(circle2, tempRectangle);
                        if(intersect.getBoundsInLocal().getWidth() != -1)
                        {         
                            System.out.println("Collision detected!");
                            double centerX2 = (boundsInScene2.getMinX() + boundsInScene2.getMaxX()) / 2;
                            double centerY2 = (boundsInScene2.getMinY() + boundsInScene2.getMaxY()) / 2;
                            end.setCenterX(centerX2);
                            end.setCenterY(centerY2);                    
                        }
                    }
                }.start();
            }            
        };
        
        circle1.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
    }    
    
    class Measure{double x; double y;}
}
