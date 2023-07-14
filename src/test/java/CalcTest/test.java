package CalcTest;

import org.example.CalcUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class test {

    CalcUtils calcUtils = new CalcUtils();

    @Test
    void addTwoNumbers() {
        Assertions.assertEquals(2, calcUtils.addStrings("1,1"));
    }

    @Test
    void addEmptyString() {
        Assertions.assertEquals(0, calcUtils.addStrings(""));
    }

    @Test
    void addUnknownAmountOfNumbers() {
        Assertions.assertEquals(5, calcUtils.addStrings("1,2,2"));
        Assertions.assertEquals(10, calcUtils.addStrings("2,2,2,2,2"));
    }

    @Test
    void handleNewLineOperator() {
        Assertions.assertEquals("1,2", calcUtils.removeNewLineOperator("1\n2"));
        Assertions.assertEquals("1,2,3,4", calcUtils.removeNewLineOperator("1\n2,3\n4"));
    }

    @Test
    void getDelimiter() {
        List<String> expected = new ArrayList<>();
        expected.add(".");
        List<String> expected1 = new ArrayList<>();
        expected1.add("@");
        Assertions.assertEquals(expected, calcUtils.getDelimiter("//.\n1.1.1"));
        Assertions.assertEquals(expected1, calcUtils.getDelimiter("//@\n1.1.1"));
    }

    @Test
    void removeDelimiterSyntax() {
        Assertions.assertEquals("1,2,3", calcUtils.removeDelimiterSyntax("//,\n1,2,3"));
        Assertions.assertEquals("1,2,3", calcUtils.removeDelimiterSyntax("1,2,3"));
        Assertions.assertEquals("1,2,3", calcUtils.removeDelimiterSyntax("//[@][#]\n1,2,3"));
    }

    @Test
    void errorMessageForNegatives() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            calcUtils.addStrings("-1");
        });
    }

    @Test
    void greaterThan1000() {
        Assertions.assertEquals(1, calcUtils.addStrings("1001,1"));
        Assertions.assertEquals(1001, calcUtils.addStrings("1000,1"));
    }

    @Test
    void anyLengthDelimiter() {
        List<String> expected = new ArrayList<>();
        expected.add("@@@");

        Assertions.assertEquals(expected, calcUtils.getDelimiter("//[@@@]\n1@@@3"));
    }

    @Test
    void getListOfDelimiters() {
        List<String> expected = new ArrayList<>();
        expected.add("@@");
        expected.add("###");
        Assertions.assertEquals(expected, calcUtils.getListOfDelimiters("//[@@][###]\n"));
    }

    @Test
    void splitStrings() {
        List<String> delimiter = new ArrayList<>();
        delimiter.add("@");
        delimiter.add("##");

        List<String> expected = new ArrayList<>();
        expected.add("1");
        expected.add("2");
        expected.add("3");

        Assertions.assertEquals(expected, calcUtils.splitStrings("//[@][##]\n1@2##3", delimiter));

    }

    @Test
    void finalTest() {
        Assertions.assertEquals(5, calcUtils.addStrings("//[@][##]\n1@1##3"));
    }
}
