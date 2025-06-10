package Sistema.dao;

import Sistema.Nota;
import Sistema.Aluno;
import Sistema.Disciplina;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaDAO implements CrudDAO<Nota> {
    private Connection conn;

    public NotaDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Nota nota) throws Exception {
        String sql = "INSERT INTO Nota (aluno_id, disciplina_id, valor) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nota.getAluno().getId());
            stmt.setInt(2, nota.getDisciplina().getId());
            stmt.setDouble(3, nota.getValor());
            stmt.executeUpdate();
        }
    }

    @Override
    public void editar(Nota nota) throws Exception {
        String sql = "UPDATE Nota SET aluno_id=?, disciplina_id=?, valor=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nota.getAluno().getId());
            stmt.setInt(2, nota.getDisciplina().getId());
            stmt.setDouble(3, nota.getValor());
            stmt.setInt(4, nota.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Nota visualizar(int id) throws Exception {
        String sql = "SELECT * FROM Nota WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Nota nota = new Nota();
                nota.setId(rs.getInt("id"));
                nota.setValor(rs.getDouble("valor"));

                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("aluno_id"));
                nota.setAluno(aluno);

                Disciplina disciplina = new Disciplina();
                disciplina.setId(rs.getInt("disciplina_id"));
                nota.setDisciplina(disciplina);

                return nota;
            }
        }
        return null;
    }

    @Override
    public void deletar(int id) throws Exception {
        String sql = "DELETE FROM Nota WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Nota> listar() throws Exception {
        List<Nota> notas = new ArrayList<>();
        String sql = "SELECT * FROM Nota";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Nota nota = new Nota();
                nota.setId(rs.getInt("id"));
                nota.setValor(rs.getDouble("valor"));

                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("aluno_id"));
                nota.setAluno(aluno);

                Disciplina disciplina = new Disciplina();
                disciplina.setId(rs.getInt("disciplina_id"));
                nota.setDisciplina(disciplina);

                notas.add(nota);
            }
        }
        return notas;
    }
}