package fr.ymanvieu.samples.springretrofit.jsonplaceholder;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Post {

    private Long id;
    private String title;
    private String body;
    private Long userId;
}
