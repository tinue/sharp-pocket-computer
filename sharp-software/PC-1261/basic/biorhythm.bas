5 "A": CLEAR : DEGREE : LPRINT ""
10 INPUT "NAME=";D$
20 LPRINT "NAME:";D$
30 INPUT "DATE OF BIRTH:YEAR=";U$,"MONTH=";V,"DAY=";W
35 F$=STR$ V:G$=STR$ W
40 LPRINT "DATE OF BIRTH:": LPRINT "    ";U$;",";F$;",";G$
50 GOSUB 500:B=X
60 INPUT "DESIRED:YEAR=";U$,"MONTH=";V:W=0
65 F$=STR$ V
70 LPRINT "DESIRED YEAR,MONTH:": LPRINT "    ";U$;",";F$
75 LPRINT ""
80 GOSUB 500:A=X
100 C=A-B
110 GOSUB 700
120 GOSUB 800
130 D=C-INT (C/23)*23
140 E=C-INT (C/28)*28
150 F=C-INT (C/33)*33
160 DIM B$(2)*21
170 B$(1)="I---------+---------I"
180 B$(2)="I         +         I"
200 LPRINT "     -    0    +"
210 LPRINT B$(1)
215 FOR K=1 TO Z
220 L=0: IF K=5*INT (K/5) LET L=1
230 IF L=1 LET B$(0)=B$(1): GOTO 250
240 B$(0)=B$(2)
250 G=SIN ((K+D)/23*360):P$="S": GOSUB 900
260 G=SIN ((K+E)/28*360):P$="K": GOSUB 900
270 G=SIN ((K+F)/33*360):P$="C": GOSUB 900
290 LPRINT B$(0);USING "###";K
300 NEXT K
310 LPRINT B$(1)
320 END
500 T=VAL U$
560 IF V>=3 LET Q=T:R=V+1:S=W: GOTO 580
570 Q=T-1:R=13+V:S=W
580 X=INT (365.25*Q)+INT (30.6*R)+S-122
590 X=X-INT (Q/100)+INT (Q/400)
600 RETURN
700 IF T<>4*INT (T/4) LET Y=0: RETURN
710 IF T<>100*INT (T/100) LET Y=1: RETURN
720 IF T<>400*INT (T/400) LET Y=0: RETURN
730 Y=1: RETURN
800 IF (V=4)+(V=6)+(V=9)+(V=11)=1 LET Z=30: RETURN
810 IF V=2 LET Z=28+Y: RETURN
820 Z=31: RETURN
900 N=11+INT (8*ABS G)*SGN G
910 B$(0)=LEFT$ (B$(0),N-1)+P$+RIGHT$ (B$(0),21-N)
920 RETURN