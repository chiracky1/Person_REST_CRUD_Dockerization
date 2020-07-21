package numeryx.fr.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import numeryx.fr.model.Person;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PersonDaoTuTest {
	
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private PersonDAO dao;
	
	@Test
	public void emptyResultTest() {
		Iterable<Person> persons = dao.findAll();
		assertThat(persons).isEmpty();
	}
	
	@Test
	public void findAllPersonsTest() {
		Person p1  = new Person("Jacques", "Cyprus", "Mecanicien", "123456789", "Talence");
		entityManager.persist(p1);
		Person p2  = new Person("Mawuli", "Koffi", "Informaticien", "123456789", "Paris");
		entityManager.persist(p2);
		Person p3  = new Person("Estime", "Biayi", "Electricien", "123456789", "Lubumbashi");
		entityManager.persist(p3);
		
		entityManager.flush();
		
		Iterable<Person> persons = dao.findAll();
		assertThat(persons).hasSize(3).contains(p1, p2, p3);
	}
	
	@Test
	public void findPersonByIdTest() {
		Person p1  = new Person("Jacques", "Cyprus", "Mecanicien", "123456789", "Talence");
		entityManager.persist(p1);
		Person p2  = new Person("Mawuli", "Koffi", "Informaticien", "123456789", "Paris");
		entityManager.persist(p2);
		Person foundPerson = dao.findById(p2.getIdPerson()).get();
		assertThat(foundPerson).isEqualTo(p2);
	}
	
	@Test
	public void findPersonByTelTest() {
		Person p1  = new Person("Jacques", "Cyprus", "Mecanicien", "123456789", "Talence");
		entityManager.persist(p1);
		Person p2  = new Person("Mawuli", "Koffi", "Informaticien", "123456001", "Paris");
		entityManager.persist(p2);
		Person p3  = new Person("Estime", "Biayi", "Electricien", "123456002", "Lubumbashi");
		entityManager.persist(p3);
		
		Person person = dao.findByTel("123456789").get();
		assertEquals("Jacques", person.getFirstName());
		assertEquals("Cyprus", person.getLastName());
		assertEquals("Mecanicien", person.getProfession());
		assertEquals("Talence", person.getAddress());
	}
	
	@Test
	public void createPersonTest() {	
		Person p  = new Person("Norman", "Albert", "Millitaire", "000111222", "Paris");
		dao.save(p);
		assertThat(p).hasFieldOrPropertyWithValue("firstName", "Norman");
		assertThat(p).hasFieldOrPropertyWithValue("lastName", "Albert");
		assertThat(p).hasFieldOrPropertyWithValue("profession", "Millitaire");
		assertThat(p).hasFieldOrPropertyWithValue("tel", "000111222");
		assertThat(p).hasFieldOrPropertyWithValue("address", "Paris");
	}
	
	@Test
	public void deletePersonTest() {
		Person p1  = new Person("Martin", "Marc", "Mecanicien", "123456789", "Talence");
		entityManager.persist(p1);
		Person p2  = new Person("Kalenga", "Rolland", "Informaticien", "123456001", "Paris");
		entityManager.persist(p2);
		Person p3  = new Person("Mukendi", "Odia", "Avocat", "123456002", "Lubumbashi");
		entityManager.persist(p3);
		
		dao.delete(p1);
		
		Iterable<Person> persons = dao.findAll();
		assertThat(persons).hasSize(2).contains(p2, p3);
	}
}
