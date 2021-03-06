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
    fun getDataNewFeed(
        @Query("tag_id") tagId :Long?=null,
        @Query("user_id") userId : Long?= null
    ): Observable<BaseListResponse<NewFeed>>

    @GET("events/{event_id}")
    fun getEventDetail(
        @Path("event_id") event_id: String,
        @Query("user_id") user_id: String
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

    @GET("donations")
    fun getDonates(): Observable<BaseListResponse<DonateModel>>

    @FormUrlEncoded
    @POST("events/comments")
    fun postComment(
        @Field("event_id") eventId: Long,
        @Field("content") content: String
    ): Observable<BaseResponse<Any>>

    @FormUrlEncoded
    @POST("events/user-joined")
    fun joinEvent(@Field("event_id") eventId: Long) : Observable<BaseResponse<Any>>


    @GET("shops")
    fun getShopPackage(): Observable<BaseListResponse<ShopPackage>>

    @GET("users/info")
    fun getInfo(): Observable<BaseResponse<UserModel>>

    @GET("tags")
    fun getMenus() : Observable<BaseListResponse<FeedMenuModel>>
}