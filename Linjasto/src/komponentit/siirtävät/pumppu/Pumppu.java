package komponentit.siirtävät.pumppu;

import komponentit.siirtävät.Siirtävä;

/**
 * Siirtää juomaa 500 litraa sekunnissa
 * Pumpulle annetaan parametrina siirrettävä määrä tai se voidaan asettaa tyhjentämään koko yksikkö
 * Säiliöitä ei saa ylitäyttää
 * Tyhjästä säiliöstä ei voi pumpata pullotukseen
 * Pullotukseen oletetaan mahtuvan aina niin paljon kuin säiliössä on juomaa
 */
public class Pumppu extends Siirtävä {

    public Pumppu(String tunnus) {
        super(tunnus, 500);
    }

}
