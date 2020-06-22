package com.jenish.veterinaryclinic.ui.pet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jenish.veterinaryclinic.R
import com.jenish.veterinaryclinic.data.Resource
import com.jenish.veterinaryclinic.databinding.PetFragmentBinding
import com.jenish.veterinaryclinic.di.Injection

/**
 * Developed By JENISH KHANPARA on 18 June 2020.
 */
class PetFragment : Fragment() {
    private val petViewModel by viewModels<PetViewModel> { Injection.petViewModelFactory }
    private lateinit var petAdapter: PetAdapter
    private lateinit var binding: PetFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PetFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = petViewModel
        }

        // set call button click listener
        binding.buttonCall.setOnClickListener {
            onContactButtonClicked()
        }

        // set chat button click listener
        binding.buttonChat.setOnClickListener {
            onContactButtonClicked()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Pet list configuration
        petAdapter = PetAdapter(this) { pet ->
            findNavController().navigate(PetFragmentDirections.toPetInfo(pet.title, pet.contentUrl))
        }
        binding.recyclerviewPet.apply { adapter = petAdapter }

        // Start observing ViewModels
        petViewModel.pets.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            if (it.status == Resource.Status.SUCCESS)
                petAdapter.submitList(it.data)
        })
    }

    private fun onContactButtonClicked() {
        activity?.let {
            val message = if (petViewModel.isWorkingHours()) {
                R.string.within_work_hours_message
            } else {
                R.string.outside_work_hours_message
            }

            val builder = AlertDialog.Builder(it)
            builder.apply {
                setMessage(message)
                setPositiveButton(
                    android.R.string.ok
                ) { dialog, _ ->
                    dialog.dismiss()
                }
            }
            builder.create().show()
        }
    }

}