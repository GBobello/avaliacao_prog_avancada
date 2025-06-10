package Sistema.ui;

import Sistema.dao.EscolaDAO;
import Sistema.Escola;
import javax.swing.*;
import java.awt.*;

public class EscolaFormPanel extends JPanel {
    public EscolaFormPanel(Escola escola) {
        setLayout(new GridLayout(3, 2, 5, 5));
        JLabel lblNome = new JLabel("Nome:");
        JTextField txtNome = new JTextField(escola != null ? escola.getNome() : "");

        JButton btnSalvar = new JButton(escola == null ? "Cadastrar" : "Salvar");
        btnSalvar.addActionListener(e -> {
            try {
                EscolaDAO dao = new EscolaDAO(Conexao.getConnection());
                if (escola == null) {
                    Escola novo = new Escola();
                    novo.setNome(txtNome.getText());
                    dao.inserir(novo);
                } else {
                    escola.setNome(txtNome.getText());
                    dao.editar(escola);
                }
                JOptionPane.showMessageDialog(this, "Salvo com sucesso!");
                Window win = SwingUtilities.getWindowAncestor(this);
                win.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
            }
        });

        add(lblNome); add(txtNome);
        add(new JLabel()); add(btnSalvar);
    }
}