package br.com.repository

import br.com.model.Cliente
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Repository
abstract class ClienteRepository(
    private val entityManager: EntityManager
): JpaRepository<Cliente, Long> {

    abstract fun findByNome(nome: String, pageable: Pageable): Page<Cliente>

    @Transactional
    fun listaCompleta(nome: String?): List<Cliente> {
        var queryString = "select c from Cliente c "
        if (nome != null) {
            queryString += "where nome = :nome"
        }
        var query = entityManager.createQuery(queryString)
        if (nome !=null) {
            query.setParameter("nome", nome)
        }
        var cliente = query.resultList
        return cliente as List<Cliente>
    }


}