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
 * The Tests class specifies a test battery implemented using the JUnit tool.
 * These tests use the test files from the Mooshak as input, generating as output
 * the expected result when running these tests.
 * This class is implemented for the Gossip project.
 * However, its adaptation for the remaining problems to be performed
 * throughout the semester is trivial.
 * In order to use this class you must include the JUnit 4 library in your execution environment.
 * Ask for help in the lab sessions, if necessary!
 *
 * A classe Tests especifica um conjunto de testes implementado recorrendo 'a ferramenta
 * JUnit. Estes testes usam como input os ficheiros de teste do Mooshak, gerando, como
 * output, o resultado esperado na execucao desses testes.
 * A classe esta implementada para os testes do projecto Gossip.
 * No entanto, a sua adaptacao para os restantes problemas a realizar  * ao longo do semestre 'e trivial.
 * Para poder usar esta classe tem de incluir no seu ambiente de execucao a biblioteca JUnit 4.
 * Peca ajuda nas sessoes de laboratorio, se necessario!
 */
public class Tests {
    /**
     * Use the following lines to specify the tests you want to perform.
     * In this example file, created for the Gossip project, we have 17 tests to perform.
     * For each input file, there is a corresponding output file. For example, the expected
     * result for the test 1_in.txt is 1_out.txt. You do not need to do anything else in the
     * rest of the class. Just configure this sequence of tests! This is already done for
     * this project. For the other projects, you must configure the tests.
     *
     * Use as linhas que se seguem para especificar os testes que vai realizar.
     * Neste ficheiro de exemplo, criado para o projecto Gossip, temos 17 testes a realizar.
     * Para cada ficheiro de input, existe um ficheiro de output correspondente. Por exemplo,
     * o resultado esperado para o teste 1_in.txt e 1_out.txt . Nao tem de fazer mais nada no
     * resto da classe. Basta configurar esta sequencia de testes! Isto ja esta feito para este
     * projecto. Para os outros projectos, tem de configurar os testes.
     */
    @Test
    public void test1() { test("1_in_base.txt","1_out_base.txt"); }
    @Test public void test2() { test("2_in_base_GN.txt","2_out_base_GN.txt"); }
    @Test public void test3() { test("3_in_base_EP.txt","3_out_base_EP.txt"); }



    /**
     * The BASE constant specifies the directory where the test files are located.
     */
    private static final File BASE = new File("tests");

    /**
     * The consoleStream variable is used to redirect the output of the program to the console.
     * The outContent variable is used to capture the output of the program.
     */
    private PrintStream consoleStream;
    /**
     * The outContent variable is used to capture the output of the program.
     */
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    /**
     * The setup method is executed before each test.
     * It redirects the output of the program to the console and captures the output of the program.
     */
    @Before
    public void setup() {
        consoleStream = System.out;
        System.setOut(new PrintStream(outContent));
    }

    /**
     * The test method is used to perform a test.
     * It receives as input the name of the input file and the name of the output file.
     * It reads the input file and the output file, and then executes the program with the input file.
     * It compares the output of the program with the expected output.
     * @param intput the name of the input file
     * @param output the name of the output file
     */
    public void test(String intput, String output) {
        test(new File(BASE, intput), new File(BASE, output));
    }

    /**
     * The test method is used to perform a test.
     * @param input the input file
     * @param output the output file
     */
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
            Class<?> mainClass = Class.forName("Main");
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

    /**
     * The removeCarriages method is used to remove the carriage returns from a string.
     * @param s the string
     * @return the string without carriage returns
     */
    private static String removeCarriages(String s) {
        return s.replaceAll("\r\n", "\n");
    }

}