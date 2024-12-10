# Sharp Pocket Computer Communicator
The purpose of this program is to safe and load Basic programs that are stored on your computer
from and to Sharp PC-1500 and PC-1600 devices through a _serial interface_.

The program takes care of the peculiarities of the two Pocket Computer devices, such as the different line
ending formats used, or the end of file marker. It also slows down communication if necessary
for the PC-1500, so that it can keep up.

## Quick Start for the impatient
See [Quickstart](Quickstart.md)

## SharpCommunicator User Guide
The Sharp Communicator comes in the form of an executable JAR file. You need
to have Java installed (at least v17), and access to a command line.

The tool is launched as follows:

`java -jar SharpCommunicator.jar <options>`

Note: `in`and `out` are from the point of view of the PC that runs the SharpCommunicator.  
The options are:
- `--in-file (-i)`: Name of the file to be loaded onto the PC-1500/1600. Before launching the tool, enter `LOAD "COM1:"` on the PC-1600, or `CLOAD` / `CLOADa` on the PC-1500.
- `--out-file (-o)`: Save a file from the PC-1500/1600 to the PC. Launch the tool, and then enter `SAVE "COM1:"` / `SAVE "COM1:", A` on the PC-1600, or `CSAVE` / `CSAVEa` on the PC-1500.
  Note that the special filename `clip` is reserved for the clipboard.
- `--in-format ascii`: Force the infile to be read as ASCII (default for any `.BAS` file, or when reading from the clipboard).
- `--in-format binary`: Force the infile to be read as binary (default for any file except `.BAS`)
- `--out-format ascii` Force the outfile to be ASCII (default for any `.BAS` file, or when writing to the clipboard).
- `--out-format asciicompact` Force the outfile to be ASCII, and shrink it (e.g. `10: PRINT I` becomes `10P.I`). This only works if the in-file is ASCII!
- `--device pc1500`: Assume the input or output device to be a PC-1500 (default).
- `--device pc1500a`: Assume the input or output device to be a PC-1500a. The software behaves identically for the PC-1500 and PC-1500a, so you can leave the default when using a PC-1500a. 
- `--device pc1600`: Assume the input or output device to be a PC-1600.
- `--add-utils (-u)`: Adds some shortcuts starting with line 61000 (`Def-J`: Init serial; `Def-S`: Save, `Def-L`: Load)

Software for the PC-1500 or PC-1600 can come in two formats:
- ASCII: Basic programs, readable / editable on the PC (e.g. `10 FOR I=1 to 100`).
- Binary: This can be a machine language program, the contents of the reserve area, or a pre-tokenized Basic program.

The pocket computers can read and write both ASCII and binary programs. Reading an ASCII program is comparable to
entering the program on the Pocket Computers keyboard, and has similar limitations in terms of maximum line size. The
PC-1500 also needs time to parse and tokenize every line, and a delay after every line feed is necessary.

Binary programs are comparable to cassette I/O. No parsing is required, and they can be sent to the Pocket Computer
at a constant speed. A binary file needs a header that tells the Pocket Computer about the type and the length
of the file content. Such a header is added automatically by the SharpCommunicator, if it is missing in the file.

To load ASCII files, the PC-1500 needs to be told that an ASCII file is to be expected, by adding an `a`: `CLOADa`.
The same is true for saving: `CSAVEa`.

The PC-1600 can detect on its own that an ASCII file is being received, and a simple `LOAD "COM1:"` will do.
Saving on the other hand must be specified with `SAVE "COM1:",A`.

When a file it transferred from the Pocket Computer to the PC (`--out-file` parameter), the Sharp Communicator software 
currently does not try to detect if an ASCII or binary program is being received, and will just save the file
as binary to disk.  

If the option `--out-format asciicompact` is given, and if the input file is ASCII, then the
output file is shrunk before writing it to disk / clipboard.
- `CSAVEa` / `SAVE "COM1:",A` will thus result in an ASCII file on the disk.
- `CSAVE` / `SAVE "COM1:"` on the other hand will give a binary file.

When a file is sent to the pocket computer (`--in-file` parameter), ASCII and binary files are treated differently:
- ASCII files are converted to tokenized Basic and in the case of the PC-1500 must be loaded with `CLOAD`.
- This can be overwritten with `--out-format ascii` or `--out-format asciicompact`. In this case, the file is 
  sent line by line, with the necessary pauses after every line. It must be loaded with `CLOADa` on the PC-1500.
- Binary files are loaded without pauses, and the binary header is added if necessary. It needs to be loaded with `CLOAD` on the PC-1500.

Note: If both `--in-file`and `--out-file` are given, then no transfer from/to a Pocket Computer happens. This way,
one can for example convert an ASCII `.BAS` file into a tokenized binary version.

Or take this example:
`java -jar SharpCommunicator.jar --in-file globe.bas --out-file clip --out-format asciicompact`. This
will put a file into the clipboard that can be pasted into an emulator, such as PockEmul, using its keyboard emulator. On stderr
one can see that three lines are still a bit too long, and these lines must then be fixed manually on the emulator.
Compare this to using the original `globe.bas`: A lot of lines are too long, and one can't see which ones. It will take
a hour or so to manually check and fix every line in the emulator.

## Limitations
### Abbreviations are not supported
Many Basic commands can be abbreviated. In fact, this is what the option `--out-format asciicompact` does.
For example, `PRINT` can be abbreviated as `P.`, `PR.`, `PRI.` or `PRIN.`. Even though this would be simple to implement,
it is not done. The reason is this: When listing a program with `LLIST`, all abbreviations get expanded into the
full keyword. Therefore, one never finds any old source code in magazines etc. that actually use these abbreviations.

Note that such files can still be loaded to a Pocket Computer as ASCII, they just can't be converted into tokenized Basic.

### Only one serial port allowed
In this release, the serial port cannot be manually specified. The software uses the port that is present. Zero or more than
one port will confuse the software. In the next release, the port can be manually specified for such cases.

## PocketPC Device notes
### Sharp PC-1600
After a reset / power loss of the PC-1600, these commands need to be entered
to make its serial port ready for receiving data:
```
SETCOM "COM1:",9600,8,N,1,N,N
INIT "COM1:",4096
OUTSTAT "COM1:"
RCVSTAT "COM1:",24
```
Save and load commands need to be prefixed with the com port, e.g. `SAVE "COM1:"`.

For sending, this is required in addition (adapt `PCONSOLE` to your needs):
```
SNDSTAT "COM1:",24
SETDEV "COM1:",PO
PCONSOLE "COM1:",80,2
```

### Sharp PC-1500
To switch `CLOAD` / `CSAVE` to the U1 USB port, enter this: `SETDEV U1,CI,CO`.

## Additional Information
[How to plug the Pocket Computer to your PC](HardwareNotes.md)  
[Extra Information](ExtraInformation.md)