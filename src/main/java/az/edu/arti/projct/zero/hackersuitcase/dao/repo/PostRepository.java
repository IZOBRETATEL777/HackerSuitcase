package az.edu.arti.projct.zero.hackersuitcase.dao.repo;

import az.edu.arti.projct.zero.hackersuitcase.dao.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
