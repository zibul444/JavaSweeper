package sweeper;

public class Coord {
    private byte x;
    private byte y;

    public Coord(final byte x, final byte y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Coord))
            return super.equals(o);
        Coord to = (Coord) o;
        return to.x == x && to.y == y;
    }

    public byte getX() {
        return x;
    }

    public byte getY() {
        return y;
    }
}
