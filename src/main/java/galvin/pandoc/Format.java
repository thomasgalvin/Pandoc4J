package galvin.pandoc;

/**
 * A list of all importable and exportable file types. These values can be
 * used as arguments to the --from / -f and --to / -t flags.
 */
public enum Format
{
    asciidoc( false, true ),
    beamer( false, true ),
    context( false, true ),
    docbook( true, true ),
    docx( false, true ),
    dzslides( false, true ),
    epub( false, true ),
    epub3( false, true ),
    fb2( false, true ),
    haddock( true, false ),
    html( true, true ),
    html5( false, true ),
    icml( false, true ),
    json( true, true ),
    latex( true, true ),
    man( false, true ),
    markdown( true, true ),
    markdown_github( true, true ),
    markdown_mmd( true, true ),
    markdown_phpextra( true, true ),
    markdown_strict( true, true ),
    mediaWiki( true, true ),
    format_native( true, true ),
    odt( false, true ),
    opendocument( false, true ),
    opml( true, true ),
    org( true, true ),
    pdf( false, true ),
    plain( false, true ),
    revealjs( false, true ),
    rst( true, true ),
    rtf( false, true ),
    slideous( false, true ),
    slidy( false, true ),
    texinfo( false, true ),
    textile( true, true );
    
    private boolean source;
    private boolean output;

    private Format( boolean source, boolean output ) {
        this.source = source;
        this.output = output;
    }

    public String from() {
        if( source ) {
            String name = name();
            if( name.startsWith( "format_") ) {
                return name.replace( "format_", "" );
            }
            return name;
        }
        throw new IllegalArgumentException( "Source format " + name() + " is not supported." );
    }

    public String to() {
        if( output ) {
            return name();
        }
        throw new IllegalArgumentException( "Output format " + name() + " is not supported." );
    }
}
