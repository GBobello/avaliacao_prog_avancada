package Sistema.ui;

import Sistema.dao.NotaDAO;
import Sistema.Nota;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class NotaListPanel extends JPanel {
    public NotaListPanel() {
        setLayout(new BorderLayout());
        JLabel lbl = new JLabel("Lista de Notas", JLabel.CENTER);
        add(lbl, BorderLayout.NORTH);

        String[] colunas = {"ID", "Aluno ID", "Disciplina ID", "Valor"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        try {
            NotaDAO dao = new NotaDAO(Conexao.getConnection());
            List<Nota> notas = dao.listar();
            for (Nota n : notas) {
                model.addRow(new Object[]{
                    n.getId(),
                    n.getAluno() != null ? n.getAluno().getId() : null,
                    n.getDisciplina() != null ? n.getDisciplina().getId() : null,
                    n.getValor()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar notas: " + e.getMessage());
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
                    NotaDAO dao = new NotaDAO(Conexao.getConnection());
                    Nota nota = dao.visualizar(id);
                    JFrame formFrame = new JFrame("Editar Nota");
                    formFrame.setContentPane(new NotaFormPanel(nota));
                    formFrame.pack();
                    formFrame.setLocationRelativeTo(null);
                    formFrame.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao carregar nota: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma nota para editar.");
            }
        });

        btnExcluir.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir esta nota?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        NotaDAO dao = new NotaDAO(Conexao.getConnection());
                        dao.deletar(id);
                        model.removeRow(row);
                        JOptionPane.showMessageDialog(this, "Nota excluída com sucesso!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir nota: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma nota para excluir.");
            }
        });

        btnCadastrar.addActionListener(e -> {
            JFrame formFrame = new JFrame("Cadastrar Nota");
            formFrame.setContentPane(new NotaFormPanel(null));
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