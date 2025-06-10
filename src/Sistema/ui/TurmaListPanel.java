package Sistema.ui;

import Sistema.dao.TurmaDAO;
import Sistema.Turma;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TurmaListPanel extends JPanel {
    public TurmaListPanel() {
        setLayout(new BorderLayout());
        JLabel lbl = new JLabel("Lista de Turmas", JLabel.CENTER);
        add(lbl, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome", "Ano", "Professor ID"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        try {
            TurmaDAO dao = new TurmaDAO(Conexao.getConnection());
            List<Turma> turmas = dao.listar();
            for (Turma t : turmas) {
                model.addRow(new Object[]{t.getId(), t.getNome(), t.getAno(), t.getProfessor() != null ? t.getProfessor().getId() : null});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar turmas: " + e.getMessage());
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
                    TurmaDAO dao = new TurmaDAO(Conexao.getConnection());
                    Turma turma = dao.visualizar(id);
                    JFrame formFrame = new JFrame("Editar Turma");
                    formFrame.setContentPane(new TurmaFormPanel(turma));
                    formFrame.pack();
                    formFrame.setLocationRelativeTo(null);
                    formFrame.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao carregar turma: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma turma para editar.");
            }
        });

        btnExcluir.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir esta turma?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        TurmaDAO dao = new TurmaDAO(Conexao.getConnection());
                        dao.deletar(id);
                        model.removeRow(row);
                        JOptionPane.showMessageDialog(this, "Turma excluída com sucesso!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir turma: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma turma para excluir.");
            }
        });

        btnCadastrar.addActionListener(e -> {
            JFrame formFrame = new JFrame("Cadastrar Turma");
            formFrame.setContentPane(new TurmaFormPanel(null));
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