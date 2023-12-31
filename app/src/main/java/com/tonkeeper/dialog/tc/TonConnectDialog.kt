package com.tonkeeper.dialog.tc

import android.content.Context
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.AppCompatTextView
import com.facebook.drawee.view.SimpleDraweeView
import com.tonapps.tonkeeperx.R
import com.tonkeeper.core.tonconnect.models.TCData
import ton.extensions.toUserFriendly
import uikit.base.BaseSheetDialog
import uikit.widget.LoaderView
import uikit.widget.ProcessTaskView

class TonConnectDialog(
    context: Context,
    private val doOnConnect: ((data: TCData) -> Unit)
): BaseSheetDialog(context) {

    private val loaderView: LoaderView
    private val contentView: View
    private val appIconView: SimpleDraweeView
    private val siteIconView: SimpleDraweeView
    private val nameView: AppCompatTextView
    private val descriptionView: AppCompatTextView
    private val connectButton: Button
    private val connectProcessView: ProcessTaskView
    private val cryptoView: TonConnectCryptoView
    private var data: TCData? = null

    init {
        setContentView(R.layout.dialog_ton_connect)
        loaderView = findViewById(R.id.loader)!!
        contentView = findViewById(R.id.content)!!
        appIconView = findViewById(R.id.app_icon)!!
        appIconView.setImageURI("res:///${R.raw.tonkeeper_logo}")
        siteIconView = findViewById(R.id.site_icon)!!
        nameView = findViewById(R.id.name)!!
        descriptionView = findViewById(R.id.description)!!
        connectButton = findViewById(R.id.connect_button)!!
        connectButton.setOnClickListener { connectWallet() }
        connectProcessView = findViewById(R.id.connect_process)!!
        cryptoView = findViewById(R.id.crypto)!!
    }

    private fun connectWallet() {
        connectButton.visibility = View.GONE
        connectProcessView.visibility = View.VISIBLE
        connectProcessView.state = ProcessTaskView.State.LOADING
        data?.let(doOnConnect)
    }

    override fun show() {
        super.show()
        loaderView.visibility = View.VISIBLE

        connectProcessView.visibility = View.GONE
        contentView.visibility = View.GONE
        connectButton.visibility = View.VISIBLE
    }

    fun setData(d: TCData) {
        cryptoView.setKey(d.accountId.toUserFriendly())
        siteIconView.setImageURI(d.manifest.iconUrl)
        nameView.text = context.getString(R.string.ton_connect_title, d.manifest.name)
        descriptionView.text = context.getString(R.string.ton_connect_description, d.host, d.shortAddress, "V")

        data = d

        showContent()
    }

    fun setSuccess() {
        connectProcessView.state = ProcessTaskView.State.SUCCESS
        dismissDelay()
    }

    fun setFailure() {
        connectProcessView.state = ProcessTaskView.State.FAILED
        dismissDelay()
    }

    private fun showContent() {
        loaderView.visibility = View.GONE
        contentView.visibility = View.VISIBLE
    }

}