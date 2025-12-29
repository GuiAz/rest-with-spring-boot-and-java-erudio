package br.com.erudio.services;

import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    //Buscar Todos - Mock com for e lista para criar as pessoas buscadas no banco - GET
    public List<Person> findAll() {
        logger.info("Finding all Person!");

        //Busca todos as pessoas encontradas utilizando a JPA
        return repository.findAll();
    }

    //Busca por Id - Criada uma pessoa mockada. - GET By ID
    public Person findById(Long id) {
        logger.info("Finding one Person!");

        //Busca a pessoa encontrada utilizando a JPA - Caso não encontre, retorna uma mensagem de erro
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
    }

    //Criação de uma pessoa - POST
    public Person create(Person person) {
        logger.info("Creating one Person!");

        //Criando uma pessoa no repositorio utilizando JPA
        return repository.save(person);
    }

    //Atualização de cadastro de uma pessoa - PUT
    public Person update(Person person) {
        logger.info("Updating one Person!");

        //Busca a pessoa no bando de dados, baseado no id dela. Caso encontre, atualiza, senão, joga o erro.
        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAdress(person.getAdress());
        entity.setGender(person.getGender());

        //Atualizando a pessoa no repositorio.
        return repository.save(person);
    }

    //Delete por Id - Criada uma pessoa mockada. - DELETE By ID
    public void delete(Long id) {
        logger.info("Deleting one Person!");

        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        //deleta a pessoa do bando de dado após a busca. Caso encontre, dela, senão, joga o erro.
        repository.delete(entity);
    }
}
