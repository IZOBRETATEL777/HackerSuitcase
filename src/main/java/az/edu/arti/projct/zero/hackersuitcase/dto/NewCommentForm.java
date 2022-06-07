package az.edu.arti.projct.zero.hackersuitcase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCommentForm {
    @Size(min = 3, message = "{Size.Post.content.validation}")
    private String content;
}
