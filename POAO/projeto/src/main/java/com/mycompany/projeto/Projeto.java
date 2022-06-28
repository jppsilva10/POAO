
package com.mycompany.projeto;

import java.io.*;
import java.util.*;
import javax.swing.*;

public class Projeto implements Serializable{
    private String nome;
    private String acronimo;
    private GregorianCalendar inicio;
    private GregorianCalendar fim;
    private int duracao;
    public List<Pessoa> pessoas_envolvidas;
    public List<Tarefa> tarefas;
    private Docente investigador_principal;

    public Projeto(String nome, String acronimo, GregorianCalendar inicio, int duracao, Docente investigador_principal) {///.........
        // mostrar os projetos que ja existem 
        this.nome = nome;
        this.acronimo = acronimo;
        this.inicio = (GregorianCalendar) inicio.clone();
        this.fim= null;
        this.duracao = duracao;
        this.investigador_principal = investigador_principal;
        pessoas_envolvidas= new ArrayList<>();
        pessoas_envolvidas.add(investigador_principal);
        tarefas= new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public GregorianCalendar getInicio() {
        return inicio;
    }

    public GregorianCalendar getFim() {
        return fim;
    }

    public void setFim(GregorianCalendar fim) {
        this.fim = (GregorianCalendar) fim.clone();
    }

    public int getDuracao() {
        return duracao;
    }

    public Docente getInvestigador_principal() {
        return investigador_principal;
    }
    
    
    /**
     * verifiva se um mestrado ou licenciado tem algum orientador que pertensa ao projeto
     * 
     * @param bolseiro  a class do parametro é bolseiro mas o metodo é usado apenas para licenciados ou mestrados 
     * @return          retorna verdadeiro se houver ou falso se não houver
     */
    public boolean pode_associar(Bolseiro bolseiro){
        if(bolseiro.getClass()!= Doutorado.class){
            List<Docente> orientadores = bolseiro.getOrientadores();
            for(int i=0; i<orientadores.size(); i++){
                if(pessoas_envolvidas.contains(orientadores.get(i))){
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * verifica se há problemas em atribuir uma tarefa a uma pessoa na data atual
     * 
     * @param tarefa  tarefa a ser atribuida
     * @param pessoa  pessoa a ser testada (Tem que ser um bolseiro)
     * @param atual   data atual
     * @return        retorna uma string especificando o problema ou uma string vazia se nao houver problemas
     */
    public String AtribuirTarefa(Tarefa tarefa, Pessoa pessoa, GregorianCalendar atual){
        // tarefa e pessoa serao escolhidas na interface
        if(tarefa.getPessoa_responsavel()!=null){
            String str= "Esta tarefa ja foi atribuida a outra pessoa.";
            return str;
        }
        long inicio_pessoa = pessoa.getData_inicio().getTimeInMillis();
        inicio_pessoa/= 1000*60*60*24;
        
        long fim_pessoa = pessoa.getData_fim().getTimeInMillis();
        fim_pessoa/= 1000*60*60*24;
        
        long data_atual= atual.getTimeInMillis();
        data_atual/= 1000*60*60*24;
        
        if(fim_pessoa<data_atual){
            String str= "A bolsa desta pessoa ja acabou.";
            return str;
        }
        else{
            if(pessoa.getData_fim().before(tarefa.getData_fim())){
                 String str= "A bolsa desta pessoa acaba antes do fim do inicio da tarefa.";
            return str;
            }
        }
        
        long inicio_tarefa = tarefa.getData_inicio().getTimeInMillis();
        inicio_tarefa/= 1000*60*60*24;
        
        GregorianCalendar fim_esperado = (GregorianCalendar) tarefa.getData_inicio().clone();
        fim_esperado.add(GregorianCalendar.MONTH, tarefa.getDuracao_estimada());//calcular fim esperado
        long fim_tarefa= fim_esperado.getTimeInMillis() / (1000*60*60*24);
        
        int duracao_dias= (int) (fim_tarefa - inicio_tarefa); // duracao da tarefa em dias
        
        float[] intervalo_tempo = new float[duracao_dias]; // criar intervalo de tempo para ver se a stamina è esgotada
        
        for(int i=0; i<duracao_dias; i++){
            intervalo_tempo[i]+= tarefa.getEsforco();
        }//atribuir o esforco da tarefa
        
        for(int i=0; i<tarefas.size();i++){
            Tarefa work = tarefas.get(i);
            long work_inicio = work.getData_inicio().getTimeInMillis();
            work_inicio/= 1000*60*60*24;
            
            GregorianCalendar work_fim_esperado= (GregorianCalendar) work.getData_inicio().clone();
            work_fim_esperado.add(GregorianCalendar.MONTH, work.getDuracao_estimada()); // fim esperado
            long work_fim = work_fim_esperado.getTimeInMillis();
            work_fim/= 1000*60*60*24;
            
            if(work.getPessoa_responsavel()==null){
               //nada
            }
            else if(work.getPessoa_responsavel().equals(pessoa)){//procura as tarefas da pessoa
                if(work_inicio > inicio_tarefa && work_fim < fim_tarefa){//   [ (   ) ]
                    int j= (int) (work_inicio - inicio_tarefa);
                    
                    for(; j < work_fim - inicio_tarefa; j++){
                        intervalo_tempo[j]+= work.getEsforco();
                        if(intervalo_tempo[j]>1){
                            String str= "Esta pessoa esta sobrecarregada.";
                            return str;
                        }
                    }
                }
                else if(work_inicio <= inicio_tarefa && work_fim >= fim_tarefa){//    (  [   ]  ) 
                    for(int j=0; j<duracao_dias; j++){
                        intervalo_tempo[j]+= work.getEsforco();
                        if(intervalo_tempo[j]>1){
                            String str= "Esta pessoa esta sobrecarregada.";
                            return str;
                        }
                    }
                }
                else if(work_fim > inicio_tarefa && work_fim < fim_tarefa){//    (  [  )  ]
                    for(int j=0; j < work_fim - inicio_tarefa; j++){
                        intervalo_tempo[j]+= work.getEsforco();
                        if(intervalo_tempo[j]>1){
                            String str= "Esta pessoa esta sobrecarregada.";
                            return str;
                        }
                    }
                }
                else if(work_inicio > inicio_tarefa && work_inicio < fim_tarefa){//  [  (  ]  )
                    int j= (int) (work_inicio - inicio_tarefa);
                    
                    for(; j < duracao_dias; j++){
                        intervalo_tempo[j]+= work.getEsforco();
                        if(intervalo_tempo[j]>1){
                            String str= "Esta pessoa esta sobrecarregada.";
                            return str;
                        }
                    }
                }//vê as interceções
            }
        }
        if(fim_pessoa < fim_tarefa){
            String str= "A bolsa desta pessoa acaba antes da data de conclusao esperada da tarefa.";
            return str;
        }
        if(fim_tarefa < inicio_pessoa){
            String str= "A bolsa desta pessoa so comeca depois da data de conclusao esperada da tarefa.";
            return str;
        }
        String str= "";
        return str;
    }
    
    /**
     * calcula o custo que o projeto teve até à data atual ou até à data de fim se já tiver terminado
     * 
     * @param data_atual  data atual
     * @return            retorna o valor do custo calculado
     */
    public float calcula_custo(GregorianCalendar data_atual){ 
        float custo_proj = 0;
        GregorianCalendar fim_trabalho;
        GregorianCalendar inicio_trabalho;
        int meses_dif = 0;
        
        if(data_atual.before(inicio)) return 0;
        
        if(fim != null){
            //se o projeto ja acabou
            fim_trabalho = fim;
        }
        else{
            //se o projeto esta em continuacao // nao ha fim ainda definido // usa-se data atual
            fim_trabalho = data_atual;
        }
            
        for(int i=0; i < pessoas_envolvidas.size() ; i++){
            if(pessoas_envolvidas.get(i).getClass() == Docente.class){
                //nada acontece pq o custo de um docente é 0
            }
            else{
                if( pessoas_envolvidas.get(i).getData_inicio().after(inicio) && pessoas_envolvidas.get(i).getData_inicio().before(fim_trabalho)){ //  [  (  ]  )  ou   [  (  )  ]
                    //se a bolsa começou depois do inicio do projeto e antes do seu fim
                    inicio_trabalho = pessoas_envolvidas.get(i).getData_inicio();
                }
                else if( pessoas_envolvidas.get(i).getData_inicio().before(inicio) && pessoas_envolvidas.get(i).getData_fim().after(inicio)){ //  (  [  )  ]  ou  (  [  ]  )
                    //se o projeto começou depois do inicio bolsa e antes do fim da bolsa 
                    inicio_trabalho = inicio;
                }
                else inicio_trabalho = null;
                    
                if(pessoas_envolvidas.get(i).getData_fim().after(fim_trabalho)  &&  pessoas_envolvidas.get(i).getData_inicio().before(fim_trabalho)){ //   [  (  ]  )   ou  (  [  ]  )
                    //se o projeto acabou antes do fim da bolsa e depois do inicio da bolsa
                    //fim_trabalho é a data final para contar o custo
                    
                    if(inicio_trabalho!=null){
                        GregorianCalendar aux = (GregorianCalendar) inicio_trabalho.clone();
                        
                        //diferenca entre meses
                        while(aux.before(fim_trabalho)){
                            aux.add(GregorianCalendar.MONTH, 1);
                            meses_dif++;
                        }
                        //resultado disso * custo
                        custo_proj += meses_dif * pessoas_envolvidas.get(i).getCusto();
                    }
                }
                else if(pessoas_envolvidas.get(i).getData_fim().before(fim_trabalho)  &&  pessoas_envolvidas.get(i).getData_fim().after(inicio)){ //  (  [  )  ]  ou  [  (  )  ]
                    //se a bolsa acabou antes do fim do projeto e depois  do inicio
                    //fim da bolsa é a data final para contar custo
                    
                    //diferenca entre meses
                    if(inicio_trabalho!=null){
                        GregorianCalendar aux = (GregorianCalendar) inicio_trabalho.clone();
                        
                        //diferenca entre meses
                        while(aux.before(pessoas_envolvidas.get(i).getData_fim())){
                            aux.add(GregorianCalendar.MONTH, 1);
                            meses_dif++;
                        }
                        //resultado disso * custo
                        custo_proj += meses_dif * pessoas_envolvidas.get(i).getCusto();
                    }
                }
            }
        }
        return custo_proj;
    }
    
    /**
     * 
     * @return retorna um arry de strings contendo a informação do projeto
     */
    public String[] print_informacao(){
        String[] str = new String[5];
        str[0]= "Nome: "+nome;
        str[1]= "Acronimo: "+acronimo;
        str[2]= "Data de inicio: "+ inicio.getTime();//.getDay()+"/"+inicio.getTime().getMonth()+"/"+inicio.getTime().getYear();
        if(fim!=null){
            str[3]= "Data de fim: "+ fim.getTime();//.getDay()+"/"+fim.getTime().getMonth()+"/"+fim.getTime().getYear();
        }
        else{
            str[3]= "Data de fim: ";
        }
        str[4]= "Duracao estimada: "+duracao;
        return str;
    }
    
    /**
     * verifica se o projeto não foi concluido dentro do tempo esperado
     * 
     * @param data_atual  data atual para fazer a verificação
     * @return   retorna verdadeiro se não foi e falso se foi
     */
    public boolean nao_concluido_a_tempo(GregorianCalendar data_atual){
        GregorianCalendar fim_esperado = inicio;
        fim_esperado.add((GregorianCalendar.DAY_OF_MONTH), duracao);
        if(fim !=null){
            if(fim_esperado.before(fim))
                return true;
        }
        else{
            if(fim_esperado.before(data_atual))
                return true;
        }
        return false;
    }
    
    /**
     * verifica se nao a data em que o projeto vai ser terminado é depois da data de fim de alguma das suas tarefas
     * 
     * @param data_atual  data em que se pretende terminar o projeto
     * @return  retorn verdadeiro se não e falso se sim
     */
    public boolean pode_ser_terminado(GregorianCalendar data_atual){
        for(int i=0; i<tarefas.size(); i++){
            Tarefa tarefa = tarefas.get(i);
            if(tarefa.getData_fim()!=null){
                if(tarefa.getData_fim().after(data_atual)) return false;
            }
        }
        return true;
    }
    
    /**
     * procura uma pessoa pertencente ao projeto através do seu email
     * 
     * @param email 
     * @return  retorna a pessoa encontrada ou null se não encontrou
     */
    public Pessoa procura_pessoa(String email){
        for (int i = 0; i < pessoas_envolvidas.size(); ++i){
            if(pessoas_envolvidas.get(i).getEmail().equals(email)){
                return pessoas_envolvidas.get(i);
            }
        }
        return null;
    }
    @Override
    public String toString(){
        return nome;
    }
    
}
