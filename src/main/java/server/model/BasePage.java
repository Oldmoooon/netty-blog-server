package server.model;

import base.Constants;
import base.Logger;
import base.annoation.Page;
import base.enums.PageType;
import com.google.common.collect.Maps;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

/**
 * @author guyue
 * @date 2018/10/15
 */
public class BasePage {
    private String template;
    private Map<String, Object> content;

    /**
     * key: page name
     * value: page class
     */
    private static Map<PageType, Class<? extends BasePage>> data = Maps.newConcurrentMap();

    //init
    static {
        loadData();
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Map<String, Object> getContent() {
        return content;
    }

    public void setContent(Map<String, Object> content) {
        this.content = content;
    }

    public static Map<PageType, Class<? extends BasePage>> getData() {
        return data;
    }

    private static void loadData() {
        Reflections reflections = new Reflections(Constants.PAGE_CLASS_PATH);
        Set<Class<? extends BasePage>> classes = reflections.getSubTypesOf(BasePage.class);
        for (Class<? extends BasePage> per : classes) {
            if (!per.isAnnotationPresent(Page.class)) {
                continue;
            }
            Page ann = per.getAnnotation(Page.class);
            data.put(ann.type(), per);
            Logger.logic.info("load page class {} success.", per.getSimpleName());
        }
    }
}
