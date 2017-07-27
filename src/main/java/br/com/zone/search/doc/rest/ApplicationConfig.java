package br.com.zone.search.doc.rest;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author daniel
 */
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(br.com.zone.search.doc.rest.ConsultaCpfResource.class);
    }
    
}
