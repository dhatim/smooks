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
package org.dhatim.javabean.lifecycle;

import org.dhatim.delivery.ExecutionLifecycleCleanable;
import org.dhatim.delivery.annotation.Initialize;
import org.dhatim.container.ExecutionContext;
import org.dhatim.cdr.annotation.ConfigParam;
import org.dhatim.javabean.context.BeanContext;
import org.dhatim.util.CollectionsUtil;

import java.util.Set;
import java.util.Map.Entry;

/**
 * Bean Result Cleanup resource.
 * <p/>
 * Execution Lifecycle Cleanable resource that performs Java result cleaup.
 *
 * @author <a href="mailto:tom.fennelly@jboss.com">tom.fennelly@jboss.com</a>
 */
public class BeanResultCleanup implements ExecutionLifecycleCleanable {

    @ConfigParam
    private String[] beanIDs;
    private Set<String> beanIDSet;

    @Initialize
    public void initialize() {
        beanIDSet = CollectionsUtil.toSet(beanIDs);
    }

    /**
     * Execute the cleanup.
     * @param executionContext The execution context.
     */
    public void executeExecutionLifecycleCleanup(ExecutionContext executionContext) {
        BeanContext beanContext = executionContext.getBeanContext();
        Set<Entry<String, Object>> beanSet = beanContext.getBeanMap().entrySet();

        for(Entry<String, Object> beanEntry : beanSet) {
            String beanID = beanEntry.getKey();
            if(!beanIDSet.contains(beanID)) {
            	beanContext.removeBean(beanID, null);
            }
        }
    }
}
