package com.tonapps.tonkeeper.ui.screen.stake

import android.icu.text.DecimalFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonapps.tonkeeper.ui.screen.stake.StakeOptionsUiState.StakeInfo
import com.tonapps.uikit.list.ListCell
import com.tonapps.wallet.api.entity.StakePoolsEntity.PoolImplementationType
import com.tonapps.wallet.data.stake.StakeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal

class StakeOptionsViewModel(
    private val repository: StakeRepository,
) : ViewModel() {

    private val df = DecimalFormat("#.##")

    private val _uiState = MutableStateFlow(StakeOptionsUiState())
    val uiState: StateFlow<StakeOptionsUiState> = _uiState

    init {
        viewModelScope.launch {
            val pools = repository.get()
            val maxApyByType = mutableMapOf<PoolImplementationType, BigDecimal>()
            val maxApy = pools.pools.maxByOrNull { it.apy } ?: return@launch
            pools.pools.forEach {
                val poolType = it.implementation
                val currentMaxApy = maxApyByType[poolType] ?: BigDecimal.ZERO
                if (it.apy > currentMaxApy) {
                    maxApyByType[poolType] = it.apy
                }
            }
            val liquid = mutableListOf<StakeInfo.Liquid>()
            val other = mutableListOf<StakeInfo.Other>()
            pools.implementations.forEach {
                when (it.key) {
                    PoolImplementationType.liquidTF.value -> liquid.add(
                        StakeInfo.Liquid(
                            name = it.value.name,
                            description = it.value.description,
                            maxApyFormatted = df.format(maxApyByType[PoolImplementationType.liquidTF]),
                            isMaxApy = maxApy.implementation == PoolImplementationType.liquidTF,
                            type = PoolImplementationType.liquidTF,
                            selected = false
                        )
                    )

                    PoolImplementationType.whales.value -> other.add(
                        StakeInfo.Other(
                            name = it.value.name,
                            description = it.value.description,
                            maxApyFormatted = df.format(maxApyByType[PoolImplementationType.whales]),
                            isMaxApy = maxApy.implementation == PoolImplementationType.whales,
                            type = PoolImplementationType.whales,
                            expandable = true,
                        )
                    )

                    else -> other.add(
                        StakeInfo.Other(
                            name = it.value.name,
                            description = it.value.description,
                            maxApyFormatted = df.format(maxApyByType[PoolImplementationType.tf]),
                            isMaxApy = maxApy.implementation == PoolImplementationType.tf,
                            type = PoolImplementationType.tf,
                            expandable = true,
                        )
                    )
                }
            }

            val positionedLiquid = liquid.mapIndexed { index, it ->
                it.copy(position = ListCell.getPosition(liquid.size, index))
            }
            val positionedOther = other.mapIndexed { index, it ->
                it.copy(position = ListCell.getPosition(other.size, index))
            }

            _uiState.value = StakeOptionsUiState(positionedLiquid + positionedOther)
        }
    }
}

data class StakeOptionsUiState(
    val info: List<StakeInfo> = emptyList()
) {

    sealed class StakeInfo(
        open val name: String,
        open val description: String,
        open val maxApyFormatted: String,
        open val isMaxApy: Boolean,
        open val type: PoolImplementationType,
        open val position: ListCell.Position
    ) {
        data class Liquid(
            override val name: String,
            override val description: String,
            override val maxApyFormatted: String,
            override val isMaxApy: Boolean,
            override val type: PoolImplementationType,
            override val position: ListCell.Position = ListCell.Position.SINGLE,
            val selected: Boolean
        ) : StakeInfo(name, description, maxApyFormatted, isMaxApy, type, position)

        data class Other(
            override val name: String,
            override val description: String,
            override val maxApyFormatted: String,
            override val isMaxApy: Boolean,
            override val type: PoolImplementationType,
            override val position: ListCell.Position = ListCell.Position.SINGLE,
            val expandable: Boolean
        ) : StakeInfo(name, description, maxApyFormatted, isMaxApy, type, position)
    }

}