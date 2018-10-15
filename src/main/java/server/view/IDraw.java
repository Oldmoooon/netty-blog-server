package server.view;

import server.model.BasePage;

/**
 * @author guyue
 * @date 2018/10/15
 */
public interface IDraw {
    /**
     * draw page from template and content to html string
     *
     * @param page a page contains template and content
     * @return html page as string
     */
    public String draw(BasePage page);
}
