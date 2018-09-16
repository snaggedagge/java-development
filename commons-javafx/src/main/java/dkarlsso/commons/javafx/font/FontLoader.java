package dkarlsso.commons.javafx.font;

import javafx.scene.text.Font;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class FontLoader {

    private static Map<String, Font> loadedFonts = new HashMap<>();

    public static Font getFont(final CustomFont customFont, final int fontSize) {
        final String cacheKey = customFont.name() + fontSize;

        if (!loadedFonts.containsKey(cacheKey)) {
            final String fontName = customFont.name().toLowerCase();
            final String fontPath = "fonts/" + fontName + "/" + fontName + "." + customFont.getFontType();
            final InputStream inputStream = FontLoader.class.getClassLoader().getResourceAsStream(fontPath);

            loadedFonts.put(cacheKey, Font.loadFont(inputStream, fontSize));
        }
        return loadedFonts.get(cacheKey);
    }

}
