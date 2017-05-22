/*
 * Milyn - Copyright (C) 2006 - 2010
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License (version 2.1) as published by the Free Software
 * Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU Lesser General Public License for more details:
 * http://www.gnu.org/licenses/lgpl.txt
 */

package org.dhatim.flatfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dhatim.cdr.SmooksResourceConfiguration;
import org.dhatim.cdr.annotation.AppContext;
import org.dhatim.cdr.annotation.Config;
import org.dhatim.cdr.annotation.ConfigParam;
import org.dhatim.cdr.annotation.Configurator;
import org.dhatim.container.ApplicationContext;
import org.dhatim.container.ExecutionContext;
import org.dhatim.delivery.VisitorAppender;
import org.dhatim.delivery.VisitorConfigMap;
import org.dhatim.delivery.annotation.Initialize;
import org.dhatim.xml.SmooksXMLReader;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.XMLConstants;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

/**
 * Flat file reader.
 *
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class FlatFileReader implements SmooksXMLReader, VisitorAppender {

	private static Log logger = LogFactory.getLog(FlatFileReader.class);
    private static Attributes EMPTY_ATTRIBS = new AttributesImpl();

    private static char[] INDENT_LF = new char[] {'\n'};
    private static char[] INDENTCHARS = new char[] {'\t', '\t'};
    private static String RECORD_NUMBER_ATTR = "number";
    private static String RECORD_TRUNCATED_ATTR = "truncated";

    private ContentHandler contentHandler;
	private ExecutionContext execContext;

    @Config
    private SmooksResourceConfiguration config;

    @AppContext
    private ApplicationContext appContext;

    @ConfigParam(name = "parserFactory")
    private Class<? extends RecordParserFactory> parserFactoryClass;
    private RecordParserFactory parserFactory;

    @ConfigParam(defaultVal="records")
    private String rootElementName;

    @ConfigParam(defaultVal="false")
    private boolean indent;

	@Initialize
	public void initialize() throws IllegalAccessException, InstantiationException {
        parserFactory = parserFactoryClass.newInstance();
        Configurator.configure(parserFactory, config, appContext);
	}

    public void addVisitors(VisitorConfigMap visitorMap) {
        if(parserFactory instanceof VisitorAppender) {
            ((VisitorAppender) parserFactory).addVisitors(visitorMap);
        }
    }

    /* (non-Javadoc)
	 * @see org.dhatim.xml.SmooksXMLReader#setExecutionContext(org.dhatim.container.ExecutionContext)
	 */
	public void setExecutionContext(ExecutionContext request) {
		this.execContext = request;
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.XMLReader#parse(org.xml.sax.InputSource)
	 */
	public void parse(InputSource inputSource) throws IOException, SAXException {
        if(contentHandler == null) {
            throw new IllegalStateException("'contentHandler' not set.  Cannot parse Record stream.");
        }
        if(execContext == null) {
            throw new IllegalStateException("'execContext' not set.  Cannot parse Record stream.");
        }

        try {
			Reader recordReader;

            // Create the record parser....
            RecordParser recordParser = parserFactory.newRecordParser();
            recordParser.setRecordParserFactory(parserFactory);
            recordParser.setDataSource(inputSource);

            try {
                recordParser.initialize();

                // Start the document and add the root "record-set" element...
                contentHandler.startDocument();
                contentHandler.startElement(XMLConstants.NULL_NS_URI, rootElementName, StringUtils.EMPTY, EMPTY_ATTRIBS);

                // Output each of the CVS line entries...
                int lineNumber = 0;

                Record record = recordParser.nextRecord();
                while (record != null) {
                    lineNumber++; // First line is line "1"

                    if(record != null) {
                        List<Field> recordFields = record.getFields();

                        if(indent) {
                            contentHandler.characters(INDENT_LF, 0, 1);
                            contentHandler.characters(INDENTCHARS, 0, 1);
                        }

                        AttributesImpl attrs = new AttributesImpl();
                        attrs.addAttribute(XMLConstants.NULL_NS_URI, RECORD_NUMBER_ATTR, RECORD_NUMBER_ATTR, "xs:int", Integer.toString(lineNumber));

                        RecordMetaData recordMetaData = record.getRecordMetaData();
                        if(recordFields.size() < recordMetaData.getUnignoredFieldCount()) {
                            attrs.addAttribute(XMLConstants.NULL_NS_URI, RECORD_TRUNCATED_ATTR, RECORD_TRUNCATED_ATTR, "xs:boolean", Boolean.TRUE.toString());
                        }

                        contentHandler.startElement(XMLConstants.NULL_NS_URI, record.getName(), StringUtils.EMPTY, attrs);
                        for(Field recordField : recordFields) {
                            String fieldName = recordField.getName();

                            if(indent) {
                                contentHandler.characters(INDENT_LF, 0, 1);
                                contentHandler.characters(INDENTCHARS, 0, 2);
                            }

                            contentHandler.startElement(XMLConstants.NULL_NS_URI, fieldName, StringUtils.EMPTY, EMPTY_ATTRIBS);

                            String value = recordField.getValue();
                            contentHandler.characters(value.toCharArray(), 0, value.length());
                            contentHandler.endElement(XMLConstants.NULL_NS_URI, fieldName, StringUtils.EMPTY);
                        }

                        if(indent) {
                            contentHandler.characters(INDENT_LF, 0, 1);
                            contentHandler.characters(INDENTCHARS, 0, 1);
                        }

                        contentHandler.endElement(XMLConstants.NULL_NS_URI, record.getName(), StringUtils.EMPTY);
                    }

                    record = recordParser.nextRecord();
                }

                if(indent) {
                    contentHandler.characters(INDENT_LF, 0, 1);
                }

                // Close out the "csv-set" root element and end the document..
                contentHandler.endElement(XMLConstants.NULL_NS_URI, rootElementName, StringUtils.EMPTY);
                contentHandler.endDocument();
            } finally {
                recordParser.uninitialize();
            }
        } finally {
        	// These properties need to be reset for every execution (e.g. when reader is pooled).
        	contentHandler = null;
        	execContext = null;
        }
	}

    public void setContentHandler(ContentHandler contentHandler) {
        this.contentHandler = contentHandler;
    }

    public ContentHandler getContentHandler() {
        return contentHandler;
    }

    /****************************************************************************
     *
     * The following methods are currently unimplemnted...
     *
     ****************************************************************************/

    public void parse(String systemId) throws IOException, SAXException {
        throw new UnsupportedOperationException("Operation not supports by this reader.");
    }

    public boolean getFeature(String name) throws SAXNotRecognizedException,
            SAXNotSupportedException {
        return false;
    }

    public void setFeature(String name, boolean value)
            throws SAXNotRecognizedException, SAXNotSupportedException {
    }

    public DTDHandler getDTDHandler() {
        return null;
    }

    public void setDTDHandler(DTDHandler arg0) {
    }

    public EntityResolver getEntityResolver() {
        return null;
    }

    public void setEntityResolver(EntityResolver arg0) {
    }

    public ErrorHandler getErrorHandler() {
        return null;
    }

    public void setErrorHandler(ErrorHandler arg0) {
    }

    public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
        return null;
    }

    public void setProperty(String name, Object value)
            throws SAXNotRecognizedException, SAXNotSupportedException {
    }
}
