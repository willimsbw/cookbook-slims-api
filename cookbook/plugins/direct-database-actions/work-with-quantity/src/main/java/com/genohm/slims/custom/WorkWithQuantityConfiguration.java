/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom;

public class WorkWithQuantityConfiguration {

	private String contentBarcode;
	private String quantityToSubtractUnitName;
	private String finalUnitName;

	public WorkWithQuantityConfiguration() {

	}

	public static WorkWithQuantityConfiguration getDefault() {
		WorkWithQuantityConfiguration workWithQuantityConfiguration = new WorkWithQuantityConfiguration();
		workWithQuantityConfiguration.contentBarcode = "some content barcode";
		workWithQuantityConfiguration.quantityToSubtractUnitName = "dl";
		workWithQuantityConfiguration.finalUnitName = "l";
		return workWithQuantityConfiguration;
	}

	public String getContentBarcode() {
		return contentBarcode;
	}

	public String getQuantityToSubtractUnitName() {
		return quantityToSubtractUnitName;
	}

	public String getFinalUnitName() {
		return finalUnitName;
	}
}
