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

    public static final String HOME_PAGE = "home";
    public static final String CONTENT_PAGE = "content";
    public static final String ERROR_PAGE = "error";

    public static final String TEMPLATE_DIR = "template/";
    public static final String HOME_TEMPLATE = "index.html";
    public static final String CONTENT_TEMPLATE = "content.html";
    public static final String ERROR_TEMPLATE = "error.html";
}
