package edu.unochapeco.saa;

import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

/**
 *
 * @author Daniel Girotto
 */
public class Login {

    private String session = null;

    public String getSession(String user, String pass) throws IOException {
        String url = "https://www.unochapeco.edu.br/usuarios/login";

        if (this.session == null) {
            Connection.Response response = Jsoup.connect(url)
                    .data("login_submited", "1",
                    "usuario", user,
                    "senha", pass,
                    "submit", "entrar")
                    .method(Connection.Method.POST)
                    .execute();
            return response.cookie("PHPSESSID");
        }
        return this.session;
    }
}