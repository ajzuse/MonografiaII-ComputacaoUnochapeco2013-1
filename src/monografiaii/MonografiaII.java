/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monografiaii;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author ajzuse
 */
public class MonografiaII {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        String user = "_username";
        String pass = "_password";

        String login = "https://www.unochapeco.edu.br/usuarios/login";

        /*
         * Passo a URL principal de login e alguns dados obrigatÛrios
         * por POST
         */
        Connection.Response response = Jsoup.connect(login)
                .data("login_submited", "1", "usuario", user, "senha", pass,
                        "submit", "entrar")
                .method(Method.POST)
                .execute();

        /*
         * Preciso pegar o valor de "PHPSESSID" e depois simplesmente
         * chamo as outras urls por GET passando o valor do Cookie.
         */
        String session = response.cookie("PHPSESSID");

        String URL = "https://www.unochapeco.edu.br/saa/materialApoio.php?op=disc&coddisc=1030292&codgrade=348&codturma=A";
        Document document = Jsoup.connect(URL)
                .cookie("PHPSESSID", session)
                .get();

        System.out.println(document);
    }

}
