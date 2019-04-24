/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pessoas;

import java.io.Serializable;

/**
 *
 * @author Marsal
 */
public class Pessoa implements Serializable{
    
    
    private int id;
    private String nome;
    private String cpf;
    private String email;
      
    public Pessoa() {
    }

    public Pessoa(int id, String nome, String cpf, String email) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

  
    public Pessoa(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }     

    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
           this.nome = nome;
    }
    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
   
    
    
}
