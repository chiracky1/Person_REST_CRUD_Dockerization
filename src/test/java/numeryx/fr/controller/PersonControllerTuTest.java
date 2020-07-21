package numeryx.fr.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import numeryx.fr.model.Person;
import numeryx.fr.service.PersonService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTuTest {
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	private PersonService service;
	
	ObjectMapper mapper = new ObjectMapper();
	String resultContent = null;
	
	@Test
	public void findAllPersonTest() throws Exception {
		//Arrange
		List<Person> persons = Arrays.asList(
				new Person("Che", "Guevara", "Doctor", "000000009", "Argentine"),
				new Person("Fidel", "Castro", "Lawyer", "000000008", "Cuba"));
		
		resultContent = mapper.writeValueAsString(persons);
		System.out.println(resultContent);
		when(service.getAllPersons()).thenReturn(persons);
		
		//Act
		mvc.perform(get("/persons")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(resultContent)))
				.andDo(print());
	}
	
	@Test
	public void getPersonByIdTest() throws Exception {
		//Arrange
		Person p = new Person("Che", "Guevara", "Doctor", "000000009", "Argentine");
		p.setIdPerson(1L);
		resultContent = mapper.writeValueAsString(p);
		when(service.getPersonById(1L)).thenReturn(p);
		
		//Act
		this.mvc.perform(get("/persons/1")
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(content().string(containsString(resultContent)))
					.andDo(print());
	}
	
	@Test
	public void getPersonByTelTest() throws Exception {
		//Arrange
		Person p = new Person("Che", "Guevara", "Doctor", "000000009", "Argentine");
		p.setIdPerson(1L);
		resultContent = mapper.writeValueAsString(p);
		when(service.getByTel("000000009")).thenReturn(p);
		
		//Act
		this.mvc.perform(get("/persons/tel/000000009")
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(content().string(containsString(resultContent)))
					.andDo(print());
	}
	
	@Test
	public void createPersonTest() throws Exception {
		//Arrange
		Person p = new Person("Fidel", "Castro", "Lawyer", "000000008", "Cuba");
		p.setIdPerson(2L);
		String person = mapper.writeValueAsString(p);
		when(service.createPerson(Mockito.any(Person.class))).thenReturn(p);
		
		//Act
		this.mvc.perform(post("/persons")
					.content(person)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andDo(print());
	}
	
	@Test
	public void updatePersonTest() throws Exception {
		//Arrange
		Person p = new Person("Fidel", "Castro", "Lawyer", "000000008", "Cuba");
		String person = mapper.writeValueAsString(p);
		when(service.getPersonById(Mockito.any(Long.class))).thenReturn(p);
		
		//Act
		this.mvc.perform(put("/persons/2")
					.content(person)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andDo(print());
	}
	
	@Test
	public void deletePersonTest() throws Exception {
		//Act
		this.mvc.perform(delete("/persons/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print());
	}	
	
}
