100 REM ****************
101 REM *Jahreskalender*
102 REM * AB JAHR 1752 *
103 REM ****************
110 PRINT " Jahreskalender":INPUT "fuer Jahr=?";JA
120 IF JA<1752 THEN 110
130 DIM M$(12),W$(12),Y$(0)*20,L(12),Q(12),J(12)
140 L=JA-1:P=L/100:Z1=INT(P/4):Z2=INT(P):Z3=INT((5*L)/4):Z=36+Z3-Z2+Z1
150 Z=Z-(7*INT(Z/7)):IF Z=0 THEN LET Z=7
160 Q(1)=Z
170 FOR K=1 TO 7
180 READ W$(K):NEXT K
190 FOR J=1 TO 12
200 READ M$(J),L(J):NEXT J
210 IF INT(JA/4)=JA/4 THEN LET L(2)=29
220 FOR J=2 TO 12
230 Q(J)=L(J-1)-28+Q(J-1)
240 IF Q(J)>7 THEN LET Q(J)=Q(J)-7
250 NEXT J
260 LPRINT "Kalender fuer ";JA:LPRINT ""
270 FOR J=1 TO 12:LPRINT M$(J);" ";JA
280 FOR K=1 TO 7:Y$(0)=W$(K)
290 IF K<Q(J) THEN LET Y$(0) = Y$(0)+"   ":T=K-Q(J)+8:GOSUB 400:GOTO 310
300 T=K-Q(J)+1:GOSUB 400
310 Y$(0)=Y$(0)+X$:T=T+7:IF T>L(J) THEN 330
320 GOSUB 400:GOTO 310
330 LPRINT Y$(0):NEXT K:LPRINT "": NEXT J:LPRINT "":END
400 X$=STR$ T: IF LEN X$ = 1 THEN LET X$=" "+X$
410 X$=" "+X$:RETURN
500 DATA "Mo","Di", "Mi", "Do", "Fr", "Sa", "So", "Januar", 31, "Februar", 28, "Maerz", 31
510 DATA "April", 30, "Mai", 31, "Juni", 30, "Juli", 31, "August", 31, "September", 30
520 DATA "Oktober", 31, "November", 30, "Dezember", 31
