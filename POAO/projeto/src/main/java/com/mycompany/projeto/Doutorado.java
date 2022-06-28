
package com.mycompany.projeto;

import java.util.*;

public class Doutorado extends Bolseiro{

    public Doutorado(String nome, String email, GregorianCalendar data_inicio, GregorianCalendar data_fim) {
        super(nome, email, data_inicio, data_fim);
        setCusto(1200);
    }   

    @Override
    public List<Docente> getOrientadores() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setOrientadores(List<Docente> orientadores) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[] print_informacao() {
        String[] str = new String[6];
        str[0]= "Doutorado";
        str[1]= "Nome: "+ getNome();
        str[2]= "Email: "+ getEmail();
        str[3]= "Data de inicio da bolsa: "+ getData_inicio().getTime();
        str[4]= "Data de fim da bolsa: "+ getData_fim().getTime();
        str[5]= "Custo por mes: "+ getCusto();
        return str;
    }
}
