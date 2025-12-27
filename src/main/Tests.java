package main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

/**
 * The main.Tests class specifies a set of tests implemented using the JUnit tool.
 * These tests use Mooshak test files as input, generating the expected result of these tests as output.
 * <p>
 * The class is implemented for testing the contactBook.ContactBook problem, to be used in the first
 * practical class on OOP. However, adapting it to the other problems to be carried out
 * throughout the semester is trivial.
 * In order to use this class, you must include the JUnit 4 library in your execution environment.
 * See how you can do this in the first practical class of the semester!
 */
public class Tests {
    /**
     * Use the following lines to specify the tests you will perform.
     * In this example file, created for the contactBook.ContactBook project, we only have
     * 3 tests to perform. For each input file, there is a corresponding output file.
     * For example, the expected result for the test
     * 1_in_base.txt is 1_out_base.txt . You do not need to do anything else in the rest of the class.
     * Just set up this test sequence!
     */
    @Test public void test1() { test("1_in_base.txt","1_out_base.txt"); }
    @Test public void test2() { test("2_in_base_GN.txt","2_out_base_GN.txt"); }
    @Test public void test3() { test("3_in_base_EP.txt","3_out_base_EP.txt"); }


    private static final File BASE = new File("tests");

    private PrintStream consoleStream;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setup() {
        consoleStream = System.out;
        System.setOut(new PrintStream(outContent));
    }

    public void test(String intput, String output) {
        test(new File(BASE, intput), new File(BASE, output));
    }

    public void test(File input, File output) {
        consoleStream.println("Testing!");
        consoleStream.println("Input: " + input.getAbsolutePath());
        consoleStream.println("Output: " + output.getAbsolutePath());

        String fullInput = "", fullOutput = "";
        try {
            fullInput = new String(Files.readAllBytes(input.toPath()));
            fullOutput = new String(Files.readAllBytes(output.toPath()));
            consoleStream.println("INPUT ============");
            consoleStream.println(new String(fullInput));
            consoleStream.println("OUTPUT ESPERADO =============");
            consoleStream.println(new String(fullOutput));
            consoleStream.println("OUTPUT =============");
        } catch(Exception e) {
            e.printStackTrace();
            fail("Erro a ler o ficheiro");
        }

        try {
            Locale.setDefault(Locale.US);
            System.setIn(new FileInputStream(input));
            Class<?> mainClass = Class.forName("main.Main");
            mainClass.getMethod("main", String[].class).invoke(null, new Object[] { new String[0] });
        } catch (Exception e) {
            e.printStackTrace();
            fail("Erro no programa");
        } finally {
            byte[] outPrintBytes = outContent.toByteArray();
            consoleStream.println(new String(outPrintBytes));

            assertEquals(removeCarriages(fullOutput), removeCarriages(new String(outContent.toByteArray())));
        }
    }

    private static String removeCarriages(String s) {
        return s.replaceAll("\r\n", "\n");
    }

}