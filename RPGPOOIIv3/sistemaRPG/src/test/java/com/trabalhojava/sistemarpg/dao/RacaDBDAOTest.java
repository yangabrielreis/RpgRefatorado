package com.trabalhojava.sistemarpg.dao;

import com.trabalhojava.sistemarpg.model.Raca;
import com.trabalhojava.sistemarpg.model.Sistema;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RacaDBDAOTest {

    @Test
    void buscarPorCodigo_DeveBuscarCorretamente() throws SQLException {
        Sistema sistema = new Sistema(1, "Tormenta");
        Raca raca = new Raca(15, "Teste", "D", 0,0,0,0,0,0,sistema);
        RacaDBDAO racaDB = new RacaDBDAO();
        racaDB.remover(raca);
        racaDB.insere(raca);
        Raca racaBusca = racaDB.buscarPorCodigo(15);
        int codigo = racaBusca.getRacaId();
        assertEquals(15, codigo, "O código da raca registrada deve ser 15");
    }

    @Test
    void buscaPorNome_DeveBuscarCorretamente() throws SQLException {
        Sistema sistema = new Sistema(1, "Tormenta");
        Raca raca = new Raca(15, "Teste", "D", 0,0,0,0,0,0,sistema);
        RacaDBDAO racaDB = new RacaDBDAO();
        racaDB.remover(raca);
        racaDB.insere(raca);
        Raca racaBusca = racaDB.buscaPorNome("Teste");
        String nome = racaBusca.getNomeRaca();
        assertEquals("Teste", nome, "O nome da raca registrada deve ser Teste");
    }
}