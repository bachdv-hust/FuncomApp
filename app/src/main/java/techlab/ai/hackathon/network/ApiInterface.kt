package techlab.ai.hackathon.network

import io.reactivex.Observable
import retrofit2.http.GET
import techlab.ai.hackathon.data.model.BaseListResponse
import techlab.ai.hackathon.data.model.BaseResponse
import techlab.ai.hackathon.data.model.DemoModel
import techlab.ai.hackathon.data.model.NewFeed

/**
 * @author BachDV
 */
interface ApiInterface {

    @GET("v1/users/list-friend")
    fun getDemo() : Observable<BaseResponse<DemoModel>>

    @GET("events")
    fun getDataNewFeed() : Observable<BaseListResponse<NewFeed>>

}