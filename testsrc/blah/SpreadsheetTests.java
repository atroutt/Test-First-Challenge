package blah;

import blah.Sheet;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpreadsheetTests {

    @Test
    public void testThatCellsAreEmptyByDefault() {
        Sheet sheet = new Sheet();
        assertEquals("", sheet.get("A1"));
        assertEquals("", sheet.get("ZX347"));
    }

    @Test
    public void testThatCellsHaveEmtpyLiteralsByDefault() {
        Sheet sheet = new Sheet();
        assertEquals("", sheet.getLiteral("A1"));
        assertEquals("", sheet.getLiteral("ZX347"));
    }

    @Test
    public void testThatTextCellsAreStored() {
        Sheet sheet = new Sheet();
        String theCell = "A21";

        sheet.put(theCell, "A string");
        assertEquals("A string", sheet.get(theCell));

        sheet.put(theCell, "A different string");
        assertEquals("A different string", sheet.get(theCell));

        sheet.put(theCell, "");
        assertEquals("", sheet.get(theCell));
    }

    @Test
    public void testThatManyCellsExist() {
        Sheet sheet = new Sheet();
        sheet.put("A1", "First");
        sheet.put("X27", "Second");
        sheet.put("ZX901", "Third");

        assertEquals("A1", "First", sheet.get("A1"));
        assertEquals("X27", "Second", sheet.get("X27"));
        assertEquals("ZX901", "Third", sheet.get("ZX901"));

        sheet.put("A1", "Fourth");
        assertEquals("A1 after", "Fourth", sheet.get("A1"));
        assertEquals("X27 same", "Second", sheet.get("X27"));
        assertEquals("ZX901 same", "Third", sheet.get("ZX901"));
    }

    @Test
    public void testThatNumericCellsAreIdentifiedAndStored() {
        Sheet sheet = new Sheet();
        String theCell = "A21";

        sheet.put(theCell, "X99"); // "Obvious" string
        assertEquals("X99", sheet.get(theCell));

        sheet.put(theCell, "14"); // "Obvious" number
        assertEquals("14", sheet.get(theCell));

        sheet.put(theCell, " 99 X"); // Whole string must be numeric
        assertEquals(" 99 X", sheet.get(theCell));

        sheet.put(theCell, " 1234 "); // Blanks ignored
        assertEquals("1234", sheet.get(theCell));

        sheet.put(theCell, " "); // Just a blank
        assertEquals(" ", sheet.get(theCell));
    }

    @Test
    public void testThatWeHaveAccessToCellLiteralValuesForEditing() {
        Sheet sheet = new Sheet();
        String theCell = "A21";

        sheet.put(theCell, "Some string");
        assertEquals("Some string", sheet.getLiteral(theCell));

        sheet.put(theCell, " 1234 ");
        assertEquals(" 1234 ", sheet.getLiteral(theCell));

        sheet.put(theCell, "=7"); // Foreshadowing formulas:)
        assertEquals("=7", sheet.getLiteral(theCell));
    }

    @Test
    public void testFormulaSpec() {
        Sheet sheet = new Sheet();
        sheet.put("B1", " =7"); // note leading space
        assertEquals("Not a formula", " =7", sheet.get("B1"));
        assertEquals("Unchanged", " =7", sheet.getLiteral("B1"));
    }

    @Test
    public void testConstantFormula() {
        Sheet sheet = new Sheet();
        sheet.put("A1", "=7");
        assertEquals("Formula", "=7", sheet.getLiteral("A1"));
        assertEquals("Value", "7", sheet.get("A1"));
    }

    @Test
    public void testParentheses() {
        Sheet sheet = new Sheet();
        sheet.put("A1", "=(7)");
        assertEquals("Parens", "7", sheet.get("A1"));
    }

    @Test
    public void testParenthesesOnAString() throws Exception {
        Sheet sheet = new Sheet();
        sheet.put("A1", "((oh hai))");
        assertEquals("((oh hai))", sheet.get("A1"));
    }

    @Test
    public void testUnequalNumberOfParentheses() throws Exception {
        Sheet sheet = new Sheet();
        sheet.put("A1", "=((7)");
        assertEquals("#ERROR", sheet.get("A1"));
    }

    @Test
    public void testDeepParentheses() {
        Sheet sheet = new Sheet();
        sheet.put("A1", "=((((10))))");
        assertEquals("Parens", "10", sheet.get("A1"));
    }

    @Test
    public void testMultiply() {
        Sheet sheet = new Sheet();
        sheet.put("A1", "=2*3*4");
        assertEquals("Times", "24", sheet.get("A1"));
    }
}
