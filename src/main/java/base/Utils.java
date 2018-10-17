package base;

import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Collections;

/**
 * @author guyue
 * @date 2018/10/15
 */
public class Utils {
    private static HtmlRenderer renderer;
    private static Parser parser;
    private static String resourcePath;

    static {
        MutableDataSet options = new MutableDataSet();
        options.setFrom(ParserEmulationProfile.MARKDOWN);
        options.set(Parser.EXTENSIONS, Collections.singletonList(TablesExtension.create()));
        parser = Parser.builder(options).build();
        renderer = HtmlRenderer.builder(options).build();
        try {
            File classesDir = new File(Utils.class.getResource("/").toURI());
            resourcePath = classesDir.getParent() + "/resources";
            Logger.system.info("resources directory is {}", resourcePath);
        } catch (URISyntaxException e) {
            Logger.system.error("load resources directory error", e);
        }
    }

    public static String faviconPath() {
        return resourcePath + "/favicon.ico";
    }

    public static String postDir() {
        return resourcePath + "/post";
    }

    public static String markDown2HTML(String src) {
        return renderer.render(parser.parse(src));
    }
}
