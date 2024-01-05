/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
        try {
            conn = new conectaDAO().connectDB();

            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, produto.getNome());
                pstmt.setInt(2, produto.getValor());
                pstmt.setString(3, produto.getStatus());

                
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!" );
                
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro ao cadastrar produto: " + e.getMessage());
        } finally {
            
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos(){
        listagem.clear();
       try {
            conn = new conectaDAO().connectDB();

            String sql = "SELECT * FROM produtos";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    while (resultSet.next()) {
                        ProdutosDTO produto = new ProdutosDTO();
                        produto.setId(resultSet.getInt("id"));
                        produto.setNome(resultSet.getString("nome"));
                        produto.setValor(resultSet.getInt("valor"));
                        produto.setStatus(resultSet.getString("status"));

                        listagem.add(produto);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }

        return listagem;
    }
    
    
    
        
}

