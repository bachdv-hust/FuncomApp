package techlab.ai.hackathon.network

import io.reactivex.Observable
import retrofit2.http.*
import techlab.ai.hackathon.data.model.BaseResponse
import techlab.ai.hackathon.data.model.*

/**
 * @author BachDV
 */
interface ApiInterface {

    @GET("v1/users/list-friend")
    fun getDemo(): Observable<BaseResponse<DemoModel>>

    @GET("events")
    fun getDataNewFeed(): Observable<BaseListResponse<NewFeed>>

    @GET("events/{eventId}")
    fun getEventDetail(
        @Path("eventId") eventId: String,
        @Query("userId") userId: String
    ): Observable<BaseResponse<EventDetail>>

    @FormUrlEncoded
    @POST("auth/register")
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("name") name: String
    ): Observable<BaseResponse<UserModel>>

    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<BaseResponse<UserModel>>

    @GET("events/comments")
    fun getComments(
        @Query("event_id") id: Long
    ): Observable<BaseListResponse<CommentModel>>

    @FormUrlEncoded
    @POST("events/comments")
    fun postComment(
        @Field("event_id") eventId: Long,
        @Field("content") content: String
    ): Observable<BaseResponse<Any>>
    @POST("/events/user-joined")
    fun event(eventId : Int) : Observable<BaseResponse<DemoModel>>


    @GET("shops")
    fun getShopPackage(): Observable<BaseListResponse<ShopPackage>>
}