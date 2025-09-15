package sudoku;

public class TimerManager {

    private long startMs;
    private long osszesenMs;
    private boolean fut;

    public TimerManager() {
        this.startMs = 0L;
        this.osszesenMs = 0L;
        this.fut = false;
    }

    public void indit() {
        if (!fut) {
            fut = true;
            startMs = System.currentTimeMillis();
            System.out.println("Időmérés elindítva.");
        }
    }

    public void leallit() {
        if (fut) {
            long most = System.currentTimeMillis();
            osszesenMs += (most - startMs);
            fut = false;
            System.out.println("Időmérés leállítva.");
        }
    }

    public void reset() {
        fut = false;
        osszesenMs = 0L;
        startMs = 0L;
        System.out.println("Időmérés visszaállítva.");
    }

    public String format() {
        long ms = fut ? (osszesenMs + (System.currentTimeMillis() - startMs)) : osszesenMs;
        long sec = ms / 1000;
        long mm = sec / 60;
        long ss = sec % 60;
        return String.format("%02d:%02d", mm, ss);
    }
}
