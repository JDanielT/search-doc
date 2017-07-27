package br.com.zone.search.doc.util;

import org.apache.http.HttpHost;

/**
 *
 * @author daniel
 */
public class ProxyUtil {

    public static HttpHost getProxy(String host, int porta) {
        return new HttpHost(host, porta);
    }

}
