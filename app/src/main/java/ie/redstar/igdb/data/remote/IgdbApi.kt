package ie.redstar.igdb.data.remote

import ie.redstar.igdb.data.model.GameDetailModel
import ie.redstar.igdb.data.model.GameListModel
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface IgdbApi {

    @POST("games/")
    suspend fun getVideoGames(@Body filters: RequestBody): List<GameListModel>

    @POST("games/")
    suspend fun getVideoGameDetail(@Body filters: RequestBody): List<GameDetailModel>
}