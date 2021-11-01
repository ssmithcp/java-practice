package com.shmit.tictactoe;

import java.util.Objects;

public enum Mark {
  X("X"), O("O"), Unmarked(" ");

  private final String symbol;

  Mark(String symbol) {
    this.symbol = Objects.requireNonNull(symbol);
  }

  @Override
  public String toString() {
    return symbol;
  }
}
