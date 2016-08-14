package galvin.pandoc;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class OptionsIO
{
    public static Options readYaml( File file ) throws FileNotFoundException, YamlException {
        YamlReader reader = new YamlReader( new FileReader( file ) );
        Options result = reader.read( Options.class );
        return result;
    }
    
    public static void writeTaml( Options options, File file ) throws IOException {
        YamlWriter writer = new YamlWriter(new FileWriter( file ));
        writer.write(options);
        writer.close();
    }
}
