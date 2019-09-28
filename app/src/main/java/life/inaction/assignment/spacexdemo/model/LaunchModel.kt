package life.inaction.assignment.spacexdemo.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class LaunchModel(
    @SerializedName("flight_number")
    val flightNumber: Long,

    @SerializedName("mission_name")
    val missionName: String,

    val upcoming: Boolean,

    @SerializedName("launch_year")
    val launchYear: String,

    @SerializedName("is_tentative")
    val tentative: Boolean,

    val rocket: Rocket,

    @SerializedName("launch_success")
    val success: Boolean?,

    val details: String,

    @SerializedName("launch_date_utc")
    val launchDate: Date,

    @SerializedName("launch_site")
    val launchSite: LaunchSite,

    @SerializedName("launch_failure_details")
    val failureDetail: FailureDetail?,

    val links: Link
) : Serializable {
    data class Link(val wikipedia: String) : Serializable
    data class LaunchSite(
        @SerializedName("site_id")
        val id: String,

        @SerializedName("site_name")
        val name: String,

        @SerializedName("site_name_long")
        val fullName: String
    ) : Serializable
    data class Rocket(
        @SerializedName("rocket_id")
        val id: String,

        @SerializedName("rocket_name")
        val name: String,

        @SerializedName("rocket_type")
        val type: String
    ) : Serializable
    data class FailureDetail(
        val time: Int,
        val reason: String
    ) : Serializable
}
