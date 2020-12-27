package br.com.cwisicredi.view.activity

import android.os.Bundle
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

class DetailActivity: AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModel()

    private val eventDTO: EventDTO by lazy { intent.getParcelableExtra(DETAIL_INFO)!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val detailActivityBinding = DetailActivityBinding.inflate(layoutInflater)
        setContentView(detailActivityBinding.root)
        loadKoinModules(eventModule)

        detailActivityBinding.detailActivityTitle.text = eventDTO.title
        detailActivityBinding.detailActivityDescription.text = eventDTO.description
        detailActivityBinding.detailActivityPrice.text = String
            .format(getString(R.string.detail_activity_price_label), eventDTO.price)
        Glide.with(this).load(eventDTO.image)
            .placeholder(R.drawable.ic_unavaliable)
            .into(detailActivityBinding.detailActivityEventImage)
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(eventModule)
    }

    companion object {
        const val DETAIL_INFO = "DetailActivity.DETAIL_INFO"
    }

}