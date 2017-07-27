package br.edu.ufca.search.doc.entidades;

/**
 *
 * @author daniel
 */
public enum ConstaObito {
    
    SIM("SIM"), NAO("NÃO");

    private ConstaObito(String descricao) {
        this.descricao = descricao;
    }
    
    private String descricao;

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
    
    public static ConstaObito buscarPorDescricao(String descricao){
        System.out.println(descricao);
        return "NÃO".equalsIgnoreCase(descricao) ? ConstaObito.NAO : ConstaObito.SIM;
    }
    
}
