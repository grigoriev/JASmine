package eu.grigoriev.jasmine.client.services;

import eu.grigoriev.jasmine.model.Token;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SessionService {

    @POST("session")
    Call<Token> create(
            @Query("application") final String application,
            @Query("username") final String username,
            @Query("password") final String encodedPassword
    );

    @DELETE("session")
    Call<ResponseBody> close();
}
