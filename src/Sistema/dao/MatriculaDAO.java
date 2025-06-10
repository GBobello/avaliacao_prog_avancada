package Sistema.dao;

import Sistema.Matricula;
import Sistema.Aluno;
import Sistema.Disciplina;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDAO implements CrudDAO<Matricula> {
    private Connection conn;

    public MatriculaDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(Matricula matricula) throws Exception {
        String sql = "INSERT INTO Matricula (aluno_id, disciplina_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, matricula.getAluno().getId());
            stmt.setInt(2, matricula.getDisciplina().getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void editar(Matricula matricula) throws Exception {
        String sql = "UPDATE Matricula SET aluno_id=?, disciplina_id=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, matricula.getAluno().getId());
            stmt.setInt(2, matricula.getDisciplina().getId());
            stmt.setInt(3, matricula.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Matricula visualizar(int id) throws Exception {
        String sql = "SELECT * FROM Matricula WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Matricula matricula = new Matricula();
                matricula.setId(rs.getInt("id"));

                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("aluno_id"));
                matricula.setAluno(aluno);

                Disciplina disciplina = new Disciplina();
                disciplina.setId(rs.getInt("disciplina_id"));
                matricula.setDisciplina(disciplina);

                return matricula;
            }
        }
        return null;
    }

    @Override
    public void deletar(int id) throws Exception {
        String sql = "DELETE FROM Matricula WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Matricula> listar() throws Exception {
        List<Matricula> matriculas = new ArrayList<>();
        String sql = "SELECT * FROM Matricula";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Matricula matricula = new Matricula();
                matricula.setId(rs.getInt("id"));

                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("aluno_id"));
                matricula.setAluno(aluno);

                Disciplina disciplina = new Disciplina();
                disciplina.setId(rs.getInt("disciplina_id"));
                matricula.setDisciplina(disciplina);

                matriculas.add(matricula);
            }
        }
        return matriculas;
    }
}