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

import org.junit.Test;
import static org.junit.Assert.*;
import org.dhatim.SmooksException;
import org.dhatim.container.ExecutionContext;
import org.dhatim.delivery.ExecutionLifecycleCleanable;
import org.dhatim.delivery.ExecutionLifecycleInitializable;
import org.dhatim.delivery.sax.SAXVisitBefore;
import org.dhatim.delivery.sax.SAXElement;

import java.io.IOException;

/**
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class SaxVisitBefore implements SAXVisitBefore, ExecutionLifecycleInitializable, ExecutionLifecycleCleanable {

    public static boolean initialized;
    public static boolean cleaned;

    public void visitBefore(SAXElement element, ExecutionContext executionContext) throws SmooksException, IOException {
        if(!initialized) {
            fail("Resource should be initialized!");
        }
        if(cleaned) {
            fail("Resource shouldn't be cleaned yet!");
        }
    }

    public void executeExecutionLifecycleInitialize(ExecutionContext executionContext) {
        initialized = true;
    }

    public void executeExecutionLifecycleCleanup(ExecutionContext executionContext) {
        cleaned = true;
    }
}