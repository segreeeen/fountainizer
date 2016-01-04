package at.hsol.fountainizer.parser.types;

/**
 * @author Felix Batusic
 */
public final class ParserConstants {
    
    /**
     * Matches headings
     */
    public static final String L_HEADING = "INT(.*?)|EXT(.*?)|EST(.*?)|INT./EXT(.*?)|INT/EXT(.*?)|I/E(.*?)|\\.(.*?)";
    
    /**
     * Matches "text ^"  
     */
    public static final String L_DUAL_DIALOGUE = "(.*?)\\s+\\^";
    
    /**
     * Matches "@text"
     */
    public static final String L_CHARACTER = "@(.*?)";
    
    /**
     * Matches (text)
     */
    public static final String L_PARENTHETICAL = "\\((.*?)\\)";
    
    /**
     * Matches "text TO:"
     */
    public static final String L_TRANSITION_1 = "(.*?)TO:";
    
    /**
     * Matches >text
     */
    public static final String L_TRANSITION_2 = ">(.*?)";
    
    /**
     * Matches ~
     */
    public static final String L_LYRICS = "~(.*?)";
    
    /**
     * Matches < text >
     */
    public static final String L_CENTERED = "\\>(.*?)\\<";
    
    /**
     * Matches === 
     */
    public static final String L_PAGEBREAK = "={3,}+";
    
    /**
     * Matches /* 
     */
    public static final String LT_COMMENTARY_START = "(.*?)/\\*(.*?)";
    
    /**
     * Matches * / 
     */
    public static final String LT_COMMENTARY_END = "(.*?)\\*/(.*?)";
    
    /**
     * Matches " *text" (without quotes)
     */
    public static final String LT_ITALIC_START = "(.*?)(?<!\\*)\\*(?!\\s)(?!\\*)(.*?)";
    
    /**
     * Matches "text* " (without quotes)
     */
    public static final String LT_ITALIC_END = "(.*?)(?<!\\*)(?<!\\s)\\*(?!\\*)(.*?)";
    
    /**
     * Matches " **text" (without quotes)
     */
    public static final String LT_BOLD_START = "(.*?)(?<!\\*)\\*\\*(?!\\s)(?!\\*)(.*?)";
    
    /**
     * Matches "text** " (without quotes)
     */
    public static final String LT_BOLD_END = "(.*?)(?<!\\*)(?<!\\s)\\*\\*(?!\\*)(.*?)";
    
    /**
     * Matches " ***text" (without quotes)
     */
    public static final String LT_BITALICS_START = "(.*?)(?<!\\*)\\*\\*\\*(?!\\s)(?!\\*)(.*?)";
    
    /**
     * Matches "text*** " (without quotes)
     */
    public static final String LT_BITALICS_END = "(.*?)(?<!\\*)(?<!\\s)\\*\\*\\*(?!\\*)(.*?)";
    
    /**
     * Matches " _text" (without quotes)
     */
    public static final String LT_UNDERLINE_START = "(.*?)(?<!_)_(?!\\s)(?!_)(.*?)";
    
    /**
     * Matches "text_ " (without quotes)
     */
    public static final String LT_UNDERLINE_END = "(.*?)(?<!_)(?<!\\s)_(?!_)(.*?)";
    
    /**
     * Matches [[
     */
    public static final String LT_NOTE_START = "(.*?)\\[\\[(.*?)";
    
    /**
     * Matches ]]
     */
    public static final String LT_NOTE_END = "(.*?)\\]\\](.*?)";
    
    /**
     * Matches TitlePage linestarts for the centered text
     */
    public static final String TP_CENTERED = "^(\\s+)?[Tt]itle:(.*?)|^(\\s+)?[cC]redit:(.*?)|^(\\s+)?[Aa]uthor:(.*?)|^(\\s+)?[Ss]ource:(.*?)";
    
    /**
     * Matches TitlePage linestarts for the down-left text
     */
    public static final String TP_LEFT = "^(\\s+)?[Dd]raft [Dd]ate:(.*?)|^(\\s+)?[Cc]ontact:(.*?)|^(\\s+)?[Nn]otes:(.*?)|^(\\s+)?[Cc]opyright:(.*?)";
    
}
