package edu.unochapeco.saa;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                        System.out.println("Login Invalido");
                        rest.write("0");
                        continue;
                    }
                    
                    else if(info == null)
                    {
                        rest.write("1");
                        continue;
                    }
                    else if(info.equalsIgnoreCase("notas"))
                    {
                       System.out.println(usuario + ": Notas");
                       JSONnotas = notas.getNotas();
                       rest.write(JSONnotas + "\n");
                    }
                    else if (info.equalsIgnoreCase("materiais"))
                    {
                        System.out.println(usuario + ": Materiais");
                        JSONmaterial = material.getMateriais();
                        rest.write(JSONmaterial + "\n");
                    }
                    else if(info.equalsIgnoreCase("horarios"))
                    {
                        System.out.println(usuario + ": Horarios");
                        JSONhorarios = horarios.getHorarios();
                        rest.write(JSONhorarios + "\n");
                    }
                    else
                    {
                        System.out.println("Opcao invalida");
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
