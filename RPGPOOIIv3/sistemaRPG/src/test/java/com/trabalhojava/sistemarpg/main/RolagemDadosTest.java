package com.trabalhojava.sistemarpg.main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RolagemDadosTest {

    @Test
    void testeAtributo_deveEstarNoIntervalo() {
        RolagemDados dados = new RolagemDados();
        int valor = dados.testeAtributo(5);
        assertTrue(valor >= 6 && valor <= 25, "O valor da rolagem deve estar entre 6 e 25");
    }

    @Test
    void rodarAtributos_deveEstarNoIntervalo() {
        RolagemDados dados = new RolagemDados();
        int valor = dados.rodarAtributos();
        assertTrue(valor >= 3 && valor <= 18, "O valor do atributo deve estar entre 3 e 18");

    }
}