package numeryx.fr.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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

	@Captor
	ArgumentCaptor<Long> idCaptor;
	@Captor
	ArgumentCaptor<String> telCaptor;

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

		List<Person> returnList = Arrays.asList(
				new Person("Patrice", "Lumumba", "Politician man", "000000001", "RD Congo"),
				new Person("Thomas", "Sankara", "Politician man", "000000002", "Burkinafaso")
		);

		when(service.getAllPersons()).thenReturn(returnList);

		List<Person> persons = service.getAllPersons();

		Person p1 = returnList.get(0);
		Person p2 = returnList.get(1);
		assertEquals(2, persons.size());
		assertThat(persons.contains(p1));
		assertThat(persons.contains(p2));
	}
	
	@Test
	public void getPersonById() {

		Person person = Person.builder().idPerson(4L).firstName("Lucky").lastName("Dube").profession("raggaeman")
				.tel("000000004").address("South Africa").build();
		
		when(dao.findById(person.getIdPerson())).thenReturn(Optional.of(person));
		
		Person p = service.getPersonById(person.getIdPerson());

		verify(dao, times(1)).findById(person.getIdPerson());
		verify(dao).findById(idCaptor.capture());
		assertEquals(4L, idCaptor.getValue());
		assertEquals(person.getIdPerson(), p.getIdPerson());
		assertEquals(person.getFirstName(), p.getFirstName());
		assertEquals(person.getLastName(), p.getLastName());
		assertEquals(person.getProfession(), p.getProfession());
		assertEquals(person.getTel(), p.getTel());
		assertEquals(person.getAddress(), p.getAddress());
	}
	
	@Test
	public void getPersonByTelTest() {

		Person person = Person.builder().idPerson(2L).firstName("Lokua").lastName("Kanza").profession("Musician")
				.tel("000000007").address("DR Congo").build();
		
		when(dao.findByTel(person.getTel())).thenReturn(Optional.of(person));
		
		Person p = service.getByTel("000000007");

		assertNotNull(p);
		verify(dao, times(1)).findByTel("000000007");
		verify(dao).findByTel(telCaptor.capture());
		assertEquals("000000007", telCaptor.getValue());
		assertEquals(person.getIdPerson(), p.getIdPerson());
		assertEquals(person.getFirstName(), p.getFirstName());
		assertEquals(person.getLastName(), p.getLastName());
		assertEquals(person.getProfession(), p.getProfession());
		assertEquals(person.getTel(), p.getTel());
		assertEquals(person.getAddress(), p.getAddress());
	}
	
	@Test
	public void createPersonTest() {

		Person person = Person.builder().idPerson(4L).firstName("Lucky").lastName("Dube").profession("raggaeman")
				.tel("000000004").address("South Africa").build();

		when(dao.save(person)).thenReturn(person);

		Person p = service.createPerson(person);

		verify(dao, times(1)).save(person);
		assertEquals(p, person);
	}

	@Test
	public void updatePersonTest() throws NullPointerException {

		Person personSaved = Person.builder().idPerson(3L).firstName("Thomas").lastName("Sankara").profession("Politician").tel("123456789")
				.address("Burkinafaso").build();

		when(dao.findById(any(Long.class))).thenReturn(Optional.ofNullable(personSaved));

		Person personToUpdate = Person.builder().profession("National hero").build();

		Person.PersonBuilder personBuilder = personSaved.toBuilder();
		Person personUpdated = personBuilder.profession("National hero").build();
		when(dao.save(any(Person.class))).thenReturn(personUpdated);

		Person person = service.updatePerson(3L, personToUpdate);

		assertEquals(personUpdated, person);
	}

	@Test
	public void updatePersonShouldThrowExceptionTest(){
		NullPointerException npe = assertThrows(NullPointerException.class, () -> {
			service.updatePerson(2L,
					Person.builder().firstName("Chirac").lastName("Mundadi").build());
		});

		String exceptionExpectedMessage = "Person with id: 2 not found";
		String actualMessage = npe.getMessage();

		assertTrue(actualMessage.contains(exceptionExpectedMessage));
	}
	
	@Test
	public void deletePersonTest() {

		Person person = Person.builder().idPerson(5L).firstName("Lucky").lastName("Dube").profession("raggaeman")
				.tel("000000004").address("South Africa").build();
		
		service.deletePerson(5L);

		verify(dao, times(1)).deleteById(person.getIdPerson());
	}
}
