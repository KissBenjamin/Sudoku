package sudoku;

public class SudokuValidator {

    // Érvényes-e, ha az adott (sor, oszlop) mezőbe 'ertek'-et írunk a board aktuális állapotán?
    public boolean ervenyesLepes(SudokuBoard board, int sor, int oszlop, int ertek) {
        if (ertek < 1 || ertek > 9) return false;
        int[][] m = board.getTabla();

        // sor / oszlop
        for (int i = 0; i < 9; i++) {
            if (m[sor][i] == ertek) return false;
            if (m[i][oszlop] == ertek) return false;
        }
        // 3x3 blokk
        int rs = (sor / 3) * 3, cs = (oszlop / 3) * 3;
        for (int r = rs; r < rs + 3; r++) {
            for (int c = cs; c < cs + 3; c++) if (m[r][c] == ertek) return false;
        }
        return true;
    }
}
