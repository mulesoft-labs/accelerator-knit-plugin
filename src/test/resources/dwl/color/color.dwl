/**
 * This module supports color related functions.
 */

%dw 2.0

/**
 * Maps a color object to a result color object.
 * @param data is an input color object.
 * @return a result color object.
 * @table Source Field, Target Field, Comments
 * @row name, data.color, The name\\, of the color.
 * @row type, data.category, The data category.
 * @row rgba, data.code.rgba, Mapped RGBA value.
 * @row hex, data.code.hex, The hex color value.
 */
fun mapColor (data) = {
    name: data.color,
    "type": data.category,
    colorType: data."type",
    rgba: mapRgba(data.code.rgba),
    hex: data.code.hex
}

/**
 * Maps the provided RGBA array to a RGBA string.
 * @param rgba is an array with the 4 RGBA values.
 * @return A RGBA string.
 */
fun mapRgba (rgba) =
(if (!isEmpty(rgba))
    "(" ++ rgba[0] ++ ", " ++ rgba[1] ++ ", " ++ rgba[2] ++ ", " ++ rgba[3] ++ ")"
else null)
