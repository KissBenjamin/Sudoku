package sudoku;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SudokuApp extends JFrame {

    private final JTextField[][] mezok = new JTextField[9][9];
    private SudokuBoard tabla = new SudokuBoard();
    private final SudokuGenerator generator = new SudokuGenerator();
    private final SudokuSolver solver = new SudokuSolver();
    private final SudokuValidator validator = new SudokuValidator();
    private final GameStateManager gsm = new GameStateManager();

    private final boolean[][] kezdeti = new boolean[9][9];

    private final TimerManager tm = new TimerManager();
    private javax.swing.Timer uiTimer;

    public SudokuApp() {
        super("Sudoku Játék");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Felül: vezérlők ---
        JPanel felso = new JPanel();
        JButton ujJatek = new JButton("Új");
        JButton megoldBtn = new JButton("Megoldás");
        JButton mentesBtn = new JButton("Mentés");
        JButton betoltBtn = new JButton("Betölt");
        JLabel idoLabel = new JLabel("00:00");

        String[] nehezsegek = {"Alap", "Haladó", "Profi"};
        JComboBox<String> nehezsegBox = new JComboBox<>(nehezsegek);

        felso.add(new JLabel("Nehézség:"));
        felso.add(nehezsegBox);
        felso.add(ujJatek);
        felso.add(megoldBtn);
        felso.add(mentesBtn);
        felso.add(betoltBtn);
        felso.add(idoLabel);
        add(felso, BorderLayout.NORTH);

        // Idő kijelző frissítése
        uiTimer = new javax.swing.Timer(1000, e -> idoLabel.setText(tm.format()));

        // --- Közép: 9x9 mező ---
        JPanel kozep = new JPanel(new GridLayout(9, 9));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField tf = new JTextField();
                tf.setHorizontalAlignment(JTextField.CENTER);

                int top    = (i % 3 == 0) ? 2 : 1;
                int left   = (j % 3 == 0) ? 2 : 1;
                int bottom = (i == 8) ? 2 : 1;
                int right  = (j == 8) ? 2 : 1;
                tf.setBorder(new MatteBorder(top, left, bottom, right, Color.BLACK));

                final int sor = i;
                final int oszlop = j;

                tf.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        if (!tf.isEditable()) return;

                        String s = tf.getText().trim();
                        if (s.matches("[1-9]")) {
                            int v = Integer.parseInt(s);
                            if (validator.ervenyesLepes(tabla, sor, oszlop, v)) {
                                tabla.setMezo(sor, oszlop, v);
                                tf.setForeground(Color.BLACK);
                            } else {
                                tf.setForeground(Color.RED);
                            }
                        } else if (s.isEmpty()) {
                            tabla.setMezo(sor, oszlop, 0);
                            tf.setForeground(Color.BLACK);
                        } else {
                            tf.setText("");
                            tf.setForeground(Color.BLACK);
                            tabla.setMezo(sor, oszlop, 0);
                        }
                    }
                });

                mezok[i][j] = tf;
                kozep.add(tf);
            }
        }
        add(kozep, BorderLayout.CENTER);

        // --- Új játék ---
        ujJatek.addActionListener(e -> {
            Nehezseg[] szintek = {Nehezseg.KONNYU, Nehezseg.KOZEPES, Nehezseg.NEHEZ};
            int idx = nehezsegBox.getSelectedIndex();
            if (idx < 0 || idx >= szintek.length) idx = 1; // KOZEPES
            Nehezseg szint = szintek[idx];

            tabla = new SudokuBoard();
            generator.generalMegoldhatoTabla(tabla, szint);
            initKezdeti();
            frissitTabla(tabla.getTabla(), false);

            tm.reset();
            tm.indit();
            uiTimer.start();

            System.out.println("Új játék generálva: " + szint);
        });

        // --- Megoldás ---
        megoldBtn.addActionListener(e -> {
            int valasz = JOptionPane.showConfirmDialog(
                this, "Biztosan meg szeretnéd nézni a megoldást?",
                "Megoldás megerősítés", JOptionPane.YES_NO_OPTION
            );
            if (valasz == JOptionPane.YES_OPTION) {
                SudokuBoard masolat = new SudokuBoard();
                masolat.setTabla(tabla.getTabla());
                if (solver.megold(masolat)) {
                    frissitTabla(masolat.getTabla(), true);
                    tm.leallit();
                    uiTimer.stop();
                    System.out.println("Megoldás megjelenítve.");
                } else {
                    System.out.println("Nem található megoldás a táblához.");
                }
            }
        });

        // --- Mentés / Betöltés ---
        mentesBtn.addActionListener(e -> gsm.mentes(tabla));
        betoltBtn.addActionListener(e -> {
            if (gsm.betolt(tabla)) {
                initKezdeti();
                frissitTabla(tabla.getTabla(), false);
                tm.reset();
                idoLabel.setText(tm.format());
            }
        });

        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Kezdő (adott) mezők megjelölése
    private void initKezdeti() {
        int[][] m = tabla.getTabla();
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                kezdeti[r][c] = (m[r][c] != 0);
    }

    // A mátrix kirajzolása. Ha megoldva==true, a nem-kezdő mezők zöldek.
    private void frissitTabla(int[][] m, boolean megoldva) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                JTextField tf = mezok[r][c];
                int v = m[r][c];
                if (v == 0) {
                    tf.setText("");
                    tf.setEditable(true);
                    tf.setForeground(Color.BLACK);
                } else {
                    tf.setText(String.valueOf(v));
                    tf.setEditable(!kezdeti[r][c] && !megoldva);
                    if (megoldva && !kezdeti[r][c]) tf.setForeground(Color.GREEN);
                    else tf.setForeground(Color.BLACK);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuApp::new);
    }
}
