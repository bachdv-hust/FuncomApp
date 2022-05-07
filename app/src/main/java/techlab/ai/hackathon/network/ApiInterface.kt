package techlab.ai.hackathon.network

import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import techlab.ai.hackathon.data.model.*

/**
 * @author BachDV
 */
interface ApiInterface {

    @GET("v1/users/list-friend")
    fun getDemo() : Observable<BaseResponse<DemoModel>>

    @GET("events")
    fun getDataNewFeed() : Observable<BaseListResponse<NewFeed>>

    @FormUrlEncoded
    @POST("auth/register")
    fun register(
        @Field("username") username : String,
        @Field("password") password : String,
        @Field("name") name : String
    ): Observable<BaseResponse<UserModel>>

    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("username") username : String,
        @Field("password") password : String
    ): Observable<BaseResponse<UserModel>>
}