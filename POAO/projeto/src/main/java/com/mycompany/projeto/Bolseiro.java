
package com.mycompany.projeto;

import java.util.*;

public abstract class Bolseiro extends Pessoa{
    private GregorianCalendar data_inicio;
    private GregorianCalendar data_fim;
    private float custo;
    public List<Docente> orientadores; 

    public Bolseiro(String nome, String email, GregorianCalendar data_inicio, GregorianCalendar data_fim) {
        super(nome, email);
        this.data_inicio = (GregorianCalendar) data_inicio.clone();
        this.data_fim = (GregorianCalendar) data_fim.clone();
    }

    @Override
    public GregorianCalendar getData_inicio() {
        return data_inicio;
    }

    @Override
    public GregorianCalendar getData_fim() {
        return data_fim;
    }

    @Override
    public float getCusto() {
        return custo;
    }

    public void setCusto(float custo) {
        this.custo = custo;
    }
}
