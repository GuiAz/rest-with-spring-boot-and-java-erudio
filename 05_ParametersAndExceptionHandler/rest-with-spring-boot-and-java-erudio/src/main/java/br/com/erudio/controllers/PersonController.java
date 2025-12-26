package br.com.erudio.controllers;

import br.com.erudio.model.Person;
import br.com.erudio.request.converters.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices service;
    //Com o @Autowired e o @Service na classe PersonServices, não é mais necessário a injeção da dependencia,
    // o Spring já faz isso com o @Autowired
    //private PersonServices service = new PersonServices();

    // http://localhost:8080/person
    //Requisicao do verbo GET para busca de todos as pessoas cadastradas
    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    public List<Person> findAll() {
        return service.findAll();
    }

    // http://localhost:8080/person/1
    //Requisicao do verbo GET para busca de uma única pessoa (por ID)
    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Person findById(@PathVariable("id") String id) {
        return service.findById(id);
    }

    // http://localhost:8080/person
    //Requisicao do verbo POST para criação de uma pessoa no bando de dados
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Person create (@RequestBody Person person) {
        return service.create(person);
    }

    // http://localhost:8080/person
    //Atualização do verbo PUT para Atualização de uma pessoa no bando de dados
    @RequestMapping(method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Person update (@RequestBody Person person) {
        return service.update(person);
    }

    // http://localhost:8080/person/1
    //Requisicao do verbo DELETE para remoção de uma única pessoa (por ID) do banco de dados
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void delete(String id) {
        service.delete(id);
    }

}
