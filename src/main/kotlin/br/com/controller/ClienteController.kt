package br.com.controller

import br.com.model.Cliente
import br.com.service.ClienteService
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.QueryValue

@Controller("/clientes")
class ClienteController(
    private val clienteService: ClienteService
) {

    @Post
    fun salvarCliente(@Body cliente: Cliente): HttpResponse<Cliente> {
        val clienteBD = clienteService.salvarCliente(cliente)
        return HttpResponse.created(clienteBD)
    }

    @Get
    fun buscarTodos(@QueryValue nome: String?, pageable: Pageable): Page<Cliente> {
        return clienteService.buscarTodos(nome, pageable)
    }

    @Get("/{id}")
    fun findById(@PathVariable id: Long): Cliente {
        return clienteService.findById(id)
    }

    @Delete("/{id}")
    fun deletarCliente(@PathVariable id: Long): HttpResponse<Unit>{
        clienteService.deletarCliente(id)
        return HttpResponse.noContent()
    }

    @Put("/{id}")
    fun alterarCliente(@PathVariable id: Long, @Body cliente: Cliente) {
        clienteService.alterarCliente(id, cliente)
    }

    @Get("/pesquisar")
    fun listar(@QueryValue nome: String?): List<Cliente> {
        return clienteService.listar(nome)
    }
}