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
public class HeapTest {
    
    public HeapTest() {
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
     * Test of store method, of class Heap.
     */
    @Test
    public void testStore() {
        System.out.println("store");
        int numb = 555;
        byte[] data = ByteConvertor.toByta(numb);
        Heap instance = Heap.getInstance();
        int result = instance.store(data);
        result = ByteConvertor.toInt(instance.get(result, data.length));
        assertEquals(numb, result);
    }
}
