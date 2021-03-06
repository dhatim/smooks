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
package org.dhatim.visitors.set;

import org.dhatim.SmooksException;
import org.dhatim.cdr.Parameter;
import org.dhatim.cdr.SmooksResourceConfiguration;
import org.dhatim.cdr.extension.ExtensionContext;
import org.dhatim.container.ExecutionContext;
import org.dhatim.delivery.dom.DOMVisitAfter;
import org.w3c.dom.Element;

/**
 * Digester for the {@link SetElementData}.
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class SetElementDataConfigDigester implements DOMVisitAfter {

    public void visitAfter(Element element, ExecutionContext executionContext) throws SmooksException {
        SmooksResourceConfiguration config = ExtensionContext.getExtensionContext(executionContext).getCurrentConfig();

        config.setParameter(new Parameter(SetElementData.ATTRIBUTE_DATA, "##value_as_xml_element").setXML(element));
    }
}
