import java.util.ArrayList;

/**
 * Ruuvikuljetin raaka-ainesiilojen täyttöön.
 * Siirtää raaka-ainetta 200 kiloa sekunnissa
 * Kuljettimelle annetaan siirrettävä määrä parametrina
 * Kuljetin ei voi siirtää enempää kuin vastaanottavaan yksikköön mahtuu
 * Raaka-ainetta tulee lisää tehtaaseen yhdessä erässä 40 tonnia
 * Siilo tai keitin ei saa ylitäyttyä
 */
public class RuuvikuljetinSiiloille {
    private final int SIIRTORAJA = 200;                 // per sekunti
    final ArrayList<RaakaAineSiilo> raakaAineSiilot;
    final int ERÄNKOKO = 40000;                         // kiloa

    // konstruktori
    public RuuvikuljetinSiiloille(ArrayList<RaakaAineSiilo> raakaAineSiilot) {
        this.raakaAineSiilot = raakaAineSiilot;
    }

    /**
     * Speksissä on määritelty, että jokainen järjestelmään tuotu erä on 40 tonnia,
     * joka on siilojen yhteenlaskettu kapasiteetti. Tämän perusteella tehdään alustava oletus,
     * jonka mukaan järjestelmään voidaan lisätä uutta raaka-ainetta vasta, kun kaikki säiliöt ovat tyhjiä.
     * Tämä voidaan todennäköisesti kumota kehityksen myöhemmässä vaiheessa, jolloin naiviin
     * oletuksen sijaan tyhjiin säiliöihin voidaan alkaa ajamaan raaka-ainetta, vaikka kaikki niistä
     * eivät tyhjiä vielä olisikaan.
     *
     * @param raakaAine säiliöihin siirrettävä raakaAine
     *
     * private.pre  forEach(siilo : raakaAineSiilot)
     *                  siilo.onTyhjä() == true;
     * private.post forEach(siilo : raakaAineSiilot)
     *                  siilo.onTäynnä() == true;
     */
    public void täytäSiilot(RaakaAine raakaAine) {}
}
