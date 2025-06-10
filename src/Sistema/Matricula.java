package Sistema;

public class Matricula {
	private int id;
    private Aluno aluno;
    private Disciplina disciplina;

    public Aluno getAluno() {
        return aluno;
    }
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
    public Disciplina getDisciplina() {
        return disciplina;
    }
    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}