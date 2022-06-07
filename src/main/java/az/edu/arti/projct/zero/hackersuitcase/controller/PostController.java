package az.edu.arti.projct.zero.hackersuitcase.controller;

import az.edu.arti.projct.zero.hackersuitcase.dao.entity.Comment;
import az.edu.arti.projct.zero.hackersuitcase.dao.entity.Post;
import az.edu.arti.projct.zero.hackersuitcase.dao.repo.CommentRepository;
import az.edu.arti.projct.zero.hackersuitcase.dao.repo.PostRepository;
import az.edu.arti.projct.zero.hackersuitcase.dao.repo.UserRepository;
import az.edu.arti.projct.zero.hackersuitcase.dto.NewCommentForm;
import az.edu.arti.projct.zero.hackersuitcase.dto.NewPostForm;
import com.sun.security.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/posts")
public class PostController {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String getAllPosts(Model model) {
        List<Post> posts =  postRepository.findAll();
        model.addAttribute("posts", posts);
        return "posts";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable long id,
                         Authentication authentication,
                         RedirectAttributes model) {
        Post post = postRepository.getById(id);
        if (authentication == null || authentication.getName() == null || !authentication.getName().equals(post.getAuthor().getEmail())) {
            return "redirect:/posts";
        }
        postRepository.delete(post);
        return "redirect:/posts";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String getPostById(@PathVariable long id,
                              Authentication authentication,
                              Model model) {
        Post post = postRepository.findById(id).get();
        model.addAttribute("authentication", authentication);
        model.addAttribute("post", post);
        model.addAttribute("comments", commentRepository.findByPostId(post.getId()));
        model.addAttribute("newComment", new NewCommentForm());
        return "post";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public String addComment(@Valid @ModelAttribute("newComment") NewCommentForm newComment,
                          BindingResult result,
                          Authentication authentication,
                          @PathVariable long id,
                          Model model) {

        if (result.hasErrors()) {
            model.addAttribute("post", postRepository.findById(id));
            model.addAttribute("comments", commentRepository.findByPostId(id));
            return "index";
        }

        Comment comment = new Comment();
        comment.setContent(newComment.getContent());
        comment.setAuthor(userRepository.findByEmail(authentication.getName()));
        comment.setCreated(new Timestamp(System.currentTimeMillis()));
        comment.setPost(postRepository.getById(id));
        commentRepository.save(comment);

        model.asMap().clear();
        return "redirect:/posts/" + id;
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String getNewPostForm(Model model) {
        model.addAttribute("newPost", new NewPostForm());
        return "post_form";
    }

    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String processAndAddNewPost(@Valid @ModelAttribute("newPost") NewPostForm newPost,
                                        BindingResult result,
                                        Authentication authentication,
                                        Model model) {

        if (result.hasErrors()) {
            return "post_form";
        }

        Post post = new Post();
        post.setAuthor(userRepository.findByEmail(authentication.getName()));
        post.setTitle(newPost.getTitle());
        post.setContent(newPost.getContent());
        post.setCreated(new Timestamp(System.currentTimeMillis()));
        postRepository.save(post);

        return "redirect:/posts/" + post.getId();
    }
}
