package at.hsol.fountainizer.core.api.parser;

/**
 * @author Felix Batusic
 */
public enum LineType {
    // @formatter:off
    HEADING(true, false, false),
    CHARACTER(true, false, false),
    DIALOGUE(false, false, false),
    PARENTHETICAL(false, false, false),
    TRANSITION(false, false, false),
    ACTION(false, false, false),
    LYRICS(false, true, false),
    CENTERED(false, true, false),
    PAGEBREAK(false, false, false),
    LINENUMBER(false, false, false),
    EMPTY(false, false, false),
    TITLEPAGE(false, false, false),

    TITLEPAGE_TITLE(false, false, false),
    TITLEPAGE_CENTERED(false, true, false),
    TITLEPAGE_LEFT(false, false, false),
    TITLEPAGE_RIGHT(false, false, false);
    // @formatter:on

    private final boolean uppercase;
    private final boolean centered;
    private final boolean underlined;

    LineType(boolean uppercase, boolean centered, boolean underlined) {
        this.uppercase = uppercase;
        this.centered = centered;
        this.underlined = underlined;
    }


    public boolean isUppercase() {
        return uppercase;
    }


    public boolean isCentered() {
        return centered;
    }


    public boolean isUnderlined() {
        return underlined;
    }


}
