package com.example.pokedex.ui.permission

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentPermissionBinding

class PermissionFragment : Fragment() {

    private var _binding: FragmentPermissionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPermissionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkDrawOverlayPermission()
        binding.permissionButton.setOnClickListener{
            val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            ContextCompat.startActivity(requireActivity(), myIntent, Bundle.EMPTY)
        }
    }

    private fun checkDrawOverlayPermission() {
        // check if we already  have permission to draw over other apps
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {
                if (!Settings.canDrawOverlays(requireActivity())) {

                }
            } else {
                Navigation.findNavController(requireView()).navigate(R.id.action_permissionFragment_to_pokemonListFragment)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkDrawOverlayPermission()
    }
}
