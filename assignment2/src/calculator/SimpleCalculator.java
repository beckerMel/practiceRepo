package calculator;

/**
 * A class that extends the AbstractCalculator class, which implements the Calculator interface.
 * Provides functionality to simulate a simple calculator which only works with non-negative
 * numbers and can only add, subtract, or multiply them; to perform operations, the
 * SimpleCalculator needs both operands explicitly specified, and multiple inputs of the '='
 * sign after an operation simply prints the same result.
 */
public class SimpleCalculator extends AbstractCalculator {

  /**
   * Construct a SimpleCalculator object. This calculator will start off completely empty, and
   * calling getResult() on it will print an empty string.
   */
  public SimpleCalculator() {
    super();
  }

  private SimpleCalculator(int numOne, boolean firstEmpty, char operator, boolean operatorEmpty,
                           int numTwo, boolean secondEmpty, boolean result) {
    super(numOne, firstEmpty, operator, operatorEmpty, numTwo, secondEmpty, result);
  }

  /* factory method for use by AbstractCalculator class */
  @Override
  protected Calculator makeCalc(int numOne, boolean firstEmpty, char operator,
                                boolean operatorEmpty, int numTwo, boolean secondEmpty,
                                boolean result) {
    return new SimpleCalculator(numOne, firstEmpty, operator, operatorEmpty, numTwo,
            secondEmpty, result);
  }

  /*
   * For the SimpleCalculator, continuing to enter '=' after performing an operation simply
   * reprints the same result. Entering '=' right after another '=' or when both operands are
   * explicitly supplied in the calculator are the only times the SimpleCalculator recognizes
   * '=' as a valid input.
   */
  @Override
  protected Calculator handleEquals(char button) {
    if (result) {
      /* An '=' after an operation in SimpleCalculator just prints the same result again */
      return this;
    } else if (!secondOpIsEmpty) {
      return makeCalc(performOperation(firstOp, secondOp), LOADED, '0',
              EMPTY, 0, EMPTY, true);
    }
    return null;
  }

  /*
   * For the simple calculator, operators can only be input after the first operand is input or
   * after the second operand is input; if input after the second operand, the calculator's
   * current operation will execute, its result will replace the first operand, and the
   * new operator will go in the operator spot.
   *
   * EX: '2' + '5' '-' --> "7-"
   */
  @Override
  protected Calculator handleOperator(char button) {
    if (!secondOpIsEmpty) {
      /* execute operation; this operator is now in the operator slot */
      return makeCalc(performOperation(firstOp, secondOp), LOADED, button, LOADED,
              0, EMPTY, false);
    } else if ((!firstOpIsEmpty) && (operatorIsEmpty)) {
      return makeCalc(firstOp, LOADED, button, LOADED, 0, EMPTY, false);
    }
    return null;
  }
}