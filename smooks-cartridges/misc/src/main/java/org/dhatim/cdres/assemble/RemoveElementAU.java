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

package org.dhatim.cdres.assemble;

import org.dhatim.cdr.SmooksResourceConfiguration;
import org.dhatim.cdr.SmooksConfigurationException;
import org.dhatim.container.ExecutionContext;
import org.dhatim.delivery.dom.Phase;
import org.dhatim.delivery.dom.VisitPhase;
import org.dhatim.delivery.dom.DOMElementVisitor;
import org.w3c.dom.Element;

/**
 * Removes a DOM element <u>during the assembly phase</u>.
 * <p/>
 * The element is visited by this Assembly Unit before it's child content
 * has been iterated over.
 * <h3>.cdrl Configuration</h3>
 * <pre>
 * &lt;smooks-resource	useragent="<i>device/profile</i>" selector="<i>target-element-name</i>" 
 * 	path="org.dhatim.cdres.assemble.RemoveElementAU" /&gt;</pre>
 * See {@link org.dhatim.cdr.SmooksResourceConfiguration}.
 * @author tfennelly
 */
@Phase(VisitPhase.ASSEMBLY)
public class RemoveElementAU implements DOMElementVisitor {

    public void visitBefore(Element element, ExecutionContext executionContext) {
        element.getParentNode().removeChild(element);
    }

	/* (non-Javadoc)
	 * @see org.dhatim.delivery.dom.DOMElementVisitor#visitAfter(org.w3c.dom.Element, org.dhatim.container.ExecutionContext)
	 */
	public void visitAfter(Element element, ExecutionContext request) {
	}
}
