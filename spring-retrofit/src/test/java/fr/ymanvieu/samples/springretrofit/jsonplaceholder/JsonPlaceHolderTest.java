package fr.ymanvieu.samples.springretrofit.jsonplaceholder;

import static okhttp3.mock.ClasspathResources.resource;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import fr.ymanvieu.samples.springretrofit.jsonplaceholder.JsonPlaceHolderTest.Config;
import okhttp3.OkHttpClient;
import okhttp3.mock.MockInterceptor;

@SpringBootTest
@Import(Config.class)
class JsonPlaceHolderTest {

    @Autowired
    JsonPlaceHolderService service;

    @Autowired
    MockInterceptor interceptor;

    @TestConfiguration
    static class Config {

        @Bean
        MockInterceptor interceptor() {
            return new MockInterceptor();
        }

        @Bean
        OkHttpClient httpClient() {
            return new OkHttpClient.Builder().addInterceptor(interceptor()).build();
        }
    }

    @Test
    void getUsers() throws Exception {
        interceptor.addRule().respond(resource("get_users.json"));

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
        interceptor.addRule().respond(resource("get_user.json"));

        var user = service.getUser(1).execute().body();

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getName()).isEqualTo("Leanne Graham");
        assertThat(user.getUsername()).isEqualTo("Bret");
        assertThat(user.getEmail()).isEqualTo("Sincere@april.biz");
    }

    @Test
    void createPost() throws Exception {
        interceptor.addRule().respond(resource("post_post.json"));

        var post = new Post().setBody("somebody").setTitle("that i used to know").setUserId(1L);
        var createdPost = service.createPost(post).execute().body();

        assertThat(createdPost).isNotNull();
        assertThat(createdPost.getId()).isEqualTo(101);
        assertThat(createdPost.getBody()).isEqualTo(post.getBody());
        assertThat(createdPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(createdPost.getUserId()).isEqualTo(post.getUserId());
    }
}
