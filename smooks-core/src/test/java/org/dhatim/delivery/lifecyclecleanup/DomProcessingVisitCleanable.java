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
package org.dhatim.delivery.lifecyclecleanup;

import org.dhatim.SmooksException;
import org.dhatim.container.ExecutionContext;
import org.dhatim.delivery.Fragment;
import org.dhatim.delivery.VisitLifecycleCleanable;
import org.dhatim.delivery.dom.DOMVisitBefore;
import org.dhatim.delivery.dom.Phase;
import org.dhatim.delivery.dom.VisitPhase;
import org.dhatim.delivery.dom.DOMVisitAfter;
import org.w3c.dom.Element;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
@Phase(value = VisitPhase.PROCESSING)
public class DomProcessingVisitCleanable implements DOMVisitBefore, DOMVisitAfter, VisitLifecycleCleanable {

    public static boolean cleaned;

    public void visitBefore(Element element, ExecutionContext executionContext) throws SmooksException {
        if(cleaned) {
            fail("Resource shouldn't be cleaned yet!");
        }
    }

    public void visitAfter(Element element, ExecutionContext executionContext) throws SmooksException {
        if(cleaned) {
            fail("Resource shouldn't be cleaned yet!");
        }
    }

    public void executeVisitLifecycleCleanup(Fragment fragment, ExecutionContext executionContext) {
        cleaned = true;
    }
}