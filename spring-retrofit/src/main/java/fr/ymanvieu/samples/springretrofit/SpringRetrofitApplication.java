package fr.ymanvieu.samples.springretrofit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.ymanvieu.samples.springretrofit.jsonplaceholder.JsonPlaceHolderService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@SpringBootApplication
@Slf4j
public class SpringRetrofitApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRetrofitApplication.class, args);
    }

    @Bean
    Retrofit retrofit(@Autowired(required = false) OkHttpClient httpClient) {
        var retrofit = new Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(JacksonConverterFactory.create());

        if (httpClient != null) {
            retrofit.client(httpClient);
        }

        return retrofit.build();
    }

    @Bean
    JsonPlaceHolderService jsonPlaceHolderService(Retrofit retrofit) {
        return retrofit.create(JsonPlaceHolderService.class);
    }

}
