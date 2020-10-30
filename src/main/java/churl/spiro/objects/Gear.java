package churl.spiro.objects;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Gear extends Circle {

    private Gear next = null;
    private Gear previous = null;

    private double rotation = 0; // Stored in radians
    private Point2D lastPosition;

    public Gear() {
        this.setFill(Color.TRANSPARENT);
        this.setStroke(Color.BLACK);
    }

    public void update(double radians, GraphicsContext canvasGC) {
        if (previous != null) {
            setPosition(previous.getPosition());
            shiftX(previous.getRadius() * Math.cos(previous.getRotation()));
            shiftY(previous.getRadius() * Math.sin(previous.getRotation()));
        }

        rotateBy(radians / getRadius());

        if (next == null) {
            Point2D currentPosition = calcPosition();

            canvasGC.strokeLine(lastPosition.getX(), lastPosition.getY(), currentPosition.getX(),
                    currentPosition.getY());

            lastPosition = currentPosition;
        } else {
            next.update(radians, canvasGC);
        }

    }

    public Gear getNext() {
        return next;
    }

    public void setNext(Gear newNext) {
        next = newNext;
    }

    public Gear getPrevious() {
        return previous;
    }

    public void setPrevious(Gear newPrevious) {
        previous = newPrevious;
    }

    public Point2D getPosition() {
        return new Point2D(this.getLayoutX(), this.getLayoutY());
    }

    public void setPosition(Point2D newPosition) {
        this.setLayoutX(newPosition.getX());
        this.setLayoutY(newPosition.getY());
    }

    public void shiftX(double deltaX) {
        this.setLayoutX(this.getLayoutX() + deltaX);
    }

    public void shiftY(double deltaY) {
        this.setLayoutY(this.getLayoutY() + deltaY);
    }

    public double getRotation() {
        return rotation;
    }

    public void rotateBy(double radians) {
        rotation = rotation + radians;

        rotation = rotation % (Math.PI * 2);
    }

    public Point2D calcPosition() {
        return new Point2D(getPosition().getX() + getRadius() * Math.cos(rotation),
                getPosition().getY() + getRadius() * Math.sin(rotation));
    }

    public void setLastPosition(Point2D newPosition) {
        lastPosition = newPosition;
    }

    public void printGearConfig() {
        Gear current = this;

        System.out.println();
        System.out.println("---------------------------------------------");
        System.out.println("Current Gear Configuration:");

        do {
            System.out.println(current.getRadius());
            current = current.getNext();
        } while (current.getNext() != null);
        System.out.println(current.getRadius());
        System.out.println("---------------------------------------------");
    }

    public List<Gear> printAsList() {
        List<Gear> gearList = new ArrayList<Gear>();
        Gear current = this;

        do {
            gearList.add(current);
            current = current.getNext();
        } while (current.getNext() != null);
        gearList.add(current);

        return gearList;
    }
}
