package server.view;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import server.model.BasePage;

import java.io.StringWriter;

/**
 * @author guyue
 * @date 2018/10/15
 */
public class ThymeleafDraw implements IDraw {
    private static TemplateEngine templateEngine;

    static {
        templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode("HTML");
        templateEngine.setTemplateResolver(templateResolver);
    }

    @Override
    public String draw(BasePage page) {
        Context context = new Context();
        context.setVariables(page.getContent());
        StringWriter stringWriter = new StringWriter();
        templateEngine.process(page.getTemplate(), context, stringWriter);
        return stringWriter.toString();
    }
}
