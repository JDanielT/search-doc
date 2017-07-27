package br.edu.ufca.search.doc.daos;

import br.edu.ufca.search.doc.entidades.PessoaFisica;
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
