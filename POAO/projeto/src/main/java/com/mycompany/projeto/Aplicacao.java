
package com.mycompany.projeto;

import java.io.*;
import static java.lang.System.*;
import java.util.*;
import javax.swing.*;

public class Aplicacao implements Serializable{
    public List<Pessoa> pessoas;
    public List<Projeto> projetos;
    public Aplicacao() {
        pessoas = new ArrayList<>();
        projetos = new ArrayList<>();
        int i=0;
        Pessoa pessoa;
        File f_pessoa_txt = new File("Pessoas.txt");
        if(f_pessoa_txt.exists() && f_pessoa_txt.isFile()) {
            try { 
                FileReader fr = new FileReader(f_pessoa_txt); 
                BufferedReader br = new BufferedReader(fr);
                String line;
                while((line = br.readLine()) != null) {
                    i++;
                    String[] info = line.split("-");
                    
                    //verificar se há informação suficiente
                    if(info.length<3){
                        String str= "Ficheiro: Pessoas.txt\nLinha "+i+": "+line+"\nInformacao insuficiente.";
                        JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                        line = br.readLine ();
                        do{
                            line = br.readLine();
                            i++;
                        }while(line != null && !line.equals("----------"));
                        if(line==null) break;
                        else continue; // se nao estiver salta para a próxima pessoa
                    }
                    
                    String tipo= info[0];
                    tipo = validar_class_pessoa(info[0], i, line, "Pessoas.txt"); 
                    if(tipo==null){
                        do{
                            line = br.readLine();
                            i++;
                        }while(line != null && !line.equals("----------"));
                        if(line==null) break;
                        else continue; // salta para a próxima pessoa
                    }
                    
                    if(tipo.contentEquals("Docente")){
                        String nome = validar_nome_pessoa(info[1], i, line, "Pessoas.txt");
                        String email = validar_email_pessoa(info[2], i, line, "Pessoas.txt", 0);
                        
                        // verificar se há informacao desnecessária
                        if(info.length>3){ 
                            String str= "Ficheiro: Pessoas.txt\nLinha "+i+": "+ line +"\nInformacao desnecessaria.";
                            JOptionPane.showMessageDialog(null, str, "Warning!", JOptionPane.WARNING_MESSAGE);
                        }
                        
                        // segunda linha de informação
                        i++;
                        // verificar se a informação acaba antes do esperado
                        if((line = br.readLine ())==null){ 
                            String str= "Ficheiro: Pessoas.txt\nLinha "+i+": "+"\nInformacao insuficiente.";
                            JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                            break;
                        }
                        
                        //verificar se há informação suficiente
                        if(info.length<2){
                            String str= "Ficheiro: Pessoas.txt\nLinha "+i+": "+line+"\nInformacao insuficiente.";
                            JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                            continue; // se nao estiver salta para a próxima pessoa
                        }
                        
                        info = line.split("-");
                        int numero = validar_numero(info[0], i, line, "Pessoas.txt");
                        String area = validar_area(info[1], i, line, "Pessoas.txt");
                        
                        //verificar se há informação desnecessária
                        if(info.length>2){
                            String str= "Ficheiro: Pessoas.txt\nLinha "+i+": "+ line +"\nInformacao desnecessaria.";
                            JOptionPane.showMessageDialog(null, str, "Warning!", JOptionPane.WARNING_MESSAGE);
                        }  
                        
                        // verificar se nao hove erros
                        if(tipo!=null && nome!=null && email!=null && numero!=-1 && area!=null){
                            pessoa= new Docente(nome, email, numero, area);
                            pessoas.add(pessoa);
                        }
                        do{
                            line = br.readLine();
                            i++;
                            if(line!=null && !line.equals("----------")){
                                String str= "Ficheiro: Pessoas.txt\nLinha "+i+": "+ line +"\nDocentes nao precisam de orientadores.";
                                JOptionPane.showMessageDialog(null, str, "Warning!", JOptionPane.WARNING_MESSAGE);
                            }
                            else break;
                        }while(true);
                        if(line==null) break;
                        else continue; // salta para a próxima pessoa
                    }
                    
                    else{
                        String nome = validar_nome_pessoa(info[1], i, line, "Pessoas.txt");
                        String email = validar_email_pessoa(info[2], i, line, "Pessoas.txt", 0);
                        
                        //verificar se há informação desnecessária
                        if(info.length>3){
                            String str= "Ficheiro: Pessoas.txt\nLinha "+i+": "+ line +"\nInformacao desnecessaria.";
                            JOptionPane.showMessageDialog(null, str, "Warning!", JOptionPane.WARNING_MESSAGE);
                        }
                        
                        // segunda linha de informação
                        i++;
                        // verificar se a informação acaba antes do esperado
                        if((line = br.readLine ())==null){
                            String str= "Ficheiro: Pessoas.txt\nLinha "+i+"\nInformacao insuficiente.";
                            JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                            break;
                        }
                        
                        //verificar se há informação suficiente
                        if(info.length<2){
                            String str= "Ficheiro: Pessoas.txt\nLinha "+i+": "+line+"\nInformacao insuficiente.";
                            JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                            do{
                                line = br.readLine();
                                i++;
                            }while(line != null && !line.equals("----------"));
                            if(line==null) break;
                            else continue; // se nao estiver salta para a próxima pessoa
                        }
                        
                        info = line.split("-");
                        GregorianCalendar inicio = validar_data(info[0], i, line, "Pessoas.txt");
                        GregorianCalendar fim = validar_data(info[1], i, line, "Pessoas.txt");
                        
                        if(fim==null || inicio==null){
                            //nada
                        }
                        // ver se as datas fazem sentido
                        else if(fim.before(inicio)){
                            String str= "Ficheiro: Pessoas.txt\nLinha "+i+": "+line+"\nA data de fim da bolsa nao pode ser antes da data de inicio.";
                            JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                            fim=null;
                        }
                        
                        //verificar se há informação desnecessária
                        if(info.length>2){
                            String str= "Ficheiro: Pessoas.txt\nLinha "+i+": "+ line +"\nInformacao desnecessaria.";
                            JOptionPane.showMessageDialog(null, str, "Warning!", JOptionPane.WARNING_MESSAGE);
                        }
                        
                        // ver se não houve erros
                        if(tipo==null || nome==null || email==null || inicio==null || fim==null){
                            do{
                                line = br.readLine();
                                i++;
                                if(line!=null && !line.equals("----------")){
                                    String str= "Ficheiro: Pessoas.txt\nLinha "+i+": "+ line +"\nDocentes nao precisam de orientadores.";
                                    JOptionPane.showMessageDialog(null, str, "Warning!", JOptionPane.WARNING_MESSAGE);
                                }
                                else break;
                            }while(true);
                            if(line==null) break;
                            else continue; // salta para a próxima pessoa
                        }
                        
                        else if(tipo.contentEquals("Licenciado")){
                            pessoa = new Licenciado(nome, email, inicio, fim);
                            List<Docente> orientadores= new ArrayList<>();
                            do{
                                line = br.readLine();
                                i++;
                                if(line != null && !line.equals("----------")){
                                    info= line.split(" ");
                                    email = validar_email_pessoa(info[0], i, line, "Pessoas.txt", 1);
                                    if(email!=null){
                                        orientadores.add((Docente) procura_pessoa(email));
                                    }
                                    if(info.length>1){
                                        String str= "Ficheiro: Pessoas.txt\nLinha "+i+": "+ line +"\nInformacao desnecessaria.";
                                        JOptionPane.showMessageDialog(null, str, "Warning!", JOptionPane.WARNING_MESSAGE);
                                    }
                                }
                                else break;
                            }while(true);
                            if(line==null){
                                if(orientadores.size()!=0){
                                    pessoa.setOrientadores(orientadores);
                                    pessoas.add(pessoa);
                                }
                                else{
                                    String str= "Ficheiro: Pessoas.txt\nLinha "+i+"\nLicenciados precisam ter pelo menos um orientador.";
                                    JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            }
                            if(orientadores.size()!=0){
                                pessoa.setOrientadores(orientadores);
                                pessoas.add(pessoa);
                            }
                            else{
                                String str= "Ficheiro: Pessoas.txt\nLinha "+i+"\nLicenciados precisam ter pelo menos um orientador.";
                                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                            }
                            
                        }
                        else if(tipo.contentEquals("Mestrado")){
                            pessoa = new Mestrado(nome, email, inicio, fim);
                            
                            List<Docente> orientadores= new ArrayList<>();
                            do{
                                line = br.readLine();
                                i++;
                                if(line != null && !line.equals("----------")){
                                    info= line.split(" ");
                                    email = validar_email_pessoa(info[0], i, line, "Pessoas.txt", 1);
                                    if(email!=null){
                                        orientadores.add((Docente) procura_pessoa(email));
                                    }
                                    if(info.length>1){
                                        String str= "Ficheiro: Pessoas.txt\nLinha "+i+": "+ line +"\nInformacao desnecessaria";
                                        JOptionPane.showMessageDialog(null, str, "Warning!", JOptionPane.WARNING_MESSAGE);
                                    }
                                }
                                else break;
                            }while(true);
                            
                            if(line==null){
                                if(orientadores.size()!=0){
                                    pessoa.setOrientadores(orientadores);
                                    pessoas.add(pessoa);
                                }
                                else{
                                    String str= "Ficheiro: Pessoas.txt\nLinha "+i+"\nMestrados precisam ter pelo menos um orientador.";
                                    JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            }
                            if(orientadores.size()!=0){
                                pessoa.setOrientadores(orientadores);
                                pessoas.add(pessoa);
                            }
                            else{
                                String str= "Ficheiro: Pessoas.txt\nLinha "+i+"\nMestrados precisam ter pelo menos um orientador.";
                                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        
                        else if(tipo.contentEquals("Doutorado")){
                            pessoa = new Doutorado(nome, email, inicio, fim);
                            pessoas.add(pessoa);
                             do{
                                line = br.readLine();
                                i++;
                                if(line!=null && !line.equals("----------")){
                                    String str= "Ficheiro: Pessoas.txt\nLinha "+i+": "+ line +"\nDouturados nao precisam de orientadores";
                                    JOptionPane.showMessageDialog(null, str, "Warning!", JOptionPane.WARNING_MESSAGE);
                                }
                                else break;
                            }while(true);
                            if(line==null) break;
                            else continue; // salta para a próxima pessoa
                        }
                    }
                }
                br.close();
                fr.close();
            } catch (FileNotFoundException ex) { 
                String str= "Erro ao abrir ficheiro Pessoas.txt.";
                JOptionPane.showMessageDialog(null, str, "Erro!", JOptionPane.ERROR_MESSAGE);
                exit(0);
            } catch (IOException ex) { 
                String str= "Erro ao ler ficheiro Pessoas.txt";
                JOptionPane.showMessageDialog(null, str, "Erro!", JOptionPane.ERROR_MESSAGE);
                exit(0);
            }
        } else {
            String str= "Ficheiro Pessoa.txt não encontrado.";
            JOptionPane.showMessageDialog(null, str, "Erro!", JOptionPane.ERROR_MESSAGE);
            exit(0);
        }
        i=1;
        Projeto projeto;
        File f_projeto_txt = new File("Projetos.txt");
        if(f_projeto_txt.exists() && f_projeto_txt.isFile()) {
            try { 
                FileReader fr = new FileReader(f_projeto_txt); 
                BufferedReader br = new BufferedReader(fr);
                String line; 
                String[] info;
                i=0;
                while((line = br.readLine()) != null) {
                    i++;
                    info = line.split("-");
                    //verificar se há informação suficiente
                    if(info.length<6){
                        String str= "Ficheiro: Projetos.txt\nLinha "+i+": "+line+"\nInformacao insuficiente";
                        JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                        do{
                            line = br.readLine();
                            i++;
                        }while(line != null && !line.equals("----------"));
                        if(line==null) break;
                            
                        do{
                            line = br.readLine();
                        }while(line != null && !line.equals("----------"));
                        if(line==null) break;
                        else continue;  // se não salta para o próximo projeto
                    }
                    
                    String nome = validar_nome_projeto(info[0], i, line, "Projetos.txt");
                    String acronimo = validar_acronimo(info[1], i, line, "Projetos.txt");
                    GregorianCalendar inicio = validar_data(info[2], i, line, "Projetos.txt");
                    int duracao= validar_duracao(info[3], i, line, "Projetos.txt");
                    
                    // verificar se as datas fazem sentido
                    GregorianCalendar fim= null;
                    if (!info[4].equals("")){
                        fim = validar_data(info[4], i, line, "Projetos.txt");
                        if(fim==null || inicio==null){
                            //nada
                        }
                        else if(fim.before(inicio)){
                            String str= "Ficheiro: Projetos.txt\nLinha "+i+": "+line+"\nA data de fim de um projeto nao pode ser antes da sua data de inicio.";
                            JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                            fim=null;
                        }
                    }
                    
                    String email = validar_email_projeto(info[5], i, line, "Projetos.txt", 1);
                    
                    // verificar se existe informacao desnecessária
                    if(info.length>6){
                        String str= "Ficheiro: Projetos.txt\nLinha "+i+": "+ line +"\nInformacao desnecessaria";
                        JOptionPane.showMessageDialog(null, str, "Warning!", JOptionPane.WARNING_MESSAGE);
                    }  
                    
                    // ver se hove erros
                    if(nome==null || acronimo==null || inicio==null || duracao==-1 || email==null){
                        do{
                            line = br.readLine();
                            i++;
                        }while(line != null && !line.equals("----------"));
                        if(line==null) break;
                            
                        do{
                            line = br.readLine();
                        }while(line != null && !line.equals("----------"));
                        if(line==null) break;
                        else continue; // se sim salta para o próximo projeto
                    }
                    
                    pessoa = procura_pessoa(email);
                    projeto = new Projeto(nome, acronimo, inicio, duracao, (Docente) pessoa);
                    if(fim!=null) projeto.setFim(fim);
                    
                    // -------------PESSOAS ENVOLVIDAS------------
                    do{
                        i++;
                        pessoa=null;
                        
                        //verificar se informaçao acaba antes do separador
                        if((line = br.readLine ())==null){
                            String str= "Ficheiro: Projetos.txt\nLinha "+i+"\nInformacao insuficiente";
                            JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                            break;
                        }
                        
                        else if(line.equals("----------")) break;
                        
                        info = line.split(" ");
                        email = validar_email_projeto(info[0], i, line, "Projetos.txt", 0);
                        if (email == null) continue;
                        
                        //verificar se já pertence ao projeto
                        if (projeto.procura_pessoa(email)!=null){
                            String str= "Ficheiro: Projetos.txt\nLinha "+i+": "+line+"\n"+projeto.procura_pessoa(email).getNome()+"ja pertence ao projeto";
                            JOptionPane.showMessageDialog(null, str, "Warnibg!", JOptionPane.WARNING_MESSAGE);
                            continue;
                        }
                        
                        pessoa = procura_pessoa(email);
                        if(pessoa.getClass()==Docente.class){
                            projeto.pessoas_envolvidas.add(pessoa);
                            continue;
                        }
                        else if(envolvida_em_projetos(pessoa)){
                            String str= "Ficheiro: Projetos.txt\nLinha "+i+": "+line+"\n" +pessoa.getNome()+"ja pertence a outro projeto.";
                            JOptionPane.showMessageDialog(null, str, "Warnibg!", JOptionPane.WARNING_MESSAGE);
                            continue;
                        }
                        
                        if(pessoa.getClass()== Licenciado.class || pessoa.getClass()== Mestrado.class){
                            if(!projeto.pode_associar((Bolseiro)pessoa)){
                                String str= "Ficheiro: Projetos.txt\nLinha "+i+": "+line+"\nNenhum dos orientadores pertence ao projeto.";
                                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                            }
                            else{
                                projeto.pessoas_envolvidas.add(pessoa);
                            }
                        }
                        else{
                            projeto.pessoas_envolvidas.add(pessoa);
                        }
                        
                    }while(true);
                    
                    if(line==null){
                        projetos.add(projeto);
                        break;
                    }
                    
                    //---------------------TAREFAS---------------------
                    do{
                        i++;
                        pessoa=null;
                        
                        // verificar se a informação acaba antes do separador
                        if((line = br.readLine ())==null){
                            String str= "Ficheiro: Projetos.txt\nLinha "+i+"\nInformacao insuficiente";
                            JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                            break;
                        }
                        
                        else if(line.equals("----------")) break;
                        
                        info = line.split("-");
                        //verificar se há informação suficiente
                        if(info.length<6){
                            String str= "Ficheiro: Projetos.txt\nLinha "+i+": "+line+"\nInformacao insuficiente";
                            JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                            continue; // se não salta para a proxima tarefa
                        }
                        
                        String tipo = validar_class_tarefa(info[0], i, line, "Projetos.txt");
                        inicio = validar_data(info[1], i, line, "Projetos.txt");
                        
                        // verificar se as datas fazem sentido
                        if(inicio!=null){
                            if(inicio.before(projeto.getInicio())){
                                String str= "Ficheiro: Projetos.txt\nLinha "+i+": "+line+"\nA data de inicio de uma tarefa nao pode ser antes da data de inicio do projeto.";
                                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                                inicio =null;
                            }
                            if(projeto.getFim()!=null){
                                if(inicio.after(projeto.getFim())){
                                    String str= "Ficheiro: Projetos.txt\nLinha "+i+": "+line+"\nA data de inicio de uma tarefa nao pode ser depois da data de fim do projeto.";
                                    JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                                    inicio =null;
                                }
                            }
                        }
                    
                        duracao= validar_duracao(info[2], i, line, "Projetos.txt");
                        
                        Tarefa tarefa;
                        
                        // ver se não houve erros
                        if(tipo==null || inicio==null || duracao==-1) continue;
                        else if(tipo.equals("Design")){
                            tarefa = new Design(inicio, duracao);
                        }
                        else if(tipo.equals("Desenvolvimento")){
                            tarefa = new Desenvolvimento(inicio, duracao);
                        }
                        else{
                            tarefa = new Documentacao(inicio, duracao);
                        }
                    
                        // ---- atribuição----
                        if(!info[3].equals("")){
                            email = validar_email_projeto(info[3], i, line, "Projetos.txt", 0);
                        }
                        else email=null;
                        
                        if(email!=null) {
                            pessoa = projeto.procura_pessoa(email);
                        }
                        if(pessoa==null){
                            if(email!=null){
                                Pessoa aux = procura_pessoa(email);
                                String str= "Ficheiro: Projetos.txt\nLinha "+i+": "+line+"\n"+aux.getNome()+" nao pertence ao projeto.";
                                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        
                        else{
                            if(pessoa.getClass()==Docente.class){
                                String s= AtribuirTarefa(tarefa, pessoa);
                                if(!s.equals("")){
                                    String str= "Ficheiro: Projetos.txt\nLinha "+i+": "+line+"\n"+pessoa.getNome()+" esta sobrecarregado(a).";
                                    JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                                }
                                else tarefa.setPessoa_responsavel(pessoa);
                            }
                            else{
                                String s= projeto.AtribuirTarefa(tarefa, pessoa, inicio);
                                if(s.equals("Esta pessoa esta sobrecarregada.")){
                                    String str= "Ficheiro: Projetos.txt\nLinha "+i+": "+line+"\n"+pessoa.getNome()+" esta sobrecarregado(a).";
                                    JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                                }
                                if(s.equals("A bolsa desta pessoa ja acabou.")){
                                    String str= "Ficheiro: Projetos.txt\nLinha "+i+": "+line+"\nA bolsa de "+pessoa.getNome()+" acaba antes do inicio da tarefa.";
                                    JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                                }
                                else tarefa.setPessoa_responsavel(pessoa);
                            }
                        }
                        // vrificar se as datas fazem sentido
                        fim= null;
                        if (!info[4].equals("")){
                            fim = validar_data(info[4], i, line, "Projetos.txt");
                            if(fim==null || inicio==null){
                                //nada 
                            }
                            else if(fim.before(inicio)){
                                String str= "Ficheiro: Projetos.txt\nLinha "+i+": "+line+"\nA data de inicio de uma tarefa nao pode ser antes da sua data de fim.\nQuer trocar a ordem?";
                                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE); 
                                fim=null;
                            }
                        }
                        
                        if(fim!=null){
                            String s = tarefa.pode_ser_atualizada(fim);
                            if(s.equals("Esta tarefa nao foi atribuida a nehuma pessoa.")){
                                String str= "Ficheiro: Projetos.txt\nLinha "+i+": "+line+"\nA tarefa nao pode ter uma data de fim se nao foi atribuida a ninguem.";
                                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                                fim = null;
                            }
                            else if(s.equals("")){
                                if(projeto.getFim()!=null){
                                    if(fim.after(projeto.getFim())){
                                        String str= "Ficheiro: Projetos.txt\nLinha "+i+": "+line+"\nA tarefa nao pode ser terminada depois do fim do projeto.";
                                        JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                                        fim =null;
                                    }
                                    else tarefa.setTaxa_execucao(100, fim);
                                }
                                else{
                                    tarefa.setTaxa_execucao(100, fim);
                                }
                            }
                            else{
                                String str= "Ficheiro: Projetos.txt\nLinha "+i+": "+line+"\nA data de fim da tarefa nao e compativel com a duracao da bolsa de "+pessoa.getNome() ;
                                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                                fim= null;
                            }
                        }
                        
                        int taxa =validar_taxa(info[5], i, line, "Projetos.txt");
                        if(taxa!=-1){
                            if(tarefa.getPessoa_responsavel()==null && taxa!=0){
                                String str= "Ficheiro: Projetos.txt\nLinha "+i+": "+line+"\nA tarefa nao pode ter sido iniciada se nao foi atribuida a ninguem.";
                                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                            }
                            else if(taxa==100){
                                if(fim==null){
                                    String str= "Ficheiro: Projetos.txt\nLinha "+i+": "+line+"\nA taxa de execucao nao pode ser 100% se a tarefa nao tem data de fim";
                                    JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            else{
                                if(fim!=null){
                                    String str= "Ficheiro: Projetos.txt\nLinha "+i+": "+line+"\nA taxa de execucao tem que ser 100% se a tarefa tem data de fim";
                                    JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                                }
                                else{
                                    tarefa.setTaxa_execucao(taxa, inicio);
                                }
                            }
                        }
                    
                        if(info.length>6){
                            String str= "Ficheiro: Projetos.txt\nLinha "+i+": "+ line +"\nInformacao desnecessaria";
                            JOptionPane.showMessageDialog(null, str, "Warning!", JOptionPane.WARNING_MESSAGE);
                        }
                        projeto.tarefas.add(tarefa);
                    
                    }while(true);
                    projetos.add(projeto);
                    if(line==null) break;
                }
                br.close();
                fr.close();
            } catch (FileNotFoundException ex) { 
                String str = "Erro ao abrir o ficheiro Projetos.txt."; 
                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                exit(0);
            } catch (IOException ex) { 
                String str = "Erro ao ler o ficheiro Projetos.txt."; 
                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                exit(0);
            }
        } else { 
            String str = "Fichiro Projetos.txt nao encontrado."; 
            JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
            exit(0); 
        }
    }
    
    public String validar_class_pessoa(String info, int i, String line, String tipo){
        String str= "Ficheiro: "+ tipo + "\nLinha "+i+": "+line+"\nO tipo \""+ info +"\" nao existe.";
        
        if(!info.equals("Docente") && !info.equals("Licenciado") && !info.equals("Mestrado") && !info.equals("Doutorado")){
            JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return info;  
    }
    
    
    public String validar_class_tarefa(String info, int i, String line, String tipo){
        String str= "Ficheiro: "+ tipo + "\nLinha "+i+": "+line+"\nO tipo \""+ info+ "\" nao existe.";
        
        if(!info.equals("Design") && !info.equals("Desenvolvimento") && !info.equals("Documentacao") && !info.equals("Doutorado")){
            JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return info; 
    }
    
    
    public String validar_nome_pessoa(String info, int i, String line, String tipo){
        String str= "Ficheiro: "+ tipo + "\nLinha "+i+": "+line+"\n\""+ info+ "\"\nOs nomes nao devem conter simbolos ou numeros.";
        
        char[] ch = info.toCharArray();
        for(int j=0; j<info.length(); j++){
            if(!Character.isLetter(ch[j]) && ch[j]!=' '){
                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        // colocar as primeiras letras em maiusculas
        String[] info2 = info.split(" ");
        for(int j=0; j<info2.length; j++){
            if(info2[j].length()>0){
                info2[j].substring(0, 1).toUpperCase();
            }
        }
        info= info2[0];
        for(int j=1; j<info2.length; j++){
            info+= " " + info2[j];
        }
        return info;
    }
    
    
    public String validar_nome_projeto(String info, int i, String line, String tipo){
        String str= "Ficheiro: "+ tipo + "\nLinha "+i+": "+line+"\n\""+ info+ "\"\nOs nomes nao devem conter simbolos ou numeros.";
        
            char[] ch = info.toCharArray();
            for(int j=0; j<info.length(); j++){
                if(!Character.isLetter(ch[j]) && ch[j]!=' '){
                    JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            }
            
            if(procura_projeto(info)!=null){
                str= "Ficheiro: "+ tipo + "\nLinha "+i+": "+line+"\n\""+ info+ "\nO nome ja pertence a outro projeto.";
                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                return null; 
            }
            return info;
    }
    
    
    public String validar_acronimo(String info, int i, String line, String tipo){
        String str= "Ficheiro: "+ tipo + "\nLinha "+i+": "+line+"\n\""+ info+ "\"\nOs acronimos nao devem conter simbolos ou numeros.";
        String titulo= "Erro ao ler o ficheiro!";
        char[] ch = info.toCharArray();
        for(int j=0; j<info.length(); j++){
            if(!Character.isLetter(ch[j]) && ch[j]!=' '){
                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }    
        return info;
    }
    
    
    public String validar_email_pessoa(String info, int i, String line, String tipo, int opcao){
        String str= "Ficheiro: "+ tipo + "\nLinha "+i+": "+line+"\n\""+ info+ "\"\nO email deve terminar em \".com\".";

            if(!info.endsWith(".com")){
                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            if(!info.contains("@")){
                str= "Ficheiro: "+ tipo + "\nLinha "+i+": "+line+"\n\""+ info+ "\"\nO email deve conter o simbolo @.";
                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            if(opcao==0){
                if(procura_pessoa(info)!=null){
                    str= "Ficheiro: "+ tipo + "\nLinha "+i+": "+line+"\n\""+ info+ "\"\nO email ja pertence a outra pessoa.";
                    JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            }
            
            if(opcao!= 0){
                Pessoa pessoa = procura_pessoa(info);
                if(pessoa==null){
                    str= "Ficheiro: "+ tipo + "\nLinha "+i+": "+line+"\n\""+ info+ "\"\nO email nao pertence a nenhuma pessoa.";
                    JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
                else if(pessoa.getClass()!= Docente.class){
                    str= "Ficheiro: "+ tipo + "\nLinha "+i+": "+line+"\n\""+ info+ "\"\nO email nao pertence a um docente.";
                    JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            }
            return info;
    }
    
    public String validar_email_projeto(String info, int i, String line, String tipo, int opcao){
        String str= "Ficheiro: "+ tipo + "\nLinha "+i+": "+line+"\n\""+ info+ "\"\nO email deve terminar em \".com\".";
        
            if(!info.endsWith(".com")){
                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            if(!info.contains("@")){
                str= "Ficheiro: "+ tipo + "\nLinha "+i+": "+line+"\n\""+ info+ "\"\nO email deve conter o simbolo @.";
                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            Pessoa pessoa = procura_pessoa(info);
            if(pessoa==null){
                str= "Ficheiro: "+ tipo + "\nLinha "+i+": "+line+"\n\""+ info+ "\"\nO email nao pertence a nehuma pessoa.";
                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            if(opcao!= 0){
                if(pessoa.getClass()!= Docente.class){
                    str= "Ficheiro: "+ tipo + "\nLinha "+i+": "+line+"\n\""+ info+ "\"\nO email nao pertence a um docente.";
                    JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            }
            return info;
    }
    
    public int validar_numero(String info, int i, String line, String tipo){
        String str= "Ficheiro: "+ tipo + "\nLinha "+i+": "+line+"\n\""+ info+ "\"\nO numero mecanografico tem que ser um valor numerico.";
        
            try{
                if(info.length()==0){
                    JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                    return -1;
                }
                int numero = Integer.parseInt(info);
                return numero;
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                return -1;
            }
    }
    
    
    public int validar_duracao(String info, int i, String line, String tipo){
        String str= "Ficheiro: "+ tipo + "\nLinha "+i+": "+line+"\n\""+ info+ "\"\nA duracao tem que ser um valor numerico.";
        
            try{
                if(info.length()==0){
                    JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                    return -1;
                }
                int numero = Integer.parseInt(info);
                if(numero==0){
                    str= "Ficheiro: "+ tipo + "\nLinha "+i+": "+line+"\n\""+ info+ "\"\nA duracao tem que ser um valor maior que 0.";
                    JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                    return -1;
                } 
                return numero;
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                return -1;
            }
    }
    
    public int validar_taxa(String info, int i, String line, String tipo){
        String str= "Ficheiro: "+ tipo + "\nLinha "+i+": "+line+"\n\""+ info+ "\"\nA taxa de execucao tem que ser um valor numerico.";
        
            try{
                if(info.length()==0){
                    JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                    return -1;
                }
                int numero = Integer.parseInt(info);
                return numero;
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                return -1;
            }
    }
    
    public String validar_area(String info, int i, String line, String tipo){
        String str= "Ficheiro: "+ tipo + "\nLinha "+i+": "+line+"\n\""+ info+ "\"\nA area de investigacao nao deve conter simbolos ou numeros.";
        
            char[] ch = info.toCharArray();
            for(int j=0; j<info.length(); j++){
                if(!Character.isLetter(ch[j]) && ch[j]!=' '){
                    JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            }
        return info;
    }

    
    public GregorianCalendar validar_data(String info, int i, String line, String tipo){
        GregorianCalendar calendario = new GregorianCalendar();
        int[] meses= {4, 6, 9, 11};
        String[] data= new String[3];
            data = info.split("/");
            
            String str= "Ficheiro: "+ tipo + "\nLinha "+i+": "+line+"\n\""+ info+ "\"";
        
            try{
                data = info.split("/");
                if(Integer.parseInt(data[1])>12 || Integer.parseInt(data[1])<=0){
                    str += "\nNumeros fora dos limites.\nDeseja corrigir?";
                    JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
                if(Integer.parseInt(data[2])>2099 || Integer.parseInt(data[2])<2000){
                    str += "\nNumeros fora dos limites.";
                    JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
                if(Integer.parseInt(data[0])>31 || Integer.parseInt(data[0])<=0){
                    str += "\nNumeros fora dos limites.";
                    JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                    return null;    
                }
                else if( Integer.parseInt(data[1])==2 && Integer.parseInt(data[0])>28 && calendario.isLeapYear( Integer.parseInt(data[2]) ) ){
                    if(JOptionPane.showConfirmDialog(null, str + "\n" + data[2]+" e um ano bissexto.", "Error", JOptionPane.YES_NO_OPTION)==0){
                        data[0]="28";
                    }
                    else return null;
                }
                else if(Integer.parseInt(data[1])==2 && Integer.parseInt(data[0])>29){
                    str += "\nNumeros fora dos limites.";
                    JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
                else{
                    for(int j =0;j< meses.length; j++){
                        if(meses[j]==Integer.parseInt(data[1]) && Integer.parseInt(data[0])>30){
                            str += "\nNumeros fora dos limites.";
                            JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                            return null;
                        }
                    }
                }
                calendario = new GregorianCalendar(Integer.parseInt(data[2]), 
                                                   Integer.parseInt(data[1])-1, 
                                                   Integer.parseInt(data[0]));
                return calendario;
            }catch(NumberFormatException ex){
                str += "\nA data deve conter valores numericos.";
                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                return null;
            }catch (IndexOutOfBoundsException ex) {
                str += "\nA data tem que estar no formato: dia/mes/ano.";
                JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        
    }
    
    /**
     * procura um projeto pertencente à aplicação usando o seu nome
     * 
     * @param nome
     * @return  retorna o projeto encontrado ou null se não encontrou
     */
    public Projeto procura_projeto(String nome){
        for (int i = 0; i < projetos.size(); ++i){
            if(projetos.get(i).getNome().equals(nome)){
                return projetos.get(i);
            }
        }
        return null;
    }
    
    /**
     * procura uma pessoa pertenceente à aplicação usando o seu email
     * 
     * @param email 
     * @return  retorna a pessoa encontrada ou null se não encontrou
     */
    public Pessoa procura_pessoa(String email){
        for (int i = 0; i < pessoas.size(); ++i){
            if(pessoas.get(i).getEmail().equals(email)){
                return pessoas.get(i);
            }
        }
        return null;
    }
    
    /**
     * guarda os dados em ficheiro objeto
     */
    public void salvar_dados(){
        
        File f_app_objeto = new File("Aplicacao.obj");

        try { 
            FileOutputStream fos = new FileOutputStream(f_app_objeto); 
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            oos.writeObject(this);
            
            oos.close(); 
            fos.close();
        } 
        catch (FileNotFoundException ex) { 
            String str ="Erro ao criar o ficheiro Aplicacao.obj.";
            JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE);
        } 
        catch (IOException ex) { 
            String str ="Erro ao escrever os dados para o ficheiro Aplicacao.obj.";
            JOptionPane.showMessageDialog(null, str, "Error!", JOptionPane.ERROR_MESSAGE); 
        }
    }
    
    /**
     * verifica se há problemas em atribuir uma tarefa a uma pessoa
     * (usada em docentes já que podem ter trefas de projetos diferentes)
     * 
     * @param tarefa tarefa a ser atribuída 
     * @param pessoa pessoa a ser testada
     * @return  retorna uma string especificando o problema ou uma string vazia se não houver
     */
    public String AtribuirTarefa(Tarefa tarefa, Pessoa pessoa){
        // tarefa e pessoa serao escolhidas na interface
        if(tarefa.getPessoa_responsavel()!=null){
            String str= "Esta tarefa ja foi atribuida a outra pessoa.";
            return str;
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
        
        for(int k=0; k<projetos.size(); k++){
            Projeto projeto = projetos.get(k);
            if(projeto.pessoas_envolvidas.contains(pessoa)){
                for(int i=0; i<projeto.tarefas.size();i++){
                    Tarefa work = projeto.tarefas.get(i);
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
            }
        }
        String str= "";
        return str;
    }
    
    /**
     * verifica se a pessoa está envolvida em algum projeto
     * 
     * @param pessoa   
     * @return  retorna verdadeiro se sim e falso se não
     */
    boolean envolvida_em_projetos(Pessoa pessoa){
        for(int i=0; i<projetos.size(); i++){
           if(projetos.get(i).pessoas_envolvidas.contains(pessoa)){
               return true;
           } 
        }
        return false;
    }
    
    /**
     * verifica se uma pessoa pode ser associada a um projeto
     * 
     * @param projeto  projeto para fazer a verifaicação
     * @param pessoa  pessoa a ser testada
     * @return   retorna verdadeiro se pode e falso se não
     */
    public boolean pode_associar(Projeto projeto, Pessoa pessoa){
        if(projeto.pessoas_envolvidas.contains(pessoa)){
            return false;
        }
        if(pessoa.getClass()== Docente.class){
            return true;
        }
        if(envolvida_em_projetos(pessoa)){
            return false;
        }
        else if(pessoa.getClass()!= Doutorado.class){
            return projeto.pode_associar((Bolseiro) pessoa);
        }
        else return true;
    }
}
