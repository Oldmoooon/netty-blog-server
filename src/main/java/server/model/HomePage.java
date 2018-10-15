package server.model;

/**
 * @author guyue
 * @date 2018/10/15
 */

import base.Constants;
import base.annoation.Page;

@Page(name = Constants.HOME_PAGE)
public class HomePage extends BasePage {
    public HomePage() {
        this.setTemplate(buildTemplatePath(Constants.HOME_TEMPLATE));
    }
}
