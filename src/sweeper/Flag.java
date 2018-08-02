package sweeper;

class Flag {
    private Matrix flagMap;     // Верхний слой

    private byte totalFlaged; // Установленных флагов
    private short totalClosed; // Сколько закрытых клеток

    void start () {
        flagMap = new Matrix(Box.CLOSED);
        totalFlaged = -1;
        totalClosed = Ranges.getSquare(); // Площадь поля
    }

    Box get (Coord coord) {
        return flagMap.get(coord);
    }

    byte getTotalFlaged() {
        return totalFlaged;
    }

    short getTotalClosed() {
        return totalClosed;
    }

    void setOpenedToBox (Coord coord) {
        flagMap.set (coord, Box.OPENED);
        totalClosed --;
    }

    private void setFlagedToBox (Coord coord) {
        flagMap.set (coord, Box.FLAGED);
        if (totalFlaged >= 0)
            totalFlaged++;
        else totalFlaged = 1;
    }

    private void setClosedToBox (Coord coord) {
        flagMap.set (coord, Box.CLOSED);
        totalFlaged--;
    }

    void toggleFlagedToBox(Coord coord) {
        switch (flagMap.get(coord)) {
            case FLAGED : setClosedToBox(coord); break;
            case CLOSED : setFlagedToBox(coord); break;
        }
    }

    public void setFlagedToLastClosedBoxes() {
        for (Coord coord : Ranges.getAllCoords())
            if (Box.CLOSED == flagMap.get(coord))
                setFlagedToBox(coord);
    }

    public void setBombedToBox(Coord coord) {
        flagMap.set(coord, Box.BOMBED);
    }

    void setOpenedToClosedBox(Coord coord) {
        if (Box.CLOSED == flagMap.get(coord))
            flagMap.set(coord, Box.OPENED);
    }

    void setNobombToFlagedBox(Coord coord) {
        if (Box.FLAGED == flagMap.get(coord))
            flagMap.set (coord, Box.NOBOMB);
    }

    short getCountOfFlagedBoxesAround(Coord coord) {
        short count = 0;
        for (Coord around : Ranges.getCoordsAround(coord))
            if (flagMap.get(around) == Box.FLAGED)
                count ++;
        return count;
    }
}
