public class NotasFaltas {

    private String unidade;
    private String periodo;
    private String curso;
    private String turma;
    private String serie;
    private String situacaoDaMatricula;

    public NotasFaltas(String unidade, String periodo, String curso, String turma, String serie, String situacaoDaMatricula) {
        this.unidade = unidade;
        this.periodo = periodo;
        this.curso = curso;
        this.turma = turma;
        this.serie = serie;
        this.situacaoDaMatricula = situacaoDaMatricula;
    }

    public String getUnidade() {
        return unidade;
    }

    public String getPeriodo() {
        return periodo;
    }

    public String getCurso() {
        return curso;
    }

    public String getTurma() {
        return turma;
    }

    public String getSerie() {
        return serie;
    }

    public String getSituacaoDaMatricula() {
        return situacaoDaMatricula;
    }
}
