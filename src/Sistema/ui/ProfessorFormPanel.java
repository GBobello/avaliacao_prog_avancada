package Sistema.ui;

import Sistema.dao.ProfessorDAO;
import Sistema.Professor;
import javax.swing.*;
import java.awt.*;

public class ProfessorFormPanel extends JPanel {
    public ProfessorFormPanel(Professor professor) {
        setLayout(new GridLayout(5, 2, 5, 5));
        JLabel lblNome = new JLabel("Nome:");
        JTextField txtNome = new JTextField(professor != null ? professor.getNome() : "");
        JLabel lblCpf = new JLabel("CPF:");
        JTextField txtCpf = new JTextField(professor != null ? professor.getCpf() : "");
        JLabel lblEspecialidade = new JLabel("Especialidade:");
        JTextField txtEspecialidade = new JTextField(professor != null ? professor.getEspecialidade() : "");

        JButton btnSalvar = new JButton(professor == null ? "Cadastrar" : "Salvar");
        btnSalvar.addActionListener(e -> {
            try {
                ProfessorDAO dao = new ProfessorDAO(Conexao.getConnection());
                if (professor == null) {
                    Professor novo = new Professor();
                    novo.setNome(txtNome.getText());
                    novo.setCpf(txtCpf.getText());
                    novo.setEspecialidade(txtEspecialidade.getText());
                    dao.inserir(novo);
                } else {
                    professor.setNome(txtNome.getText());
                    professor.setCpf(txtCpf.getText());
                    professor.setEspecialidade(txtEspecialidade.getText());
                    dao.editar(professor);
                }
                JOptionPane.showMessageDialog(this, "Salvo com sucesso!");
                Window win = SwingUtilities.getWindowAncestor(this);
                win.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
            }
        });

        add(lblNome); add(txtNome);
        add(lblCpf); add(txtCpf);
        add(lblEspecialidade); add(txtEspecialidade);
        add(new JLabel()); add(btnSalvar);
    }
}