10 "BIO":CLEAR :INPUT "Biorhythm, Year? ";L, "Month?";M
20 TEXT :COLOR 0
30 LPRINT "Month";M;" /";L
40 N=0
50 GOSUB 700
60 GOSUB 600: O=A
70 INPUT "Your name? ";A$
80 INPUT "Birthday? Year = ";L,"Month = ";M,"Day = ";N
90 LPRINT A$
95 LPRINT N;".";M;".";L
96 CLS:WAIT0:PRINT"Plotting..."
98 GOSUB 600:P=A
100 A=O-P:O=0:P=0
110 LF 2
120 COLOR 2
130 LPRINT " -- Physical"
140 COLOR 3
150 LPRINT " -- Emotional"
160 COLOR 1
170 LPRINT " -- Intellectual"
180 LF 1
190 COLOR 0
200 LPRINT "  (-)      (+)"
205 GRAPH 
210 GLCURSOR (100,0):SORGN 
215 Y=I*2.5*5*(-1)
220 LINE (-100,0)-(115,0)
230 LINE (0,0)-(0,Y)
235 LINE (115,Y)-(115,0)
240 FOR Q=5TO 30STEP 5
243 R=Q
245 IF Q=30LET R=1
250 Y=R*2.5*(-1)*5
260 LINE (-90,Y)-(115,Y)
270 X=80
290 Z=Y+5
300 LINE (115,Z)-(X,Z),9
310 LPRINT R
320 NEXT Q
330 B=INT (A/23):B=A-(23*B)
340 C=INT (A/28):C=A-(28*C)
350 D=INT (A/33):D=A-(33*D)
360 FOR J=1TO 3
395 COLOR J
400 E=0
410 FOR Y=0TO I
420 IF J=2LET X=SIN ((B+Y)/23*360)*80
430 IF J=3LET X=SIN ((C+Y)/28*360)*80
440 IF J=1LET X=SIN ((D+Y)/33*360)*80
450 Z=Y*(-1)*2.5*5
460 F=0
470 IF E=0LET F=9:LET E=1
480 LINE (O,P)-(X,Z),F
490 O=X:P=Z
500 NEXT Y
510 NEXT J
515 TEXT :LF 5:COLOR 0
520 END
600 IF M-3>=0LET M=M+1:GOTO 620
610 L=L-1:M=13+M
620 A=INT (365.25*L)+INT (30.6*M)+N
625 A=A-INT (L/100)+INT (L/400)
630 RETURN 
640 END
700 IF M=2GOTO 790
710 IF M=4GOTO 770
720 IF M=6GOTO 770
730 IF M=9GOTO 770
740 IF M=11GOTO 770
750 I=31:GOTO 900
770 I=30:GOTO 900
790 K=INT (L/4):K=L-K*4
800 IF K=0GOTO 840
820 I=28:GOTO 900
840 K=INT (L/100):K=L-K*100
845 IF K=0GOTO 850
847 GOTO 890
850 K=INT (L/400):K=L-K*400
860 IF K=0GOTO 890
870 GOTO 820
890 I=29
900 RETURN 
910 END