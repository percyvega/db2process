package com.percyvega.db2process.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class JaxbUtil {

    private static final Logger logger = LoggerFactory.getLogger(JaxbUtil.class);

    // get XML text file from an object
    public static <T> File getXmlFileFromObject(Object o, Class<T> clazz) {
        File marshalledFile = null;

        JAXBContext jaxbContext;
        Marshaller marshaller;

        try {
            marshalledFile = new File("logs/" + new SimpleDateFormat("yyyyddMM_HHmmssSSS").format(new Date()) + ".xml");

            jaxbContext = JAXBContext.newInstance(clazz);
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(o, marshalledFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return marshalledFile;
    }

    // get object from XML text file
    @SuppressWarnings("unchecked")
    public static <T> T getObjectFromXMLFile(File marshalledFile, Class<T> clazz) {
        JAXBContext jaxbContext;
        Unmarshaller unmarshaller;
        T unmarshalled = null;

        try {
            jaxbContext = JAXBContext.newInstance(clazz);
            unmarshaller = jaxbContext.createUnmarshaller();
            unmarshalled = (T) unmarshaller.unmarshal(marshalledFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return unmarshalled;
    }

    // get XML text from an object
    public static <T> String getXmlString(Object o, Class<T> clazz) {
        String marshalled = null;
        StringWriter stringWriter = new StringWriter();

        JAXBContext jaxbContext;
        Marshaller jaxbMarshaller;

        try {
            jaxbContext = JAXBContext.newInstance(clazz);

            jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            jaxbMarshaller.marshal(o, stringWriter);
            marshalled = stringWriter.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return marshalled;
    }

    // get an object from an XML text
    @SuppressWarnings("unchecked")
    public static <T> T getObjectFromXMLString(String marshalledString, Class<T> clazz) {
        JAXBContext jaxbContext;
        Unmarshaller jaxbUnmarshaller;
        T unmarshalled = null;

        try {
            jaxbContext = JAXBContext.newInstance(clazz);
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            unmarshalled = (T) jaxbUnmarshaller.unmarshal(new StringReader(marshalledString));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return unmarshalled;
    }

    // get the XML text of an object that went through a transformation
    public static <T> String getTransformedXMLStringFromObject(Object o, Class<T> clazz, String xslFilePath) throws JAXBException,
            TransformerFactoryConfigurationError, TransformerConfigurationException, TransformerException, UnsupportedEncodingException {

        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        JAXBSource source = new JAXBSource(jaxbContext, o);

        ByteArrayOutputStream s = new ByteArrayOutputStream();
        Result result = new StreamResult(new OutputStreamWriter(s, "UTF-8"));

        // find XSLT file
        File xslFile = new File(xslFilePath);

        // set up XSLT transformation
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute("indent-number", 4);
        Transformer transformer = transformerFactory.newTransformer(new StreamSource(xslFile));
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        // run transformation
        transformer.transform(source, result);

        return new String(s.toByteArray());
    }

}