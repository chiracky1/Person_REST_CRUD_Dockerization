package numeryx.fr.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import numeryx.fr.PersonRestCrudCiApplication;
import numeryx.fr.config.MysqlConfig;
import numeryx.fr.model.Person;
import numeryx.fr.service.PersonService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
		webEnvironment = WebEnvironment.MOCK, 
		classes = {PersonRestCrudCiApplication.class, MysqlConfig.class}) //MysqlConfig Config another DataSource for tests purposes
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PersonControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private PersonService service;
	
	ObjectMapper mapper = new ObjectMapper();
	String resultContent = null;
	
	@Test
	public void createPersonTest() throws Exception {
		//Arrange
		Person p = new Person("Marco", "Polo", "trader", "000000009", "USA");
		String person = mapper.writeValueAsString(p);
		this.mvc.perform(post("/persons")
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
