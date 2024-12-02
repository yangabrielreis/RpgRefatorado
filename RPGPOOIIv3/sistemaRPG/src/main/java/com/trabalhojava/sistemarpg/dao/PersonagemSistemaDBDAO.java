package com.trabalhojava.sistemarpg.dao;

import com.trabalhojava.sistemarpg.model.PersonagemSistema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonagemSistemaDBDAO implements PersonagemSistemaDAO, IConst{
    private String sql;
    private Connection connection;
    private PreparedStatement statement;

    public PersonagemSistemaDBDAO() {}

    private void open() throws SQLException {
        connection = Conexao.getConexao("jdbc:postgresql://localhost:5432/sistemarpg", "postgres", "postgres");
    }

    private void close() throws SQLException {
        connection.close();
    }

    public void insere(PersonagemSistema personagemSistema) throws SQLException {
        open();
        sql = "INSERT INTO personagem_sistema(personagemId,sistemaId,classeId,racaId,nivel,forca,destreza,constituicao,sabedoria,inteligencia,carisma,hp) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        statement = connection.prepareStatement(sql);
        statement.setInt(1,personagemSistema.getPersonagem().getPersonagemId());
        statement.setInt(2,personagemSistema.getSistema().getSistemaId());
        statement.setInt(3,personagemSistema.getClasse().getClasseId());
        statement.setInt(4,personagemSistema.getRaca().getRacaId());
        statement.setInt(5,personagemSistema.getNivel());
        statement.setInt(6,personagemSistema.getForca());
        statement.setInt(7,personagemSistema.getDestreza());
        statement.setInt(8,personagemSistema.getConstituicao());
        statement.setInt(9,personagemSistema.getSabedoria());
        statement.setInt(10,personagemSistema.getInteligencia());
        statement.setInt(11,personagemSistema.getCarisma());
        statement.setInt(12,personagemSistema.getHp());
        statement.executeUpdate();
        close();
    }

    public void atualizar(PersonagemSistema personagemSistemaVelho, PersonagemSistema personagemSistemaNovo) throws SQLException {
        open();
        sql = "UPDATE personagem_sistema SET personagemId=?, sistemaId=?, classeId=?, racaId=?, nivel=?, hp=?, forca=?, destreza=?, constituicao=?, sabedoria=?, inteligencia=?, carisma=? WHERE personagemId=? AND sistemaId=? AND classeId=? AND racaId=?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1,personagemSistemaNovo.getPersonagem().getPersonagemId());
        statement.setInt(2,personagemSistemaNovo.getSistema().getSistemaId());
        statement.setInt(3,personagemSistemaNovo.getClasse().getClasseId());
        statement.setInt(4,personagemSistemaNovo.getRaca().getRacaId());
        statement.setInt(5,personagemSistemaNovo.getNivel());
        statement.setInt(6,personagemSistemaNovo.getHp());
        statement.setInt(7,personagemSistemaNovo.getForca());
        statement.setInt(8,personagemSistemaNovo.getDestreza());
        statement.setInt(9,personagemSistemaNovo.getConstituicao());
        statement.setInt(10,personagemSistemaNovo.getSabedoria());
        statement.setInt(11,personagemSistemaNovo.getInteligencia());
        statement.setInt(12,personagemSistemaNovo.getCarisma());
        statement.setInt(13,personagemSistemaVelho.getPersonagem().getPersonagemId());
        statement.setInt(14,personagemSistemaVelho.getSistema().getSistemaId());
        statement.setInt(15,personagemSistemaVelho.getClasse().getClasseId());
        statement.setInt(16,personagemSistemaVelho.getRaca().getRacaId());
        statement.executeUpdate();
        close();
    }

    public void remover(PersonagemSistema personagemSistema) throws SQLException {
        open();
        sql = "DELETE FROM personagem_sistema WHERE personagemId = ? AND sistemaId = ? AND classeId = ? AND racaId = ?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1,personagemSistema.getPersonagem().getPersonagemId());
        statement.setInt(2,personagemSistema.getSistema().getSistemaId());
        statement.setInt(3,personagemSistema.getClasse().getClasseId());
        statement.setInt(4,personagemSistema.getRaca().getRacaId());
        statement.executeUpdate();
        close();
    }

    /*3° Refatoração
    Autor: Mateus de Oliveira Lopes
    Refatoração geral para remover a função busca por código que nunca é utilizada por PersonagemSistemaDBDAO
    Objetivo: Remover funções não usadas
    */

    public List<PersonagemSistema> listar() throws SQLException {
        open();
        sql = "SELECT * FROM personagem_sistema";
        statement = connection.prepareStatement(sql);
        /*4° Refatoração
        Autor: Mateus de Oliveira Lopes
        Transformar uma variável global da classe que é usada em apenas uma função em uma variavel local da função onde ela é usada
        Objetivo: Deixar mais claro o uso da variável
        */
        ResultSet result = statement.executeQuery();
        ArrayList<PersonagemSistema> personagemSistemas = new ArrayList<>();
        PersonagemDBDAO personagemDB = new PersonagemDBDAO();
        ClasseDBDAO classeDB = new ClasseDBDAO();
        SistemaDBDAO sistemaDB = new SistemaDBDAO();
        RacaDBDAO racaDB = new RacaDBDAO();
        while (result.next()) {
            PersonagemSistema personagemSistema = new PersonagemSistema();
            personagemSistema.setPersonagem(personagemDB.buscaPorCodigo(result.getInt("personagemId")));
            personagemSistema.setSistema(sistemaDB.buscaPorCodigo(result.getInt("sistemaId")));
            personagemSistema.setClasse(classeDB.buscaPorCodigo(result.getInt("classeId")));
            personagemSistema.setRaca(racaDB.buscarPorCodigo(result.getInt("racaId")));
            PersonagemSistema personagemSistema2 = new PersonagemSistema(personagemSistema.getPersonagem(), personagemSistema.getSistema(), personagemSistema.getRaca(), personagemSistema.getClasse());
            personagemSistemas.add(personagemSistema2);
        }
        close();
        return personagemSistemas;
    }
}
