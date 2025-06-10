package Sistema.ui;

import Sistema.dao.DisciplinaDAO;
import Sistema.Disciplina;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DisciplinaListPanel extends JPanel {
    public DisciplinaListPanel() {
        setLayout(new BorderLayout());
        JLabel lbl = new JLabel("Lista de Disciplinas", JLabel.CENTER);
        add(lbl, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome", "Código"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        try {
            DisciplinaDAO dao = new DisciplinaDAO(Conexao.getConnection());
            List<Disciplina> disciplinas = dao.listar();
            for (Disciplina d : disciplinas) {
                model.addRow(new Object[]{d.getId(), d.getNome(), d.getCodigo()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar disciplinas: " + e.getMessage());
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
                    DisciplinaDAO dao = new DisciplinaDAO(Conexao.getConnection());
                    Disciplina disciplina = dao.visualizar(id);
                    JFrame formFrame = new JFrame("Editar Disciplina");
                    formFrame.setContentPane(new DisciplinaFormPanel(disciplina));
                    formFrame.pack();
                    formFrame.setLocationRelativeTo(null);
                    formFrame.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao carregar disciplina: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma disciplina para editar.");
            }
        });

        btnExcluir.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir esta disciplina?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        DisciplinaDAO dao = new DisciplinaDAO(Conexao.getConnection());
                        dao.deletar(id);
                        model.removeRow(row);
                        JOptionPane.showMessageDialog(this, "Disciplina excluída com sucesso!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir disciplina: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma disciplina para excluir.");
            }
        });

        btnCadastrar.addActionListener(e -> {
            JFrame formFrame = new JFrame("Cadastrar Disciplina");
            formFrame.setContentPane(new DisciplinaFormPanel(null));
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