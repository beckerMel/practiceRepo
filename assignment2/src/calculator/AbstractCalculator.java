package calculator;

/**
 * This abstract class is an implementation of the Calculator class and provides functionality for
 * calculators that work with non-negative numbers and can only add, multiply, or subtract with
 * them. It has separate functions for handling inputs that are numbers, operators, '=', and 'C'
 * (clear) so that classes that extend AbstractCalculator may overwrite some input categories
 * and use AbstractCalculator's implementation for others; note that implementations for handling
 * operators and '=' are not given, so all classes that extend AbstractCalculator must give their
 * own implementation for handling these inputs.
 */
abstract class AbstractCalculator implements Calculator {
  /* The first and second operands of the calculator at any given state, as well as the operator */
  protected final int firstOp;

  protected final int secondOp;

  protected final char operator;


  /* Whether the current number in the first operand position is the result of a prior operation */
  protected final boolean result;

  protected final int ZERO = 48;

  protected final int NINE = 57;

  /* EMPTY = the calculator won't print this value, even if it's actually storing it
     LOADED = the calculator will print this value if getResult() is called */
  protected final boolean EMPTY = true;

  protected final boolean LOADED = false;

  /* Variables for whether the calculator should treat each operand / the operator as
   * empty or not at print-time */
  protected final boolean firstOpIsEmpty;
  protected final boolean operatorIsEmpty;
  protected final boolean secondOpIsEmpty;

  /**
   * Create a new instance of an AbstractCalculator. At first, every slot in the calculator
   * (first operand, operator, second operand) is treated as empty and would not be printed,
   * their values are initialized to zero, and the "result" field is initialized to false since
   * whatever is in a brand-new calculator cannot be the result of a previous operation.
   */
  protected AbstractCalculator() {
    this.firstOp = 0;
    this.operator = '0';
    this.secondOp = 0;
    this.result = false;
    firstOpIsEmpty = true;
    operatorIsEmpty = true;
    secondOpIsEmpty = true;
  }

  /* Constructor only meant to be called from member methods; clients can only create empty
   * calculators, but this constructor allows member methods to create calculators that
   * begin with their fields holding any value  */
  protected AbstractCalculator(int numOne, boolean firstEmpty, char operator, boolean operatorEmpty,
                               int numTwo, boolean secondEmpty, boolean result) {
    this.firstOp = numOne;
    this.operator = operator;
    this.secondOp = numTwo;
    this.firstOpIsEmpty = firstEmpty;
    this.secondOpIsEmpty = secondEmpty;
    this.operatorIsEmpty = operatorEmpty;
    this.result = result;
  }

  /* factory method overridden by classes which extend AbstractCalculator */
  protected abstract Calculator makeCalc(int numOne, boolean firstEmpty, char operator,
                                         boolean operatorEmpty, int numTwo, boolean secondEmpty,
                                         boolean result);


  /* Function that takes an original number and "addition", a digit which it will effectively
   * append to the end of the original number, then returns the result. If the result exceeds
   * the maximum size of an integer, the function throws an IllegalArgumentException since the
   * user input too many digits into one of the calculator's operands.
   */
  protected int combineNums(int original, int addition) throws IllegalArgumentException {
    try {
      int newNum = Math.multiplyExact(original, 10);
      newNum = Math.addExact(newNum, addition);
      return newNum;
    } catch (ArithmeticException e) {
      throw new IllegalArgumentException("Operand is too large");
    }
  }

  /* Executes the operation specified by the operator currently loaded into the calculator, then
   * returns the result. */
  protected int performOperation(int operand1, int operand2) {
    int newNum = 0;
    try {
      switch (operator) {
        case '+':
          newNum = Math.addExact(operand1, operand2);
          break;
        case '-':
          newNum = Math.subtractExact(operand1, operand2);
          break;
        case '*':
          newNum = Math.multiplyExact(operand1, operand2);
          break;
        default:
          newNum = -1;
      }
    } catch (ArithmeticException e) {
      /* If operation exceeds max/min int values, set its result to 0 */
      newNum = 0;
    }
    return newNum;
  }

  /* Store a number in the calculator when one is input. */
  protected Calculator handleNumber(int button) {
    if (result || (firstOpIsEmpty)) {
      /* If the first operator is empty or the result of a previous operation and a number is
       * input, the entered number marks the start of a brand-new operation */
      return makeCalc(button, LOADED, '0', EMPTY, 0, EMPTY, false);
    } else {
      if (operatorIsEmpty) {
        /* digit is appended to end of first operand */
        return makeCalc(combineNums(firstOp, button), LOADED, '0', EMPTY,
                0, EMPTY, false);
      } else if (!secondOpIsEmpty) {
        /* digit is appended to end of second operand */
        return makeCalc(firstOp, LOADED, operator, LOADED,
                combineNums(secondOp, button), LOADED, false);
      } else {
        /* only remaining case - the number is first digit of second operand */
        return makeCalc(firstOp, LOADED, operator, LOADED, button, LOADED, false);
      }
    }
  }

  /* Calculators that extend this class give their own implementation for handling
  operator inputs */
  protected abstract Calculator handleOperator(char button);

  /* Calculators that extend this class give their own implementation for handling the '=' input */
  protected abstract Calculator handleEquals(char button);

  /* Return an empty calculator */
  protected Calculator handleClear() {
    return makeCalc(0, EMPTY, '0', EMPTY, 0, EMPTY, false);
  }

  /**
   * Decide whether user's input to calculator is a number, operator, clear command, or '=' and
   * create a new calculator with the correct updates. When entering in operands, note that an
   * exception is thrown if they exceed the maximum size of an integer (2147483647), and any time an
   * operation is executed whose result exceeds this number or goes lower than the minimum integer
   * value (-2147483648), the calculator acts as though the result was 0.
   *
   * @param button the input to the calculator, representing a single button on a real calculator.
   * @return a new calculator that has been updated based on the provided button input
   * @throws IllegalArgumentException when the inputted character is not a valid calculator button
   *         or is a valid calculator button that can't be input to the calculator in its current
   *         state - e.g. '-' is a valid calculator button, but an empty calculator may not consider
   *         it valid as the very first input.
   */
  public Calculator input(char button) throws IllegalArgumentException {
    Calculator newCalc = null;
    if ((button >= '0') && (button <= '9')) {
      int asNumber = Character.getNumericValue(button);
      newCalc = handleNumber(asNumber);
    } else if ((button == '+') || (button == '-') || (button == '*')) {
      newCalc = handleOperator(button);
    } else if (button == '=') {
      newCalc = handleEquals(button);
    } else if (button == 'C') {
      newCalc = handleClear();
    }

    /* if newCalc = null, it means that none of the above conditions were met OR one of them
     * was, but now is not a valid time to enter a button of that type  */
    if (newCalc == null) {
      throw new IllegalArgumentException("Invalid input '" + button + "' to calculator");
    }
    return newCalc;
  }

  /**
   * Print the current screen of the calculator. If the calculator is currently empty, print
   * nothing.
   *
   * @return A string containing the contents of the calculator's screen - this could be anywhere
   *     from the empty string (for an empty calculator) to a single operand, to an operand and
   *     an operator, to two operands with an operator in between.
   */
  public String getResult() {
    String screen = "";

    if (firstOpIsEmpty) {
      return screen;
    }
    screen += firstOp;

    if (!operatorIsEmpty) {
      screen += operator;
      if (!secondOpIsEmpty) {
        screen += secondOp;
      }
    }
    return screen;
  }
}