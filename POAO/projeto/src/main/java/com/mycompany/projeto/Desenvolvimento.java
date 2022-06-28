
package com.mycompany.projeto;

import java.util.GregorianCalendar;

public class Desenvolvimento extends Tarefa{
    public Desenvolvimento(GregorianCalendar inicio, int duracao){
        super(inicio, duracao);
        setEsforco(1);
    }
}
