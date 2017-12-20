package eu.grigoriev.jasmine.client;

import eu.grigoriev.jasmine.client.services.AccountService;
import eu.grigoriev.jasmine.client.services.SessionService;
import eu.grigoriev.jasmine.model.Token;
import eu.grigoriev.jasmine.model.User;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
public class JasmineClient {

    public static final String BASE_URL = "http://172.19.0.3:8080/jasmine/";
    public static final String SERVICE = "service";
    public static final String USERNAME = "username";
    public static final String PASSOWRD = "password";

    private static Token token = null;
    private Retrofit retrofit = null;

    public JasmineClient(String baseUrl) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(
                chain -> {
                    Request original = chain.request();

                    if (token != null) {
                        Request request = original.newBuilder()
                                .header("Authorization", token.toString())
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);
                    } else {
                        return chain.proceed(original);
                    }
                }

        );
        OkHttpClient okHttpClient = okHttpClientBuilder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

    }

    public Token login(String service, String username, String encodedPassword) throws IOException {
        SessionService sessionService = retrofit.create(SessionService.class);

        Response<Token> tokenResponse = sessionService.create(service, username, encodedPassword).execute();
        return tokenResponse.body();
    }

    public User getUserInfo(String service, String username) throws IOException {
        AccountService accountService = retrofit.create(AccountService.class);

        Response<User> userResponse = accountService.info(service, username).execute();
        return userResponse.body();
    }

    public void logout() {
        SessionService sessionService = retrofit.create(SessionService.class);

        sessionService.close();
    }

    public static void main(String[] args) throws IOException {
        String encodedPassword = Base64.getEncoder().encodeToString(PASSOWRD.getBytes(StandardCharsets.UTF_8));

        JasmineClient jasmineClient = new JasmineClient(BASE_URL);
        token = jasmineClient.login(SERVICE, USERNAME, encodedPassword);
        User user = jasmineClient.getUserInfo(SERVICE, USERNAME);
        log.info("user = {}", user);
        jasmineClient.logout();
    }

}
