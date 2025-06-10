package Sistema.dao;

import Sistema.Aluno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO implements CrudDAO<Aluno> {
    private Connection conn;

    public AlunoDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Aluno aluno) throws Exception {
        String sql = "INSERT INTO Aluno (nome, cpf, matricula) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setInt(3, aluno.getMatricula());
            stmt.executeUpdate();
        }
    }

    @Override
    public void editar(Aluno aluno) throws Exception {
        String sql = "UPDATE Aluno SET nome=?, cpf=?, matricula=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setInt(3, aluno.getMatricula());
            stmt.setInt(4, aluno.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Aluno visualizar(int id) throws Exception {
        String sql = "SELECT * FROM Aluno WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setCpf(rs.getString("cpf"));
                aluno.setMatricula(rs.getInt("matricula"));
                return aluno;
            }
        }
        return null;
    }

    @Override
    public void deletar(int id) throws Exception {
        String sql = "DELETE FROM Aluno WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Aluno> listar() throws Exception {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM Aluno";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setCpf(rs.getString("cpf"));
                aluno.setMatricula(rs.getInt("matricula"));
                alunos.add(aluno);
            }
        }
        return alunos;
    }
}