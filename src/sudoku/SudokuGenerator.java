package sudoku;

import java.util.Random;

public class SudokuGenerator {

    // Megoldható tábla generálása adott nehézség mellett (egyszerű, mégis stabil)
    public void generalMegoldhatoTabla(SudokuBoard board, Nehezseg szint) {
        SudokuSolver solver = new SudokuSolver();
        int[][] m = new int[9][9];

        // 1) teljes kész megoldás
        solver.megoldVeletlen(m);
        board.setTabla(m);

        // 2) mezők törlése a kívánt adott számig,
        //    opcionálisan ránézünk az egyediségre (limit=2 — ha >1, visszarakjuk)
        int torlendo = 81 - szint.getAdottak();
        Random rand = new Random();

        while (torlendo > 0) {
            int r = rand.nextInt(9), c = rand.nextInt(9);
            if (board.getMezo(r, c) == 0) continue;

            int backup = board.getMezo(r, c);
            board.setMezo(r, c, 0);

            int[][] masolat = board.getTabla();
            int megoldasok = solver.megoldasokSzama(masolat, 2); // max 2-ig számol
            if (megoldasok == 1) {
                torlendo--;
            } else {
                board.setMezo(r, c, backup); // nem egyedi -> vissza
            }
        }
    }
}
