/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.custom;

/**
 *
 * @author Timo
 */
public class Convert {

    /**
     * Try parsing a integer. If that fails, just return 0
     *
     * @param String value the string to convert
     * @return Integer the integer to return
     */
    public static Integer tryParseInt(String value) {
        Integer result;

        try {
            result = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            result = 0;
        }
        return result;
    }

    /**
     * Tries to parse double.
     *
     * @param String value the string to parse
     * @return return double. On fail, returns 0
     */
    public static Double tryParseDouble(String value) {
        double result;

        try {
            result = Double.parseDouble(value);
        } catch (Exception e) {
            result = 0;
        }
        return result;
    }
}
