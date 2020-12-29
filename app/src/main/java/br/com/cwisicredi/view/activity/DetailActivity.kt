package br.com.cwisicredi.view.activity

import android.content.ClipDescription
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.com.cwisicredi.R
import br.com.cwisicredi.databinding.DetailActivityBinding
import br.com.cwisicredi.databinding.DetailCheckInDialogBinding
import br.com.cwisicredi.module.eventModule
import br.com.cwisicredi.viewmodel.DetailViewModel
import br.com.network.dto.EventDTO
import br.com.network.exception.NetworkDataException
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import java.text.SimpleDateFormat
import java.util.*


class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModel()

    private var isAllVisible: Boolean = false

    private var isCheckInShowing = false

    private val eventDTO: EventDTO by lazy { intent.getParcelableExtra(DETAIL_INFO)!! }

    private val dateFormat: SimpleDateFormat by lazy {
        SimpleDateFormat(
            getString(R.string.date_format_default), Locale.getDefault()
        )
    }

    private val detailActivityBinding: DetailActivityBinding by lazy {
        DetailActivityBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(detailActivityBinding.root)
        loadKoinModules(eventModule)
        setupMainInformation()
        setupFabButton()
    }

    private fun setupFabButton() {
        setupGoToClick()
        setupShare()
        detailActivityBinding.detailActivityCheckIn.setOnClickListener {
            createCheckInDialog()
            shrinkFab()
        }

        detailActivityBinding.detailActivityGoTo.visibility = View.GONE
        detailActivityBinding.detailActivityShare.visibility = View.GONE
        detailActivityBinding.detailActivityCheckIn.visibility = View.GONE

        detailActivityBinding.detailActivityFab.shrink()
        detailActivityBinding.detailActivityFab.setOnClickListener(fabButtonClicked())
    }

    private fun createCheckInDialog() {
        if (!isCheckInShowing) {
            isCheckInShowing = true
            val dialogBiding = DetailCheckInDialogBinding.inflate(layoutInflater)
            dialogBiding.detailCheckInDialogEmailEdit.error = getString(R.string
                .detail_activity_required_field)
            dialogBiding.detailCheckInDialogNameEdit.error = getString(R.string
                .detail_activity_required_field)
            val dialog = AlertDialog.Builder(this).setTitle(R.string
                .detail_check_in_dialog_confirm)
                .setPositiveButton(R.string.detail_check_in_dialog_confirm,
                    DialogInterface.OnClickListener { _, _ -> })
                .setNegativeButton(R.string.detail_check_in_dialog_cancel,
                    DialogInterface.OnClickListener { _, _ ->  })
                .setView(dialogBiding.root).create()
            dialog.show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(View.OnClickListener {
                    val result = detailViewModel
                        .validateRequiredInfoAndNotifyError(
                            dialogBiding.detailCheckInDialogEmailEdit.text.toString(),
                            dialogBiding.detailCheckInDialogNameEdit.text.toString()
                        )
                    if (result) {
                        dialog.dismiss()
                        detailActivityBinding.progress.visibility = View.VISIBLE
                        executeCheckIn(dialogBiding)

                    }
                })
            dialog.setOnDismissListener {
                isCheckInShowing = false
                shrinkFab()
            }
            observerDialogResult(dialogBiding)
        }
    }

    private fun observerDialogResult(dialogBiding: DetailCheckInDialogBinding) {
        detailViewModel.invalidEmail.removeObservers(this)
        detailViewModel.invalidEmail.observe(this, androidx.lifecycle.Observer {
            dialogBiding.detailCheckInDialogEmailEdit.error = getString(
                R.string
                    .detail_activity_required_field
            )
        })

        detailViewModel.invalidName.removeObservers(this)
        detailViewModel.invalidName.observe(this, androidx.lifecycle.Observer {
            dialogBiding.detailCheckInDialogNameEdit.error = getString(
                R.string
                    .detail_activity_required_field
            )
        })
    }

    private fun executeCheckIn(dialogBiding: DetailCheckInDialogBinding) {
        detailViewModel.checkIn(
            eventDTO.id,
            dialogBiding.detailCheckInDialogNameEdit.text.toString(),
            dialogBiding.detailCheckInDialogEmailEdit.text.toString()
        )
            .observe(
                this, androidx.lifecycle.Observer
                {
                    detailActivityBinding.progress.visibility = View.GONE
                    if (it !is NetworkDataException) {
                        Snackbar
                            .make(
                                detailActivityBinding.root,
                                getString(R.string.detail_activity_check_in_ok),
                                Snackbar.LENGTH_LONG
                            ).show()
                    } else {
                        Snackbar
                            .make(
                                detailActivityBinding.root,
                                getString(R.string.detail_activity_check_in_fail),
                                Snackbar.LENGTH_LONG
                            ).show()
                    }
                })
    }

    private fun setupShare() {
        detailActivityBinding.detailActivityShare.setOnClickListener {
            shrinkFab()
            val intent = Intent(Intent.ACTION_SEND)
            val shareBody = String.format(
                getString(R.string.detail_activity_share_body),
                eventDTO.title, eventDTO.description,
                dateFormat.format(eventDTO.date), eventDTO.price
            )
            intent.type = ClipDescription.MIMETYPE_TEXT_PLAIN
            intent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(
                Intent.createChooser(
                    intent,
                    getString(R.string.detail_activity_share_from)
                )
            )
        }
    }

    private fun setupGoToClick() {
        detailActivityBinding.detailActivityGoTo.setOnClickListener {
            val gmmIntentUri = Uri.parse(
                String.format(
                    getString(R.string.google_navigation_intent_extra),
                    eventDTO.latitude,
                    eventDTO.longitude
                )
            )
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage(getString(R.string.google_navigation_intent))
            detailActivityBinding.detailActivityFab.shrink()
            shrinkFab()
            startActivity(mapIntent)
        }
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
            extendFab()
        } else {
            shrinkFab()
        }
    }

    private fun extendFab() {
        detailActivityBinding.detailActivityGoTo.show()
        detailActivityBinding.detailActivityShare.show()
        detailActivityBinding.detailActivityCheckIn.show()
        detailActivityBinding.detailActivityFab.extend()
        isAllVisible = true
    }

    private fun shrinkFab() {
        detailActivityBinding.detailActivityGoTo.hide()
        detailActivityBinding.detailActivityShare.hide()
        detailActivityBinding.detailActivityCheckIn.hide()
        detailActivityBinding.detailActivityFab.shrink()
        isAllVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(eventModule)
    }

    companion object {
        const val DETAIL_INFO = "DetailActivity.DETAIL_INFO"
    }

}