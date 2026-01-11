package id.antasari.fpa_230104040205_laportitik.data

import android.content.Context
import io.appwrite.Client
import io.appwrite.services.Account
import io.appwrite.services.Databases
import io.appwrite.services.Storage

object AppwriteClient {
    private const val ENDPOINT = "https://sgp.cloud.appwrite.io/v1"

    // SUDAH DIUPDATE DENGAN ID KAMU:
    private const val PROJECT_ID = "696383920015f73f6be8"

    lateinit var client: Client
    lateinit var account: Account
    lateinit var databases: Databases
    lateinit var storage: Storage

    fun init(context: Context) {
        client = Client(context)
            .setEndpoint(ENDPOINT)
            .setProject(PROJECT_ID)
            .setSelfSigned(true)

        account = Account(client)
        databases = Databases(client)
        storage = Storage(client)
    }
}