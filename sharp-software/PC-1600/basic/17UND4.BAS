5 "17UND4"
10 "A"CLS :CLEAR :WAIT 0:BEEP ON :GOSUB "DISPLAY"
20 "B"CLS :CLEAR :WAIT 0:RANDOM :D=1000
30 DIM K$(32)*13,W$(32)*2,X$(75),KK$(32)
40 X$(55)="01390503"
50 X$(56)="36494936"
60 X$(57)="2649493E"
70 X$(49)="02017F003E41413E"
80 X$(66)="7F494936"
90 X$(68)="3F21211E"
100 X$(75)="1F040A11"
110 X$(65)="3E09093E"
120 K$(1)="Karo  7     7"
130 K$(2)="Karo  8     8"
140 K$(3)="Karo  9     9"
150 K$(4)="Karo  10   10"
160 K$(5)="Karo  Bube  2"
170 K$(6)="Karo  Dame  3"
180 K$(7)="Karo  Knig 4"
190 K$(8)="Karo  Ass  11"
200 K$(9)="Herz  7     7"
210 K$(10)="Herz  8     8"
220 K$(11)="Herz  9     9"
230 K$(12)="Herz  10   10"
240 K$(13)="Herz  Bube  2"
250 K$(14)="Herz  Dame  3"
260 K$(15)="Herz  Knig 4"
270 K$(16)="Herz  Ass  11"
280 K$(17)="Pik   7     7"
290 K$(18)="Pik   8     8"
300 K$(19)="Pik   9     9"
310 K$(20)="Pik   10   10"
320 K$(21)="Pik   Bube  2"
330 K$(22)="Pik   Dame  3"
340 K$(23)="Pik   Knig 4"
350 K$(24)="Pik   Ass  11"
360 K$(25)="Kreuz 7     7"
370 K$(26)="Kreuz 8     8"
380 K$(27)="Kreuz 9     9"
390 K$(28)="Kreuz 10   10"
400 K$(29)="Kreuz Bube  2"
410 K$(30)="Kreuz Dame  3"
420 K$(31)="Kreuz Knig 4"
430 K$(32)="Kreuz Ass  11"
440 X=3:Y=3:A=1:AA=2:B=25:BB=1
450 LINE (A,32)-(B,BB),,,B
460 I=INT (RND 32)
470 Z=VAL RIGHT$ (K$(I),2):WT=WT+Z
480 IF K$(I)=""GOTO 460
490 O=ASC MID$ (K$(I),7,1)
500 K$=LEFT$ (K$(I),11):KK$(I)=K$(I):K$(I)=""
510 IF LEFT$ (K$,4)="Karo"LET B$="081C3E7F3E1C08":GOTO 550
520 IF LEFT$ (K$,4)="Herz"LET B$="060F1F3E1F0F06":GOTO 550
530 IF LEFT$ (K$,3)="Pik"LET B$="183CBEFFBE3C18":GOTO 550
540 IF LEFT$ (K$,5)="Kreuz"LET B$="0E04457F45040E"
550 GCURSOR X,Y:GPRINT B$
560 GCURSOR X+1,Y+9:GPRINT X$(O)
570 IF K=1LET S$="   Bank "
580 IF K=0LET S$="Spieler "
590 CURSOR 14,1:PRINT S$;WT
600 IF K=1WAIT 100
610 CURSOR 18,3:PRINT D;" $"
620 WAIT 0
630 IF K=1GOTO 680
640 IF WT=21BEEP 3,15,800:WAIT 100:CURSOR 5,2:PRINT "du hast GEWONNEN...":D=D+100:GOTO 770
650 IF WT>21BEEP 3,250,200:WAIT 100:CURSOR 0,2:PRINT "Du hast leider verloren !":D=D-100:GOTO 770
660 CURSOR 11,0:INPUT "Neue Karte ? ";A$:IF A$="J"GOTO 730
670 CLS :K=1:Z=0:WK=WT:WT=0:GOTO 440
680 IF WT<WKAND K=1GOTO 730:GOTO 460
690 IF WT>21AND K=1BEEP 3,15,800:WAIT 100:CURSOR 5,2:PRINT "DU hast gewonnen !":D=D+100:GOTO 770
700 IF WT>=WKBEEP 3:WAIT 0:CLS :PRINT "   Die Bank hat mit":PRINT "       ";WT;" :";WK
710 WAIT 100:PRINT "       gewonnen":D=D-100:GOTO 770
720 IF K=1GOTO 730
730 A=A+15:AA=AA+3:B=B+15:BB=BB+3
740 LINE (A,32)-(B,BB),,,B:LINE (A+9,AA)-(B-15,32),X
750 X=X+15:Y=Y+3
760 GOTO 460
770 IF D<=0CLS :WAIT 0:PRINT :PRINT "Du bist jetzt pleite !":END
780 CLS :WAIT 0:PRINT "Du hast jetzt";D;" $":PRINT
790 INPUT "Noch ein Spiel ? (J/N) ";N$
800 IF N$="N"CLS :END
810 WAIT 0:CLS :FOR I=0TO 32
820 IF LEN KK$(I)>0LET K$(I)=KK$(I)
830 NEXT I
840 CLS :K=0:Z=0:O=0:WT=0:WK=0:GOTO 40
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
110 T$="17 UND 4 ":GOSUB 40:GOSUB 20
120 GOSUB 90
130 BEEP 3:CLS :CLEAR :WAIT 0:CALL &02DD,A:RETURN
