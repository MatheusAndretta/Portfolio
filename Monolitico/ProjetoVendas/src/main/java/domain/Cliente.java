package main.java.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import main.java.dao.Persistente;

@Entity
@Table(name = "TB_CLIENTE")
public class Cliente implements Persistente{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Cliente_seq" )
    @SequenceGenerator(name = "Cliente_seq",sequenceName = "seq_Cliente",initialValue = 1,allocationSize = 1)
    private Long id;

    @Column(name = "NOME",length = 50,nullable = false)
    private String nome;

    @Column(name = "CPF",nullable = false,unique = true)
    private Long cpf;

    @Column(name = "TELEFONE",nullable = false)
    private Long telefone;

    @Column(name = "ENDERECO",length = 100,nullable = false)
    private String end;

    @Column(name = "NUMERO",nullable = false)
    private Integer numero;

    @Column(name = "CIDADE",length = 50,nullable = false)
    private String cidade;

    @Column(name = "ESTADO",length = 50,nullable = false)
    private String estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
}
