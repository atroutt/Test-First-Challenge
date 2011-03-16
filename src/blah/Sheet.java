package blah;

import java.util.HashMap;
import java.util.Map;

public class Sheet {
    private final Map<String, String> map = new HashMap<String, String>();

    public String get(String cellReference) {
        String value = getLiteral(cellReference);

        value = trimIfInteger(value);

        value = trimLeadingEquals(value);

        value = trimSurroundingParentheses(value);

        return value;
    }

    private String trimSurroundingParentheses(String value) {
        while(value.startsWith("("))
            value = value.substring(1);
        while(value.endsWith(")"))
            value = value.substring(0, value.length() - 1);

        return value;
    }

    private String trimLeadingEquals(String value) {
        if ( value.startsWith("="))
            value = value.substring(1);
        return value;
    }


    private String trimIfInteger(String value) {
        try {
            value = "" + Integer.parseInt(value.trim());
        } catch (NumberFormatException ignored) {
        }
        return value;
    }

    public String getLiteral(String cellReference) {
        String value = map.get(cellReference);
        return value == null ? "" : value;
    }

    public void put(String theCell, String s) {
        map.put(theCell, s);
    }
}
