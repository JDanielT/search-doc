package br.com.zone.search.doc.daos;

import br.com.zone.search.doc.entidades.PessoaFisica;
import javax.ejb.Stateless;

/**
 *
 * @author daniel
 */
@Stateless
public class PessoaFisicaDAO extends DAOGenerico<PessoaFisica> {
    
    public PessoaFisicaDAO() {
        super(PessoaFisica.class);
    }
    
    public PessoaFisica buscarPorDocumento(String documento){
        return buscarUmResultado("SELECT p FROM PessoaFisica p WHERE p.numeroCpf = ?1", documento);
    }
    
}
