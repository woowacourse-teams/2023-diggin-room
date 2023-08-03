package com.digginroom.digginroom.feature.room.customview.roominfoview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.DialogTrackInfoLayoutBinding
import com.digginroom.digginroom.model.TrackModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TrackInfoDialog(val trackModel: TrackModel) :
    BottomSheetDialogFragment() {

    private lateinit var binding: DialogTrackInfoLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogTrackInfoLayoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.trackModel = trackModel
        binding.tvDescription.text =
            view.context.getString(R.string.fake_description).format(trackModel.superGenre)
    }
}
