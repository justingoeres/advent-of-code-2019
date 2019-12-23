package org.jgoeres.adventofcode2019.Day23;

public class NetworkPacket {
    private Integer address;
    private Long X;
    private Long Y;

    public NetworkPacket(int address, long X, long Y) {
        this.address = address;
        this.X = X;
        this.Y = Y;
    }

    public NetworkPacket(int address) {
        this.address = address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public void setX(long x) {
        this.X = x;
    }

    public void setY(Long y) {
        this.Y = y;
    }

    public int getAddress() {
        return address;
    }

    public Long getX() {
        return X;
    }

    public Long getY() {
        return Y;
    }

    public boolean isComplete() {
        return (address != null
                && X != null
                && Y != null);
    }
}
