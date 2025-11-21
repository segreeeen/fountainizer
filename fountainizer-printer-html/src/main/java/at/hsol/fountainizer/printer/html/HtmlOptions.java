package at.hsol.fountainizer.printer.html;

import at.hsol.fountainizer.core.api.Options;

public class HtmlOptions extends Options {
    private boolean innerHtmlOnly = false;

    public HtmlOptions(boolean innerHtmlOnly) {
        this.innerHtmlOnly = innerHtmlOnly;
    }

    public boolean isInnerHtmlOnly() {
        return innerHtmlOnly;
    }

    public void setInnerHtmlOnly(boolean innerHtmlOnly) {
        this.innerHtmlOnly = innerHtmlOnly;
    }
}
