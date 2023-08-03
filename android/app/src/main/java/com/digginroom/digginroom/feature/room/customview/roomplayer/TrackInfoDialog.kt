package com.digginroom.digginroom.feature.room.customview.roomplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.digginroom.digginroom.databinding.DialogTrackInfoLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TrackInfoDialog() :
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
        binding.tvDescription.text = "sdfsdfsdfsdfdsf"
    }
}
