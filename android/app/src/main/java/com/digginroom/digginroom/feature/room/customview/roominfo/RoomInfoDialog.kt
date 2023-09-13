package com.digginroom.digginroom.feature.room.customview.roominfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.digginroom.digginroom.databinding.DialogTrackInfoLayoutBinding
import com.digginroom.digginroom.model.TrackModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RoomInfoDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogTrackInfoLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogTrackInfoLayoutBinding.inflate(inflater, container, false)
        isCancelable = true
        return binding.root
    }

    fun show(fragmentManager: FragmentManager, trackModel: TrackModel) {
        showNow(fragmentManager, "")
        binding.trackModel = trackModel
    }
}
