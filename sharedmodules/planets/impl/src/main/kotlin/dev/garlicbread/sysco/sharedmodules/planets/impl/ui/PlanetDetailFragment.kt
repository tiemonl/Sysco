package dev.garlicbread.sysco.sharedmodules.planets.impl.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.garlicbread.sysco.sharedmodules.planets.impl.R
import com.garlicbread.sysco.sharedmodules.planets.impl.databinding.FragmentPlanetDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.garlicbread.sysco.sharedmodules.planets.impl.viewmodel.PlanetDetailViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlanetDetailFragment : Fragment() {

    private var _binding: FragmentPlanetDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlanetDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanetDetailBinding.inflate(inflater, container, false)
        setupObserver()
        if (arguments != null) {
            viewModel.getPlanet(requireArguments().getInt(BUNDLE_ID))
        }
        return binding.root
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            viewModel.planet.collect { state ->
                when (state) {
                    is PlanetDetailViewModel.PlanetState.Success -> {
                        binding.apply {
                            errorMessage.isVisible = false
                            progressBar.isVisible = false
                            planetImageView.isVisible = true
                            Glide.with(root).load("https://picsum.photos/id/${state.planet.id}/500/500").into(planetImageView)
                            planetNameTextView.apply {
                                isVisible = true
                                text =
                                    getString(R.string.planet_name_format, state.planet.name)
                            }
                            planetOrbitalPeriodTextView.apply {
                                isVisible = true
                                text = getString(
                                    R.string.planet_orbital_period_format,
                                    state.planet.orbitalPeriod
                                )
                            }
                            planetGravityTextView.apply {
                                isVisible = true
                                text = getString(R.string.planet_gravity_format, state.planet.gravity)
                            }
                        }
                    }

                    is PlanetDetailViewModel.PlanetState.Error -> {
                        binding.apply {
                            errorMessage.isVisible = true
                            errorMessage.text = state.message
                            progressBar.isVisible = false
                            planetImageView.isVisible = false
                            planetNameTextView.isVisible = false
                            planetOrbitalPeriodTextView.isVisible = false
                            planetGravityTextView.isVisible = false
                        }
                    }

                    PlanetDetailViewModel.PlanetState.Loading -> {
                        binding.apply {
                            errorMessage.isVisible = false
                            progressBar.isVisible = true
                            planetImageView.isVisible = false
                            planetNameTextView.isVisible = false
                            planetOrbitalPeriodTextView.isVisible = false
                            planetGravityTextView.isVisible = false
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val BUNDLE_ID = "id"
        fun newInstance(id: Int) = PlanetDetailFragment().apply {
            arguments = bundleOf(BUNDLE_ID to id)
        }
    }
}