import junit.framework.*;

public class TriangleTest extends TestCase {

  public static void main (String[] args) {
    junit.textui.TestRunner.run(TriangleTest.class);
  }

  @org.junit.jupiter.api.Test
  public void testCase1() {
    Triangle x1 = new Triangle();
    assertEquals("Is triangle scalene",String.valueOf(x1.checkTriangle(4, 26, 28)));
  }

  @org.junit.jupiter.api.Test
  public void testCase2() {
    Triangle x45 = new Triangle();
    assertEquals("Is triangle isosceles ",String.valueOf(x45.checkTriangle(55, 55, 56)));
  }

  @org.junit.jupiter.api.Test
  public void testCase3() {
    Triangle x55 = new Triangle();
    assertEquals("Is triangle equilateral ",String.valueOf(x55.checkTriangle(61, 61, 61)));
  }

  @org.junit.jupiter.api.Test
  public void testCase4() {
    Triangle x73 = new Triangle();
    assertEquals("Not a triangle",String.valueOf(x73.checkTriangle(90, 22, 52)));
  }

  }
