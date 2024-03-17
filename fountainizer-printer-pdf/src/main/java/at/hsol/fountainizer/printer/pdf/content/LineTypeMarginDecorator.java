package at.hsol.fountainizer.printer.pdf.content;

import at.hsol.fountainizer.core.api.parser.LineType;

/**
 * @author Felix Batusic
 */
public enum LineTypeMarginDecorator implements Margin {
    // @formatter:off
    HEADING(LineType.HEADING, 80.0F, 0F, 15.0F, 0.0F),
    CHARACTER(LineType.CHARACTER, 0.0F, 0F, 0.0F, 0.0F),
    DIALOGUE(LineType.DIALOGUE, 40.0F, 80F, 0.2F, 0.0F),
    PARENTHETICAL(LineType.PARENTHETICAL, 20.0F, 0F, 0.2F, 0.0F),
    TRANSITION(LineType.TRANSITION, 0F, 40F, 0F, 0.0F),
    ACTION(LineType.ACTION, 0.0F, 40F, 10F, 0F),
    LYRICS(LineType.LYRICS, 10.0F, 0F, 10.0F, 10.0F),
    CENTERED(LineType.CENTERED, 10.0F, 0F, 10.0F, 10.0F),
    PAGEBREAK(LineType.PAGEBREAK, 0F, 0F, 0F, 0F),
    LINENUMBER(LineType.LINENUMBER, 20F, 0F, 0F, 0F),
    EMPTY(LineType.EMPTY, 0F, 0F, 0F, 0F),
    TITLEPAGE(LineType.TITLEPAGE, 0F, 0F, 0F, 0F),

    TITLEPAGE_TITLE(LineType.TITLEPAGE_TITLE, 0F, 0F, 0.0F, 10.0F),
    TITLEPAGE_CENTERED(LineType.TITLEPAGE_CENTERED, 0F, 0F, 0F, 20.0F),
    TITLEPAGE_LEFT(LineType.TITLEPAGE_LEFT, 80F, 0F, 0.0F, 3.0F),
    TITLEPAGE_RIGHT(LineType.TITLEPAGE_RIGHT, 380F, 0F, 0.0F, 0.0F);
    // @formatter:on

    private final LineType lineType;
    private final float marginLeft;
    private final float marginRight;
    private final float marginTop;
    private final float marginBottom;

    LineTypeMarginDecorator(LineType lineType, float marginLeft, float marginRight, float marginTop, float marginBottom) {
        this.lineType = lineType;
        this.marginLeft = marginLeft;
        this.marginRight = marginRight;
        this.marginTop = marginTop;
        this.marginBottom = marginBottom;
    }

    @Override
    public boolean isUppercase() {
        return lineType.isUppercase();
    }

    @Override
    public boolean isCentered() {
        return lineType.isCentered();
    }

    @Override
    public boolean isUnderlined() {
        return lineType.isUnderlined();
    }

    @Override
    public float getMarginLeft() {
        return marginLeft;
    }

    @Override
    public float getMarginTop() {
        return marginTop;
    }

    @Override
    public float getMarginBottom() {
        return marginBottom;
    }

    @Override
    public float getMarginRight() {
        return marginRight;
    }

    public static LineTypeMarginDecorator getFromLineType(LineType lineType) {
        return switch (lineType) {
            case ACTION -> LineTypeMarginDecorator.ACTION;
            case EMPTY -> LineTypeMarginDecorator.EMPTY;
            case HEADING -> LineTypeMarginDecorator.HEADING;
            case CHARACTER -> LineTypeMarginDecorator.CHARACTER;
            case DIALOGUE -> LineTypeMarginDecorator.DIALOGUE;
            case PARENTHETICAL -> LineTypeMarginDecorator.PARENTHETICAL;
            case TRANSITION -> LineTypeMarginDecorator.TRANSITION;
            case LYRICS -> LineTypeMarginDecorator.LYRICS;
            case CENTERED -> LineTypeMarginDecorator.CENTERED;
            case PAGEBREAK -> LineTypeMarginDecorator.PAGEBREAK;
            case LINENUMBER -> LineTypeMarginDecorator.LINENUMBER;
            case TITLEPAGE -> LineTypeMarginDecorator.TITLEPAGE;
            case TITLEPAGE_TITLE -> LineTypeMarginDecorator.TITLEPAGE_TITLE;
            case TITLEPAGE_CENTERED -> LineTypeMarginDecorator.TITLEPAGE_CENTERED;
            case TITLEPAGE_LEFT -> LineTypeMarginDecorator.TITLEPAGE_LEFT;
            case TITLEPAGE_RIGHT -> LineTypeMarginDecorator.TITLEPAGE_RIGHT;
        };
    }

}
