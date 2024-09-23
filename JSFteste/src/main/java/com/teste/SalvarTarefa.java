package com.teste;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalvarTarefa {
	
	//Salva tarefa no BD
    public void salvarTarefa(Tarefa tarefa) {
        String sql = "INSERT INTO tarefas (titulo, descricao, responsavel, prioridade, deadline) VALUES (?, ?, ?, ?, ?)";
        System.out.println("Preparando para salvar a tarefa: " + tarefa.getTitulo());

        try (Connection conn = Conectar.getConnection(); //   conexão sera fechada automaticamente
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (conn != null) {  
                System.out.println("Conexão estabelecida, preparando o comando SQL.");
                stmt.setString(1, tarefa.getTitulo());
                stmt.setString(2, tarefa.getDescricao());
                stmt.setString(3, tarefa.getResponsavel());
                stmt.setString(4, tarefa.getPrioridade().name());
                stmt.setString(5, tarefa.getDeadline());
               

                stmt.executeUpdate();  // Executa a inserção
                System.out.println("Tarefa salva com sucesso!");
            } else {
                System.out.println("Conexão com o banco de dados falhou.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar a tarefa: " + e.getMessage());
            e.printStackTrace();  // Tratar erro adequadamente
        }
    }
    
    
    //Recupera lista do DB
    public List<Tarefa> listarTarefas() {
        List<Tarefa> tarefas = new ArrayList<>();
        String sql = "SELECT * FROM tarefas";

        try (Connection conn = Conectar.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Tarefa tarefa = new Tarefa();
                tarefa.setId(rs.getInt("id"));             
                tarefa.setTitulo(rs.getString("titulo"));
                tarefa.setDescricao(rs.getString("descricao"));
                tarefa.setResponsavel(rs.getString("responsavel"));
                tarefa.setPrioridade(Tarefa.Prioridade.valueOf(rs.getString("prioridade")));
                tarefa.setDeadline(rs.getString("deadline"));
                tarefa.setSituacao(rs.getString("situacao"));
                tarefas.add(tarefa);
                
            }
            
           
        } catch (SQLException e) {
            System.out.println("Erro ao listar tarefas: " + e.getMessage());
            e.printStackTrace();
        }
        
		/*
		 * for (Tarefa tarefa : tarefas) { System.out.println("ID: " + tarefa.getId());
		 * // Imprime o ID System.out.println("Título: " + tarefa.getTitulo());
		 * System.out.println("Descrição: " + tarefa.getDescricao());
		 * System.out.println("Responsável: " + tarefa.getResponsavel());
		 * System.out.println("Prioridade: " + tarefa.getPrioridade());
		 * System.out.println("Deadline: " + tarefa.getDeadline());
		 * System.out.println("---------------------------"); }
		 */
            

        return tarefas;
    }
    
    
    
    public void excluirTarefa(int id) {
        String sql = "DELETE FROM tarefas WHERE id = ?";

        try (Connection conn = Conectar.getConnection(); // 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id); // Define o ID da tarefa a ser excluido

            int rowsDeleted = stmt.executeUpdate(); // Executa a exclusão

            if (rowsDeleted > 0) {
                System.out.println("Tarefa com ID " + id + " excluída com sucesso!");
            } else {
                System.out.println("Nenhuma tarefa foi encontrada com o ID " + id);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao excluir a tarefa: " + e.getMessage());
        }
    }
    
    
    
    public void concluirTarefa(int id) {
        String sql = "UPDATE tarefas SET situacao = 'Concluida' WHERE id = ?";
        try (Connection conn = Conectar.getConnection();
        		PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public Tarefa buscarTarefaPorId(int id) {
        String sql = "SELECT * FROM tarefas WHERE id = ?";
        Tarefa tarefa = null;

        try (Connection conn = Conectar.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                tarefa = new Tarefa();
                tarefa.setId(rs.getInt("id"));
                tarefa.setTitulo(rs.getString("titulo"));
                tarefa.setDescricao(rs.getString("descricao"));
                tarefa.setResponsavel(rs.getString("responsavel"));
                tarefa.setPrioridade(Tarefa.Prioridade.valueOf(rs.getString("prioridade")));
                tarefa.setDeadline(rs.getString("deadline"));
                tarefa.setSituacao(rs.getString("situacao")); // Adicione esta linha para carregar a situação
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar tarefa por ID: " + e.getMessage());
        }

        return tarefa;
    }
    
    
    public void atualizarTarefa(Tarefa tarefa) {
        String sql = "UPDATE tarefas SET titulo = ?, descricao = ?, responsavel = ?, prioridade = ?, deadline = ?, situacao = ? WHERE id = ?";

        try (Connection conn = Conectar.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setString(3, tarefa.getResponsavel());
            stmt.setString(4, tarefa.getPrioridade().name());
            stmt.setString(5, tarefa.getDeadline());
            stmt.setString(6, tarefa.getSituacao());
            stmt.setInt(7, tarefa.getId());

            int rowsUpdated = stmt.executeUpdate(); // Executa a atualização

            if (rowsUpdated > 0) {
                System.out.println("Tarefa atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma tarefa foi encontrada para atualizar.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar a tarefa: " + e.getMessage());
        }
    }


    
    
}
