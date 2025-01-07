/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import java.util.Map;

import org.apache.camel.Handler;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.genohm.slims.common.model.Content;
import com.genohm.slims.common.model.ContentMeta;
import com.genohm.slims.custom.WorkWithQuantityConfiguration;
import com.genohm.slims.custom.api.SlimsLogger;
import com.genohm.slims.server.dao.common.Dao;
import com.genohm.slims.server.dao.criterion.SlimsRestrictions;
import com.genohm.slims.server.quantity.Quantity;
import com.genohm.slims.server.repository.queriers.ContentRecordQueries;
import com.genohm.slims.server.service.quantity.QuantityService;
import com.genohm.slims.server.service.unit.UnitService;
import com.genohm.slimsgate.camel.gatekeeper.SlimsGateErrorException;

@Component
public class WorkWithQuantityBean {
	private static final Logger LOGGER = SlimsLogger.getLogger(WorkWithQuantityBean.class);

	@Autowired
	private WorkWithQuantityConfiguration workWithQuantityConfiguration;
	@Autowired
	private Dao<Content> contentDao;
	@Autowired
	private ContentRecordQueries contentRecordQueries;
	@Autowired
	private QuantityService quantityService;
	@Autowired
	private UnitService unitService;

	@Transactional
	@Handler
	public void workingWithQuantity() {
		// Fetch record with a quantity field
		Map<String, Object> content = contentRecordQueries.fetchOneStrict(SlimsRestrictions.equals(ContentMeta.BAR_CODE, workWithQuantityConfiguration.getContentBarcode())).toJavaUtil()
				.orElseThrow(() -> new SlimsGateErrorException(String.format("No content found with barcode (%s) = '%s'", ContentMeta.BAR_CODE, workWithQuantityConfiguration.getContentBarcode())));
		Quantity initialVolume = quantityService.castOrConvertToQuantity(content.get("cntn_cf_volume"));
		LOGGER.info("After the 'cast or convert' we have this value {} and the type is {}. This will allow us to perform actions on this Quantity.", initialVolume, initialVolume.getClass().getName());

		// Create 1 new Quantity Object
		Quantity volumeToSubtract = new Quantity(2, unitService.convertToUnit(workWithQuantityConfiguration.getQuantityToSubtractUnitName()));

		// method `compareTo` is handling the unit conversion to do a correct comparison and will return:
		//  -1  if `volumeToSubtract` is bigger than `initialVolume`
		//  0   if `volumeToSubtract` is equal to `initialVolume`
		//  1   if `volumeToSubtract` is smaller than `initialVolume`
		if (initialVolume.compareTo(volumeToSubtract) < 0){
			// Then we should not reduce the volume -> we will be in negative volume and this is not good
			throw new SlimsGateErrorException(String.format("Error - Not enough volume. Initial volume is %s and we want to remove %s of it. This will lead to a negative volume, this is not accepted.",
					initialVolume, volumeToSubtract));
		}

		// Subtract 2 of the unit provided in the plugin configuration (default = `dl`) to the volume (unit is the one coming from the SLIMS field, for example `ml`)
		Quantity reducedVolume = initialVolume.substract(volumeToSubtract);

		// Convert to a certain unit provided in the plugin configuration (parameter: `finalUnitName`)
		Quantity finalVolume = reducedVolume.convertTo(unitService.convertToUnit(workWithQuantityConfiguration.getFinalUnitName()));

		// update the record, another field with this new value
		content.put("cntn_cf_reducedVolume", finalVolume);
		contentDao.update(content);
	}
	
}
