10 REMAnalyse binary format
20 REM PEEK Hex
30 PEEK &FF
40 REM PEEK Dec
50 PEEK 255
60 REM PEEK with parentheses
70 PEEK(&FF)
80 REM Number formats
90 A=123456787890
100 B=1.23456789
110 C=1E99
120 REM Labels
130 "A"PRINTA
140 "B"PRINT(B)
150 REM CLOAD Test
160 CLOAD
170 CLOADa
180 END