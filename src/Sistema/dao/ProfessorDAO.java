package Sistema.dao;

import Sistema.Professor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO implements CrudDAO<Professor> {
    private Connection conn;

    public ProfessorDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Professor professor) throws Exception {
        String sql = "INSERT INTO Professor (nome, cpf, especialidade) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getCpf());
            stmt.setString(3, professor.getEspecialidade());
            stmt.executeUpdate();
        }
    }

    @Override
    public void editar(Professor professor) throws Exception {
        String sql = "UPDATE Professor SET nome=?, cpf=?, especialidade=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getCpf());
            stmt.setString(3, professor.getEspecialidade());
            stmt.setInt(4, professor.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Professor visualizar(int id) throws Exception {
        String sql = "SELECT * FROM Professor WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Professor professor = new Professor();
                professor.setId(rs.getInt("id"));
                professor.setNome(rs.getString("nome"));
                professor.setCpf(rs.getString("cpf"));
                professor.setEspecialidade(rs.getString("especialidade"));
                return professor;
            }
        }
        return null;
    }

    @Override
    public void deletar(int id) throws Exception {
        String sql = "DELETE FROM Professor WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Professor> listar() throws Exception {
        List<Professor> professores = new ArrayList<>();
        String sql = "SELECT * FROM Professor";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Professor professor = new Professor();
                professor.setId(rs.getInt("id"));
                professor.setNome(rs.getString("nome"));
                professor.setCpf(rs.getString("cpf"));
                professor.setEspecialidade(rs.getString("especialidade"));
                professores.add(professor);
            }
        }
        return professores;
    }
}