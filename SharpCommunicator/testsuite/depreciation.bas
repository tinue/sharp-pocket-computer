   10 "A":CLEAR :WAIT 
   20 INPUT "COST?";A:GOTO 30
   25 END 
   30 INPUT "NO. OF TIMES?";B
   40 INPUT "REM. RATE (%)?";O
   50 IF (O<5)+(O>99)=1GOTO 40
   60 C=1-(O/100)^(1/B)
   70 D=INT (C*10^5+.5)/10^5
   80 E=0
   90 FOR I=1TO B
  100 F=INT (D*A)
  110 E=E+F
  120 A=A-F
  130 PRINT I;"   DEPR.=";F
  150 PRINT I;"   UNDEPR.=";A
  160 NEXT I
  170 PRINT "TTL DEPR.=";E
  200 GOTO 20
  500 "B":CLEAR :WAIT
  510 INPUT "COST?";E:GOTO 520
  515 GOTO 610
  520 INPUT "YEAR OF LIFE?";F
  530 INPUT "DEPR. MONTH?";G
  535 INPUT "REM. RATE(%)?";H
  540 IF (H<5)+(H>99)=1GOTO 535
  546 H=(100-H)/100
  550 D=INT (E*H/F*G/12)
  560 A=A+D:B=E+B
  575 PRINT "DEPR.=";D
  580 PRINT "UNDEPR.=";E-D
  590 GOTO 510
  610 PRINT "TTL COST=";B
  615 PRINT "TTL DEPR.=";A
  620 PRINT "TTL UNDEPR.=";B-A
  630 END
