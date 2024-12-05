# Quick Start Guide

## Load a file from your computer onto the Sharp PC-1500 (using CE-158X via USB)
First, prepare the PC-1500 by entering:
- `SETDEV U1,CI,CO`
- `CLOAD`

Then, send the program from your PC with: `java -jar SharpCommunicator.jar --in-file globe.bas`

## Load a file from your computer onto the Sharp PC-1600 (using USB-UART)
First, prepare the PC-1600 by entering:
- `SETCOM "COM1:",9600,8,N,1,N,N`
- `INIT "COM1:",4096`
- `OUTSTAT "COM1:"`
- `RCVSTAT "COM1:",24`
- `LOAD "COM1:`

Then, send the program from your PC with: `java -jar SharpCommunicator.jar --in-file globe.bas --device pc1600`

## Sharp PC-1500 -> Computer (using CE-158X via USB)
- First, prepare the PC-1500 by entering `SETDEV U1,CI,CO`
- Next, start the receiver on the PC with: `java -jar SharpCommunicator.jar --out-file globe.bas --out-format binary`
- Last, save the program on the PC-1500 with: `CSAVEa`

## Shrink a Basic program
Take the file `globe.bas` from the PC-1500 Basic programs: Line 90 seemingly can't be keyed in!
The PC-1500 stops accepting input after 80 characters.

When hitting "Enter" after 80 characters, new space becomes available. This is because blanks are
removed, and the Basic keywords are replaced by 2 byte tokens. One can now continue entering the rest of the line.

Because this limit also affects emulators such as "PockEmul", it can be useful to compact a Basic listing as
much as possible: Remove extra blanks, and replace long Basic keywords by their abbreviated version.

For example, `10 PRINT I` becomes `10P.I`. The compacted version is more likely to directly fit into 80 characters.
Even the lines that are still too long have fewer missing characters to be fixed manually.
To facilitate the manual fix, these lines and the missing characters are printed to `stderr`.

To shrink a program, enter:  
`java -jar SharpCommunicator.jar --in-file globe.bas --out-file clip`

Paste the result into the keyboard simulator of PockEmul. After the program has been
entered, fix the 8 lines that have been printed to `stderr` manually in the emulator.