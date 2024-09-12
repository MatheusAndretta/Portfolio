package main.java.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import main.java.dao.Persistente;



@Entity
@Table(name = "TB_VENDA")
public class Venda implements Persistente{

    public enum Status{
        INICIADA, CONCLUIDA, CANCELADA;

        public static Status getByName(String value){
            for (Status status : Status.values()) {
                if(status.name().equals(value)){
                    return  status;
                }
            }
            return null;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "Venda_seq")
    @SequenceGenerator(name = "Venda_seq",sequenceName = "seq_Venda",initialValue = 1,allocationSize = 1)
    private Long id;

    @Column(name = "CODIGO",nullable = false,unique = true)
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "id_cliente_fk",foreignKey = @ForeignKey(name = "fk_venda_cliente"),referencedColumnName = "id",nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "venda",cascade = CascadeType.ALL)
    private Set<ProdutoQuantidade> produtos;



    @Column(name = "VALOR_TOTAL",nullable = false)
    private BigDecimal valorTotal;

    @Column(name = "DATA_VENDA",nullable = false)
    private Instant dataVenda;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_VENDA",nullable = false)
    private Status status;

    

    public Venda() {
        produtos = new HashSet<>();
    }



    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public Cliente getCliente() {
        return cliente;
    }



    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }



    public Set<ProdutoQuantidade> getProdutos() {
        return produtos;
    }



    public void setProdutos(Set<ProdutoQuantidade> produtos) {
        this.produtos = produtos;
    }



    public String getCodigo() {
        return codigo;
    }



    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }



    public BigDecimal getValorTotal() {
        return valorTotal;
    }



    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }



    public Instant getDataVenda() {
        return dataVenda;
    }



    public void setDataVenda(Instant dataVenda) {
        this.dataVenda = dataVenda;
    }



    public Status getStatus() {
        return status;
    }



    public void setStatus(Status status) {
        this.status = status;
    }

    public void adicionarProduto(Produto produto, Integer quantidade){
        validarStatus();
        Optional<ProdutoQuantidade> op = produtos.stream().filter(filter -> filter.getProduto().getCodigo().equals(produto.getCodigo())).findAny();
        if (op.isPresent()) {
            ProdutoQuantidade produtoqtd = op.get();
            produtoqtd.adicionar(quantidade);
        }else{
            ProdutoQuantidade prod = new ProdutoQuantidade();
            prod.setVenda(this);
            prod.setProduto(produto);
            prod.adicionar(quantidade);
            produtos.add(prod);
        }
        recalcularValorTotalVenda();
    }

    public void removerProduto(Produto produto, Integer quantidade){
        validarStatus();
        Optional<ProdutoQuantidade> op = produtos.stream().filter(filter -> filter.getProduto().getCodigo().equals(produto.getCodigo())).findAny();
        if (op.isPresent()) {
            ProdutoQuantidade prodQtd = op.get();
            if (prodQtd.getQuantidade() > quantidade) {
                prodQtd.remover(quantidade);
                recalcularValorTotalVenda();
            } else{
                produtos.remove(op.get());
                recalcularValorTotalVenda();
            }
        }
    }

    public void removerTodosProdutos(){
        validarStatus();
        produtos.clear();
        valorTotal = BigDecimal.ZERO;
    }

    public Integer getQuantidadeTotalProdutos(){
        int result = produtos.stream()
        .reduce(0,(partialCountResult,prod) -> partialCountResult + prod.getQuantidade(),Integer::sum);
        return result;
    }



    private void recalcularValorTotalVenda() {
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (ProdutoQuantidade produtoQuantidade : this.produtos) {
            valorTotal = valorTotal.add(produtoQuantidade.getValorTOTAL());
        }
        this.valorTotal = valorTotal;
    }



    private void validarStatus() {
        if (this.status == Status.CONCLUIDA) {
            throw new UnsupportedOperationException("IMPOSSÍVEL ALTERAR VENDA FINALIZADA");
        }
    }

    
}
