package az.edu.arti.projct.zero.hackersuitcase.dao.repo;

import az.edu.arti.projct.zero.hackersuitcase.dao.entity.CommentVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {
    CommentVote findByUser_IdAndComment_Id(Long userId, Long commentId);
}
