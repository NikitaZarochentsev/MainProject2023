package com.example.mainproject.presentation.ui.neworder

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.mainproject.R
import com.example.mainproject.databinding.FragmentNewOrderBinding
import com.example.mainproject.presentation.ui.map.MapFragment
import com.example.mainproject.presentation.ui.orders.OrdersFragment
import com.example.mainproject.presentation.ui.profile.ProfileFragment
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewOrderFragment : Fragment() {

    private lateinit var binding: FragmentNewOrderBinding
    private val viewModel: NewOrderViewModel by viewModels()

    companion object {
        private const val IMAGE_URL_ARGUMENT = "IMAGE_URL_ARGUMENT"
        private const val PRODUCT_SIZE_ARGUMENT = "PRODUCT_SIZE_ARGUMENT"
        private const val PRODUCT_TITLE_ARGUMENT = "PRODUCT_TITLE_ARGUMENT"
        private const val PRODUCT_DEPARTMENT_ARGUMENT = "PRODUCT_DEPARTMENT_ARGUMENT"
        private const val PRODUCT_PRICE_ARGUMENT = "PRODUCT_PRICE_ARGUMENT"
        private const val PRODUCT_ID_ARGUMENT = "PRODUCT_ID_ARGUMENT"

        fun newInstance(
            imageUrl: String,
            productSize: String,
            productTitle: String,
            productDepartment: String,
            productPrice: Int,
            productId: String,
        ): NewOrderFragment {
            val arguments = Bundle()
            arguments.putString(IMAGE_URL_ARGUMENT, imageUrl)
            arguments.putString(PRODUCT_SIZE_ARGUMENT, productSize)
            arguments.putString(PRODUCT_TITLE_ARGUMENT, productTitle)
            arguments.putString(PRODUCT_DEPARTMENT_ARGUMENT, productDepartment)
            arguments.putInt(PRODUCT_PRICE_ARGUMENT, productPrice)
            arguments.putString(PRODUCT_ID_ARGUMENT, productId)
            val fragment = NewOrderFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(MapFragment.LOCATION_RESULT_KEY) { _, bundle ->
            binding.textLocationNewOrder.setText(
                bundle.getCharSequence(MapFragment.LOCATION_BUNDLE_KEY).toString()
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNewOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarNewOrder.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        val imageUrl = requireArguments().getString(IMAGE_URL_ARGUMENT)
        if (imageUrl != null) {
            binding.imageProductNewOrder.load(imageUrl) {
                transformations(
                    RoundedCornersTransformation(
                        resources.getDimension(R.dimen.normal_border_radius_100)
                    )
                )
            }
        }

        val productTitle = requireArguments().getString(PRODUCT_TITLE_ARGUMENT)
        val productSize = requireArguments().getString(PRODUCT_SIZE_ARGUMENT)
        if (productTitle != null && productSize != null) {
            binding.textViewTitleNewOrder.text =
                getString(R.string.new_order_product_title, productSize, productTitle)
        }

        val productDepartment = requireArguments().getString(PRODUCT_DEPARTMENT_ARGUMENT)
        if (productDepartment != null) {
            binding.textViewDepartmentNewOrder.text = productDepartment
        }

        val productId = requireArguments().getString(PRODUCT_ID_ARGUMENT)

        binding.textLocationNewOrder.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragmentContainerViewMain, MapFragment())
                addToBackStack(null)
            }
        }

        binding.textDateNewOrder.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.date_picker_title)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
            datePicker.addOnPositiveButtonClickListener {
                viewModel.setEtd(DateFormat.format("yyyy-MM-dd'T'HH:mm'Z'", it).toString())
                binding.textDateNewOrder.setText(DateFormat.format("dd MMMM", it))
            }
            datePicker.show(childFragmentManager, null)
        }

        val productPrice = requireArguments().getInt(PRODUCT_PRICE_ARGUMENT)
        binding.progressButtonNewOrder.setText(
            getString(
                R.string.new_order_button,
                productPrice.toString()
            )
        )

        binding.counterNewOrder.setOnChangeListener {
            binding.progressButtonNewOrder.setText(
                getString(
                    R.string.new_order_button,
                    (productPrice * binding.counterNewOrder.getValue()).toString()
                )
            )
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.progressButtonNewOrder) { progressButtonView, insets ->
            val navigationInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
            val params = progressButtonView.layoutParams as ConstraintLayout.LayoutParams
            params.setMargins(
                progressButtonView.marginLeft,
                progressButtonView.marginTop,
                progressButtonView.marginRight,
                progressButtonView.marginBottom + navigationInsets.bottom
            )
            progressButtonView.layoutParams = params
            insets
        }

        binding.progressButtonNewOrder.setOnClickListener {
            binding.progressButtonNewOrder.isLoading = true
            if (productId != null && productSize != null) {
                viewModel.createOrder(
                    productId,
                    binding.counterNewOrder.getValue(),
                    productSize,
                    binding.textLocationNewOrder.text.toString(),
                    binding.textApartmentNewOrder.text.toString(),
                )
            }
        }

        viewModel.newOrderUiState.observe(this as LifecycleOwner) { state ->
            when (state) {
                is NewOrderViewModel.NewOrderUiState.Success -> {
                    Toast.makeText(
                        requireContext(),
                        "Заказ ${state.numberOrder} создан",
                        Toast.LENGTH_SHORT
                    ).show()
                    parentFragmentManager.popBackStack()
                    if (parentFragmentManager.backStackEntryCount == 2) {
                        parentFragmentManager.popBackStack()
                    }
                    parentFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragmentContainerViewMain, ProfileFragment())
                        addToBackStack(null)
                    }
                    parentFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragmentContainerViewMain, OrdersFragment())
                        addToBackStack(null)
                    }
                }
                is NewOrderViewModel.NewOrderUiState.Error -> {
                    Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}