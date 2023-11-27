package com.tonkeeper.api.nft.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tonkeeper.api.toJSON
import com.tonkeeper.api.userLikeAddress
import io.tonapi.models.NftItem

@Entity(tableName = "nft")
class NftEntity(
    @PrimaryKey val nftAddress: String,
    val data: String
) {

    constructor(nftItem: NftItem) : this(
        nftItem.address.userLikeAddress,
        toJSON(nftItem)
    )
}