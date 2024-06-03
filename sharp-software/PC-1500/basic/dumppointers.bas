10 REM Active bank of CE163F
20 PRINT "Bank:"; PEEK&E2
30 REM Keyboard intercept
40 K$="off": IF PEEK&79D4=&55 LET K$="on"
50 H=&785B: GOSUB "HEXPEEK": K$=K$+" at &" +H$
60 PRINT "Kb. intcpt ";K$
70 REM Basic pointers bottom, top, merge
80 DIM PR$(1)*30: PR$(1)= "Basic: &"
90 H=&7865: GOSUB "HEXPEEK": PR$(1)=PR$(1)+H$+" &"
100 H=&7867: GOSUB "HEXPEEK": PR$(1)=PR$(1)+H$+" &"
110 H=&7869: GOSUB "HEXPEEK": PR$(1)=PR$(1)+H$
120 PRINT PR$(1)
130 REM Start of DIMes variables
140 H=&7899: GOSUB "HEXPEEK": T$=H$
150 PRINT "Var start: &"; T$
160 REM Battery status
170 K$="low": IF PEEK&79F1=0 LET K$="good"
180 PRINT "Battery ";K$
190 END
1000 "HEXPEEK": REM Returns 2 bytes hex value of addr. H as H$
1010 REM Overwrites variables MM, MH, ML, MT$ and MM$
1020 MM = PEEK H: GOSUB 1040: H$=MT$
1030 MM = PEEK (H+1): GOSUB 1040: H$=H$+MT$: RETURN
1040 MH = INT (MM/16): ML = MM - (16*MH)
1050 MM=MH:GOSUB 1070:MT$=MM$
1060 MM=ML:GOSUB 1070:MT$=MT$+MM$: RETURN
1070 IF MM=0 LET MM$="0": RETURN
1080 IF MM>9 GOTO 1100
1090 MM$=CHR$ (MM+48): RETURN
1100 MM$=CHR$ (MM+55): RETURN
