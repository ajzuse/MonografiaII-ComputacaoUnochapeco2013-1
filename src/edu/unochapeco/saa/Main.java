package edu.unochapeco.saa;

import IceBreakRestServer.IceBreakRestServer;
import java.io.IOException;

/**
 *
 * @author Daniel Girotto
 * IceBreak Rest Server
 * https://code.google.com/p/icebreakrest/wiki/IceBreak
 */
public class Main {

    public static void main(String[] args) {
        IceBreakRestServer rest;   // Declare the HTTP server class

        try { 
            rest  = new IceBreakRestServer();  
            rest.setPort(90);

            while (true) {
                rest.getHttpRequest();     
                String usuario = rest.getQuery("usuario");
                String senha = rest.getQuery("senha");
                String info = rest.getQuery("info");


                if(usuario != null || senha != null)
                {
                    Login login = new Login();
                    login.connect(usuario, senha);
                    String sessao = login.getSession();

                    NotasGraduacao notas = new NotasGraduacao(sessao);
                    MaterialApoio material = new MaterialApoio(sessao);
                    HorariosSemestre horarios = new HorariosSemestre(sessao);

                    if(info == null || info.equalsIgnoreCase("notas")){
                       rest.write(notas.getNotas() + "\n");
                    }

                    if (info == null || info.equalsIgnoreCase("materiais"))
                    {
                        rest.write(material.getMateriais() + "\n");
                    }

                    if(info == null || info.equalsIgnoreCase("horarios"))
                    {
                        rest.write(horarios.getHorarios() + "\n");
                    }
                }
                else {
                    rest.write("Parâmetros Inválidos");
                }
            }
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
