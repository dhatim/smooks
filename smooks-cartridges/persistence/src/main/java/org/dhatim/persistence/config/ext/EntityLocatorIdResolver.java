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
package org.dhatim.persistence.config.ext;

import org.dhatim.SmooksException;
import org.dhatim.cdr.SmooksResourceConfiguration;
import org.dhatim.cdr.annotation.AppContext;
import org.dhatim.cdr.extension.ExtensionContext;
import org.dhatim.cdr.extension.ResourceConfigUtil;
import org.dhatim.container.ApplicationContext;
import org.dhatim.container.ExecutionContext;
import org.dhatim.delivery.dom.DOMVisitBefore;
import org.w3c.dom.Element;

/**
 * @author <a href="mailto:maurice.zeijen@smies.com">maurice.zeijen@smies.com</a>
 *
 */
public class EntityLocatorIdResolver implements DOMVisitBefore {

	@AppContext
	private ApplicationContext applicationContext;

	/* (non-Javadoc)
	 * @see org.dhatim.delivery.dom.DOMVisitBefore#visitBefore(org.w3c.dom.Element, org.dhatim.container.ExecutionContext)
	 */
	public void visitBefore(Element element, ExecutionContext executionContext)
			throws SmooksException {
		SmooksResourceConfiguration config = ExtensionContext.getExtensionContext(executionContext).getResourceStack().peek();

		int index = LocatorIndex.getLocatorIndex(applicationContext).increment();

		ResourceConfigUtil.setProperty(config, "id", Integer.toString(index), executionContext);
	}

}
