# Manual tests before a release
Apart from Unit tests, these tests should be done before a release.  
Notes:
- `sc` is a shortcut for `java -jar SharpCommunicator.jar "$@"`
- All programs below can be found in the `testsuite` directory, along with the file you are currently reading.
## PC-1500
- Load a Basic ASCII program (`CLOAD` / `sc -i depreciation.bas`)
- Load the same program and include the utilities (`CLOAD` / `sc -i depreciation.bas -u`)
- Re-initialize serial (`Def-J`)
- Load a tokenized Basic program without a header  (`CLOAD` / `sc -i depreciation-tokenized-raw.bin`)
  - *Will fail at the moment with error 61*
  - Initialize the PC-1500 with `NEW` before continuing
- Load a Basic Binary program that includes a header (`CLOAD` / `sc -i depreciation-tokenized-ce158header.bin`)
- Save the program back to disk (`sc -o deptest.bin` / `CSAVE"depreciation"`); Compare with `depreciation-tokenized-ce158header.bin`
  - There will be differences, because the PC-1500 writes two Basic pointers into the CE-158 header that are ignored when loading. The CE-158 manual declares these fields as "don't care".
- Save the program back to disk as ASCII (`sc -o deptest.bas -of binary` / `CSAVEa`) Compare with `depreciation.bas` (it will differ in line endings, but not in content)
- Check compacting a program (`sc -i LineLengthTest.bas -o clip`)
  - Note the output on StdErr: `20: "`. It means that after loading the shortened program in ASCII mode, the last Quote will not fit.
  - Try loading the too-long program in ASCII: `CLOADa / sc -i LineLengthTest.bas -of ascii`. You will get an `ERROR 67`
  - Load the program binary: `CLOAD / sc -i LineLengthTest.bas`: This will be successful and use up every last byte of the PC-1500 line buffer.
- Check tokenizing a program without using the Pocket Computer (`sc -i LineLengthTest.bas -o linetest.bin -of binary`)
  - Having to use `-of binary` is a known bug, it should be the default.

## PC-1600
- Load a Basic ASCII program with utilities (`LOAD "COM1:"` / `sc -i depreciation.bas -u`)
- Re-init the COM port (`Def-J`)
- Save it back to the PC (`sc -o deptest.bas -of binary -d pc1600` / `Def-S`)
- Save it back to the PC, tokenized (`sc -o deptest.bin -d pc1600` / `SAVE"COM1:"`)
- Load the tokenized program back (`Def-L`, `sc -i deptest.bin -d pc1600`)
- Make a tokenized version from ASCII and load it (`sc -i depreciation.bas -o depbinary.bin -of binary -d pc1600 -u`; `Def-L` / `sc -i depbinary.bin -d pc1600`)

# List of test programs
- `depreciation.bas`: Ascii Version of the depreciation program (Sharp PC-1500 Applications Manual, Program No P5-D-12). The last line of this program has been changed, because its line number would be beyond the end of the utilities add-on.
- `depreciation-tokenized-raw.bin`: Tokenized version of the depreciation program (no header)
- `depreciation-tokenized-ce158header.bin`: Tokenized version of the depreciation program (with CE-158 header)
- `depreciation-tokenized-pc1600header.bin`: Tokenized version of the depreciation program (with PC-1600 header)
- `LineLengthTest.bas`: Line will fit if tokenized, but not without (217 chars long).

Note: To better compare the various binary formats, the `deprecation.bas` is always saved with a filename of "deprecation", and without the utility program.
