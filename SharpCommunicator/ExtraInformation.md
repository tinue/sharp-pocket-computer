# Some extra information of PocketPC Devices
## PC/1600 commands relevant to serial communication
### INIT
Sets the receive buffer size. Default is 40 bytes after power on.

`INIT "COM1:" <buffer>`
- buffer: Size of the buffer in bytes.

Example: `INIT "COM1:",4096`

### SETCOM
Sets the protocol settings for the serial port.

`SETCOM "COM1:",[BR],[WL],[PR],[ST],[XO],[SI]`
- BR: Baud rate (50 - 38400)
- WL: Word length (5-8 bits)
- PR: Parity (E, O, N)
- ST: Stop Bits (1 or 2)
- XO: XON / XOFF (X = Yes, N = no)
- SI: Shift in/out protocol (S = yes, N = no)

Example: `SETCOM "COM1:", 9600,8,N,1,N,N`

### OUTSTAT
Force-sets the state of the control signals for the serial port.

`OUTSTAT "COM1:" [,setting]`
- setting means:

|   | RTS  | DTR  |
|---|------|------|
| 0 | high | high |
| 1 | high | low  |
| 2 | low  | high |
| 3 | low  | low  |

Example: `OUTSTAT "COM1:"`, i.e. without setting, to make RTS/DTR work
dynamically.

### INSTAT
Returns the current settings of the control signals for the serial port.

`INSTAT "COM1:"`

The state is returned as an integer, with each bit representing a signal.
Values are inversed, i.e. `0` means `high`, and `1` means `low`.

| Bit | 7      | 6      | 5  | 4   | 3  | 2   | 1   | 0   |
|-----|--------|--------|----|-----|----|-----|-----|-----|
|     | unused | unused | CI | DSR | CD | CTS | RTS | DTR |

Example: `PRINT INSTAT "COM1:"`: Prints the current state

### SNDSTAT
Sets the send handshake protocol, and the timeout for the serial port.

`SNDSTAT "COM1:",<protocol>[,<timeout>]`
- Timeout is between 0 and 255, in units of 0.5 seconds. 0 disables the timeout.
- Protocol is as follows:

| Bit | 7   | 6   | 5   | 4   | 3  | 2   | 1   | 0   |
|-----|-----|-----|-----|-----|----|-----|-----|-----|
|     | n/a | n/a | n/a | DSR | CD | CTS | n/a | n/a |

- Bit = `1` means "ignore"
- Bit = `0` means "set high"

Examples:

- `SNDSTAT "COM1:",24`: Enables CTS (what we need for this project)
- `SNDSTAT "COM1:",28`: Disable all flow control.

### RCVSTAT
Sets the receive handshake protocol and the timeout for the serial port

`RCVSTAT "COM1:",<protocol>[,<timeout>]`
- Timeout is between 0 and 255, in units of 0.5 seconds. 0 disables the timeout.
- Protocol is as follows:

| Bit | 7   | 6   | 5   | 4   | 3  | 2   | 1   | 0   |
|-----|-----|-----|-----|-----|----|-----|-----|-----|
|     | n/a | n/a | n/a | DSR | CD | CTS | n/a | n/a |

- Bit = `1` means "ignore"
- Bit = `0` means "must be high"

Examples:

- `RCVSTAT "COM1:",24`: Enables CTS handshake (what we need for this project)
- `SNDRCVSTAT "COM1:",28`: Disable all flow control.

Note: The manual mentions _Note that bits 1,2,6,7, and 8 are not used; set them to 0._  
However, most of the examples
found online, and even in the manual itself, set one of more of these bits to 1. As a result, one can find
different numbers for "enable CTS", such as 59. This number also works, but 24 would be correct according to
the manual.

### SETDEV
Specifies the serial port as input and output for some Basic commands.
`SETDEV` without parameters releases the port and resets input / optput to
keyboard / printer.

`SETDEV "COM1:"[,KI][,PO]`
- ,KI: Sets COM1: as device for the command `INPUT`.
- ,PO: Sets COM1: as device for the commands `LPRINT`, `LLIST` and `LFILES`.

Example: `SETDEV "COM1:",KI,PO`: Redirects both input and output to COM1

### PCONSOLE
Set the line length and end of line code for communication through the serial port.

`PCONSOLE "COM1:",[line length],[EOL code]`
- Line length: 16-255 (0 means no limit)
- EL Code: 0 = CR, 1 = LF, 2 = CR/LF

Example: `PCONSOLE "COM1:",80,2`: Set the line length to 80 chars, and use CR/LF
(i.e. Windows style) for the line ending.

### SAVE
Save a program via serial port.

`SAVE "<COM1:>"[,A]`
- A: ASCII format (instead of compressed binary format)

### LOAD
Loads a program from serial port.

`LOAD "<COM1:>"[,R]`
- R: Auto-starts the program after loading.