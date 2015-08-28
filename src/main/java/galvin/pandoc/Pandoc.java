package galvin.pandoc;

import galvin.StreamSucker;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pandoc {
    private File executable;
    
    ////////
    
    public String render( Options options, File source ) throws IOException {
        List<File> sources = new ArrayList();
        sources.add( source );
        return Pandoc.this.render( options, sources );
    }
    
    public String render( Options options, List<File> sources ) throws IOException {
        if( sources != null && !sources.isEmpty() ){
            options = options.withSources( sources );
        }
        
        return run( options, null );
    }
    
    public String render( Options options, String source ) throws IOException {
        return run( options, source );
    }
    
    ////////
    

    public void render( Options options, File source, File output ) throws IOException {
        List<File> sources = new ArrayList();
        sources.add( source );
        Pandoc.this.render( options, sources, output );
    }
    
    public void render( Options options, List<File> sources, File output ) throws IOException {
        if( sources != null && !sources.isEmpty() ){
            options = options.withSources( sources );
        }
        
        if( output != null ) {
            options = options.withOutput( output );
            verifyOutput( options );
        }

        run( options, null );
    }
    
    public void render( Options options, String source, File output ) throws IOException {
        if( output != null ) {
            options = options.withOutput( output );
            verifyOutput( options );
        }

        run( options, source );
    }

    //////////
    
    private String run( Options options, String source ) throws IOException {
        if( options.getExecutable() == null ) {
            options = options.withExecutable( executable );
        }
        
        boolean fromString = !StringUtils.isBlank( source );
        
        if( !fromString ) {
            verifyInput( options );
        }
        
        String[] command = options.getPandocCommand();
        Process p = Runtime.getRuntime().exec( command );

        StreamSucker errorStream = new StreamSucker( p.getErrorStream(), "pandoc error" );
        errorStream.start();
        
        if( fromString ) {
            OutputStream out = p.getOutputStream();
            IOUtils.write( source, out );
            IOUtils.closeQuietly( out );
        }

        String result = IOUtils.toString( p.getInputStream() );

        try {
            p.waitFor();
        }
        catch( InterruptedException ie ) {
            throw new IOException( "An error occured while waiting for Pandoc to exit", ie );
        }

        return result;
    }

    private void print( String[] command ) {
        System.out.println( "" );
        System.out.println( "" );
        System.out.println( "" );
        System.out.println( "" );
        for( String cmd : command ) {
            System.out.print( cmd );
            System.out.print( " " );
        }
        System.out.println( "" );
        System.out.println( "" );
        System.out.println( "" );
        System.out.println( "" );
    }

    private void verifyInput( Options options )
        throws IOException {
        List<File> input = options.getSources();
        if( input == null || input.isEmpty() ) {
            throw new IOException( "No source file(s) specified" );
        }

        for( File file : input ) {
            if( !file.canRead() ) {
                throw new IOException( "Cannot read file: " + file );
            }
        }
    }

    private void verifyOutput( Options options )
        throws IOException {
        File output = options.getOutput();

        if( output == null ) {
            throw new IOException( "Output file must be specified" );
        }

        output.getParentFile().mkdirs();
        if( !output.getParentFile().exists() ) {
            throw new IOException( "Output directory does not exist: " + output.getParentFile().getAbsolutePath() );
        }

        if( !output.exists() ) {
            if( !output.getParentFile().canWrite() ) {
                throw new IOException( "Cannot write to file: " + output.getAbsolutePath() );
            }
        }
        else if( !output.canWrite() ) {
            throw new IOException( "Cannot write to file: " + output.getAbsolutePath() );
        }
    }

}
