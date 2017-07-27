package br.com.zone.search.doc.util;

/**
 *
 * @author daniel
 */
public class CpfUtil {

    public static String format(String cpf){
        cpf = cpf.replaceAll("\\.", "").replaceAll("-", "");
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);
    }
    
}
