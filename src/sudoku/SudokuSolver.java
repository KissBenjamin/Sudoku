package sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SudokuSolver {

    private final Random rnd = new Random();

    // Megpróbálja megoldani a board-ot (mély másolattal dolgozik, visszaír)
    public boolean megold(SudokuBoard board) {
        int[][] m = board.getTabla();
        if (backtrack(m)) { board.setTabla(m); return true; }
        return false;
    }

    // Teljes megoldás készítése üres mátrixon (véletlen sorrend)
    public boolean megoldVeletlen(int[][] m) {
        return backtrackRandom(m);
    }

    // Megoldások számolása limitig (egyediség-ellenőrzéshez)
    public int megoldasokSzama(int[][] m, int limit) {
        return countSolutions(m, limit, 0);
    }

    // ---------- backtracking (determinista) ----------
    private boolean backtrack(int[][] m) {
        int[] cella = kovetkezoUres(m);
        if (cella == null) return true;
        int r = cella[0], c = cella[1];
        for (int v = 1; v <= 9; v++) {
            if (ervenyesLepes(m, r, c, v)) {
                m[r][c] = v;
                if (backtrack(m)) return true;
                m[r][c] = 0;
            }
        }
        return false;
    }

    // ---------- backtracking (véletlen) ----------
    private boolean backtrackRandom(int[][] m) {
        int[] cella = kovetkezoUres(m);
        if (cella == null) return true;
        int r = cella[0], c = cella[1];

        List<Integer> szamok = new ArrayList<>();
        for (int v = 1; v <= 9; v++) szamok.add(v);
        Collections.shuffle(szamok, rnd);

        for (int v : szamok) {
            if (ervenyesLepes(m, r, c, v)) {
                m[r][c] = v;
                if (backtrackRandom(m)) return true;
                m[r][c] = 0;
            }
        }
        return false;
    }

    private int countSolutions(int[][] m, int limit, int found) {
        if (found >= limit) return found;
        int[] cella = kovetkezoUres(m);
        if (cella == null) return found + 1;

        int r = cella[0], c = cella[1];
        for (int v = 1; v <= 9; v++) {
            if (ervenyesLepes(m, r, c, v)) {
                m[r][c] = v;
                found = countSolutions(m, limit, found);
                m[r][c] = 0;
                if (found >= limit) return found;
            }
        }
        return found;
    }

    private int[] kovetkezoUres(int[][] m) {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (m[r][c] == 0) return new int[]{r, c};
        return null;
    }

    private boolean ervenyesLepes(int[][] m, int sor, int oszlop, int ertek) {
        if (ertek < 1 || ertek > 9) return false;
        for (int i = 0; i < 9; i++) {
            if (m[sor][i] == ertek) return false;
            if (m[i][oszlop] == ertek) return false;
        }
        int rs = (sor / 3) * 3, cs = (oszlop / 3) * 3;
        for (int r = rs; r < rs + 3; r++)
            for (int c = cs; c < cs + 3; c++)
                if (m[r][c] == ertek) return false;
        return true;
    }
}
