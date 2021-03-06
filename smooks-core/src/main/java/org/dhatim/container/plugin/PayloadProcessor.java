/*
 * Milyn - Copyright (C) 2006 - 2010
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License (version 2.1) as published
 * by the Free Software Foundation.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.
 * 
 * See the GNU Lesser General Public License for more details:
 * http://www.gnu.org/licenses/lgpl.txt
 */

package org.dhatim.container.plugin;

import org.dhatim.Smooks;
import org.dhatim.SmooksException;
import org.dhatim.assertion.AssertArgument;
import org.dhatim.container.ExecutionContext;
import org.dhatim.payload.ByteResult;
import org.dhatim.payload.JavaResult;
import org.dhatim.payload.StringResult;

import javax.xml.transform.Result;
import javax.xml.transform.Source;

/**
 * Processor class for an abstract payload type.
 * <p/>
 * This class can be used to ease Smooks integration with application
 * containers (for example ESBs).  It works out how to filter the supplied Object payload
 * through Smooks, to produce the desired {@link ResultType result type}.
 * <p/>
 * The "payload" object supplied to the {@link #process(Object, org.dhatim.container.ExecutionContext)}
 * method can be one of type:
 * <ul>
 * <li>{@link String},</li>
 * <li>{@link Byte} array,</li>
 * <li>{@link java.io.Reader},</li>
 * <li>{@link java.io.InputStream},</li>
 * <li>{@link Source},</li>
 * <li>{@link SourceResult}, or</li>
 * <li>any Java user type (gets wrapped in a {@link org.dhatim.payload.JavaSource}).</li>
 * </ul>
 * <p/>
 * The {@link SourceResult} payload type allows full control over the filter
 * {@link Source} and {@link Result}.
 *
 * @author <a href="mailto:daniel.bevenius@gmail.com">Daniel Bevenius</a>
 */
public class PayloadProcessor {

    private Smooks smooks;
    private ResultType resultType;
    private String javaResultBeanId;

    /**
     * Public constructor.
     * @param smooks The Smooks instance to be used.
     * @param resultType The required result type.
     */
    public PayloadProcessor(final Smooks smooks, final ResultType resultType) {
        AssertArgument.isNotNull(smooks, "smooks");
        AssertArgument.isNotNull(resultType, "resultType");
        this.smooks = smooks;
        this.resultType = resultType;
    }

    /**
     * Set the bean ID to be unpacked from a {@link JavaResult}.
     * <p/>
     * Only relevant for {@link ResultType#JAVA}.  If not specified, the
     * complete {@link org.dhatim.payload.JavaResult#getResultMap() result Map}
     * will be returned as the result of the {@link #process(Object, org.dhatim.container.ExecutionContext)}
     * method call.
     *
     * @param javaResultBeanId The bean ID to be unpacked.
     */
    public void setJavaResultBeanId(final String javaResultBeanId) {
        AssertArgument.isNotNullAndNotEmpty(javaResultBeanId, "javaResultBeanId");
        this.javaResultBeanId = javaResultBeanId;
    }

    /**
     * Process the supplied payload.
     * <p/>
     * See class level javadoc.
     *
     * @param payload The payload to be filtered. See class level javadoc for supported data types.
     * @return The filter result. Will be "unpacked" as per the {@link ResultType} supplied in the
     * {@link #PayloadProcessor(org.dhatim.Smooks, ResultType) constructor}.
     * @throws SmooksException
     */
    public final Object process(final Object payload, final ExecutionContext executionContext) throws SmooksException {
        AssertArgument.isNotNull(payload, "payload");

        Source source;
        Result result;

        if (payload instanceof SourceResult) {
            SourceResult sourceResult = (SourceResult) payload;
            source = sourceResult.getSource();
            result = sourceResult.getResult();
        } else {
            source = SourceFactory.getInstance().createSource(payload);
            result = ResultFactory.getInstance().createResult(resultType);
        }

        // Filter it through Smooks...
        smooks.filterSource(executionContext, source, result);

        // Extract the result...
        if (result instanceof JavaResult) {
            if (javaResultBeanId != null) {
                return ((JavaResult) result).getResultMap().get(javaResultBeanId);
            } else {
                return ((JavaResult) result).getResultMap();
            }
        } else if (result instanceof StringResult) {
            return ((StringResult) result).getResult();
        } else if (result instanceof ByteResult) {
            return ((ByteResult) result).getResult();
        }

        return result;
    }
}
