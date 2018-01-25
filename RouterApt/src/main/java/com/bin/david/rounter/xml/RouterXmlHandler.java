package com.bin.david.rounter.xml;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by huang on 2018/1/25.
 */

public class RouterXmlHandler extends DefaultHandler {
    private RouterReadCallback callback;

    public RouterXmlHandler(RouterReadCallback callback) {
        this.callback = callback;
    }

    @Override
    public void startDocument() throws SAXException {
        this.callback.readStart();
    }

    @Override
    public void endDocument() throws SAXException {
        this.callback.readEnd();
    }

    @Override
    public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
        System.out.println("-------->startElement() is invoked...");
        System.out.println("uri" + uri);
        System.out.println("localName" + localName);
        System.out.println("qName:" + qName);
        if(attributes.getLength()>0){
            System.out.println("element-->" + attributes.getQName(0) + "---" + attributes.getValue(0));
        }
    }



    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.out.println("-------->endElement() is invoked...");
        System.out.println("uri" + uri);
        System.out.println("localName" + localName);
        System.out.println("qName:" + qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        System.out.println("------------>characters() is invoked...");
        System.out.println("content:" + new String(ch, start, length));
    }
}
