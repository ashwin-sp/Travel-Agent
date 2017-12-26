package actio.ashcompany.com.travelagentv11.retrofit

import actio.ashcompany.com.travelagentv11.callback.ImageCallback
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by ashwin-4529 on 25/12/17.
 */

object RetrofitCallBuilder: Callback<String>
{
   private lateinit var loggingInterceptor: HttpLoggingInterceptor
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var retrofitBuilder: Retrofit
    private lateinit var imageCallback: ImageCallback
    fun initRetroBuilder()
    {
        loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        okHttpClient = OkHttpClient.Builder()
                .readTimeout(40, TimeUnit.SECONDS)
                .connectTimeout(40, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build()
        retrofitBuilder = Retrofit.Builder().baseUrl("https://www.googleapis.com/customsearch/")
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
    }
    fun getData(key:String, cx: String, query: String, callback: ImageCallback)
    {
        val getAPI = retrofitBuilder.create(RetrofitCalls::class.java)
        imageCallback = callback
        val call =  getAPI.getData(key, cx, query)
        try {
            call.enqueue(this)
        }catch (e: Exception)
        {
            e.printStackTrace()
        }
    }
    override fun onResponse(call: Call<String>?, response: Response<String>?) {
        System.out.println("Response Body ==> "+ response!!.body())
        val jresponse = JSONObject(response.body())
        val items = jresponse.getJSONArray("items")
        val firstObject = items.getJSONObject(0)
        imageCallback.updateImage(firstObject.getString("link"))
    }

    override fun onFailure(call: Call<String>?, t: Throwable?) {

    }
}

