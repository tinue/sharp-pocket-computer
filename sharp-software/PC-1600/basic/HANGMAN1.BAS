5 "HANGMAN1"
10 "A"CLS :CLEAR :WAIT 0:BEEP ON :GOSUB "DISPLAY"
90 "B"CLS :CLEAR :FOR X=1TO 9:GOSUB STR$ X:NEXT X
100 GCURSOR 48,8:GPRINT "FEFFFEC0C0C0C0C0C0FEFFFE000000FEFFFFC7C7C7C7C7C7FFFFFE000000";
110 GPRINT "FEFFFF070707070707FFFFFE000000FEFFFF07070787C7C7C7C782000000";
120 GPRINT "FEFFFF07070E1C0E0707FFFFFE000000FEFFFFC7C7C7C7C7C7FFFFFE000000FEFFFF070707";
130 GPRINT "070707FFFFFE
140 GCURSOR 48,16:GPRINT "7FFF7F0101010101017FFF7F0000007FFF7F0101010101017FFF7F000000";
150 GPRINT "7FFF7F0000000000007FFF7F0000007FFFFFE0E0E0E0E1E1FFFF7F0000007FFF7F00000000";
160 GPRINT "0000007FFF7F0000007FFF7F0101010101017FFF7F0000007FFF7F0000000000007FFF7F
170 CURSOR 10,3:PRINT "Any key to start"
180 C$="BEGIN"
190 IF INKEY$ <>""GOTO 260
200 GOSUB 640
210 IF INKEY$ <>""GOTO 260
220 GOSUB 660
230 IF INKEY$ <>""GOTO 260
240 GOTO 90
260 CLS :CLEAR :DIM A$(0)*20,Z$(20)*20,V$(1)*20:P=7:RESTORE
270 FOR I=1TO (RND 19):READ A$(0):NEXT I:A=LEN A$(0)
280 FOR I=1TO A:Z$(I)="_":NEXT I
290 FOR I=1TO A:V$(0)=V$(0)+Z$(I):NEXT I
300 CURSOR 7,0:PRINT V$(0)
310 CURSOR 15,2:INPUT "Letter:";L$
320 IF LEN L$<>1BEEP 3:GOTO 310
330 NT=NT+1:FOR Z=1TO A:A$=MID$ (A$(0),Z,1):IF A$=L$LET Z$(Z)=A$
340 NEXT Z
345 CURSOR P,3:PRINT L$:P=P+1
350 FOR X=1TO A:V$(1)=V$(1)+Z$(X):NEXT X
360 IF V$(1)=V$(0)LET T=T+1ELSE IF V$(1)=A$(0)GOTO 430
370 V$(0)="":V$(1)=""
380 IF T=0GOTO 290ELSE IF T>9GOSUB 640:GOTO 410
390 FOR D=1TO T:GOSUB STR$ T:NEXT D
400 GOTO 290
410 CLS :CURSOR 10,0:PRINT "I won!":CURSOR 6,2:PRINT "You had 10 trials...":CURSOR 0,3:PRINT "but no success
420 GOTO 440
430 CLS :PRINT "*** Congratulation!! ***":CURSOR 1,1:PRINT A$(0):CURSOR 17,2:PRINT "is right!"
440 CURSOR 0,3:PRINT "You needete";NT;" Trials":GOSUB 640:PAUSE :PAUSE :PAUSE :PAUSE
450 CLS :CURSOR 1,1:PRINT "Once again (Y/N)?"
460 IF INKEY$ ="N"GOTO 480
470 IF INKEY$ ="Y"GOTO 90
475 GOTO 460
480 END
530 "1"GCURSOR 0,24:GPRINT "8080404020201010080804040402020201010101010101020202040404080810";
540 GPRINT "10202040408080":RETURN
550 "2"FOR I=0TO 2:LINE (18+I,0)-(18+I,24):NEXT I:RETURN
560 "3"FOR I=0TO 2:LINE (20,I)-(35,I):NEXT I:RETURN
570 "4"FOR I=0TO 2:LINE (25+I,2)-(20,7+I):NEXT I:RETURN
580 "5"LINE (34,2)-(34,7):RETURN
590 "6"LINE (33,7)-(35,7):LINE (36,8)-(36,10):LINE (33,11)-(35,11):LINE (32,8)-(32,10):RETURN
600 "7"LINE (34,11)-(34,17):RETURN
610 "8"LINE (30,21)-(34,17):LINE -(38,21):RETURN
620 "9"LINE (30,14)-(34,12):LINE -(38,14):RETURN
630 '---------------------
640 FOR I=0TO 31:LINE (0,I)-(155,I),X:IF INKEY$ <>""AND C$="BEGIN"GOTO 260
650 NEXT I:WAIT 75:GPRINT :WAIT 0:RETURN
660 FOR I=0TO 77:LINE (I,0)-(I,31),X:LINE (155-I,0)-(155-I,31),X:IF INKEY$ <>""AND C$="BEGIN"GOTO 260
670 NEXT I:RETURN
680 '---------------------
690 DATA "POCKET-COMPUTER","ANWEISUNG","BRIEF
700 DATA "TELEFONANSCHLUSS","CASSETTE","DISKETTE","AMERIKA
710 DATA "COMPUTER","MIKROFON","DAMPFSCHIFF","ARMBANDUHR
720 DATA "PLOTTER","FLASCHE","TASCHENRECHNER","TASTATUR","VENTILATOR","VIBRATION
730 DATA "FISCHEL","BMW"
99999
5 "DISPLAY":BEEP 3
10 GOTO 100
20 LINE (0,2)-(155,10),X,,BF
21 LINE (0,0)-(155,0):LINE (155,12)-(0,12)
30 CURSOR 0,2:RETURN
40 TA=86-LEN T$*6
50 GCURSOR TA,3
55 FOR ZZ=1TO LEN T$
60 T=ASC MID$ (T$,ZZ,1)
62 FOR ZY=0TO 4
64 GPRINT PEEK ((T-65)*6+44650+ZY);
65 GPRINT PEEK ((T-65)*6+44650+ZY);:NEXT ZY:GPRINT "00";
70 NEXT ZZ
80 RETURN
90 CURSOR 0,2:FOR ZZ=1TO 51:PRINT " ";:NEXT ZZ:CURSOR 0,2
95 RETURN
100 CLS :CLEAR
110 T$="HANGMAN 1":GOSUB 40:GOSUB 20
120 GOSUB 90
130 BEEP 3:CLS :CLEAR :WAIT 0:CALL &02DD,A:RETURN
