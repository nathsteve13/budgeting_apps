package com.ubaya.budgetingapps.view.expense

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.budgetingapps.databinding.DialogExpenseDetailBinding
import com.ubaya.budgetingapps.databinding.FragmentExpenseTrackerBinding
import com.ubaya.budgetingapps.model.expense.Expense
import com.ubaya.budgetingapps.viewmodel.ExpenseViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExpenseTrackerFragment : Fragment() {
    private lateinit var binding: FragmentExpenseTrackerBinding
    private lateinit var viewModel: ExpenseViewModel
    private val expenseAdapter = ExpenseAdapter(arrayListOf()) { expense ->
        showExpenseDetailDialog(expense)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentExpenseTrackerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]

        binding.recExpense.layoutManager = LinearLayoutManager(context)
        binding.recExpense.adapter = expenseAdapter

        viewModel.refresh()

        viewModel.expenseLD.observe(viewLifecycleOwner) { expenseList ->
            expenseAdapter.updateExpenseList(expenseList)
        }

        binding.fabAdd.setOnClickListener {
            val action = ExpenseTrackerFragmentDirections.expenseTrackerFragmentToNewExpenseFragment(-1)
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun showExpenseDetailDialog(expense: Expense) {
        val bindingDialog = DialogExpenseDetailBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(bindingDialog.root)
            .setCancelable(true)
            .create()

        val dateFormat = SimpleDateFormat("dd MMM yyyy HH.mm", Locale("id"))
        val formattedDate = dateFormat.format(Date(expense.date))
        val formattedAmount = "IDR " + NumberFormat.getNumberInstance(Locale("id", "ID")).format(expense.amount)

        bindingDialog.txtDateTime.text = formattedDate
        bindingDialog.chipExpenseBudget.text = expense.budgetName
        bindingDialog.txtDescription.text = expense.description
        bindingDialog.txtAmount.text = formattedAmount

        bindingDialog.btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}
