package fr.ymanvieu.samples.springretrofit.jsonplaceholder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JsonPlaceHolderIntTest {

    @Autowired
    JsonPlaceHolderService service;

    @Test
    void getUsers() throws Exception {
        var users = service.getUsers().execute().body();

        assertThat(users)
            .hasSize(10)
            .element(0)
            .satisfies(user -> {
                assertThat(user.getId()).isEqualTo(1);
                assertThat(user.getName()).isEqualTo("Leanne Graham");
                assertThat(user.getUsername()).isEqualTo("Bret");
                assertThat(user.getEmail()).isEqualTo("Sincere@april.biz");
            });
    }

    @Test
    void getUser() throws Exception {
        var user = service.getUser(1).execute().body();

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getName()).isEqualTo("Leanne Graham");
        assertThat(user.getUsername()).isEqualTo("Bret");
        assertThat(user.getEmail()).isEqualTo("Sincere@april.biz");
    }

    @Test
    void createPost() throws Exception {
        var post = new Post().setBody("somebody").setTitle("that i used to know").setUserId(1L);
        var createdPost = service.createPost(post).execute().body();

        assertThat(createdPost).isNotNull();
        assertThat(createdPost.getId()).isEqualTo(101);
        assertThat(createdPost.getBody()).isEqualTo(post.getBody());
        assertThat(createdPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(createdPost.getUserId()).isEqualTo(post.getUserId());
    }
}
