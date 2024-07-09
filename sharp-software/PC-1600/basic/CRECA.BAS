10 CLS :CURSOR 8,0:PRINT "Anleitung:":WAIT :CURSOR 7,2:PRINT "Press Enter!":CURSOR 0,0
20 PRINT "Der Zauberer Mycore hat   Dich in sein Schloss der  Kreaturen verbannt."
30 CURSOR 0,0
40 PRINT "Aber Du hast die mglich- keit zu entkommen.Eseinen     Schlssel der dich
70 CURSOR 0,0
80 PRINT "in die Freiheit fhren    wird.Hierzu hast Du folge-ne Befehle zur Wahl:"
90 CLS :WAIT :CURSOR 0,0
100 PRINT "-NIMM--UNTERSUCHE--SIEHE---SCHIESSE--OEFFNE-BENUTZE---HILF---INVENTAR------
110 CURSOR 0,0
120 CURSOR 0,0:WAIT 0:PRINT "Weiterhin gibt es die     Richtungen:               ":WAIT
130 CURSOR 0,2:PRINT "(N) - (S) - (O) - (W)     (H)=Hoch       (R)=Runter"
140 CURSOR 0,0:WAIT 0
150 PRINT "Hier noch ein kleiner Tip!Willst Du Sachen untersu- chen,nimm sie vorher "
160 WAIT :CURSOR 0,3:PRINT "wenn Du kannst !         "
170 CLS :WAIT 0:CURSOR 3,0:PRINT "Und nun viel Glck !"
180 CURSOR 0,1:PRINT "Umgeschrieben fr PC-1600       von H.Richter":WAIT
190 CURSOR 7,3:PRINT "Press Enter!":CLS
200 LOAD "X:CRECA1",R
