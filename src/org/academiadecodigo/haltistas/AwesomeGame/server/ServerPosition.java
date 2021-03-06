package org.academiadecodigo.haltistas.AwesomeGame.server;

public class ServerPosition {

    public static final int MIN_COLUMN = 0;
    public static final int MAX_COLUMN = 99;
    public static final int MIN_ROW = 0;
    public static final int MAX_ROW = 59;

    private int column;
    private int row;

    public ServerPosition(int column, int row) {

        this.column = column;
        this.row = row;
    }

    public void moveUP() {
        row--;
    }

    public void moveDown() {
        row++;
    }

    public void moveLeft() {
        column--;
    }

    public void moveRight() {
        column++;
    }

    @Override
    public boolean equals(Object o) {

        if (o instanceof ServerPosition) {

            ServerPosition p = (ServerPosition) o;
            return p.column == this.column && p.row == this.row;
        }
        return false;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        return "ServerPosition{" +
                "column=" + column +
                ", row=" + row +
                '}';
    }
}
