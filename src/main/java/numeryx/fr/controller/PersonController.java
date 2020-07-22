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
		return new ApiResponse<List<Person>>(HttpStatus.OK.value(), "Person list fetched successfully ", service.getAllPersons());
	}
	
	@GetMapping("/{id}")
	public ApiResponse<Person> getPersonById(@PathVariable Long id){
		return new ApiResponse<Person>(HttpStatus.OK.value(), "Person fetched sucessfully ", service.getPersonById(id));
	}
	
	@GetMapping("/tel/{tel}")
	public ApiResponse<Person> getPersonByTel(@PathVariable String tel){
		return new ApiResponse<Person>(HttpStatus.OK.value(), "Person fetched sucessfully ", service.getByTel(tel));
	}
	
	@PostMapping
	public ApiResponse<Person> createPerson(@RequestBody Person p){
		Preconditions.checkNotNull(p);
		return new ApiResponse<Person>(HttpStatus.OK.value(), "Person saved successfully ", service.createPerson(p));
	}
	
	@PutMapping("/{id}")
	public ApiResponse<Person> updatePerson(@PathVariable("id") Long id,@RequestBody Person p){
		Preconditions.checkNotNull(p);
		Preconditions.checkNotNull(id);
		Person person = service.getPersonById(id);
		if(Objects.isNull(person)) {
			return new ApiResponse<Person>(HttpStatus.NOT_FOUND.value(), "Person with id: "+id+ " not found", null);
		}	
		p.setIdPerson(id);
		return new ApiResponse<Person>(
				HttpStatus.OK.value(), 
				"Person updated successfully", 
				service.createPerson(p));
	}
	
	@DeleteMapping("/{id}")
	public ApiResponse<Void> deletePerson(@PathVariable Long id){
		Person p = service.getPersonById(id);
		Predicate<Person> isNotNull = person -> person != null;
		if(!isNotNull.test(p))
			return new ApiResponse<Void>(HttpStatus.NOT_FOUND.value(), "Person with id: "+id+ " not found", null);
		service.deletePerson(id);
		return new ApiResponse<Void>(HttpStatus.OK.value(), "Person delete sucessfully", null);
	}

}
