package org.academiadecodigo.haltistas.AwesomeGame.server;

import org.academiadecodigo.haltistas.AwesomeGame.server.apple.Apple;
import org.academiadecodigo.haltistas.AwesomeGame.server.apple.AppleType;

import java.util.LinkedList;

public class Snake {

    public LinkedList<ServerPosition> positionList;
    private String name;
    private volatile boolean over;
    private Direction direction;
    private boolean isEatingApple;
    private boolean isEatingGreen;
    private boolean isEatingRed;
    private int maxCol;
    private int minCol;


    public Snake(String name, ServerPosition initial1, ServerPosition initial2, ServerPosition initial3) {

        this.name = name;
        direction = Direction.UP;
        positionList = new LinkedList<>();

        positionList.add(initial1);
        positionList.add(initial2);
        positionList.add(initial3);
    }

    public void setDirection(String msg) {

        if (msg.equals("up") && (direction.equals(Direction.RIGHT) || direction.equals(Direction.LEFT))) {
            direction = Direction.UP;
        }

        if (msg.equals("down") && (direction.equals(Direction.RIGHT) || direction.equals(Direction.LEFT))) {
            direction = Direction.DOWN;
        }

        if (msg.equals("left") && (direction.equals(Direction.UP) || direction.equals(Direction.DOWN))) {
            direction = Direction.LEFT;
        }

        if (msg.equals("right") && (direction.equals(Direction.UP) || direction.equals(Direction.DOWN))) {
            direction = Direction.RIGHT;
        }
    }

    public String move() {

        ServerPosition position = new ServerPosition(positionList.get(0).getColumn(), positionList.get(0).getRow());
        String msg = "";

        switch (direction) {

            case UP:

                position.moveUP();
                positionList.add(0, position);

                if (position.getRow() == ServerPosition.MIN_ROW) {
                    setOver();
                }

                msg = "move-" + name + "-u";
                break;

            case DOWN:

                position.moveDown();
                positionList.add(0, position);

                if (position.getRow() == ServerPosition.MAX_ROW) {
                    setOver();
                }

                msg = "move-" + name + "-d";
                break;

            case LEFT:

                position.moveLeft();
                positionList.add(0, position);

                if (position.getColumn() == minCol) {
                    setOver();
                }

                msg = "move-" + name + "-l";
                break;

            case RIGHT:

                position.moveRight();
                positionList.add(0, position);

                if (position.getColumn() == maxCol) {
                    setOver();
                }

                msg = "move-" + name + "-r";
                break;

            default:

                System.out.println("WTF!");
                break;

        }
        return msg;
    }

    public String deleteLast() {

        positionList.remove(positionList.size() - 1);

        if (positionList.size() == 0) {
            setOver();
        }
        String delete = "delete" + "-" + name;
        return delete;
    }

    public void checkCollision(Snake snake) {

        if (over) {
            return;
        }

        ServerPosition head = positionList.get(0);

        for (ServerPosition position : snake.positionList) {

            if (head.equals(position)) {
                setOver();

            }
        }
    }

    public boolean isEatingApple(Apple apple) {


        if (apple.compare(positionList.peek())) {
            AppleType type = apple.getType();

            switch (type) {

                case RED:
                    isEatingRed = true;
                    isEatingApple = true;
                    break;

                case GREEN:
                    isEatingGreen = true;
                    isEatingApple = true;
                    break;

                default:
                    System.out.println("Apple colliding snake error");
            }
        }
        return isEatingApple;
    }

    public boolean getIsEatingApple() {
        return isEatingApple;
    }

    public void setEatingAppleFalse() {
        isEatingApple = false;
    }

    public boolean getIsEatingGreen() {
        return isEatingGreen;
    }

    public void setIsEatingGreenFalse() {
        isEatingGreen = false;
    }

    public boolean getIsEatingRed() {
        return isEatingRed;
    }

    public void setIsEatingRedFalse() {
        isEatingRed = false;
    }

    public void setMinCol(int minCol) {
        this.minCol = minCol;
    }

    public void setMaxCol(int maxCol) {
        this.maxCol = maxCol;
    }

    public String getName() {
        return name;
    }

    private void setOver() {
        over = true;
    }

    public boolean getIsOver() {
        return over;
    }

    @Override
    public String toString() {
        return "PlayerSnake{" + positionList.peek() + "}";
    }

    public boolean hasPosition(ServerPosition position) {
        return positionList.contains(position);
    }
}
