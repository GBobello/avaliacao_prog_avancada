package Sistema.ui;

import Sistema.dao.MatriculaDAO;
import Sistema.Matricula;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MatriculaListPanel extends JPanel {
    public MatriculaListPanel() {
        setLayout(new BorderLayout());
        JLabel lbl = new JLabel("Lista de Matrículas", JLabel.CENTER);
        add(lbl, BorderLayout.NORTH);

        String[] colunas = {"ID", "Aluno ID", "Disciplina ID"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        try {
            MatriculaDAO dao = new MatriculaDAO(Conexao.getConnection());
            List<Matricula> matriculas = dao.listar();
            for (Matricula m : matriculas) {
                model.addRow(new Object[]{
                    m.getId(),
                    m.getAluno() != null ? m.getAluno().getId() : null,
                    m.getDisciplina() != null ? m.getDisciplina().getId() : null
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar matrículas: " + e.getMessage());
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
                    MatriculaDAO dao = new MatriculaDAO(Conexao.getConnection());
                    Matricula matricula = dao.visualizar(id);
                    JFrame formFrame = new JFrame("Editar Matrícula");
                    formFrame.setContentPane(new MatriculaFormPanel(matricula));
                    formFrame.pack();
                    formFrame.setLocationRelativeTo(null);
                    formFrame.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao carregar matrícula: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma matrícula para editar.");
            }
        });

        btnExcluir.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir esta matrícula?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        MatriculaDAO dao = new MatriculaDAO(Conexao.getConnection());
                        dao.deletar(id);
                        model.removeRow(row);
                        JOptionPane.showMessageDialog(this, "Matrícula excluída com sucesso!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir matrícula: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma matrícula para excluir.");
            }
        });

        btnCadastrar.addActionListener(e -> {
            JFrame formFrame = new JFrame("Cadastrar Matrícula");
            formFrame.setContentPane(new MatriculaFormPanel(null));
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