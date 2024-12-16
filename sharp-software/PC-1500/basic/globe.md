# Background information on "globe.bas"

## Original article

[Geographie dans l'espace](pictures/GeographieDanslEspace.pdf)

## English translation

Note 99% automatic translation, with a bit of cleanup my me. For the mentioned figures, check the PDF.

_“And yet it turns!”, Galileo would certainly have exclaimed if he had been able to see this program working. It in
fact draws, on the printer of a PC-1500, the terrestrial globe - with the seas and continents - from all the angles
desired by amateur cartographers._

Who has never noticed the distortions of geographic maps caused by the roundness of the earth? Cartographers currently
use several projection systems: conical, cylindrical, etc.; but none offers a faithful reproduction. As soon as we
approach the polar regions, the geographical surfaces increase disproportionately, and the beautiful planisphere turns
out to be much less precise than the cumbersome world map.

How to resolve the trade-off between precision and size? The solution seems to have been found by this program running
on PC-1500, which allows you to obtain on a printer the representation of the terrestrial globe in all its faces. From
the pole to the equator, all you have to do is enter a few coordinates and the printer gives you the desired face of the
globe, without any of the distortions observable on traditional maps.

The only drawback for anyone who wants to copy it: the endless lines of DATA, which correspond to the coordinates of
each of the points of the geographical contours.

For the rest, nothing could be simpler. The program is quite interactive and begins with a series of questions
concerning the angle at which we want to see the earth appear.

R (MM) asks to indicate the radius of the earth in the drawing. This entry therefore controls the final size of the
plot. Be careful not to enter a value greater than 22 so that the entire drawing fits on the width of the printer paper.
Otherwise, only the central part of the drawing will be printed as in figures 2 and 3 (page 163).

`Alpha` requires the entry of a value from 0 to 360: this is, in fact, the angle of inclination towards the front of the
earth's axis (figure 1 opposite).

`Beta` concerns the angle of rotation of the tilted assembly around the vertical axis.
`Phi` asks for the longitude of the meridian passing through the earth's axis and the vertical axis, and `Grid` the
interval between meridians and parallels (figures 4 and 5). If `Grid` = 0, these lines do not appear.

`Step` is the angle according to which the argument varies with each step, for tracing the contour of the terrestrial
globe, the meridians and parallels. These lines should not be too “choppy”: six degrees seem sufficient (figures 6 and
7).

After these few questions come two options: transparency and color. The first allows (or not) to see what is on the
hidden side of the globe (figures 8, 9 and 10), and `Color` gives color drawings.

The principle of tracing is very simple: meridians and parallels are traced by loops which automatically calculate the
longitude and latitude of successive points. The plot points are contained in DATA, and a subroutine, from 290 to 360,
calculates z to determine whether each point is visible or not (because of the transparency option), then determines x
and y before plotting the line.

The formulas used in the subroutine are simply the direction cosines for the double change of axes by the alpha (a) and
beta (b) rotations.

This program has been optimized to fit on 6 KB of memory. As a result, only one 4 KB memory expansion module is required
for its operation.

Jose Baume