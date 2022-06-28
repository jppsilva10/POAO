
package com.mycompany.projeto;

import java.awt.*;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.*; 
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import static java.lang.System.*;

public class POAO_projeto extends JFrame{
    Aplicacao app;
    Projeto projeto;
    Pessoa pessoas;
    Pessoa pessoas_associadas;
    GregorianCalendar Data;
    int salvo;
    Integer[] types_dias1 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
    Integer[] types_dias2 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30};
    Integer[] types_dias3 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29};
    Integer[] types_dias4 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28};
    JComboBox combobox_dia;
    JComboBox combobox_mes;
    JComboBox combobox_ano_dezenas;
    JComboBox combobox_ano_unidades;
    //projetos:
    JLabel label_Nome_projeto;
    Font f = new Font("Serif", Font.PLAIN, 40);
    DefaultListModel<Projeto> lista_projetos_values;
    JList<Projeto> lista_projetos;
    JComboBox combobox_lista_projetos;
    JButton button_selecionar_projetos;
    JButton button_criar_projetos;
    JTextField text_nome_projeto;
    JTextField text_acronimo_projeto;
    JComboBox combobox_projeto_duracao_dezenas;
    JComboBox combobox_projeto_duracao_unidades;
    //menu:
    JButton button_eliminar;
    JButton button_atribuir;
    JButton button_atualizar;
    JTextField text_atualizar;
    JButton button_custo;
    JButton button_associa;
    JButton button_terminar;
    JButton button_salvar;
    //tarefas:
    JComboBox combobox_lista_tarefas;
    DefaultListModel<Tarefa> lista_tarefas_values;
    JList<Tarefa> lista_tarefas;
    JButton button_criar_tarefas;
    JComboBox combobox_tipos_tarefas;
    JComboBox combobox_tarefa_duracao_dezenas;
    JComboBox combobox_tarefa_duracao_unidades;
    //info:
    JList<String> text_info;
    DefaultListModel<String> text_info_values;
    JButton button_informacao;
    //pessoas:
    JComboBox combobox_lista_pessoas;
    DefaultListModel<Pessoa> lista_pessoas_values;
    JList<Pessoa> lista_pessoas;
    //pessoas associadas:
    DefaultListModel<Pessoa> lista_pessoas_associadas_values;
    JList<Pessoa> lista_pessoas_associadas;
    
    public static void main(String[] args) {
        POAO_projeto frame = new POAO_projeto(); 
        frame.setTitle("Aplicacao"); 
        frame.setSize(400, 300); 
        
    }
    
    private class Action implements ActionListener { 
        @Override 
        public void actionPerformed(ActionEvent e) { 
            
            //------------------------- PROJETOS-------------------------
            
            if(e.getSource() == button_selecionar_projetos) {
                text_info_values.clear();
                if(lista_projetos.getSelectedValue()!=null){
                    projeto= lista_projetos.getSelectedValue();
                    label_Nome_projeto.setText(projeto.getNome());
                    lista_projetos.clearSelection();
                    // seleciona o projeto
                    
                    lista_pessoas_associadas_values.clear();
                    for(int i=0; i<projeto.pessoas_envolvidas.size(); i++){
                        lista_pessoas_associadas_values.addElement(projeto.pessoas_envolvidas.get(i));
                    }  
                    // coloca as pessoas associadas na lista
                    
                    lista_tarefas_values.clear();
                    for (int i = 0; i < projeto.tarefas.size(); ++i){
                        combobox_lista_tarefas_action(projeto.tarefas.get(i));
                    }
                    //coloca as tarefas na lista
                    
                    if(combobox_lista_pessoas.getSelectedIndex()==5){
                        lista_pessoas_values.clear();
                        for (int i = 0; i < app.pessoas.size(); ++i){
                            combobox_lista_pessoas_action(app.pessoas.get(i));
                        }
                        // coloca as pessoas na lista se estiver na opção de pessoas que posso associar
                    }
                }
                else{
                    text_info_values.addElement("Tem que escolher um projeto para selecionar.");
                }
            }
            if(e.getSource() == button_criar_projetos) {
                
                text_info_values.clear();
                text_nome_projeto.setBackground(Color.WHITE);
                text_acronimo_projeto.setBackground(Color.WHITE);
                String nome= text_nome_projeto.getText();
                int erro=0;
                
                char[] ch = nome.toCharArray();
                for(int j=0; j<nome.length(); j++){
                    if(!Character.isLetter(ch[j]) && ch[j]!=' '){
                        text_info_values.addElement("Nomes nao podem ter numeros ou simbolos.");
                        text_nome_projeto.setBackground(Color.PINK);
                        erro=-1;
                        break;
                    }
                }
                 
                if(nome.length()==0){
                    text_info_values.addElement("Tem que escolher um nome.");
                    text_nome_projeto.setBackground(Color.PINK);
                    erro=-1;
                }    
                
                if(app.procura_projeto(nome)!=null){
                    text_info_values.addElement("Ja existe um projeto com esse nome.");
                    text_nome_projeto.setBackground(Color.PINK);
                    erro=-1;
                }
                // validar nome
                    
                String acronimo= text_acronimo_projeto.getText();
                ch = acronimo.toCharArray();
                for(int j=0; j<acronimo.length(); j++){
                    if(!Character.isLetter(ch[j]) && ch[j]!=' '){
                        text_info_values.addElement("Acronimos nao podem ter numeros ou simbolos.");
                        text_acronimo_projeto.setBackground(Color.PINK);
                        erro=-1;
                        break;
                    }
                }
                    
                if(acronimo.length()==0){
                    text_info_values.addElement("Tem que escolher um acronimo.");
                    text_acronimo_projeto.setBackground(Color.PINK);
                    erro=-1;
                }
                //validar acronimo  
                
                Pessoa docente = lista_pessoas.getSelectedValue();
                if(docente==null){
                    text_info_values.addElement("Tem que escolher um investigador principal.");
                    erro=-1;
                }
                
                else if(docente.getClass()!=Docente.class){
                    text_info_values.addElement("O investigador principal tem que ser um docente.");
                    erro=-1;
                }
                // validar docente
                    
                int duracao= combobox_projeto_duracao_dezenas.getSelectedIndex()*10;
                duracao+= combobox_projeto_duracao_unidades.getSelectedIndex();
                if(duracao == 0){
                    text_info_values.addElement("O valor da duracao tem que ser maior que 0.");
                    erro=-1;
                }
                //validar duracao
   
                if(erro==0){
                    Projeto novo_projeto = new Projeto(nome, acronimo, Data, duracao, (Docente) docente);
                    app.projetos.add(novo_projeto);
                    text_info_values.addElement("Projeto criado.");
                    text_nome_projeto.setText("");
                    text_acronimo_projeto.setText("");
                    lista_pessoas.clearSelection();
                    combobox_lista_projetos_action(novo_projeto);
                    salvo=0;
                }
            }
            
            //--------------------------MENU--------------------------
            
            if(e.getSource() == button_eliminar) {
                // verificar se o projeto já terminou
                text_info_values.clear();
                if(projeto.getFim()!=null){
                    text_info_values.addElement("Não pode fazer alterações a um projeto que ja acabou.");
                    lista_tarefas.clearSelection();
                }
                // eliminar tarefa
                else if(lista_tarefas.getSelectedValue()!=null){
                    if(JOptionPane.showConfirmDialog(null, "Tem certeza de que quer elimina esta tarefa?", "Mensage", JOptionPane.YES_NO_OPTION)==0){
                        Tarefa tarefa = lista_tarefas.getSelectedValue();
                        app.projetos.remove(projeto);
                        lista_projetos_values.removeElement(projeto);
                        projeto.tarefas.remove(tarefa);
                        lista_tarefas_values.removeElement(tarefa);
                        app.projetos.add(projeto);
                        combobox_lista_projetos_action(projeto);
                        text_info_values.addElement("A tarefa foi eliminada.");
                        salvo=0;
                    }
                    lista_tarefas.clearSelection();
                }
                else{
                    text_info_values.addElement("Tem que escolher uma tarefa para eliminar.");
                }
            }
            
            if(e.getSource() == button_atribuir) {
                text_info_values.clear();
                Tarefa tarefa=null;
                Pessoa pessoa=null;
                int erro=0;
                // verificar se o projeto já acabou
                if(projeto.getFim()!=null){
                    text_info_values.addElement("Não pode fazer alterações a um projeto que ja acabou.");
                    lista_tarefas.clearSelection();
                    lista_pessoas.clearSelection();
                    lista_pessoas_associadas.clearSelection();
                    erro=-1;
                }
                else{
                    // verificar se alguma tarefa foi escolhida
                    if(lista_tarefas.getSelectedValue()==null){
                        text_info_values.addElement("Tem que escolher uma tarefa para atribuir.");
                        erro=-1;
                    }
                    else{
                        tarefa = lista_tarefas.getSelectedValue();
                    }
                
                    // verificar se alguma foi escolhida
                    if(lista_pessoas_associadas.getSelectedValue()!=null){
                        pessoa = lista_pessoas_associadas.getSelectedValue();
                    }
                    else if(lista_pessoas.getSelectedValue()!=null){
                        pessoa = lista_pessoas.getSelectedValue();
                        if(!projeto.pessoas_envolvidas.contains(pessoa)){
                            text_info_values.addElement("A pessoa tem que estar envolvida no projeto.");
                            lista_pessoas.clearSelection();
                            erro=-1;
                        }
                    }
                    else{
                        text_info_values.addElement("Tem que escolher uma pessoa para atribuir a tarefa.");
                        erro=-1;
                    }
                }
                
                if(erro==0){
                    if(pessoa.getClass()== Docente.class){
                        String str = app.AtribuirTarefa(tarefa, pessoa);
                        if(!str.equals("")){
                            text_info_values.addElement(str);
                            erro=-1;
                        }
                    }
                    // verificar se posso atribuir a tarefa
                    else{
                        String str = projeto.AtribuirTarefa(tarefa, pessoa, Data);
                        
                        if(str.equals("A bolsa desta pessoa acaba antes da data de conclusao esperada da tarefa.")){
                            if(JOptionPane.showConfirmDialog(null, str+"\nTem certeza?", "Mensage", JOptionPane.YES_NO_OPTION)!=0){
                                erro=-1;
                                lista_tarefas.clearSelection();
                                lista_pessoas.clearSelection();
                                lista_pessoas_associadas.clearSelection();
                            }  
                        }
                        
                        else if(str.equals("A bolsa desta pessoa so comeca depois da data de conclusao esperada da tarefa.")){
                            if(JOptionPane.showConfirmDialog(null, str+"\nTem certeza?", "Mensage", JOptionPane.YES_NO_OPTION)!=0){
                                lista_tarefas.clearSelection();
                                lista_pessoas.clearSelection();
                                lista_pessoas_associadas.clearSelection();
                                erro=-1;
                            }  
                        }
                        
                        else if(!str.equals("")){
                            text_info_values.addElement(str);
                            erro=-1;
                        }
                    }
                }
                if(erro==0){
                    text_info_values.addElement("A tarefa foi atribuida.");
                    app.projetos.remove(projeto);
                    lista_projetos_values.removeElement(projeto);
                    projeto.tarefas.remove(tarefa);
                    lista_tarefas_values.removeElement(tarefa);
                    tarefa.setPessoa_responsavel(pessoa);
                    projeto.tarefas.add(tarefa);
                    app.projetos.add(projeto);
                    combobox_lista_tarefas_action(tarefa);
                    combobox_lista_projetos_action(projeto);
                    lista_tarefas.clearSelection();
                    lista_pessoas.clearSelection();
                    lista_pessoas_associadas.clearSelection();
                    salvo=0;
                }
            }
            
            if(e.getSource() == button_atualizar) { 
                try{
                    float valor=0;
                    int erro=0;
                    Tarefa tarefa= null;
                    text_info_values.clear();
                    text_atualizar.setBackground(Color.WHITE);
                    // verificar se o projeto já foi terminado
                    if(projeto.getFim()!=null){
                        text_info_values.addElement("Não pode fazer alterações a um projeto que ja acabou.");
                        lista_tarefas.clearSelection();
                        erro=-1;
                    }
                    // verificar se alguma tarefa foi selicionada
                    else if(lista_tarefas.getSelectedValue()==null){
                        text_info_values.addElement("Tem que escolher uma tarefa para atualizar.");
                        erro=-1;
                    }
                    // verifcar se a tarefa pode ser atualizada
                    else{
                        tarefa= lista_tarefas.getSelectedValue();
                        if(!tarefa.pode_ser_atualizada(Data).equals("")){
                            text_info_values.addElement(tarefa.pode_ser_atualizada(Data));
                            lista_tarefas.clearSelection();
                            erro=-1;
                        }
                        else{
                            if(text_atualizar.getText().length()==0){
                                text_info_values.addElement("Tem que escolher um valor para atualizar a tarefa.");
                                text_atualizar.setBackground(Color.PINK);
                                erro=-1;
                            }
                            else{
                                valor = Float.valueOf(text_atualizar.getText());
                                if(valor <0 || valor>100){
                                    text_info_values.addElement("O valor da atualizacao tem que ser entre 0 e 100.");
                                    text_atualizar.setBackground(Color.PINK);
                                    erro=-1;
                                }
                            }
                        }
                    }
                    if(erro==0){
                        // verificar se as datas fazem sentido
                        text_atualizar.setText("");
                        if(valor == 100 && Data.before(tarefa.getData_inicio())){
                            String str= "A data de fim de uma tarefa nao pode ser antes da sua data de inicio.";
                            JOptionPane.showMessageDialog(null, str, "Erro!", JOptionPane.ERROR_MESSAGE);
                        }
                        else{
                            app.projetos.remove(projeto);
                            lista_projetos_values.removeElement(projeto);
                            projeto.tarefas.remove(tarefa);
                            lista_tarefas_values.removeElement(tarefa);
                            tarefa.setTaxa_execucao(valor, Data);
                            projeto.tarefas.add(tarefa);
                            app.projetos.add(projeto);
                            text_info_values.addElement("A tarefa foi atualizada.");
                            combobox_lista_projetos_action(projeto);
                            combobox_lista_tarefas_action(tarefa);
                            salvo=0;
                        }
                        lista_tarefas.clearSelection();
                    }
                } catch(NumberFormatException ex){
                    text_info_values.addElement("A atualizacao tem que ter um valor numerico.");
                    text_atualizar.setBackground(Color.PINK);
                }
            }
            
            if(e.getSource() == button_custo) { 
                text_info_values.clear();
                if(projeto != null){
                    float custo;
                    custo = projeto.calcula_custo(Data);
                    text_info_values.addElement(String.valueOf(custo));
                }
                else{
                    text_info_values.addElement("Tem de selecionar um projeto para calcular o seu custo.");
                }
            }
            
            if(e.getSource() == button_associa){
                text_info_values.clear();
                int erro=-1;
                Pessoa pessoa =null;
                if(projeto != null){
                    if(lista_pessoas.getSelectedValue() != null){
                        pessoa = lista_pessoas.getSelectedValue();
                        if(projeto.pessoas_envolvidas.contains(pessoa)){
                            text_info_values.addElement("Esta pessoa já pertence ao projeto!");
                        }
                        else{
                            if(pessoa.getClass() == Docente.class){
                                erro=0;
                            }
                            else if(!app.envolvida_em_projetos(pessoa)){
                                if(pessoa.getClass()== Doutorado.class){
                                    erro=0;
                                }
                                else if(projeto.pode_associar((Bolseiro) pessoa)){
                                    erro=0;
                                }
                                else{
                                    text_info_values.addElement("Nenhum dos orientadores desta pessoa pertence ao projeto.");
                                }
                            }
                            else{
                                text_info_values.addElement("Esta pessoa ja esta envolvida em outro projeto.");
                            }
                            
                        }
                    }
                    else{
                        text_info_values.addElement("Tem que escolher uma pessia para associar ao projeto.");
                    }
                }
                else{
                    text_info_values.addElement("Nunhum projeto foi selecionado.");
                }
                if(erro==0){
                    salvo=0;
                    app.projetos.remove(projeto);
                    lista_projetos_values.removeElement(projeto);
                    projeto.pessoas_envolvidas.add(pessoa);
                    app.projetos.add(projeto); 
                    combobox_lista_projetos_action(projeto);
                    lista_pessoas_associadas_values.addElement(pessoa);
                    if(combobox_lista_pessoas.getSelectedIndex()==5){
                        lista_pessoas_values.removeElement(pessoa);
                    }
                }
            }
            
            if(e.getSource() == button_terminar) { 
                text_info_values.clear();
                //verificar se algum projeto foi selecionado
                if(projeto==null){
                    text_info_values.addElement("Nenhum projeto foi selecionado.");
                }
                // verificar se o projeto já foi terminado
                else if(projeto.getFim()!=null){
                    text_info_values.addElement("O projeto ja foi terminado.");
                }
                else{
                    // verificar se as datas fazem sentido
                    if(Data.before(projeto.getInicio())){
                        String str= "A data de fim de um projeto nao pode ser antes da sua data de inicio.";
                        JOptionPane.showMessageDialog(null, str, "Erro!", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(!projeto.pode_ser_terminado(Data)){
                        String str= "Existem tarefas com data de fim posterior à data atual.";
                        JOptionPane.showMessageDialog(null, str, "Erro!", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        app.projetos.remove(projeto);
                        lista_projetos_values.removeElement(projeto);
                        projeto.setFim(Data);
                        app.projetos.add(projeto);
                        combobox_lista_projetos_action(projeto);
                        text_info_values.addElement("O projeto " + projeto.getNome() + " foi terminado.");
                        salvo=0;
                    }
                }
            }
            
            if(e.getSource() == button_salvar) { 
                text_info_values.clear();
                // verificar se é necessário salvar os dados
                if(salvo==0){
                    app.salvar_dados();
                    salvo=1;
                    text_info_values.addElement("Dados salvos");
                }
                else{
                    text_info_values.addElement("Os dados já foram salvos");
                }
            }
            
            if(e.getSource() == button_criar_tarefas) {
                text_info_values.clear();
                // verificar se as datas fazem sentido
                if(Data.before(projeto.getInicio())){
                    String str= "A data de inicio de uma tarefa nao pode ser antes da data de inicio do seu projeto.";
                    JOptionPane.showMessageDialog(null, str, "Erro!", JOptionPane.ERROR_MESSAGE);
                }
                
                else{
                    Tarefa tarefa= null;
                    int duracao= combobox_tarefa_duracao_dezenas.getSelectedIndex()*10;
                    duracao+= combobox_tarefa_duracao_unidades.getSelectedIndex();
                    // verificar duracao
                    if(duracao==0){
                        text_info_values.addElement("O valor da duracao tem que ser maior que 0.");
                    }                
                    else{
                        if(combobox_tipos_tarefas.getSelectedIndex() == 0){
                            tarefa = new Design(Data, duracao);
                        }
                        if(combobox_tipos_tarefas.getSelectedIndex() == 1){
                            tarefa = new Desenvolvimento(Data, duracao);
                        }
                        if(combobox_tipos_tarefas.getSelectedIndex() == 2){
                            tarefa = new Documentacao(Data , duracao);
                        }
                        text_info_values.addElement("Tarefa criada.");
                        app.projetos.remove(projeto);
                        lista_projetos_values.removeElement(projeto);
                        projeto.tarefas.add(tarefa);
                        app.projetos.add(projeto);
                        combobox_lista_projetos_action(projeto);
                        combobox_lista_tarefas_action(tarefa);
                        salvo=0;
                    }
                }
            }
            //--------------------------OUTPUT---------------------------
            if(e.getSource() == button_informacao) { 
                if(lista_projetos.getSelectedValue()!=null){
                    Projeto aux= lista_projetos.getSelectedValue();
                    text_info_values.clear();
                    for(int i=0; i<aux.print_informacao().length; i++){
                        text_info_values.addElement(aux.print_informacao()[i]);
                    }
                    lista_projetos.clearSelection();
                }
                else if(lista_tarefas.getSelectedValue()!=null){
                    if(projeto!=null){
                        Tarefa aux= lista_tarefas.getSelectedValue();
                        text_info_values.clear();
                        for(int i=0; i<aux.print_informacao().length; i++){
                            text_info_values.addElement(aux.print_informacao()[i]);
                        }
                    }
                    lista_tarefas.clearSelection();
                }
                else if(lista_pessoas.getSelectedValue()!=null){
                    Pessoa aux= lista_pessoas.getSelectedValue();
                    text_info_values.clear();
                    for(int i=0; i<aux.print_informacao().length; i++){
                        text_info_values.addElement(aux.print_informacao()[i]);
                    }
                    lista_pessoas.clearSelection();
                }
                else if(lista_pessoas_associadas.getSelectedValue()!=null){
                    Pessoa aux= lista_pessoas_associadas.getSelectedValue();
                    text_info_values.clear();
                    for(int i=0; i<aux.print_informacao().length; i++){
                        text_info_values.addElement(aux.print_informacao()[i]);
                    }
                    lista_pessoas_associadas.clearSelection();
                }
            }
        } 
    }
    
    //-----------------------------LISTAGEM--------------------------------
    
    private class ComboBoxAction implements ActionListener { 
        @Override public void actionPerformed(ActionEvent e) { 
            if(e.getSource() == combobox_lista_projetos){
                lista_projetos_values.clear();
                for (int i = 0; i < app.projetos.size(); ++i){
                    combobox_lista_projetos_action(app.projetos.get(i));
                }
            }
            if(e.getSource() == combobox_lista_tarefas){
                lista_tarefas_values.clear();
                if(projeto!=null){
                    for (int i = 0; i < projeto.tarefas.size(); ++i){
                        combobox_lista_tarefas_action(projeto.tarefas.get(i));
                    }
                }
            }
            if(e.getSource() == combobox_lista_pessoas){
                lista_pessoas_values.clear();
                for (int i = 0; i < app.pessoas.size(); ++i){
                    combobox_lista_pessoas_action(app.pessoas.get(i));
                }
            }
            if(e.getSource() == combobox_dia){
                Data.set(2000 + combobox_ano_dezenas.getSelectedIndex()*10+
                         combobox_ano_unidades.getSelectedIndex(),
                         combobox_mes.getSelectedIndex(),
                         combobox_dia.getSelectedIndex()+1);
            }
            if(e.getSource() == combobox_mes){
                int ano= 2000 + combobox_ano_dezenas.getSelectedIndex()*10+
                         combobox_ano_unidades.getSelectedIndex();
                int mes= combobox_mes.getSelectedIndex();
                
                if(Data.isLeapYear(ano) && mes+1 == 2){
                    combobox_dia.removeAllItems();
                    for(int i=0; i< types_dias3.length; i++){
                       combobox_dia.addItem(types_dias3[i]); 
                    }
                }
                else if(mes+1 == 2){
                    combobox_dia.removeAllItems();
                    for(int i=0; i< types_dias4.length; i++){
                       combobox_dia.addItem(types_dias4[i]); 
                    }
                }
                else if(mes+1== 4 || mes+1== 6 || mes+1== 9 || mes+1== 11){
                    combobox_dia.removeAllItems();
                    for(int i=0; i< types_dias2.length; i++){
                       combobox_dia.addItem(types_dias2[i]); 
                    }
                    
                }
                else{
                    combobox_dia.removeAllItems();
                    for(int i=0; i< types_dias1.length; i++){
                       combobox_dia.addItem(types_dias1[i]); 
                    }
                }    
                Data.set(ano, mes , combobox_dia.getSelectedIndex()+1);
            }
            if(e.getSource() == combobox_ano_dezenas){
                int ano= 2000 + combobox_ano_dezenas.getSelectedIndex()*10+
                         combobox_ano_unidades.getSelectedIndex();
                int mes= combobox_mes.getSelectedIndex();
                
                if(Data.isLeapYear(ano) && mes+1 == 2){
                    combobox_dia.removeAllItems();
                    for(int i=0; i< types_dias3.length; i++){
                       combobox_dia.addItem(types_dias3[i]); 
                    }
                }
                else if(mes+1 == 2){
                    combobox_dia.removeAllItems();
                    for(int i=0; i< types_dias4.length; i++){
                       combobox_dia.addItem(types_dias4[i]); 
                    }
                }
                Data.set(ano, mes, combobox_dia.getSelectedIndex()+1);
            }
            if(e.getSource() == combobox_ano_unidades){
                int ano= 2000 + combobox_ano_dezenas.getSelectedIndex()*10+
                         combobox_ano_unidades.getSelectedIndex();
                int mes= combobox_mes.getSelectedIndex();
                
                if(Data.isLeapYear(ano) && mes+1 == 2){
                    combobox_dia.removeAllItems();
                    for(int i=0; i< types_dias3.length; i++){
                       combobox_dia.addItem(types_dias3[i]); 
                    }
                }
                else if(mes+1 == 2){
                    combobox_dia.removeAllItems();
                    for(int i=0; i< types_dias4.length; i++){
                       combobox_dia.addItem(types_dias4[i]); 
                    }
                }
                Data.set(ano, mes, combobox_dia.getSelectedIndex()+1);
            }
            
        } 
    }
    
    public void combobox_lista_projetos_action(Projeto projeto){
        if(combobox_lista_projetos.getSelectedIndex() == 0){
            lista_projetos_values.addElement(projeto);
        }
        if(combobox_lista_projetos.getSelectedIndex() == 1){
            if(projeto.getFim()!=null){
                lista_projetos_values.addElement(projeto);
            }
        }
        if(combobox_lista_projetos.getSelectedIndex() == 2){
            if(projeto.nao_concluido_a_tempo(Data) ){
                lista_projetos_values.addElement(projeto);
            }
        }        
    }
    
    public void combobox_lista_tarefas_action(Tarefa tarefa){
        if(projeto!=null){
            if(combobox_lista_tarefas.getSelectedIndex() == 0){
                lista_tarefas_values.addElement(tarefa);
            }
            if(combobox_lista_tarefas.getSelectedIndex() == 1){
                if(tarefa.getTaxa_execucao()==0){
                    lista_tarefas_values.addElement(tarefa);
                }
            }
            if(combobox_lista_tarefas.getSelectedIndex() == 2){
                if(tarefa.nao_concluida_a_tempo(Data)){
                    lista_tarefas_values.addElement(tarefa);
                }
            }
            if(combobox_lista_tarefas.getSelectedIndex() == 3){
                if(tarefa.getTaxa_execucao()==100){
                    lista_tarefas_values.addElement(tarefa);
                }
            }
        }
    }
    
    public void combobox_lista_pessoas_action(Pessoa pessoa){
        if(combobox_lista_pessoas.getSelectedIndex() == 0){
            lista_pessoas_values.addElement(pessoa);
        }
        if(combobox_lista_pessoas.getSelectedIndex() == 1){
            if(pessoa.getClass()== Docente.class){
                lista_pessoas_values.addElement(pessoa);
            }    
        }
        if(combobox_lista_pessoas.getSelectedIndex() == 2){
            if(pessoa.getClass()== Doutorado.class){
                lista_pessoas_values.addElement(pessoa);
            }    
        }
        if(combobox_lista_pessoas.getSelectedIndex() == 3){
            if(pessoa.getClass()== Mestrado.class){
                lista_pessoas_values.addElement(pessoa);
                }    
            }
        if(combobox_lista_pessoas.getSelectedIndex() == 4){
            if(pessoa.getClass()== Licenciado.class){
                lista_pessoas_values.addElement(pessoa);
            }    
        }
        if(combobox_lista_pessoas.getSelectedIndex() == 5){
            if(projeto!=null){
                if(app.pode_associar(projeto, pessoa)){
                    lista_pessoas_values.addElement(pessoa);
                }
            }
        }
    }
    
    //---------------------------CONSTRUTOR------------------------
    
    
    public POAO_projeto() { 
        super();
        this.setTitle("Aplicacao"); 
        this.setSize(400, 300);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        
        File f_app_objeto = new File("Aplicacao.obj");
        if(f_app_objeto.exists() && f_app_objeto.isFile()){
            try { 
                FileInputStream fis = new FileInputStream(f_app_objeto); 
                ObjectInputStream ois = new ObjectInputStream(fis);
            
                app= (Aplicacao)ois.readObject();
            
                ois.close();
                fis.close();
            } catch (FileNotFoundException ex) {
                String str= "Erro ao abrir ficheiro Aplicacao.obj.";
                JOptionPane.showMessageDialog(null, str, "Erro!", JOptionPane.ERROR_MESSAGE);
                exit(0);
            } catch (IOException ex) { 
                String str= "Erro ao ler o ficheiro Aplicacao.obj.";
                JOptionPane.showMessageDialog(null, str, "Erro!", JOptionPane.ERROR_MESSAGE);
                    //exit(0);
            } catch (ClassNotFoundException ex) { 
                String str= "Erro ao converter os dados do ficheiro Aplicacao.obj.";
                JOptionPane.showMessageDialog(null, str, "Erro!", JOptionPane.ERROR_MESSAGE);
                exit(0);
            }
        }
        else{
            app = new Aplicacao();
            app.salvar_dados();
        }
        Data =new GregorianCalendar(2000, 0, 1);
        salvo=1;
        JPanel main_panel = new JPanel();
        main_panel.setLayout(new BorderLayout());
        
        label_Nome_projeto = new JLabel("\n\n");
        label_Nome_projeto.setFont(f);
        JPanel panel_Nome_projeto =new JPanel();
        panel_Nome_projeto.add(label_Nome_projeto);
        main_panel.add(panel_Nome_projeto, BorderLayout.NORTH);
        
        //------------panel_projetos:-----------
        JPanel panel_projetos = new JPanel();
        panel_projetos.setLayout(new GridLayout(2, 1));
        
        // 1 - escolher:
        JPanel panel_selecionar_projetos = new JPanel();
        panel_selecionar_projetos.setLayout(new BorderLayout());
        
        JPanel panel_escolher_lista_projetos = new JPanel();
        JLabel label_nome_lista_projetos = new JLabel("Lista de projetos:");
        String[] types_lista_projetos = {"Todos", "Concluidos", "Nao concluidos a tempo"};
        combobox_lista_projetos= new JComboBox(types_lista_projetos);
        combobox_lista_projetos.addActionListener(new ComboBoxAction());
        panel_escolher_lista_projetos.add(label_nome_lista_projetos);
        panel_escolher_lista_projetos.add(combobox_lista_projetos);
        panel_selecionar_projetos.add(panel_escolher_lista_projetos, BorderLayout.NORTH);
        
        lista_projetos_values = new DefaultListModel(); 
        for (int i = 0; i < app.projetos.size(); ++i){
            lista_projetos_values.addElement(app.projetos.get(i));
        }
        lista_projetos = new JList(lista_projetos_values);
        JScrollPane listscroller_projetos = new JScrollPane(lista_projetos);
        panel_selecionar_projetos.add(listscroller_projetos, BorderLayout.CENTER);
        
        JPanel panel_button_selecionar_projetos = new JPanel();
        button_selecionar_projetos = new JButton("selecionar projeto");
        panel_button_selecionar_projetos.add(button_selecionar_projetos);
        panel_selecionar_projetos.add(panel_button_selecionar_projetos, BorderLayout.SOUTH);
        button_selecionar_projetos.addActionListener(new Action());
        
        panel_projetos.add(panel_selecionar_projetos);
        
        //2 - criar:
        JPanel panel_criar_projetos = new JPanel();
        panel_criar_projetos.setLayout(new GridLayout(7, 1));
        
        JPanel panel_separador_projetos = new JPanel();
        panel_criar_projetos.add(panel_separador_projetos);
        
        JPanel panel_nome_projeto = new JPanel();
        JLabel label_nome_projeto = new JLabel("Nome:");
        text_nome_projeto= new JTextField(10);
        panel_nome_projeto.add(label_nome_projeto);
        panel_nome_projeto.add(text_nome_projeto);
        panel_criar_projetos.add(panel_nome_projeto);
        
        JPanel panel_acronimo_projeto = new JPanel();
        JLabel label_acronimo_projeto = new JLabel("Acronimo:");
        text_acronimo_projeto= new JTextField(10);
        panel_acronimo_projeto.add(label_acronimo_projeto);
        panel_acronimo_projeto.add(text_acronimo_projeto);
        panel_criar_projetos.add(panel_acronimo_projeto);
        
        JPanel panel_duracao_projeto = new JPanel();
        JLabel label_duracao_projeto = new JLabel("Duracao estimada:");
        String[] types_numeros = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        combobox_projeto_duracao_dezenas= new JComboBox(types_numeros);
        combobox_projeto_duracao_unidades= new JComboBox(types_numeros);
        panel_duracao_projeto.add(label_duracao_projeto);
        panel_duracao_projeto.add(combobox_projeto_duracao_dezenas);
        panel_duracao_projeto.add(combobox_projeto_duracao_unidades);
        panel_criar_projetos.add(panel_duracao_projeto);
        
        JPanel panel_button_criar_projetos = new JPanel();
        button_criar_projetos = new JButton("criar projeto");
        panel_button_criar_projetos.add(button_criar_projetos);
        panel_criar_projetos.add(panel_button_criar_projetos);
        button_criar_projetos.addActionListener(new Action());
        
        JLabel inutil = new JLabel("\n\n");
        panel_criar_projetos.add(inutil);
        
        panel_projetos.add(panel_criar_projetos);
        //-----------------------------------------
        main_panel.add(panel_projetos, BorderLayout.WEST);
        //------------ações-----------
        JPanel panel_acoes = new JPanel();
        panel_acoes.setLayout(new GridLayout(1, 3));
        
        // 1 - menu:
        JPanel menu= new JPanel();
        menu.setLayout(new GridLayout(3, 1));
        
        JLabel label_separador_menu1 = new JLabel("\n\n");
        menu.add(label_separador_menu1);
        
        JPanel panel_menu = new JPanel();
        panel_menu.setLayout(new GridLayout(8, 1));
        
        JPanel panel_titulo_menu = new JPanel();
        JLabel label_titulo_menu = new JLabel("                                 MENU:");
        panel_titulo_menu.add(label_titulo_menu);
        panel_menu.add(label_titulo_menu);
        
        JPanel panel_eliminar = new JPanel();
        button_eliminar = new JButton("eliminar tarefa");
        panel_eliminar.add(button_eliminar);
        panel_menu.add(panel_eliminar);
        button_eliminar.addActionListener(new Action());
        
        JPanel panel_atribuir = new JPanel();
        button_atribuir = new JButton("atribuir tarefa");
        panel_atribuir.add(button_atribuir);
        panel_menu.add(panel_atribuir);
        button_atribuir.addActionListener(new Action());
        
        JPanel panel_atualizar = new JPanel();
        button_atualizar = new JButton("atualizar tarefa");
        text_atualizar= new JTextField(10);
        panel_atualizar.add(button_atualizar);
        panel_atualizar.add(text_atualizar);
        panel_menu.add(panel_atualizar);
        button_atualizar.addActionListener(new Action());
        
        JPanel panel_custo = new JPanel();
        button_custo = new JButton("calcular custo");
        panel_custo.add(button_custo);
        panel_menu.add(panel_custo);
        button_custo.addActionListener(new Action());
        
        JPanel panel_associa = new JPanel();
        button_associa = new JButton("associar pessoa");
        panel_associa.add(button_associa);
        panel_menu.add(panel_associa);
        button_associa.addActionListener(new Action());
        
        JPanel panel_terminar = new JPanel();
        button_terminar = new JButton("terminar projeto");
        panel_terminar.add(button_terminar);
        panel_menu.add(panel_terminar);
        button_terminar.addActionListener(new Action());
        
        JPanel panel_salvar = new JPanel();
        button_salvar = new JButton("salvar dados");
        panel_salvar.add(button_salvar);
        panel_menu.add(panel_salvar);
        button_salvar.addActionListener(new Action());
        
        menu.add(panel_menu);
        
        JLabel label_separador_menu2 = new JLabel("\n\n");
        menu.add(label_separador_menu2);
        
        panel_acoes.add(menu);
        
        // 2 - tarefas
        JPanel panel_tarefas = new JPanel();
        panel_tarefas.setLayout(new GridLayout(3, 1));
        JPanel panel_lista_tarefas= new JPanel();
        panel_lista_tarefas.setLayout(new BorderLayout());
        
        JLabel label_separador_tarefas1 = new JLabel("\n\n");
        panel_tarefas.add(label_separador_tarefas1);
        
        JPanel panel_escolher_lista_tarefas = new JPanel();
        JLabel label_nome_lista_tarefas = new JLabel("Lista de tarefas:");
        String[] types_lista_tarefas = {"Todas", "Nao iniciadas", "Nao concluidas a tempo" , "Concluidas"};
        combobox_lista_tarefas= new JComboBox(types_lista_tarefas);
        combobox_lista_tarefas.addActionListener(new ComboBoxAction());
        panel_escolher_lista_tarefas.add(label_nome_lista_tarefas);
        panel_escolher_lista_tarefas.add(combobox_lista_tarefas);
        panel_lista_tarefas.add(panel_escolher_lista_tarefas, BorderLayout.NORTH);
        
        lista_tarefas_values = new DefaultListModel(); 
        
        lista_tarefas = new JList(lista_tarefas_values);
        JScrollPane listscroller_tarefas = new JScrollPane(lista_tarefas);
        panel_lista_tarefas.add(listscroller_tarefas, BorderLayout.CENTER);
        
        JPanel panel_criar_tarefas = new JPanel();
        button_criar_tarefas = new JButton("criar tarefa");
        String[] types_tarefas = {"Design", "Desenvolvimento", "Documentacao" };
        combobox_tipos_tarefas= new JComboBox(types_tarefas);
        combobox_tarefa_duracao_dezenas= new JComboBox(types_numeros);
        combobox_tarefa_duracao_unidades= new JComboBox(types_numeros);
        panel_criar_tarefas.add(button_criar_tarefas);
        panel_criar_tarefas.add(combobox_tipos_tarefas);
        panel_criar_tarefas.add(combobox_tarefa_duracao_dezenas);
        panel_criar_tarefas.add(combobox_tarefa_duracao_unidades);
        button_criar_tarefas.addActionListener(new Action());
        panel_lista_tarefas.add(panel_criar_tarefas, BorderLayout.SOUTH);
        panel_tarefas.add(panel_lista_tarefas);
        
        // DATA
        JPanel PData= new JPanel();
        PData.setLayout(new GridLayout(3, 1));
        JLabel label_separador_tarefas2 = new JLabel("\n\n");
        PData.add(label_separador_tarefas2);
        JPanel panel_data = new JPanel();
        JLabel label_Data = new JLabel("Data: ");
        JLabel label_Data_separador = new JLabel("/ ");
        JLabel label_Data_ano = new JLabel("/ 20");
        combobox_dia = new JComboBox(types_dias1);
        combobox_dia.addActionListener(new ComboBoxAction());
        Integer[] types_meses = {1,2,3,4,5,6,7,8,9,10,11,12};
        combobox_mes = new JComboBox(types_meses);
        combobox_mes.addActionListener(new ComboBoxAction());
        combobox_ano_dezenas = new JComboBox(types_numeros);
        combobox_ano_dezenas.addActionListener(new ComboBoxAction());
        combobox_ano_unidades = new JComboBox(types_numeros);
        combobox_ano_unidades.addActionListener(new ComboBoxAction());
        panel_data.add(label_Data);
        panel_data.add(combobox_dia);
        panel_data.add(label_Data_separador);
        panel_data.add(combobox_mes);
        panel_data.add(label_Data_ano);
        panel_data.add(combobox_ano_dezenas);
        panel_data.add(combobox_ano_unidades);
        PData.add(panel_data);
        panel_tarefas.add(PData);
        panel_acoes.add(panel_tarefas);
        
        
        // 3 - print
        JPanel info = new JPanel();
        info.setLayout(new GridLayout(3, 1));
        
        JLabel label_separador_info1 = new JLabel("\n\n");
        info.add(label_separador_info1);
        JLabel label_separador_info2 = new JLabel("    ");
        JLabel label_separador_info3 = new JLabel("    ");
        JPanel panel_info= new JPanel();
        panel_info.setLayout(new BorderLayout());
        
        text_info_values= new DefaultListModel();
        text_info= new JList(text_info_values);
        JScrollPane listscroller_info = new JScrollPane(text_info);
        panel_info.add(listscroller_info, BorderLayout.CENTER);
        panel_info.add(label_separador_info2, BorderLayout.WEST);
        panel_info.add(label_separador_info3, BorderLayout.EAST);
        
        JPanel panel_button_informacao = new JPanel();
        button_informacao = new JButton("informacao");
        panel_button_informacao.add(button_informacao);
        panel_info.add(panel_button_informacao, BorderLayout.SOUTH);
        button_informacao.addActionListener(new Action());
        
        info.add(panel_info);
        
        JLabel label_separador_info4 = new JLabel("\n\n");
        info.add(label_separador_info4);
        
        panel_acoes.add(info);
        //-----------------------------
        main_panel.add(panel_acoes, BorderLayout.CENTER);
        //----------------pessoas-------------------
        JPanel panel_pessoas = new JPanel();
        panel_pessoas.setLayout(new GridLayout(2, 1));
        
        // 1 - pessoas da aplicacao:
        JPanel panel_lista_pessoas = new JPanel();
        panel_lista_pessoas.setLayout(new BorderLayout());
        
        JPanel panel_escolher_lista_pessoas = new JPanel();
        JLabel label_nome_lista_pessoas = new JLabel("          Lista de pessoas:");
        String[] types_lista_pessoas = {"Todas", "Docentes", "Doutorados" , "Mestrados", "Licenciados", "Posso associar"};
        combobox_lista_pessoas= new JComboBox(types_lista_pessoas);
        combobox_lista_pessoas.addActionListener(new ComboBoxAction());
        panel_escolher_lista_pessoas.add(label_nome_lista_pessoas);
        panel_escolher_lista_pessoas.add(combobox_lista_pessoas);
        panel_lista_pessoas.add(panel_escolher_lista_pessoas, BorderLayout.NORTH);
        
        lista_pessoas_values = new DefaultListModel(); 
        for (int i = 0; i < app.pessoas.size(); ++i){
            lista_pessoas_values.addElement(app.pessoas.get(i));
        }
        lista_pessoas = new JList(lista_pessoas_values);
        JScrollPane listscroller_pessoas = new JScrollPane(lista_pessoas);
        panel_lista_pessoas.add(listscroller_pessoas, BorderLayout.CENTER);
        
        JPanel panel_separador_pessoas = new JPanel();
        JLabel label_separador_pessoas = new JLabel("\n");
        panel_separador_pessoas.add(label_separador_pessoas);
        panel_lista_pessoas.add(panel_separador_pessoas, BorderLayout.SOUTH);
        
        panel_pessoas.add(panel_lista_pessoas);
        
        // 2 - pessoas associadas
        JPanel panel_lista_pessoas_associadas = new JPanel();
        panel_lista_pessoas_associadas.setLayout(new BorderLayout());
        
        JPanel panel_nome_lista_pessoas_associadas= new JPanel();
        JLabel label_nome_lista_pessoas_associadas = new JLabel("Lista de pessoas associadas:");
        panel_nome_lista_pessoas_associadas.add(label_nome_lista_pessoas_associadas);
        panel_lista_pessoas_associadas.add(panel_nome_lista_pessoas_associadas, BorderLayout.NORTH);
        
        lista_pessoas_associadas_values = new DefaultListModel(); 
        lista_pessoas_associadas = new JList(lista_pessoas_associadas_values);
        JScrollPane listscroller_pessoas_associadas = new JScrollPane(lista_pessoas_associadas);
        panel_lista_pessoas_associadas.add(listscroller_pessoas_associadas, BorderLayout.CENTER);
        
        JLabel label_separador_pessoas_associadas = new JLabel("\n\n");
        panel_lista_pessoas_associadas.add(label_separador_pessoas_associadas, BorderLayout.SOUTH);
        
        panel_pessoas.add(panel_lista_pessoas_associadas);
        
        //-------------------------------------------
        main_panel.add(panel_pessoas, BorderLayout.EAST);
        
        this.add(main_panel);
        this.addWindowListener(new Close());
        this.setVisible(true);
    }
    
    private class Close implements WindowListener{
        @Override
        public void windowClosing(WindowEvent e){
            if(salvo==0){
                String str = "Quer savar os dados antes de sair?";
                if(JOptionPane.showConfirmDialog(null, str, "Warning", JOptionPane.YES_NO_OPTION)==0){
                    app.salvar_dados();
                    exit(0);
                }
                exit(0);
            }
        }
        @Override
        public void windowOpened(WindowEvent e) {
        }

        @Override
        public void windowClosed(WindowEvent e) {
        }

        @Override
        public void windowIconified(WindowEvent e) {
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
        }

        @Override
        public void windowActivated(WindowEvent e) {
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
        }
    }
}
