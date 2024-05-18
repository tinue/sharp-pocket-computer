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