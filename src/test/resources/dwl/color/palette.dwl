/**
 * This module provides palette support functions.
 */

%dw 2.0

import mapColor from dw::color::color

/**
 * Map palette maps the provided colors object and returns 
 * a myColors object with the mapped result.
 * @param data is a colors object.
 * @return a myColors object.
 */
fun mapPalette (data) = {
    myColors: data.colors map (item, index) -> mapColor(item)
}