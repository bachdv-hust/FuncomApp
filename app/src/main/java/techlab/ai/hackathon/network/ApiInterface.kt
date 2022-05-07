package techlab.ai.hackathon.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import techlab.ai.hackathon.data.model.BaseResponse
import techlab.ai.hackathon.data.model.DemoModel

/**
 * @author BachDV
 */
interface ApiInterface {

    @GET("v1/users/list-friend")
    fun getDemo() : Observable<BaseResponse<DemoModel>>

    @POST("/events/user-joined")
    fun event(eventId : Int) : Observable<BaseResponse<DemoModel>>

}