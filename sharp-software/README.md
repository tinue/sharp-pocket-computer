# Basic and Asssembly programs for Sharp Pocket Computers
These programs were either written by myself, or the source is given below.

## setcom.bas (part of the pc1600-serial project)
The purpose of this program is to simplify testing of the SharpCommunicator tool.
* DEF-J Initializes the serial port, and enables redirection of both input and output. It also sets the line length to 80 characters, and the line end to CR/LF.
* DEF-S Saves the program (first launch the SharpCommunicator, then press DEF-S)
* DEF-L Loads the program (first press DEF-L, then launch the SharpCommunicator)
* DEF-A launches a very simple test program which prints to the printer.

## biorhythm.bas (PC-1261)
The program prints the biorhythm for a specific month, based on the birth date.
It is taken from the manual of the CE-125 printer. While originally intended by Sharp for the
PC-1250/60, it works unchanged on the PC-1600. For the application to work,
the print output must be redirected using `SETDEV "COM1:",PO`, and the `SharpCommunicator`
must be running in printer mode.

## 39zeich.bas (PC-1600)
Downloaded from [sharp-pc-1600](https://www.sharp-pc-1600.de/Down_Anwender.html).
This is to test a mixed Basic / Assembly application.

## worldclock.bas (PC-1600)
Downloaded from the Facebook group "80's-90's Sharp & Casio World of Computing".
I tweaked the program slightly, to allow for a non-GMT city to be the "home base".
The program does not support cities in India, because they differ by a fraction
of a full hour from GMT (5:30 hours).

## kalender.bas
Found in a scan of an old [German magazine](http://www.sharp-pc-1600.de/allesfuersharp/Alles_fur_Sharp_Computer_09.pdf)
The program prints the calendar of the chosen year onto the printer.

## commando.bas (PC-1500)
### Story
My name is Command, a man who once participated in Vietnam. One day, my girlfriend was kidnapped by an organization. So I stood up to the organization alone.
### Content
Keys:
* 4 / 6: Left / Right
* 7 / 9: Left / Right
* 2 / 8: Crouch / Stand up
* 5: Use Sword

Please move the command and break the enemy's young land. Jump left and right in country 6, left and right in country 6. Put it in 2 and stand up at 8. You can't move when you're busy. I pretend to be in control with the national key. If there is joy next to you, you can defeat it. Also, the number on the left is damage, and if it is less than 0, one person will die. There are the first three commands. The damage increases when it collides with acid and when the handball is emitted near you. However, even if it is confirmed nearby, if you put it off, you can reduce the damage, and if you put it next to the first obstacle, you will not receive damage.

To a certain extent, if you go to the right (a little to the left of the building), the surface is clear. I was able to rescue her with 5 sides clear. The number of commands will increase by 1 person, and then it will be repeated by 1 to 5 sides.

Move Commando to destroy the enemy base. Use "4" and "6" to jump left and right, "7" and "9" to jump left and right, "2" to lie down, and "8" to stand up. You cannot move while lying down. Use the "5" key to swing your sword and defeat enemies next to you.
Also, the number on the left is damage, and if it falls below 0, one person will die. There are three Commandos at the beginning. Damage increases when you collide with an enemy or when a grenade explodes near you.
However, if you lie down even if an explosion occurs nearby, you can reduce damage, and if you lie down next to an obstacle, you will not receive damage.
If you go to the right to a certain extent (slightly left of the building), you will clear the level. If you clear level 5, you will rescue her, one more person will be added to your command, and levels 1 to 5 will be repeated.