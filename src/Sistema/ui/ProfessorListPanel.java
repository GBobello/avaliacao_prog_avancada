package Sistema.ui;

import Sistema.dao.ProfessorDAO;
import Sistema.Professor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProfessorListPanel extends JPanel {

    public ProfessorListPanel() {
        setLayout(new BorderLayout());
        JLabel lbl = new JLabel("Lista de Professores", JLabel.CENTER);
        add(lbl, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome", "CPF", "Especialidade"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        try {
            ProfessorDAO dao = new ProfessorDAO(Conexao.getConnection());
            List<Professor> professores = dao.listar();
            for (Professor p : professores) {
                model.addRow(new Object[]{p.getId(), p.getNome(), p.getCpf(), p.getEspecialidade()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar professores: " + e.getMessage());
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
                    ProfessorDAO dao = new ProfessorDAO(Conexao.getConnection());
                    Professor professor = dao.visualizar(id);
                    JFrame formFrame = new JFrame("Editar Professor");
                    formFrame.setContentPane(new ProfessorFormPanel(professor));
                    formFrame.pack();
                    formFrame.setLocationRelativeTo(null);
                    formFrame.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao carregar professor: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um professor para editar.");
            }
        });

        btnExcluir.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este professor?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        ProfessorDAO dao = new ProfessorDAO(Conexao.getConnection());
                        dao.deletar(id);
                        model.removeRow(row);
                        JOptionPane.showMessageDialog(this, "Professor excluído com sucesso!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir professor: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um professor para excluir.");
            }
        });

        btnCadastrar.addActionListener(e -> {
            JFrame formFrame = new JFrame("Cadastrar Professor");
            formFrame.setContentPane(new ProfessorFormPanel(null));
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