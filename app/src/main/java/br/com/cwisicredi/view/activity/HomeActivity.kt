package br.com.cwisicredi.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.cwisicredi.databinding.HomeActivityBinding
import br.com.cwisicredi.module.coroutinesModule
import br.com.cwisicredi.module.eventModule
import br.com.cwisicredi.scope.NetworkScope
import br.com.cwisicredi.scope.UiScope
import br.com.cwisicredi.view.adapter.EventsAdapter
import br.com.cwisicredi.viewmodel.HomeViewModel
import br.com.network.dto.EventDTO
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class HomeActivity : AppCompatActivity(), EventsAdapter.EventCallback {

    private lateinit var binding: HomeActivityBinding;

    private val adapter: EventsAdapter by inject()

    private val job: Job by inject()

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadKoinModules(listOf(eventModule, coroutinesModule))
        setEventAdapter()
    }

    override fun onStop() {
        super.onStop()
        job.cancel()
    }

    private fun setEventAdapter() {
        binding.homeActivityList.layoutManager = LinearLayoutManager(this)
        binding.homeActivityList.adapter = this.adapter
        this.adapter.eventCallback = this
        binding.progress.visibility = View.VISIBLE
        homeViewModel.eventLiveData.observe(this, Observer { t ->
                this.adapter.setData(t)
                binding.progress.visibility = View.GONE
                binding.homeActivityError.visibility = View.GONE
            })
        homeViewModel.eventErrorMsgLiveData.observe(this, Observer {
            this.adapter.setData(it)
            binding.progress.visibility = View.GONE
            binding.homeActivityError.visibility = View.VISIBLE
        })
        homeViewModel.getEvents()
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(listOf(eventModule, coroutinesModule))
    }

    override fun onEventClicked(eventDTO: EventDTO) {
        intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.DETAIL_INFO, eventDTO)
        startActivity(intent)
    }

}