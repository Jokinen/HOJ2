package linjastonKomponentit.Ruuvikuljetin;

import linjastonKomponentit.RaakaAine;
import linjastonKomponentit.RaakaAineSiilo;
import omatVirheilmoitukset.Virheet;

import java.util.ArrayList;

public class RuuvikuljetinSiiloille extends Ruuvikuljetin {
    final ArrayList<RaakaAineSiilo> raakaAineSiilot;

    public RuuvikuljetinSiiloille(ArrayList<RaakaAineSiilo> raakaAineSiilot) {
        super();
        this.raakaAineSiilot = raakaAineSiilot;
    }

    /**
     * Speksissä on määritelty, että jokainen järjestelmään tuotu erä on 40 tonnia,
     * joka on siilojen yhteenlaskettu kapasiteetti. Tämän perusteella tehdään alustava oletus,
     * jonka mukaan järjestelmään voidaan lisätä uutta raaka-ainetta vasta, kun kaikki säiliöt ovat tyhjiä.
     * Tämä voidaan todennäköisesti kumota kehityksen myöhemmässä vaiheessa, jolloin naiviin
     * oletuksen sijaan tyhjiin säiliöihin voidaan alkaa ajamaan raaka-ainetta, vaikka kaikki siilot
     * eivät tyhjiä vielä olisikaan.
     *
     * @param raakaAine säiliöihin siirrettävä raaka-aine
     * @throws Virheet.SiilotEivätTyhjiäException
     *              EXISTS(siilo : raakaAineSiilot)
     *                  siilo.onTyhjä() == false;
     * @.postPrivate FORALL(siilo : raakaAineSiilot)
     *                  siilo.onTäynnä() == true;
     */
    public void täytäSiilot(RaakaAine raakaAine) {}
}
