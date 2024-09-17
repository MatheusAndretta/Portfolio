package main.java.dao;

/**
 * A interface Persistente define um contrato para entidades que precisam de um identificador único. 
 * Esta interface serve como uma base para garantir que todas as classes que a implementam possam ser identificadas de forma consistente, 
 * facilitando a integração com mecanismos de persistência e gerenciamento de dados.
 */

public interface Persistente {

    /**
     * Retorna o identificador único da entidade. E
     * ste identificador é usado para distinguir a entidade de outras e para operações como armazenamento e recuperação.
     * 
     * @return O identificador único da entidade.
     */
    public Long getId();


    /**
     * Define o identificador único da entidade. 
     * Este método permite que o identificador seja atribuído ou alterado conforme necessário.
     * 
     * 
     * @param id
     */

    public void setId(Long id);

}
