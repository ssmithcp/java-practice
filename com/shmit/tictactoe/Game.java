package com.shmit.tictactoe;

import java.util.List;
import java.util.Objects;

public final class Game {

  private final StandardGameBoard board = new StandardGameBoard();

  private final Mark firstPlayer;

  public Game() {
    this(Mark.X);
  }

  public Game(Mark firstPlayer) {
    if (Objects.requireNonNull(firstPlayer) == Mark.Unmarked) {
      throw new IllegalStateException("First player must be X or O");
    }
    this.firstPlayer = Objects.requireNonNull(firstPlayer);
  }

  public Mark whoseTurn() {
    if (isStalemate()) {
      return null;
    }

    final int playsFromFirstPlayer =
      (int) board.getRawBoard()
        .stream()
        .filter(m -> m == firstPlayer)
        .count();

    final Mark secondPlayer = getSecondPlayer();
    final int playsFromSecondPlayer =
      (int) board.getRawBoard()
      .stream()
      .filter(m -> m == secondPlayer)
      .count();

    return playsFromFirstPlayer == playsFromSecondPlayer ? firstPlayer : secondPlayer;
  }

  private Mark getSecondPlayer() {
    return firstPlayer == Mark.X ? Mark.O : Mark.X;
  }

  public void addMark(int boardLocation) {
    Mark current = board.getMark(boardLocation);

    if (current != Mark.Unmarked) {
      throw new IllegalStateException(
        String.format("cannot play over already marked square %d", boardLocation)
      );
    }

    board.addMark(boardLocation, whoseTurn());
  }

  public boolean isStalemate() {
    return !hasWinner() && board.countUnmarked() == 0;
  }

  public boolean hasWinner() {
    return getWinner() != null;
  }

  public Mark getWinner() {
    if (checkWon(Mark.X)) {
      return Mark.X;
    }
    if (checkWon(Mark.O)) {
      return Mark.O;
    }

    return null;
  }

  private boolean checkWon(Mark player) {
    return rowWin(0, player)
      || rowWin(1, player)
      || rowWin(2, player)
      || columnWin(0, player)
      || columnWin(1, player)
      || columnWin(2, player)
      || backSlashDiagnolWin(player)
      || forwardSlashDiagnolWin(player);
  }

  private boolean rowWin(int row, Mark player) {
    final int rowOffset = board.getWidth() * row;
    final List<Mark> rawBoard = board.getRawBoard();

    return rawBoard.get(rowOffset) == player
      && rawBoard.get(rowOffset + 1) == player
      && rawBoard.get(rowOffset + 2) == player;
  }

  private boolean columnWin(int column, Mark player) {
    final int rowOffset = board.getWidth();
    final List<Mark> rawBoard = board.getRawBoard();

    return rawBoard.get(column) == player
      && rawBoard.get(column + rowOffset) == player
      && rawBoard.get(column + (rowOffset * 2)) == player;
  }

  private boolean backSlashDiagnolWin(Mark player) {
    final List<Mark> rawBoard = board.getRawBoard();
    return rawBoard.get(0) == player
      && rawBoard.get(4) == player
      && rawBoard.get(8) == player;
  }

  private boolean forwardSlashDiagnolWin(Mark player) {
    final List<Mark> rawBoard = board.getRawBoard();
    return rawBoard.get(2) == player
      && rawBoard.get(4) == player
      && rawBoard.get(6) == player;
  }

  @Override
  public String toString() {
    return board.toString();
  }
}
