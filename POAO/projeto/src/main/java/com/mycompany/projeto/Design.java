
package com.mycompany.projeto;

import java.util.*;

public class Design extends Tarefa{
    public Design(GregorianCalendar inicio, int duracao){
        super(inicio, duracao);
        setEsforco((float) 0.5);
    }
}