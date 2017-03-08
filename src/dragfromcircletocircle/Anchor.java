/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dragfromcircletocircle;

import javafx.beans.property.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

class Anchor extends Circle 
{ 
    Anchor(Color color, DoubleProperty x, DoubleProperty y) 
    {
        super(x.get(), y.get(), 10);
        setFill(color.deriveColor(1, 1, 1, 0.5));
        setStroke(color);
        setStrokeWidth(2);
        setStrokeType(StrokeType.OUTSIDE);

        x.bind(centerXProperty());
        y.bind(centerYProperty());
        enableDrag();
    }

// make a node movable by dragging it around with the mouse.
    private void enableDrag() 
    {
        final Delta dragDelta = new Delta();

        setOnMousePressed(new EventHandler<MouseEvent>() 
        {
            @Override public void handle(MouseEvent mouseEvent) 
            {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = getCenterX() - mouseEvent.getX();
                dragDelta.y = getCenterY() - mouseEvent.getY();
                getScene().setCursor(Cursor.MOVE);
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() 
        {
            @Override public void handle(MouseEvent mouseEvent) {
                getScene().setCursor(Cursor.HAND);
            }
        });

        setOnMouseDragged(new EventHandler<MouseEvent>()
        {
            @Override public void handle(MouseEvent mouseEvent)
            {
                double newX = mouseEvent.getX() + dragDelta.x;
                if (newX > 0 && newX < getScene().getWidth()) 
                {
                    setCenterX(newX);
                }  

                double newY = mouseEvent.getY() + dragDelta.y;                    
                if (newY > 0 && newY < getScene().getHeight()) 
                {
                    setCenterY(newY);
                }  
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>()
        {
            @Override public void handle(MouseEvent mouseEvent)
            {
                if (!mouseEvent.isPrimaryButtonDown())
                {
                    getScene().setCursor(Cursor.HAND);
                }
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() 
        {
            @Override public void handle(MouseEvent mouseEvent) 
            {
                if (!mouseEvent.isPrimaryButtonDown())
                {
                    getScene().setCursor(Cursor.DEFAULT);
                }
            }
        });
    }

    // records relative x and y co-ordinates.
    private class Delta { double x, y; }
}  