package galvin.pandoc;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import galvin.KeyValue;
import lombok.AllArgsConstructor;
import lombok.experimental.Wither;

/**
 * A wrapper for all of Pandoc's options. For an in-depth exaplnation of what
 * all of these options do, visit: http://pandoc.org/README.html
 */
@Data @NoArgsConstructor @AllArgsConstructor
public class Options
{
    /**
     * This constructor is necessary for the @Wither annotation to work
     * correctly.
     * @param executable - the path to the Pandoc exe. 
     * @param sources - the input files
     * @param output the file to write the resulting document to. Can be null.
     */
    public Options( File executable, 
                    List<File> sources,
                    File output ){
        this.executable = executable;
        this.sources = sources;
        this.output = output;
    }
    
    /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// ///
    
    @Wither private File executable;
    @Wither private List<File> sources = new ArrayList();
    @Wither private File output;
    
    /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// ///
    
    private Format from;
    private Format to;
    
    private Boolean strict;
    private Boolean parseRaw;
    private Boolean smart;
    private Boolean oldDashes;
    private Boolean normalize;
    private Boolean preserveTabs;
    private Boolean noWrap;
    private Boolean tableOfContents;
    private Boolean noHighlight;
    private Boolean selfContained;
    private Boolean offline;
    private Boolean html5;
    private Boolean htmlQTags;
    private Boolean ascii;
    private Boolean referenceLinks;
    private Boolean atxHeaders;
    private Boolean chapters;
    private Boolean numberSections;
    private Boolean noTexLigatures;
    private Boolean incremental;
    private Boolean sectionDivs;
    private Boolean natlib;
    private Boolean gladtex;
    private Boolean trace;
    private Boolean dumpArgs;
    private Boolean ignoreArgs;
    private Boolean standalone;
    private Boolean listings;
    
    private Integer baseHeaderLevel;
    private Integer tabStop;
    private Integer columns;
    private Integer numberOffset;
    private Integer slideLevel;
    private Integer epubChapterLevel;
    
    private String indentedCodeClasses;
    private String highlightStyle;
    private String defaultImgExtension;
    private String idPrefix;
    private String titlePrefix;
    private String latexEngineOpt;
    
    private File dataDir;
    private File filterExecutable;
    private File template;
    private File includeInHeader;
    private File includeBeforeBody;
    private File includeAfterBody;
    private File referenceODT;
    private File referenceDOCX;
    private File epubStylesheet;
    private File epubCoverImage;
    private File epubMetadata;
    private File epubEmbededFont;
    private File latexEngine;
    private File bibliography;
    private File csl;
    private File citationAbbreviations;
    private File extractMedia;
    
    private URL css;
    private URL katexStylesheet;
    
    private List<KeyValue<String, String>> metadata = new ArrayList();
    private List<KeyValue<String, String>> variables = new ArrayList();
    
    private EmailObfuscation emailObfuscation;
    private TrackChanges trackChanges;
    
    private Boolean latextmathml;
    private URL latextmathmlURL;
    
    private Boolean asciitmathml;
    private URL asciitmathmlURL;
    
    private Boolean mathml;
    private URL mathmlURL;
    
    private Boolean mimetex;
    private URL mimetexURL;
    
    private Boolean jsmath;
    private URL jsmathURL;
    
    private Boolean mathjax;
    private URL mathjaxURL;
    
    private Boolean webtex;
    private URL webtexURL;
    
    private Boolean katex;
    private URL katexURL;
    
    public void verify() {
        if( executable == null ) {
            throw new IllegalArgumentException( "Pandoc executable must be specified" );
        }
        
        if( !executable.exists() ) {
            throw new IllegalArgumentException( "Pandoc executable does not exist at specified location: " + executable.getAbsolutePath() );
        }
        
        if( !executable.canExecute()) {
            throw new IllegalArgumentException( "Pandoc executable cannot be executed by current user" );
        }
    }
    
    public String[] getPandocCommand() {
        List<String> commandSegments = new ArrayList();
        commandSegments.add( executable.getAbsolutePath() );
        
        commandSegments.add( getFrom( from )  );
        commandSegments.add( getTo( to )  );
        
        commandSegments.add( getFile( output, "--output" )  );
        commandSegments.add( getBoolean( standalone, "-s" )  );
        commandSegments.add( getBoolean( strict, "--strict" )  );
        commandSegments.add( getBoolean( oldDashes, "--old-dashes" )  );
        commandSegments.add( getBoolean( normalize, "--normalize" )  );
        commandSegments.add( getBoolean( preserveTabs, "--preserve-tabs" )  );
        commandSegments.add( getBoolean( noWrap, "--no-wrap" )  );
        commandSegments.add( getBoolean( tableOfContents, "--toc" )  );
        commandSegments.add( getBoolean( noHighlight, "--no-highlight" )  );
        commandSegments.add( getBoolean( selfContained, "--self-contained" )  );
        commandSegments.add( getBoolean( offline, "--offline" )  );
        commandSegments.add( getBoolean( html5, "--html5" )  );
        commandSegments.add( getBoolean( htmlQTags, "--html-q-tags" )  );
        commandSegments.add( getBoolean( ascii, "--ascii" )  );
        commandSegments.add( getBoolean( referenceLinks, "--reference-links" )  );
        commandSegments.add( getBoolean( atxHeaders, "--atx-headers" )  );
        commandSegments.add( getBoolean( chapters, "--chapters" )  );
        commandSegments.add( getBoolean( numberSections, "--numberSections" )  );
        commandSegments.add( getBoolean( noTexLigatures, "--no-tex-ligatures" )  );
        commandSegments.add( getBoolean( incremental, "--incremental" )  );
        commandSegments.add( getBoolean( sectionDivs, "--section-divs" )  );
        commandSegments.add( getBoolean( natlib, "--natlib" )  );
        commandSegments.add( getBoolean( gladtex, "--gladtex" )  );
        commandSegments.add( getBoolean( trace, "--trace" )  );
        commandSegments.add( getBoolean( dumpArgs, "--dump-args" )  );
        commandSegments.add( getBoolean( ignoreArgs, "--ignore-args" )  );
        commandSegments.add( getBoolean( listings, "--listings" )  );
        
        commandSegments.add( getInteger( baseHeaderLevel, "--base-header-level" )  );
        commandSegments.add( getInteger( tabStop, "--tab-stop" )  );
        commandSegments.add( getInteger( columns, "--columns" )  );
        commandSegments.add( getInteger( numberOffset, "--number-offset" )  );
        commandSegments.add( getInteger( slideLevel, "--slide-level" )  );
        commandSegments.add( getInteger( epubChapterLevel, "--epub-chapter-level" )  );
        
        commandSegments.add( getString( indentedCodeClasses, "--indented-code-classes" )  );
        commandSegments.add( getString( highlightStyle, "--highlight-style" )  );
        commandSegments.add( getString( defaultImgExtension, "--default-image-extension" )  );
        commandSegments.add( getString( idPrefix, "--id-prefix" )  );
        commandSegments.add( getString( titlePrefix, "--title-prefix" )  );
        commandSegments.add( getString( latexEngineOpt, "--latex-enging-opt" )  );
        
        commandSegments.add( getFile( dataDir, "--data-dir" )  );
        commandSegments.add( getFile( filterExecutable, "--filter" )  );
        commandSegments.add( getFile( template, "--template" )  );
        commandSegments.add( getFile( includeInHeader, "--include-in-header" )  );
        commandSegments.add( getFile( includeBeforeBody, "--include-before-body" )  );
        commandSegments.add( getFile( includeAfterBody, "--include-after-body" )  );
        commandSegments.add( getFile( referenceODT, "--reference-odt" )  );
        commandSegments.add( getFile( referenceDOCX, "--reference-docx" )  );
        commandSegments.add( getFile( epubStylesheet, "--epub-stylesheet" )  );
        commandSegments.add( getFile( epubCoverImage, "--epub-cover-image" )  );
        commandSegments.add( getFile( epubMetadata, "--epub-metadata" )  );
        commandSegments.add( getFile( epubEmbededFont, "--epub-embed-font" )  );
        commandSegments.add( getFile( latexEngine, "--latex-engine" )  );
        commandSegments.add( getFile( bibliography, "--bibliography" )  );
        commandSegments.add( getFile( csl, "--csl" )  );
        commandSegments.add( getFile( citationAbbreviations, "--citation-abbreviations" )  );
        commandSegments.add( getFile( extractMedia, "--extract-media" )  );
        
        commandSegments.add( getURL( css, "--css" )  );
        commandSegments.add( getURL( katexStylesheet, "--katex-stylesheet" )  );
        
        if( metadata != null ){
            for( KeyValue keyValue : metadata ) {
                commandSegments.add( getMetadata( keyValue )  );
            }
        }
        
        if( variables != null ){
            for( KeyValue keyValue : variables ) {
                commandSegments.add( getVariable( keyValue )  );
            }
        }
        
        commandSegments.add( getEmailObfuscation( emailObfuscation )  );
        commandSegments.add( getTrackChanges( trackChanges )  );
        
        commandSegments.add( getBooleanUrl( latextmathml, latextmathmlURL, "--latexmathml" )  );
        commandSegments.add( getBooleanUrl( asciitmathml, asciitmathmlURL, "--asciitmathml" )  );
        commandSegments.add( getBooleanUrl( mathml, mathmlURL, "--mathml" )  );
        commandSegments.add( getBooleanUrl( mimetex, mimetexURL, "--mimetex" )  );
        commandSegments.add( getBooleanUrl( jsmath, jsmathURL, "--jsmath" )  );
        commandSegments.add( getBooleanUrl( mathjax, mathjaxURL, "--mathjax" )  );
        commandSegments.add( getBooleanUrl( webtex, webtexURL, "--webtex" )  );
        commandSegments.add( getBooleanUrl( katex, katexURL, "--katex" )  );
        
        for( File file : sources ){
            commandSegments.add( file.getAbsolutePath() );
        }
        
        Iterator<String> iter = commandSegments.iterator();
        while( iter.hasNext() ) {
            if( StringUtils.isBlank( iter.next() ) ){
                iter.remove();
            }
        }
        
        String[] result = new String[ commandSegments.size() ];
        commandSegments.toArray( result );
        return result;
    }
    
    private String getBoolean( Boolean flag, String name ){
        if( flag != null && flag ) {
            return name;
        }
        return "";
    }
    
    private String getFrom( Format format ){
        if( format != null ) {
            return "--from=" + format.from();
        }
        return "";
    }
    
    private String getTo( Format format ){
        if( format != null ) {
            return "--to=" + format.to();
        }
        return "";
    }
    
    private String getInteger( Integer value, String name ){
        if( value != null ) {
            return name + "=" + value.toString();
        }
        return "";
    }
    
    private String getString( String value , String name) {
        if( !StringUtils.isBlank( value ) ){
            return name + "=" + value;
        }
        return "";
    }
    
    private String getFile( File file, String name ){
        if( file != null ){
            return name + "=" + file.getAbsolutePath();
        }
        return "";
    }
    
    private String getURL( URL url, String name ){
        if( url != null ){
            return name + "=" + url.toString();
        }
        return "";
    }
    
    private String getEmailObfuscation( EmailObfuscation email ){
        if( email != null ){
            return "--email-obfuscation=" + email.name();
        }
        return "";
    }
    
    private String getTrackChanges( TrackChanges trackChanges ) {
        if( trackChanges != null ){
            return "--track-changes=" + trackChanges.name();
        }
        return "";
    }
    
    private String getMetadata( KeyValue<String, String> keyValue ) {
        if( keyValue != null ){
            String key = keyValue.getKey();
            String value = keyValue.getValue();
            
            if( !StringUtils.isBlank( key ) ){
                String result = "--metadata=" + key;
                if( !StringUtils.isBlank( value ) ) {
                    result += ":" + value;
                }
                return result;
            }
        }
        return "";
    }
    
    private String getVariable( KeyValue<String, String> keyValue ) {
        if( keyValue != null ){
            String key = keyValue.getKey();
            String value = keyValue.getValue();
            
            if( !StringUtils.isBlank( key ) ){
                String result = "--variable=" + key;
                if( !StringUtils.isBlank( value ) ) {
                    result += ":" + value;
                }
                return result;
            }
        }
        return "";
    }
    
    private String getBooleanUrl( Boolean flag, URL url, String name ){
        if( flag != null && flag ){
            String result = name;
            if( url != null )
            {
                result += url.toString();
            }
            return result;
        }
        return "";
    }
}
