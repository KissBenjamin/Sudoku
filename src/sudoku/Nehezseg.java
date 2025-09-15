package sudoku;

public enum Nehezseg {
    KONNYU(40),   // ~40 adott
    KOZEPES(30),  // ~30 adott
    NEHEZ(25);    // ~25 adott

    private final int adottak;

    Nehezseg(int adottak) { this.adottak = adottak; }
    public int getAdottak() { return adottak; }
}
