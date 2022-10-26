import org.junit.Test;

import calculator.Calculator;
import calculator.SimpleCalculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * A test class providing functionality to test that the SimpleCalculator class works as expected.
 */
public class SimpleCalculatorTest extends AbstractCalculatorTest {

  /* An empty SimpleCalculator */
  private Calculator stupidCalc;

  protected Calculator makeTestCalc() {
    return new SimpleCalculator();
  }

  /* Ensures that an illegal input (anything but a number or 'C') cannot be put into calculator
  first, and legal inputs can without throwing an exception */
  @Test
  public void testFirstCalcInputs() {
    boolean success = true;
    stupidCalc = new SimpleCalculator();
    inputNumbers(stupidCalc);
    inputClear(stupidCalc);
    inputEquals(stupidCalc, false);
    inputOperators(stupidCalc, false);
    inputInvalidChars(stupidCalc);
    assertEquals(success, true);
  }

  @Test
  public void testClearedCalcInputs() {
    boolean success = true;
    initializeTwoOp('+');
    twoOperand = twoOperand.input('C');
    inputNumbers(twoOperand);
    inputClear(twoOperand);
    inputEquals(twoOperand, false);
    inputOperators(twoOperand, false);
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
    inputEquals(oneOperand, false);
    inputOperators(oneOperand, false);
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
    inputEquals(oneOperand, false);
    inputOperators(oneOperand, false);
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
    inputEquals(oneOperand, false);
    inputOperators(oneOperand, false);
    inputInvalidChars(oneOperand);
    assertEquals(success, true);
  }


  @Test(expected = IllegalArgumentException.class)
  public void minusAfterPlus() {
    initializeOneOp();
    Calculator onePlus = oneOperand.input('+');
    onePlus = onePlus.input('-');
  }

  @Test(expected = IllegalArgumentException.class)
  public void minusAfterMinus() {
    initializeOneOp();
    Calculator oneMinus = oneOperand.input('-');
    oneMinus = oneMinus.input('-');
  }

  @Test(expected = IllegalArgumentException.class)
  public void minusAfterTimes() {
    initializeOneOp();
    Calculator oneTimes = oneOperand.input('*');
    oneTimes = oneTimes.input('-');
  }

  @Test(expected = IllegalArgumentException.class)
  public void plusAfterPlus() {
    initializeOneOp();
    Calculator onePlus = oneOperand.input('+');
    onePlus = onePlus.input('+');
  }

  @Test(expected = IllegalArgumentException.class)
  public void plusAfterMinus() {
    initializeOneOp();
    Calculator oneMinus = oneOperand.input('-');
    oneMinus = oneMinus.input('+');
  }

  @Test(expected = IllegalArgumentException.class)
  public void plusAfterTimes() {
    initializeOneOp();
    Calculator oneTimes = oneOperand.input('*');
    oneTimes = oneTimes.input('+');
  }

  @Test(expected = IllegalArgumentException.class)
  public void timesAfterPlus() {
    initializeOneOp();
    Calculator onePlus = oneOperand.input('+');
    onePlus = onePlus.input('*');
  }

  @Test(expected = IllegalArgumentException.class)
  public void timesAfterMinus() {
    initializeOneOp();
    Calculator oneMinus = oneOperand.input('-');
    oneMinus = oneMinus.input('*');
  }

  @Test(expected = IllegalArgumentException.class)
  public void timesAfterTimes() {
    initializeOneOp();
    Calculator oneTimes = oneOperand.input('*');
    oneTimes = oneTimes.input('*');
  }

  /* tests that any input that isn't another number throws an illegal argument exception after
   * an operand and a '+' have been entered into the calculator */
  @Test
  public void illegalInputsOperandOperatorPlus() {
    initializeOneOp();
    Calculator onePlus = oneOperand.input('+');
    for (int i = 0; i < 48; i++) {
      try {
        onePlus.input((char) i);
        fail(i + " failed to throw an exception as first input to calc");
      } catch (IllegalArgumentException e) {
        continue;
      }
    }
    for (int i = 58; i < 66; i++) {
      try {
        onePlus.input((char) i);
        fail(i + " failed to throw an exception as first input to calc");
      } catch (IllegalArgumentException e) {
        continue;
      }
    }

    for (int i = 68; i < 128; i++) {
      try {
        onePlus.input((char) i);
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
    Calculator oneMinus = oneOperand.input('-');
    for (int i = 0; i < 48; i++) {
      try {
        oneMinus.input((char) i);
        fail(i + " failed to throw an exception as first input to calc");
      } catch (IllegalArgumentException e) {
        continue;
      }
    }
    for (int i = 58; i < 66; i++) {
      try {
        oneMinus.input((char) i);
        fail(i + " failed to throw an exception as first input to calc");
      } catch (IllegalArgumentException e) {
        continue;
      }
    }

    for (int i = 68; i < 128; i++) {
      try {
        oneMinus.input((char) i);
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
    Calculator oneTimes = oneOperand.input('*');
    for (int i = 0; i < 48; i++) {
      try {
        oneTimes.input((char) i);
        fail(i + " failed to throw an exception as first input to calc");
      } catch (IllegalArgumentException e) {
        continue;
      }
    }
    for (int i = 58; i < 66; i++) {
      try {
        oneTimes.input((char) i);
        fail(i + " failed to throw an exception as first input to calc");
      } catch (IllegalArgumentException e) {
        continue;
      }
    }

    for (int i = 68; i < 128; i++) {
      try {
        oneTimes.input((char) i);
        fail(i + " failed to throw an exception as first input to calc");
      } catch (IllegalArgumentException e) {
        continue;
      }
    }
  }

  /* makes sure that entering "=" after only first operand and plus sign are in throws exception */
  @Test(expected = IllegalArgumentException.class)
  public void oneOpThrowsExceptionPlus() {
    initializeOneOp();
    Calculator onePlus = oneOperand.input('+');
    onePlus = onePlus.input('=');
  }

  /* makes sure that entering "=" after only first operand and plus sign are in throws exception */
  @Test(expected = IllegalArgumentException.class)
  public void oneOpThrowsExceptionMinus() {
    initializeOneOp();
    Calculator oneMinus = oneOperand.input('-');
    oneMinus = oneMinus.input('=');
  }

  /* makes sure that entering "=" after only first operand and plus sign are in throws exception */
  @Test(expected = IllegalArgumentException.class)
  public void oneOpThrowsExceptionTimes() {
    initializeOneOp();
    Calculator oneTimes = oneOperand.input('*');
    oneTimes = oneTimes.input('=');
  }

  @Test
  public void manyEqualsWorksWithMultipleOperations() {
    stupidCalc = new SimpleCalculator();
    stupidCalc = stupidCalc.input('1');
    stupidCalc = stupidCalc.input('7');
    int total = 17;
    int n = randomNumber(100, 1);
    for (int i = 0; i < n; i++) {
      stupidCalc = stupidCalc.input('+');
      stupidCalc = stupidCalc.input('3');
      stupidCalc = stupidCalc.input('6');
      stupidCalc = stupidCalc.input('5');
      stupidCalc = stupidCalc.input('=');
      total = addLikeCalc(total, 365);
      stupidCalc = stupidCalc.input('-');
      stupidCalc = stupidCalc.input('3');
      stupidCalc = stupidCalc.input('8');
      stupidCalc = stupidCalc.input('2');
      stupidCalc = stupidCalc.input('=');
      total = subtractLikeCalc(total, 382);
      stupidCalc = stupidCalc.input('*');
      stupidCalc = stupidCalc.input('1');
      stupidCalc = stupidCalc.input('1');
      stupidCalc = stupidCalc.input('=');
      total = multiplyLikeCalc(total, 11);
    }

    n = randomNumber(100, 2);
    for (int i = 0; i < n; i++) {
      stupidCalc = stupidCalc.input('=');
    }

    assertEquals(String.valueOf(total), stupidCalc.getResult());
  }

  @Test
  public void addingChangesResultAfterEqualsDoesnt() {
    stupidCalc = new SimpleCalculator();
    stupidCalc = stupidCalc.input('1');
    stupidCalc = stupidCalc.input('7');
    int total = 17;
    int n = randomNumber(100, 1);
    for (int i = 0; i < n; i++) {
      stupidCalc = stupidCalc.input('+');
      stupidCalc = stupidCalc.input('3');
      stupidCalc = stupidCalc.input('6');
      stupidCalc = stupidCalc.input('5');
      stupidCalc = stupidCalc.input('=');
      total = addLikeCalc(total, 365);
      stupidCalc = stupidCalc.input('-');
      stupidCalc = stupidCalc.input('3');
      stupidCalc = stupidCalc.input('8');
      stupidCalc = stupidCalc.input('2');
      stupidCalc = stupidCalc.input('=');
      total = subtractLikeCalc(total, 382);
      stupidCalc = stupidCalc.input('*');
      stupidCalc = stupidCalc.input('1');
      stupidCalc = stupidCalc.input('1');
      stupidCalc = stupidCalc.input('=');
      total = multiplyLikeCalc(total, 11);
    }

    n = randomNumber(100, 2);

    for (int i = 0; i < n; i++) {
      stupidCalc = stupidCalc.input('=');
    }

    stupidCalc = stupidCalc.input('+');
    stupidCalc = stupidCalc.input('5');
    stupidCalc = stupidCalc.input('8');
    stupidCalc = stupidCalc.input('2');
    stupidCalc = stupidCalc.input('8');
    stupidCalc = stupidCalc.input('=');
    total = addLikeCalc(total, 5828);

    assertEquals(String.valueOf(total), stupidCalc.getResult());
  }

  @Test
  public void subtractingChangesResultAfterEqualsDoesnt() {
    stupidCalc = new SimpleCalculator();
    stupidCalc = stupidCalc.input('1');
    stupidCalc = stupidCalc.input('7');
    int total = 17;
    int n = randomNumber(100, 1);
    for (int i = 0; i < n; i++) {
      stupidCalc = stupidCalc.input('+');
      stupidCalc = stupidCalc.input('3');
      stupidCalc = stupidCalc.input('6');
      stupidCalc = stupidCalc.input('5');
      stupidCalc = stupidCalc.input('=');
      total = addLikeCalc(total, 365);
      stupidCalc = stupidCalc.input('-');
      stupidCalc = stupidCalc.input('3');
      stupidCalc = stupidCalc.input('8');
      stupidCalc = stupidCalc.input('2');
      stupidCalc = stupidCalc.input('=');
      total = subtractLikeCalc(total, 382);
      stupidCalc = stupidCalc.input('*');
      stupidCalc = stupidCalc.input('1');
      stupidCalc = stupidCalc.input('1');
      stupidCalc = stupidCalc.input('=');
      total = multiplyLikeCalc(total, 11);
    }

    n = randomNumber(100, 2);

    for (int i = 0; i < n; i++) {
      stupidCalc = stupidCalc.input('=');
    }

    stupidCalc = stupidCalc.input('-');
    stupidCalc = stupidCalc.input('7');
    stupidCalc = stupidCalc.input('1');
    stupidCalc = stupidCalc.input('6');
    stupidCalc = stupidCalc.input('0');
    stupidCalc = stupidCalc.input('=');
    total = subtractLikeCalc(total, 7160);

    assertEquals(String.valueOf(total), stupidCalc.getResult());
  }

  @Test
  public void multChangesResultAfterEqualsDoesnt() {
    stupidCalc = new SimpleCalculator();
    stupidCalc = stupidCalc.input('1');
    stupidCalc = stupidCalc.input('7');
    int total = 17;
    int n = randomNumber(100, 1);
    for (int i = 0; i < n; i++) {
      stupidCalc = stupidCalc.input('+');
      stupidCalc = stupidCalc.input('3');
      stupidCalc = stupidCalc.input('6');
      stupidCalc = stupidCalc.input('5');
      stupidCalc = stupidCalc.input('=');
      total = addLikeCalc(total, 365);
      stupidCalc = stupidCalc.input('-');
      stupidCalc = stupidCalc.input('3');
      stupidCalc = stupidCalc.input('8');
      stupidCalc = stupidCalc.input('2');
      stupidCalc = stupidCalc.input('=');
      total = subtractLikeCalc(total, 382);
      stupidCalc = stupidCalc.input('*');
      stupidCalc = stupidCalc.input('1');
      stupidCalc = stupidCalc.input('1');
      stupidCalc = stupidCalc.input('=');
      total = multiplyLikeCalc(total, 11);
    }

    n = randomNumber(100, 2);

    for (int i = 0; i < n; i++) {
      stupidCalc = stupidCalc.input('=');
    }

    stupidCalc = stupidCalc.input('*');
    stupidCalc = stupidCalc.input('3');
    stupidCalc = stupidCalc.input('4');
    stupidCalc = stupidCalc.input('=');
    total = multiplyLikeCalc(total, 34);

    assertEquals(String.valueOf(total), stupidCalc.getResult());
  }
}