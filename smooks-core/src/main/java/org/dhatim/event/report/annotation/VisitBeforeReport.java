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
package org.dhatim.event.report.annotation;

import org.dhatim.cdr.annotation.AnnotationConstants;

import java.lang.annotation.*;

/**
 * Visit before report annotation.
 *
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public abstract @interface VisitBeforeReport {

    public abstract String condition() default "true";

    public abstract String summary() default AnnotationConstants.NULL_STRING;

    public abstract String detailTemplate() default AnnotationConstants.NULL_STRING;
}