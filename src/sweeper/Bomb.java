package sweeper;

class Bomb {
    private Matrix bombMap;
    private short totalBombs;

    Bomb(short totalBombs) {
        this.totalBombs = totalBombs;
        fixBombsCount();
    }

    void start () {
        bombMap = new Matrix(Box.ZERO);

        for (byte i = 0; i < totalBombs; i++)
            placeBomb();

    }

    Box get (Coord coord) {
        return bombMap.get(coord);
    }

    public short getTotalBombs() {
        return totalBombs;
    }

    private void fixBombsCount () {
        short maxBombs = (short) (Ranges.getSize().getX() * Ranges.getSize().getY() / 2);
        if (totalBombs > maxBombs)
            totalBombs = maxBombs;
    }

    private void placeBomb() {
        while (true) {
            Coord coord = Ranges.getRandomCoord();
            if (Box.BOMB == bombMap.get(coord))
                continue;
            bombMap.set(coord, Box.BOMB);
            incNumbersAroundBomb(coord);
            break;
        }
    }

    private void incNumbersAroundBomb(Coord coord) {
        for (Coord around : Ranges.getCoordsAround(coord))
            if (Box.BOMB != bombMap.get(around))
                bombMap.set(around, bombMap.get(around).nextNumberBox());
    }
}
