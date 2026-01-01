package br.com.erudio.services;

import br.com.erudio.data.dto.v1.PersonDTO;
import br.com.erudio.data.dto.v2.PersonDTOV2;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.mapper.custom.PersonMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static br.com.erudio.mapper.ObjectMapper.parseListObjects;
import static br.com.erudio.mapper.ObjectMapper.parseObjects;


@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper converter;

    //Buscar Todos - Mock com for e lista para criar as pessoas buscadas no banco - GET
    public List<PersonDTO> findAll() {
        logger.info("Finding all Person!");

        //Isolamento de entidades, precisamos converter uma DTO
//        return repository.findAll();
        return parseListObjects(repository.findAll(), PersonDTO.class);
    }

    //Busca por Id - Criada uma pessoa mockada. - GET By ID
    public PersonDTO findById(Long id) {
        logger.info("Finding one Person!");

        //Busca a pessoa encontrada utilizando a JPA - Caso não encontre, retorna uma mensagem de erro
        //Isolamento de entidades, precisamos converter uma DTO
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        return parseObjects(entity, PersonDTO.class);
    }

    //Criação de uma pessoa - POST V1
    public PersonDTO create(PersonDTO person) {
        logger.info("Creating one Person!");
        var entity = parseObjects(person, Person.class);

        //Criando uma pessoa no repositorio utilizando JPA
        //Isolamento de entidades, precisamos converter uma DTO
        return parseObjects(repository.save(entity), PersonDTO.class);
    }

    //Criação de uma pessoa - POST V2
    public PersonDTOV2 createV2(PersonDTOV2 person) {
        logger.info("Creating one Person!");
        var entity = converter.converDTOToEntity(person);

        //Criando uma pessoa no repositorio utilizando JPA
        //Isolamento de entidades, precisamos converter uma DTO
        //Foi criado um novo mapper, pois o dozer complicaria o processo
        return converter.convertEntityToDTO(repository.save(entity));
    }

    //Atualização de cadastro de uma pessoa - PUT
    public PersonDTO update(PersonDTO person) {
        logger.info("Updating one Person!");

        //Busca a pessoa no bando de dados, baseado no id dela. Caso encontre, atualiza, senão, joga o erro.
        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        //Atualizando a pessoa no repositorio.
        return parseObjects(repository.save(entity), PersonDTO.class);
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
