package life.inaction.assignment.spacexdemo.api

import life.inaction.assignment.spacexdemo.model.LaunchModel
import life.inaction.assignment.spacexdemo.model.RocketModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SpaceXService {
    @GET("launches")
    fun allLaunches(): Call<List<LaunchModel>>

    @GET("rockets/{rocket_id}")
    fun fetchRocket(@Path("rocket_id") rocketId: String): Call<RocketModel>
}
