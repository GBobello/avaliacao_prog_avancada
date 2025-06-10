package Sistema.ui;

import javax.swing.*;
import java.awt.*;

public class EscolaSwingApp extends JFrame {
    public EscolaSwingApp() {
        setTitle("Sistema Escolar");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        // Menu Aluno
        JMenu menuAluno = new JMenu("Alunos");
        JMenuItem itemListarAluno = new JMenuItem("Listar");
        itemListarAluno.addActionListener(e -> {
            setContentPane(new AlunoListPanel());
            this.pack();
        });
        menuAluno.add(itemListarAluno);
        menuBar.add(menuAluno);

        // Menu Disciplina
        JMenu menuDisciplina = new JMenu("Disciplinas");
        JMenuItem itemListarDisciplina = new JMenuItem("Listar");
        itemListarDisciplina.addActionListener(e -> {
            setContentPane(new DisciplinaListPanel());
            this.pack();
        });
        menuDisciplina.add(itemListarDisciplina);
        menuBar.add(menuDisciplina);

        // Menu Turma
        JMenu menuTurma = new JMenu("Turmas");
        JMenuItem itemListarTurma = new JMenuItem("Listar");
        itemListarTurma.addActionListener(e -> {
          setContentPane(new TurmaListPanel());
          this.pack();
        });
        menuTurma.add(itemListarTurma);
        menuBar.add(menuTurma);

        // Menu Escola
        JMenu menuEscola = new JMenu("Escolas");
        JMenuItem itemListarEscola = new JMenuItem("Listar");
        itemListarEscola.addActionListener(e -> {
            setContentPane(new EscolaListPanel());
            this.pack();
        });
        menuEscola.add(itemListarEscola);
        menuBar.add(menuEscola);

        // Menu Professor
        JMenu menuProfessor = new JMenu("Professores");
        JMenuItem itemListarProfessor = new JMenuItem("Listar");
        itemListarProfessor.addActionListener(e -> {
            setContentPane(new ProfessorListPanel());
            this.pack();
        });
        menuProfessor.add(itemListarProfessor);
        menuBar.add(menuProfessor);

        // Menu Nota
        JMenu menuNota = new JMenu("Notas");
        JMenuItem itemListarNota = new JMenuItem("Listar");
        itemListarNota.addActionListener(e -> {
            setContentPane(new NotaListPanel());
            this.pack();
        });
        menuNota.add(itemListarNota);
        menuBar.add(menuNota);

        // Menu Matrícula
        JMenu menuMatricula = new JMenu("Matrículas");
        JMenuItem itemListarMatricula = new JMenuItem("Listar");
        itemListarMatricula.addActionListener(e -> {
            setContentPane(new MatriculaListPanel());
            this.pack();
        });
        menuMatricula.add(itemListarMatricula);
        menuBar.add(menuMatricula);

        setJMenuBar(menuBar);
        setContentPane(new JPanel()); // Tela inicial vazia
    }
}