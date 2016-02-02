package komponentit.varastoivat.kypsytyssäiliö;

import komponentit.varastoivat.Varastoiva;

/**
 * juoman nimi
 * tilavuus 10000 litraa
 * vain yksi pumppu voi täyttää tai tyhjentää säiliötä kerrallaan
 */
public class Kypsytyssäiliö extends Varastoiva {
    public Kypsytyssäiliö(String tunnus) {
        super(tunnus, 10000);
    }
}
