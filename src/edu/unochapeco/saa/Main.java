package edu.unochapeco.saa;

import java.io.IOException;

/**
 *
 * @author Daniel Girotto
 */
public class Main {

    public static void main(String[] args) {
        Login login = new Login();
        
        try {
            if(login.connect("", ""))
            {
                String session = login.getSession();
                MaterialApoio apoio = new MaterialApoio(session);
                String materiais = apoio.getMateriais();

                System.out.println(materiais);
            }
            else
                System.out.println("Login Invalido");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
