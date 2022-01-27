package numeryx.fr.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import numeryx.fr.dao.PersonDAO;
import numeryx.fr.model.Person;
import numeryx.fr.service.PersonService;
@Service
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	private PersonDAO dao;

	@Override
	public List<Person> getAllPersons() {
		List<Person> persons = new ArrayList<Person>();
		dao.findAll().iterator().forEachRemaining(persons::add);
		return persons;
	}

	@Override
	public Person getPersonById(Long id) {
		Optional<Person> person = dao.findById(id);
		return person.isPresent() ? person.get() : null;
	}

	@Override
	public Person getByTel(String tel) {
		Optional<Person> person = dao.findByTel(tel);
		return person.isPresent() ? person.get() : null ;
	}

	@Override
	public Person createPerson(Person p) {
		return dao.save(p);
	}

	@Override
	public Person updatePerson(Long id, Person p) throws NullPointerException {
		Person person = getPersonById(id);
		if(Objects.isNull(person)){
			throw new NullPointerException("Person with id: " + id + " not found");
		}
		p.setIdPerson(person.getIdPerson());
		return dao.save(p);
	}

	@Override
	public void deletePerson(Long id) {
		dao.deleteById(id);
	}

}
