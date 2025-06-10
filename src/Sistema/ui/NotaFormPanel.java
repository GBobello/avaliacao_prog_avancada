package Sistema.ui;

import Sistema.dao.NotaDAO;
import Sistema.Nota;
import Sistema.Aluno;
import Sistema.Disciplina;
import javax.swing.*;
import java.awt.*;

public class NotaFormPanel extends JPanel {
    public NotaFormPanel(Nota nota) {
        setLayout(new GridLayout(5, 2, 5, 5));
        JLabel lblAlunoId = new JLabel("ID do Aluno:");
        JTextField txtAlunoId = new JTextField(nota != null && nota.getAluno() != null ? String.valueOf(nota.getAluno().getId()) : "");
        JLabel lblDisciplinaId = new JLabel("ID da Disciplina:");
        JTextField txtDisciplinaId = new JTextField(nota != null && nota.getDisciplina() != null ? String.valueOf(nota.getDisciplina().getId()) : "");
        JLabel lblValor = new JLabel("Valor:");
        JTextField txtValor = new JTextField(nota != null ? String.valueOf(nota.getValor()) : "");

        JButton btnSalvar = new JButton(nota == null ? "Cadastrar" : "Salvar");
        btnSalvar.addActionListener(e -> {
            try {
                NotaDAO dao = new NotaDAO(Conexao.getConnection());
                int alunoId = Integer.parseInt(txtAlunoId.getText());
                int disciplinaId = Integer.parseInt(txtDisciplinaId.getText());
                double valor = Double.parseDouble(txtValor.getText());

                if (nota == null) {
                    Nota novo = new Nota();
                    Aluno aluno = new Aluno();
                    aluno.setId(alunoId);
                    novo.setAluno(aluno);

                    Disciplina disciplina = new Disciplina();
                    disciplina.setId(disciplinaId);
                    novo.setDisciplina(disciplina);

                    novo.setValor(valor);
                    dao.inserir(novo);
                } else {
                    Aluno aluno = new Aluno();
                    aluno.setId(alunoId);
                    nota.setAluno(aluno);

                    Disciplina disciplina = new Disciplina();
                    disciplina.setId(disciplinaId);
                    nota.setDisciplina(disciplina);

                    nota.setValor(valor);
                    dao.editar(nota);
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
        add(lblValor); add(txtValor);
        add(new JLabel()); add(btnSalvar);
    }
}