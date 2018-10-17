package server.controller;

import base.Constants;
import base.Logger;
import base.annoation.Processor;
import com.google.common.collect.Maps;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author guyue
 * @date 2018/10/16
 */
public class Router {
    private static Map<String, IProcessor> data = Maps.newConcurrentMap();
    static {
        Reflections reflections = new Reflections(Constants.PROCESSOR_CLASS_PATH);
        Set<Class<? extends IProcessor>> classes = reflections.getSubTypesOf(IProcessor.class);
        for (Class<? extends IProcessor> per : classes) {
            if (!per.isAnnotationPresent(Processor.class)) {
                continue;
            }
            Processor ann = per.getAnnotation(Processor.class);
            for (String path : ann.path()) {
                try {
                    data.put(path, per.getDeclaredConstructor().newInstance());
                    Logger.system.info("load processor {}->{} success", path, per.getSimpleName());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    Logger.system.error("load processor {} error.", per.getSimpleName(), e);
                }
            }
        }
    }

    public static void route(FullHttpRequest request, FullHttpResponse response) {
        String uri = request.uri();
        String key = match(uri);
        IProcessor processor = data.getOrDefault(key, data.get(Constants.DEFAULT_PROCESSOR_KEY));
        if (null != processor) {
            processor.process(request, response);
        } else {
            Logger.system.error("there is no processor for default path and {}", uri);
        }
    }

    private static String match(String uri) {
        List<String> matches = data.keySet()
                .stream()
                .filter(key -> Pattern.matches(key, uri))
                .collect(Collectors.toList());
        if (matches.size() > 1) {
            Logger.system.error("a uri can match more than one routers.");
        }
        return matches.get(0);
    }
}
