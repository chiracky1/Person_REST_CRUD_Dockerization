package numeryx.fr.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PersonDto {
	
	private Long idPerson;
	private String firstName;
	private String lastName;
	private String profession;
	private String tel;
	private String address;

}
