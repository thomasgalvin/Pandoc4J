/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galvin.pandoc;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author galvint
 */
public class RenderTest {
    
    public RenderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testRender() throws Exception {
        File source = new File("target/test-classes/lorem.md");
        File dest = new File("target/test-classes/lorem.html");
        
        Options options = new Options();
        options.getSources().add( source );
        options.setOutput( dest );
        options.setFrom( Format.markdown );
        options.setTo( Format.html5 );
        
        Pandoc pandoc = new Pandoc( new File("/usr/local/bin/pandoc") );
        pandoc.renderFilesToFile( options );
    }
}
