5 "A"CLEAR
10 RANDOM :CLS :WAIT 96:CURSOR 4:PRINT "*** Star Wars ***"
20 DIM T$(4)*18,E$(1)*18,ST(5):AD=&78C0
30 T$(0)="000000000000000000"
40 T$(1)="000000000100000000"
50 T$(2)="000000070207000000"
60 T$(3)="00001F040E041F0000"
70 T$(4)="3E49081C1C1C08493E"
80 E$(0)="00000A041F040A0000"
90 E$(1)="08492A1C7F1C2A4908"
100 POKE AD,72,118,74,0,88,119,90,0,106,77,251,181,255,1,14,251,181,255,17,30,64
110 POKE AD+21,80,136,14,154
115 IF DERETURN
120 CLS :INPUT "Spielstufe? (1-64)",L
130 IF L<1OR L>64OR L<>INT LTHEN 120
140 CLS :T1=DEG TIME
190 R=LOG (EXP L)
200 P=RND 6-1:GCURSOR 0:GPRINT L;127
210 Q=SGN (RND R-.1*R):IF Q<0LET Q=0
215 IF ST(P)=4LET Q=1
220 A=ASC INKEY$ -17:IF DELET A=RND 12-1
225 IF A<0OR A>5THEN 310
230 S=ST(A):Z=SGN (S-RND 4):IF Z<0LET Z=0
240 FOR I=0TO 3-S+ZSTEP 2:WAIT 0
250 BEEP 2,1,320:W=2^(6-1)+2^(5-1):X=15+A*24:GCURSOR X:V=POINT XOR W:GPRINT V
260 NEXT I
270 IF Z=0OR ST(A)=0THEN 310
280 G=0:IF ST(A)>2LET G=1
300 GCURSOR 11+24*A:WAIT 0:GPRINT E$(G):BEEP 1,255,200:SC=SC+ST(A):ST(A)=0:IF L>1LET L=L-1
310 ST(P)=ST(P)+Q
320 IF ST(P)<5THEN 360
330 CALL AD:ST(P)=0
340 FOR I=1TO 20:BEEP 1,1,640:NEXT I
350 CALL AD:C=C+1:IF C=3AND DE=0THEN 500
360 WAIT 0:GCURSOR 11+24*P:GPRINT T$(ST(P))
380 GOTO 190
500 T=DMS (DEG TIME -T1):H=T/100:H=H-INT H:H=INT (100*H)
510 M=T-INT T:M=INT (100*M):S=100*T:S=S-INT S:S=INT (100*S)
520 WAIT 256:CLS :PRINT "    *** Spielende ***"
530 PRINT "Punkte:";SC:PRINT "Zeit: ";STR$ H;":";STR$ M;":";STR$ S:PRINT "Spielstufe:";L
540 WAIT 0:PRINT "Neues Spiel? (J/N)"
550 A$=INKEY$ :IF A$="J"THEN 5
560 IF A$<>"N"THEN 540
570 END
600 "D"CLEAR :DE=1:L=1:GOSUB 10:CLS :WAIT 96
610 FOR I=1TO 4:GPRINT T$(I);:PRINT " =";I;" ";:NEXT I:WAIT 192:CURSOR 25:PRINT ""
620 GOTO 140