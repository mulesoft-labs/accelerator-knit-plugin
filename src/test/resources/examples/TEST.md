# color
###### color
This module supports color related functions.

### Functions
__fun__ `mapColor` ( __data__)

> __param__ `data` is an input color object.   
__return__ a result color object.   
> 
> Maps a color object to a result color object. 

> | Source Field |  Target Field |  Comments | 
> | -|-|-|
> | name |  data.color |  The name, of the color. | 
> | type |  data.category |  The data category. | 
> | rgba |  data.code.rgba |  Mapped RGBA value. | 
> | hex |  data.code.hex |  The hex color value. | 


__fun__ `mapRgba` ( __rgba__)

> __param__ `rgba` is an array with the 4 RGBA values.   
__return__ A RGBA string.   
> 
> Maps the provided RGBA array to a RGBA string. 



# palette
###### palette
This module provides palette support functions.

### Functions
__fun__ `mapPalette` ( __data__)

> __param__ `data` is a colors object.   
__return__ a myColors object.   
> 
> Map palette maps the provided colors object and returns a myColors object with the mapped result. 



# main
###### main
This is the entry point script. It imports the mapPallette 
method and calls it with the payload.
