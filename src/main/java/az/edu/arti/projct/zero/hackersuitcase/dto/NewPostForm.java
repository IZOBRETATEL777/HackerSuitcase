package az.edu.arti.projct.zero.hackersuitcase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPostForm {
    @Size(min = 3, max = 50, message = "{Size.Topic.title.validation}")
    private String title;

    @Size(min = 5, message = "{Size.Topic.content.validation}")
    private String content;
}
