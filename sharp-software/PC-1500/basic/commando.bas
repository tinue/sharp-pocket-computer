1 CLEAR :RANDOM :RESTORE :WAIT 0:DIM A$(2),K$(5),B$(4),C$(23),T(22)
2 PRINT "*****<<< COMMANDO >>>*****":BEEP 2
3 S=0:O=3:R=1:M=1:P=5:GOSUB 900
5 IF INKEY$ =""GOTO 5
6 BEEP 1:PRINT "  SCENE ";M:GOSUB 930:CLS
7 A=1:B=1:D=9:E=23:F=0:G=0
9 FOR I=0TO 22:CURSOR I:GPRINT C$(I):NEXT I:CURSOR 23:GPRINT I$;J$
10 IF R=5CURSOR 25:GPRINT B$
15 ON FGOTO 90,100
20 ON VAL (INKEY$ )GOTO 130,30,130,35,115,45,65,60,75
25 GOTO 130
30 B=0:GOTO 130
35 IF A-1<1OR T(A-1)=1OR B=0GOTO 130
40 CURSOR A:GPRINT G$:A=A-1:GOTO 130
45 IF A>19GOTO 235
50 IF T(A+1)=1OR B=0GOTO 130
55 CURSOR A:GPRINT G$:A=A+1:GOTO 130
60 B=1:GOTO 130
65 IF B=0OR A-1<1GOTO 130
70 F=1:CURSOR A:GPRINT C$(A):A=A-1:B=2:GOTO 130
75 IF B=0GOTO 130
80 IF A>19GOTO 235
85 F=2:CURSOR A:GPRINT C$(A):A=A+1:B=2:GOTO 130
90 IF A-1<1OR T(A-1)=1LET B=1:F=0:GOTO 130
95 CURSOR A:GPRINT C$(A):A=A-1:B=1:F=0:GOTO 130
100 IF T(A+1)=1LET B=1:F=0:GOTO 130
105 IF A>19GOTO 235
110 CURSOR A:GPRINT C$(A):A=A+1:B=1:F=0:GOTO 130
115 IF B=3GOTO 130
120 GCURSOR A*6+4:GPRINT "0808":IF A+1<>EGOTO 130
125 BEEP 2,70,10:CURSOR A+1:GPRINT C$(A+1):E=23:S=S+20*B
130 CURSOR A:GPRINT A$(B):IF A=ELET D=D-4:E=23:BEEP 1,200,20
135 IF A>17CURSOR E:GPRINT C$(E):E=23:GOTO 155
140 CURSOR E:GPRINT C$(E):E=E-1:CURSOR E:IF T(E)=0GPRINT E$:GOTO 150
145 GPRINT F$
150 IF A=ELET D=D-4:E=23:BEEP 1,200,20
155 ON GGOTO 170,175,175,180,200
160 K=22:IF RND P<>1GOTO 190
165 CURSOR 22:GPRINT C$:G=1:H=INT ((22-A)/4):H=H+(H=0):GOTO 190
170 K=K-H:CURSOR 22:GPRINT D$:CURSOR K:GPRINT B$(1):G=G+1:GOTO 15
175 CURSOR 22:GPRINT D$:GOTO 185
180 CURSOR 22:GPRINT G$
185 CURSOR K:GPRINT C$(K):K=K-H+RND 2-1:CURSOR K:GPRINT B$(G):G=G+1
190 U$=STR$ (INT (D)):CURSOR 0:PRINT U$:IF D<0GOTO 260
195 GOTO 15
200 CURSOR K:GPRINT H$:G=0:IF ABS (K-A)>4CURSOR K:GPRINT C$(K):GOTO 15
205 N=SGN (K-A):IF ABS (K-A)>1AND T(A+N)=1AND B=0CURSOR K:GPRINT C$(K):GOTO 15
210 L=5-ABS (K-A)+1:IF K=ALET L=6:GOTO 230
215 IF T(K)=1LET L=L-1
220 IF T(A+N)=1LET L=L-1
225 IF B=0LET L=L/2
230 D=D-L:CURSOR K:GPRINT C$(K):FOR I=1TO L:BEEP 1,150+I*10,5:NEXT I:GOTO 190
235 CURSOR K:GPRINT C$(K):CURSOR 22:GPRINT G$:CURSOR 20:GPRINT A$(1):PAUSE
240 FOR I=1TO 3:CURSOR I+20:GPRINT B$(I):BEEP 1,200+I*10,80:CURSOR I+20:GPRINT C$(I+20):NEXT I:BEEP 1,240,80
245 CURSOR 24:GPRINT H$:BEEP 3:CURSOR 23:GPRINT H$:BEEP 9:PAUSE
250 FOR I=INT DTO 0STEP -1:S=S+50:U$=STR$ (I):CURSOR 0:PRINT U$:BEEP 1:NEXT I:IF R=5GOTO 280
255 M=M+1:R=R+1:PAUSE :PAUSE " SCORE ";S:GOTO 6
260 PAUSE :WAIT 10:O=O-1:CURSOR A:GPRINT "400060406050":BEEP 3,200
265 IF O>0PAUSE " SCORE ";S:WAIT 0:GOTO 6
270 CURSOR 8:PRINT "GAME OVER":RESTORE 955:FOR I=1TO 11:READ U,V:BEEP 1,U,V:NEXT I
275 PAUSE :WAIT :PRINT " SCORE ";S:GOTO 1
280 PAUSE :PAUSE :FOR I=24TO 21STEP -1:CURSOR I:GPRINT G$;A$;G$:BEEP 1:PAUSE :NEXT I:CURSOR 20:GPRINT A$(0)
285 FOR I=1TO 4:CURSOR 21:GPRINT "0000040000":BEEP I,70,80:CURSOR 21:GPRINT "00060E0600":BEEP I,50,100
290 CURSOR 21:GPRINT "060F1E0F06":BEEP I,38,120:BEEP I,31,500:NEXT I
295 PAUSE :CURSOR 8:PRINT "BONUS 500":BEEP 9:S=S+500:M=M+1:R=1:P=P-1+(P=0):O=O+1:PAUSE :GOTO 6
900 FOR I=1TO 10:READ @$(1):NEXT I
905 FOR I=0TO 2:READ A$(I):NEXT I
910 FOR I=1TO 5:READ P$:K$(I)=P$+"0000":NEXT I
915 FOR I=1TO 4:READ P$:B$(I)=P$+"0000":NEXT I
920 RETURN
921 DATA "20367D3020","28743D2422","603C3B4848","603C7F4000","04641E3C40"
922 DATA "020A070F08","000000000000","516478724821","7F130303037F","73737F73737F"
923 DATA "487034600804","087B1A680402","0A0E070A02"
924 DATA "00787878","00707870","00787060","00407870","00285000"
925 DATA "00060604","00060700","040C0C00","00606000"
930 FOR I=0TO 22:C$(I)=G$:T(I)=0:NEXT I
935 RESTORE 950:FOR I=1TO R:READ W:NEXT I
940 FOR I=3TO 19:V=INT (W/2):IF V<>W/2LET T(I)=1:C$(I)=K$(R)
945 W=V:NEXT I:C$(23)=I$:RETURN
950 DATA 174410,174372,214148,215332,199233
955 DATA 90,180,90,120,90,50,90,180,75,150,80,50,81,150,90,50,90,120,96,50,90,300