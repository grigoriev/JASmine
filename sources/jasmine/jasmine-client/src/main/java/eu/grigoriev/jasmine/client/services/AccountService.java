package eu.grigoriev.jasmine.client.services;

import eu.grigoriev.jasmine.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface AccountService {

    @POST("account")
    Call<User> register(
            @Query("application") final String application,
            @Query("username") final String username,
            @Query("password") final String password
    );

    @GET("account")
    Call<User> info(
            @Query("application") final String application,
            @Query("username") final String username
    );

    @PUT("account")
    Call<User> update(final User user);

    @DELETE("account")
    Call<ResponseBody> delete(
            @Query("application") final String application,
            @Query("username") final String username
    );
}
