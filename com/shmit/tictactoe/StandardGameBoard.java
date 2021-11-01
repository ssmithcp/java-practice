package com.shmit.tictactoe;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class StandardGameBoard {

  private final List<Mark> board =
    IntStream
      .range(0, 9)
      .mapToObj((ignored) -> Mark.Unmarked)
      .collect(Collectors.toList());

  public int getWidth() {
    return 3;
  }

  public int getHeight() {
    return 3;
  }

  public int countUnmarked() {
    // this count could also be maintained independently instead
    // calculating it each time
    return (int) board
      .stream()
      .filter((m) -> m == Mark.Unmarked)
      .count();
  }

  // exposing this data could be a mistake
  public List<Mark> getRawBoard() {
    return Collections.unmodifiableList(board);
  }

  public Mark getMark(int location) {
    checkLocation(location);
    return board.get(location - 1);
  }

  private static void checkLocation(int location) {
    if (location < 1 || location > 9) {
      throw new IllegalStateException("location must be between 1 and 9");
    }
  }

  public void addMark(int location, Mark mark) {
    checkLocation(location);
    board.set(location - 1, mark);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    for (int ctr = 0; ctr < board.size(); ++ctr) {
      Mark current = board.get(ctr);

      if (current != Mark.Unmarked) {
        sb.append(current.toString());
      } else {
        sb.append(ctr + 1);
      }

      if (ctr % getWidth() == getWidth() - 1) {
        sb.append(System.lineSeparator());
      }
    }

    return sb.toString();
  }
}
