package com.bin.david.rounter.xml;

import com.bin.david.rounter.bean.RouterInfo;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by huang on 2018/1/25.
 */

public class RouterWriter {

    //测试写入xml
    public void write(Map<String,RouterInfo> routerMap){
        SAXTransformerFactory tff = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
        try {
            TransformerHandler handler = tff.newTransformerHandler();
             Transformer tr = handler.getTransformer();
             tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
             tr.setOutputProperty(OutputKeys.INDENT, "yes");

            File f = new File("./router.xml");
            if (!f.exists()) {
                f.createNewFile();
            }
            Result result = new StreamResult(new FileOutputStream(f));
            handler.setResult(result);
            handler.startDocument();
            AttributesImpl attr = new AttributesImpl();
            handler.startElement("", "", "routerStore", attr);

            for (Map.Entry<String,RouterInfo> routeEntry : routerMap.entrySet()) {
                attr.clear();
                RouterInfo routerInfo = routeEntry.getValue();
                attr.addAttribute("", "", "name", "", routeEntry.getKey()+"");
                handler.startElement("", "", "router", attr);
                createRouterElement(handler,  "path",routerInfo.getPath());
                handler.endElement("", "", "router");
            }
            handler.endElement("", "", "routerStore");
            handler.endDocument();

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成路由元素
     * @param handler
     * @param name
     * @param value
     * @throws SAXException
     */
    private void createRouterElement(TransformerHandler handler,String name,String value) throws SAXException {
        if (value!= null && !value.trim().equals("")) {
            handler.startElement("", "", name, null);
              handler.characters(value.toCharArray(), 0, value.length());
            handler.endElement("", "", name);
        }
    }

}
