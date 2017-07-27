package br.edu.ufca.search.doc.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniel
 */
public class IntelligenceDirectDigitalPropertiesUtil {
    
    private static final Properties PROP = new Properties();
    
    public static Map<String, String> getHeaders(){
        
        Map<String, String> headers = new HashMap<>();
        
        try(InputStream input = IntelligenceDirectDigitalPropertiesUtil.class.getResourceAsStream("/intelligence.directdigital.headers.properties")){
        
            PROP.load(input);
            
            PROP.keySet().forEach(key -> {
                headers.put(key.toString(), PROP.getProperty(key.toString()));
            });
        
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IntelligenceDirectDigitalPropertiesUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IntelligenceDirectDigitalPropertiesUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return headers;
        
    }
        
}
