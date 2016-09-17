package net.bplaced.therefactory.jewelthief.jewels;

import com.badlogic.gdx.math.Polygon;

public class Gold extends Jewel {

    public Gold() {
        super(Gold.class.getSimpleName());
    }

    @Override
    public Polygon getPolygon() {
        float x = getPosition().x;
        float y = getPosition().y;
        float[] vertices = {
                x + 4, y - 6,
                x + 8, y + 3,
                x + 5, y + 6,
                x - 3, y + 6,
                x - 8, y - 6,
        };
        return new Polygon(vertices);
    }

}
