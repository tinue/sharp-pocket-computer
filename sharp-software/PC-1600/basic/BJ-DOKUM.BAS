2 CLEAR
4 GRAPH :GLCURSOR (83,0)
6 LPRINT "Programm- und Bedienungsanleitung zum 'Blackjack - Profitrainer'";
8 GRAPH :GLCURSOR (84,0)
10 LPRINT "Programm- und Bedienungsanleitung zum 'Blackjack - Profitrainer'";
12 GRAPH :GLCURSOR (85,0)
14 LPRINT "Programm- und Bedienungsanleitung zum 'Blackjack - Profitrainer'":TEXT :LF 2
16 LPRINT TAB (7);"Dieses Spiel wird mit mindestens 4 Kartenspielen  52 Karten gespie";
18 LPRINT "lt."
20 LPRINT TAB (7);"Der Spieler spielt gegen die Bank mit dem Ziel, Karten bis 21, oder"
22 LPRINT TAB (7);"so nahe wie mglich an 21 zu ziehen.":LF 1
24 LPRINT TAB (7);"Nach Ttigung der Einstze gibt der Croupier die Karten offen aus"
26 LPRINT TAB (7);"-zuerst eine an jeden Spieler (im Uhrzeigersinn), zuletzt sich se";
28 LPRINT "lbst"
30 LPRINT TAB (7);"eine. Danach erhlt jeder Spieler -ebenfalls offen- eine zweite Kar";
32 LPRINT "te."
34 LPRINT TAB (7);"Asse zhlen 1(eins) oder 11(elf), Bilder zhlen 10(zehn), alle ander";
36 LPRINT "en"
38 LPRINT TAB (7);"Karten zhlen ihren normalen Wert.":LF 1
40 LPRINT TAB (7);"Hat der Spieler Blackjack (ein As und eine 10, oder ein As und ein"
42 LPRINT TAB (7);"Bild), so ist dies die hchste Paarung, die 3:2 ausbezahlt wird und"
44 LPRINT TAB (7);"nur von einem Blackjack des Croupiers ausgeglichen werden kann (sta";
46 LPRINT "nd-"
48 LPRINT TAB (7);"off, en carte, galit). Dies bedeutet, da";:LPRINT CHR$ (&E1);
50 LPRINT " der Satz weder verliert"
52 LPRINT TAB (7);"noch gewinnt. Kommt kein Blackjack zustande, versucht der Spieler, so"
54 LPRINT TAB (7);"nahe wie mglich an 21 heranzukommen, indem er mit der Erklrung 'Ka";
56 LPRINT "rte'"
58 LPRINT TAB (7);"beliebig viele Karten vom Croupier fordert. Mit dem Ausdruck 'danke'"
60 LPRINT TAB (7);"oder 'nein' gibt er zu verstehen, da";:LPRINT CHR$ (&E1);
62 LPRINT " er keine weiteren Karten wnscht."
64 LPRINT TAB (7);"Haben alle Spieler sich erklrt, zieht der Croupier seine Karten."
66 LF 1
68 GRAPH :GLCURSOR (94,0)
70 LPRINT "Der Croupier mu";:LPRINT CHR$ (&E1);
72 LPRINT " bis 16 ziehen und darf ab 17 keine Karte mehr ziehen!";
74 GRAPH :GLCURSOR (95,0)
76 LPRINT "Der Croupier mu";:LPRINT CHR$ (&E1);
78 LPRINT " bis 16 ziehen und darf ab 17 keine Karte mehr ziehen!";
80 TEXT :LF 2
82 LPRINT TAB (7);"Ist der Spieler nher an 21 als der Croupier, gewinnt er die Hhe se";
84 LPRINT "ines"
86 LPRINT TAB (7);"Einsatzes. Ist der Gesamtwert seiner Karten niedriger als der des Cr";
88 LPRINT "ou-"
90 LPRINT TAB (7);"piers, verliert er seinen Einsatz. Ist der Gesamtwert der Karten des"
92 LPRINT TAB (7);"Spielers identisch mit dem Wert der Karten des Crupiers (stand-off),"
94 LPRINT TAB (7);"kann der Satz, der weder gewinnt noch verliert, stehenbleiben, zurc";
96 LPRINT "kge-"
98 LPRINT TAB (7);"zogen oder verndert werden.":LF 1
100 LPRINT TAB (7);"berschreitet der Spieler whrend des Ziehens den Gesamtwert 21, ver";
102 LPRINT "liert"
104 LPRINT TAB (7);"er sofort ohne Rcksicht auf die Karten des Croupiers.":LF 1
106 GRAPH :GLCURSOR (83,0)
108 LPRINT "Verdoppeln.....";
110 GRAPH :GLCURSOR (84,0)
112 LPRINT "Verdoppeln.....";:TEXT
114 LPRINT TAB (23);"Ergeben die ersten beiden Karten des Spielers den Ge-"
116 LPRINT TAB (7);"samtwert 9, 10 oder 11, kann er seinen ursprnglichen Einsatz";
118 LPRINT " verdop-"
120 LPRINT TAB (7);"peln, erhlt aber nur noch eine zustzliche Karte.":LF 1
122 GRAPH :GLCURSOR (83,0)
124 LPRINT "Teilen (split).....";
126 GRAPH :GLCURSOR (84,0)
128 LPRINT "Teilen (split).....";:TEXT
130 LPRINT TAB (27);"Sind die beiden ersten Karten, die der Spieler er-"
132 LPRINT TAB (7);"hlt, gleichwertig, kann er sie teilen. Er mu";:LPRINT CHR$ (&E1);
134 LPRINT " dann die gleiche Hhe sei-"
136 LPRINT TAB (7);"nes Einsatzes an die geteilte 'Hand' setzen und erhlt fr jede";
138 LPRINT " der bei-"
140 LPRINT TAB (7);"den 'Hnde' beliebig viele Karten. Sind nach einer Teilung wiederum";
142 LPRINT " die"
144 LPRINT TAB (7);"ersten beiden Karten gleichwertig, so kann sie der Spieler erneut";
146 LPRINT " tei-"
148 LPRINT TAB (7);"len. Teilt der Spieler zwei Asse, erhlt er auf jedes As nur eine";
150 LPRINT " zustz-"
152 LPRINT TAB (7);"liche Karte. Im Falle einer Teilung ergeben ein As mit einer 10";
154 LPRINT " oder"
156 LPRINT TAB (7);"einem Bild nicht Blackjack, sondern 21. Auch bei einer Teilung kann";
158 LPRINT " ent-"
160 LPRINT TAB (7);"sprechend verdoppelt werden.":LF 1
162 GRAPH :GLCURSOR (83,0)
164 LPRINT "Versicherung.....";
166 GRAPH :GLCURSOR (84,0)
168 LPRINT "Versicherung.....";:TEXT
170 LPRINT TAB (25);"Der Spieler kann sich gegen einen Blackjack des Crou-"
172 LPRINT TAB (7);"piers versichern, d. h. hat der Croupier mit seiner ersten Karte ein";
174 LPRINT " As,"
176 LPRINT TAB (7);"so kann der Spieler die Hlfte seines ursprnglichen Einsatzes auf";
178 LPRINT " die"
180 LPRINT TAB (7);"Insurance-Linie setzen. Hat der Croupier dann einen Blackjack, wird";
182 LPRINT " die-":LF 1:LPRINT TAB (41);"-1-":LF 10
184 CLS :WAIT 0:PRINT "Bitte ein neues Blatt ein-"
186 PRINT "spannen und anschlie";:PRINT CHR$ (&E1);"end"
188 PRINT "die ENTER-Taste drcken ..":WAIT :INPUT ".............. ";A$
190 CLS
192 LPRINT TAB (7);"ser Einsatz 2:1 ausbezahlt, hat der Croupier keinen Blackjack, ist";
194 LPRINT " die-"
196 LPRINT TAB (7);"ser Einsatz verloren.":LF 2
198 LPRINT TAB (17);"- - - - - - - - - - - - - - - - - - - - - - - - - -":LF 2
200 LPRINT TAB (7);"So oder hnlich lauten die in allen europischen Casinos ausliegen";
202 LPRINT "den"
204 LPRINT TAB (7);"Spielregeln fr das sich einer steigenden Beliebtheit erfreuende";
206 LPRINT " Black-"
208 LPRINT TAB (7);"jack-Spiel. Man mag es kaum glauben, da";:LPRINT CHR$ (&E1);
210 LPRINT " sich in den wenigen Spielregeln"
212 LPRINT TAB (7);"ein Variantenreichtum niederschlgt, der in der Vergangenheit Lese";
214 LPRINT "stoff"
216 LPRINT TAB (7);"fr zahlreiche Fachbcher geliefert hat und weitere Publikationen";
218 LPRINT " wer-"
220 LPRINT TAB (7);"den folgen.":LF 1
222 LPRINT TAB (7);"Was ist das Besondere am Blackjack-Spiel? Weil die immerhin 208 bis"
224 LPRINT TAB (7);"maximal 312 Spielkarten in der Spielbank vom Croupier aus einem Kar";
226 LPRINT "ten-"
228 LPRINT TAB (7);"schlitten herausgezogen und an die Spieler ausgeteilt werden, danach"
230 LPRINT TAB (7);"aber nicht wieder in den Kartenschlitten zurckwandern, sondern auf";
232 LPRINT " einer"
234 LPRINT TAB (7);"Ablage bis zum nchsten Mischen verweilen mssen, besitzen sie gewi";
236 LPRINT "sser-"
238 LPRINT TAB (7);"ma";:LPRINT CHR$ (&E1);:LPRINT "en ein Gedchtnis. Folglich ist daher ";
240 LPRINT "jedes einzelne Blackjackspiel"
242 LPRINT TAB (7);"geringfgig vom vorherigen abhngig. Geht man beispielsweise davon";
244 LPRINT " aus,":LPRINT TAB (7);"da";:LPRINT CHR$ (&E1);
246 LPRINT " in den ersten Spielen eines Kartenschlittens verhltnism";
248 LPRINT CHR$ (&E1);:LPRINT "ig wenig"
250 LPRINT TAB (7);"hohe Karten gezogen wurden, so hat der Spieler in den kommenden Sp";
252 LPRINT "ielen"
254 LPRINT TAB (7);"einen gewissen Vorteil, hauptschlich deshalb, weil mehr Blackjack";
256 LPRINT "s er-"
258 LPRINT TAB (7);"wartet werden knnen, was die hchste Kartenkombination mit der lu";
260 LPRINT "kra-"
262 LPRINT TAB (7);"tivsten Gewinnauszahlung darstellt.":LF 1
264 LPRINT TAB (7);"Derartige Schwankungen in der Kartenverteilung kann man systematis";
266 LPRINT "ch aus-"
268 LPRINT TAB (7);"nutzen und sie erbringen -gem. den Aussagen vieler Fachbcher- geg";
270 LPRINT "enber"
272 LPRINT TAB (7);"der Bank einen Vorteil, der bis zu 1,5 % betragen kann.":LF 1
274 LPRINT TAB (7);"Natrlich begann auch der Autor dieses Programmes zunchst mit dem";
276 LPRINT " Lesen"
278 LPRINT TAB (7);"der einschlgigen Blackjack-Fachliteratur. Er erkannte jedoch rech";
280 LPRINT "t bald"
282 LPRINT TAB (7);"die Notwendigkeit, das in den Bchern zum Ausdruck gebrachte Fachw";
284 LPRINT "issen"
286 LPRINT TAB (7);"anhand von Tests nher zu untersuchen. Also erstellte der Autor zu";
288 LPRINT "nchst"
290 LPRINT TAB (7);"auf einem Personalcomputer ein entsprechendes Analyseprogramm. Dam";
292 LPRINT "it war"
294 LPRINT TAB (7);"war es ihm mglich, die einzelnen Expertenaussagen in diversen Tes";
296 LPRINT "trei-"
298 LPRINT TAB (7);"hen zu untersuchen. Es war stets sein Bestreben, dabei die echten ";
300 LPRINT "Casi-"
302 LPRINT TAB (7);"nobedingungen nicht zu vernachlssigen. Es gibt Leute, die haben C";
304 LPRINT "ompu-"
306 LPRINT TAB (7);"tersimulationen durchgefhrt, bei denen meistens nur das Verhltnis";
308 LPRINT " 'ein"
310 LPRINT TAB (7);"Spieler gegen die Bank' bercksichtigt wurde. Dagegen ist eigentlich"
312 LPRINT TAB (7);"kein Einwand zu erheben, solange nicht die Behauptung vertreten wird,"
314 LPRINT TAB (7);"da";:LPRINT CHR$ (&E1);
316 LPRINT " diese Ergebnisse im Casinobetrieb auch herauskommen wrden. Der Autor"
318 LPRINT TAB (7);"hat bei seinen Untersuchungen jedoch festgestellt, da";
320 LPRINT CHR$ (&E1);:LPRINT " man Computertests"
322 LPRINT TAB (7);"nur unter praxisnahen Bedingungen durchfhren darf. Beispielsweise";
324 LPRINT " wird"
326 LPRINT TAB (7);"in Europa nie ein Blackjacktisch mit nur einem Spieler (Box) betri";
328 LPRINT "eben."
330 LPRINT TAB (7);"Also hat er seine Tests gleichzeitig auf alle 7 Boxen eines Tische";
332 LPRINT "s aus-"
334 LPRINT TAB (7);"gelegt. Er konnte auch whlen, mit wievielen Kartendecks im Schlit";
336 LPRINT "ten"
338 LPRINT TAB (7);"gespielt wird. Weiterhin hat er auch das Abstechen der Karten nach";
340 LPRINT " dem"
342 LPRINT TAB (7);"Mischen variieren knnen. Es war ihm auch mit seinem Analyseprogra";
344 LPRINT "mm mg-"
346 LPRINT TAB (7);"lich, die unter Fachkundigen gelufigen sog. Basisspielregeln zu m";
348 LPRINT "odifi-"
350 LPRINT TAB (7);"zieren. Bei allen derart durchgefhrten Untersuchungen kann der Au";
352 LPRINT "tor mit"
354 LPRINT TAB (7);"Gewi";:LPRINT CHR$ (&E1);:LPRINT "heit seine Meinung vertreten, da";
356 LPRINT CHR$ (&E1);:LPRINT " die bisherigen Aussagen der Fach-"
358 LPRINT TAB (7);"welt nicht in allen Punkten als richtig betrachtet werden drfen."
360 LF 1:LPRINT TAB (41);"-2-":LF 10
362 CLS :WAIT 0:PRINT "Bitte ein neues Blatt ein-"
364 PRINT "spannen und anschlie";:PRINT CHR$ (&E1);"end"
366 PRINT "die ENTER-Taste drcken ..":WAIT :INPUT ".............. ";A$
368 CLS
370 LPRINT TAB (7);"Eine konsequente Weiterentwicklung der vorgenannten Testaktivitten";
372 LPRINT " fand"
374 LPRINT TAB (7);"in der Umarbeitung des Personalcomputer-Bj.-Analyseprogrammes zu ei";
376 LPRINT "nem"
378 LPRINT TAB (7);"nach den Regeln aller gewonnenen Erkenntnisse arbeitenden Trainings";
380 LPRINT "-,"
382 LPRINT TAB (7);"oder auch als interessanten Zeitvertreib gedachten Spielprogrammes ";
384 LPRINT "seinen"
386 LPRINT TAB (7);"Abschlu";:LPRINT CHR$ (&E1);:LPRINT ".";
388 LPRINT " Dazu war der SHARP PC-1600 wegen seinen ausgezeichneten Grafik-"
390 LPRINT TAB (7);"mglichkeiten bestens geeignet.":LF 1
392 LPRINT TAB (7);"Der Blackjack-Profitrainer bietet als entspannendes bungsprogramm";
394 LPRINT " dem"
396 LPRINT TAB (7);"ernsthaften Glckspielinteressierten die Mglichkeit, sein Knnen";
398 LPRINT " unter"
400 LPRINT TAB (7);"casinopraxisnahen Bedingungen zu optimieren. Nachstehend die wich";
402 LPRINT "tigsten"
404 LPRINT TAB (7);"Angaben zur Leistungsbreite:":LF 1
406 LPRINT TAB (7);"Wahlweises Spiel mit 1 bis 6 Kartendecks.":LF 1
408 LPRINT TAB (7);"Bestimmung eines Toleranzbereiches fr das Abschneiden der Karten";
410 LPRINT "ausgabe."
412 LPRINT TAB (7);"Dort wird mittels Zufallsgenerator die sog. Trennkarte eingefgt."
414 LF 1
416 LPRINT TAB (7);"Angabe der Anzeigedauer einzelner Dialoge. Das ist sehr wichtig ";
418 LPRINT "fr das"
420 LPRINT TAB (7);"ben der Zhltechnik zur Anpassung an die Schnelligkeit im Casino."
422 LF 1
424 LPRINT TAB (7);"Wahlweise nach jedem Spieldurchgang Abruf des Counts um eine vergl";
426 LPRINT "eichs-"
428 LPRINT TAB (7);"mglichkeit mit dem eigenen Zhlstand zu erhalten.":LF 1
430 LPRINT TAB (7);"Eigenes Mitspielen in mehreren, oder in allen 7 Boxen. Boxen,";
432 LPRINT " die man"
434 LPRINT TAB (7);"nicht selbst bespielt, spielt das Programm selbst nach den Basisre";
436 LPRINT "geln.":LF 1
438 LPRINT TAB (7);"Wahlweise kann bestimmt werden, da";:LPRINT CHR$ (&E1);
440 LPRINT " eine, oder mehrere Boxen einmal nicht"
442 LPRINT TAB (7);"bespielt werden. (Simulation schwacher Tischfrequentierung zur Beob";
444 LPRINT "ach-"
446 LPRINT TAB (7);"tung von vernderten Kartenausgabeeinflssen auf die restlichen";
448 LPRINT " Spieler.)":LF 1
450 LPRINT TAB (7);"Programmiertes Automatikspiel ohne eigenes Engagement. Es ist sehr";
452 LPRINT " auf-"
454 LPRINT TAB (7);"schlu";:LPRINT CHR$ (&E1);:LPRINT "reich, auf diese Weise einmal den";
456 LPRINT " Computer ber eine lngere Zeit"
458 LPRINT TAB (7);"(etwa 1000 Schlitten = 9000 Spiele) alle 7 Boxen selbst spielen zu";
460 LPRINT " las-"
462 LPRINT TAB (7);"sen und dabei beobachten zu knnen, wie sich die Basisspielregeln";
464 LPRINT " im Sal-"
466 LPRINT TAB (7);"denverlauf je Box und insgesamt auswirken. Man erkennt dabei sehr";
468 LPRINT " gut,"
470 LPRINT TAB (7);"ob die Spieler im sog. Pluscount wirklich erhhte Gewinnchancen ge";
472 LPRINT "genber"
474 LPRINT TAB (7);"der Bank haben.":LF 1
476 LPRINT TAB (7);"Alle diejenigen, die Blackjack nur vom Hrensagen her kennen, kn";
478 LPRINT "nen wh-"
480 LPRINT TAB (7);"rend der Beschftigung mit dem Programm die vielseitigen Spielsit";
482 LPRINT "uationen"
484 LPRINT TAB (7);"kennenlernen und erfahren dabei die unterschiedlichen Verhaltensw";
486 LPRINT "eisen."
488 LPRINT TAB (7);"Weiterhin wird ihnen, sollte einmal der Wunsch nach einem Casinobe";
490 LPRINT "such"
492 LPRINT TAB (7);"erwachen, ein gewisser Teil evtl. vorhandener Schwellenangst ";
494 LPRINT "genommen.":LF 1
496 LPRINT TAB (7);"Nachdem das Programm in den Computer geladen ist, kann es mit der";
498 LPRINT " Anwei-"
500 LPRINT TAB (7);"sung  'RUN'  und nachfolgendes Bettigen der  'ENTER-Taste' gesta";
502 LPRINT "rtet"
504 LPRINT TAB (7);"werden.":LF 1
506 LPRINT TAB (7);"Zu Beginn erfolgt die Beantwortung einiger Ablaufsteuerungsangaben:"
508 LF 1:GRAPH :GLCURSOR (83,0)
510 LPRINT "ANZAHL KARTENDECKS (1-6)";
512 GRAPH :GLCURSOR (84,0)
514 LPRINT "ANZAHL KARTENDECKS (1-6)";:TEXT :LF 2
516 LPRINT TAB (7);"Weil in den Casinos mit unterschiedlich vielen Decks gespielt wird,";
518 LPRINT " kann"
520 LF 1:LPRINT TAB (41);"-3-":LF 10
522 CLS :WAIT 0:PRINT "Bitte ein neues Blatt ein-"
524 PRINT "spannen und anschlie";:PRINT CHR$ (&E1);"end"
526 PRINT "die ENTER-Taste drcken ..":WAIT :INPUT ".............. ";A$
528 CLS
530 LPRINT TAB (7);"hier bestimmt werden, wieviele Decks der Computer nehmen soll. 4= v";
532 LPRINT "ier"
534 LPRINT TAB (7);"Decks, 6= sechs Decks usw. Also eine Zahl von 1-6 eintippen und dan";
536 LPRINT "ach"
538 LPRINT TAB (7);"die ENTER-Taste drcken.":LF 1
540 LPRINT TAB (7);"Beantwortung der Frage";
542 GRAPH :GLCURSOR (384,0)
544 LPRINT "ABSTECHUNG VON KARTE=";
546 GRAPH :GLCURSOR (385,0)
548 LPRINT "ABSTECHUNG VON KARTE=":TEXT :LF 2
550 LPRINT TAB (7);"Hiermit kann bestimmt werden, nach wieviel Karten frhestens die Tr";
552 LPRINT "enn-"
554 LPRINT TAB (7);"karte liegen sollte. Empfehlung, und heute gelufig bei 6 Decks ab";
556 LPRINT " Kar-"
558 LPRINT TAB (7);"te 193. Also 193 eintippen und danach die ENTER-Taste drcken. ";
560 LPRINT "Natrlich"
562 LPRINT TAB (7);"kann auch ein anderer Wert eingegeben werden.":LF 1
564 LPRINT TAB (7);"Beantwortung der Frage";
566 GRAPH :GLCURSOR (384,0)
568 LPRINT "ABSTECHUNG BIS KARTE=";
570 GRAPH :GLCURSOR (385,0)
572 LPRINT "ABSTECHUNG BIS KARTE=":TEXT :LF 2
574 LPRINT TAB (7);"Hiermit kann bestimmt werden, nach wieviel Karten sptestens die";
576 LPRINT " Trenn-"
578 LPRINT TAB (7);"karte liegen sollte. Empfehlung bei 6 Decks bis Karte 222. Also";
580 LPRINT " 222 ein-"
582 LPRINT TAB (7);"tippen und danach die ENTER-Taste drcken.":LF 1
584 LPRINT TAB (7);"Bei jedem Kartenmischen ermittelt der Computer einen Wert zwisc";
586 LPRINT "hen AB-"
588 LPRINT TAB (7);"und BIS-Karte und fgt die Trennkarte an der entsprechenden Pos";
590 LPRINT "ition ein.":LF 1
592 LPRINT TAB (7);"Beantwortung der Frage";
594 GRAPH :GLCURSOR (384,0)
596 LPRINT "DISPLAYDAUER (1-20) ?";
598 GRAPH :GLCURSOR (385,0)
600 LPRINT "DISPLAYDAUER (1-20) ?":TEXT :LF 2
602 LPRINT TAB (7);"Hier kann die Anzeigedauer von Kartenkauf und Bankziehen und Sa";
604 LPRINT "ldenanzei-"
606 LPRINT TAB (7);"ge bestimmt werden. 1= krzeste und 20= lngste Anzeigedauer. E";
608 LPRINT "mpfehlung"
610 LPRINT TAB (7);"zu Beginn 8-12, also eine 8, oder 11 usw. eintippen und danach ";
612 LPRINT "die ENTER-"
614 LPRINT TAB (7);"Taste drcken. Diese Option ist wichtig beim Training der Karte";
616 LPRINT "nzhlbun-"
618 LPRINT TAB (7);"gen (Counten) fr das Praxisspiel.":LF 1
620 LPRINT TAB (7);"Beantwortung der Frage";
622 GRAPH :GLCURSOR (384,0)
624 LPRINT "AUTOMATIKSPIEL (J/N) ?";
626 GRAPH :GLCURSOR (385,0)
628 LPRINT "AUTOMATIKSPIEL (J/N) ?":TEXT :LF 2
630 LPRINT TAB (7);"Bei J spielt der Computer nach den Basisspielregeln alleine, be";
632 LPRINT "i N kann"
634 LPRINT TAB (7);"man selbst in einer, oder mehreren, ja sogar in allen 7 Boxen s";
636 LPRINT "pielen."
638 LPRINT TAB (7);"Empfehlung N eintippen und die ENTER-Taste drcken, weil man se";
640 LPRINT "lbst mit-"
642 LPRINT TAB (7);"spielen mchte. Interessant ist es aber auch, hier einmal den C";
644 LPRINT "omputer"
646 LPRINT TAB (7);"ber eine lngere Zeit (etwa 1000 Schlitten = ca. 9000-10000 Sp";
648 LPRINT "iele) alle"
650 LPRINT TAB (7);"7 Boxen selbst spielen zu lassen und dabei beobachten zu knnen";
652 LPRINT ", wie sich"
654 LPRINT TAB (7);"die Basisspielregeln im Saldenverlauf je Box und insgesamt ausw";
656 LPRINT "irken. Man"
658 LPRINT TAB (7);"erkennt dabei sehr gut, ob die Spieler im sog. Pluscount wirkli";
660 LPRINT "ch erhhte"
662 LPRINT TAB (7);"Gewinnchancen gegenber der Bank haben usw.":LF 1
664 LPRINT TAB (7);"Die Anzeige 'ERZEUGEN KARTENSCHLITTEN' braucht nur abgewartet z";
666 LPRINT "u werden."
668 LPRINT TAB (7);"Hier ist keine Eingabe erforderlich.":LF 1
670 LPRINT TAB (7);"Das gilt auch fr die nchste Anzeige 'DIE KARTEN WERDEN GEMISCH";
672 LPRINT "T'":LF 1
674 LPRINT TAB (7);"Beantwortung der Frage";
676 GRAPH :GLCURSOR (384,0)
678 LPRINT "BOXENAENDERUNGEN (J/N) ?";
680 GRAPH :GLCURSOR (385,0)
682 LPRINT "BOXENAENDERUNGEN (J/N) ?":TEXT :LF 2
684 LPRINT TAB (7);"Hier ist unbedingt zu Beginn ein J einzutippen. Das gilt auch fr";
686 LPRINT " die Op-"
688 LPRINT TAB (7);"tion Automatikspiel. Spter kann man hier simulieren, damit zwisch";
690 LPRINT "enzeit-"
692 LPRINT TAB (7);"lich auch einmal nicht alle Boxen besetzt sind. Dann werden auch ";
694 LPRINT "nur vom"
696 LPRINT TAB (7);"Computer, der ja der Dealer ist, die wirklich besetzten Boxen mit";
698 LPRINT " Karten"
700 LPRINT TAB (7);"bedient.":LF 1
702 LPRINT TAB (7);"Beantwortung der Frage";
704 GRAPH :GLCURSOR (384,0)
706 LPRINT "BOX 1 BESETZT (J/N/S)";
708 GRAPH :GLCURSOR (385,0)
710 LPRINT "BOX 1 BESETZT (J/N/S)":TEXT :LF 1
712 LF 1:LPRINT TAB (41);"-4-":LF 10
714 CLS :WAIT 0:PRINT "Bitte ein neues Blatt ein-"
716 PRINT "spannen und anschlie";:PRINT CHR$ (&E1);"end"
718 PRINT "die ENTER-Taste drcken ..":WAIT :INPUT ".............. ";A$
720 CLS
722 LPRINT TAB (7);"J= die Box ist besetzt, wird aber vom Computer nach Basisspielreg";
724 LPRINT "eln ge-"
726 LPRINT TAB (7);"spielt. N= die Box ist nicht besetzt und erhlt auch keine Karten";
728 LPRINT ". S= die"
730 LPRINT TAB (7);"Box wird vom Bediener selbst gespielt. Der Bediener trifft alle a";
732 LPRINT "nfallen-"
734 LPRINT TAB (7);"den Spielentscheidungen selbst. Der Computer gibt an solche Boxen";
736 LPRINT " nur die"
738 LPRINT TAB (7);"Karten aus, erfragt, wenn es die Kartenwerte zulassen, Splitt- un";
740 LPRINT "d Doppe-"
742 LPRINT TAB (7);"lungswnsche, sowie die Ausgabe weiterer Karten auf Anforderung d";
744 LPRINT "es Spie-"
746 LPRINT TAB (7);"lers, gerade so, wie eben auch im Casino der Croupier vorgehen w";
748 LPRINT "rde.":LF 1
750 LPRINT TAB (7);"Die vorgenannte Frage ist auch fr die Boxen 2-7 zu beantworten.":LF 1
752 LPRINT TAB (7);"Beantwortung der Frage";
754 GRAPH :GLCURSOR (384,0)
756 LPRINT "ZAEHLSTANDANZEIGE (J/N) ?";
758 GRAPH :GLCURSOR (385,0)
760 LPRINT "ZAEHLSTANDANZEIGE (J/N) ?":TEXT :LF 2
762 LPRINT TAB (7);"Hier kann zu Trainingszwecken nach jedem Spiel der Count abgefrag";
764 LPRINT "t wer-"
766 LPRINT TAB (7);"den, um die eigene Zhlung zu vergleichen.":LF 1
768 LPRINT TAB (7);"Beantwortung der Frage";
770 GRAPH :GLCURSOR (384,0)
772 LPRINT "BOX n WIEVIEL JETONS ?";
774 GRAPH :GLCURSOR (385,0)
776 LPRINT "BOX n WIEVIEL JETONS ?":TEXT :LF 2
778 LPRINT TAB (7);"Fr alle Boxen, die vom Bediener bespielt werden, erfragt das Pro";
780 LPRINT "gramm"
782 LPRINT TAB (7);"die Einstze in Stcken. Also hier eine Zahl zwischen 1 und 50 (e";
784 LPRINT "nt-"
786 LPRINT TAB (7);"spricht Minimum 10 und Maximum 500 DM) eintippen und danach die E";
788 LPRINT "NTER-"
790 LPRINT TAB (7);"Taste drcken. Boxen, die der Computer fiktiv mitspielt, spielen ";
792 LPRINT "grund-"
794 LPRINT TAB (7);"stzlich Masse-egale 1 Stck. Der selbst mitspielende Teilnehmer ";
796 LPRINT "hat hier"
798 LPRINT TAB (7);"aber die wunschgetreue Mglichkeit, auszuprobieren, ob er bei erh";
800 LPRINT "hten"
802 LPRINT TAB (7);"Einstzen in gnstigen Spielsituationen wirklich die von den dive";
804 LPRINT "rsen Ex-"
806 LPRINT TAB (7);"perten genannten berlegenheiten gegenber der Bank erreichen kan";
808 LPRINT "n.":LF 1
810 LPRINT TAB (7);"Das Spiel luft jetzt an. Alle folgenden Anzeigen und Abfragen si";
812 LPRINT "nd"
814 LPRINT TAB (7);"selbsterklrend, denn das Programm ist sehr bedienerfreundlich im";
816 LPRINT " Dialog-"
818 LPRINT TAB (7);"ablauf mit schnell zu begreifenden wenigen unterschiedlichen Funk";
820 LPRINT "tionen"
822 LPRINT TAB (7);"aufgebaut.":LF 1
824 LPRINT TAB (7);"Pro Spiel kommen die Fragen zur Boxennderung und Zhlstandanzeig";
826 LPRINT "e wie-"
828 LPRINT TAB (7);"der. Es gengt jetzt, wenn man nichts ndern will und auch den Co";
830 LPRINT "unt"
832 LPRINT TAB (7);"nicht sehen mchte, je Frage nur die ENTER-Taste zu drcken. Klar, d";
834 LPRINT "a";:LPRINT CHR$ (&E1)
836 LPRINT TAB (7);"dieses Programm auch im Falle eines As der Bank die Versicherung von";
838 LPRINT " den"
840 LPRINT TAB (7);"selbst spielenden Teilnehmern erfragt und im Saldenverlauf entsprech";
842 LPRINT "end"
844 LPRINT TAB (7);"bercksichtigt.":LF 1
846 GRAPH :GLCURSOR (94,0)
848 LPRINT "Erklrungen zu den im Programm eingearbeiteten Basis-Spielregeln";
850 GRAPH :GLCURSOR (95,0)
852 LPRINT "Erklrungen zu den im Programm eingearbeiteten Basis-Spielregeln"
854 TEXT :LF 2
856 LPRINT TAB (7);"Die diversen Verhaltensregeln, welche in den verschiedenen Fachbche";
858 LPRINT "rn"
860 LPRINT TAB (7);"behandelt werden, haben in den nachstehenden Erklrungen eine Vorgeh";
862 LPRINT "ens-"
864 LPRINT TAB (7);"weise erhalten, die nicht den Anspruch als alleinige Mglichkeit, er";
866 LPRINT "folg-"
868 LPRINT TAB (7);"reich Blackjack zu spielen, erheben. Der Betreiber des Programmes 'B";
870 LPRINT "lack-"
872 LPRINT TAB (7);"jack-Profitrainer' sollte die genannten Regelungen lediglich als ric";
874 LPRINT "h-"
876 LPRINT TAB (7);"tungweisend betrachten.":LF 1
878 GRAPH :GLCURSOR (83,0)
880 LPRINT "1. Zhltechnik (Counten)";
882 GRAPH :GLCURSOR (84,0)
884 LPRINT "1. Zhltechnik (Counten)":TEXT :LF 2
886 LPRINT TAB (7);"Die Kartenwerte 7, 8 und 9 werden nicht bercksichtigt. Fr jede Kar";
888 LPRINT "te"
890 LPRINT TAB (7);"mit den Werten 2, 3, 4, 5 und 6 wird +1 gezhlt. Asse und alle Bilde";
892 LPRINT "r"
894 LPRINT TAB (7);"einschl. 10 werden -1 gezhlt. Das Ergebnis dieser Zhlweise wird ";
896 LPRINT "nach-"
898 LPRINT TAB (7);"stehend als Count bezeichnet.":LF 1
900 LPRINT TAB (41);"-5-":LF 10
902 CLS :WAIT 0:PRINT "Bitte ein neues Blatt ein-"
904 PRINT "spannen und anschlie";:PRINT CHR$ (&E1);"end"
906 PRINT "die ENTER-Taste drcken ..":WAIT :INPUT ".............. ";A$
908 CLS
910 GRAPH :GLCURSOR (83,0)
912 LPRINT "2. As-Zhlbesonderheiten";
914 GRAPH :GLCURSOR (84,0)
916 LPRINT "2. As-Zhlbesonderheiten":TEXT :LF 2
918 LPRINT TAB (7);"Befindet sich in einer Box unter den ersten beiden Karten ein As, ";
920 LPRINT "und"
922 LPRINT TAB (7);"ist die Summe beider Karten 17 und darber, wird das As als 11 gew";
924 LPRINT "er-"
926 LPRINT TAB (7);"tet. Anderenfalls wird ein As mit 1 gezhlt und weitere Karten gez";
928 LPRINT "ogen.":LF 1
930 GRAPH :GLCURSOR (83,0)
932 LPRINT "3. Basisregeln fr das Kartenziehen";
934 GRAPH :GLCURSOR (84,0)
936 LPRINT "3. Basisregeln fr das Kartenziehen":TEXT :LF 2
938 LPRINT TAB (7);"Aufkarte der Bank ->  š 2 š 3 š 4 š 5 š 6 š 7 š 8 š 9 š 10š Asš"
940 LPRINT TAB (7);"                   ---š---š---š---š---š---š---š---š---š---š---š"
942 LPRINT TAB (7);"Kartensumme Box -> 12 š z š z š   š   š   š z š z š z š z š z š"
944 LPRINT TAB (7);"                   ---š---š---š---š---š---š---š---š---š---š---š"
946 LPRINT TAB (7);"                   13 š   š   š   š   š   š z š z š z š z š z š"
948 LPRINT TAB (7);"                   ---š---š---š---š---š---š---š---š---š---š---š"
950 LPRINT TAB (7);"                   14 š   š   š   š   š   š z š z š z š z š z š"
952 LPRINT TAB (7);"                   ---š---š---š---š---š---š---š---š---š---š---š"
954 LPRINT TAB (7);"                   15 š   š   š   š   š   š z š z š z š z š z š"
956 LPRINT TAB (7);"                   ---š---š---š---š---š---š---š---š---š---š---š"
958 LPRINT TAB (7);"                   16 š   š   š   š   š   š z š z š z š   š z š"
960 LPRINT TAB (7);"                   ---š---š---š---š---š---š---š---š---š---š---š"
962 LPRINT TAB (7);"                17-21 = nicht mehr ziehen. Jedoch ist eine Beson-"
964 LPRINT TAB (7);"                                           derheit im Falle eines"
966 LPRINT TAB (7);"                                           As und Gesamtwert 18"
968 LPRINT TAB (7);"                                           zu bercksichtigen."
970 LPRINT TAB (7);"z = ziehen weitere Karten                  Dann noch gegen eine"
972 LPRINT TAB (7);"                                           9, oder 10 der Bank"
974 LPRINT TAB (7);"                                           ziehen.":LF 2
976 GRAPH :GLCURSOR (83,0)
978 LPRINT "4. Doppel-Signale";
980 GRAPH :GLCURSOR (84,0)
982 LPRINT "4. Doppel-Signale":TEXT :LF 2
984 LPRINT TAB (7);"Aufkarte der Bank ->  š 2 š 3 š 4 š 5 š 6 š 7 š 8 š 9 š 10š Asš"
986 LPRINT TAB (7);"                   ---š---š---š---š---š---š---š---š---š---š---š"
988 LPRINT TAB (7);"Kartensumme Box ->  9 š d š d š d š d š d š   š   š   š   š   š"
990 LPRINT TAB (7);"                 / ---š---š---š---š---š---š---š---š---š---š---š"
992 LPRINT TAB (7);"                /  10 š d š d š d š d š d š d š d š d š   š   š"
994 LPRINT TAB (7);"d = doppeln    /   ---š---š---š---š---š---š---š---š---š---š---š"
996 LPRINT TAB (7);"              /    11 š d š d š d š d š d š d š d š d š d š   š"
998 LPRINT TAB (7);"             /     ---š---š---š---š---š---š---š---š---š---š---š"
1000 LPRINT TAB (7);"            / As+8= 9 š   š   š d š d š d š   š   š   š   š   š"
1002 LPRINT TAB (7);"           /       ---š---š---š---š---š---š---š---š---š---š---š"
1004 LPRINT TAB (7);"           š"
1006 LPRINT TAB (7);"           š       Bei einem Count von Null bzw. Minus gilt die"
1008 LPRINT TAB (7);"           š       nachstehende Besonderheit:"
1010 LPRINT TAB (7);"           š"
1012 LPRINT TAB (7);"           š<-----  9 š   š   š d š d š d š   š   š   š   š   š"
1014 LPRINT TAB (7);"                   ---š---š---š---š---š---š---š---š---š---š---š"
1016 LF 1
1018 LPRINT TAB (7);"                   As+8 zhlen bei einem Count von Null bzw."
1020 LPRINT TAB (7);"                   Minus immer als 19.":LF 5
1022 LPRINT TAB (41);"-6-":LF 10
1024 CLS :WAIT 0:PRINT "Bitte ein neues Blatt ein-"
1026 PRINT "spannen und anschlie";:PRINT CHR$ (&E1);"end"
1028 PRINT "die ENTER-Taste drcken ..":WAIT :INPUT ".............. ";A$
1030 CLS
1032 GRAPH :GLCURSOR (83,0)
1034 LPRINT "5. Splitt-Signale";
1036 GRAPH :GLCURSOR (84,0)
1038 LPRINT "5. Splitt-Signale":TEXT :LF 2
1040 LPRINT TAB (7);"Aufkarte der Bank ->  š 2 š 3 š 4 š 5 š 6 š 7 š 8 š 9 š 10š Asš"
1042 LPRINT TAB (7);"                   ---š---š---š---š---š---š---š---š---š---š---š"
1044 LPRINT TAB (7);"Kartensumme Box -> 2/2š   š   š s š s š s š s š   š   š   š   š"
1046 LPRINT TAB (7);"                   ---š---š---š---š---š---š---š---š---š---š---š"
1048 LPRINT TAB (7);"                   3/3š   š   š s š s š s š s š   š   š   š   š"
1050 LPRINT TAB (7);"                   ---š---š---š---š---š---š---š---š---š---š---š"
1052 LPRINT TAB (7);"                   4/4š   š   š   š   š   š   š   š   š   š   š"
1054 LPRINT TAB (7);"                   ---š---š---š---š---š---š---š---š---š---š---š"
1056 LPRINT TAB (7);"                   5/5š   š   š   š   š   š   š   š   š   š   š"
1058 LPRINT TAB (7);"                   ---š---š---š---š---š---š---š---š---š---š---š"
1060 LPRINT TAB (7);"s = splitten       6/6š   š s š s š s š s š   š   š   š   š   š"
1062 LPRINT TAB (7);"                   ---š---š---š---š---š---š---š---š---š---š---š"
1064 LPRINT TAB (7);"                   7/7š s š s š s š s š s š s š   š   š   š   š"
1066 LPRINT TAB (7);"                   ---š---š---š---š---š---š---š---š---š---š---š"
1068 LPRINT TAB (7);"                   8/8š s š s š s š s š s š s š s š s š s š s š"
1070 LPRINT TAB (7);"                   ---š---š---š---š---š---š---š---š---š---š---š"
1072 LPRINT TAB (7);"                   9/9š s š s š s š s š s š   š s š s š   š   š"
1074 LPRINT TAB (7);"                 -----š---š---š---š---š---š---š---š---š---š---š"
1076 LPRINT TAB (7);"                 10/10š   š   š   š   š   š   š   š   š   š   š"
1078 LPRINT TAB (7);"                 -----š---š---š---š---š---š---š---š---š---š---š"
1080 LPRINT TAB (7);"                 As/Asš s š s š s š s š s š s š s š s š s š s š"
1082 LPRINT TAB (7);"                 -----š---š---š---š---š---š---š---š---š---š---š"
1084 LF 2
1086 LPRINT TAB (7);"Bei einem Count von Null bzw. Minus werden 2/2 nur gegen 5, 6";
1088 LPRINT " und 7"
1090 LPRINT TAB (7);"der Bank gesplittet und 6/6 nur gegen 5 und 6 der Bank.":LF 1
1092 LPRINT TAB (7);"Generell gilt die Regel, auch im Splittverfahren bei entsprec";
1094 LPRINT "hender"
1096 LPRINT TAB (7);"Mglichkeit zu doppeln.":LF 1
1098 LPRINT TAB (7);"Jede Box wird entsprechend den internationalen Regeln bis zu ";
1100 LPRINT "vier"
1102 LPRINT TAB (7);"Mal gesplittet, also 4 Unterhnde.":LF 1
1104 LPRINT TAB (7);"Der Programmautor wnscht allen 'Blackjack-Spielern' viel Erf";
1106 LPRINT "olg.":LF 18
1108 LPRINT TAB (41);"-7-":LF 10
1110 CLS :WAIT 0:PRINT "Ende der Dokumentations-"
1112 PRINT "druckausgabe. Bitte jetzt"
1114 PRINT "das Programm laden......."
1115 CLS :LOAD "X:MENUE.BAS",R
