package fr.ymanvieu.samples.springretrofit.jsonplaceholder;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderService {

    @GET("/users")
    Call<List<User>> getUsers();

    @GET("/users/{userId}")
    Call<User> getUser(@Path("userId") long userId);

    @Headers("Content-type: application/json; charset=UTF-8")
    @POST("/posts")
    Call<Post> createPost(@Body Post post);

}
