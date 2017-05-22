package org.dhatim.javabean.decoders;

import java.util.Date;

import org.dhatim.javabean.DataDecodeException;
import org.dhatim.javabean.DecodeType;

/**
* {@link java.sql.Timestamp} data decoder.
* <p/>
* Extends {@link org.dhatim.javabean.decoders.DateDecoder} and returns
* a java.sql.Timestamp instance.
* <p/>
*
* @author <a href="mailto:maurice.zeijen@smies.com">maurice.zeijen@smies.com</a>
*/
@DecodeType(java.sql.Timestamp.class)
public class SqlTimestampDecoder extends DateDecoder
{
	@Override
	public Object decode(String data) throws DataDecodeException {
		Date date = (Date)super.decode(data);
	    return new java.sql.Timestamp(date.getTime());
	}
}

