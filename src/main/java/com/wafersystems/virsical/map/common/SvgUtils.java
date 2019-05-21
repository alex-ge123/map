package com.wafersystems.virsical.map.common;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * svg解析工具包
 *
 * @author tandk
 * @date 2019/5/9 13:30
 */
@Slf4j
public class SvgUtils {
  private SvgUtils() {
    throw new IllegalStateException("SVG Utility class");
  }

  /**
   * 描述：解析SVG文件，获取相关元素 。
   */
  public static Map<String, String> analyzeSvgFile(InputStream in) throws DocumentException {
    // 创建SAXReader对象
    SAXReader reader = new SAXReader();
    reader.setEntityResolver(new IgnoreDtdEntityResolver());
    reader.setStringInternEnabled(false);
    // 读取文件 转换成Document
    Document document = reader.read(in);
    Element root = document.getRootElement();
    Attribute width = root.attribute("width");
    Attribute height = root.attribute("height");
    StringBuilder childString = new StringBuilder();
    for (Iterator it = root.elementIterator(); it.hasNext(); ) {
      Element child = (Element) it.next();
      childString.append(child.asXML());
    }
    Attribute vb = root.attribute("viewBox");
    Map<String, String> map = new HashMap<>(4);
    map.put("width", width != null ? width.getText().replace("px", "") : "");
    map.put("height", height != null ? height.getText().replace("px", "") : "");
    map.put("viewBox", vb != null ? vb.getText() : "");
    String element = childString.toString();
    String cls = ".cls-1";
    String clsClass = "class=\"cls-1\"";
    if (!StrUtil.isEmpty(element)) {
      if (element.contains(cls)) {
        element = element.replace(cls, "");
      }
      if (element.contains(clsClass)) {
        element = element.replace(clsClass, "");
      }
    }
    map.put("element", element);
    return map;
  }

  public static class IgnoreDtdEntityResolver implements EntityResolver {

    @Override
    public InputSource resolveEntity(String publicId, String systemId) {
      return new InputSource(
        new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?>".getBytes(StandardCharsets.UTF_8)));
    }

  }
}
