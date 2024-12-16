# Sharp PC-1500 and PC-1500A Memory Layout

## Overview

This chapter covers the Sharp PC1500/A memory layout, specifically with a modern bank switched 16KiB module installed, a
CE1638.
The CE1638 is an 8x16KiB memory expansion card for the Sharp PC-1500. See the links at the end for further information.

## Memory map

With the CE1638 installed, the memory map of a PC-1500 is as follows (all adresses in hex):

| Start | End  | Start | End | KiB | What                                     | Remark                                                                                                                                      |
|-------|------|-------|-----|-----|------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------|
| 0000  | 3FFF |       |     | 16  | CE1638                                   | Bank switched, i.e. one out of 8 banks is mapped; First few bytes are reserved, the rest can be partitioned into machine language and Basic |
| 4000  | 47FF |       |     | 2   | PC-1500/A Standard User Memory           |                                                                                                                                             |
| 4800  | 57FF |       |     | 4   | PC-1500A Additional Standard User Memory |                                                                                                                                             |
| 7600  | 7BFF |       |     | 1.5 | Standard System Memory                   | Display Buffer, Input Buffer, variables A-Z and A$-Z$, System variables                                                                     |
| 7C01  | 7FFF |       |     | 1   | PC-1500A Machine language area           | Cannot be used for Basic. The manual explicitly says 7C01, not 7C00, but it's unclear whether this is correct                               |

Additional remarks:

- Because of incomplete decoding, part of the system memory located 7600-77FF can also be addresses as 7000-71FF,
  7200-73FF or 7400-75FF. In some PC-1500 models
  the memory 7800-7BFF is also addressable as 7C00-7FFF.

### Bank switching

The CE1638 has a total of 128KiB memory. Only 16KiB are visible to the PC-1500/A at any time, and the rest is hidden.
This is accomplished by way
of bank switching: One 16KiB page is mapped to the address range shown above.

It is important to understand that only these first 16KiB are bank switched. The pre-installed user memory, and the
System RAM, remain
the same after a bank switch.

This can lead to unecpected side effects: As far as the PC-1500 is concerned, there is a single continuous RAM area from
0000 to 47FF (18KiB).
However, we know that the first 16KiB can be one of eight pages, while the top 2KiB are always the same.

Imagine this scenario:

- Bank 0 is active, and a very large Basic program is entered, covering the entire 18KiB of memory.
- Switch to Bank 1, and enter another very big program.
- Switch back to Bank 0: While the first 16KiB are restored fine, the top 2KiB will be wrong. The Basic program won't
  work anymore.

## System RAM

In the system RAM, various settings and pointers are stored. These are things like current cursor positions, last line
in Basic where an error occured,
current WAIT settings etc. The System RAM is part of the PC-1500/A, and thus not bank switched.

A simple bank switch will not touch the System Area, and as a result the System Area does not match up with the content
of the first 16KiB of RAM.
One would have to re-init the system with `NEW 0`, which defies the purpose of bank switching.

The other extreme - a fully transparent bank switch - requires a backup/restore of the entire System area. This would
use up 1.5 KiB RAM of every Bank.

The firmware for the CE1638 opts for a middle ground. It backups/restores the three pointers that are absolutely
necessary:

- 7865/66: Start of Basic (used for partitioning the user memory into machine language and Basic part)
- 7867/68: End of Basic (every line Basic code that is entered increases this pointer)
- 1869/6A: Start of Edit (MERGE, or next line entered on the keyboard goes to this address)

This means that for example the variables A-Z and A$-Z$ are shared amongst the banks:

- Bank 0: `A=10`
- Switch to Bank 1, then `A=4`
- Switch back to Bank 0: `A` will now be 4, not 10.

## Variables

The variables are part of the System RAM, and not bank switched.

### String variables

| Variable | Address | Variable | Address | Variable | Address | Variable | Address |
|----------|---------|----------|---------|----------|---------|----------|---------|
| A$       | 78C0-CF | H$       | 7680-8F | O$       | 76F0-FF | V$       | 77B0-BF |
| B$       | 78D0-DF | I$       | 7690-9F | P$       | 7750-5F | W$       | 77C0-CF |
| C$       | 78E0-EF | J$       | 76A0-AF | Q$       | 7760-6F | X$       | 77D0-DF |
| D$       | 78F0-FF | K$       | 76B0-BF | R$       | 7770-7F | Y$       | 77E0-EF |
| E$       | 7650-5F | L$       | 76C0-CF | S$       | 7780-8F | Z$       | 77F0-FF |
| F$       | 7660-6F | M$       | 76D0-DF | T$       | 7790-9F |          |         |
| G$       | 7670-7F | N$       | 76E0-EF | U$       | 77A0-AF |          |         |

### Fixed variables

| Variable | Address | Variable | Address | Variable | Address | Variable | Address |
|----------|---------|----------|---------|----------|---------|----------|---------|
| A        | 7900-07 | H        | 7938-3F | O        | 7970-77 | V        | 79A8-AF |
| B        | 7908-0F | I        | 4940-47 | P        | 7978-7F | W        | 79B0-B7 |
| C        | 7910-17 | J        | 7948-4F | Q        | 7980-87 | X        | 79B8-BF |
| D        | 7918-1F | K        | 7950-57 | R        | 7988-8F | Y        | 79C0-C7 |
| E        | 7920-27 | L        | 7958-5F | S        | 7990-97 | Z        | 79C8-CF |
| F        | 7928-2F | M        | 7960-67 | T        | 7998-9A |          |         |
| G        | 7930-37 | N        | 7968-6F | U        | 79A0-A7 |          |         |

### DIMed variables

The `DIM`ed variables, and the two letter variables are lost after a bank switch: They are allocated starting from the
top of RAM, which is shared.
They may or may not reach "down" into the bank switched part. The firmware could be updated to backup/restore the "Start
of Variables" pointer (7899/9A),
but only if this pointer is still high enough in the shared area. Given that a `RUN` command clears these variables
anyway, it is probably
not worth the hassle.

## Links

- CE1638
  Purchase: [https://www.soigeneris.com/sharp-pc-1500-memory-modules](https://www.soigeneris.com/sharp-pc-1500-memory-modules)
- CE1638 Support: Same page, select the tab "Downloads and Video"
