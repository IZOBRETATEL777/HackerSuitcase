package az.edu.arti.projct.zero.hackersuitcase.controller;

import az.edu.arti.projct.zero.hackersuitcase.dao.entity.Comment;
import az.edu.arti.projct.zero.hackersuitcase.dao.entity.CommentVote;
import az.edu.arti.projct.zero.hackersuitcase.dao.entity.User;
import az.edu.arti.projct.zero.hackersuitcase.dao.repo.CommentRepository;
import az.edu.arti.projct.zero.hackersuitcase.dao.repo.CommentVoteRepository;
import az.edu.arti.projct.zero.hackersuitcase.dao.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/comments")
public class CommentController {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentVoteRepository commentVoteRepository;

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable long id,
                         Authentication authentication,
                         RedirectAttributes model) {
        Comment comment = commentRepository.getById(id);
        if (authentication == null || authentication.getName() == null || !authentication.getName().equals(comment.getAuthor().getEmail())) {
            return "redirect:/posts";
        }
        commentRepository.delete(comment);
        return "redirect:/posts/" + comment.getPost().getId();
    }

    @RequestMapping(value = "like/{id}", method = RequestMethod.GET)
    public String like(@PathVariable long id,
                       Authentication authentication) {
        Comment comment = commentRepository.getById(id);
        if (authentication == null || authentication.getName() == null || authentication.getName().equals(comment.getAuthor().getEmail())) {
            return "redirect:/posts/" + comment.getPost().getId();
        }
        User user = userRepository.findByEmail(authentication.getName());
        CommentVote commentVote = commentVoteRepository.findByUser_IdAndComment_Id(user.getId(), comment.getId());
        if (commentVote == null) {
            commentVote = CommentVote.builder().user(user).comment(comment).reaction("like").build();
        }
        else if (!commentVote.getReaction().equals("like")) {
            commentVote.setReaction("like");
        }
        else {
            return "redirect:/posts/" + comment.getPost().getId();
        }
        Long rating = comment.getRating();
        if (user.getCompany() != null && !user.getCompany().isBlank())
            rating += 3;
        else
            rating += 1;
        comment.setRating(rating);
        commentRepository.save(comment);
        commentVoteRepository.save(commentVote);
        return "redirect:/posts/" + comment.getPost().getId();
    }

    @RequestMapping(value = "dislike/{id}", method = RequestMethod.GET)
    public String dislike(@PathVariable long id,
                       Authentication authentication) {
        Comment comment = commentRepository.getById(id);
        if (authentication == null || authentication.getName() == null || authentication.getName().equals(comment.getAuthor().getEmail())) {
            return "redirect:/posts/" + comment.getPost().getId();
        }
        User user = userRepository.findByEmail(authentication.getName());
        CommentVote commentVote = commentVoteRepository.findByUser_IdAndComment_Id(user.getId(), comment.getId());
        if (commentVote == null) {
            commentVote = CommentVote.builder().user(user).comment(comment).reaction("dislike").build();
        }
        else if (!commentVote.getReaction().equals("dislike")) {
            commentVote.setReaction("dislike");
        }
        else {
            return "redirect:/posts/" + comment.getPost().getId();
        }
        Long rating = comment.getRating();
        if (user.getCompany() != null && !user.getCompany().isBlank())
            rating -= 3;
        else
            rating -= 1;
        comment.setRating(rating);
        commentRepository.save(comment);
        commentVoteRepository.save(commentVote);
        return "redirect:/posts/" + comment.getPost().getId();
    }
}
