package az.edu.arti.projct.zero.hackersuitcase.dao.repo;

import az.edu.arti.projct.zero.hackersuitcase.dao.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long id);
}
