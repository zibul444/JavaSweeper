package sweeper;

import java.util.ArrayList;
import java.util.Random;

public class Ranges {
    static private Coord size;
    static private ArrayList<Coord> allCoords;
    static private Random random = new Random();

    static void setSize (Coord size) {
        Ranges.size = size;
        allCoords = new ArrayList<>();
        for (byte x = 0; x < size.getX(); x++) {
            for (byte y = 0; y < size.getY(); y++) {
                allCoords.add(new Coord(x, y));
            }
        }
    }

    public static Coord getSize() {
        return size;
    }

    public static ArrayList<Coord> getAllCoords() {
        return allCoords;
    }

    static boolean inRange (Coord coord) {
        return  coord.getX() >= 0 && coord.getX() < size.getX() &&
                coord.getY() >= 0 && coord.getY() < size.getY();
    }

    static Coord getRandomCoord() {
        return new Coord((byte) random.nextInt(size.getX()), (byte) random.nextInt(size.getY()));
    }

    static  ArrayList<Coord> getCoordsAround(Coord coord) {
        Coord around;
        ArrayList<Coord> list = new ArrayList<>();
        for (byte x = (byte) (coord.getX() - 1); x <= coord.getX() + 1; x++)
            for (byte y = (byte) (coord.getY() - 1); y <= coord.getY() + 1; y++)
                if (inRange(around = new Coord( x,  y)))
                    if (!around.equals(coord))
                        list.add (around);
        return list;
    }

    static short getSquare() {
        return (short) (size.getX() * size.getY());
    }
}
