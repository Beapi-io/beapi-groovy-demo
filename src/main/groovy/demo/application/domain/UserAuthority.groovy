package demo.application.domain

import lombok.Getter
import lombok.Setter
import javax.persistence.IdClass;
import javax.persistence.*

@Entity
@Table(name = "user_authority")
@Getter
@Setter
class UserAuthority implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	public UserAuthority(User user, Authority auth) {
		this.user = user
		this.authority = auth
	}
	 */

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id


	//@ManyToOne(optional = false,targetEntity=User.class, fetch=FetchType.EAGER)
	@ManyToOne
	@JoinColumn(nullable = false, name = "user_id")
	private User user


	//@ManyToOne(optional = false,targetEntity=Authority.class, fetch=FetchType.EAGER)
	@ManyToOne
	@JoinColumn(nullable = false, name = "authority_id")
	private Authority authority

	public Long getUser(){
		return this.user;
	}

	public void setUser(User user){
		this.user = user;
	}

	public Long getAuthority(){
		return this.authority;
	}

	public void setAuthority(Authority auth){
		this.authority = auth;
	}
}
