1 "GLOBE"REM for Sharp PC-1500
2 "Author:Jose Baume
3 "L/ordin.individuel #47
10 "Z"TEXT :WAIT 0:DEGREE
20 INPUT "R(mm) :";S,"ALPHA :";A,"BETA :";B,"PHI :";F,"GRID :";G,"STEP :";P
30 INPUT "Transparency (Y or N) : ";V$
40 V=1:IF LEFT$ (V$,1)="Y"LET V=2
70 INPUT "Color (Y ou N) : ";CO$:PRINT "DRAWING..."
80 C=0,D=0:IF LEFT$ (CO$,1)="Y"LET C=1,D=2
90 CSIZE 1:LPRINT "RADIUS";S:LPRINT "ALPHA";A:LPRINT "BETA";B:LPRINT "PHI";F:LPRINT "GRID";G:LPRINT "STEP";P
100 LPRINT TIME
110 REM CONTOUR
120 COLOR D:T=9,R=S*4.9
130 LCURSOR 0:GRAPH :SORGN :GLCURSOR (108,-1.1*R):SORGN :GLCURSOR (R,0):T=0
140 FOR I=0TO 360STEP P
150 X=R*COS I,Y=R*SIN I
160 LINE -(X,Y),T
170 NEXT I
180 REM MERIDIANS
190 GOSUB 210
200 GOTO 230
210 D=D+C,D=D-4*INT (D/4):COLOR D
220 RETURN
230 T=9,J=SIN B,K=COS B,H=SIN A,M=COS A
240 IF G=0GOTO 470
250 FOR O=GTO 360STEP G
260 FOR L=90TO -90STEP -P
270 GOSUB 290
280 GOTO 370
290 Q=SIN (O-F),E=COS (O-F),U=COS L,W=SIN L
300 ON VGOTO 310,330
310 Z=R*(W*H*K-U*Q*J+U*E*M*K)
320 IF Z<0LET T=9
330 X=R*(U*Q*K+W*H*J+U*E*M*J)
340 Y=R*(W*M-U*E*H)
350 LINE -(X,Y),T
360 T=0:RETURN
370 NEXT L
380 T=9:NEXT O
390 REM PARALLELS
400 GOSUB 210
410 FOR L=-90+GTO 90-GSTEP G
420 FOR O=0TO 360STEP P
430 GOSUB 290
440 NEXT O
450 T=9:NEXT L
460 REM CONTINENTS
470 RESTORE 580:GOSUB 210
480 READ N,L,O
490 ON ERROR GOTO 560
500 GOSUB 290
510 FOR I=2TO N
520 READ L,O:T=0
530 GOSUB 290
540 NEXT I
550 T=9:GOTO 480
560 GLCURSOR (-2*R,-1.8*R):TEXT :CSIZE 1:LPRINT TIME :END
570 REM EUROPE
580 DATA 218,41,29,42,35,41,38,42.5,42.3,46,37,48,39,46.5,35,46,37,44.3,34,45.5
590 DATA 32,46.2,33.5,47,31,42.5,27,41,29,40.8,23,38,24,36.5,22.8,40.5,19.5,42,19.5
600 DATA 45.7,13.7,45.5,12.3,44.4,12.3,43.6,13.6,42.5,14.1,40,18.5,40.5,17,39.7
610 DATA 16.5,39,17.2,38,15.6,38,12.5,36.6,15,38.9,16.1,40,15.7,41.3,13
620 DATA 43,10.5,44.3,8.9,43.2,6.2,43.5,4,42.7,3,41.8,3.3,39.5,-.4,38.7,.3,36.6
630 DATA -2.1,36.5,-4.8,36,-5.4,37.1,-6.7,37,-8.8,38.6,-8.8,38.6,-9.4,41.2,-8.6
640 DATA 43.1,-9.3,43.7,-7.7,43.3,-1.5
650 DATA 46.1,-1.2,47.3,-2.5,48,-4.7,48.6,-4.7,48.8,-3.1
660 DATA 48.7,-1.7,49.8,-2,49.8,-1.3,49.4,-1.1,49.3,-.1,49.7,.2,50.2,1.5,50.9
670 DATA 1.6,51.4,3.6,53.3,4.7,54,8.3,57,8.1,57.6,10.7,56.4,11.9,54.5,10,54,14.2
680 DATA 55,20,59,22,60,30,60.6,28,60,22,63,21,65.6,26,66,22,61,17,60,19,56,16
690 DATA 55.4,13,59,10.3,58,7.6,58.5,6,62.5,5.5,64,10,70.3,19,71.2,27,67.8,41.5
700 REM ASIA
710 DATA 66.5,39,67.2,33,64.5,35,64,40,68.2,44,69,67,72,70,77,112,74,110,72,130,70
720 DATA 175,67,190,66,177,63,180,60,170,60,163,55,162,51,157,57,156,62,163,62
730 DATA 157,59,153,59,143,55,135,54,141,48,140,39,128,35,129.5,34,126,39,125.5
740 DATA 41,121,38.5,118,30,122,23,117,21,110,22,108,19,105.5,14.5,109,11.5,109,8
750 DATA 105,13,100.5,9,99,5,103.5,1,104,4,101,9,98,17,97,23,92,15,80,10,80,8,77
760 DATA 12,74.5,21,72,25,67,25,56,30,50,29.5,49,24,53,25,56,24,56,23,60,17,56,12.5
770 REM AFRICA
780 DATA 44,28,35,28,33,15,40,10.5,45,12,51.4,4,47.7,-5,39,-16,41,-20,35,-25,35,-26
790 DATA 33,-29,32,-34,26,-35,20,-18,12
800 DATA -11,14,-1,9,3,10,4.6,8.4,4.3,5.9,6.5,4.3,4.8,-2,4.6,-7.7,7.8,-12.9,9.6
810 DATA -13.4,12.4,-16.7,14.9,-17.6,17.2,-16.1,21.3,-17.2,28,-12.9,30.3,-9.5,31
820 DATA -9.8,32,-9.8,33.3,-8.3
830 DATA 33.9,-6.9,35.8,-6,35.9,-5.4,35.2,-4.7,35,-2,36.4,1,37.3,10.2
840 DATA 36.7,10.4,37,11,36.1,10.5,35.2,11.1,34,10,32.8,12.5,32.94,13.2,32.4,15.3
850 DATA 31.5,15.6,30,19,31,20,32,19.7,33,22,31
860 DATA 29,31.6,31,31.2,33.5,37,36,37,28,40,26,41,29
870 DATA 19,58.5,-5,58.2,-1.8,56,-3.3,56,-2,53,.5,53,1.6
880 DATA 52.2,1.7,51.3,.8,51.3,1.5,50.9,1,50,-5.8,51.4,-3.7
890 DATA 51.7,-5,53.3,-4.5,53.3,-3,55,-3.5,54.7,-5,57.5,-6.5,58.5,-5
900 REM ISLANDS
910 DATA 5,55.3,-6.5,54.3,-10,51.4,-10,52.2,-6.3,55.3,-6.5
920 DATA 7,66.5,-22.5,65.4,-24.5,66.6,-16,65,-13.5,63,-19,64,-22,66.5,-22.5
930 DATA 10,43,9.4,42.4,8.5,41.5,8.8,40.9,9.8,39.1,9.7,38.9,8.4,40.8,8.4,41.3,9.2
940 DATA 42.1,9.6,43,9.4
950 DATA 6,-13,49,-17,44,-25,44,-25,47,-15,50.5,-13,49
960 DATA 12,60,-44,65,-40,70,-22,82,-15,83.6,-30,78.5,-73,76,-68,75.6,-59,70,-51,66
970 DATA -53.5,61,-48,60,-44
980 REM AMERICA
990 DATA 84,63,-77,52,-56,50,-65,46,-64,43.7,-70.4,41.5,-70.7,40.6,-74,37,-76
1000 DATA 35.2,-75.7,31,-81.6,27,-80,25,-80.5,28,-82.7,29,-82.5,30,-84,30.3,-89,29
1010 DATA -90,29.7,-94,27,-97.5,22,-97.7,19,-96,18.4,-94,19,-91,21,-90,21.6,-87,16
1020 DATA -89,15.6,-83,10.5,-83.5,9,-81.5,9.7,-79,8,-77,11,-75,12,-71,10.6,-63,4
1030 DATA -52,0,-50,-6,-34,-12,-39,-22,-41,-25,-48,-28,-48,-41,-63,-51,-69,-55,-65
1040 DATA -55,-70,-50,-76,-37,-74,-18,-70,-6,-81,0,-81,6.6,-77.5,9,-79,7,-81,9.5
1050 DATA -85,13,-88,14,-91.5,16.2,-95,15.7,-96.6,19.6,-106,22,-105.7,29,-112.4
1060 DATA 31.3,-113,31.6,-115,30,-114.6,23,-109.5,25,-112.3,30,-115.9,34,-118.5
1070 DATA 34.5,-120.7,39,-124,43,-124.5,48.5,-124.5,59,-138,61,-148,54,-165,59
1080 DATA -158,62,-166,68,-167,71,-157,68,-110,70,-82,60,-95,54,-80,63,-77
1090 REM PACIFIC
1100 DATA 32,-10.5,142.4,-17.5,141,-15,135.5,-12,137,-11,132,-15,129,-14,127,-20
1110 DATA 120,-22,114,-26,113,-32,116,-34.5,115,-35.2,118,-31.5,130,-32.5,133.5,-35
1120 DATA 135.5,-33,137.8,-35.2,137.5,-38,144.4,-39,143.4,-37.8,145,-39.2,146
1130 DATA -37.5,150,-34,151,-32.7,152.7,-29,153.6,-25.6,153,-21,148.4,-18.8,146.3
1140 DATA -14.5,144.7,-14.7,144,-10.5,142.4
1150 DATA 29,-63,-56,-64,-60,-66,-65,-73,-75,-73,-85,-73,-100,-75,-100,-73,-125
1160 DATA -75,-137,-78,-165,-77.6,164,-72,170,-68,155,-66,135,-66,115,-66,90,-69.5
1170 DATA 75,-68,70,-66,55,-69,40,-70,20,-70,0,-71,-10,-74,-20,-78,-35,-75,-60,-67
1180 DATA -61,-64.3,-59,-63,-55
1190 DATA 7,9.7,80,7,82,6.5,81.8,6.3,80.5,6.4,80,8,79.7,9.7,80
1200 DATA 22,45.5,141.8,43.3,145.7,42,143,42.6,141.6,40.6,140,38.2,139.6,37,136.9
1210 DATA 35.6,135.7,35.6,133,34,130.9,32.9,132,31.4,131.3,31.2,130.2,33.3,129.7
1220 DATA 34,130.9,34.5,135,33.5,135.7,36,140.6,39.8,142,42.5,139.7,43.5,141.4,45.5
1230 DATA 141.8,11,6,95,1.7,98.8,-3.2,101.6,-5.9,105.7,-6.6,114.2,-8.6,114.5
1240 DATA -7.1,105.6,-2.9,105.9,.4,103.6,5,97.5,6,95,6,1.9,109.3,7,116.9,5,119.3,-4
1250 DATA 116,-2.9,110.3,1.9,109.3,11,0,130,-2.5,141,-6.5,148,-6.8,146.8,-10.7,151
1260 DATA -7.7,144.3,-9.3,143,-8,138.4,-5.4,138.1,-4,133.1,0,130,14,-34.5,172.7
1270 DATA -36.7,175.9,-37.5,176,-38,177.3,-37.4,178.5,-41.6,175.5,-40.6,172.5
1280 DATA -42.8,171,-46,166.2,-46.7,169.4,-40.2,175.3,-39.3,174,-37.7,174.8,-34.5
1290 DATA 172.7