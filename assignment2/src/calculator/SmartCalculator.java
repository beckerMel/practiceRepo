package calculator;

/**
 * A class that extends the AbstractCalculator class, which implements the Calculator interface.
 * Provides functionality to simulate a smart calculator which only works with non-negative
 * numbers and can only add, subtract, or multiply them; the SmartCalculator can perform
 * calculations even when no second operand is provided, and repeat operations with multiple
 * inputs of the '=' sign.
 */
public class SmartCalculator extends AbstractCalculator {
  /**
   * Creates an instance of a SmartCalculator object for the user. This calculator will start
   * off completely empty, and calling getResult() on it will print an empty string.
   */
  public SmartCalculator() {
    super();
  }

  private SmartCalculator(int numOne, boolean firstEmpty, char operator, boolean operatorEmpty,
                          int numTwo, boolean secondEmpty, boolean result) {
    super(numOne, firstEmpty, operator, operatorEmpty, numTwo, secondEmpty, result);
  }

  @Override
  protected Calculator makeCalc(int numOne, boolean firstEmpty, char operator,
                                boolean operatorEmpty, int numTwo, boolean secondEmpty,
                                boolean result) {
    return new SmartCalculator(numOne, firstEmpty, operator, operatorEmpty, numTwo,
            secondEmpty, result);
  }

  /* The SmartCalculator accepts the '+' as the first input, any operator after the first/second
   * operands have been entered, and any operator immediately after another operator (the second
   * operator will replace the operator that was already in the calculator). */
  @Override
  protected Calculator handleOperator(char button) {
    if (result) {
      /* If the first operand is the result of a previous operation, inputting another operator
       * prepares SmartCalculator to make this result the first operand of the operation
       * specified by the operator. */
      return makeCalc(firstOp, LOADED, button, LOADED, 0, EMPTY, false);
    } else if (!secondOpIsEmpty) {
      /* If both operands are already loaded, perform the operation and load result into first
       * operand, with new operator in operator slot */
      return makeCalc(performOperation(firstOp, secondOp), LOADED, button, LOADED,
              '0', EMPTY, false);
    } else if (!firstOpIsEmpty) {
      return makeCalc(firstOp, LOADED, button, LOADED, 0, EMPTY, false);
    } else if (button == '+') {
      return this;
    }
    return null;
  }

  /* Multiple inputs of '=' to the SmartCalculator repeats the operation again, such that:
   * '7' + '8' '=' '=' '=' gives a result of 31. In addition, an '=' input when only one
   * operand and an operator (but no second operand) are in the calculator performs the
   * operation between the first operand and itself, such that '7' '+' '=' gives a result
   * of 14; if '=' is input again, we would get 21.
   *
   * Note: Because of this, we need to save the value of the first operand so we can re-use it
   * every time '=' is input. The calc stores the negative version of the number in the
   * second operand slot and marks it as empty; this saves the value while the calculator will
   * never print it, and the calculator can tell it apart since it's only suppsoed to work with
   * non-negative numbers. */
  @Override
  protected Calculator handleEquals(char button) {
    if ((firstOp == 0) && (secondOp == 0)) {
      /* any operation of zero unto itself is 0 */
      return makeCalc(0, LOADED, '0', EMPTY, 0, EMPTY, true);
    } else if (secondOp < 0) {
      /* check if there is an implied but missing second operand we should be using */
      return makeCalc(performOperation(firstOp, (secondOp * -1)), LOADED,
              operator, EMPTY, secondOp, EMPTY, true);
    } else if (!secondOpIsEmpty) {
      /* we have a second operand - perform the operation with the two operands, and save the
       * negative version of the second op so we secretly keep it if we need it again  */
      return makeCalc(performOperation(firstOp, secondOp), LOADED,
              operator, EMPTY, (secondOp * -1), EMPTY, true);
    } else if (!operatorIsEmpty) {
      /* we have no second operand - implies that 1st operand gets operation performed on itself. */
      return makeCalc(performOperation(firstOp, firstOp), LOADED,
              operator, EMPTY, (firstOp * -1), EMPTY, true);
    }
    return null;
  }
}