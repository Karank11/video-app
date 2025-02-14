package com.example.videoapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videoapp.R
import com.example.videoapp.databinding.FragmentVideoFeedBinding
import com.example.videoapp.databinding.VideoItemBinding
import com.example.videoapp.domain.Video
import com.example.videoapp.viewmodels.VideoFeedViewModel

class VideoFeedFragment : Fragment() {

    private val viewModel: VideoFeedViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, VideoFeedViewModel.Factory(activity.application)).get(VideoFeedViewModel::class.java)
    }

    /**
     * RecyclerView Adapter to convert a list of videos to cards.
     */
    private var videoAdapter: VideoAdapter? = null

    /**
     * Helper method to generate Youtube app Links
     */
    private val Video.launchUri: Uri
        get() {
            val httpUri = Uri.parse(url)
            return Uri.parse("vnd.youtube:" + httpUri.getQueryParameter("v"))
        }

    /**
     * This is called immediately after onCreateView() has returned, and fragment's view hierarchy has been created.
     * It can be used to do final initialization.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.playlist.observe(viewLifecycleOwner) {videos ->
            videos?.let {
                videoAdapter?.videos = it
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentVideoFeedBinding.inflate(inflater)

        // Set lifecycleOwner so that dataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        videoAdapter = VideoAdapter(VideoClick {video ->
            // When a video is clicked, this lambda will be called by VideoAdapter.
            val packageManager = context?.packageManager ?: return@VideoClick

            // Try to generate the direct intent to Youtube App.
            var intent = Intent(Intent.ACTION_VIEW, video.launchUri)
            if (intent.resolveActivity(packageManager) == null) {
                intent = Intent(Intent.ACTION_VIEW, Uri.parse(video.url))
            }
            startActivity(intent)
        })

        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = videoAdapter
        }

        return binding.root
    }
}

/**
 * Click Listener for Videos.
 */
class VideoClick(val block: (Video) -> Unit) {
    fun onClick(video: Video) = block(video)
}

class VideoAdapter(private val callback: VideoClick): RecyclerView.Adapter<VideoViewHolder>() {

    var videos: List<Video> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = videos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding: VideoItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.video_item,
            parent,
            false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.binding.also {
            it.video = videos[position]
            it.videoCallback = callback
        }
    }

}

class VideoViewHolder(val binding: VideoItemBinding): RecyclerView.ViewHolder(binding.root)