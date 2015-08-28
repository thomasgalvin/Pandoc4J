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
    private static final String HEADING = "<h1>Lorem ipsum dolor sit amet</h1>\n\n";
    private static final String GREEKING = "Lorem ipsum dolor sit amet, consectetur "
        + "adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore "
        + "magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco "
        + "laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor "
        + "in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla "
        + "pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa "
        + "qui officia deserunt mollit anim id est laborum.\n\n";
    
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
    public void testFilesToFile() throws Exception {
        File source = new File("target/test-classes/lorem.md");
        File expected = new File("target/test-classes/expected.html");
        File output = new File("target/test-classes/file-to-file.html");
        
        Options options = new Options();
        options.setFrom( Format.markdown );
        options.setTo( Format.html5 );
        
        Pandoc pandoc = new Pandoc( new File("/usr/local/bin/pandoc") );
        pandoc.render( options, source, output );
        
        String expectedHtml = FileUtils.readFileToString( expected );
        String actualHtml = FileUtils.readFileToString( output );
        assertEquals( "Generated HTML did not match expected", expectedHtml, actualHtml );
    }
    
    @Test
    public void testStringToFile() throws Exception {
        File source = new File("target/test-classes/lorem.md");
        File expected = new File("target/test-classes/expected.html");
        File output = new File("target/test-classes/string-to-file.html");
        
        String expectedHtml = FileUtils.readFileToString( expected );
        String sourceText = FileUtils.readFileToString( source );
        
        Options options = new Options();
        options.setFrom( Format.markdown );
        options.setTo( Format.html5 );
        
        Pandoc pandoc = new Pandoc( new File("/usr/local/bin/pandoc") );
        pandoc.render( options, sourceText, output );
        
        String actualHtml = pandoc.render( options, source );
        assertEquals( "Generated HTML did not match expected", expectedHtml, actualHtml );
    }
    
    @Test
    public void testFilesToString() throws Exception {
        File source = new File("target/test-classes/lorem.md");
        File expected = new File("target/test-classes/expected.html");
        
        Options options = new Options();
        options.setFrom( Format.markdown );
        options.setTo( Format.html5 );
        
        Pandoc pandoc = new Pandoc( new File("/usr/local/bin/pandoc") );
        String actualHtml = pandoc.render( options, source );
        String expectedHtml = FileUtils.readFileToString( expected );
        assertEquals( "Generated HTML did not match expected", expectedHtml, actualHtml );
    }
    
    @Test
    public void testStringToString() throws Exception {
        File source = new File("target/test-classes/lorem.md");
        File expected = new File("target/test-classes/expected.html");
        
        String expectedHtml = FileUtils.readFileToString( expected );
        String sourceText = FileUtils.readFileToString( source );
        
        Options options = new Options();
        options.setFrom( Format.markdown );
        options.setTo( Format.html5 );
        
        Pandoc pandoc = new Pandoc( new File("/usr/local/bin/pandoc") );
        String actualHtml = pandoc.render( options, sourceText );
        
        assertEquals( "Generated HTML did not match expected", expectedHtml, actualHtml );
    }
    
    @Test
    public void largeFileTest() throws Exception {
        int size = HEADING.length() * 25 + GREEKING.length() * 2500;
        StringBuilder text = new StringBuilder(size);
        for( int i = 0; i < 25; i++ ){
            text.append( HEADING );
            for( int j = 0; j < 100; j++ ){
                text.append( GREEKING );
            }
        }
        
        Options options = new Options();
        options.setFrom( Format.markdown );
        options.setTo( Format.html5 );
        options.setStandalone( Boolean.TRUE );
        
        Pandoc pandoc = new Pandoc( new File("/usr/local/bin/pandoc") );
        String actualHtml = pandoc.render( options, text.toString() );
        System.out.println( actualHtml );
    }
}
