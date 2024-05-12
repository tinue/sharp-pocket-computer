# Basic and Asssembly programs for the PC-1600
These programs were either written by myself, or the source is given below.

## setcom.bas
The purpose of this program is to simplify testing of the SharpCommunicator tool.
* DEF-J Initializes the serial port, and enables redirection of both input and output. It also sets the line length to 80 characters, and the line end to CR/LF.
* DEF-S Saves the program (first launch the SharpCommunicator, then press DEF-S)
* DEF-L Loads the program (first press DEF-L, then launch the SharpCommunicator)
* DEF-A launches a very simple test program which prints to the printer.