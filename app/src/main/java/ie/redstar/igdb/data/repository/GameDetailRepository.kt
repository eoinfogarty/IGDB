package ie.redstar.igdb.data.repository

import android.util.Log
import com.api.igdb.apicalypse.APICalypse
import ie.redstar.igdb.data.local.GameDetailDao
import ie.redstar.igdb.data.model.GameDetailModel
import ie.redstar.igdb.data.remote.IgdbApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

interface GameDetailRepository {

    fun getGameDetails(id: Int): Flow<GameDetailResult>
}

sealed class GameDetailResult {
    data class Success(
        val games: GameDetailModel
    ) : GameDetailResult()

    data class Error(
        val error: Exception,
        val cachedGame: GameDetailModel?
    ) : GameDetailResult()
}

class GameDetailDataSource(
    private val igdbApi: IgdbApi,
    private val gameDetailDao: GameDetailDao
) : GameDetailRepository {

    override fun getGameDetails(id: Int) = flow<GameDetailResult> {
        val query = APICalypse()
            .fields("name, first_release_date, rating, summary, storyline, cover.image_id, genres.name, screenshots.image_id, similar_games.name, similar_games.cover.image_id, similar_games.first_release_date")
            .where("id = $id")
            .buildQuery()
            .toRequestBody("text/plain".toMediaType())

        val result = try {
            val gameDetail = igdbApi.getVideoGameDetail(query).first()
            gameDetailDao.insert(gameDetail)

            val cachedGame = gameDetailDao.getGamesDetail(id)
            GameDetailResult.Success(cachedGame)
        } catch (e: Exception) {
            Log.e(this::class.simpleName, "Error  :$e")
            val cachedGame = gameDetailDao.getGamesDetail(id)
            GameDetailResult.Error(e, cachedGame)
        }

        emit(result)
    }.flowOn(Dispatchers.IO)
}
