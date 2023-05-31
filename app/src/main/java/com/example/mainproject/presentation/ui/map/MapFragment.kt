package com.example.mainproject.presentation.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.mainproject.databinding.FragmentMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.search.*

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    private lateinit var searchManager: SearchManager
    private lateinit var searchSession: Session

    companion object {
        const val LOCATION_RESULT_KEY = "LOCATION_RESULT_KEY"
        const val LOCATION_BUNDLE_KEY = "LOCATION_BUNDLE_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(binding.cardTitleMap) { cardView, insets ->
            val navigationInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            val params = cardView.layoutParams as ConstraintLayout.LayoutParams

            params.setMargins(
                cardView.marginLeft,
                cardView.marginTop + navigationInsets.top,
                cardView.marginRight,
                cardView.marginBottom
            )
            cardView.layoutParams = params

            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.cardLocationMap) { cardView, insets ->
            val navigationInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
            val params = cardView.layoutParams as ConstraintLayout.LayoutParams

            params.setMargins(
                cardView.marginLeft,
                cardView.marginTop,
                cardView.marginRight,
                cardView.marginBottom + navigationInsets.bottom
            )
            cardView.layoutParams = params

            insets
        }

        binding.buttonBackMap.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.mapview.map.move(
            CameraPosition(Point(54.180536, 45.178863), 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0f),
            null
        )

        // просто метка на карте
        // binding.mapview.map.mapObjects.addPlacemark(Point(54.180536, 45.178863))

        binding.mapview.map.addCameraListener { _, cameraPosition, _, finished ->
            if (finished) {
                val latitude = cameraPosition.target.latitude
                val longitude = cameraPosition.target.longitude

                searchManager =
                    SearchFactory.getInstance().createSearchManager(SearchManagerType.ONLINE)
                val point = Point(latitude, longitude)
                val searchListener = object : Session.SearchListener {
                    override fun onSearchError(p0: com.yandex.runtime.Error) {
                        Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
                    }

                    override fun onSearchResponse(p0: Response) {
                        val addressComponents =
                            p0.collection.children.firstOrNull()?.obj?.metadataContainer?.getItem(
                                ToponymObjectMetadata::class.java
                            )?.address?.components

                        if (addressComponents != null) {
                            val city =
                                addressComponents.firstOrNull { it.kinds.contains(Address.Component.Kind.LOCALITY) }
                            val street =
                                addressComponents.firstOrNull { it.kinds.contains(Address.Component.Kind.STREET) }
                            val house =
                                addressComponents.firstOrNull { it.kinds.contains(Address.Component.Kind.HOUSE) }
                            binding.textLoactionMap.text =
                                "${city?.name}, ${street?.name}, ${house?.name}"
                        }
                    }
                }

                searchSession = searchManager.submit(
                    point,
                    16,
                    SearchOptions().setSearchTypes(SearchType.GEO.value),
                    searchListener
                )
            }
        }

        binding.buttonEnterMap.setOnClickListener {
            if (binding.textLoactionMap.text != "") {
                setFragmentResult(
                    LOCATION_RESULT_KEY,
                    bundleOf(LOCATION_BUNDLE_KEY to binding.textLoactionMap.text)
                )
                parentFragmentManager.popBackStack()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapview.onStart()
    }

    override fun onStop() {
        binding.mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}