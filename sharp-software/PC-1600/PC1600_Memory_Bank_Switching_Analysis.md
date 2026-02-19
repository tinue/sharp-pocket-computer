# Sharp PC-1600 Memory Extension and Bank Switching -- Complete Analysis

**Sources:**
- `Service_Manual.pdf` -- hardware architecture, schematics, gate array, slot pinouts
- `SHARP-PC1600-Systemhandbuch.pdf` -- firmware, OS calls, module headers, software

---

## Part 1: System Architecture Overview

The PC-1600 is a dual-CPU pocket computer with a sophisticated bank-switched memory system that expands a 64KB directly-addressable Z-80 address space to 320KB.

### Main Chips (FPC PWB unit)

| Ref | Part | Function |
|-----|------|----------|
| IC1 | SC7852 | Main CPU 2 (Z-80 compatible, CMOS, 3.58MHz) |
| IC2 | LH5803 | Main CPU 1 (8-bit, CMOS, 2.6MHz / 1.3MHz internal) |
| IC3 | LU57813P | Sub CPU (4-bit, CMOS, 307.2KHz) -- power mgmt, RTC |
| IC4 | LR38041 | Gate Array -- central memory management and bus routing |

### Memory PWB Chips

| Ref | Part | Function |
|-----|------|----------|
| IC1 | 64K SRAM | Internal RAM 1 (8KB) |
| IC2 | 64K SRAM | Internal RAM 2 (8KB) |
| IC3 | 27C256FA51 | System ROM 1 (32KB) |
| IC4 | 27C256FPA5 | System ROM 2 (32KB) |
| IC5 | 27C256FPA6 | System ROM 3 (32KB) |

- **Total internal ROM:** 96KB (80KB for Z-80 BASIC interpreter + 16KB for LH-5803)
- **Total internal RAM:** 16KB (two 8KB SRAMs), expandable to 80KB with modules

---

## Part 2: Memory Map and Bank Switching Mechanism

The Z-80 has 16 address lines = 64KB address space, divided into four 16KB pages. Bank switching expands this to 320KB across 8 banks (Bank 0--7).

### Port 31H (IOW MAP / IOR MAP) -- The Primary Bank Select Register

Writing to Z-80 I/O port 31H selects which bank appears in each page:

| Bit | Name | Function |
|-----|------|----------|
| b0 | PVOUT | Bank for 0000-3FFF and C000-FFFF (0=Bank 0, 1=Bank 1) |
| b1 | PU | Bank select for 4000-7FFF and 8000-BFFF |
| b2 | PT | Bank select for 4000-7FFF and 8000-BFFF |
| b3 | b3 | Bank select for 4000-7FFF and 8000-BFFF |
| b4 | b4 | Bank select for 8000-BFFF |
| b5 | b5 | Bank select for 8000-BFFF |
| b6 | b6 | Controls LHS1/LHS2/LHS3 memory select remapping |
| b7 | b7 | Bank select for C000-FFFF |

**Example** (from Systemhandbuch):

```asm
3E 2E    LD A,2EH       ; = 0010 1110 binary
D3 31    OUT (31H),A    ; b4-b6=010: 8000-BFFF -> Bank 2
                        ; b1-b3=111: 4000-7FFF -> Bank 7
```

### Bank Selection Truth Tables

**Address range 0000-3FFF (Page 0):**

| b0 | Selected Bank |
|----|---------------|
| 0 | Bank 0 (PVOUT=0) |
| 1 | Bank 1 (PVOUT=1) |

**Address range 4000-7FFF (Page 1):**

| Bank | b3 | b2 (PT) | b1 (PU) | PVOUT |
|------|----|---------|---------|-------|
| 0 | 0 | 0 | 0 | 0 |
| 1 | 0 | 0 | 1 | 1 |
| 2 | 0 | 1 | 0 | 0 |
| 3 | 0 | 1 | 1 | 1 |
| C | 1 | 0 | 0 | 0 |
| 5 | 1 | 0 | 1 | 1 |
| 6 | 1 | 1 | 0 | 1 |
| 7 | 1 | 1 | 1 | 1 |

**Address range 8000-BFFF (Page 2):**

| Bank | b5 | b4 | b3 | PT | PU | PVOUT |
|------|----|----|----|----|----|-------|
| 0 | 0 | 0 | 0 | 0 | 0 | 0 |
| 1 | 0 | 0 | 1 | 0 | 0 | 1 |
| 2 | 0 | 1 | 0 | 0 | 1 | 0 |
| 3 | 0 | 1 | 1 | 0 | 1 | 1 |
| C | 1 | 0 | 0 | 1 | 0 | 0 |
| 5 | 1 | 0 | 1 | 1 | 0 | 1 |
| 6 | 1 | 1 | 0 | 1 | 1 | 0 |
| 7 | 1 | 1 | 1 | 1 | 1 | 0 |

**Address range C000-FFFF (Page 3):**

| b7 | Selected Bank |
|----|---------------|
| 0 | Bank 0 |
| 1 | Bank 1 |

### Memory Map by Bank

| Address Range | Bank 0 | Bank 1 | Bank 2 | Bank 3 |
|---------------|--------|--------|--------|--------|
| 0000-3FFF | System ROM (CS001) **NEVER SWITCHED OUT** | (Slot 2) | -- | -- |
| 4000-7FFF | System ROM (BASIC, Editor) | Slot 2 ROM | Slot 1 ROM | System ROM (CS24, sub-banked 2x8K) |
| 8000-BFFF | Slot 1a (RAM2) | Slot 1b (RAM2) | Slot 2a (RAM1) | Slot 2b (RAM1) |
| C000-FFFF | Internal 16KB RAM (RAM3) | (Bank 1) | -- | -- |

Additional banks in 4000-7FFF:

| Bank | Contents |
|------|----------|
| 3b | Hidden BASIC ROM (selected via Port 3DH, bit b2) |
| 4 | CE-1600P Plotter/Centronics ROM |
| 5 | CE-1600P Floppy (5000-5FFF) / Cassette (6000-7FFF) |

Additional banks in 8000-BFFF:

| Bank | Contents |
|------|----------|
| 6 | Display routines, timer/serial control, char/token tables (CS123) |

### Auxiliary Bank Control Ports

**Port 3DH (IOW C/D):**
- Bit b2 normally set. Clearing b2 selects hidden BASIC ROM on Bank 3 -> Bank 3b at 4000-7FFF
- Cannot be read via IN instruction; readable from system address F07DH
- Bits D0-D2 written here are latched by the gate array into outputs A14A-A16A, providing extra address lines for sub-banking the CS24 ROM space (Bank 3, 4000-7FFF) into two 8KB halves

**Port 28H (Slot 2 sub-banking):**
- Further subdivides Slot 2 (8000-BFFF, Banks 2+3) for modules larger than 32KB
- This is the key port for using large memory modules in Slot 2

---

## Part 3: Gate Array LR38041 -- The Memory Management Hub

The LR38041 gate array (IC4 on FPC PWB) handles all memory address decoding, bank switching signal generation, bus arbitration between the two CPUs, and chip select generation.

### Key Pin Assignments

| Pin | Signal | Dir | Function |
|-----|--------|-----|----------|
| 1 | SLCB | In | Sub CPU P1, system operation start |
| 8 | RD | Out(L) | Combined read signal (wired OR of ME0, ME1, OD, CKOS, BRO with Z-80 RD) |
| 28-30 | D0-D2 | I/O | Z-80 data bus bits 0-2 |
| 31-35 | D3-D7 | Out | Z-80 data bus bits 3-7 |
| 36 | C/D | In(H) | Goes high when Z-80 writes to port 3DH |
| 38 | CL | In(L) | System reset |
| 39 | A13 | In | CPU address A13 |
| 44 | A13A | Out | **Inverted A13** -- selects upper/lower 8KB RAM |
| 45 | A14A | Out | Latched D0 from C/D write -- extended address |
| 46 | A15A | Out | Latched D1 from C/D write -- extended address |
| 47 | A16A | Out | Latched D2 from C/D write -- **sub-banks CS24** |
| 48-50 | LHS1-3 | In(L) | Memory select signals from SC7852 |
| 51-53 | KA0-K2 | In(L) | I/O select signals from SC7852 |
| 54-56 | S1-S3 | Out(L) | Buffered slot select signals to expansion slots |
| 59-61 | K0-K2 | Out(L) | Buffered I/O select signals to expansion slots |

### Key Gate Array Functions

1. **Bus Arbitration:** Manages shared bus between Z-80 (SC7852) and LH-5803. Signal ELH: High = Z-80 operating, Low = LH-5803 operating.

2. **Address Line Translation:**
   - A13A: Inverted A13, used as 8KB half-select for RAM chips
   - A14A-A16A: Latched from data bus via C/D register write
   - A16A separates CS24 16KB space into two 8KB banks

3. **Slot Signal Buffering:** LHS1-3 and KA0-K2 from SC7852 are buffered to S1-S3 and K0-K2 for expansion slots. Required because SC7852 power is off during system-off while gate array maintains levels.

4. **Reset Behavior:** When CL goes low, A16A forced high, A15A forced low, A14A forced high -- establishing known initial bank config.

---

## Part 4: Chip Select Signals -- Address Decoding

Generated internally by the SC7852 custom CPU, based on bank selection register and current address:

| Signal | SC7852 Pin | Active | Selects |
|--------|------------|--------|---------|
| CS001 | 43 | Low | ROM 0000-7FFF Bank 0 (main system ROM) |
| CS123 | 44 | Low | ROM 8000-BFFF Bank 6, LH-5803 ROM C000-FFFF |
| CS24 | 45 | Low | ROM 4000-7FFF Bank 3/4 (sub-banked by A16A) |
| RAM3 | 49 | High | Internal 16KB RAM C000-FFFF Bank 0 |
| RAM2 | 50 | Low | Slot 1 (S1) RAM 8000-BFFF Bank 0/1 |
| RAM1 | 51 | Low | Slot 2 (S2) RAM 8000-BFFF Bank 2/3 |
| LHS1 | 46 | Low | Sub-select within 8000-BFFF (remappable) |
| LHS2 | 47 | Low | Sub-select within 8000-BFFF (remappable) |
| LHS3 | 48 | Low | Sub-select within 8000-BFFF (remappable) |

**INH signal** (on slot connectors, active low): When pulled low, inhibits internal ROM (CS001/CS123), allowing external slot modules to override internal memory.

**LHS1/LHS2/LHS3 remapping via bit b6 of I/O 31H:**

| Signal | b6=0 | b6=1 |
|--------|------|------|
| LHS1 | A800-AFFF (Bk 0) | B000-B7FF (Bk 0) |
| LHS2 | B000-B7FF (Bk 0) | A800-FAFF (Bk 0) |
| LHS3 | B800-BFFF (Bk 0) | A000-A7FF (Bk 0) |

---

## Part 5: Expansion Slot Connectors

### CN-7 (Slot 1 / S1) -- 40 pins

| Pin | Signal | Pin | Signal | Pin | Signal | Pin | Signal |
|-----|--------|-----|--------|-----|--------|-----|--------|
| 1 | VCC | 11 | D3 | 21 | NC | 31 | A6 |
| 2 | PVIN | 12 | D2 | 22 | A15 | 32 | A5 |
| 3 | PU | 13 | D1 | 23 | A14 | 33 | A4 |
| 4 | **RAM2** | 14 | D0 | 24 | A13 | 34 | A3 |
| 5 | PVOUT | 15 | INH | 25 | A12 | 35 | A2 |
| 6 | MREQ | 16 | S1 | 26 | A11 | 36 | A1 |
| 7 | D7 | 17 | S2 | 27 | A10 | 37 | A0 |
| 8 | D6 | 18 | S3 | 28 | A9 | 38 | RD |
| 9 | D5 | 19 | PT | 29 | A8 | 39 | WR |
| 10 | D4 | 20 | VGG | 30 | A7 | 40 | GND |

### CN-8 (Slot 2 / S2) -- 40 pins

Same as CN-7 except:
- Pin 4 = **RAM1** (instead of RAM2)
- Pin 16 = **K0** (I/O select, instead of S1)
- Pin 17 = **K1** (I/O select, instead of S2)
- Pin 18 = **K2** (I/O select, instead of S3)

### Key Signals on Expansion Slots

| Pin | Signal | Function |
|-----|--------|----------|
| 5 | PVOUT | Current PVOUT bank state (0 or 1) |
| 3 | PU | Bank select from I/O 31H |
| 19 | PT | Bank select from I/O 31H |
| 2 | PVIN | LH-5803 PV signal input (direct from LH-5803 pin 60) |
| 4 | RAM2/RAM1 | Chip select for the respective slot |
| 15 | INH | When pulled low, inhibits internal ROM |
| 16-18 | S1/S2/S3 or K0/K1/K2 | Sub-select / I/O-select within slot range |

---

## Part 6: Firmware -- Transparent Bank Switching

### How the OS Makes Bank Switching Transparent

The critical design principle: **Bank 0 (0000-3FFF) is NEVER switched out.** It contains all gateway routines. Any code in any bank can always call these routines to switch banks. The `BANKCALL` routine saves the current bank state and restores it on return, making cross-bank calls transparent.

### System Calls (all in Bank 0, always accessible)

| Address | Name | Description |
|---------|------|-------------|
| 0190H | **BANKSET** | Set bank. A=bank number, B=page number. |
| 0193H | **BANKREAD** | Read current bank of page in B. Returns in A. |
| 018DH | **MEMORYCHK** | Test if memory exists at bank. D=bank, E=high byte of address (40-B8). Returns: Carry+A=00 = no memory; NC+A=01 = RAM; NC+A=03 = ROM. |
| 019CH | **BANKJUMP** | Jump to different bank. A=bank, HL=address. WARNING: return address destroyed. |
| 019FH | **BANKCALL** | Call routine in different bank. A=bank, HL=address. Current bank saved and restored on return. |
| 0196H | **SLOT1MAP** | Remap Slot 1 addressing. A=00: Normal. A=01: Slot 1b -> 4000-7FFF of Bank I. |
| 0199H | **SLOT2MAP** | Remap Slot 2 addressing. A=00: Normal. A=01: Slot IIa -> 0000-3FFF of Bank 1. A=02: Slot IIb -> 0000-3FFF of Bank 1. |
| 00E8H | **SLOTST** | Test slot header at high byte in D. Returns: A=00 error; F0="P" (program); F2="S" (system); FF="M" (RAM-Disk). |

### RST Shortcuts (single-byte opcodes, fastest cross-bank calls)

**RST 18H + 2-byte immediate (nn): BANKJP (Bank Jump)**

Address encoding implicitly selects bank:

| Address Range | Target Bank |
|---------------|-------------|
| 0000-3FFF | Bank 0 |
| 4000-7FFF | Bank 3 |
| 8000-BFFF | Bank 6 |
| C000-FFFF | Bank 0, mapped to 4000-7FFF |

**RST 20H + 3-byte immediate (b, nn): BANKCAL (Bank Call)**

- b = Bank number
- nn = Target address
- Example: `E7 05 08 40` = `CALL Bank 5, address 4008H`

### ROM Module Detection and Headers

At reset, the system scans for modules. Bitmaps at F0AEH and F0AFH encode which ROM modules are present:
- **F0AEH:** Modules with start address 4000 (bit-encoded)
- **F0AFH:** Modules with start address 6000 (bit-encoded)
- Bit mapping: b6=Bank 1, b5=Bank 2, ... b0=Bank 7

Each module begins with an **8-byte header** at 8000H, A000H, or B000H:

| Offset | Field |
|--------|-------|
| +0 | ID Code: 43H |
| +1 | ID Code: 16H |
| +2 | Reset jump / checksum |
| +3 | Start address / boot indicator |
| +4 | Module length / boot address |
| +5 | BASIC address |
| +6 | End address |
| +7 | Type byte: 80H/FFH = "M" (RAM-Disk), F0H = "P" (Program), F2H = "S" (System), 01H = Write-protected |
| +8 | RESERVE area (RAM-Disk formatting info) |

### Module Jump Table (at address 4000H for Page 1 modules)

```
4000 : 43       ID-Code
4001 : 16       ID-Code
4002 : JP XXXX  Reset jump
4005 : JP XXXX  Interrupt jump
4008 : JP XXXX  Jump to important routines
400B : (not used)
400E : JP XXXX  Jump for AUTORUN.BAS search
4011 : XX XX    Pointer to Device Name (e.g., "COM1:")
4013 : XX XX    Pointer to Token Table (+0036)
4015 : JP XXXX  Jump for File processing
```

### Working Memory Allocation for ROM Modules

ROM modules can allocate working memory via `CALL 02DFH`. Parameters: DE = size in bytes, C = module number (Creg).

| Creg | Purpose |
|------|---------|
| 00 | Standard system memory |
| 01-07 | ROM Bank 1-7, Start 4000 (EXROM1-EXROM7) |
| 08-0E | ROM Bank 1-7, Start 6000 (EXROM8-EXROME) |
| 0F | COM buffer |
| 10 | File buffer (DE = n * 313 bytes) |

### Slot Management System Addresses

| Address Range | Purpose |
|---------------|---------|
| F015-F01EH | Slot S1 descriptor |
| F01F-F028H | Slot S2 descriptor |
| F029-F02CH | Slot S0 descriptor |
| F050-F053H | S1: EPROM or write-protected |
| F054-F055H | S1: RAM only |
| F056-F059H | S2: EPROM or write-protected |
| F05A-F05BH | S2: RAM only |
| F07DH | Mirror of Port 3D state |
| F0AE-F0AFH | ROM module bitmaps |
| F0DC-F0DDH | Boot bank and address |
| F123-F126H | Coded module config (for reset) |

### Boot Sequence

1. Reset cause determined (stored at F1ABH). Bits: b0=ALL RESET, b1=Internal RESET, b2=External RESET, b4=POWER ON, b5=External POWER ON, b6=WAKE$(0), b7=WAKE$(I)=CI
2. Peripheral reset and device identification via ROM-Bit scan
3. `CALL 4002` issued to all devices (A=07: device can deregister)
4. Memory module detection (changed config sets FA23H bit b7 = "NEW0?")
5. Power-on test (can program resume from `POWER AOFF`?)
6. Boot search priority:
   1. Peripheral device (Floppy) -- `CALL 4002, A=05`
   2. Found peripheral -- `CALL 4002, A=06`
   3. System software (EPROM)
   4. System software (RAM)
   5. Jump to bank (F0DCH), address (F0DDH)

---

## Part 7: CE-1600M Memory Extension Module

### Specifications

| Property | Value |
|----------|-------|
| Model | CE-1600M |
| Capacity | 32KB (4 x 8KB SRAM) |
| Battery | 3V DC lithium (CR2032 x 1), ~5 years in use, ~24 months removed |
| Write protect | Slide switch (dot side = protected) |
| Size | 40.9 x 42.8 x 8.5 mm, 15 grams |

### Internal Hardware

- 4 x 8KB SRAM chips (RAM1-RAM4, wire-bonded, not replaceable)
- 1 x TC74HC139F dual 2-to-4 decoder/demultiplexer
- 2 x 560K ohm pull-up resistors
- DAN202 diode array + 1SS98 diode (battery protection)
- CR2032 lithium battery
- DIP switch (configuration)

### Circuit Description

The TC74HC139F dual decoder selects one of four 8KB SRAMs based on address lines from the slot connector. Its enable/select inputs come from the slot's bank signals, and its active-low outputs each enable one SRAM chip.

### Memory Maps

**When in Slot 2:**
- 8000-BFFF: Bank 2 and Bank 3 accessible
- User area: up to 44,612 bytes

**When in Slot 1:**
- 8000-BFFF: Bank 0 and Bank 1 accessible
- User area: up to 44,612 bytes

**With CE-159 in S1 and CE-1600M in S2:**
- User area: up to 52,794 bytes (across Banks 0, 2, and 3)

---

## Part 8: Dual-Processor Architecture (Z-80 + LH-5803)

The second processor (LH-5803) provides PC-1500 compatibility.

### LH-5803 Memory Map

| Address Range | Contents |
|---------------|----------|
| 0000-3FFF | Same as Z-80's 8000-BFFF (S1/S2 modules) |
| 4000-7FFF | RAM 16KB (same as Z-80's C000-FFFF) |
| 8000-BFFF | CE-150 (PVOUT=0) or CE-158 (PVOUT=1) |
| C000-FFFF | ROM 16KB (CS123) |

### Key Differences from Z-80

- Addresses stored in reversed byte order (hi-lo vs Z-80's lo-hi)
- Bit b15 is inverted
- Both CPUs share the same bus -- cannot operate simultaneously
- One is always in HALT while the other runs

### Calling LH-5803 from Z-80

Set parameters at:
- **F002H:** 20 = no params, 30 = pass registers (F005-F00BH)
- **F00C/H:** Start address (LH-5803 notation)
- **F00EH:** Bank (00=RPV, 01=SPV)

Execute: `CALL 01C6H` (CALLH)

### LH-5803 Memory Enable Signals

| Signal | Pin | Function |
|--------|-----|----------|
| ME0 | 29 | Accesses 64KB area for program counter P and stack S |
| ME1 | 30 | Accesses second 64KB area for CPU data commands |

**PV signal** (pin 60): Internal flip-flop output, sent directly to PVOUT of SC7852. When Z-80 operates, PV floats and is pulled down internally.

---

## Part 9: Complete I/O Port Map for Memory Management

### Memory-Related Ports

| Port | Dir | Function |
|------|-----|----------|
| **31H** | R/W | **PRIMARY BANK SELECT REGISTER** (IOW MAP / IOR MAP). b0: Page 0/3 bank (PVOUT). b1-b3: Page 1 bank (4000-7FFF), 8 banks. b4-b6: Page 2 bank (8000-BFFF), 8 banks. b6: LHS1/2/3 remapping. b7: Page 3 bank (C000-FFFF). |
| **3DH** | W | **HIDDEN ROM / EXTENDED ADDRESS** (IOW C/D). b2: normally set; clearing selects Bank 3b. D0-D2: latched to gate array A14A-A16A. |
| **28H** | W | **SLOT 2 SUB-BANKING.** Subdivides 8000-BFFF Banks 2+3 for modules >32KB. |
| 30H | R/W | Module control (IOW MOD / IOR MOD) |
| 32H | R/W | Interrupt cause/priority |
| 35H | W | Interrupt mask register |
| 39H | W | Interrupt vector low byte (Z-80 IM2 mode) |

### Z-80 I/O Space Overview

| Address Range | Assignment |
|---------------|------------|
| 00-0FH | Prohibited |
| 10-1FH | LH-5810 compatible port (in SC7852) |
| 20-27H | TC8576F UART |
| 28-2FH | S2 (Slot 2) I/O |
| 30-3FH | SC7852 internal control registers |
| 40-4FH | System reserve |
| 50-57H | HD61202 LCD driver (IC2) |
| 58-5BH | HD61202 LCD driver (IC3) |
| 60-6FH | S2 (Slot 2) I/O |
| 78-7FH | CE-1600F |
| 80-83H | CE-1600P |

---

## Part 10: Using a PC-1500 8x16K Module in the PC-1600

This section describes how to adapt a PC-1500 memory extension module (8 banks of 16KB = 128KB) for use in the PC-1600 with transparent bank switching.

### Challenge 1: Physical Connector

The PC-1500 uses a different expansion connector than the PC-1600's 40-pin slot connectors. An adapter board is needed.

**PC-1600 Slot 2 is preferred** because:
- Port 28H provides sub-banking for modules >32KB
- RAM1 chip select is on pin 4
- K0/K1/K2 I/O select signals available on pins 16-18
- Full address bus (A0-A15), data bus (D0-D7), RD, WR available

### Challenge 2: Bank Select Signal Mapping

The PC-1600 natively supports 2 banks per slot (e.g., Bank 2 + Bank 3 for Slot 2). Your module has 8 banks of 16KB each = 128KB.

Standard bank signals on the slot connector:
- **PVOUT** (pin 5): 1 bit
- **PU** (pin 3): 1 bit
- **PT** (pin 19): 1 bit
- **RAM1** (pin 4): chip select for Slot 2

For 8 banks you need 3 address bits. The available bank signals (PVOUT, PU, PT) provide exactly 3 bits, but the firmware only uses 2 of these for Slot 2 banking (Banks 2 and 3). For full 8-bank access, you need **Port 28H for sub-banking**.

### Approach A: Direct Port 28H Sub-Banking (Recommended)

Port 28H is specifically designed for Slot 2 modules larger than 32KB. The module's bank select lines should be wired to the sub-bank signals provided through Port 28H.

**Implementation:**

1. Build an adapter board that maps the PC-1500 module's edge connector to the PC-1600 Slot 2 (CN-8) 40-pin connector.

2. Wire the 3 bank select lines from your 8x16K module to signals derived from Port 28H outputs. Port 28H bits are accessible on the Slot 2 connector through the K0/K1/K2 I/O select lines (pins 16-18).

3. Use RAM1 (pin 4) as the master chip enable, gated with the decoded bank select.

4. Wire address lines A0-A13 to select within each 16KB bank.

5. Connect RD (pin 38), WR (pin 39), and data bus D0-D7 (pins 7-14).

### Approach B: Firmware Driver with BANKCALL

If direct Port 28H wiring is insufficient, write a machine-language driver that:

1. Resides in Bank 0 (0000-3FFF) or is called via `BANKCALL` (019FH).

2. Manages the 8 banks by directly writing to Port 28H:

```asm
LD A,<bank_number>    ; 0-7
OUT (28H),A           ; Select sub-bank in Slot 2
```

3. Performs the actual data transfer while the correct bank is selected.

4. Provides a RAM-Disk interface by placing proper module headers at the start of each bank:
   - Offset +0: 43H (ID code)
   - Offset +1: 16H (ID code)
   - Offset +7: FFH ("M" = RAM-Disk module)

5. Hooks into the boot sequence so the OS detects the module.

### Software Integration for Transparent Access

To make the 128KB module work transparently with BASIC commands:

1. Format the module as RAM-Disk using the `INIT` command or by manually writing the correct headers.

2. The OS will detect the module at boot via `SLOTST` (00E8H) and the module header scan.

3. Once detected as type "M" (RAM-Disk), the OS handles all bank switching transparently when you use:

```basic
SAVE "S2:filename"
LOAD "S2:filename"
FILES "S2:"
KILL "S2:filename"
```

4. For the 8 sub-banks via Port 28H, you may need a custom driver that extends the standard 2-bank (32KB) Slot 2 handling to cover all 8 banks (128KB). This driver would:
   - Hook the file system calls for S2:
   - Calculate which 16KB sub-bank contains the target data
   - Write the appropriate value to Port 28H
   - Perform the access in the 8000-BFFF window
   - Restore the previous Port 28H state

### Key Considerations

- Bank 0 (0000-3FFF) must never be disturbed -- all gateway routines live here
- The module must respond correctly to `MEMORYCHK` (018DH) probes during boot -- each 16KB bank should return A=01 (RAM)
- Battery backup must be maintained for SRAM contents
- The write-protect signal should be wired through if the PC-1500 module supports it

**Total accessible RAM with this setup:**

| Component | Size |
|-----------|------|
| Internal (C000-FFFF) | 16KB |
| Slot 1 (if CE-1600M or CE-159) | up to 32KB |
| Slot 2 (your 8x16K module) | 128KB |
| **Total** | **up to 176KB** |

---

## Part 11: Signal Summary -- All Memory-Related Signals

### Bank Selection Signals (from SC7852, controlled by Port 31H)

| Signal | Pin | Function |
|--------|-----|----------|
| PT | 5 | Memory bank signal |
| PU | 6 | Memory bank signal |
| PVOUT | 7 | Bank 0/1 selector |

### Gate Array Generated Signals

| Signal | Pin | Function |
|--------|-----|----------|
| A13A | 44 | Inverted A13, selects 8KB RAM half |
| A14A | 45 | Latched D0 from C/D write |
| A15A | 46 | Latched D1 from C/D write |
| A16A | 47 | Latched D2 from C/D write, sub-banks CS24 |

### Bus Control Signals

| Signal | Chip | Function |
|--------|------|----------|
| ELH | SC7852 | CPU ownership (H=Z-80, L=LH-5803) |
| SLCT | SC7852 | Master memory/IO enable from sub CPU |
| LHWAIT | SC7852 | Wait signal to LH-5803 |
| MREQ | SC7852 | Memory request |
| IORQ | SC7852 | I/O request |
| INH | Slot | Inhibit internal ROM |
| DME0 | SC7852 | LH-5803 memory select |

---

## Part 12: Internal RAM/ROM Wiring (Memory PWB)

### Connector CN-12 (Memory PWB to main board, 36 pins)

| Pin | Signal | Pin | Signal | Pin | Signal |
|-----|--------|-----|--------|-----|--------|
| 1 | VGG | 13 | D6 | 25 | A1 |
| 2 | WR | 14 | D5 | 26 | A2 |
| 3 | RAM3 | 15 | D4 | 27 | A3 |
| 4 | A8 | 16 | D3 | 28 | A4 |
| 5 | A9 | 17 | INH | 29 | A5 |
| 6 | LHA90 | 18 | VCC | 30 | A6 |
| 7 | A11 | 19 | GND | 31 | A7 |
| 8 | A13A | 20 | CS001 | 32 | A12 |
| 9 | RD | 21 | D2 | 33 | CS123 |
| 10 | A10 | 22 | D1 | 34 | A14 |
| 11 | A13 | 23 | D0 | 35 | A15 |
| 12 | D7 | 24 | A0 | 36 | CS24 |

### RAM Wiring

**RAM1 (IC1):** A0-A12 to address bus, D0-D7 to data bus
- CS1 = A13A (inverted A13 from gate array)
- CS2 = LHA90, OE = RD, WR = WR

**RAM2 (IC2):** Identical except CS1 = A13 (direct, not inverted)
- RAM1 covers one 8KB half, RAM2 covers the other

Both have RAM3 on chip enable -> only active when C000-FFFF Bank 0 is selected.

### ROM Wiring

- **ROM1 (IC3):** CE = CS001 (Bank 0, 0000-7FFF), A15 = OE
- **ROM2 (IC4):** CE = CS001, same structure
- **ROM3 (IC5):** CE via resistor network, A15 = OE, A16A for extra banking
