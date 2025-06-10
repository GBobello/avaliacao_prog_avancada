package Sistema.ui;

import Sistema.dao.AlunoDAO;
import Sistema.Aluno;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AlunoListPanel extends JPanel {

    public AlunoListPanel() {
        setLayout(new BorderLayout());
        JLabel lbl = new JLabel("Lista de Alunos", JLabel.CENTER);
        add(lbl, BorderLayout.NORTH); 

        String[] colunas = {"ID", "Nome", "CPF", "Matrícula"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        try {
            AlunoDAO dao = new AlunoDAO(Conexao.getConnection());
            List<Aluno> alunos = dao.listar();
            for (Aluno a : alunos) {
                model.addRow(new Object[]{a.getId(), a.getNome(), a.getCpf(), a.getMatricula()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar alunos: " + e.getMessage());
        }

        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panelBotoes = new JPanel();
        JButton btnEditar = new JButton("Editar Selecionado");
        JButton btnExcluir = new JButton("Excluir Selecionado");
        JButton btnCadastrar = new JButton("Cadastrar Novo");

       
        btnEditar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                try {
                    AlunoDAO dao = new AlunoDAO(Conexao.getConnection());
                    Aluno aluno = dao.visualizar(id);
                    JFrame formFrame = new JFrame("Editar Aluno");
                    formFrame.setSize(800, 800);
                    formFrame.setResizable(false);
                    formFrame.setContentPane(new AlunoFormPanel(aluno));
                    formFrame.pack();
                    formFrame.setLocationRelativeTo(null);
                    formFrame.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao carregar aluno: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um aluno para editar.");
            }
        });

       
        btnExcluir.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este aluno?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        AlunoDAO dao = new AlunoDAO(Conexao.getConnection());
                        dao.deletar(id);
                        model.removeRow(row);
                        JOptionPane.showMessageDialog(this, "Aluno excluído com sucesso!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir aluno: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um aluno para excluir.");
            }
        });

        
        btnCadastrar.addActionListener(e -> {
            JFrame formFrame = new JFrame("Cadastrar Aluno");
            formFrame.setSize(800, 800);
            formFrame.setResizable(false);
            formFrame.setContentPane(new AlunoFormPanel(null));
            formFrame.pack();
            formFrame.setLocationRelativeTo(null);
            formFrame.setVisible(true);
        });

        panelBotoes.add(btnEditar);
        panelBotoes.add(btnExcluir);
        panelBotoes.add(btnCadastrar);
        add(panelBotoes, BorderLayout.SOUTH);
    }
}