package az.edu.arti.projct.zero.hackersuitcase.dao.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Table(name = "posts")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Post {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Timestamp created;

    @Column(nullable = false)
    String title;

    @Column(columnDefinition = "longtext")
    String content;

    @ManyToOne
    @JoinColumn(name = "author_id")
    User author;

    @OneToMany(mappedBy = "post")
    List<Comment> comments;
}
