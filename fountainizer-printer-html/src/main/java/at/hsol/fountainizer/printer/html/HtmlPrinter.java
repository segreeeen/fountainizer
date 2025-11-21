package at.hsol.fountainizer.printer.html;

import at.hsol.fountainizer.core.api.Options;
import at.hsol.fountainizer.core.api.parser.Content;
import at.hsol.fountainizer.core.api.parser.Line;
import at.hsol.fountainizer.core.api.printer.FilePrinter;
import at.hsol.fountainizer.core.api.printer.StringPrinter;
import at.hsol.fountainizer.core.api.types.LineType;
import j2html.tags.DomContent;
import j2html.tags.specialized.DivTag;
import j2html.tags.specialized.HtmlTag;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static j2html.TagCreator.*;

public class HtmlPrinter implements FilePrinter, StringPrinter {
    private HtmlOptions options;

    HtmlPrinter(HtmlOptions options) {
        this.options = options;
    }

    @Override
    public void setOptions(Options options) {
        this.options = (HtmlOptions) options;
    }

    @Override
    public String printToString(Content content) {
        if (options == null) {
            this.options = new HtmlOptions(false);
        }

        return createHtml(content).render();
    }

    @Override
    public void printToFile(Content content, String outputFile) throws IOException {
        if (options == null) {
            this.options = new HtmlOptions(false);
        }

        File file = new File(outputFile);
        FileOutputStream fos = new FileOutputStream(file);
        String html;
        if (this.options.isInnerHtmlOnly()) {
            html = createScriptContent(content).render();
        } else {
            html = createHtml(content).render();
        }

        fos.write(html.getBytes(StandardCharsets.UTF_8));
        fos.close();
    }

    private HtmlTag createHtml(Content content) {
        String css = readCssFromResources();
        String js = readJsFromResources();

        return html(
                head(
                        title(),
                        style(css)
                ),
                body(
                        div().withId("document").withClass("document"),
                        createScriptContent(content),
                        script(js)
                )
        );
    }

    private DomContent createScriptContent(Content content) {
        return div(
                div(
                        createTitlePage(content.getTitlepageLines())
                ).withClass("titlepage"),
                each(createScript(content), s -> s)
        ).withId("script-content");
    }

    private List<DomContent> createScript(Content content) {
        Line current = content.getFirst();
        List<DomContent> scripts = new ArrayList<>();

        while (current != null) {
            DomContent domLine = switch (current.getLineType()) {
                case EMPTY -> br();
                case ACTION -> p(attrs(".action"), current.getText());
                case CHARACTER -> p(attrs(".character"), current.getText());
                case DIALOGUE -> p(attrs(".dialogue"), current.getText());
                case TRANSITION -> p(attrs(".transition"), current.getText());
                case HEADING -> p(attrs(".heading"), current.getText());
                case PARENTHETICAL -> p(attrs(".parenthetical"), current.getText());
                case LYRICS -> p(attrs(".lyrics"), current.getText());
                default -> null;
            };

            if (domLine != null) {
                scripts.add(domLine);
            }

            current = current.getNext();
        }

        return scripts;
    }

    private DomContent createTitlePage(Map<LineType, List<Line>> titlepageLines) {
        List<Line> titleLines = titlepageLines.get(LineType.TITLEPAGE_TITLE);
        List<Line> titleLinesCenter = titlepageLines.get(LineType.TITLEPAGE_CENTERED);
        List<Line> titleLinesLeft = titlepageLines.get(LineType.TITLEPAGE_LEFT);
        List<Line> titleLinesRight = titlepageLines.get(LineType.TITLEPAGE_RIGHT);

        DivTag titlePageCreator = div(attrs(".titlepage"));

        if (titleLines != null)
            titlePageCreator.with(each(titleLines, l -> p(attrs("p.titlepage_title"), l.getText())));

        if (titleLinesCenter != null)
            titlePageCreator.with(each(titleLinesCenter, l -> p(attrs("p.titlepage_center"), l.getText())));

        if (titleLinesLeft != null)
            titlePageCreator.with(each(titleLinesLeft, l -> p(attrs("p.titlepage_left"), l.getText())));

        if (titleLinesRight != null)
            titlePageCreator.with(each(titleLinesRight, l -> p(attrs("p.titlepage_right"), l.getText())));

        return titlePageCreator;
    }

    private String readJsFromResources() {
        try (InputStream in = HtmlPrinter.class.getResourceAsStream("/at/hsol/fountainizer/printer/html/script.js")) {
            if (in == null) throw new IllegalStateException("Resource not found");
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readCssFromResources() {
        try (InputStream in = HtmlPrinter.class.getResourceAsStream("/at/hsol/fountainizer/printer/html/style.css")) {
            if (in == null) throw new IllegalStateException("Resource not found");
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
