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
package org.dhatim.delivery.sax.terminate;

import java.io.IOException;
import java.util.Set;

import org.dhatim.SmooksException;
import org.dhatim.cdr.annotation.ConfigParam;
import org.dhatim.cdr.annotation.ConfigParam.Use;
import org.dhatim.container.ExecutionContext;
import org.dhatim.delivery.ordering.Producer;
import org.dhatim.delivery.sax.SAXElement;
import org.dhatim.delivery.sax.SAXVisitAfter;
import org.dhatim.delivery.sax.SAXVisitBefore;
import org.dhatim.util.CollectionsUtil;

/**
 * Terminate Visitor.
 * 
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class TerminateVisitor implements SAXVisitBefore, SAXVisitAfter, Producer {

	private boolean terminateBefore = false;
	
	/* (non-Javadoc)
	 * @see org.dhatim.delivery.sax.SAXVisitBefore#visitBefore(org.dhatim.delivery.sax.SAXElement, org.dhatim.container.ExecutionContext)
	 */
	public void visitBefore(SAXElement element, ExecutionContext executionContext) throws SmooksException, IOException {
		if(terminateBefore) {
			throw new TerminateException(element, true);
		}
	}

	/* (non-Javadoc)
	 * @see org.dhatim.delivery.sax.SAXVisitAfter#visitAfter(org.dhatim.delivery.sax.SAXElement, org.dhatim.container.ExecutionContext)
	 */
	public void visitAfter(SAXElement element, ExecutionContext executionContext) throws SmooksException, IOException {
		if(!terminateBefore) {
			throw new TerminateException(element, false);
		}
	}

	/**
	 * @param terminateBefore the terminateBefore to set
	 */
	@ConfigParam(use = Use.OPTIONAL)
	public TerminateVisitor setTerminateBefore(boolean terminateBefore) {
		this.terminateBefore = terminateBefore;
		return this;
	}

	/* (non-Javadoc)
	 * @see org.dhatim.delivery.ordering.Producer#getProducts()
	 */
	public Set<? extends Object> getProducts() {
		// Doesn't actually produce anything.  Just using the Producer/Consumer mechanism to
		// force this vistor to the top of the visitor apply list.
		return CollectionsUtil.toSet();
	}
}
