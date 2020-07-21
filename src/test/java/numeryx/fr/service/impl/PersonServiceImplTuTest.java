package numeryx.fr.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import numeryx.fr.dao.PersonDAO;
import numeryx.fr.model.Person;
import numeryx.fr.service.PersonService;

@ExtendWith(SpringExtension.class)
public class PersonServiceImplTuTest {
	
	@MockBean
	private PersonDAO dao;
	
	@TestConfiguration
	static class PersonServiceConfig{
		@Bean
		public PersonService personService() {
			return new PersonServiceImpl();
		}
	}
	
	@Autowired
	private PersonService service;
	
	@Test
	public void getAllPersonsTest() {
		//Arrange
		List<Person> returnList = Arrays.asList(
				new Person("Patrice", "Lumumba", "Politician man", "000000001", "RD Congo"),
				new Person("Thomas", "Sankara", "Politician man", "000000002", "Burkinafaso")
		);
		when(service.getAllPersons()).thenReturn(returnList);
		//Act
		List<Person> persons = service.getAllPersons();
		//Assert
		Person p1 = returnList.get(0);
		Person p2 = returnList.get(1);
		assertEquals(2, persons.size());
		assertThat(persons.contains(p1));
		assertThat(persons.contains(p2));
	}
	
	@Test
	public void getPersonById() {	
		//Arrange
		Person person = new Person();
		person.setIdPerson(4L);
		person.setFirstName("Lucky");
		person.setLastName("Dube");
		person.setProfession("Musician");
		person.setTel("000000004");
		person.setAddress("South Africa");
		
		when(dao.findById(person.getIdPerson())).thenReturn(Optional.of(person));
		
		//Act
		Person p = service.getPersonById(person.getIdPerson());
		//Assert
		verify(dao, times(1)).findById(person.getIdPerson());
		assertEquals(person.getIdPerson(), p.getIdPerson());
		assertEquals(person.getFirstName(), p.getFirstName());
		assertEquals(person.getLastName(), p.getLastName());
		assertEquals(person.getProfession(), p.getProfession());
		assertEquals(person.getTel(), p.getTel());
		assertEquals(person.getAddress(), p.getAddress());
	}
	
	@Test
	public void getPersonByTelTest() {
		//Arrange
		Person person = new Person();
		person.setIdPerson(2L);
		person.setFirstName("Lokua");
		person.setLastName("Kanza");
		person.setProfession("Musician");
		person.setTel("000000007");
		person.setAddress("DR Congo");
		
		when(dao.findByTel(person.getTel())).thenReturn(Optional.of(person));
		
		//Act
		Person p = service.getByTel("000000007");
		//Assert
		assertNotNull(p);
		assertEquals(person.getIdPerson(), p.getIdPerson());
		assertEquals(person.getFirstName(), p.getFirstName());
		assertEquals(person.getLastName(), p.getLastName());
		assertEquals(person.getProfession(), p.getProfession());
		assertEquals(person.getTel(), p.getTel());
		assertEquals(person.getAddress(), p.getAddress());
	}
	
	@Test
	public void createPersonTest() {
		//Arrange
		Person person = new Person();
		person.setIdPerson(4L);
		person.setFirstName("Lucky");
		person.setLastName("Dube");
		person.setProfession("Musician");
		person.setTel("000000004");
		person.setAddress("South Africa");
		//Act
		service.createPerson(person);
		//Assert
		verify(dao, times(1)).save(person);
	}
	
	@Test
	public void deletePersonTest() {
		//Arrange
		Person person = new Person();
		person.setIdPerson(5L);
		person.setFirstName("Lucky");
		person.setLastName("Dube");
		person.setProfession("Musician");
		person.setTel("000000004");
		person.setAddress("South Africa");
		service.createPerson(person);
		
		//Act
		service.deletePerson(5L);
		//Assert
		verify(dao, times(1)).deleteById(person.getIdPerson());
	}
}
