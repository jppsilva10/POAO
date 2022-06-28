
package com.mycompany.projeto;

import java.util.*;

public class Documentacao extends Tarefa{
    public Documentacao(GregorianCalendar inicio, int duracao){
        super(inicio,duracao);
        setEsforco((float) 0.25);
    }
}
