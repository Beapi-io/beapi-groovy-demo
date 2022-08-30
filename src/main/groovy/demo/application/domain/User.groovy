package demo.application.domain

import org.hibernate.annotations.IndexColumn
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "user")
@Getter
@Setter
//@Cacheable
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id

	@Version
	@Column(nullable = false, name = "version")
	private Long version;

	@Column(nullable = false, name = "username", unique = true)
	String username

	@Column(nullable = false, name = "password")
	String password

	@Column(nullable = true, name = "uid")
	String uid

	@Column(nullable = true, name = "first_name")
	String firstName = 'null'

	@Column(nullable = true, name = "last_name")
	String lastName = 'null'

	//@NotEmpty(message = "Email is required")
	@Column(nullable = false, name = "email", unique = true)
	String email

	@Column(nullable = true, name = "email_verified")
	Boolean emailVerified=false

	@Column(nullable = true, name = "oauth_id")
	String oauthId

	@Column(nullable = true, name = "oauth_provider")
	String oauthProvider

	@Column(nullable = true, name = "avatar_url")
	String avatarUrl

	@Column(nullable = true, name = "enabled")
	Boolean enabled=true

	@Column(nullable = true, name = "account_expired")
	Boolean accountExpired=false

	@Column(nullable = true, name = "account_locked")
	Boolean accountLocked=false

	@Column(nullable = true, name = "password_expired")
	Boolean passwordExpired=false




	//@OneToMany(targetEntity=UserAuthority.class, fetch=FetchType.EAGER, orphanRemoval = true)
	//@JoinColumn(name = "user_id")
	//@OrderColumn(name="authority_id")

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "user_authority",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName="id"),
			inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName="id")
	)
	private List<Authority> roles
	//private UserAuthority[] roles

	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public Long getVersion(){
		return version;
	}

	public void setVersion(Long version){
		this.version = version;
	}
	public String getUserame(){
		return username;
	}

	public void setUsername(String name){
		this.username = name;
	}

	public String getEmail(){
		return email;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public Boolean getEnabled(){
		return this.enabled
	}

	public void setEnabled(Boolean var){
		this.setEnabled = var
	}

	public Boolean getAccountExpired(){
		return this.accountExpired
	}

	public void setAccountExpired(Boolean var){
		this.setAccountExpired=var
	}

	public Boolean getAccountLocked(){
		return this.accountLocked
	}

	public void setAccountLocked(Boolean var){
		this.setAccountLocked = var
	}

	public Boolean getPasswordExpired(){
		return this.passwordExpired
	}

	public void setPasswordExpired(Boolean var){
		this.setPasswordExpired = var
	}

	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public Authority[] getAuthorities(){
		return roles;
	}

}
