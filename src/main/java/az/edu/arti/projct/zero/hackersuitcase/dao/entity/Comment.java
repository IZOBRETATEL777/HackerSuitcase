package az.edu.arti.projct.zero.hackersuitcase.dao.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Table(name = "comments")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Comment {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Timestamp created;

    @Column(columnDefinition = "longtext")
    String content;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    Long rating = 0L;

    @ManyToOne
    @JoinColumn(name = "author_id")
    User author;

    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;

    @OneToMany
    @JoinColumn(name = "comment")
    List<CommentVote> commentVotes;
}
