package org.academiadecodigo.haltistas.AwesomeGame.player;


public class Decoder {

    private PlayerGrid playerGrid;

    Decoder(PlayerGrid playerGrid) {

        this.playerGrid = playerGrid;
    }


    public void decoding(String msg) {
        int snake;
        int row;
        int col;

        String[] words = msg.split("-");


        switch (words[0]) {

            case "start":
                playerGrid.start();
                break;
            case "move":
                snake = Integer.parseInt(words[1]);
                playerGrid.move(snake, words[2]);
                break;
            case "deletewall":
                playerGrid.deleteWall();
                break;
            case "deleteapple":
                row = Integer.parseInt(words[1]);
                col = Integer.parseInt(words[2]);
                playerGrid.deleteApple(row, col);
                break;
            case "green":
                row = Integer.parseInt(words[1]);
                col = Integer.parseInt(words[2]);
                playerGrid.greenApple(row, col);
                break;
            case "red":
                row = Integer.parseInt(words[1]);
                col = Integer.parseInt(words[2]);
                playerGrid.redApple(row, col);
                break;
            case "delete":
                snake = Integer.parseInt(words[1]);
                playerGrid.deleteSnake(snake);
                break;
            case "over":
                playerGrid.gameOver(words[1]);
                break;
            case "restart":
                playerGrid.restart();

        }
    }
}



