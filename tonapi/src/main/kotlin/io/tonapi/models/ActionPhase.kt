/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package io.tonapi.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param success 
 * @param totalActions 
 * @param skippedActions 
 * @param fwdFees 
 * @param totalFees 
 */


data class ActionPhase (

    @Json(name = "success")
    val success: kotlin.Boolean,

    @Json(name = "total_actions")
    val totalActions: kotlin.Int,

    @Json(name = "skipped_actions")
    val skippedActions: kotlin.Int,

    @Json(name = "fwd_fees")
    val fwdFees: kotlin.Long,

    @Json(name = "total_fees")
    val totalFees: kotlin.Long

)
