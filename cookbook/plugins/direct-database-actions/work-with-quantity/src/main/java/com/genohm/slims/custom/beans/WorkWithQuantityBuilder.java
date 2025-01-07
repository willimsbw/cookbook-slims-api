/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkWithQuantityBuilder extends RouteBuilder {
	@Autowired
	private WorkWithQuantityBean workWithQuantityBean;

	@Override
	public void configure() throws Exception {
		from("direct:work-with-quantity")
			.bean(workWithQuantityBean)
			.routeId("work-with-quantity");
	}

}
