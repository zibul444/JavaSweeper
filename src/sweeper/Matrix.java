package sweeper;

class Matrix {
    private Box [] [] matrix;

    Matrix(final Box box) {
        matrix = new Box [Ranges.getSize().getX()] [Ranges.getSize().getY()];
        for (Coord coord : Ranges.getAllCoords())
            matrix [coord.getX()] [coord.getY()] = box;
    }

    Box get (final Coord coord) {
        if (Ranges.inRange(coord))
            return matrix [coord.getX()] [coord.getY()];
        return null;
    }

    void set (final Coord coord, final Box box) {
        if (Ranges.inRange(coord))
            matrix [coord.getX()] [coord.getY()] = box;
    }

}
