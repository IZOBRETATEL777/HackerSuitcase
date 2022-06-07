package az.edu.arti.projct.zero.hackersuitcase.dao.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Table(name = "users")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class User {
	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(nullable = false, unique = true)
	@Email
	@NotBlank
	@Size(max = 255)
	String email;

	@Column(nullable = false)
	@NotBlank
	@Size(min = 6, max = 255)
	String password;

	@Column(name = "first_name", columnDefinition = "varchar(30)")
	@Size(max = 30)
	String firstName;

	@Column(name = "last_name", columnDefinition = "varchar(50)")
	@Size(max = 50)
	String lastName;

	@Column(columnDefinition = "varchar(50)")
	@Size(max = 50)
	String company;

	@OneToMany(mappedBy = "author")
	List<Comment> comments;

	@OneToMany(mappedBy = "author")
	List<Post> posts;

	@OneToMany(mappedBy = "user")
	List<CommentVote> commentVotes;
}
