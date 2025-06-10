package Sistema.ui;

import Sistema.dao.EscolaDAO;
import Sistema.Escola;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EscolaListPanel extends JPanel {
    public EscolaListPanel() {
        setLayout(new BorderLayout());
        JLabel lbl = new JLabel("Lista de Escolas", JLabel.CENTER);
        add(lbl, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        try {
            EscolaDAO dao = new EscolaDAO(Conexao.getConnection());
            List<Escola> escolas = dao.listar();
            for (Escola e : escolas) {
                model.addRow(new Object[]{e.getId(), e.getNome()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar escolas: " + e.getMessage());
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
                    EscolaDAO dao = new EscolaDAO(Conexao.getConnection());
                    Escola escola = dao.visualizar(id);
                    JFrame formFrame = new JFrame("Editar Escola");
                    formFrame.setContentPane(new EscolaFormPanel(escola));
                    formFrame.pack();
                    formFrame.setLocationRelativeTo(null);
                    formFrame.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao carregar escola: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma escola para editar.");
            }
        });

        btnExcluir.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir esta escola?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        EscolaDAO dao = new EscolaDAO(Conexao.getConnection());
                        dao.deletar(id);
                        model.removeRow(row);
                        JOptionPane.showMessageDialog(this, "Escola excluída com sucesso!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir escola: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma escola para excluir.");
            }
        });

        btnCadastrar.addActionListener(e -> {
            JFrame formFrame = new JFrame("Cadastrar Escola");
            formFrame.setContentPane(new EscolaFormPanel(null));
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