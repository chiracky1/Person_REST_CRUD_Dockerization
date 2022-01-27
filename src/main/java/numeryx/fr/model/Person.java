package numeryx.fr.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Persons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idPerson;
	
	@Column
	private String firstName;
	
	@Column
	private String lastName;
	
	@Column
	private String profession;
	
	@Column
	private String tel;
	
	@Column
	private String address;

	/**
	 * @param firstName
	 * @param lastName
	 * @param profession
	 * @param tel
	 * @param address
	 */
	public Person(String firstName, String lastName, String profession, String tel, String address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.profession = profession;
		this.tel = tel;
		this.address = address;
	}
	
}
