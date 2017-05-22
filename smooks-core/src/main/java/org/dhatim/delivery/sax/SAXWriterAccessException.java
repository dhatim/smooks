
package org.dhatim.delivery.sax;

import org.dhatim.SmooksException;

/**
 * {@link SAXElement} element writer access excecption.
 * <p/>
 * See {@link org.dhatim.delivery.sax.SAXElement}.
 *
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
public class SAXWriterAccessException extends SmooksException {

    public SAXWriterAccessException(String message) {
        super(message);
    }

    public SAXWriterAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
