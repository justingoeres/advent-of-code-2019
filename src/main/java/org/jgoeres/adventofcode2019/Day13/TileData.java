package org.jgoeres.adventofcode2019.Day13;

import org.jgoeres.adventofcode2019.common.XYPoint;

public class TileData {
    private XYPoint position;
    private Tile tile;

    public TileData() {
    }

    public TileData(XYPoint XY, Tile tile) {
        this.position = XY;
        this.tile = tile;
    }

    public TileData(int x) {
        this.position = new XYPoint();
        this.setX(x);
    }

    public XYPoint getPosition() {
        return position;
    }

    public void setPosition(XYPoint xy) {
        this.position = xy;
    }

    public void setX(int x) {
        this.position.setX(x);
    }

    public void setY(int y) {
        this.position.setY(y);
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    @Override
    public String toString() {
        return position.toString() + "\t" + tile.toString();

    }
}
