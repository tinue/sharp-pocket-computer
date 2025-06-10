# Sharp CE-150 Printer / Plotter
## Replacement pens
Replacement pens for the CE-150 plotter are still available, but:
1. They are expensive (used for medicinal equipment)
1. They dry up within 2-3 weeks, even if they remain capped

Therefore, I decided to print my own replacement pens. They are far from perfect, unfortunately. Regular ball pen mines do not deliver enough ink for a good print, and gel mines are too big to fit into a 3D printed shell.

### Material
- Mines: I dismantle an "Original BiC 4 Colours" 1mm ball point pen. It containes four mines which have the correct colors.
- 3D printer: I am using a Bambu A1 Mini printer
- Filament: Generic ABS filament.

### Print
The print is done with Bambu Studio, by importing the .stl file. Copy/paste as many pens as you want in Bambu Studio, and print them out.

### Assembly
The original pen is 23.3mm long, and its replacement needs to be very precise. Too short, and nothing gets printed. Too long, and it will jam up the rotating pen holder in the printer, and bend the metal push-back wheel out of shape. It might even damage the metal wheel beyond repair, so be warned!

By design in Tinkercad (see below), the pen holder is 18.1mm high with an inside hole of 15.4mm length. Due to printing tolerances and filament variations, you need to measure the printed result, and you might even have to measure each piece. What you need to figure out is the height of the end piece, because this is what you need to subtract from the 23.3mm overall length.

Given the design values:
- Head part is 18.1 - 15.4, i.e. 2.7mm
- Length of the cut pen mine is therefore 23.3 - 2.7, i.e. 20.6mm

Cut the mine, push it firmly into the holder and measure the completed pen with calipers. Only if the length is 23.3 or maybe 23.4, try it in the printer.

## Appendix
### 3D Part
The piece was designed with Tinkercad. It consists of these parts:
- Cone, base radius 2.5mm, top radius 1.4mm, height 0.6mm. Placed 16.4mm above the plate
- Cone, base radius 1.4mm, top radius 1.0mm, height 1.1mm. Placed 17.0mm above the plate
- Cylinder 5mm diameter, 16.4mm height, on plate
- Cylinder 3.2mm diameter, 16.4mm height, hole, on plate

Assembly:
- Center and combine the two cones to produce the head part
- Center and combine the cylinder and the head part, creating a solid pen holder.
- Center and combine the solid pen holder and the hollow cylinder, to make room for the pen mine.

If you cut the pen mine too short, you can ungroup the pen holder, shrink the hollow cylinder a bit, and regroup them. Before you re-print, try to remove the too-short pen from the original pen holder: This very often fails, because the plastic part separates from the metal part, leaving the plastic part and the ink inside of the pen holder.


