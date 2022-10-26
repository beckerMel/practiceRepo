import org.junit.Test;

import calculator.Calculator;
import calculator.SmartCalculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * A test class providing functionality to test that the SmartCalculator class works as expected.
 */
public class SmartCalculatorTest extends AbstractCalculatorTest {
  protected final int NINE = 57;
  private Calculator smartCalc;

  protected Calculator makeTestCalc() {
    return new SmartCalculator();
  }

  @Test
  public void plusCanBeFirst() {
    smartCalc = new SmartCalculator();
    try {
      smartCalc = smartCalc.input('+');
    } catch (Exception e) {
      fail("Smart calc should be able to accept '+' as first character");
    }
  }

  @Test
  public void testFirstCalcInputs() {
    boolean success = true;
    smartCalc = new SmartCalculator();
    inputNumbers(smartCalc);
    inputClear(smartCalc);
    inputEquals(smartCalc, true);
    smartCalc.input('+');

    try {
      smartCalc.input('-');
      fail("'-' should fail input");
    } catch (IllegalArgumentException e) {
      doNothing();
    }

    try {
      smartCalc.input('*');
      fail("'' should fail input");
    } catch (IllegalArgumentException e) {
      doNothing();
    }

    inputInvalidChars(smartCalc);
    assertEquals(success, true);
  }

  @Test
  public void testClearedCalcInputs() {
    boolean success = true;
    initializeTwoOp('+');
    twoOperand = twoOperand.input('C');
    inputNumbers(twoOperand);
    inputClear(twoOperand);
    inputEquals(twoOperand, true);
    twoOperand.input('+');

    try {
      twoOperand.input('-');
      fail("'-' should fail input");
    } catch (IllegalArgumentException e) {
      doNothing();
    }

    try {
      twoOperand.input('*');
      fail("'' should fail input");
    } catch (IllegalArgumentException e) {
      doNothing();
    }

    inputInvalidChars(twoOperand);
    assertEquals(success, true);
  }

  /* Ensures that an illegal input (anything but a number or 'C') cannot be put into
   calculator after the first operand and a '+' operator, but legal inputs can */
  @Test
  public void testInputsAfterOp1andPlus() {
    boolean success = true;
    initializeOneOp();
    oneOperand = oneOperand.input('+');
    inputNumbers(oneOperand);
    inputClear(oneOperand);
    inputEquals(oneOperand, true);
    inputOperators(oneOperand, true);
    inputInvalidChars(oneOperand);
    assertEquals(success, true);
  }

  /* Ensures that an illegal input (anything but a number or 'C') cannot be put into
   calculator after the first operand and a '-' operator, but legal inputs can */
  @Test
  public void testInputsAfterOp1andMinus() {
    boolean success = true;
    initializeOneOp();
    oneOperand = oneOperand.input('-');
    inputNumbers(oneOperand);
    inputClear(oneOperand);
    inputEquals(oneOperand, true);
    inputOperators(oneOperand, true);
    inputInvalidChars(oneOperand);
    assertEquals(success, true);
  }

  /* Ensures that an illegal input (anything but a number or 'C') cannot be put into
  calculator after the first operand and a '*' operator, but legal inputs can */
  @Test
  public void testInputsAfterOp1andTimes() {
    boolean success = true;
    initializeOneOp();
    oneOperand = oneOperand.input('*');
    inputNumbers(oneOperand);
    inputClear(oneOperand);
    inputEquals(oneOperand, true);
    inputOperators(oneOperand, true);
    inputInvalidChars(oneOperand);
    assertEquals(success, true);
  }

  @Test
  public void firstPlusDoesntShow() {
    smartCalc = new SmartCalculator();
    smartCalc = smartCalc.input('+');
    assertEquals("", smartCalc.getResult());
  }

  @Test
  public void multiFirstPlusDoesntShow() {
    smartCalc = new SmartCalculator();
    int n = randomNumber(10000, 2);
    for (int i = 0; i < n; i++) {
      smartCalc = smartCalc.input('+');
    }
    assertEquals("", smartCalc.getResult());
  }

  @Test
  public void minusAfterPlus() {
    initializeOneOp();
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('+');
      oneOperand = oneOperand.input('-');
    }
    expectedOutput += '-';
    assertEquals(expectedOutput, oneOperand.getResult());
  }

  @Test
  public void clearWorksMinusAfterPlus() {
    initializeOneOp();
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('+');
      oneOperand = oneOperand.input('-');
    }
    oneOperand = oneOperand.input('C');
    assertEquals("", oneOperand.getResult());
  }

  /* Adding minus to a new calc based on old calc doesn't change original */
  @Test
  public void minusAfterPlusOriginalCalcDoesntChange() {
    initializeOneOp();
    Calculator newCalc;
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('+');
      newCalc = oneOperand.input('-');
    }
    expectedOutput += '+';
    assertEquals(expectedOutput, oneOperand.getResult());
  }

  @Test
  public void minusAfterMinus() {
    initializeOneOp();
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('-');
      oneOperand = oneOperand.input('-');
    }
    expectedOutput += '-';
    assertEquals(expectedOutput, oneOperand.getResult());
  }

  @Test
  public void clearWorksMinusAfterMinus() {
    initializeOneOp();
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('-');
      oneOperand = oneOperand.input('-');
    }
    oneOperand = oneOperand.input('C');
    assertEquals("", oneOperand.getResult());
  }

  @Test
  public void minusAfterTimes() {
    initializeOneOp();
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('*');
      oneOperand = oneOperand.input('-');
    }
    expectedOutput += '-';
    assertEquals(expectedOutput, oneOperand.getResult());
  }

  @Test
  public void clearWorksMinusAfterTimes() {
    initializeOneOp();
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('*');
      oneOperand = oneOperand.input('-');
    }
    oneOperand = oneOperand.input('C');
    assertEquals("", oneOperand.getResult());
  }

  @Test
  public void minusAfterTimesOriginalCalcDoesntChange() {
    initializeOneOp();
    Calculator newCalc;
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('*');
      newCalc = oneOperand.input('-');
    }
    expectedOutput += '*';
    assertEquals(expectedOutput, oneOperand.getResult());
  }


  @Test
  public void plusAfterPlus() {
    initializeOneOp();
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('+');
      oneOperand = oneOperand.input('+');
    }
    expectedOutput += '+';
    assertEquals(expectedOutput, oneOperand.getResult());
  }

  @Test
  public void clearWorksPlusAfterPlus() {
    initializeOneOp();
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('+');
      oneOperand = oneOperand.input('+');
    }
    oneOperand = oneOperand.input('C');
    assertEquals("", oneOperand.getResult());
  }

  @Test
  public void plusAfterMinus() {
    initializeOneOp();
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('-');
      oneOperand = oneOperand.input('+');
    }
    expectedOutput += '+';
    assertEquals(expectedOutput, oneOperand.getResult());
  }

  @Test
  public void clearWorksPlusAfterMinus() {
    initializeOneOp();
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('-');
      oneOperand = oneOperand.input('+');
    }
    oneOperand = oneOperand.input('C');
    assertEquals("", oneOperand.getResult());
  }

  @Test
  public void plusAfterMinusOriginalCalcDoesntChange() {
    initializeOneOp();
    Calculator newCalc;
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('-');
      newCalc = oneOperand.input('+');
    }
    expectedOutput += '-';
    assertEquals(expectedOutput, oneOperand.getResult());
  }

  @Test
  public void plusAfterTimes() {
    initializeOneOp();
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('*');
      oneOperand = oneOperand.input('+');
    }
    expectedOutput += '+';
    assertEquals(expectedOutput, oneOperand.getResult());
  }

  @Test
  public void clearWorksPlusAfterTimes() {
    initializeOneOp();
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('*');
      oneOperand = oneOperand.input('+');
    }
    oneOperand = oneOperand.input('C');
    assertEquals("", oneOperand.getResult());
  }

  @Test
  public void plusAfterTimesOriginalCalcDoesntChange() {
    initializeOneOp();
    Calculator newCalc;
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('*');
      newCalc = oneOperand.input('+');
    }
    expectedOutput += '*';
    assertEquals(expectedOutput, oneOperand.getResult());
  }

  @Test
  public void timesAfterPlus() {
    initializeOneOp();
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('+');
      oneOperand = oneOperand.input('*');
    }
    expectedOutput += '*';
    assertEquals(expectedOutput, oneOperand.getResult());
  }

  @Test
  public void clearWorksTimesAfterPlus() {
    initializeOneOp();
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('+');
      oneOperand = oneOperand.input('*');
    }
    oneOperand = oneOperand.input('C');
    assertEquals("", oneOperand.getResult());
  }

  @Test
  public void timesAfterPlusOriginalCalcDoesntChange() {
    initializeOneOp();
    Calculator newCalc;
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('+');
      newCalc = oneOperand.input('*');
    }
    expectedOutput += '+';
    assertEquals(expectedOutput, oneOperand.getResult());
  }

  @Test
  public void timesAfterMinus() {
    initializeOneOp();
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('-');
      oneOperand = oneOperand.input('*');
    }
    expectedOutput += '*';
    assertEquals(expectedOutput, oneOperand.getResult());
  }

  @Test
  public void clearWorksTimesAfterMinus() {
    initializeOneOp();
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('-');
      oneOperand = oneOperand.input('*');
    }
    oneOperand = oneOperand.input('C');
    assertEquals("", oneOperand.getResult());
  }

  @Test
  public void timesAfterMinusOriginalCalcDoesntChange() {
    initializeOneOp();
    Calculator newCalc;
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('-');
      newCalc = oneOperand.input('*');
    }
    expectedOutput += '-';
    assertEquals(expectedOutput, oneOperand.getResult());
  }

  @Test
  public void timesAfterTimes() {
    initializeOneOp();
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('*');
      oneOperand = oneOperand.input('*');
    }
    expectedOutput += '*';
    assertEquals(expectedOutput, oneOperand.getResult());
  }

  @Test
  public void clearWorksTimesAfterTimes() {
    initializeOneOp();
    int n = randomNumber(10000, 1);
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('*');
      oneOperand = oneOperand.input('*');
    }
    oneOperand = oneOperand.input('C');
    assertEquals("", oneOperand.getResult());
  }


  /* tests that any input that isn't another number, 'C', '=', or an operator throws an illegal
   * argument exception after an operand and a '+' have been entered into the calculator */
  @Test
  public void illegalInputsOperandOperatorPlus() {
    initializeOneOp();
    oneOperand = oneOperand.input('+');
    for (int i = 0; i < 42; i++) {
      try {
        oneOperand.input((char) i);
        fail(i + " failed to throw an exception as first input to calc");
      } catch (IllegalArgumentException e) {
        continue;
      }
    }
    try {
      oneOperand.input((char) 44);
      fail((char) 44 + " failed to throw an exception as first input to calc");
    } catch (IllegalArgumentException e) {
      doNothing();
    }
    for (int i = 46; i < ZERO; i++) {
      try {
        oneOperand.input((char) i);
        fail(i + " failed to throw an exception as first input to calc");
      } catch (IllegalArgumentException e) {
        continue;
      }
    }
    for (int i = 58; i < 61; i++) {
      try {
        oneOperand.input((char) i);
        fail(i + " failed to throw an exception as first input to calc");
      } catch (IllegalArgumentException e) {
        continue;
      }
    }

    for (int i = 62; i < 67; i++) {
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

  /* tests that any input that isn't another number throws an illegal argument exception after
   * an operand and a '-' have been entered into the calculator */
  @Test
  public void illegalInputsOperandOperatorMinus() {
    initializeOneOp();
    oneOperand = oneOperand.input('-');
    for (int i = 0; i < 42; i++) {
      try {
        oneOperand.input((char) i);
        fail(i + " failed to throw an exception as first input to calc");
      } catch (IllegalArgumentException e) {
        continue;
      }
    }
    try {
      oneOperand.input((char) 44);
      fail((char) 44 + " failed to throw an exception as first input to calc");
    } catch (IllegalArgumentException e) {
      doNothing();
    }
    for (int i = 46; i < ZERO; i++) {
      try {
        oneOperand.input((char) i);
        fail(i + " failed to throw an exception as first input to calc");
      } catch (IllegalArgumentException e) {
        continue;
      }
    }
    for (int i = 58; i < 61; i++) {
      try {
        oneOperand.input((char) i);
        fail(i + " failed to throw an exception as first input to calc");
      } catch (IllegalArgumentException e) {
        continue;
      }
    }

    for (int i = 62; i < 67; i++) {
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

  /* tests that any input that isn't another number throws an illegal argument exception after
   * an operand and a '-' have been entered into the calculator */
  @Test
  public void illegalInputsOperandOperatorTimes() {
    initializeOneOp();
    oneOperand = oneOperand.input('*');
    for (int i = 0; i < 42; i++) {
      try {
        oneOperand.input((char) i);
        fail(i + " failed to throw an exception as first input to calc");
      } catch (IllegalArgumentException e) {
        continue;
      }
    }
    try {
      oneOperand.input((char) 44);
      fail((char) 44 + " failed to throw an exception as first input to calc");
    } catch (IllegalArgumentException e) {
      doNothing();
    }
    for (int i = 46; i < ZERO; i++) {
      try {
        oneOperand.input((char) i);
        fail(i + " failed to throw an exception as first input to calc");
      } catch (IllegalArgumentException e) {
        continue;
      }
    }
    for (int i = 58; i < 61; i++) {
      try {
        oneOperand.input((char) i);
        fail(i + " failed to throw an exception as first input to calc");
      } catch (IllegalArgumentException e) {
        continue;
      }
    }

    for (int i = 62; i < 67; i++) {
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
  public void secondOpMissingPlus() {
    initializeOneOp();
    int result = multiplyLikeCalc(2, Integer.parseInt(expectedOutput));
    oneOperand = oneOperand.input('+');
    oneOperand = oneOperand.input('=');
    expectedOutput = String.valueOf(result);
    assertEquals(expectedOutput, oneOperand.getResult());
  }

  @Test
  public void secondOpMissingPlusMultiple() {
    smartCalc = new SmartCalculator();
    String expected = "";
    int addition = 0;
    int total = 0;

    int operandSize = randomNumber(9, 1);
    for (int i = 0; i < operandSize; i++) {
      int digit = randomNumber(NINE, ZERO);
      smartCalc = smartCalc.input((char) digit);
      expected += (char) digit;
    }
    if (Integer.parseInt(expected) == 0) {
      /* prevents number from being "00" or "000" or etc. */
      expected = "0";
    }
    if (!smartCalc.getResult().equals("0")) {
      expected = expected.replaceFirst("^0*", "");
    }
    addition = Integer.parseInt(expected);
    total = addition;

    int n = randomNumber(10, 1);
    smartCalc = smartCalc.input('+');
    for (int i = 0; i < n; i++) {
      smartCalc = smartCalc.input('=');
      total = addLikeCalc(total, addition);
    }
    smartCalc = smartCalc.input('=');
    total = addLikeCalc(total, addition);
    expected = String.valueOf(total);
    assertEquals(expected, smartCalc.getResult());
    assertEquals(expected, smartCalc.getResult());
  }

  @Test
  public void clearWorksSecondOpMissingPlusMultiple() {
    Calculator testCalc = new SmartCalculator();
    testCalc = testCalc.input((char) ZERO);
    int n = randomNumber(10, 2);
    testCalc = testCalc.input('+');
    for (int i = 0; i < n; i++) {
      testCalc = testCalc.input('=');
    }
    testCalc = testCalc.input('=');
    testCalc = testCalc.input('C');
    assertEquals("", testCalc.getResult());
  }

  /* makes sure that entering "=" after only first operand and plus sign are in throws exception */
  @Test
  public void secondOpMissingTimes() {
    smartCalc = new SmartCalculator();
    String expected = "";
    int operandSize = randomNumber(9, 1);
    for (int i = 0; i < operandSize; i++) {
      int digit = randomNumber(NINE, ZERO);
      smartCalc = smartCalc.input((char) digit);
      expected += (char) digit;
    }
    if (Integer.parseInt(expected) == 0) {
      /* prevents number from being "00" or "000" or etc. */
      expected = "0";
    }
    if (!smartCalc.getResult().equals("0")) {
      expected = expected.replaceFirst("^0*", "");
    }
    smartCalc = smartCalc.input('*');
    smartCalc = smartCalc.input('=');
    int result = multiplyLikeCalc(Integer.parseInt(expected), Integer.parseInt(expected));
    assertEquals(String.valueOf(result), smartCalc.getResult());
  }

  @Test
  public void secondOpMissingTimesMultiple() {
    smartCalc = new SmartCalculator();
    String expected = "";
    int addition = 0;
    int total = 0;

    int operandSize = randomNumber(9, 1);
    for (int i = 0; i < operandSize; i++) {
      int digit = randomNumber(NINE, ZERO);
      smartCalc = smartCalc.input((char) digit);
      expected += (char) digit;
    }
    if (Integer.parseInt(expected) == 0) {
      /* prevents number from being "00" or "000" or etc. */
      expected = "0";
    }
    if (!smartCalc.getResult().equals("0")) {
      expected = expected.replaceFirst("^0*", "");
    }

    addition = Integer.parseInt(expected);
    total = addition;

    int n = randomNumber(3, 1);
    smartCalc = smartCalc.input('*');
    for (int i = 0; i < n; i++) {
      smartCalc = smartCalc.input('=');
      total = multiplyLikeCalc(total, addition);
    }
    expected = String.valueOf(total);
    assertEquals(expected, smartCalc.getResult());
  }

  @Test
  public void clearWorksSecondOpMissingTimesMultiple() {
    smartCalc = new SmartCalculator();

    int operandSize = randomNumber(9, 1);
    for (int i = 0; i < operandSize; i++) {
      int digit = randomNumber(NINE, ZERO);
      smartCalc = smartCalc.input((char) digit);
    }
    int n = randomNumber(3, 1);
    smartCalc = smartCalc.input('*');
    for (int i = 0; i < n; i++) {
      smartCalc = smartCalc.input('=');
    }
    smartCalc = smartCalc.input('C');
    assertEquals("", smartCalc.getResult());
  }

  /* makes sure that entering "=" after only first operand and - executes operation on first
   * operand */
  @Test
  public void secondOpMissingMinus() {
    initializeOneOp();
    oneOperand = oneOperand.input('-');
    oneOperand = oneOperand.input('=');
    assertEquals("0", oneOperand.getResult());
  }

  @Test
  public void secondOpMissingMinusMultiple() {
    smartCalc = new SmartCalculator();
    String expected = "";
    int subtraction = 0;
    int total = 0;

    int operandSize = randomNumber(9, 1);
    for (int i = 0; i < operandSize; i++) {
      int digit = randomNumber(NINE, ZERO);
      smartCalc = smartCalc.input((char) digit);
      expected += (char) digit;
    }
    if (Integer.parseInt(expected) == 0) {
      /* prevents number from being "00" or "000" or etc. */
      expected = "0";
    }
    if (!smartCalc.getResult().equals("0")) {
      expected = expected.replaceFirst("^0*", "");
    }

    subtraction = Integer.parseInt(expected);
    total = subtraction;

    int n = randomNumber(3, 1);
    smartCalc = smartCalc.input('-');
    for (int i = 0; i < n; i++) {
      smartCalc = smartCalc.input('=');
      total = subtractLikeCalc(total, subtraction);
    }
    smartCalc = smartCalc.input('=');
    total = subtractLikeCalc(total, subtraction);
    expected = String.valueOf(total);
    assertEquals(expected, smartCalc.getResult());
  }

  /* makes sure that entering "=" after only first operand and - executes operation on first
   * operand */
  @Test
  public void clearWorksSecondOpMissingMinusMultiple() {
    initializeOneOp();
    int n = randomNumber(10, 2);
    oneOperand = oneOperand.input('-');
    for (int i = 0; i < n; i++) {
      oneOperand = oneOperand.input('=');
    }
    oneOperand = oneOperand.input('=');
    oneOperand = oneOperand.input('C');
    assertEquals("", oneOperand.getResult());
  }
}