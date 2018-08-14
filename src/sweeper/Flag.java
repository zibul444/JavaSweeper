package sweeper;

class Flag {
    private Matrix flagMap;     // Верхний слой

    private byte totalFlagged; // Установленных флагов
    private short totalClosed; // Сколько закрытых клеток

    void start () {
        flagMap = new Matrix(Box.CLOSED);
        totalFlagged = -1;
        totalClosed = Ranges.getSquare(); // Площадь поля
    }

    Box get (final Coord coord) {
        return flagMap.get(coord);
    }

    byte getTotalFlagged() {
        return totalFlagged;
    }

    short getTotalClosed() {
        return totalClosed;
    }

    void setOpenedToBox (final Coord coord) {
        flagMap.set (coord, Box.OPENED);
        totalClosed --;
    }

    private void setFlaggedToBox(final Coord coord) {
        flagMap.set (coord, Box.FLAGED);
        if (totalFlagged >= 0)
            totalFlagged++;
        else totalFlagged = 1;
    }

    private void setClosedToBox (final Coord coord) {
        flagMap.set (coord, Box.CLOSED);
        totalFlagged--;
    }

    void toggleFlaggedToBox(final Coord coord) {
        switch (flagMap.get(coord)) {
            case FLAGED : setClosedToBox(coord); break;
            case CLOSED : setFlaggedToBox(coord); break;
        }
    }

    public void setFlagedToLastClosedBoxes() {
        for (Coord coord : Ranges.getAllCoords())
            if (Box.CLOSED == flagMap.get(coord))
                setFlaggedToBox(coord);
    }

    public void setBombedToBox(final Coord coord) {
        flagMap.set(coord, Box.BOMBED);
    }

    void setOpenedToClosedBox(final Coord coord) {
        if (Box.CLOSED == flagMap.get(coord))
            flagMap.set(coord, Box.OPENED);
    }

    void setNobombToFlagedBox(final Coord coord) {
        if (Box.FLAGED == flagMap.get(coord))
            flagMap.set (coord, Box.NOBOMB);
    }

    short getCountOfFlagedBoxesAround(final Coord coord) {
        short count = 0;
        for (Coord around : Ranges.getCoordsAround(coord))
            if (flagMap.get(around) == Box.FLAGED)
                count ++;
        return count;
    }
}
