package edu.unochapeco.saa;

import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Daniel Girotto
 */
public class Login {

    private String session = null;
    
    
    public boolean connect(String user, String pass) throws IOException 
    {
        String url = "https://www.unochapeco.edu.br/usuarios/login";

        if (this.session == null) {
            Connection.Response response = Jsoup.connect(url)
                    .data("login_submited", "1",
                    "usuario", user,
                    "senha", pass,
                    "submit", "entrar")
                    .method(Connection.Method.POST)
                    .execute();
            
            this.session = response.cookie("PHPSESSID");
        }
        
         //Testa se o login foi bem sucedido
            Document document = Jsoup.connect(url)
                .cookie("PHPSESSID", this.session)
                .get();

        return !document.text().contains("GRADUAÇÃO PÓS BOLSAS NOTÍCIAS UNOWEBTV EVENTOS INSTITUCIONAL MINHA UNO WEBMAIL CONTATO");
        
        
    }

    public String getSession() throws IOException {
        
        return this.session;
    }
}