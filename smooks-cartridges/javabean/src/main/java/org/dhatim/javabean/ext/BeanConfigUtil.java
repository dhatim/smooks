/*
 * Milyn - Copyright (C) 2006 - 2010
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License (version 2.1) as published by the Free Software
 *  Foundation.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 *  See the GNU Lesser General Public License for more details:
 *  http://www.gnu.org/licenses/lgpl.txt
 */

package org.dhatim.javabean.ext;

import org.dhatim.cdr.ConfigSearch;
import org.dhatim.cdr.SmooksConfigurationException;
import org.dhatim.cdr.SmooksResourceConfiguration;
import org.dhatim.cdr.extension.ExtensionContext;
import org.dhatim.container.ExecutionContext;
import org.dhatim.javabean.BeanInstanceCreator;

import java.util.List;

/**
 * Bean configuration utilities.
 * 
 * <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public abstract class BeanConfigUtil {
    
    public static final String BEAN_CLASS_CONFIG = "beanClass";

    public static SmooksResourceConfiguration findBeanCreatorConfig(String beanId, ExecutionContext executionContext) {
        ExtensionContext extensionContext = ExtensionContext.getExtensionContext(executionContext);
        List<SmooksResourceConfiguration> creatorConfigs = extensionContext.lookupResource(new ConfigSearch().resource(BeanInstanceCreator.class.getName()).param("beanId", beanId));

        if(creatorConfigs.size() > 1) {
            throw new SmooksConfigurationException("Multiple <jb:bean> configurations exist for beanId '" + beanId + "'.  'beanId' values must be unique.");
        }

        return creatorConfigs.get(0);
    }
}
