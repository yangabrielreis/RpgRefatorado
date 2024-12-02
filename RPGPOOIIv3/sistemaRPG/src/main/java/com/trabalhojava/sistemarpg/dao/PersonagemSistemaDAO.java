package com.trabalhojava.sistemarpg.dao;

import java.sql.SQLException;
import java.util.List;
import com.trabalhojava.sistemarpg.model.PersonagemSistema;

public interface PersonagemSistemaDAO {
    void insere(PersonagemSistema var1) throws SQLException;
    void atualizar(PersonagemSistema varNova, PersonagemSistema varVelha) throws SQLException;
    void remover(PersonagemSistema var1) throws SQLException;
    /*3° Refatoração
    Autor: Mateus de Oliveira Lopes
    Refatoração geral para remover a função busca por código que nunca é utilizada por PersonagemSistemaDBDAO
    Objetivo: Remover funções não usadas
    */
    //PersonagemSistema buscaPorCodigo(int personagemId, int classeId, int sistemaId, int racaId) throws SQLException;
    List<PersonagemSistema> listar() throws SQLException;
}
