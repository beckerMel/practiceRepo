package calculator;

/**
 * This interface provides functionality to simulate a calculator. The interface allows users to
 * input numbers and operations one character at a time and display what would be on the current
 * "screen" of the calculator if it were real.
 */
public interface Calculator {
  /**
   * Provide a character to the calculator as input.
   * @param button the input to the calculator, representing a single button on a real calculator.
   * @return a calculator that has been updated based on the provided input to the original
   *     calculator.
   */
  Calculator input(char button);

  /**
   * Print the current "screen" of the calculator.
   * @return a string containing the contents of the calculator's display.
   */
  String getResult();
}