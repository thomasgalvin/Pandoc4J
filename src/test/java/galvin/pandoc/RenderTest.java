/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galvin.pandoc;

import java.io.File;
import org.apache.commons.io.FileUtils;
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
        File expected = new File("target/test-classes/expected.html");
        File output = new File("target/test-classes/lorem.html");
        
        Options options = new Options();
        options.getSources().add( source );
        options.setOutput( output );
        options.setFrom( Format.markdown );
        options.setTo( Format.html5 );
        
        Pandoc pandoc = new Pandoc( new File("/usr/local/bin/pandoc") );
        pandoc.renderFilesToFile( options );
        
        String expectedHtml = FileUtils.readFileToString( expected );
        String actualHtml = FileUtils.readFileToString( output );
        assertEquals( "Generated HTML did not match expected", expectedHtml, actualHtml );
    }
}
