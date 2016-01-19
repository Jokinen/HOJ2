package linjasto.komponentit.varastoivat.kypsytyssäiliö;

import linjasto.komponentit.varastoivat.Varastoiva;

/**
 * juoman nimi
 * tilavuus 10000 litraa
 * vain yksi pumppu voi täyttää tai tyhjentää säiliötä kerrallaan
 */
public class Kypsytyssäiliö extends Varastoiva {
    public Kypsytyssäiliö() {
        super(10000);
    }
}
