# Basic and Asssembly programs for the PC-1600
These programs were either written by myself, or the source is given below.

## setcom.bas
The purpose of this program is to simplify testing of the SharpCommunicator tool.
* DEF-J Initializes the serial port, and enables redirection of both input and output. It also sets the line length to 80 characters, and the line end to CR/LF.
* DEF-S Saves the program (first launch the SharpCommunicator, then press DEF-S)
* DEF-L Loads the program (first press DEF-L, then launch the SharpCommunicator)
* DEF-A launches a very simple test program which prints to the printer.

## biorhythm.bas
The program prints the biorhythm for a specific month, based on the birth date.
It is taken from the manual of the CE-125 printer. While originally intended by Sharp for the
PC-1250/60, it works unchanged on the PC-1600. For the application to work,
the print output must be redirected using `SETDEV "COM1:",PO`, and the `SharpCommunicator`
must be running in printer mode.

## 39zeich.bas
Downloaded from [sharp-pc-1600](https://www.sharp-pc-1600.de/Down_Anwender.html).
This is to test a mixed Basic / Assembly application.

## worldclock.bas
Downloaded from the Facebook group "80's-90's Sharp & Casio World of Computing".
I tweaked the program slightly, to allow for a non-GMT city to be the "home base".
The program does not support cities in India, because they differ by a fraction
of a full hour from GMT (5:30 hours).

## commando.bas
### Story
My name is Command, a man who once participated in Vietnam. One day, my girlfriend was kidnapped by an organization. So I stood up to the organization alone.
### Content
Please move the command and break the enemy's young land. Jump left and right in country 6, left and right in country 6. Put it in 2 and stand up at 8. You can't move when you're busy. I pretend to be in control with the national key. If there is joy next to you, you can defeat it. Also, the number on the left is damage, and if it is less than 0, one person will die. There are the first three commands. The damage increases when it collides with acid and when the handball is emitted near you. However, even if it is confirmed nearby, if you put it off, you can reduce the damage, and if you put it next to the first obstacle, you will not receive damage.

To a certain extent, if you go to the right (a little to the left of the building), the surface is clear. I was able to rescue her with 5 sides clear. The number of commands will increase by 1 person, and then it will be repeated by 1 to 5 sides.
