
package com.mycompany.projeto;

import java.io.*;
import java.util.*;

public abstract class Tarefa implements Serializable{
    private GregorianCalendar data_inicio;
    private GregorianCalendar data_fim;
    private int duracao_estimada;
    private float taxa_execucao;
    private Pessoa pessoa_responsavel;
    private float esforco;
    public Tarefa(GregorianCalendar inicio, int duracao){
        pessoa_responsavel = null;
        data_inicio= (GregorianCalendar) inicio.clone();
        duracao_estimada=duracao;
        taxa_execucao=0;
    }
    public float getEsforco(){
        return esforco;
    }
    
    public void setEsforco(float esforco){
        this.esforco=esforco;
    }
    
    public Pessoa getPessoa_responsavel(){
        return pessoa_responsavel;
    }
    
    public void setPessoa_responsavel(Pessoa pessoa_responsavel) {
        this.pessoa_responsavel = pessoa_responsavel;
    }

    public GregorianCalendar getData_inicio() {
        return data_inicio;
    }

    public GregorianCalendar getData_fim() {
        return data_fim;
    }

    public int getDuracao_estimada() {
        return duracao_estimada;
    }

    public float getTaxa_execucao() {
        return taxa_execucao;
    }

    public void setTaxa_execucao(float taxa_execucao, GregorianCalendar fim) {
        this.taxa_execucao = taxa_execucao;
        if(taxa_execucao==100){
            data_fim= (GregorianCalendar) fim.clone();
        }
    }
    
    /**
     * verifica se a tarefa não foi concluida dentro do tempo esperado
     * 
     * @param data_atual  data atual para fazer a verificação
     * @return   retorna verdadeiro se não foi e falso se foi
     */
    public boolean nao_concluida_a_tempo(GregorianCalendar data_atual){
        //Lista de Tarefas nao Concluidas NA DATA ESTIMADA
        GregorianCalendar fim_estimado = data_inicio;
        fim_estimado.add(GregorianCalendar.MONTH, duracao_estimada);
        if(taxa_execucao==100){
            if(fim_estimado.before(data_fim)){
                return true;
            }
        }
        else{
            if(fim_estimado.before(data_atual)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * verifica se há problemas em atualizar a tarefa
     * 
     * @param data_atual  data atual para a verificação
     * @return   retorna uma setring especificando o problema ou uma string vazia se não houver problemas
     */
    public String pode_ser_atualizada(GregorianCalendar data_atual){
        if(taxa_execucao==100){
            String str= "Esta tarefa ja foi concluida.";
            return str;
        }
        if(pessoa_responsavel==null){
            String str= "Esta tarefa nao foi atribuida a nehuma pessoa.";
            return str;
        }
        if(pessoa_responsavel.getClass()== Doutorado.class || pessoa_responsavel.getClass()== Licenciado.class || pessoa_responsavel.getClass()== Mestrado.class){
            if(pessoa_responsavel.getData_inicio().after(data_atual)){
                String str= "A bolsa da pessoa responsavel por esta tarefa ainda nao comecou.";
                return str;
            }
            if(pessoa_responsavel.getData_fim().before(data_atual)){
                String str= "A bolsa da pessoa responsavel por esta tarefa ja acabou.";
                return str;
            }
        }
        return "";
    }
    
    /**
     * 
     * @return retorna um array de strings contendo a informação da tarefa
     */
    public String[] print_informacao(){
        String[] str = new String[7];
        
        if(this.getClass()==Design.class){
            str[0]= "Design";
        }
        else if(this.getClass()==Documentacao.class){
            str[0]= "Documentacao";
        }
        else{
            str[0]= "Desenvolvimento";
        }
        
        str[1]= "Esforco por dia: " + esforco;
        
        if(pessoa_responsavel!=null){
            str[2]= "Pessoa responsavel: "+ pessoa_responsavel.toString();
        }
        else{
            str[2]= "Pessoa responsavel: ";
        }
        str[3]= "Taxa de Execucao: "+ taxa_execucao +"%";
        str[4]= "Data de inicio: "+ data_inicio.getTime();//.getDay()+"/"+data_inicio.getTime().getMonth()+"/"+data_inicio.getTime().getYear();
        if(data_fim!=null){
            str[5]= "Data de fim: "+ data_fim.getTime();//.getDay()+"/"+data_fim.getTime().getMonth()+"/"+data_fim.getTime().getYear();
        }
        else{
            str[5]= "Data de fim: ";
        }
        str[6]= "Duracao estimada: "+duracao_estimada;
        return str;
    }
    @Override
    public String toString(){
        if(getClass()==Design.class)return "Design " + data_inicio.getTime();
        else if(getClass()==Desenvolvimento.class)return "Desenvolvimento " + data_inicio.getTime();
        else return "Documentacao " + data_inicio.getTime();
    }
}