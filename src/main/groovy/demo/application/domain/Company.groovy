package demo.application.domain

import lombok.Getter
import lombok.Setter

import javax.persistence.*

@Entity
@Table(name = "company")
@Getter
@Setter
class Company implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id

	@Version
	@Column(nullable = false, name = "version")
	private Long version;


	@Column(nullable = false, name = "name")
	String name


	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public Long getVersion(){
		return version;
	}

	public void setVersion(String version){
		this.version = version;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

}
