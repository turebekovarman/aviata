package com.example.aviatickets.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aviatickets.R
import com.example.aviatickets.adapter.OfferListAdapter
import com.example.aviatickets.databinding.FragmentOfferListBinding
import com.example.aviatickets.model.service.FakeService

class OfferListFragment : Fragment() {

    var offers: List<Offer> = emptyList()
    companion object {
        fun newInstance() = OfferListFragment()
    }

    private var _binding: FragmentOfferListBinding? = null
    private val binding
        get() = _binding!!

    private val adapter: OfferListAdapter by lazy {
        OfferListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOfferListBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        ApiClient.apiService.getFlights().enqueue(object : retrofit2.Callback<List<Offer>> {
            override fun onResponse(call: Call<List<Offer>>, response: Response<List<Offer>>) {
                if (response.isSuccessful) {
                    offers = response.body() ?: emptyList()
                    adapter.submitList(offers)

                } else {

                    print("something wrong")

                }
            }

            override fun onFailure(call: Call<List<Offer>>, t: Throwable) {
                println(t)
            }
        })

    }

    private fun setupUI() {
        with(binding) {
            offerList.adapter = adapter

            sortRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.sort_by_price -> {
                        val sortedByPrice = offers.sortedBy { it.price }
                        adapter.submitList(sortedByPrice.toList())
                    }
                    R.id.sort_by_duration -> {
                        val sortedByDuration = offers.sortedBy { it.flight.duration }
                        adapter.submitList(sortedByDuration.toList())
                    }
                }
            }
        }
    }
}