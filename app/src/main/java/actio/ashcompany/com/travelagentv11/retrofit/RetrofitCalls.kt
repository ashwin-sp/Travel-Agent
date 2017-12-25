package actio.ashcompany.com.travelagentv11.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by ashwin-4529 on 25/12/17.
 */

interface RetrofitCalls{
    @GET("v1")
    fun getData(@Query("key") key: String, @Query("cx") cx: String, @Query("q") q: String, @Query("searchType") searchType: String="image") : Call<String>
}