package Sistema.dao;

import Sistema.Disciplina;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO implements CrudDAO<Disciplina> {
    private Connection conn;

    public DisciplinaDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Disciplina disciplina) throws Exception {
        String sql = "INSERT INTO Disciplina (nome, codigo) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, disciplina.getNome());
            stmt.setString(2, disciplina.getCodigo());
            stmt.executeUpdate();
        }
    }

    @Override
    public void editar(Disciplina disciplina) throws Exception {
        String sql = "UPDATE Disciplina SET nome=?, codigo=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, disciplina.getNome());
            stmt.setString(2, disciplina.getCodigo());
            stmt.setInt(3, disciplina.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Disciplina visualizar(int id) throws Exception {
        String sql = "SELECT * FROM Disciplina WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Disciplina disciplina = new Disciplina();
                disciplina.setId(rs.getInt("id"));
                disciplina.setNome(rs.getString("nome"));
                disciplina.setCodigo(rs.getString("codigo"));
                return disciplina;
            }
        }
        return null;
    }

    @Override
    public void deletar(int id) throws Exception {
        String sql = "DELETE FROM Disciplina WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Disciplina> listar() throws Exception {
        List<Disciplina> disciplinas = new ArrayList<>();
        String sql = "SELECT * FROM Disciplina";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Disciplina disciplina = new Disciplina();
                disciplina.setId(rs.getInt("id"));
                disciplina.setNome(rs.getString("nome"));
                disciplina.setCodigo(rs.getString("codigo"));
                disciplinas.add(disciplina);
            }
        }
        return disciplinas;
    }
}