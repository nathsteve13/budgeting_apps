package com.ubaya.budgetingapps.view.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.budgetingapps.databinding.FragmentReportBinding
import com.ubaya.budgetingapps.viewmodel.BudgetViewModel

class ReportFragment : Fragment() {
    private lateinit var binding: FragmentReportBinding
    private lateinit var viewModel: BudgetViewModel
    private lateinit var reportAdapter: ReportAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(BudgetViewModel::class.java)
        reportAdapter = ReportAdapter(arrayListOf())

        binding.recViewReport.layoutManager = LinearLayoutManager(context)
        binding.recViewReport.adapter = reportAdapter

        observeViewModel()
        viewModel.refresh()
    }

    private fun observeViewModel() {
        viewModel.budgetLD.observe(viewLifecycleOwner) { budgets ->
            reportAdapter.updateList(budgets)

            val totalBudget = budgets.sumOf { it.amount.toIntOrNull() ?: 0 }
            val totalUsed = budgets.sumOf { it.used.toIntOrNull() ?: 0 }
            val totalLeft = totalBudget - totalUsed

            binding.tvTotalBudget.text = "Total Budget: Rp $totalBudget"
            binding.tvTotalExpense.text = "Total Terpakai: Rp $totalUsed"
            binding.tvRemainingBudget.text = "Sisa Budget: Rp $totalLeft"
        }
    }
}
