package br.com.cwisicredi.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.cwisicredi.databinding.DetailActivityBinding
import br.com.cwisicredi.module.eventModule
import br.com.cwisicredi.viewmodel.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class DetailActivity: AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val detailActivityBinding = DetailActivityBinding.inflate(layoutInflater)
        setContentView(detailActivityBinding.root)
        loadKoinModules(eventModule)
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(eventModule)
    }

    companion object {
        const val DETAIL_INFO = "DetailActivity.DETAIL_INFO"
    }

}