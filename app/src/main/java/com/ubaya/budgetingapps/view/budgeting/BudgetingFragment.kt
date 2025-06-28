package com.ubaya.budgetingapps.view.budgeting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.budgetingapps.databinding.FragmentBudgetingBinding
import com.ubaya.budgetingapps.viewmodel.BudgetViewModel

class BudgetingFragment : Fragment() {
    private lateinit var binding: FragmentBudgetingBinding
    private lateinit var viewModel: BudgetViewModel
    private val budgetAdapter = BudgetAdapter(arrayListOf()) { budget ->
        val action = BudgetingFragmentDirections.actionBudgetingFragmentToBudgetEditFragment(budget.uuid)
        Navigation.findNavController(requireView()).navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBudgetingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this).get(BudgetViewModel::class.java)
        viewModel.refresh()

        binding.recViewBudget.layoutManager = LinearLayoutManager(context)
        binding.recViewBudget.adapter = budgetAdapter

        binding.btnAdd.setOnClickListener {
            val action = BudgetingFragmentDirections.actionBudgetingFragmentToBudgetEditFragment(-1)
            findNavController().navigate(action)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.budgetLD.observe(viewLifecycleOwner) {
            budgetAdapter.updateBudgetList(it)
        }
    }
}
