package io.tonapi.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetAllTokensResponse(
    @Json(name = "jsonrpc")
    val jsonrpc: String,
    @Json(name = "result")
    val result: Result,
    @Json(name = "id")
    val id: Int
) {
    @JsonClass(generateAdapter = true)
    data class Result(
        @Json(name = "version")
        val version: Int,
        @Json(name = "assets")
        val assets: List<Asset>
    ) {
        @JsonClass(generateAdapter = true)
        data class Asset(
            @Json(name = "contract_address")
            val contractAddress: String,
            @Json(name = "symbol")
            val symbol: String,
            @Json(name = "display_name")
            val displayName: String,
            @Json(name = "image_url")
            val imageUrl: String,
            @Json(name = "decimals")
            val decimals: Int,
            @Json(name = "priority")
            val priority: Int,
            @Json(name = "kind")
            val kind: String,
            @Json(name = "wallet_address")
            val walletAddress: String?,
            @Json(name = "balance")
            val balance: String?,
            @Json(name = "deprecated")
            val deprecated: Boolean,
            @Json(name = "community")
            val community: Boolean,
            @Json(name = "blacklisted")
            val blacklisted: Boolean,
            @Json(name = "default_symbol")
            val defaultSymbol: Boolean,
            @Json(name = "default_list")
            val defaultList: Boolean,
            @Json(name = "third_party_usd_price")
            val thirdPartyUsdPrice: String?,
            @Json(name = "third_party_price_usd")
            val thirdPartyPriceUsd: String?,
            @Json(name = "dex_usd_price")
            val dexUsdPrice: String?,
            @Json(name = "dex_price_usd")
            val dexPriceUsd: String?
        )
    }
}