package at.hsol.fountainizer.parser;

/**
 * @author Felix Batusic
 */
final class ParserConstants {

	/**
	 * Matches headings
	 */
	static final String L_HEADING = "INT[\\.?](.*?)|EXT[\\.?](.*?)|EST[\\.?](.*?)|INT[\\.?]/EXT[\\.?](.*?)|I[\\.?]/E[\\.?](.*?)|\\.(.*?)";

	/**
	 * Matches int heading
	 */
	static final String L_HEADING_INT = "INT[\\.?](.*?)";

	/**
	 * Matches ext heading
	 */
	static final String L_HEADING_EXT = "EXT[\\.?](.*?)";

	/**
	 * Matches est heading
	 */
	static final String L_HEADING_EST = "EST[\\.?](.*?)";

	/**
	 * Matches int/ext heading
	 */
	static final String L_HEADING_INT_EXT = "INT[\\.?]/EXT[\\.?](.*?)|INT/EXT(.*?)|I/E(.*?)";

	/**
	 * Matches . (heading start)
	 */
	static final String L_HEADING_CUSTOM = "\\.(.*?)";

	/**
	 * Matches "text ^"
	 */
	static final String L_DUAL_DIALOGUE = "(.*?)\\s+\\^";

	/**
	 * Matches "@text"
	 */
	static final String L_CHARACTER = "@(.*?)";

	/**
	 * Matches (text)
	 */
	static final String L_PARENTHETICAL = "\\((.*?)\\)";

	/**
	 * Matches "text TO:"
	 */
	static final String L_TRANSITION_1 = "(.*?)TO:";

	/**
	 * Matches >text
	 */
	static final String L_TRANSITION_2 = ">(.*?)";

	/**
	 * Matches ~
	 */
	static final String L_LYRICS = "~(.*?)";

	/**
	 * Matches < text >
	 */
	static final String L_CENTERED = "\\>(.*?)\\<";

	/**
	 * Matches ===
	 */
	static final String L_PAGEBREAK = "={3,}+";

	/**
	 * Matches /*
	 */
	static final String LT_COMMENTARY_START = "(.*?)/\\*(.*?)";

	/**
	 * Matches * /
	 */
	static final String LT_COMMENTARY_END = "(.*?)\\*/(.*?)";

    /**
     * Matches #
     */
    static final String LT_COMMENTARY_LINE = "(.*?)#(.*?)";

	/**
	 * Matches " *text" (without quotes)
	 */
	static final String LT_ITALIC_START = "(.*?)(?<!\\*)\\*(?!\\s)(?!\\*)(.*?)";

	/**
	 * Matches "text* " (without quotes)
	 */
	static final String LT_ITALIC_END = "(.*?)(?<!\\*)(?<!\\s)\\*(?!\\*)(.*?)";

	/**
	 * Matches " **text" (without quotes)
	 */
	static final String LT_BOLD_START = "(.*?)(?<!\\*)\\*\\*(?!\\s)(?!\\*)(.*?)";

	/**
	 * Matches "text** " (without quotes)
	 */
	static final String LT_BOLD_END = "(.*?)(?<!\\*)(?<!\\s)\\*\\*(?!\\*)(.*?)";

	/**
	 * Matches " ***text" (without quotes)
	 */
	static final String LT_BITALICS_START = "(.*?)(?<!\\*)\\*\\*\\*(?!\\s)(?!\\*)(.*?)";

	/**
	 * Matches "text*** " (without quotes)
	 */
	static final String LT_BITALICS_END = "(.*?)(?<!\\*)(?<!\\s)\\*\\*\\*(?!\\*)(.*?)";

	/**
	 * Matches " _text" (without quotes)
	 */
	static final String LT_UNDERLINE_START = "(.*?)(?<!_)_(?!\\s)(?!_)(.*?)";

	/**
	 * Matches "text_ " (without quotes)
	 */
	static final String LT_UNDERLINE_END = "(.*?)(?<!_)(?<!\\s)_(?!_)(.*?)";

	/**
	 * Matches [[
	 */
	static final String LT_NOTE_START = "(.*?)\\[\\[(.*?)";

	/**
	 * Matches ]]
	 */
	static final String LT_NOTE_END = "(.*?)\\]\\](.*?)";

	/**
	 * Matches TitlePageLines linestarts for the centered text
	 */
	static final String TP_CENTERED = "^(\\s+)?[cC]redit:(.*?)|^(\\s+)?[Aa]uthor:(.*?)|^(\\s+)?[Ss]ource:(.*?)";

	/**
	 * Matches TitlePageLines linestarts for the down-left text
	 */
	static final String TP_LEFT = "^(\\s+)?[Cc]ontact:(.*?)|^(\\s+)?[Nn]otes:(.*?)|^(\\s+)?[Cc]opyright:(.*?)|^(\\s+)?[Dd]raft [Dd]ate:(.*?)";

	static final String TP_TITLE = "^(\\s+)?[Tt]itle:(.*?)";

}
