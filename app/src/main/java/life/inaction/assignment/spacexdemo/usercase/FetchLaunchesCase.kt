package life.inaction.assignment.spacexdemo.usercase

import life.inaction.assignment.spacexdemo.api.SpaceXService
import life.inaction.assignment.spacexdemo.model.LaunchModel

class FetchLaunchesCase(private val mSpaceXService: SpaceXService) {
    @Throws(UnknownException::class)
    fun run(): List<LaunchModel> {
        try {
            val launches = mSpaceXService.allLaunches().execute()
            launches.body()?.let {
                return it
            }
            throw UnknownException()
        } catch (e: Exception) {
            throw UnknownException()
        }
    }
}
