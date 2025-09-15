# Sudoku
Sudoku játék Java és Java Swing felhasználásával – oktatási és önfejlesztési célokra

# 🧩 Sudoku - Java GUI (Swing) játék

Ez a program egy klasszikus **Sudoku játék**, amelyet **Java Swing** segítségével készítettem.  
A játék grafikus felületen fut, lehetőség van új táblák generálására különböző nehézségi szintekkel, mentésre, betöltésre és a teljes megoldás megjelenítésére.

---

## 🚀 Funkciók

- ✅ Grafikus felhasználói felület (`JFrame`, `JTextField`, `JButton`)  
- ✅ Új játék indítása három nehézségi szinten (Alap, Haladó, Profi)  
- ✅ Mindig garantáltan megoldható Sudoku táblák generálása  
- ✅ Beviteli mezők validálása (csak 1–9 szám engedélyezett)  
- ✅ Mentés és betöltés fájlba  
- ✅ Megoldás gomb – kérdés után megmutatja a helyes megoldást (új mezők zöld színnel)  
- ✅ Időzítő, amely a játék kezdésétől számolja az eltelt időt  

---

## 🧱 Fejlesztési szakaszok

### 1. Alaplogika (konzolban)
- Sudoku tábla reprezentáció (9x9 mátrix)  
- Generátor, amely mindig megoldható táblát készít  
- Megoldó algoritmus (backtracking)  
- Érvényesség-ellenőrzés sor, oszlop és blokk szerint  

### 2. Swing alap GUI megvalósítása
- `JFrame` ablak létrehozása  
- 9x9 mező `JTextField`-ekből, blokkok kiemelt keretezéssel  
- Felső panel: új játék, mentés, betöltés, megoldás gombok, idő kijelző  
- Nehézségválasztó legördülő menü  

### 3. Játéklogika integráció
- Új játék generálása választott nehézséggel  
- Mentés és betöltés kezelése  
- Megoldás gomb megerősítéssel  
- Timer indítása/leállítása  

---

## 🛠️ Fejlesztési lehetőségek

- 🎨 Felhasználói felület szebbé tétele (pl. színes téma, jobb vizuális blokkok)  
- 🎵 Hangjelzések helyes/hibás bevitelhez  
- 📊 Statisztika (megoldott játékok száma, átlagidő)  
- 🌐 Online mód (más játékosokkal való versenyzés)  
- 🧠 Nehezebb táblák generálása (többféle algoritmussal)  

---

## 🔧 Használat (Eclipse-ben vagy más IDE-ben)

1. Hozz létre egy új Java projektet.  
2. Másold be a forráskódot a `sudoku` package-be (`SudokuBoard`, `SudokuGenerator`, `SudokuSolver`, `SudokuValidator`, `GameStateManager`, `TimerManager`, `SudokuApp`).  
3. Futtasd a `SudokuApp` osztály `main()` metódusát.  
4. Válassz nehézségi szintet, majd kattints az **Új** gombra a játék indításához.  
5. Írd be a számokat a Sudoku mezőkbe (csak 1–9).  
6. Használd a mentés/betöltés gombokat, vagy a **Megoldás** gombot a teljes megoldás megtekintéséhez.  

---

## 📄 Licenc

Ez a projekt szabadon használható, másolható, fejleszthető tanulási célokra.

---
