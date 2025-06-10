package Sistema;


public class Turma {
	private int id;
	private String nome;
    private int ano;
    private Professor professor;

    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}    
    
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getAno() {
        return ano;
    }
    public void setAno(int ano) {
        this.ano = ano;
    }
    public Professor getProfessor() {
        return professor;
    }
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}
