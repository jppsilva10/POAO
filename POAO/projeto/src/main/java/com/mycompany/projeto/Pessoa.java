
package com.mycompany.projeto;

import java.io.*;
import java.util.*;

public abstract class Pessoa implements Serializable{
    private String nome;
    private String email;
    public Pessoa(String nome, String email){
        this.nome = nome;
        this.email= email;
    }
    
    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
    
    @Override
    public String toString(){
        return nome;
    }
    
    abstract public List<Docente> getOrientadores();
    abstract public void setOrientadores(List<Docente> orientadores);
    abstract public GregorianCalendar getData_inicio();
    abstract public GregorianCalendar getData_fim();
    abstract public float getCusto();
    abstract public String[] print_informacao();
}


