package main.java.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import main.java.dao.Persistente;

 /**
 * Representa um produto no sistema.
 * <p>
 * Esta classe é mapeada para a tabela {@code TB_PRODUTO} no banco de dados e inclui informações
 * sobre o produto, como código, nome, descrição e valor.
 * </p>
 */

@Entity
@Table(name = "TB_PRODUTO")
public class Produto implements Persistente{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "Produto_seq")
    @SequenceGenerator(name = "Produto_seq",sequenceName = "seq_Produto",initialValue = 1,allocationSize = 1)
    private Long id;

    @Column(name = "codigo", nullable = false, length = 10, unique = true)
    private String codigo;

    @Column(name = "NOME", nullable = false, length = 50)
    private String nome;

    @Column(name = "DESCRICAO", nullable = false, length = 50)
    private String descricao;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }



}