package Sistema.ui;

import Sistema.dao.MatriculaDAO;
import Sistema.Matricula;
import Sistema.Aluno;
import Sistema.Disciplina;
import javax.swing.*;
import java.awt.*;

public class MatriculaFormPanel extends JPanel {
    public MatriculaFormPanel(Matricula matricula) {
        setLayout(new GridLayout(4, 2, 5, 5));
        JLabel lblAlunoId = new JLabel("ID do Aluno:");
        JTextField txtAlunoId = new JTextField(matricula != null && matricula.getAluno() != null ? String.valueOf(matricula.getAluno().getId()) : "");
        JLabel lblDisciplinaId = new JLabel("ID da Disciplina:");
        JTextField txtDisciplinaId = new JTextField(matricula != null && matricula.getDisciplina() != null ? String.valueOf(matricula.getDisciplina().getId()) : "");

        JButton btnSalvar = new JButton(matricula == null ? "Cadastrar" : "Salvar");
        btnSalvar.addActionListener(e -> {
            try {
                MatriculaDAO dao = new MatriculaDAO(Conexao.getConnection());
                int alunoId = Integer.parseInt(txtAlunoId.getText());
                int disciplinaId = Integer.parseInt(txtDisciplinaId.getText());

                if (matricula == null) {
                    Matricula novo = new Matricula();
                    Aluno aluno = new Aluno();
                    aluno.setId(alunoId);
                    novo.setAluno(aluno);

                    Disciplina disciplina = new Disciplina();
                    disciplina.setId(disciplinaId);
                    novo.setDisciplina(disciplina);

                    dao.inserir(novo);
                } else {
                    Aluno aluno = new Aluno();
                    aluno.setId(alunoId);
                    matricula.setAluno(aluno);

                    Disciplina disciplina = new Disciplina();
                    disciplina.setId(disciplinaId);
                    matricula.setDisciplina(disciplina);

                    dao.editar(matricula);
                }
                JOptionPane.showMessageDialog(this, "Salvo com sucesso!");
                Window win = SwingUtilities.getWindowAncestor(this);
                win.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
            }
        });

        add(lblAlunoId); add(txtAlunoId);
        add(lblDisciplinaId); add(txtDisciplinaId);
        add(new JLabel()); add(btnSalvar);
    }
}