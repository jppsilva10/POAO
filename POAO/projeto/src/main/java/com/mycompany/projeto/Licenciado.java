
package com.mycompany.projeto;

import java.util.*;

public class Licenciado extends Bolseiro{  

    public Licenciado(String nome, String email, GregorianCalendar data_inicio, GregorianCalendar data_fim) {
        super(nome, email, data_inicio, data_fim);
        orientadores= new ArrayList<>();
        setCusto(800);
    }

    @Override
    public String[] print_informacao() {
        String[] str = new String[7];
        str[0]= "Licenciado";
        str[1]= "Nome: "+ getNome();
        str[2]= "Email: "+ getEmail();
        str[3]= "Data de inicio da bolsa: "+ getData_inicio().getTime();
        str[4]= "Data de fim da bolsa: "+ getData_fim().getTime();
        str[5]= "Custo por mes: "+ getCusto();
        if(orientadores.size()==0){
            str[6]= "Orientadores: ";
        }
        else{
            str[6]= "Orientadores:";
            for(int i=0; i<orientadores.size(); i++){
                str[6]+= " " + orientadores.get(i).getNome();
            }
        }
        return str;
    }

    @Override
    public List<Docente> getOrientadores() {
        return orientadores;
    }

    @Override
    public void setOrientadores(List<Docente> orientadores) {
        this.orientadores=orientadores;
    }
    
}
