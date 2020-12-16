package com.example.machinetestandroid.ui.paging3.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.machinetestandroid.R
import kotlinx.android.synthetic.main.paging3_recyclerview_load_state_footer.view.*

class Paging3LoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<Paging3LoadStateAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(itemView: View, retry: () -> Unit) : RecyclerView.ViewHolder(itemView) {
        val pbProgress: ProgressBar = itemView.pb_progress
        val tvError: TextView = itemView.tv_retry
        val btnRetry: Button = itemView.btn_retry

        init {
            btnRetry.setOnClickListener {
                retry.invoke()
            }
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.pbProgress.isVisible = loadState is LoadState.Loading
        holder.tvError.isVisible = loadState !is LoadState.Loading
        holder.btnRetry.isVisible = loadState !is LoadState.Loading
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        context = parent.context
        return LoadStateViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.paging3_recyclerview_load_state_footer, parent, false), retry
        )
    }
}
