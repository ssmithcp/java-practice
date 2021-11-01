package com.shmit.tictactoe;

import java.util.Scanner;

public class TicTacToeMain {

  public static void main(String[] args) throws Exception {
    final Game game = new Game(Mark.X);

    try (Scanner scanner = new Scanner(System.in)) {
      while (!game.hasWinner() && !game.isStalemate()) {
        Mark currentPlayer = game.whoseTurn();

        System.out.print(game);
        System.out.print(String.format("Player %s, choose a square: ", currentPlayer));
        game.addMark(Integer.parseInt(scanner.nextLine()));
      }
    }

    System.out.print(game);
    if (game.isStalemate()) {
      System.out.println("No one won the game");
    }
    if (game.hasWinner()) {
      System.out.println(String.format("Player %s won the game", game.getWinner()));
    }

    System.out.println("Play again soon!");
  }
}
