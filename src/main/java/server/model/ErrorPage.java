package server.model;

/**
 * @author guyue
 * @date 2018/10/15
 */

import base.Constants;
import base.annoation.Page;
import base.enums.PageType;

@Page(type = PageType.ERROR, template = Constants.ERROR_TEMPLATE)
public class ErrorPage extends BasePage {
}
