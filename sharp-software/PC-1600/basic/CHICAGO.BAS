1 "CHICAGO"
3 "A"CLS :CLEAR :WAIT 0:BEEP ON :GOSUB "DISPLAY"
5 "B"CLS :CLEAR :WAIT 0:GOTO 20
10 X$=INKEY$ (1):IF X$<>CHR$ &0DGOTO 10ELSE RETURN
20 "A"CLS :CLEAR :RANDOM :DIM C(3):CURSOR 9,1:PRINT "CHICAGO":LINE (0,22)-(155,22)
25 LINE (52,7)-(96,15),X,,BF
30 CURSOR 0,3:PRINT "Anzahl der Spiele ?":X$=INKEY$ (1):A=VAL X$:IF A<1OR A>9GOTO 30
40 D=3:N1=1:N2=1:R=R+1:S1=0:S2=0:S4=0
50 '  Spiel   Spieler
60 CLS :PRINT " Spieler:":PRINT N1;".Wurf= "
65 LINE (0,0)-(55,7),X,,BF
70 P=0:FOR I=1TO D
80 GCURSOR 52+I*15,7:GOSUB 1050
90 LINE (52+I*15-1,5)-(52+I*15+10,16),,,B:P=I
100 NEXT I
110 IF P=3GOTO 140
120 I=P+1:GCURSOR 100+I*15,7:GOSUB 1070
130 LINE (100+I*15-1,5)-(100+I*15+10,16),,,B:P=P+1:GOTO 110
140 LINE (0,22)-(155,22)
150 IF D=1GOTO 200
160 IF N1<3CURSOR 0,3:PRINT "Wfel raus ?     (J/N)"ELSE GOTO 240
170 X$=INKEY$ (1):IF X$<>"J"IF X$<>"N"GOTO 170
180 IF X$="J"GOSUB 800:ELSE GOTO 200
190 S1=S1+G:GOTO 230
200 IF N1<3CURSOR 0,3:PRINT "Neuer Wurf ?   (J/N)   "ELSE GOTO 240
210 X$=INKEY$ (1):IF X$<>"J"IF X$<>"N"GOTO 200
220 IF X$="N"OR N1=3GOTO 240
230 N1=N1+1:GOTO 60
240 ' Spieler  Abrechnung
250 FOR I=1TO D
260 IF C(I)=1LET C(I)=100
270 IF C(I)=6LET C(I)=60
280 S1=S1+C(I)
290 NEXT I
300 IF S1=300LET S4=1:WAIT 50:GOTO 750
310 CURSOR 0,3:PRINT R;".Spiel :";S1;" Punkte":GOSUB 10
320 ' Spiel  Rechner
330 D=3
340 CLS :PRINT " Rechner:":PRINT N2;".Wurf= "
345 LINE (0,0)-(54,7),X,,BF
350 P=0:FOR I=1TO D
360 GCURSOR 52+I*15,7:GOSUB 1050
370 LINE (52+I*15-1,5)-(52+I*15+10,16),,,B:P=I
380 NEXT I
390 IF P=3GOTO 420
400 I=P+1:GCURSOR 100+I*15,7:GOSUB 1070
410 LINE (100+I*15-1,5)-(100+I*15+10,16),,,B:P=P+1:GOTO 390
420 LINE (0,22)-(155,22)
430 IF N2=N1OR N2=3GOTO 550
440 CURSOR 19,3:PRINT "<ENTER>":GOSUB 10
450 IF C(1)=1IF C(2)=1IF C(3)GOTO 750
460 IF N2=2IF C(1)=1OR C(1)=6IF C(2)=1OR C(2)=6GOTO 540
470 IF S2>S1GOTO 520
480 W$="":FOR I=1TO D
490 IF C(I)=1OR C(I)=6LET W$=W$+STR$ I
500 NEXT I
510 IF W$<>""GOSUB 850:S2=S2+G
520 IF N2=N1OR N2=3GOTO 550
530 N2=N2+1:GOTO 340
540 ' Rechner  Abrechnug
550 FOR I=1TO D
560 IF C(I)=1LET C(I)=100
570 IF C(I)=6LET C(I)=60
580 S2=S2+C(I)
590 NEXT I
600 IF S2=300WAIT 50:GOTO 750
610 CURSOR 0,3:PRINT R;".Spiel : ";S2;" Punkte    ":GOSUB 10
620 ' Endvergleich
630 IF S1=S2LET S1=S1+1
640 CLS :IF S1>S2PRINT R;".Spiel  Sieger: Spieler":P1=P1+1:A=A-1
641 IF S1<S2GOTO 650
645 LINE (0,0)-(48,7),X,,BF:GOTO 660
650 CLS :PRINT R;".Spiel  Sieger: Rechner":P2=P2+1:A=A-1
655 LINE (0,0)-(48,7),X,,BF
660 PRINT "Spieler=";S1;"  Rechner=";S2:LINE (0,15)-(155,15)
670 PRINT "Punktestand:":PRINT "Spieler=";P1;"    Rechner=";P2:GOSUB 10
680 IF A=0GOTO 690ELSE GOTO 40
690 CLS :CURSOR 8,0:PRINT "SPIELENDE"
700 ' SPIELENDE
710 IF P1=P2CURSOR 3,2:PRINT " Unentschieden"
720 IF P1>P2CURSOR 5,2:PRINT "Sieger: SPIELER"
730 IF P2>P1CURSOR 5,2:PRINT "Sieger: RECHNER"
740 WAIT 0,P:END
750 '  CHICAGO
760 CLS :IF S4=1LET A$="SPIELER"ELSE LET A$="RECHNER"
770 FOR I=1TO 8:WAIT 40:CURSOR 9,1:PRINT "CHICAGO":CURSOR 5,3:PRINT "SIEGER: ";A$
780 WAIT 8:CURSOR 9,1:PRINT "        ":CURSOR 5,3:PRINT "               ":NEXT I
790 WAIT 0,P:END
800 '  Wrfel raus
810 CURSOR 0,3:PRINT "Welche Wrfel?          "
820 FOR I=1TO D:CURSOR 12+(I*2),3:PRINT I:NEXT I
830 LINE (130,23)-(130,31):CURSOR 22,3:INPUT W$
840 IF LEN W$>2GOTO 800
850 B=LEN W$:V=VAL W$:G=0:T=0:ON BGOTO 870,950
860 '1Wrfel raus
870 ON VGOTO 880,890,900
880 T=C(1):C(1)=C(2):C(2)=C(3):C(3)=T:GOTO 900
890 T=C(2):C(2)=C(3):C(3)=T
900 IF C(3)=6LET G=60
910 IF C(3)=1LET G=100
920 IF C(3)<>1IF C(3)<>6LET G=G+C(3)
930 D=D-1:RETURN
940 '2Wrfel raus
950 IF V=12OR V=21LET T=C(1):C(1)=C(3):C(3)=T:GOTO 970
960 IF V=13OR V=31LET T=C(1):C(1)=C(2):C(2)=T
970 D=D-2:IF C(3)=6IF C(2)=6IF N1=1LET C(3)=1
975 IF C(3)=6IF C(2)=6IF N2=1LET C(3)=1
980 IF C(3)=6LET G=G+60
990 IF C(3)=1LET G=G+100
1000 IF C(2)=6LET G=G+60
1010 IF C(2)=1LET G=G+100
1020 IF C(3)<>1AND C(3)<>6LET G=G+C(3)
1030 IF C(2)<>1AND C(2)<>6LET G=G+C(2)
1040 RETURN
1050 ' Ziehung
1060 C(I)=RND 6
1070 IF C(I)=1GPRINT 0;0;0;0;24;24;0;0;0;0
1080 IF C(I)=2GPRINT 0;0;6;6;0;0;96;96;0;0
1090 IF C(I)=3GPRINT 0;3;3;0;24;24;0;192;192;0
1100 IF C(I)=4GPRINT 0;0;102;102;0;0;102;102;0;0
1110 IF C(I)=5GPRINT 0;195;195;0;24;24;0;195;195;0
1120 IF C(I)=6GPRINT 0;0;219;219;0;0;219;219;0;0
1130 RETURN
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
110 T$="CHICAGO ":GOSUB 40:GOSUB 20
120 GOSUB 90
130 BEEP 3:CLS :CLEAR :WAIT 0:CALL &02DD,A:RETURN
