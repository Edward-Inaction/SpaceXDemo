package life.inaction.assignment.spacexdemo.usercase

import life.inaction.assignment.spacexdemo.api.SpaceXService
import life.inaction.assignment.spacexdemo.model.RocketModel

class FetchRocketCase(private val mSpaceXService: SpaceXService) {
    @Throws(UnknownException::class)
    fun run(rocketId: String): RocketModel {
        try {
            val rocket = mSpaceXService.fetchRocket(rocketId).execute()
            rocket.body()?.let {
                return it
            }
            throw UnknownException()
        } catch (e: Exception) {
            throw UnknownException()
        }
    }
}
