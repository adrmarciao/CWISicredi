package br.com.cwisicredi.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import br.com.cwisicredi.R
import br.com.cwisicredi.databinding.HomeAdapterItemBinding
import br.com.network.dto.EventDTO
import com.bumptech.glide.Glide
import java.util.*
import kotlin.collections.ArrayList

class EventsAdapter(private val context: Context) : Adapter<EventsAdapter.Holder>() {

    lateinit var eventCallback: EventCallback

    private val events: ArrayList<EventDTO> by lazy { ArrayList() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_adapter_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val event = events[position]
        holder.title.text = String.format(
            Locale.getDefault(),
            context.getText(R.string.home_activity_event_name_label).toString(),
            event.title
        )
        holder.description.text = event.description
        Glide.with(context).load(event.image).placeholder(R.drawable.ic_unavaliable)
            .into(holder.eventImage)
        holder.itemView.setOnClickListener {
            eventCallback.onEventClicked(event)
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }

    fun setData(events: List<EventDTO>?) {
        events?.let {
            this.events.clear()
            this.events.addAll(it)
            notifyDataSetChanged()
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView
        val description: TextView
        val eventImage: ImageView

        init {
            val bind = HomeAdapterItemBinding.bind(itemView)
            this.title = bind.homeAdapterEventTitle
            this.description = bind.homeAdapterEventDescription
            this.eventImage = bind.homeAdapterEventImage
        }
    }

    interface EventCallback {
        fun onEventClicked(eventDTO: EventDTO)
    }
}