package Sistema.ui;

import Sistema.dao.TurmaDAO;
import Sistema.Turma;
import Sistema.Professor;
import javax.swing.*;
import java.awt.*;

public class TurmaFormPanel extends JPanel {
    public TurmaFormPanel(Turma turma) {
        setLayout(new GridLayout(5, 2, 5, 5));
        JLabel lblNome = new JLabel("Nome:");
        JTextField txtNome = new JTextField(turma != null ? turma.getNome() : "");
        JLabel lblAno = new JLabel("Ano:");
        JTextField txtAno = new JTextField(turma != null ? String.valueOf(turma.getAno()) : "");
        JLabel lblProfessorId = new JLabel("ID do Professor:");
        JTextField txtProfessorId = new JTextField(turma != null && turma.getProfessor() != null ? String.valueOf(turma.getProfessor().getId()) : "");

        JButton btnSalvar = new JButton(turma == null ? "Cadastrar" : "Salvar");
        btnSalvar.addActionListener(e -> {
            try {
                TurmaDAO dao = new TurmaDAO(Conexao.getConnection());
                String nome = txtNome.getText();
                int ano = Integer.parseInt(txtAno.getText());
                int professorId = Integer.parseInt(txtProfessorId.getText());

                if (turma == null) {
                    Turma novo = new Turma();
                    novo.setNome(nome);
                    novo.setAno(ano);

                    Professor professor = new Professor();
                    professor.setId(professorId);
                    novo.setProfessor(professor);

                    dao.inserir(novo);
                } else {
                    turma.setNome(nome);
                    turma.setAno(ano);

                    Professor professor = new Professor();
                    professor.setId(professorId);
                    turma.setProfessor(professor);

                    dao.editar(turma);
                }
                JOptionPane.showMessageDialog(this, "Salvo com sucesso!");
                Window win = SwingUtilities.getWindowAncestor(this);
                win.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
            }
        });

        add(lblNome); add(txtNome);
        add(lblAno); add(txtAno);
        add(lblProfessorId); add(txtProfessorId);
        add(new JLabel()); add(btnSalvar);
    }
}