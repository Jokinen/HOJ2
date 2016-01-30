package hojclient;

import javax.swing.*;

/**
 * Kutsuu JFramesta metodia, jota JFrame käyttää hakeakseen palvelimelta
 * tuoreet tiedot.
 */
public class Päivittäjä implements Runnable {
    private Thread säie;
    private MainWindow ikkuna;
    private boolean käynnissä = false;

    public Päivittäjä(MainWindow m) {
        ikkuna = m;
        säie = new Thread(this);
    }

    public void käynnistä() {
        käynnissä = true;
        säie.start();
    }

    public void run() {
        try {
            while (käynnissä) {
                ikkuna.päivitäTiedot();
                Thread.sleep(10);
            }
        } catch (Exception e) {
            käynnissä = false;
            e.printStackTrace();
        }
    }

    public void sulje() {
        käynnissä = false;
    }
}
