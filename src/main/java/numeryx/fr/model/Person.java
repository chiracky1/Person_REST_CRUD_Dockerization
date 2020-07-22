package numeryx.fr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Persons")
public class Person implements Serializable {

	/**
	 * 
	 */
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

	public Person() {
		super();
	}

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

	/**
	 * @return the id
	 */
	public Long getIdPerson() {
		return idPerson;
	}

	/**
	 * @param id the id to set
	 */
	public void setIdPerson(long idPerson) {
		this.idPerson = idPerson;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the profession
	 */
	public String getProfession() {
		return profession;
	}

	/**
	 * @param profession the profession to set
	 */
	public void setProfession(String profession) {
		this.profession = profession;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Person [idPerson=" + idPerson + ", firstName=" + firstName + ", lastName=" + lastName + ", profession=" + profession
				+ ", tel=" + tel + ", address=" + address + "]";
	}
	
}
