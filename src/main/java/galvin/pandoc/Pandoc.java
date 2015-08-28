package galvin.pandoc;

import galvin.StreamSucker;
import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pandoc {
    private File executable;

    public void renderFilesToFile( Options options ) throws IOException {
        verifyOutput( options );
        verifyInput( options );

        options.setExecutable( executable );

        String[] command = options.getPandocCommand();
        Process p = Runtime.getRuntime().exec( command );
        
        StreamSucker errorStream = new StreamSucker( p.getErrorStream(), "pandoc error" );
        StreamSucker messageStream = new StreamSucker( p.getInputStream(), "pandoc mesage" );

        errorStream.start();
        messageStream.start();

        try {
            p.waitFor();
        }
        catch( InterruptedException ie ) {
            throw new IOException( "An error occured while waiting for Pandoc to exit", ie );
        }
    }
    
    private void print( String[] command ){
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

    private void verifyInput( Options options ) throws IOException {
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

    private void verifyOutput( Options options ) throws IOException {
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
