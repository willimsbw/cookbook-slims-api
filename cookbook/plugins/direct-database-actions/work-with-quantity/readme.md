# plugin-name: "work-with-quantity"


# General Introduction
This plugin creates one flow intended to serve as example for handling Quantity and Unit utilizing SLIMSGate api. 

## Usage: _Content Management_
## Type: *slimsgate*

# Flow

<details>

<summary>

### <a name="Quantity"></a> Work with Quantity

</summary>

#### User inputs:
None
#### Process
- Fetch a content record with the configured barcode in the property _contentBarcode_
- Retrieve the volume (field 'cntn_cf_volume'), this is a Map<String, Object>, and convert/cast it to a Quantity Object using the service *QuantityService*
- Create a new Quantity Object with the Unit configured in the property _quantityToSubtractUnitName_ using the service *UnitService* and a hard-coded amount of 2 
- Compare the two Quantity Objects to make sure that the volume of this content is bigger or equal to the Quantity created, because we want to reduce the volume of the content with this Quantity and want to avoid having a negative quantity
- Finally, converting the reduced volume Quantity to another configured unit in the property _finalUnitName_ using the service *UnitService* and using this value to update the hard-coded field 'cntn_cf_reducedVolume'

</details>

# Required Configuration
- At least one Content Type
- At least one Content
- SLIMS Store package 'SI Unit' installed
- At least two custom fields on Content table
  - cntn_cf_volume of datatype 'Quantity', dimension is 'Volume' and unit 'ml'
  - cntn_cf_reducedVolume of datatype 'Quantity', dimension is 'Volume' and unit 'l'

# Parameters description
* (_String_) **contentBarcode**: The "Barcode" of the content we want to use here
* (_String_) **quantityToSubtractUnitName**: The abbreviation of the unit we want to use to reduce the volume (like this it will be: remove 2 <of this unit> to the cntn_cf_volume)
* (_String_) **finalUnitName**: The abbreviation of the unit we want to use for the final value in the field 'cntn_cf_reducedVolume', this will be used to convert the current value to this unit