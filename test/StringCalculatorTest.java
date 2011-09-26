import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StringCalculatorTest {

    private StringCalculator calculator = new StringCalculator();

    @Test
    public void addEmptyStringEqualsZero() {
        assertEquals(0, calculator.add(""));
    }

    @Test
    public void addOneDigit() {
        assertEquals(1, calculator.add("1"));
        assertEquals(2, calculator.add("2"));
        assertEquals(3, calculator.add("3"));
        assertEquals(9, calculator.add("9"));
        assertEquals(198, calculator.add("198"));
    }

    @Test
    public void addTwoDigits() {
        assertEquals(2, calculator.add("1,1"));
        assertEquals(3, calculator.add("1,2"));
        assertEquals(311, calculator.add("104,207"));
        assertEquals(1428, calculator.add("916,512"));
    }

    @Test
    public void addThreeDigits() {
        assertEquals(3, calculator.add("1,1,1"));
        assertEquals(6, calculator.add("1,2,3"));
        assertEquals(403, calculator.add("150,230,23"));
        assertEquals(1418, calculator.add("1,916,501"));
    }

    @Test
    public void allowNewLineAsDelimiter() {
        assertEquals(6, calculator.add("1\n2,3"));
    }

    @Test
    public void allowUserDeterminedDelimiter() {
        assertEquals(6, calculator.add("//;\n1;2;3"));
        assertEquals(6, calculator.add("//pp\n1pp2pp3"));
    }

    @Test
    public void negativesNotAllowed() {
        try {
            calculator.add("-1,3");
            fail("Negatives not allowed, should throw exception.");
        } catch (IllegalArgumentException e) {
            assertEquals("Negatives not allowed. -1", e.getMessage());
        }
    }

    @Test
    public void listAllNegativesInErrorMessage() {
        try {
            calculator.add("-1,-3");
            fail("Negatives not allowed, should throw exception.");
        } catch (IllegalArgumentException e) {
            assertEquals("Negatives not allowed. -1, -3", e.getMessage());
        }
    }

    @Test
    public void ignoreDigitsGreaterThanThousand() {
        assertEquals(1, calculator.add("1,1001"));
    }

    @Test
    public void allowMultipleDelimiters() {
        assertEquals(6, calculator.add("//[;][,]\n1;2,3"));
    }

    @Test
    public void allowMultipleDelimitersWithMoreThanOneChar() {
        assertEquals(6, calculator.add("//[;;][,,]\n1;;2,,3"));
    }

}
