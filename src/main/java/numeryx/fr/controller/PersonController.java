package numeryx.fr.controller;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;

import numeryx.fr.model.ApiResponse;
import numeryx.fr.model.Person;
import numeryx.fr.service.PersonService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/persons")
public class PersonController {
	
	@Autowired
	private PersonService service;
	
	@GetMapping
	public ApiResponse<List<Person>> findAllPerson(){
		return ApiResponse.<List<Person>>builder().status(HttpStatus.OK.value())
				.message("Person list fetched successfully")
				.result(service.getAllPersons())
				.build();
	}
	
	@GetMapping("/{id}")
	public ApiResponse<Person> getPersonById(@PathVariable Long id){
		return ApiResponse.<Person>builder().status(HttpStatus.OK.value())
				.message("Person fetched successfully")
				.result(service.getPersonById(id))
				.build();
	}
	
	@GetMapping("/tel/{tel}")
	public ApiResponse<Person> getPersonByTel(@PathVariable String tel){
		return ApiResponse.<Person>builder().status(HttpStatus.OK.value())
				.message("Person fetched successfully")
				.result(service.getByTel(tel))
				.build();
	}
	
	@PostMapping
	public ApiResponse<Person> createPerson(@RequestBody Person p){
		Preconditions.checkNotNull(p);
		return ApiResponse.<Person>builder().status(HttpStatus.OK.value())
				.message("Person saved successfully")
				.result(service.createPerson(p))
				.build();
	}
	
	@PutMapping("/{id}")
	public ApiResponse<Person> updatePerson(@PathVariable("id") Long id,@RequestBody Person p) throws NullPointerException {
		Preconditions.checkNotNull(p);
		Preconditions.checkNotNull(id);
		return ApiResponse.<Person>builder().status(HttpStatus.OK.value()).message("Person updated successfully")
				.result(service.updatePerson(id, p)).build();
	}
	
	@DeleteMapping("/{id}")
	public ApiResponse<Void> deletePerson(@PathVariable Long id){
		Person p = service.getPersonById(id);
		Predicate<Person> isNotNull = person -> person != null;
		if(!isNotNull.test(p))
			return new ApiResponse<Void>(HttpStatus.NOT_FOUND.value(), "Person with id: "+id+ " not found", null);
		service.deletePerson(id);
		return ApiResponse.<Void>builder().status(HttpStatus.OK.value()).message("Person deleted successfully")
				.result(null).build();
	}

}
