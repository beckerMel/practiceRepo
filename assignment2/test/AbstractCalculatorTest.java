import org.junit.Before;
import org.junit.Test;

import calculator.Calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * An abstract test class that provides common tests that may be useful for many concrete
 * classes that correspond to the Calculator interface.
 */
abstract class AbstractCalculatorTest {
  protected final int ZERO = 48;

  protected final int SEVEN = 55;

  protected final int NINE = 57;

  /* A simple calculator with one operand loaded on already - whether this works correctly is
   * tested in firstOperandMultiDigits() */
  protected Calculator oneOperand;

  /* A simple calculator with two operands and an operator loaded on already - whether this works
   * correctly is tested in secondOperandMultiDigits() */
  protected Calculator twoOperand;

  /* The first number of oneOperand */
  protected String expectedOutput;

  /* The first and second numbers of twoOperand */
  protected String firstNum;

  protected String secondNum;

  Calculator myCalc;

  @Before
  public void setup() {
    myCalc = makeTestCalc();
  }

  protected abstract Calculator makeTestCalc();

  protected void initializeOneOp() {
    expectedOutput = "";
    oneOperand = makeTestCalc();
    int operandSize = randomNumber(9, 1);
    for (int i = 0; i < operandSize; i++) {
      int digit = randomNumber(NINE, ZERO);
      oneOperand = oneOperand.input((char) digit);
      expectedOutput += (char) digit;
    }
    if (Integer.parseInt(expectedOutput) == 0) {
      /* prevents number from being "00" or "000" or etc. */
      expectedOutput = "0";
    }
    if (!oneOperand.getResult().equals("0")) {
      expectedOutput = expectedOutput.replaceFirst("^0*", "");
    }
    /* Some 10-digit operands are small enough; this insures they can be entered as the operand */
    if ((operandSize == 9) && (Integer.parseInt(expectedOutput) < 214748365)) {
      int add10th = randomNumber(1, 0);
      if (add10th == 1) {
        int digit = randomNumber(SEVEN, ZERO);
        oneOperand = oneOperand.input((char) digit);
        expectedOutput += (char) digit;
      }
    }
  }

  protected void initializeTwoOp(char operator) {
    initializeOneOp();
    twoOperand = oneOperand.input(operator);
    firstNum = expectedOutput;
    secondNum = "";
    int operandSize = randomNumber(9, 1);
    for (int i = 0; i < operandSize; i++) {
      int digit = randomNumber(NINE, ZERO);
      twoOperand = twoOperand.input((char) digit);
      secondNum += (char) digit;
    }
    String secondOpEmpty = firstNum + operator;
    if (Integer.parseInt(secondNum) == 0) {
      secondNum = "0";
    }
    if (!twoOperand.getResult().equals(secondOpEmpty + "0")) {
      secondNum = secondNum.replaceFirst("^0*", "");
    }
    if ((operandSize == 9) && (Integer.parseInt(secondNum) < 214748365)) {
      int add10th = randomNumber(1, 0);
      if (add10th == 1) {
        int digit = randomNumber(SEVEN, ZERO);
        twoOperand = twoOperand.input((char) digit);
        secondNum += (char) digit;
      }
    }
  }

  /* Meant to test whether calculator legally accepts numbers as inputs */
  protected void inputNumbers(Calculator c) {
    for (int i = 48; i < 58; i++) {
      c.input((char) i);
    }
  }

  /* for style reasons - put in catch blocks that would otherwise be empty, since they
   * only exist to make sure the exception doesn't end the program */
  protected void doNothing() {
    return;
  }

  protected void inputOperators(Calculator c, boolean allowed) {
    if (allowed) {
      c.input('+');
      c.input('-');
      c.input('*');
    } else {
      try {
        c.input('+');
        fail("'+' could be entered by shouldn't be allowed");
      }
      catch (IllegalArgumentException e) {
        doNothing();
      }
      try {
        c.input('-');
        fail("'-' could be entered by shouldn't be allowed");
      }
      catch (IllegalArgumentException e) {
        doNothing();
      }
      try {
        c.input('*');
        fail("'+' could be entered by shouldn't be allowed");
      }
      catch (IllegalArgumentException e) {
        doNothing();
      }
    }
  }

  protected void inputEquals(Calculator c, boolean allowed) {
    if (allowed) {
      c.input('=');
    } else {
      try {
        c.input('=');
        fail("equals can be input here but shouldn't be allowed");
      } catch (IllegalArgumentException e) {
        doNothing();
      }
    }
  }

  protected void inputClear(Calculator c) {
    c.input('C');
  }

  /* inputs every invalid character */
  protected void inputInvalidChars(Calculator c) {
    for (int i = 0; i < 42; i++) {
      try {
        c.input((char) i);
        fail("didn't catch invalid input " + i);
      } catch (IllegalArgumentException e) {
        continue;
      }
    }

    try {
      c.input((char) 44);
      fail("didn't catch invalid input " + (char) 44);
    } catch (IllegalArgumentException e) {
      doNothing();
    }

    for (int i = 46; i < 48; i++) {
      try {
        c.input((char) i);
        fail("didn't catch invalid input " + i);
      } catch (IllegalArgumentException e) {
        continue;
      }
    }

    for (int i = 58; i < 61; i++) {
      try {
        c.input((char) i);
        fail("didn't catch invalid input " + i);
      } catch (IllegalArgumentException e) {
        continue;
      }
    }

    for (int i = 62; i < 67; i++) {
      try {
        c.input((char) i);
        fail("didn't catch invalid input " + i);
      } catch (IllegalArgumentException e) {
        continue;
      }
    }

    for (int i = 68; i < 128; i++) {
      try {
        c.input((char) i);
        fail("didn't catch invalid input " + i);
      } catch (IllegalArgumentException e) {
        continue;
      }
    }
  }

  /* Tests accept/reject behavior for inputs other than numbers for a calculator
 that has both a first and second operand; doesn't test numbers as inputs because those
 would just be part of the second operand as well */
  @Test
  public void testInputsAfterOp2() {
    boolean success = true;
    initializeTwoOp('+');
    inputClear(twoOperand);
    inputEquals(twoOperand, true);
    inputOperators(twoOperand, true);
    inputInvalidChars(twoOperand);
    assertEquals(success, true);
  }

  @Test
  public void testInputsAfterPerformingPlus() {
    boolean success = true;
    initializeTwoOp('+');
    twoOperand = twoOperand.input('=');
    inputNumbers(twoOperand);
    inputClear(twoOperand);
    inputEquals(twoOperand, true);
    inputOperators(twoOperand, true);
    inputInvalidChars(twoOperand);
    assertEquals(success, true);
  }

  @Test
  public void testInputsAfterPerformingMinus() {
    boolean success = true;
    initializeTwoOp('-');
    twoOperand = twoOperand.input('=');
    inputNumbers(twoOperand);
    inputClear(twoOperand);
    inputEquals(twoOperand, true);
    inputOperators(twoOperand, true);
    inputInvalidChars(twoOperand);
    assertEquals(success, true);
  }

  @Test
  public void testInputsAfterPerformingTimes() {
    boolean success = true;
    initializeTwoOp('*');
    twoOperand = twoOperand.input('=');
    inputNumbers(twoOperand);
    inputClear(twoOperand);
    inputEquals(twoOperand, true);
    inputOperators(twoOperand, true);
    inputInvalidChars(twoOperand);
    assertEquals(success, true);
  }

  /* Tests for correct input accept/reject behavior after one operand other than
 numbers, which would just also be part of the operand */
  @Test
  public void testInputsAfterOp1() {
    boolean success = true;
    initializeOneOp();
    inputClear(oneOperand);
    inputEquals(oneOperand, false);
    inputOperators(oneOperand, true);
    inputInvalidChars(oneOperand);
    assertEquals(success, true);
  }

  protected int randomNumber(int maxVal, int minVal) {
    int range = maxVal - minVal + 1;
    return (int) Math.floor((Math.random() * range) + minVal);
  }

  /* Addition that behaves like the calculator is supposed to - if the operation produces a
   * result that will overflow, simply treat the result like it's 0 */
  protected int addLikeCalc(int num1, int num2) {
    try {
      return Math.addExact(num1, num2);
    } catch (ArithmeticException e) {
      return 0;
    }
  }

  protected int multiplyLikeCalc(int num1, int num2) {
    try {
      return Math.multiplyExact(num1, num2);
    } catch (ArithmeticException e) {
      return 0;
    }
  }

  protected int subtractLikeCalc(int num1, int num2) {
    try {
      return Math.subtractExact(num1, num2);
    } catch (ArithmeticException e) {
      return 0;
    }
  }

  @Test
  public void newCalcIsEmpty() {
    Calculator newCalc = makeTestCalc();
    assertEquals("", newCalc.getResult());
  }

  @Test
  public void canClearEmptyCalc() {
    try {
      myCalc.input('C');
    } catch (IllegalArgumentException e) {
      fail("Clearing threw exception when it shouldn't");
    }
  }

  @Test
  public void clearedEmptyCalcIsStillEmpty() {
    myCalc.input('C');
    assertEquals("", myCalc.getResult());
  }

  @Test
  public void addSingleDigitToEmptyCalc() {
    int digit = randomNumber(NINE, ZERO);
    myCalc = myCalc.input((char) digit);
    String expected = String.valueOf((char) digit);
    assertEquals(expected, myCalc.getResult());
  }

  @Test
  public void clearWorksOnCalcWithOneNumber() {
    int digit = randomNumber(NINE, ZERO);
    myCalc = myCalc.input((char) digit);
    myCalc = myCalc.input('C');
    assertEquals("", myCalc.getResult());
  }

  /* Ensures that after adding a single digit to a new calculator from an empty one, the old one
   * is still empty*/
  @Test
  public void addSingleDigitToNewCalcFirstStillEmpty() {
    int digit = randomNumber(NINE, ZERO);
    Calculator newCalc = myCalc.input((char) digit);
    assertEquals("", myCalc.getResult());
  }

  /* Tests whether a random number from digit sizes 0 - 10 can be made the first operand of
   * the calculator */
  @Test
  public void firstOperandMultiDigits() {
    initializeOneOp();
    assertEquals(expectedOutput, oneOperand.getResult());
  }

  @Test
  public void clearWorksOnCalcWithOneMultiDigitOperand() {
    initializeOneOp();
    oneOperand = oneOperand.input('C');
    assertEquals("", oneOperand.getResult());
  }

  @Test
  public void clearWithOneMultiDigitOperandOnNewCalcDoesntChangeOldCalc() {
    initializeOneOp();
    Calculator newCalc = oneOperand.input('C');
    /* the above should not have actually cleared oneOperand */
    assertEquals(expectedOutput, oneOperand.getResult());
  }

  /* Ensures that all inputs other than another number, 'C', or an operator are considered illegal
   * after the first operand is loaded into the calculator */
  @Test
  public void illegalInputsAfterNumber() {
    initializeOneOp();
    for (int i = 0; i < 42; i++) {
      try {
        oneOperand.input((char) i);
        fail(i + " failed to throw an exception as input after first operand");
      } catch (IllegalArgumentException e) {
        continue;
      }
    }

    try {
      oneOperand.input((char) 44);
      fail((char) 44 + " failed to throw an exception as input after first operand");
    } catch (IllegalArgumentException e) {
      doNothing();
    }


    for (int i = 46; i < 48; i++) {
      try {
        oneOperand.input((char) i);
        fail(i + " failed to throw an exception as input after first operand");
      } catch (IllegalArgumentException e) {
        continue;
      }
    }

    for (int i = 58; i < 66; i++) {
      try {
        oneOperand.input((char) i);
        fail(i + " failed to throw an exception as first input to calc");
      } catch (IllegalArgumentException e) {
        continue;
      }
    }

    for (int i = 68; i < 128; i++) {
      try {
        oneOperand.input((char) i);
        fail(i + " failed to throw an exception as first input to calc");
      } catch (IllegalArgumentException e) {
        continue;
      }
    }
  }

  @Test
  public void plusAfterOperandIsAllowed() {
    initializeOneOp();
    oneOperand = oneOperand.input('+');
    expectedOutput += '+';
    assertEquals(expectedOutput, oneOperand.getResult());
  }

  @Test
  public void addingPlusToNewCalcDoesntChangeOriginal() {
    initializeOneOp();
    Calculator newCalc = oneOperand.input('+');
    /* expected output = only the first operand as a string without a plus */
    assertEquals(expectedOutput, oneOperand.getResult());
  }

  @Test
  public void clearWorksOnCalcWithOneOperandAndPlus() {
    initializeOneOp();
    oneOperand = oneOperand.input('+');
    oneOperand = oneOperand.input('C');
    assertEquals("", oneOperand.getResult());
  }


  @Test
  public void minusAfterOperandIsAllowed() {
    initializeOneOp();
    oneOperand = oneOperand.input('-');
    expectedOutput += '-';
    assertEquals(expectedOutput, oneOperand.getResult());
  }

  @Test
  public void addingMinusToNewCalcDoesntChangeOriginal() {
    initializeOneOp();
    Calculator newCalc = oneOperand.input('-');
    /* expected output = only the first operand as a string without a minus */
    assertEquals(expectedOutput, oneOperand.getResult());
  }

  @Test
  public void clearWorksOnCalcWithOneOperandAndMinus() {
    initializeOneOp();
    oneOperand = oneOperand.input('-');
    oneOperand = oneOperand.input('C');
    assertEquals("", oneOperand.getResult());
  }

  @Test
  public void timesAfterOperandIsAllowed() {
    initializeOneOp();
    oneOperand = oneOperand.input('*');
    expectedOutput += '*';
    assertEquals(expectedOutput, oneOperand.getResult());
  }

  @Test
  public void clearWorksOnCalcWithOneOperandAndTimes() {
    initializeOneOp();
    oneOperand = oneOperand.input('*');
    oneOperand = oneOperand.input('C');
    assertEquals("", oneOperand.getResult());
  }

  @Test
  public void addingTimesToNewCalcDoesntChangeOriginal() {
    initializeOneOp();
    Calculator newCalc = oneOperand.input('*');
    /* expected output = only the first operand as a string without a '*' */
    assertEquals(expectedOutput, oneOperand.getResult());
  }


  /* ensures that clear works after the entering of one multi-digit operand */
  @Test
  public void firstOperandMultiDigitsClearWorks() {
    initializeOneOp();
    oneOperand = oneOperand.input('C');
    assertEquals("", oneOperand.getResult());
  }

  /* ensures that inputting the digits of a multi-digit operand into a new calculator
   * leaves the original calculator object unchanged  */
  @Test
  public void firstOperandMultiDigitsNewCalcDoesntReset() {
    int operandSize = randomNumber(9, 1);
    Calculator newCalc;
    for (int i = 0; i < operandSize; i++) {
      int digit = randomNumber(NINE, ZERO);
      newCalc = myCalc.input((char) digit);
    }
    assertEquals("", myCalc.getResult());
  }

  @Test
  public void twoOpsWithPlusPrintsCorrectly() {
    initializeTwoOp('+');
    String expected = firstNum + "+" + secondNum;
    assertEquals(expected, twoOperand.getResult());
  }

  @Test
  public void clearWorksTwoOpsWithPlus() {
    initializeTwoOp('+');
    twoOperand = twoOperand.input('C');
    assertEquals("", twoOperand.getResult());
  }

  @Test
  public void clearWorksTwoOpsWithMinus() {
    initializeTwoOp('-');
    twoOperand = twoOperand.input('C');
    assertEquals("", twoOperand.getResult());
  }

  @Test
  public void clearWorksTwoOpsWithTimes() {
    initializeTwoOp('*');
    twoOperand = twoOperand.input('C');
    assertEquals("", twoOperand.getResult());
  }

  @Test
  public void secondOperandDoesntChangeOriginalCalc() {
    initializeOneOp();
    oneOperand = oneOperand.input('+');
    expectedOutput += '+';
    Calculator myCalc = oneOperand.input((char) randomNumber(NINE, ZERO));
    assertEquals(expectedOutput, oneOperand.getResult());
  }

  @Test
  public void twoOpsWithMinusPrintsCorrectly() {
    initializeTwoOp('-');
    String expected = firstNum + "-" + secondNum;
    assertEquals(expected, twoOperand.getResult());
  }

  @Test
  public void twoOpsWithTimesPrintsCorrectly() {
    initializeTwoOp('*');
    String expected = firstNum + "*" + secondNum;
    assertEquals(expected, twoOperand.getResult());
  }

  @Test
  public void performOperationPlus() {
    initializeTwoOp('+');
    int result = addLikeCalc(Integer.parseInt(firstNum), Integer.parseInt(secondNum));
    twoOperand = twoOperand.input('=');
    assertEquals(String.valueOf(result), twoOperand.getResult());
  }

  /* Inputting '=' and setting the result = to a new calculator shouldn't change original */
  @Test
  public void performOperationPlusOriginalDoesntChange() {
    initializeTwoOp('+');
    Calculator myCalc = twoOperand.input('=');
    assertEquals(firstNum + "+" + secondNum, twoOperand.getResult());
  }

  /* Inputting '=' and setting the result = to a new calculator shouldn't change original */
  @Test
  public void performOperationMinusOriginalDoesntChange() {
    initializeTwoOp('-');
    Calculator myCalc = twoOperand.input('=');
    assertEquals(firstNum + "-" + secondNum, twoOperand.getResult());
  }

  /* Inputting '=' and setting the result = to a new calculator shouldn't change original */
  @Test
  public void performOperationTimesOriginalDoesntChange() {
    initializeTwoOp('*');
    Calculator myCalc = twoOperand.input('=');
    assertEquals(firstNum + "*" + secondNum, twoOperand.getResult());
  }

  @Test
  public void clearWorksAfterPlusOp() {
    initializeTwoOp('+');
    twoOperand = twoOperand.input('=');
    twoOperand = twoOperand.input('C');
    assertEquals("", twoOperand.getResult());
  }

  @Test
  public void clearWorksAfterMinusOp() {
    initializeTwoOp('-');
    twoOperand = twoOperand.input('=');
    twoOperand = twoOperand.input('C');
    assertEquals("", twoOperand.getResult());
  }

  @Test
  public void clearWorksAfterTimesOp() {
    initializeTwoOp('*');
    twoOperand = twoOperand.input('=');
    twoOperand = twoOperand.input('C');
    assertEquals("", twoOperand.getResult());
  }

  /* ensures the calculator can print two operands  */
  @Test
  public void performOperationMinus() {
    initializeTwoOp('-');
    int result = subtractLikeCalc(Integer.parseInt(firstNum), Integer.parseInt(secondNum));
    twoOperand = twoOperand.input('=');
    assertEquals(String.valueOf(result), twoOperand.getResult());
  }

  @Test
  public void performOperationTimes() {
    initializeTwoOp('*');
    int result = multiplyLikeCalc(Integer.parseInt(firstNum), Integer.parseInt(secondNum));
    twoOperand = twoOperand.input('=');
    assertEquals(String.valueOf(result), twoOperand.getResult());
  }

  @Test
  public void negativeResultPrints() {
    myCalc = myCalc.input('9');
    myCalc = myCalc.input('-');
    myCalc = myCalc.input('1');
    myCalc = myCalc.input('0');
    myCalc = myCalc.input('=');
    assertEquals("-1", myCalc.getResult());
  }

  @Test
  public void multiDigitNegativeResultPrints() {
    myCalc = myCalc.input('5');
    myCalc = myCalc.input('1');
    myCalc = myCalc.input('-');
    myCalc = myCalc.input('6');
    myCalc = myCalc.input('7');
    myCalc = myCalc.input('=');
    assertEquals("-16", myCalc.getResult());
  }

  @Test
  public void clearWorksAfterNegativeResult() {
    myCalc = myCalc.input('5');
    myCalc = myCalc.input('1');
    myCalc = myCalc.input('-');
    myCalc = myCalc.input('6');
    myCalc = myCalc.input('7');
    myCalc = myCalc.input('=');
    myCalc = myCalc.input('C');
    assertEquals("", myCalc.getResult());
  }

  @Test
  public void secondPlusUpdatesResult() {
    initializeTwoOp('+');
    twoOperand = twoOperand.input('+');
    int sum = addLikeCalc(Integer.parseInt(firstNum), Integer.parseInt(secondNum));
    assertEquals(sum + "+", twoOperand.getResult());
  }

  /* Ensures that adding many numbers before pressing '=' continually updates the first
   * operand in the calculator */
  @Test
  public void nthPlusUpdatesResult() {
    initializeTwoOp('+');
    int sum = addLikeCalc(Integer.parseInt(firstNum), Integer.parseInt(secondNum));
    twoOperand = twoOperand.input('+');
    int n = randomNumber(100000, 1);
    for (int i = 0; i < n; i++) {
      secondNum = "";
      int operandSize = randomNumber(9, 1);
      for (int j = 0; j < operandSize; j++) {
        int digit = randomNumber(NINE, ZERO);
        twoOperand = twoOperand.input((char) digit);
        secondNum += (char) digit;
      }

      if ("".equals(twoOperand.getResult())) {
        twoOperand = twoOperand.input('1');
        secondNum += "1";
      } else if (!twoOperand.getResult().equals("0")) {
        secondNum.replaceFirst("^0*", "");
      }

      twoOperand = twoOperand.input('+');
      sum = addLikeCalc(sum, Integer.parseInt(secondNum));
    }
    assertEquals(sum + "+", twoOperand.getResult());
  }

  @Test
  public void clearWorksAfterNthPlus() {
    initializeTwoOp('+');
    twoOperand = twoOperand.input('+');
    int n = randomNumber(1000000, 1);
    for (int i = 0; i < n; i++) {
      int operandSize = randomNumber(9, 1);
      for (int j = 0; j < operandSize; j++) {
        int digit = randomNumber(NINE, ZERO);
        twoOperand = twoOperand.input((char) digit);
      }

      twoOperand = twoOperand.input('+');
    }
    twoOperand = twoOperand.input('C');
    assertEquals("", twoOperand.getResult());
  }

  @Test
  public void secondMinusUpdatesResult() {
    initializeTwoOp('-');
    twoOperand = twoOperand.input('-');
    int diff = subtractLikeCalc(Integer.parseInt(firstNum), Integer.parseInt(secondNum));
    assertEquals(diff + "-", twoOperand.getResult());
  }

  @Test
  public void nthMinusUpdatesResult() {
    initializeTwoOp('-');
    int diff = subtractLikeCalc(Integer.parseInt(firstNum), Integer.parseInt(secondNum));
    twoOperand = twoOperand.input('-');
    int n = randomNumber(100000, 1);
    for (int i = 0; i < n; i++) {
      secondNum = "";
      int operandSize = randomNumber(9, 1);
      for (int j = 0; j < operandSize; j++) {
        int digit = randomNumber(NINE, ZERO);
        twoOperand = twoOperand.input((char) digit);
        secondNum += (char) digit;
      }

      if ("".equals(twoOperand.getResult())) {
        twoOperand = twoOperand.input('1');
        secondNum += "1";
      } else if (!twoOperand.getResult().equals("0")) {
        secondNum.replaceFirst("^0*", "");
      }

      twoOperand = twoOperand.input('-');
      diff = subtractLikeCalc(diff, Integer.parseInt(secondNum));
    }
    assertEquals(diff + "-", twoOperand.getResult());
  }

  @Test
  public void clearWorksNthMinus() {
    initializeTwoOp('-');
    twoOperand = twoOperand.input('-');
    int n = randomNumber(100000, 1);
    for (int i = 0; i < n; i++) {
      int operandSize = randomNumber(9, 1);
      for (int j = 0; j < operandSize; j++) {
        int digit = randomNumber(NINE, ZERO);
        twoOperand = twoOperand.input((char) digit);
      }
      twoOperand = twoOperand.input('-');
    }
    twoOperand = twoOperand.input('C');
    assertEquals("", twoOperand.getResult());
  }

  @Test
  public void secondTimesUpdatesResult() {
    initializeTwoOp('*');
    twoOperand = twoOperand.input('*');
    int quot = multiplyLikeCalc(Integer.parseInt(firstNum), Integer.parseInt(secondNum));
    assertEquals(quot + "*", twoOperand.getResult());
  }

  @Test
  public void nthTimesUpdatesResult() {
    initializeTwoOp('*');
    int quot = multiplyLikeCalc(Integer.parseInt(firstNum), Integer.parseInt(secondNum));
    twoOperand = twoOperand.input('*');
    int n = randomNumber(100000, 1);
    for (int i = 0; i < n; i++) {
      secondNum = "";
      int operandSize = randomNumber(9, 1);
      for (int j = 0; j < operandSize; j++) {
        int digit = randomNumber(NINE, ZERO);
        twoOperand = twoOperand.input((char) digit);
        if (secondNum.equals("0")) {
          secondNum = Character.toString((char) digit);
          continue;
        }
        secondNum += (char) digit;
      }
      twoOperand = twoOperand.input('*');
      quot = multiplyLikeCalc(quot, Integer.parseInt(secondNum));
    }
    assertEquals(quot + "*", twoOperand.getResult());
  }

  @Test
  public void clearWorksAfterNthTimes() {
    initializeTwoOp('*');
    twoOperand = twoOperand.input('*');
    int n = randomNumber(100000, 1);
    for (int i = 0; i < n; i++) {
      int operandSize = randomNumber(9, 1);
      for (int j = 0; j < operandSize; j++) {
        int digit = randomNumber(NINE, ZERO);
        twoOperand = twoOperand.input((char) digit);
      }
      twoOperand = twoOperand.input('*');
    }
    twoOperand = twoOperand.input('C');
    assertEquals("", twoOperand.getResult());
  }

  /* Performs many plus, minus, and times operations in succession with no '=' between
   * operations */
  @Test
  public void performManyOpsNoEquals() {
    myCalc = myCalc.input('1');
    myCalc = myCalc.input('7');
    int total = 17;
    int n = randomNumber(100000, 1);
    for (int i = 0; i < n; i++) {
      myCalc = myCalc.input('+');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('6');
      myCalc = myCalc.input('5');
      total = addLikeCalc(total, 365);
      myCalc = myCalc.input('-');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('8');
      myCalc = myCalc.input('2');
      total = subtractLikeCalc(total, 382);
      myCalc = myCalc.input('*');
      myCalc = myCalc.input('1');
      myCalc = myCalc.input('1');
      total = multiplyLikeCalc(total, 11);
    }
    myCalc = myCalc.input('=');
    assertEquals(String.valueOf(total), myCalc.getResult());
  }

  /* Performs many plus, minus, and times operations in succession with no '=' between
   * operations */
  @Test
  public void clearWorksAfterMultiOps() {
    myCalc = myCalc.input('1');
    myCalc = myCalc.input('7');
    int total = 17;
    int n = randomNumber(100000, 1);
    for (int i = 0; i < n; i++) {
      myCalc = myCalc.input('+');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('6');
      myCalc = myCalc.input('5');
      total = addLikeCalc(total, 365);
      myCalc = myCalc.input('-');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('8');
      myCalc = myCalc.input('2');
      total = subtractLikeCalc(total, 382);
      myCalc = myCalc.input('*');
      myCalc = myCalc.input('1');
      myCalc = myCalc.input('1');
      total = multiplyLikeCalc(total, 11);
    }
    myCalc = myCalc.input('=');
    myCalc = myCalc.input('C');
    assertEquals("", myCalc.getResult());
  }

  @Test
  public void performManyOpsWithEquals() {
    myCalc = myCalc.input('1');
    myCalc = myCalc.input('7');
    int total = 17;
    int n = randomNumber(100000, 1);
    for (int i = 0; i < n; i++) {
      myCalc = myCalc.input('+');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('6');
      myCalc = myCalc.input('5');
      myCalc = myCalc.input('=');
      total = addLikeCalc(total, 365);
      myCalc = myCalc.input('-');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('8');
      myCalc = myCalc.input('2');
      myCalc = myCalc.input('=');
      total = subtractLikeCalc(total, 382);
      myCalc = myCalc.input('*');
      myCalc = myCalc.input('1');
      myCalc = myCalc.input('1');
      myCalc = myCalc.input('=');
      total = multiplyLikeCalc(total, 11);
    }
    assertEquals(String.valueOf(total), myCalc.getResult());
  }

  @Test(expected = IllegalArgumentException.class)
  public void operand1CantOverflowEndWith8() {
    myCalc = myCalc.input('2');
    myCalc = myCalc.input('1');
    myCalc = myCalc.input('4');
    myCalc = myCalc.input('7');
    myCalc = myCalc.input('4');
    myCalc = myCalc.input('8');
    myCalc = myCalc.input('3');
    myCalc = myCalc.input('6');
    myCalc = myCalc.input('4');
    myCalc = myCalc.input('8');
  }

  @Test(expected = IllegalArgumentException.class)
  public void operand2CantOverflowEndWith8() {
    initializeOneOp();
    int operator = randomNumber(2, 0);
    switch (operator) {
      case 0:
        oneOperand = oneOperand.input('+');
        break;
      case 1:
        oneOperand = oneOperand.input('-');
        break;
      case 2:
        oneOperand = oneOperand.input('*');
        break;
      default:
        oneOperand = oneOperand.input('0');
    }
    oneOperand = oneOperand.input('1');
    oneOperand = oneOperand.input('2');
    oneOperand = oneOperand.input('1');
    oneOperand = oneOperand.input('4');
    oneOperand = oneOperand.input('7');
    oneOperand = oneOperand.input('4');
    oneOperand = oneOperand.input('8');
    oneOperand = oneOperand.input('3');
    oneOperand = oneOperand.input('6');
    oneOperand = oneOperand.input('4');
    oneOperand = oneOperand.input('8');
  }

  @Test(expected = IllegalArgumentException.class)
  public void operand1CantOverflowEndWith9() {
    myCalc = myCalc.input('2');
    myCalc = myCalc.input('1');
    myCalc = myCalc.input('4');
    myCalc = myCalc.input('7');
    myCalc = myCalc.input('4');
    myCalc = myCalc.input('8');
    myCalc = myCalc.input('3');
    myCalc = myCalc.input('6');
    myCalc = myCalc.input('4');
    myCalc = myCalc.input('9');
  }

  @Test(expected = IllegalArgumentException.class)
  public void operand2CantOverflowEndWith9() {
    initializeOneOp();
    int operator = randomNumber(2, 0);
    switch (operator) {
      case 0:
        oneOperand = oneOperand.input('+');
        break;
      case 1:
        oneOperand = oneOperand.input('-');
        break;
      case 2:
        oneOperand = oneOperand.input('*');
        break;
      default:
        oneOperand = oneOperand.input('0');
    }
    oneOperand = oneOperand.input('2');
    oneOperand = oneOperand.input('1');
    oneOperand = oneOperand.input('4');
    oneOperand = oneOperand.input('7');
    oneOperand = oneOperand.input('4');
    oneOperand = oneOperand.input('8');
    oneOperand = oneOperand.input('3');
    oneOperand = oneOperand.input('6');
    oneOperand = oneOperand.input('4');
    oneOperand = oneOperand.input('9');
  }

  @Test
  public void all10DigitNumsOverflowFirstOperand() {
    int digit = ZERO;
    /* make sure first digit isn't 0 so number is actually 10 digits */
    while (digit == ZERO) {
      digit = randomNumber(NINE, ZERO);
    }
    myCalc = myCalc.input((char) digit);
    for (int i = 0; i < 9; i++) {
      digit = (char) i;
      try {
        myCalc = myCalc.input((char) digit);
        fail("Did not throw exception");
      } catch (IllegalArgumentException e) {
        continue;
      }
    }
  }

  @Test
  public void all10DigitNumsOverflowSecondOperand() {
    initializeOneOp();
    int operator = randomNumber(2, 0);
    switch (operator) {
      case 0:
        oneOperand = oneOperand.input('+');
        break;
      case 1:
        oneOperand = oneOperand.input('-');
        break;
      case 2:
        oneOperand = oneOperand.input('*');
        break;
      default:
        oneOperand = oneOperand.input('0');
        break;
    }
    int digit = ZERO;
    /* make sure first digit isn't 0 so number is actually 10 digits */
    while (digit == ZERO) {
      digit = randomNumber(NINE, ZERO);
    }
    myCalc = myCalc.input((char) digit);
    for (int i = 0; i < 9; i++) {
      digit = (char) i;
      try {
        myCalc = myCalc.input((char) digit);
        fail("Did not throw exception");
      } catch (IllegalArgumentException e) {
        continue;
      }
    }
  }

  /* ensures that a result that reaches the negative operand value returns to 0 */
  @Test
  public void negOverflowZerosOutSubtraction() {
    myCalc = myCalc.input('0');
    for (int i = 0; i < 6487866; i++) {
      myCalc = myCalc.input('-');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('1');
    }
    myCalc = myCalc.input('-');
    myCalc = myCalc.input('3');
    myCalc = myCalc.input('=');
    assertEquals("0", myCalc.getResult());
  }

  @Test
  public void posOverflowZerosOutAddition() {
    myCalc = myCalc.input('0');
    for (int i = 0; i < 6487866; i++) {
      myCalc = myCalc.input('+');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('1');
    }
    myCalc = myCalc.input('+');
    myCalc = myCalc.input('2');
    myCalc = myCalc.input('=');
    assertEquals("0", myCalc.getResult());
  }

  @Test
  public void posAdditionOverflowZerosOutWithManyOpsBeforeHand() {
    myCalc = myCalc.input('1');
    myCalc = myCalc.input('2');
    int n = randomNumber(3, 1);
    for (int i = 0; i < n; i++) {
      myCalc = myCalc.input('+');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('9');
      myCalc = myCalc.input('6');
      myCalc = myCalc.input('=');
      myCalc = myCalc.input('-');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('9');
      myCalc = myCalc.input('7');
      myCalc = myCalc.input('=');
      myCalc = myCalc.input('*');
      myCalc = myCalc.input('1');
      myCalc = myCalc.input('1');
      myCalc = myCalc.input('=');
    }
    myCalc = myCalc.input('+');
    myCalc = myCalc.input('2');
    myCalc = myCalc.input('1');
    myCalc = myCalc.input('4');
    myCalc = myCalc.input('7');
    myCalc = myCalc.input('4');
    myCalc = myCalc.input('8');
    myCalc = myCalc.input('3');
    myCalc = myCalc.input('6');
    myCalc = myCalc.input('4');
    myCalc = myCalc.input('7');
    myCalc = myCalc.input('=');
    assertEquals("0", myCalc.getResult());
  }

  @Test
  public void posOverflowZerosOutMultiplication() {
    myCalc = myCalc.input('3');
    myCalc = myCalc.input('3');
    myCalc = myCalc.input('2');
    myCalc = myCalc.input('*');
    myCalc = myCalc.input('6');
    myCalc = myCalc.input('4');
    myCalc = myCalc.input('8');
    myCalc = myCalc.input('7');
    myCalc = myCalc.input('8');
    myCalc = myCalc.input('6');
    myCalc = myCalc.input('6');
    myCalc = myCalc.input('=');
    assertEquals("0", myCalc.getResult());
  }

  @Test
  public void negOverflowZerosOutMultiplication() {
    myCalc = myCalc.input('1');
    myCalc = myCalc.input('-');
    myCalc = myCalc.input('3');
    myCalc = myCalc.input('3');
    myCalc = myCalc.input('3');
    myCalc = myCalc.input('*');
    myCalc = myCalc.input('6');
    myCalc = myCalc.input('4');
    myCalc = myCalc.input('8');
    myCalc = myCalc.input('7');
    myCalc = myCalc.input('8');
    myCalc = myCalc.input('6');
    myCalc = myCalc.input('6');
    myCalc = myCalc.input('=');
    assertEquals("0", myCalc.getResult());
  }

  @Test
  public void multiOpsPositiveOverflow() {
    myCalc = myCalc.input('3');
    myCalc = myCalc.input('3');
    myCalc = myCalc.input('2');
    myCalc = myCalc.input('7');
    myCalc = myCalc.input('+');
    myCalc = myCalc.input('2');
    myCalc = myCalc.input('1');
    myCalc = myCalc.input('-');
    myCalc = myCalc.input('3');
    myCalc = myCalc.input('5');
    myCalc = myCalc.input('6');
    myCalc = myCalc.input('*');
    myCalc = myCalc.input('6');
    myCalc = myCalc.input('4');
    myCalc = myCalc.input('8');
    myCalc = myCalc.input('7');
    myCalc = myCalc.input('8');
    myCalc = myCalc.input('6');
    myCalc = myCalc.input('6');
    myCalc = myCalc.input('=');
    assertEquals("0", myCalc.getResult());
  }

  @Test
  public void multiOpsNegativeOverflow() {
    myCalc = myCalc.input('2');
    myCalc = myCalc.input('1');
    myCalc = myCalc.input('-');
    myCalc = myCalc.input('3');
    myCalc = myCalc.input('3');
    myCalc = myCalc.input('2');
    myCalc = myCalc.input('7');
    myCalc = myCalc.input('+');
    myCalc = myCalc.input('3');
    myCalc = myCalc.input('5');
    myCalc = myCalc.input('6');
    myCalc = myCalc.input('*');
    myCalc = myCalc.input('6');
    myCalc = myCalc.input('4');
    myCalc = myCalc.input('8');
    myCalc = myCalc.input('7');
    myCalc = myCalc.input('8');
    myCalc = myCalc.input('6');
    myCalc = myCalc.input('6');
    myCalc = myCalc.input('=');
    assertEquals("0", myCalc.getResult());
  }

  /* Ensures that adding works as normal after a negative overflow */
  @Test
  public void addAfterNegOverflow() {
    myCalc = myCalc.input('0');
    for (int i = 0; i < 6487866; i++) {
      myCalc = myCalc.input('-');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('1');
    }
    myCalc = myCalc.input('-');
    myCalc = myCalc.input('3');
    myCalc = myCalc.input('=');
    myCalc = myCalc.input('+');
    int operandSize = randomNumber(9, 1);
    /* generate random second operand to add to 0'd result */
    String expected = "";
    for (int i = 0; i < operandSize; i++) {
      int digit = randomNumber(NINE, ZERO);
      myCalc = myCalc.input((char) digit);
      expected += (char) digit;
    }
    String secondOpEmpty = expected + '+';
    if (Integer.parseInt(expected) == 0) {
      expected = "0";
    }
    if (!myCalc.getResult().equals(secondOpEmpty + "0")) {
      expected = expected.replaceFirst("^0*", "");
    }
    myCalc = myCalc.input('=');
    assertEquals(expected, myCalc.getResult());
  }

  @Test
  public void addAfterPosOverflow() {
    myCalc = myCalc.input('0');
    for (int i = 0; i < 6487866; i++) {
      myCalc = myCalc.input('+');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('1');
    }
    myCalc = myCalc.input('+');
    myCalc = myCalc.input('2');
    myCalc = myCalc.input('=');
    myCalc = myCalc.input('+');
    int operandSize = randomNumber(9, 1);
    /* generate random second operand to add to 0'd result */
    String expected = "";
    for (int i = 0; i < operandSize; i++) {
      int digit = randomNumber(NINE, ZERO);
      myCalc = myCalc.input((char) digit);
      expected += (char) digit;
    }
    String secondOpEmpty = expected + '+';
    if (Integer.parseInt(expected) == 0) {
      expected = "0";
    }
    if (!myCalc.getResult().equals(secondOpEmpty + "0")) {
      expected = expected.replaceFirst("^0*", "");
    }
    myCalc = myCalc.input('=');
    assertEquals(expected, myCalc.getResult());
  }

  /* Ensures that subtracting works as normal after a negative overflow */
  @Test
  public void subtractAfterNegOverflow() {
    myCalc = myCalc.input('0');
    for (int i = 0; i < 6487866; i++) {
      myCalc = myCalc.input('-');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('1');
    }
    myCalc = myCalc.input('-');
    myCalc = myCalc.input('3');
    myCalc = myCalc.input('=');
    myCalc = myCalc.input('-');
    int operandSize = randomNumber(9, 1);
    /* generate random second operand to subtract from 0'd result */
    String expected = "";
    for (int i = 0; i < operandSize; i++) {
      int digit = randomNumber(NINE, ZERO);
      myCalc = myCalc.input((char) digit);
      expected += (char) digit;
    }
    String secondOpEmpty = expected + '+';
    if (Integer.parseInt(expected) == 0) {
      expected = "0";
    }
    if (!myCalc.getResult().equals(secondOpEmpty + "0")) {
      expected = expected.replaceFirst("^0*", "");
    }
    myCalc = myCalc.input('=');
    assertEquals("-" + expected, myCalc.getResult());
  }

  @Test
  public void subtractAfterPosOverflow() {
    myCalc = myCalc.input('0');
    for (int i = 0; i < 6487866; i++) {
      myCalc = myCalc.input('+');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('1');
    }
    myCalc = myCalc.input('+');
    myCalc = myCalc.input('3');
    myCalc = myCalc.input('=');

    myCalc = myCalc.input('-');

    int operandSize = randomNumber(9, 1);
    /* generate random second operand to subtract from 0'd result */
    String expected = "";
    for (int i = 0; i < operandSize; i++) {
      int digit = randomNumber(NINE, ZERO);
      myCalc = myCalc.input((char) digit);
      expected += (char) digit;
    }
    String secondOpEmpty = expected + '+';
    if (Integer.parseInt(expected) == 0) {
      myCalc = myCalc.input('1');
      expected = "1";
    }
    if (!myCalc.getResult().equals(secondOpEmpty + "0")) {
      expected = expected.replaceFirst("^0*", "");
    }
    myCalc = myCalc.input('=');
    assertEquals("-" + expected, myCalc.getResult());
  }

  /* Ensures that multiplying works as normal after a negative overflow */
  @Test
  public void multiplyAfterNegOverflow() {
    myCalc = myCalc.input('0');
    for (int i = 0; i < 6487866; i++) {
      myCalc = myCalc.input('-');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('1');
    }
    myCalc = myCalc.input('-');
    myCalc = myCalc.input('3');
    myCalc = myCalc.input('=');
    myCalc = myCalc.input('*');
    int operandSize = randomNumber(9, 1);
    /* generate random second operand to subtract from 0'd result */
    for (int i = 0; i < operandSize; i++) {
      int digit = randomNumber(NINE, ZERO);
      myCalc = myCalc.input((char) digit);
    }
    myCalc = myCalc.input('=');
    assertEquals("0", myCalc.getResult());
  }

  @Test
  public void multiplyAfterPosOverflow() {
    myCalc = myCalc.input('0');
    for (int i = 0; i < 6487866; i++) {
      myCalc = myCalc.input('+');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('3');
      myCalc = myCalc.input('1');
    }
    myCalc = myCalc.input('+');
    myCalc = myCalc.input('3');
    myCalc = myCalc.input('=');
    myCalc = myCalc.input('*');
    int operandSize = randomNumber(9, 1);
    /* generate random second operand to subtract from 0'd result */
    for (int i = 0; i < operandSize; i++) {
      int digit = randomNumber(NINE, ZERO);
      myCalc = myCalc.input((char) digit);
    }
    myCalc = myCalc.input('=');
    assertEquals("0", myCalc.getResult());
  }
}
