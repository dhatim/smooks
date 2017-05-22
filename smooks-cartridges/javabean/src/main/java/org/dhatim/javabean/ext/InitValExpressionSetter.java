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
package org.dhatim.javabean.ext;

import org.dhatim.SmooksException;
import org.dhatim.cdr.SmooksResourceConfiguration;
import org.dhatim.cdr.annotation.ConfigParam;
import org.dhatim.cdr.extension.ExtensionContext;
import org.dhatim.container.ExecutionContext;
import org.dhatim.delivery.dom.DOMVisitBefore;
import org.dhatim.javabean.BeanInstanceCreator;
import org.dhatim.xml.DomUtils;
import org.w3c.dom.Element;

/**
 * Expression Binding initVal setter.
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class InitValExpressionSetter implements DOMVisitBefore {
	
	@ConfigParam
	private String initValAttrib;

	public void visitBefore(Element element, ExecutionContext executionContext) throws SmooksException {
		String initValExpression = DomUtils.getAttributeValue(element, initValAttrib);
		
		if(initValExpression != null) {
	        ExtensionContext extensionContext = ExtensionContext.getExtensionContext(executionContext);
	        SmooksResourceConfiguration creatorConfig = extensionContext.getResourceByName(BeanInstanceCreator.class.getName());
			String propertyName = DomUtils.getAttributeValue(element, "property");
	        
			creatorConfig.setParameter(BeanInstanceCreator.INIT_VAL_EXPRESSION, "this." + propertyName + " = (" + initValExpression + ");");
		}
	}
}
