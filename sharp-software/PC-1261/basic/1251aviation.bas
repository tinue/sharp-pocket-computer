Few weeks ago I received a Tandy pc-3 (aka pc-1250/51), and it had some software on it. It results to be one of the most amazing software on a pocket computer i've ever seen.
After reviewing and asking a fellow member who works in aviation area, it was confirmed that these programs have aviation purposes. But could not figure out which airplane it was used for. If any member has some more information, your welcome to post it here.
Anyhow, knowing that I think I could breakdown all the code. The fact, that pocket computers had been used for aviation purposes is very cool. 😎

Included calculations:

- Density Altitude Calculation
- Wind Correction Angle and Magnetic Course Calculation
- Fuel Consumption Calculation
- Great Circle Distance Calculation
- True Airspeed Calculation
- Heading and Wind Speed/Direction Calculation
- True Altitude Calculation
- Navigation and Direction Calculation
- Center of Gravity Calculation


Here the explanation of the main functions of these programs:
Density Altitude Calculation (Line 10):
This function calculates the density altitude based on pressure altitude (PRES ALT) and temperature (TEMP).
It uses a formula involving various constants and mathematical operations.
It prints out the calculated density altitude.
Wind Correction Angle and Magnetic Course Calculation (Lines 15-33):
This function computes wind correction angle (WCA), true heading (TH), groundspeed (GS), and magnetic heading (MH) based on true airspeed (TAS), wind direction (WD), wind speed (WS), and magnetic course (MC).
It involves trigonometric calculations and formulas related to navigation.
It prints out the calculated values of WCA, TH, GS, and MH.
Fuel Consumption Calculation (Line 21):
This function calculates fuel consumption based on distance (DIS) and groundspeed (GS).
It involves a simple formula based on distance and groundspeed.
It prints out the calculated fuel consumption.
Great Circle Distance Calculation (Lines 23-35):
This function calculates the great circle distance between two given points specified by latitude and longitude.
It involves several trigonometric calculations using the haversine formula.
It prints out the calculated great circle distance.
True Airspeed Calculation (Line 37):
This function calculates true airspeed (TAS) based on indicated airspeed (CAS), pressure altitude (PRES ALT), and temperature (TEMP).
It involves a formula that incorporates temperature and pressure altitude corrections.
It prints out the calculated true airspeed.
Heading and Wind Speed/Direction Calculation (Lines 39-45):
This function calculates the difference between two given directions (DIR), wind speed (WS), and wind direction (WD).
It involves trigonometric calculations to determine left or right correction and type of wind (true or headwind).
It prints out the calculated wind corrections.
True Altitude Calculation (Line 47):
This function calculates true altitude (TA) based on indicated altitude (CA), pressure altitude (PRES ALT), and temperature (TEMP).
It involves a formula incorporating temperature and pressure altitude corrections.
It prints out the calculated true altitude.
Navigation and Direction Calculation (Lines 49-77):
This function involves various navigation calculations, including determining relative directions and distances between waypoints.
It uses input from the user regarding waypoint coordinates and distances.
It prints out calculated directions and distances.
Center of Gravity Calculation (Lines 100-374):
This function calculates the center of gravity (CG) based on the positions and weights of various components such as passengers, fuel, and cargo.
It involves extensive user input regarding weights and positions of different components.
It prints out the calculated center of gravity and provides warnings if the center of gravity exceeds certain limits.
These funtions seem to be for a rather small airplane. Still open to know to which type of airplanes it belongs.
Here the code:
1 USING "#####":R=.5: GOTO 8
2 IF ABS COS I>ABS COS 20 THEN PRINT "BAD VOR": END
3 USING "####":R=.05: GOTO 8
4 INPUT "PRES ALT?"; P,"TEMP?";T: RETURN
8 DEGREE : WAIT : RETURN
9 "D": GOSUB 1: GOSUB 4
10 A=145366-549960/(273+T)^(1/4.256)*(1-6.8809[E]-6*P)^1.235+R: PRINT "DENS ALT:";A: END
11 "F": GOSUB 3: INPUT "WD?";D,"WS?";E,"TC?*";K: GOTO 15
13 PAUSE K
15 INPUT "TAS?";I,"DEC?(-E,+W)?";J:H=D-K:A=ASN ((E/I)*SIN H):B=K+A:F=B+J
16 C=I*COS A-E*COS H:S=K+J
17 PRINT "WCA:";A+R;" TH:";B+R: PRINT "MC:";S+R;" MH:";F+R: INPUT "DIS?*";L
18 PRINT "GS:";C+R;" DIS:";L: GOTO 20
19 PAUSE L
20 I=L/C: PRINT USING "###.##";" T:";DMS I: INPUT "BURN?";A
21 PRINT " FUEL:";A*I+1: END
23 "L": INPUT "LAT DEP?";F,"LON DEP?";G:F=DEG F:G=DEG G
25 INPUT "LAT DES?";H,"LON DES?";J:H=DEG H:J=DEG J:A=90-H:B=90-F:L=G-J: IF L=0 THEN LET L=9^-8
27 GOSUB 3: IF A=B THEN LET A=A+9^-8
29 C=ATN (COS (.5*(B-A))/COS (.5*(B+A))/TAN (L/2))
30 D=ATN (SIN (.5*(B-A))/SIN (.5*(B+A))/TAN (L/2))
31 I=C+D:E=2*C-I:L=[PI]/90*ATN (TAN (.5*(B-A))*SIN (.5*(I+E))/SIN (.5*(I-E)))
32 IF E<0 THEN LET E=360+E
33 I=180-I:L=3437.75*L:K=(E+I)/2: PRINT "RV:";I;" DIS:";L:F=H:G=J: GOTO 25
35 "A": GOSUB 1: INPUT "CAS?";A: GOSUB 4
37 PRINT "TAS:";A*[SQR]((273+T)/(288/10^(P/(63691.8-.21907*P))))+R: END
39 "C": GOSUB 3: INPUT "DIR?";A,"WD?";B,"WS?";G:C=G*COS (B-A):D=G*SIN (B-A):E$="L"
40 IF D>0 THEN LET E$="R"
43 F$="TW": IF C>0 THEN LET F$="HW"
45 PRINT R+ABS D;E$;R+ABS C;F$: END
47 "H": GOSUB 1: INPUT "CA?";A: GOSUB 4: PRINT "TA:";(273+T)/(288-P*.001918)*A+R: END
49 "N"GOSUB 3: INPUT "MC?*";S: GOTO 53
51 PAUSE S
53 INPUT "REF DIR?";Q,"REF DIS?";N
61 INPUT "DIR VOR?*";A: GOTO 63
62 PAUSE A
63 I=A-S: IF I<0 THEN LET I=360+I
64 C=180-I: GOSUB 2: INPUT "DIS VOR?*";M: GOTO 67
65 PAUSE M
67 D=N*SIN (Q-S)/SIN C: PAUSE "DIS:";D+R:F=(D-M)*SIN C:D$="L": IF F>0 THEN LET D$="R"
68 PRINT D$;R+ABS F: GOTO 61
69 "V": GOSUB 3: INPUT "DIR 1-2?";U,"DIS 1-2?";O
77 INPUT "DIR 1?";A,"DIR 2?";B:I=A-B: GOSUB 2:M=ABS (O*SIN (B-U)/SIN I): PRINT "DIS:";M+R: GOTO 77
90 "B": CLEAR : GOTO 100
100 USING "#####.##"
120 DIM A(12),I(12),M(12),W(12),Y$(1)
130 A=B=C=D=V=L=Z=W=U=T=M=O=E=K=J=X=0
140 PRINT =LPRINT
160 INPUT "P.MAX? ";X
180 INPUT " CG AV MAX? ";U
190 INPUT " CG AR MAX? ";D
200 INPUT "NB PRS? ";C
210 INPUT "P.PIL? ";P:W(1)=P
215 INPUT "POS PIL? ";A:A(1)=A
218 M(1)=W(1)*A(1)
220 IF C<2 THEN GOTO 290
225 INPUT "P.PAX1? ";W:W(2)=W
230 INPUT "POS PAX1? ";A:A(2)=A
232 M(2)=W(2)*A(2)
240 IF C<3 THEN GOTO 290
245 INPUT "P PAX2? ";P:W(3)=P
250 INPUT "POS PAX2 ";A:A(3)=A
252 M(3)=W(3)*A(3)
255 IF C<4 THEN GOTO 290
260 INPUT "P PAX3? ";P:W(4)=P
265 INPUT "POS PAX?* ";A:A(4)=A
268 M(4)=W(4)*A(4)
290 INPUT "QTE ESS? ";E:W(5)=E*.72
300 INPUT "POS ESS? ";A:A(5)=A
305 M(5)=W(5)*A(5)
310 INPUT "QTE OIL? ";O:W(6)=O*.9
320 INPUT "POS OIL? ";A:A(6)=A
325 M(6)=W(6)*A(6)
326 INPUT "P. BAG? ";B:W(7)=B
328 INPUT "POS BAG? ";A:A(7)=A
330 M(7)=W(7)*A(7)
332 INPUT "P.EMPTY? ";V:W(8)=V
334 INPUT "POS EMPTY? ";Z:A(8)=Z
336 M(8)=W( 8 )*A( 8 )
338 INPUT "BALLAST?";K:W(9)=K
340 INPUT "POS.BALL?";J:A(9)=J
342 M(9)=W(9)*A(9)
344 FOR I=1 TO 10
346 T=T+W(I):M=M+M(I): NEXT I
348 G=M/T
350 F=M(9)
352 PRINT "P.CURRENT: ";T
354 IF T>X THEN BEEP 3: PRINT "*P. CURRENT>P. MAX*"
356 USING "##.##"
358 PRINT "AV";U;" ";G;" AR";D
360 PRINT "NEW. CG: ";G
364 IF G<U OR G>D THEN BEEP 3: PRINT "*CG EXTRAORDINARY*"
370 INPUT "AGAIN(Y/N)";Y$
372 IF Y$="Y" THEN GOTO 90
374 END
520 "Z": INPUT "MB?";A:I=A*.02954
525 USING "###.##"
530 PRINT I
