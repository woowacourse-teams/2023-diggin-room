package com.digginroom.digginroom.feature.room.customview.roominfoview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.digginroom.digginroom.databinding.DialogTrackInfoLayoutBinding
import com.digginroom.digginroom.model.TrackModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommentDialog : BottomSheetDialogFragment() {
    private lateinit var track: TrackModel
    private lateinit var binding: DialogTrackInfoLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogTrackInfoLayoutBinding.inflate(inflater, container, false)
        binding.trackModel = track
        return binding.root
    }

    fun updateTrackModel(track: TrackModel) {
        this.track = track
    }
}