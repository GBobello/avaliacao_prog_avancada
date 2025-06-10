package Sistema.ui;

import Sistema.dao.DisciplinaDAO;
import Sistema.Disciplina;
import javax.swing.*;
import java.awt.*;

public class DisciplinaFormPanel extends JPanel {
    public DisciplinaFormPanel(Disciplina disciplina) {
        setLayout(new GridLayout(4, 2, 5, 5));
        JLabel lblNome = new JLabel("Nome:");
        JTextField txtNome = new JTextField(disciplina != null ? disciplina.getNome() : "");
        JLabel lblCodigo = new JLabel("Código:");
        JTextField txtCodigo = new JTextField(disciplina != null ? disciplina.getCodigo() : "");

        JButton btnSalvar = new JButton(disciplina == null ? "Cadastrar" : "Salvar");
        btnSalvar.addActionListener(e -> {
            try {
                DisciplinaDAO dao = new DisciplinaDAO(Conexao.getConnection());
                if (disciplina == null) {
                    Disciplina novo = new Disciplina();
                    novo.setNome(txtNome.getText());
                    novo.setCodigo(txtCodigo.getText());
                    dao.inserir(novo);
                } else {
                    disciplina.setNome(txtNome.getText());
                    disciplina.setCodigo(txtCodigo.getText());
                    dao.editar(disciplina);
                }
                JOptionPane.showMessageDialog(this, "Salvo com sucesso!");
                Window win = SwingUtilities.getWindowAncestor(this);
                win.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
            }
        });

        add(lblNome); add(txtNome);
        add(lblCodigo); add(txtCodigo);
        add(new JLabel()); add(btnSalvar);
    }
}