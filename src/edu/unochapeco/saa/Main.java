package edu.unochapeco.saa;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel Girotto
 * IceBreak Rest Server
 * https://code.google.com/p/icebreakrest/wiki/IceBreak
 */
public class Main {

    public static void main(String[] args) {
        RestServer rest;   // Declare the HTTP server class

        try { 
            rest  = new RestServer();  
            rest.setPort(90);

            while (true) {
                rest.getHttpRequest();     
                String usuario = rest.getQuery("usuario");
                String senha = rest.getQuery("senha");
                String info = rest.getQuery("info");


                if(usuario != null || senha != null)
                {
                    Login login = new Login();
                    boolean loginValido = login.connect(usuario, senha);
                    String sessao = login.getSession();
                    String JSONnotas, JSONmaterial, JSONhorarios;
                    NotasGraduacao notas = new NotasGraduacao(sessao);
                    MaterialApoio material = new MaterialApoio(sessao);
                    HorariosSemestre horarios = new HorariosSemestre(sessao);

                    if(!loginValido)
                    {
                        rest.write("Erro: Login Inválido");
                        continue;
                    }
                    else if(info.equalsIgnoreCase("notas"))
                    {
                       //Ajuste no tamanho para materiais: 14
                       JSONnotas = notas.getNotas();
                       rest.write(JSONnotas + "\n");
                    }
                    else if (info.equalsIgnoreCase("materiais"))
                    {
                        //Ajuste no tamanho para materiais: 153
                        JSONmaterial = material.getMateriais();
                        rest.write(JSONmaterial + "\n");
                    }
                    else if(info.equalsIgnoreCase("horarios"))
                    {
                        //Ajuste de tamanho para horarios: 181
                        JSONhorarios = horarios.getHorarios();
                        rest.write(JSONhorarios + "\n");
                    }
                    else
                    {
                        rest.write("Opção Inválida");
                    }
                    
                    Thread.sleep(1);
                }
                else {
                    rest.write("Parâmetros Inválidos");
                }
            }
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
