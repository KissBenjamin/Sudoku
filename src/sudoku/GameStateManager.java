package sudoku;

import java.io.*;

public class GameStateManager {

    private static final String MENTES_FAJL = "sudoku_save.dat";

    public void mentes(SudokuBoard tabla) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(MENTES_FAJL))) {
            out.writeObject(tabla.getTabla()); // int[][] mentése
            System.out.println("Játék sikeresen elmentve: " + MENTES_FAJL);
        } catch (IOException e) {
            System.out.println("Hiba a mentés során: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public boolean betolt(SudokuBoard tabla) {
        File fajl = new File(MENTES_FAJL);
        if (!fajl.exists()) {
            System.out.println("Nincs mentés, amit betölthetnénk.");
            return false;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fajl))) {
            int[][] beolvasott = (int[][]) in.readObject();
            tabla.setTabla(beolvasott);
            System.out.println("Játék betöltve: " + MENTES_FAJL);
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Hiba a betöltés során: " + e.getMessage());
            return false;
        }
    }
}
