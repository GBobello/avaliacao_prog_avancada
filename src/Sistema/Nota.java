package Sistema;

public class Nota {
	private int id;
    private Aluno aluno;
    private Disciplina disciplina;
    private double valor;

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
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}