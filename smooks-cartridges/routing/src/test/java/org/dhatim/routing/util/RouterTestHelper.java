package org.dhatim.routing.util;

import org.dhatim.container.MockExecutionContext;
import org.dhatim.javabean.repository.BeanId;
import org.dhatim.routing.jms.TestBean;

public final class RouterTestHelper
{
	private RouterTestHelper() {}

	public static MockExecutionContext createExecutionContext(
			final String beanIdName,
			final Object bean)
	{
        final MockExecutionContext executionContext = new MockExecutionContext();

        BeanId beanId = executionContext.getContext().getBeanIdStore().register(beanIdName);
        executionContext.getBeanContext().addBean(beanId, bean, null);
        return executionContext;
	}

	public static TestBean createBean()
	{
		final String name = "Daniel";
		final String address = "Fleminggatan";
		final String phoneNumber = "555-555-5555";

		final TestBean bean = new TestBean();
		bean.setAddress( address );
		bean.setName( name );
		bean.setPhoneNumber( phoneNumber );
		return bean;
	}

}
