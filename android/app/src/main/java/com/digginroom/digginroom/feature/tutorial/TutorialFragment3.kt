package com.digginroom.digginroom.feature.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.FragmentTutorial3Binding


class TutorialFragment3 : Fragment() {
    private lateinit var binding: FragmentTutorial3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_tutorial3, container, false)
    }
}