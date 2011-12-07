/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caesar.interpreter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hohy
 */
public class InterpreterStackTest {
    
    public InterpreterStackTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class InterpreterStack.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        InterpreterStack expResult = null;
        InterpreterStack result = InterpreterStack.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of popInteger method, of class InterpreterStack.
     */
    @Test
    public void testPopInteger() {
        System.out.println("popInteger");
        InterpreterStack instance = null;
        int expResult = 0;
        int result = instance.popInteger();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of popDouble method, of class InterpreterStack.
     */
    @Test
    public void testPopDouble() {
        System.out.println("popDouble");
        InterpreterStack instance = null;
        double expResult = 0.0;
        double result = instance.popDouble();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of popString method, of class InterpreterStack.
     */
    @Test
    public void testPopString() {
        System.out.println("popString");
        InterpreterStack instance = null;
        String expResult = "";
        String result = instance.popString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pushInteger method, of class InterpreterStack.
     */
    @Test
    public void testPushInteger() {
        System.out.println("pushInteger");
        int value = 0;
        InterpreterStack instance = null;
        instance.pushInteger(value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pushDouble method, of class InterpreterStack.
     */
    @Test
    public void testPushDouble() {
        System.out.println("pushDouble");
        Double value = null;
        InterpreterStack instance = null;
        instance.pushDouble(value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pushString method, of class InterpreterStack.
     */
    @Test
    public void testPushString() {
        System.out.println("pushString");
        String string = "";
        InterpreterStack instance = null;
        instance.pushString(string);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of peek method, of class InterpreterStack.
     */
    @Test
    public void testPeek() {
        System.out.println("peek");
        InterpreterStack instance = null;
        Object expResult = null;
        Object result = instance.peek();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pop method, of class InterpreterStack.
     */
    @Test
    public void testPop() {
        System.out.println("pop");
        int objectSize = 0;
        InterpreterStack instance = null;
        byte[] expResult = null;
        byte[] result = instance.pop(objectSize);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of push method, of class InterpreterStack.
     */
    @Test
    public void testPush() {
        System.out.println("push");
        byte[] array = null;
        InterpreterStack instance = null;
        instance.push(array);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
