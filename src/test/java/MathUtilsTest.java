import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Annotations that can be used for conditional executions and assumptions
 * @EnabledOnOs(OS.LINUX)
 * @EnabledOnJre(JRE.JAVA_11)
 * @EnabledIf()
 * @EnabledIfSystemProperty()
 * @EnabledIfEnvironmentVariable()
 *
 * Tag helps to configure the test run to run specific methods/class
 */

@DisplayName("When running MathUtilsTest")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MathUtilsTest {

    MathUtils mathUtils;
    TestInfo testInfo;
    TestReporter testReporter;

    @BeforeAll
    void beforeAllInit() {
        System.out.println("This needs to run before all");
    }

    @BeforeEach
    void init(TestInfo testInfo, TestReporter testReporter) {
        this.testInfo = testInfo;
        this.testReporter = testReporter;
        mathUtils = new MathUtils();
    }

    @AfterEach
    void cleanup() {
        System.out.println("Cleaning up.....");
    }

    @Test
    @DisplayName("Testing add method")
    @Tag("Math")
    void test() {
        int expected = 3;
        int actual = mathUtils.add(1,2);
        assertEquals(expected, actual, () -> "should return expected sum " + expected + " and returned sum is " + actual);
    }

    @Test
    @Tag("Math")
    void testDivide() {
        boolean isServerUp = true;
        assumeTrue(isServerUp);
        assertThrows(ArithmeticException.class, () -> mathUtils.divide(1, 0), "Divide by zero should throw");
    }

    @RepeatedTest(3)
    @Tag("Circle")
    void testComputeCircleRadius(RepetitionInfo repetitionInfo) {
        if(repetitionInfo.getCurrentRepetition() == 2) {
            assertEquals(314.1592653589793, mathUtils.computeCircleArea(10), "Should return right circle area");
        }
        assertEquals(314.1592653589793, mathUtils.computeCircleArea(10), "Should return right circle area");
    }

    @Test
    @Disabled
    @DisplayName("Testing disabled")
    void testDisabled() {
        fail("This test should be disabled");
    }

    @Nested
    @DisplayName("arithmetic operation")
    @Tag("Math")
    class ArithmeticOperation {

        @Test
        @DisplayName("When adding two positive numbers")
        void testAddPositive() {
            assertEquals(2, mathUtils.add(1, 1), "should return the right sum");
        }

        @Test
        @DisplayName("When adding two positive numbers")
        void testAddNegative() {
            assertEquals(-1, mathUtils.add(-1, 0), "should return the right sum");
        }
    }

    @Test
    @DisplayName("Multiply method - assert all")
    @Tag("Math")
    void testMultiply() {
        testReporter.publishEntry("Running " + testInfo.getDisplayName() + " with tags " + testInfo.getTags());
        assertAll(
                () -> assertEquals(4, mathUtils.multiply(2,2), "should return the right product"),
                () -> assertEquals(0, mathUtils.multiply(2,0), "should return the right product"),
                () -> assertEquals(-2, mathUtils.multiply(2,-1), "should return the right product")
        );
    }
}


