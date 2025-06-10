package Sistema.ui;

import Sistema.dao.AlunoDAO;
import Sistema.Aluno;
import javax.swing.*;
import java.awt.*;

public class AlunoFormPanel extends JPanel {
    public AlunoFormPanel(Aluno aluno) {
        setLayout(new GridLayout(5, 2, 5, 5));
        JLabel lblNome = new JLabel("Nome:");
        JTextField txtNome = new JTextField(aluno != null ? aluno.getNome() : "");
        JLabel lblCpf = new JLabel("CPF:");
        JTextField txtCpf = new JTextField(aluno != null ? aluno.getCpf() : "");
        JLabel lblMatricula = new JLabel("Matrícula:");
        JTextField txtMatricula = new JTextField(aluno != null ? String.valueOf(aluno.getMatricula()) : "");

        JButton btnSalvar = new JButton(aluno == null ? "Cadastrar" : "Salvar");
        btnSalvar.addActionListener(e -> {
            try {
                AlunoDAO dao = new AlunoDAO(Conexao.getConnection());
                if (aluno == null) {
                    Aluno novo = new Aluno();
                    novo.setNome(txtNome.getText());
                    novo.setCpf(txtCpf.getText());
                    novo.setMatricula(Integer.parseInt(txtMatricula.getText()));
                    dao.inserir(novo);
                } else {
                    aluno.setNome(txtNome.getText());
                    aluno.setCpf(txtCpf.getText());
                    aluno.setMatricula(Integer.parseInt(txtMatricula.getText()));
                    dao.editar(aluno);
                }
                JOptionPane.showMessageDialog(this, "Salvo com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
            }
        });

        add(lblNome); add(txtNome);
        add(lblCpf); add(txtCpf);
        add(lblMatricula); add(txtMatricula);
        add(new JLabel()); add(btnSalvar);
    }
}