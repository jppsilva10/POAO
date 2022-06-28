
package com.mycompany.projeto;

import java.util.*;

public class Docente extends Pessoa{
    private int numero;
    private String area;
    
    public Docente(String nome, String email, int numero, String area) {
        super(nome, email);
        this.area= area;
        this.numero=numero;
    }
    
    @Override
    public String[] print_informacao() {
        String[] str = new String[5];
        str[0]= "Docente";
        str[1]= "Nome: "+ getNome();
        str[2]= "Email: "+ getEmail();
        str[3]= "Numero mecanografico: "+ numero;
        str[4]= "Area de ivestigacao: "+ area;
        return str;
    }

    @Override
    public GregorianCalendar getData_inicio() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GregorianCalendar getData_fim() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float getCusto() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Docente> getOrientadores() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setOrientadores(List<Docente> orientadores) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

