package br.edu.ufca.search.doc.entidades;

import br.edu.ufca.search.doc.conversores.LocalDatePersistenceConverter;
import br.edu.ufca.search.doc.daos.BaseEntity;
import br.edu.ufca.search.doc.rest.Exclude;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "pessoas_fisicas")
public class PessoaFisica implements BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Exclude
    private Long id;   
    
    @Column(name = "numero_cpf", length = 14, unique = true)
    private String numeroCpf;
    
    @Column(name = "nome_pessoa_fisica")
    private String nomePessoaFisica;
    
    @Column(name = "data_nascimento")
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate dataNascimento;
    
    @Column(name = "situacao_cadastral")
    private String situacaoCadastral;
    
    @Column(name = "data_inscricao")
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate dataInscricao;
    
    @Column(name = "data_inscricao_anterior_1990")
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate dataInscricaoAnterior1990;
    
    @Column(name = "digito_verificador", length = 5)
    private String digitoVerificador;
    
    @Column(name = "consta_obito", length = 3)
    private ConstaObito constaObito;
    
    @Column(name = "data_emissao")
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate dataEmissao;
    
    @Column(name = "codigo_controle_comprovante", length = 35)
    private String codigoControleComprovante;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCpf() {
        return numeroCpf;
    }

    public void setNumeroCpf(String numeroCpf) {
        this.numeroCpf = numeroCpf;
    }

    public String getNomePessoaFisica() {
        return nomePessoaFisica;
    }

    public void setNomePessoaFisica(String nomePessoaFisica) {
        this.nomePessoaFisica = nomePessoaFisica;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSituacaoCadastral() {
        return situacaoCadastral;
    }

    public void setSituacaoCadastral(String situacaoCadastral) {
        this.situacaoCadastral = situacaoCadastral;
    }

    public LocalDate getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(LocalDate dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public LocalDate getDataInscricaoAnterior1990() {
        return dataInscricaoAnterior1990;
    }

    public void setDataInscricaoAnterior1990(LocalDate dataInscricaoAnterior1990) {
        this.dataInscricaoAnterior1990 = dataInscricaoAnterior1990;
    }
    
    public String getDigitoVerificador() {
        return digitoVerificador;
    }

    public void setDigitoVerificador(String digitoVerificador) {
        this.digitoVerificador = digitoVerificador;
    }

    public ConstaObito getConstaObito() {
        return constaObito;
    }

    public void setConstaObito(ConstaObito constaObito) {
        this.constaObito = constaObito;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getCodigoControleComprovante() {
        return codigoControleComprovante;
    }

    public void setCodigoControleComprovante(String codigoControleComprovante) {
        this.codigoControleComprovante = codigoControleComprovante;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PessoaFisica other = (PessoaFisica) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Pessoa{" + "id=" + id + ", numeroCpf=" + numeroCpf + ", nomePessoaFisica=" + nomePessoaFisica + ", dataNascimento=" + dataNascimento + ", situacaoCadastral=" + situacaoCadastral + ", dataInscricao=" + dataInscricao + ", dataInscricaoAnterior1990=" + dataInscricaoAnterior1990 + ", digitoVerificador=" + digitoVerificador + ", constaObito=" + constaObito + ", dataEmissao=" + dataEmissao + ", codigoControleComprovante=" + codigoControleComprovante + '}';
    }

}
