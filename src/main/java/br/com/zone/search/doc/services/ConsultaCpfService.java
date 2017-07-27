package br.com.zone.search.doc.services;

import br.com.zone.search.doc.daos.PessoaFisicaDAO;
import br.com.zone.search.doc.entidades.ConstaObito;
import br.com.zone.search.doc.entidades.PessoaFisica;
import br.com.zone.search.doc.rest.ConstaObitoDeserializer;
import br.com.zone.search.doc.rest.LocalDateDeserializer;
import br.com.zone.search.doc.util.CpfUtil;
import br.com.zone.search.doc.util.IntelligenceDirectDigitalPropertiesUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author daniel
 *
 * Essa classe consulta o CPF informado no site
 * http://intelligence.directdigital.com.br
 */
@Model
public class ConsultaCpfService{

    @Inject
    private PessoaFisicaDAO dao;

    private final static String URL = "http://intelligence.directdigital.com.br/open/consultas/execute";

    public PessoaFisica buscarPessoa(String cpf, String nascimento, HttpHost proxy) {
        
        cpf = CpfUtil.format(cpf);
        
        PessoaFisica p = dao.buscarPorDocumento(cpf);

        if (p == null) {

            try (CloseableHttpClient client = getHttpClient(proxy)) {

                //conexao via get a fim de recuperar cookies
                HttpGet httpGet = new HttpGet(URL);

                try (CloseableHttpResponse getResponse = client.execute(httpGet)) {
                   
                    HttpPost httpPost = new HttpPost(URL);

                    //setando headers definidos no arquivo .properties de configuração
                    IntelligenceDirectDigitalPropertiesUtil.getHeaders().entrySet().forEach(entry -> {
                        httpPost.setHeader(entry.getKey(), entry.getValue());
                    });
                    
                    //setando cookies recuperados via get
                    httpPost.addHeader("Cookie", getResponse.getHeaders("Set-Cookie")[0].getValue());
                 
                    List<NameValuePair> params = new ArrayList<>();
                    params.add(new BasicNameValuePair("receita_federal_pf[cpf]", cpf));
                    params.add(new BasicNameValuePair("receita_federal_pf[data_nascimento]", nascimento));
                    params.add(new BasicNameValuePair("class_name", "ReceitaFederalPf"));

                    httpPost.setEntity(new UrlEncodedFormEntity(params));

                    try (CloseableHttpResponse postResponse = client.execute(httpPost)) {
                   
                        BufferedReader rd = new BufferedReader(new InputStreamReader(
                                postResponse.getEntity().getContent()));

                        String html = "";

                        String line;
                        while ((line = rd.readLine()) != null) {
                            html += line;
                        }
                        
                        p = htmlToPessoFisica(html);
                        
                        if(p.getNumeroCpf() != null && !p.getNumeroCpf().isEmpty()){
                            this.salvarPessoa(p);
                        }
                        
                    }

                }

            } catch (Exception ex) {
                Logger.getLogger(IntelligenceDirectDigitalPropertiesUtil.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return p;

    }

    private PessoaFisica htmlToPessoFisica(String html) {

        final String LINHA = "TR";
        final String COLUNA = "TD";
        final String SPAN = "SPAN";

        Document doc = Jsoup.parse(removerCarcteresEstranhos(html));
        Elements linhas = doc.getElementsByTag(LINHA);

        String array[] = new String[20];

        JsonObject obj = new JsonObject();

        for (int i = 0, l = 0, v = 1; i < linhas.size(); i++, l = l + 2, v = v + 2) {

            Element colunas = linhas.get(i);

            for (int j = 0; j < colunas.getElementsByTag(COLUNA).size(); j++) {

                Element element = colunas.getElementsByTag(COLUNA).get(j);

                if (j % 2 == 0) {
                    String label = element.getElementsByTag(SPAN).html();
                    array[l] = firstToLowerCase(removerEspacosVazios(label));
                } else {
                    array[v] = element.html().toUpperCase();
                }

                obj.addProperty(array[l], array[v]);

            }

        }

        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .registerTypeAdapter(ConstaObito.class, new ConstaObitoDeserializer())
                .create();

        return g.fromJson(obj.toString(), PessoaFisica.class);

    }

    private static String removerCarcteresEstranhos(String s) {
        return s.replaceAll("=\\\"", "=\"").replaceAll("<\\\\/", "</").replaceAll("\\\\n", "\n");
    }

    private static String firstToLowerCase(String s) {
        return s.replaceFirst(s.substring(0, 1), s.substring(0, 1).toLowerCase());
    }

    private static String removerEspacosVazios(String s) {
        return s.replaceAll("\\s", "");
    }

    //cria uma nova thread para salvar os dados pesquisados 
    //a fim de diminuir o tempo de resposta da consulta ao serviço
    private void salvarPessoa(PessoaFisica pf) {
        new Thread(() -> {
            dao.salvar(pf);
        }).start();
    }

    public CloseableHttpClient getHttpClient(HttpHost proxy) {
        CloseableHttpClient client;
        if (proxy == null) {
            client = HttpClients.createDefault();
        } else {
            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
            client = HttpClients.custom().setRoutePlanner(routePlanner).build();
        }
        return client;
    }

}
