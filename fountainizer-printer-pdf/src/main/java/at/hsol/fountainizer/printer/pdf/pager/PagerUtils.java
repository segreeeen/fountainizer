package at.hsol.fountainizer.printer.pdf.pager;

import at.hsol.fountainizer.core.api.parser.StylizedText;
import at.hsol.fountainizer.core.api.parser.StylizedTextPart;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.io.IOException;

public class PagerUtils {
    public static float stringWidth(AbstractPager<?> page, StylizedText fountainEmphasizedText) throws IOException {
        float width = 0.0f;
        for (StylizedTextPart richFormat : fountainEmphasizedText.getStylizedTextParts()) {
            PDFont font = selectFont(page, richFormat);
            float stringWidth = font.getStringWidth(richFormat.getText()) / 1000 * page.getFontSize();
            width = width + stringWidth;
        }
        return width;
    }

    public static float formatWidth(AbstractPager<?> page, StylizedTextPart richFormat) throws IOException {
            return selectFont(page, richFormat).getStringWidth(richFormat.getText()) / 1000 * page.getFontSize();
    }

    public static PDFont selectFont(Pager page, StylizedTextPart richFormat) {
        if (richFormat.isBold() && richFormat.isItalic()) {
            return page.getBoldItalicFont();
        } else if (richFormat.isBold()) {
            return page.getBoldFont();
        } else if (richFormat.isItalic()) {
            return page.getItalicFont();
        } else {
            return page.getFont();
        }
    }
}
