/*
	Milyn - Copyright (C) 2006 - 2010

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License (version 2.1) as published by the Free Software
	Foundation.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

	See the GNU Lesser General Public License for more details:
	http://www.gnu.org/licenses/lgpl.txt
 */
package org.dhatim.smooks.edi.unedifact;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.dhatim.cdr.annotation.AppContext;
import org.dhatim.cdr.annotation.ConfigParam;
import org.dhatim.container.ApplicationContext;
import org.dhatim.container.ExecutionContext;
import org.dhatim.edisax.BufferedSegmentReader;
import org.dhatim.edisax.interchange.ControlBlockHandlerFactory;
import org.dhatim.edisax.interchange.InterchangeContext;
import org.dhatim.edisax.model.internal.Delimiters;
import org.dhatim.edisax.unedifact.UNEdifactInterchangeParser;
import org.dhatim.edisax.registry.DefaultMappingsRegistry;
import org.dhatim.namespace.NamespaceDeclarationStack;
import org.dhatim.xml.SmooksXMLReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * UN/EDIFACT Smooks reader.
 * 
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class UNEdifactReader extends UNEdifactInterchangeParser implements SmooksXMLReader {

	@ConfigParam
	private String mappingModel;

	@ConfigParam(defaultVal = "false")
	private boolean validate;

	@ConfigParam(defaultVal = "false")
	private boolean ignoreNewLines;

    @ConfigParam(defaultVal = "true")
    private boolean ignoreEmptyNodes;

	@AppContext
	private ApplicationContext applicationContext;

	private ExecutionContext executionContext;

	public void setExecutionContext(ExecutionContext executionContext) {
		this.executionContext = executionContext;
	}

	@Override
	public void parse(InputSource unedifactInterchange) throws IOException,
			SAXException {
		ignoreNewLines(ignoreNewLines);
        ignoreEmptyNodes(ignoreEmptyNodes);
		validate(validate);
		// Default Mappings Registry is already set to LazyMappingsRegistry
		// only if mappingModel is defined we should set another instance
		if (!StringUtils.isEmpty(mappingModel)) {
			setMappingsRegistry(new DefaultMappingsRegistry(mappingModel, applicationContext.getResourceLocator().getBaseURI()));
		}
		super.parse(unedifactInterchange);
	}

	@Override
	protected InterchangeContext createInterchangeContext(
            BufferedSegmentReader segmentReader, boolean validate,
            ControlBlockHandlerFactory controlBlockHandlerFactory, NamespaceDeclarationStack namespaceDeclarationStack) {

		return new InterchangeContext(segmentReader, registry, getContentHandler(), getFeatures(), controlBlockHandlerFactory, namespaceDeclarationStack, validate) {
			@Override
			public void pushDelimiters(Delimiters delimiters) {
				super.pushDelimiters(delimiters);
				// Bind the delimiters into the bean context. Will then get
				// auto-wired into interchanges...
				executionContext.getBeanContext().addBean(
						"interchangeDelimiters", delimiters);
			}
		};
	}
}
