package com.ftbw.app.bestworld.view.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ftbw.app.bestworld.databinding.FragmentCreateSelectorBinding

class SelectorFragment(private val listener: IFragmentSelector) : Fragment() {

    private var _bdg: FragmentCreateSelectorBinding? = null
    private val bdg get() = _bdg!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bdg = FragmentCreateSelectorBinding.inflate(inflater, container, false)
        return bdg.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bdg.background.setOnClickListener { listener.closeFragmentSelector() }
        bdg.uploadPost.setOnClickListener { listener.uploadPost() }
        bdg.createEvent.setOnClickListener { listener.createEvent() }
    }

    interface IFragmentSelector {
        fun closeFragmentSelector()
        fun uploadPost()
        fun createEvent()
    }
}