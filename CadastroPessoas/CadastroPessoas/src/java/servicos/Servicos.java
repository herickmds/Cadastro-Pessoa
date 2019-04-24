/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicos;

import com.sun.research.ws.wadl.Response;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import pessoas.Pessoa;
import pessoas.SqlPessoa;

/**
 * REST Web Service
 *
 * @author Marsal
 */
@Path("servicos")
public class Servicos {

    private static final SqlPessoa banco = new SqlPessoa();

    public static ArrayList<Pessoa> pessoas;

    static int contador = 1;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Servicos
     */
    public Servicos() {
    }

    

    @GET
    @Path("listar")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pessoa> getPessoas() {
        pessoas = SqlPessoa.getLista();
        return pessoas;
    }

    @POST
    @Path("adicionar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void adicionar(Pessoa p) {
        ArrayList<Pessoa> pessoasAdd = new ArrayList<>();
        if (p != null) {
            pessoasAdd.add(p);
            banco.inserir(p);
        }
    }

    @PUT
    @Path("atualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    public void atualizar(Pessoa pessoa) {
        banco.alterar(pessoa);

    }

    @DELETE
    @Path("deletar")
    @Consumes(MediaType.TEXT_PLAIN)
    public static void excluir(int id) {
        for (Pessoa p : SqlPessoa.getLista()) {
            if (p.getId() == id) {
                banco.excluir(p);
                break;
            }
        }
    }

    @GET
    @Path("{id}")
    public Pessoa getPessoa(@PathParam("id") int id) {
        try {
            Pessoa pessoaBuscada = banco.buscar(id);
            return pessoaBuscada;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao listar registros de pessoas.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}
