package server.model;

/**
 * @author guyue
 * @date 2018/10/15
 */

import base.Constants;
import base.annoation.Page;
import base.enums.PageType;
import com.google.common.collect.Maps;

import java.util.HashMap;

@Page(type = PageType.HOME, template = Constants.HOME_TEMPLATE)
public class HomePage extends BasePage {
    public HomePage() {
        this.setTemplate(Constants.HOME_TEMPLATE);
        initContent();
    }

    private void initContent() {
        HashMap<String, Object> content = Maps.newHashMap();
        HashMap<String, String> value = Maps.newHashMap();
        value.put("name1", "content1");
        value.put("name2", "content2");
        value.put("name3", "content3");
        value.put("name4", "content4");
        value.put("name5", "content5");
        content.put("data", value);
        this.setContent(content);
    }
}
