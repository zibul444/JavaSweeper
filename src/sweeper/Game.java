package sweeper;

public class Game {
    private Bomb bomb;
    private Flag flag;

    private GameState state;

    public Game(byte cols, byte rows, short bombs) {
        Ranges.setSize(new Coord(cols, rows));
//        if (bombs > (cols * rows)) bombs = (short) (cols * rows / 2);
        bomb = new Bomb(bombs);
        flag = new Flag ();
    }

    public void start() {
        bomb.start();
        flag.start();
        state = GameState.PLAYED;
    }

    public void pressLeftButton(Coord coord) {
        if (isGameOver()) return;

        openBox(coord);
        checkWinner();
    }

    public void pressRightButton(Coord coord) {
        if (isGameOver()) return;

        flag.toggleFlagedToBox(coord);
    }


    public Box getBox(Coord coord) {
        if (Box.OPENED == flag.get(coord))
            return bomb.get (coord);
        else
            return flag.get(coord);
    }

    public GameState getState() {
        return state;
    }

    public short getTotalBombs() {
        return bomb.getTotalBombs();
    }

    public short getTotalFlagged() {
        return flag.getTotalFlaged();
    }

    private boolean isGameOver() {
        if (state != GameState.PLAYED) {
            start();
            return true;
        }
        return false;
    }

    private void checkWinner() {
        if (GameState.PLAYED == state)
            if (flag.getTotalClosed() == bomb.getTotalBombs()) {
                state = GameState.WINNER;
                flag.setFlagedToLastClosedBoxes();
            }
    }

    private void openBox(Coord coord) {
        switch (flag.get(coord)) {
            case OPENED : setOpenedToClosedBoxesAroundNumber (coord); break;
            case FLAGED : break;
            case CLOSED :
                switch (bomb.get(coord)) {
                    case ZERO : openBoxesAroundZero (coord); break;
                    case BOMB : openBombs(coord); break;
                    default   : flag.setOpenedToBox(coord); break;

                }
        }
    }

    void setOpenedToClosedBoxesAroundNumber(Coord coord) {
        if (Box.BOMB != bomb.get(coord))
            if (bomb.get(coord).getNumber () == flag.getCountOfFlagedBoxesAround (coord))
                for (Coord around : Ranges.getCoordsAround(coord))
                    if (flag.get(around) == Box.CLOSED)
                        openBox(around);
    }

    private void openBombs(Coord bombedCoord) {
        flag.setBombedToBox(bombedCoord);
        for (Coord coord: Ranges.getAllCoords())
            if (bomb.get(coord) == Box.BOMB)
                flag.setOpenedToClosedBox (coord);
            else
                flag.setNobombToFlagedBox (coord);
        state = GameState.BOMBED;
    }

    private void openBoxesAroundZero (Coord coord) {
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordsAround(coord))
            openBox (around);
    }
}
