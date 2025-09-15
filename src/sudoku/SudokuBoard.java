package sudoku;

import java.util.Arrays;

public class SudokuBoard {

    private final int MERET = 9;
    private int[][] tabla;

    public SudokuBoard() {
        this.tabla = new int[MERET][MERET];
        torolTabla();
    }

    public void torolTabla() {
        for (int i = 0; i < MERET; i++) Arrays.fill(this.tabla[i], 0);
    }

    // Mély másolat
    public int[][] getTabla() {
        int[][] m = new int[MERET][MERET];
        for (int i = 0; i < MERET; i++) m[i] = Arrays.copyOf(this.tabla[i], MERET);
        return m;
    }

    // Mély másolat beállítása
    public void setTabla(int[][] uj) {
        if (uj == null || uj.length != MERET || uj[0].length != MERET) {
            throw new IllegalArgumentException("Hibás tábla méret! 9x9 kell legyen.");
        }
        for (int i = 0; i < MERET; i++) {
            for (int j = 0; j < MERET; j++) {
                int v = uj[i][j];
                if (v < 0 || v > 9) throw new IllegalArgumentException("Hibás érték a táblában: " + v);
            }
        }
        for (int i = 0; i < MERET; i++) this.tabla[i] = Arrays.copyOf(uj[i], MERET);
    }

    public int getMezo(int sor, int oszlop) {
        if (!ervenyesIndex(sor, oszlop)) throw new IllegalArgumentException("Hibás pozíció!");
        return this.tabla[sor][oszlop];
    }

    // 0 = üres; true ha sikerült
    public boolean setMezo(int sor, int oszlop, int ertek) {
        if (!ervenyesIndex(sor, oszlop)) { System.out.println("Hibás pozíció!"); return false; }
        if (ertek < 0 || ertek > 9)      { System.out.println("Hibás érték! 0-9."); return false; }
        this.tabla[sor][oszlop] = ertek;
        return true;
    }

    private boolean ervenyesIndex(int sor, int oszlop) {
        return sor >= 0 && sor < MERET && oszlop >= 0 && oszlop < MERET;
    }

    public void kiirTabla() {
        System.out.println("Sudoku tábla:");
        for (int sor = 0; sor < 9; sor++) {
            if (sor % 3 == 0) System.out.println("+-------+-------+-------+");
            for (int oszlop = 0; oszlop < 9; oszlop++) {
                if (oszlop % 3 == 0) System.out.print("| ");
                int v = this.tabla[sor][oszlop];
                System.out.print(v == 0 ? ". " : (v + " "));
            }
            System.out.println("|");
        }
        System.out.println("+-------+-------+-------+");
    }
}
