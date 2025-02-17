package org.eu.exodus_privacy.exodusprivacy.fragments.apps.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.eu.exodus_privacy.exodusprivacy.R
import org.eu.exodus_privacy.exodusprivacy.databinding.RecyclerViewAppItemBinding
import org.eu.exodus_privacy.exodusprivacy.fragments.apps.AppsFragmentDirections
import org.eu.exodus_privacy.exodusprivacy.fragments.trackerdetail.TrackerDetailFragmentDirections
import org.eu.exodus_privacy.exodusprivacy.manager.database.app.ExodusApplication
import org.eu.exodus_privacy.exodusprivacy.utils.setExodusColor
import org.eu.exodus_privacy.exodusprivacy.utils.setVersionReport

class AppsRVAdapter(
    private val currentDestinationId: Int
) : ListAdapter<ExodusApplication, AppsRVAdapter.ViewHolder>(AppsDiffUtil()) {

    inner class ViewHolder(val binding: RecyclerViewAppItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecyclerViewAppItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val app = getItem(position)

        holder.binding.apply {
            root.setOnClickListener {
                val action = if (currentDestinationId == R.id.appsFragment) {
                    AppsFragmentDirections.actionAppsFragmentToAppDetailFragment(app.packageName)
                } else {
                    TrackerDetailFragmentDirections.actionTrackerDetailFragmentToAppDetailFragment(
                        app.packageName
                    )
                }
                it.findNavController().navigate(action)
            }
            appIconIV.background = app.icon.toDrawable(context.resources)
            appNameTV.text = app.name
            appVersionTV.text = context.getString(R.string.app_version, app.versionName)
            trackersChip.apply {
                val trackerNum = app.exodusTrackers.size
                text = if (app.exodusVersionCode == 0L) "?" else trackerNum.toString()
                setExodusColor(trackerNum)
            }
            permsChip.apply {
                val permsNum = app.permissions.size
                text = permsNum.toString()
                setExodusColor(permsNum)
            }
            versionChip.setVersionReport(app)
        }
    }
}
