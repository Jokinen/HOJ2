package komponentit.varastoivat.raakaAineSiilo;

import komponentit.varastoivat.Varastoiva;

/**
 *  raaka-aineen tyyppi
 *  täyttöaste 0-10000 kiloa
 *  siiloa ei voi tyhjentää ja täyttää samaan aikaan
 *  Samasta siilosta voi ottaa raaka-ainetta vain yksi kuljetin kerrallaan
 */
public class RaakaAineSiilo extends Varastoiva {
    public RaakaAineSiilo(String tunnus) {
        super(tunnus, 10000);
    }
}
