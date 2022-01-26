package numeryx.fr.service;

import java.util.List;

import numeryx.fr.model.Person;

public interface PersonService {
	
	List<Person> getAllPersons();
	Person  getPersonById(Long id);
	Person getByTel(String tel);
	Person createPerson(Person p);
	void deletePerson(Long id);

}
