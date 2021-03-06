package org.academiadecodigo.haltistas.AwesomeGame.player;

import org.academiadecodigo.haltistas.AwesomeGame.utils.Sound;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.pictures.Picture;

import java.util.ArrayList;
import java.util.Arrays;


public class PlayerGrid {

    private final int CELL_SIZE = 10;
    private final int PADDING = 10;
    private final int ROWS = 60;
    private final int COLS = 100;
    private final int WALL_POS = 500;

    private Sound sound;
    private PlayerPosition[][] positions;
    private ArrayList<PlayerSnake> playerSnakeList;

    private Picture pictureRestart;
    private Picture canvas;
    private Picture wall;


    public void init() {
        canvas = new Picture(PADDING, PADDING, "resources/sand.jpg");
        canvas.draw();
        wall = new Picture(WALL_POS, PADDING, "resources/wall.jpg");
        sound = new Sound("/resources/dj-snake.wav");

        new Rectangle(PADDING, PADDING, COLS * CELL_SIZE, ROWS * CELL_SIZE).draw();


        positions = new PlayerPosition[COLS][ROWS];
        int initialColSnake1 = 24;
        int initialColSnake2 = 75;
        int initialRowSnakes = 30;

        for (int col = 0; col < COLS; col++) {
            for (int row = 0; row < ROWS; row++) {
                positions[col][row] = new PlayerPosition(col, row, CELL_SIZE, PADDING);
            }
        }
        playerSnakeList = new ArrayList<>();
        PlayerSnake playerSnake1 = new PlayerSnake(this, positions[initialColSnake1][initialRowSnakes], positions[initialColSnake1][initialRowSnakes + 1],
                positions[initialColSnake1][initialRowSnakes + 2], Color.BLUE);
        playerSnakeList.add(playerSnake1);
        PlayerSnake playerSnake2 = new PlayerSnake(this, positions[initialColSnake2][initialRowSnakes], positions[initialColSnake2][initialRowSnakes + 1],
                positions[initialColSnake2][initialRowSnakes + 2], Color.GREEN);
        playerSnakeList.add(playerSnake2);
    }

    public void start() {
        wall.draw();
        sound.loopIndef();

    }

    public PlayerPosition getPos(int col, int row) {
        return positions[col][row];
    }

    public void move(int snake, String dir) {

        switch (dir) {
            case "r":
                playerSnakeList.get(snake).moveRight();
                break;
            case "l":
                playerSnakeList.get(snake).moveLeft();
                break;
            case "u":
                playerSnakeList.get(snake).moveUp();
                break;
            case "d":
                playerSnakeList.get(snake).moveDown();
                break;
            default:
                System.out.println("shit");
                break;
        }

    }

    public void deleteSnake(int snake) {
        playerSnakeList.get(snake).removePos();
    }

    public void greenApple(int row, int col) {
        positions[col][row].paintGreenApple();
    }

    public void deleteApple(int row, int col) {
        positions[col][row].deleteAp();
    }

    public void redApple(int row, int col) {
        positions[col][row].paintRedApple();
    }


    public void deleteWall() {
        wall.delete();
    }


    public void gameOver(String player) {

        pictureRestart = new Picture(PADDING, PADDING, "resources/restart.jpg");

        try {

            switch (player) {
                case "0":
                    Picture picturePlayer2 = new Picture(PADDING, PADDING, "resources/player2winner.png");
                    picturePlayer2.draw();
                    canvas.delete();
                    wall.delete();

                    Thread.sleep(4000);

                    picturePlayer2.delete();
                    pictureRestart.draw();

                    break;
                case "1":
                    Picture picturePlayer1 = new Picture(PADDING, PADDING, "resources/player1winner.png");
                    picturePlayer1.draw();
                    canvas.delete();

                    Thread.sleep(4000);

                    picturePlayer1.delete();
                    pictureRestart.draw();

                    break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void restart() {
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {

                positions[i][j].deletePos();

                if (positions[i][j].isApple()) {
                    positions[i][j].deleteAp();
                }
            }

        }

        positions = null;
        playerSnakeList.clear();

        pictureRestart.delete();
        init();
    }

    @Override
    public String toString() {
        return "PlayerGrid{" +
                "positions=" + Arrays.toString(positions) +
                ", playerSnakeList=" + playerSnakeList +
                '}';
    }
}



