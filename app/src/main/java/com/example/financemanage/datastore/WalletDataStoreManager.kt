// com.example.financemanage.datastore.WalletDataStoreManager.kt

package com.example.financemanage.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class WalletDataStoreManager(private val context: Context) {
    private val Context.dataStore by preferencesDataStore("wallet_prefs")

    companion object {
        val WALLET_ID = intPreferencesKey("wallet_id")
    }

    suspend fun saveWalletId(id: Int) {
        context.dataStore.edit { prefs ->
            prefs[WALLET_ID] = id
        }
    }

    suspend fun getWalletId(): Int? {
        return context.dataStore.data.first()[WALLET_ID]
    }
}
