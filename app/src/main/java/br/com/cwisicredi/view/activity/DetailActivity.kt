package br.com.cwisicredi.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.com.cwisicredi.R
import br.com.cwisicredi.databinding.DetailActivityBinding
import br.com.cwisicredi.module.eventModule
import br.com.cwisicredi.viewmodel.DetailViewModel
import br.com.network.dto.EventDTO
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import java.text.SimpleDateFormat
import java.util.*


class DetailActivity: AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModel()

    private var isAllVisible: Boolean = false

    private val eventDTO: EventDTO by lazy { intent.getParcelableExtra(DETAIL_INFO)!! }

    private val dateFormat: SimpleDateFormat by lazy { SimpleDateFormat("dd/MM/yyyy HH:mm",
        Locale.getDefault()) }

    private val detailActivityBinding: DetailActivityBinding by lazy {DetailActivityBinding.inflate(
        layoutInflater
    )}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(detailActivityBinding.root)
        loadKoinModules(eventModule)
        setupMainInformation()
        setupFabButton()
    }

    private fun setupFabButton() {
        detailActivityBinding.detailActivityGoTo.visibility = View.GONE
        detailActivityBinding.detailActivityGoTo.setOnClickListener {
            val gmmIntentUri = Uri.parse(
                String.format(
                    "google.navigation:q=%1\$2s,%2\$2s",
                    eventDTO.latitude,
                    eventDTO.longitude
                )
            )
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        detailActivityBinding.detailActivityShare.visibility = View.GONE
        detailActivityBinding.detailActivityShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            val shareBody = String.format(getString(R.string.detail_activity_share_body),
                eventDTO.title, eventDTO.description,
                dateFormat.format(eventDTO.date), eventDTO.price)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(intent,
                getString(R.string.detail_activity_share_from)))
        }
//        detailActivityBinding.addAlarmActionText.visibility = View.GONE
//        detailActivityBinding.addPersonActionText.visibility = View.GONE
        detailActivityBinding.detailActivityFab.shrink()
        detailActivityBinding.detailActivityFab.setOnClickListener(fabButtonClicked())
    }

    private fun setupMainInformation() {
        detailActivityBinding.detailActivityTitle.text = eventDTO.title
        detailActivityBinding.detailActivityDescription.text = eventDTO.description
        detailActivityBinding.detailActivityPrice.text = String
            .format(getString(R.string.detail_activity_price_label), eventDTO.price)
        Glide.with(this).load(eventDTO.image)
            .placeholder(R.drawable.ic_unavaliable)
            .into(detailActivityBinding.detailActivityEventImage)
    }

    private fun fabButtonClicked(): (v: View) -> Unit = {
        if (!isAllVisible) {
            detailActivityBinding.detailActivityGoTo.show()
            detailActivityBinding.detailActivityShare.show()
//            detailActivityBinding.addAlarmActionText.visibility = View.VISIBLE
//            detailActivityBinding.addPersonActionText.visibility = View.VISIBLE
            detailActivityBinding.detailActivityFab.extend()
        } else {
            detailActivityBinding.detailActivityGoTo.hide()
            detailActivityBinding.detailActivityShare.hide()
//            detailActivityBinding.addAlarmActionText.visibility = View.GONE
//            detailActivityBinding.addPersonActionText.visibility = View.GONE
            detailActivityBinding.detailActivityFab.shrink()
        }
        isAllVisible = !isAllVisible
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(eventModule)
    }

    companion object {
        const val DETAIL_INFO = "DetailActivity.DETAIL_INFO"
    }

}