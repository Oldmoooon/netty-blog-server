package base;

/**
 * @author guyue
 * @date 2018/9/5
 */
public class Constants {
    public static final int PORT = 8080;

    public static final int BOSS_THREAD_COUNT = 1;
    public static final int WORKER_THREAD_COUNT = 4;
    public static final boolean SO_KEEP_ALIVE = true;

    public static final String PAGE_CLASS_PATH = "server.model";
    public static final String PROCESSOR_CLASS_PATH = "server.controller";

    public static final String HOME_PAGE = "home";
    public static final String CONTENT_PAGE = "content";
    public static final String ERROR_PAGE = "error";

    private static final String TEMPLATE_DIR = "template/";
    public static final String HOME_TEMPLATE = TEMPLATE_DIR + "index.html";
    public static final String CONTENT_TEMPLATE = TEMPLATE_DIR + "content.html";
    public static final String ERROR_TEMPLATE = TEMPLATE_DIR + "error.html";

    public static final String HTML_HEADER_VALUE_HTML = "text/html";
    public static final String HTML_HEADER_VALUE_ICON = "image/x-icon";
    public static final String DEFAULT_PROCESSOR_KEY = "/index.html";
}
