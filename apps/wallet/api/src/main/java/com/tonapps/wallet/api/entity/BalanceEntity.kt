package com.tonapps.wallet.api.entity

import android.os.Parcelable
import com.tonapps.blockchain.Coin
import io.tonapi.models.JettonBalance
import io.tonapi.models.TokenRates
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class BalanceEntity(
    val token: TokenEntity,
    val value: Float,
    val walletAddress: String
): Parcelable {

    @IgnoredOnParcel
    var rates: TokenRates? = null

    constructor(jettonBalance: JettonBalance) : this(
        token = TokenEntity(jettonBalance.jetton),
        value = Coin.parseFloat(jettonBalance.balance, jettonBalance.jetton.decimals),
        walletAddress = jettonBalance.walletAddress.address,
    ) {
        rates = jettonBalance.price
    }
}