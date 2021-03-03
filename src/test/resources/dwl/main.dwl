/**
 * This is the entry point script. It imports the mapPallette 
 * method and calls it with the payload.
 */

%dw 2.0

import mapPalette from dw::color::palette

output application/json skipNullOn="everywhere"
---

/**
 * Map payload according to the following mappings.
 * @table Input, Output
 * @row propertyIn, propertyOut
 */
mapPalette(payload)
