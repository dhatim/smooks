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

package org.dhatim.cdres.trans;

import org.dhatim.cdr.SmooksResourceConfiguration;
import org.dhatim.cdr.SmooksConfigurationException;
import org.dhatim.cdr.annotation.ConfigParam;
import org.dhatim.container.ExecutionContext;
import org.dhatim.delivery.dom.DOMElementVisitor;
import org.w3c.dom.Element;

/**
 * Set a DOM element attribute <u>during the processing phase</u>.
 * <h3>.cdrl Configuration</h3>
 * <pre>
 * &lt;smooks-resource	useragent="<i>device/profile</i>" selector="<i>target-element-name</i>" 
 * 	path="org.dhatim.cdres.trans.SetAttributeTU"&gt;
 * 
 * 	&lt;!-- The name of the element attribute to be set. --&gt;
 * 	&lt;param name="<b>attributeName</b>"&gt;<i>attribute-name</i>&lt;/param&gt;
 * 
 * 	&lt;!-- The value of the element attribute to be set. --&gt;
 * 	&lt;param name="<b>attributeValue</b>"&gt;<i>attribute-value</i>&lt;/param&gt;
 * 
 * 	&lt;!-- (Optional) Overwrite existing attributes of the same name. Default false. --&gt;
 * 	&lt;param name="<b>overwrite</b>"&gt;<i>true/false</i>&lt;/param&gt;
 * 
 * 	&lt;!-- (Optional) Visit the target element before iterating over the elements
 * 		child content. Default false. --&gt;
 * 	&lt;param name="<b>visitBefore</b>"&gt;<i>true/false</i>&lt;/param&gt;
 * &lt;/smooks-resource&gt;</pre>
 * See {@link org.dhatim.cdr.SmooksResourceConfiguration}.
 * @author tfennelly
 */
public class SetAttributeTU implements DOMElementVisitor {

    @ConfigParam
	private String attributeName;

    @ConfigParam
	private String attributeValue;

    @ConfigParam(use = ConfigParam.Use.OPTIONAL, defaultVal = "false")
	private boolean visitBefore;

    @ConfigParam(use = ConfigParam.Use.OPTIONAL, defaultVal = "false")
	private boolean overwrite;

    public void visitBefore(Element element, ExecutionContext executionContext) {
        if(visitBefore) {
            visit(element, executionContext);
        }
    }

    public void visitAfter(Element element, ExecutionContext executionContext) {
        if(!visitBefore) {
            visit(element, executionContext);
        }
    }

    private void visit(Element element, ExecutionContext executionContext) {
		// If the element already has the new attribute and we're
		// not overwriting, leave all as is and return.
		if(!overwrite && element.hasAttribute(attributeName)) {
			return;
		}
		element.setAttribute(attributeName, attributeValue);
	}
}
