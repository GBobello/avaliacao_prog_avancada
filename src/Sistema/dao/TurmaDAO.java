package Sistema.dao;

import Sistema.Turma;
import Sistema.Professor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurmaDAO implements CrudDAO<Turma> {
    private Connection conn;

    public TurmaDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Turma turma) throws Exception {
        String sql = "INSERT INTO Turma (nome, ano, professor_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, turma.getNome());
            stmt.setInt(2, turma.getAno());
            stmt.setInt(3, turma.getProfessor().getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void editar(Turma turma) throws Exception {
        String sql = "UPDATE Turma SET nome=?, ano=?, professor_id=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, turma.getNome());
            stmt.setInt(2, turma.getAno());
            stmt.setInt(3, turma.getProfessor().getId());
            stmt.setInt(4, turma.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Turma visualizar(int id) throws Exception {
        String sql = "SELECT * FROM Turma WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Turma turma = new Turma();
                turma.setId(rs.getInt("id"));
                turma.setNome(rs.getString("nome"));
                turma.setAno(rs.getInt("ano"));

                int professorId = rs.getInt("professor_id");
                if (!rs.wasNull()) {
                    Professor professor = new Professor();
                    professor.setId(professorId);
                    turma.setProfessor(professor);
                }

                return turma;
            }
        }
        return null;
    }

    @Override
    public void deletar(int id) throws Exception {
        String sql = "DELETE FROM Turma WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Turma> listar() throws Exception {
        List<Turma> turmas = new ArrayList<>();
        String sql = "SELECT * FROM Turma";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Turma turma = new Turma();
                turma.setId(rs.getInt("id"));
                turma.setNome(rs.getString("nome"));
                turma.setAno(rs.getInt("ano"));

                int professorId = rs.getInt("professor_id");
                if (!rs.wasNull()) {
                    Professor professor = new Professor();
                    professor.setId(professorId);
                    turma.setProfessor(professor);
                }

                turmas.add(turma);
            }
        }
        return turmas;
    }
}