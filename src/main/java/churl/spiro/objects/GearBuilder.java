package churl.spiro.objects;

import javafx.geometry.Point2D;

// Erzeugt eine Gear-Kette in Form einer doppelt verlinkten Liste
public class GearBuilder {

    Gear root = null;

    public GearBuilder extend(double radius) {
        if (root == null) {
            Gear newGear = new Gear();

            newGear.setRadius(radius);
            newGear.setPosition(new Point2D(540, 360));
            root = newGear;

            return this;
        }

        return extend(root, radius);
    }

    private GearBuilder extend(Gear current, double radius) {
        Gear next = current.getNext();

        if (next == null) {
            Gear newGear = new Gear();

            newGear.setRadius(radius);
            newGear.setPosition(current.getPosition());
            newGear.shiftX(current.getRadius());
            newGear.setPrevious(current);
            newGear.setLastPosition(newGear.calcPosition());
            current.setNext(newGear);

            return this;
        }

        return extend(next, radius);
    }

    public Gear build() {
        return root;
    }
}
