package life.inaction.assignment.spacexdemo.model

import com.google.gson.annotations.SerializedName

data class RocketModel(
    val active: Boolean,

    val stages: Int,

    val boosters: Int,

    @SerializedName("cost_per_launch")
    val cost: Long,

    @SerializedName("success_rate_pct")
    val successRate: Int,

    @SerializedName("first_flight")
    val firstFlight: String,

    val country: String,

    val company: String,

    @SerializedName("engines")
    val engine: Engine,

    val wikipedia: String,
    val description: String
    ) {
    data class Engine(
        val type: String
    )
}
