package com.example.machinetestandroid.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.machinetestandroid.R
import kotlinx.android.synthetic.main.movie_load_state_footer.view.*

class MovieLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<MovieLoadStateAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(itemView: View, retry: () -> Unit) : RecyclerView.ViewHolder(itemView) {
        val pbProgress = itemView.progressBar
        val tvError = itemView.tv_error
        val btnRetry = itemView.btn_retry

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
        return MovieLoadStateAdapter.LoadStateViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_load_state_footer, parent, false), retry
        )
    }
}