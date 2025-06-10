package Sistema.dao;

import Sistema.Escola;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EscolaDAO implements CrudDAO<Escola> {
    private Connection conn;

    public EscolaDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Escola escola) throws Exception {
        String sql = "INSERT INTO Escola (nome) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, escola.getNome());
            stmt.executeUpdate();
        }
    }

    @Override
    public void editar(Escola escola) throws Exception {
        String sql = "UPDATE Escola SET nome=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, escola.getNome());
            stmt.setInt(2, escola.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Escola visualizar(int id) throws Exception {
        String sql = "SELECT * FROM Escola WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Escola escola = new Escola();
                escola.setId(rs.getInt("id"));
                escola.setNome(rs.getString("nome"));
                return escola;
            }
        }
        return null;
    }

    @Override
    public void deletar(int id) throws Exception {
        String sql = "DELETE FROM Escola WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Escola> listar() throws Exception {
        List<Escola> escolas = new ArrayList<>();
        String sql = "SELECT * FROM Escola";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Escola escola = new Escola();
                escola.setId(rs.getInt("id"));
                escola.setNome(rs.getString("nome"));
                escolas.add(escola);
            }
        }
        return escolas;
    }
}