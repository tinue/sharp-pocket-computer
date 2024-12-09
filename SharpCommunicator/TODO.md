# List of ideas for possible improvements
Please note that this is not a promise to delivers anything at all! The list ist just a collection of ideas - my own and from other people - that could fit to thie project.

## Known defects
- Just like after a REM, anything after a single quote must be ignored (to be verified for the PC-1500)
- Binary file load only works if the .bin file already has a CE-158 header present.

## Future software work
- Restructure the README file. Start with the software part, and put the hardware stuff into its own file.
-Allow to specify the serial port. Important if the port can't be autodetected.
-Normalize the file when saving to PC (line breaks, EOF marker, leading / trailing blanks etc.)
  -also safe a binary loaded program (reverse-tokenize it, and remove the binary header)
- Define an ASCII format for "Reserve Keys" and support to load these (maybe even save)
    - This will also require tokenization support
    - It also needs a special reserve key header
    - Note: The reserve area can already be loaded and saved in binary format
- Support a "terminal" mode, where input from the keyboard is sent to the Pocket Computer,
  and output is shown on the screen.
    - As part of the terminal mode, support both `MODE 0` and `MODE 1` codepages correctly.

## Hardware Idea: Use a Raspberry Pi Pico instead of a USB / UART adapter
A Raspberry Pi Pico has a USB connector, serial pins, and 264k RAM. It should
therefore be possible to send a Pocket Computer program via USB to the Raspberry Pi Pico
at full speed and buffer it in its RAM, and then the Pico sends the data to the
Pocket Computer via its serial lines, using RTC/CTS flow control.

This should be cheaper than an USB / UART adapter, and it would work on a Mac as well.

Signal level adaption (3.3V vs. TTL) and signal inversion needs to be solved.