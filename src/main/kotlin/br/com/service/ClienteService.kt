package br.com.service

import br.com.exception.RegistroNaoEncontradoException
import br.com.model.Cliente
import br.com.repository.ClienteRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import javax.transaction.Transactional

@Singleton
open class ClienteService(
    private val clienteRepository: ClienteRepository
) {

    fun salvarCliente(cliente: Cliente): Cliente {
       return clienteRepository.save(cliente)
    }

    fun buscarTodos(nome: String?, pageable: Pageable): Page<Cliente> {
        var cliente = if (nome == null) {
            clienteRepository.findAll(pageable)
        }else {
            clienteRepository.findByNome(nome,pageable)
        }
        return cliente
    }

    fun findById(id: Long): Cliente {
        return clienteRepository.findById(id).orElseThrow{
            RegistroNaoEncontradoException("Registro não encontrado!")
        }
    }

    fun deletarCliente(id: Long) {
        val clienteBD = findById(id)
        clienteRepository.delete(clienteBD)
    }

    @Transactional
    open fun alterarCliente(id: Long, cliente: Cliente) {
        val clienteBD = findById(id)
        clienteBD.nome = cliente.nome
        clienteBD.documento = cliente.documento
        clienteBD.endereco = cliente.endereco
        clienteRepository.save(clienteBD)
    }

    fun listar(nome: String?): List<Cliente> {
        return clienteRepository.listaCompleta(nome)
    }


}