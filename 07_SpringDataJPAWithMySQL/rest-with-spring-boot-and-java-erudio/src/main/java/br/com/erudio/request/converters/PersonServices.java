package br.com.erudio.request.converters;

import br.com.erudio.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();

    private Logger logger = Logger.getLogger(PersonServices.class.getName());


    //Buscar Todos - Mock com for e lista para criar as pessoas buscadas no banco - GET
    public List<Person> findAll() {
        logger.info("Finding all Person!");
        List<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < 8; i++) {
            Person person = mockPerson(i);
            persons.add(person);
        }

        return persons;
    }

    //Busca por Id - Criada uma pessoa mockada. - GET By ID
    public Person findById(String id) {
        logger.info("Finding one Person!");

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Leandro");
        person.setLastName("Costa");
        person.setAdress("Uberlândia - Minas Gerais - Brasil");
        person.setGender("Male");
        return person;
    }

    //Criação de uma pessoa - POST
    public Person create(Person person) {
        logger.info("Creating one Person!");

        return person;
    }

    //Atualização de cadastro de uma pessoa - PUT
    public Person update(Person person) {
        logger.info("Updating one Person!");

        return person;
    }

    //Delete por Id - Criada uma pessoa mockada. - DELETE By ID
    public void delete(String id) {
        logger.info("Deleting one Person!");
    }



    //Mock
    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Firstname " + i);
        person.setLastName("Lasttname" + i);
        person.setAdress("Some Addres in Brasil");
        person.setGender("Male");
        return person;
    }


}
