package server.controller;

import base.annoation.Processor;

/**
 * @author guyue
 * @date 2018/10/16
 */
@Processor(path = {"/content/.*"})
public class ContentProcessor implements IProcessor {
}
