package ie.redstar.igdb.data.repository

import android.util.Log
import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.apicalypse.Sort
import com.api.igdb.exceptions.RequestException
import ie.redstar.igdb.data.local.GameListDao
import ie.redstar.igdb.data.model.GameListModel
import ie.redstar.igdb.data.remote.IgdbApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

interface GameListRepository {

    fun getGamesList(offset: Int): Flow<GameListResult>
}

sealed class GameListResult {
    data class Success(
        val games: List<GameListModel>
    ) : GameListResult()

    data class Error(
        val error: Throwable,
        val cachedGames: List<GameListModel>
    ) : GameListResult()
}

class GameListDataSource(
    private val igdbApi: IgdbApi,
    private val gameListDao: GameListDao
) : GameListRepository {

    override fun getGamesList(offset: Int) = flow<GameListResult> {
        val query = APICalypse()
            .fields("name, first_release_date, cover.image_id, rating, rating_count")
            .limit(30)
            .offset(offset)
            .where("rating_count > 50")
            .sort("rating", Sort.DESCENDING)
            .buildQuery()
            .toRequestBody("text/plain".toMediaType())

        val result = try {
            val games = igdbApi.getVideoGames(query)
            if (offset == 0) {
                gameListDao.deleteAll()
            }
            gameListDao.insert(games)

            val cachedGames = gameListDao.getGamesList()
            GameListResult.Success(cachedGames)
        } catch (e: RequestException) {
            Log.e(this::class.simpleName, "Error  :$e")
            val cachedGames = gameListDao.getGamesList()
            GameListResult.Error(e, cachedGames)
        }

        emit(result)
    }.flowOn(Dispatchers.IO)
}