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
package org.dhatim.delivery.sax;

import java.io.IOException;

import org.dhatim.SmooksException;
import org.dhatim.container.ExecutionContext;
import org.dhatim.delivery.sax.annotation.StreamResultWriter;
import org.dhatim.delivery.sax.annotation.TextConsumer;

@TextConsumer
public class VisitAfterWrittingVisitor implements SAXVisitAfter {

	@StreamResultWriter	
	private SAXToXMLWriter writer;
	
	public static String elementText = null;
	
	public void visitAfter(SAXElement element, ExecutionContext executionContext) throws SmooksException, IOException {
		writer.writeStartElement(element);
		writer.writeText("{{", element);
		writer.writeText(element);
		writer.writeText("}}", element);
		writer.writeEndElement(element);
		
		elementText = element.getTextContent();
	}
}