package vn.vngalaxy.fas.data.source.remote.appwrite

import android.content.Context
import io.appwrite.Client
import io.appwrite.services.Account
import io.appwrite.services.Databases
import io.appwrite.services.Realtime

class AppwriteManager(private val context: Context) {
    private lateinit var client: Client
    private lateinit var account: Account
    private lateinit var database: Databases
    private lateinit var realtime: Realtime

    fun initClient(endpoint: String, projectId: String) {
        client = Client(context)
            .setEndpoint(endpoint)
            .setProject(projectId)
            .setSelfSigned(true)

        account = Account(client)
        database = Databases(client)
        realtime = Realtime(client)
    }

    fun getAccount(): Account {
        return account
    }

    fun getDatabase(): Databases {
        return database
    }

    fun getRealtime(): Realtime {
        return realtime
    }
}
