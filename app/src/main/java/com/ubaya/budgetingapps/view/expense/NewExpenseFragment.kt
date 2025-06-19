package com.ubaya.budgetingapps.view.expense

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ubaya.budgetingapps.databinding.FragmentNewExpenseBinding
import com.ubaya.budgetingapps.model.budget.Budget
import com.ubaya.budgetingapps.model.expense.Expense
import com.ubaya.budgetingapps.viewmodel.BudgetViewModel
import com.ubaya.budgetingapps.viewmodel.ExpenseViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NewExpenseFragment : Fragment() {
    private lateinit var binding: FragmentNewExpenseBinding
    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var budgetViewModel: BudgetViewModel
    private lateinit var spinnerAdapter: ArrayAdapter<String>

    private var budgetList: List<Budget> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]
        budgetViewModel = ViewModelProvider(this)[BudgetViewModel::class.java]

        expenseViewModel.expenseLD.observe(viewLifecycleOwner) { expenses ->
            for (expense in expenses) {
                Log.d("DEBUG_EXPENSE", "Expense: $expense")
            }
        }

        expenseViewModel.refresh()

        spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            ArrayList()
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = spinnerAdapter

        observeViewModel()
        budgetViewModel.refresh()

        val currentTimestamp = System.currentTimeMillis()
        val date = Date(currentTimestamp)
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        binding.txtDate.text = formatter.format(date)


        binding.btnAddExpense.setOnClickListener {
            val selectedPosition = binding.spinner.selectedItemPosition
            val selectedBudget = budgetList[selectedPosition]
            val amountText = binding.txtNominal.text.toString()
            val descriptionText = binding.txtNotes.text.toString()

            val amount = amountText?.toIntOrNull()

            if(amountText.isNotBlank() && descriptionText.isNotBlank() && amount != null && amount >=0) {
                val newExpense = Expense(
                    date = currentTimestamp,
                    amount = amount,
                    description = descriptionText,
                    budgetId = selectedBudget.uuid,
                    budgetName = selectedBudget.name)

                expenseViewModel.insert(newExpense)

                Toast.makeText(requireContext(), "Expense added", Toast.LENGTH_SHORT).show()

                findNavController().popBackStack()
            }
            else {
                Toast.makeText(requireContext(), "Please enter the valid amount and description", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun observeViewModel() {
        budgetViewModel.budgetLD.observe(viewLifecycleOwner) { budgets ->
            budgetList = budgets
            val budgetNames = budgets.map { it.name }

            spinnerAdapter.clear()
            spinnerAdapter.addAll(budgetNames)
            spinnerAdapter.notifyDataSetChanged()

            if (budgets.isNotEmpty()) {
                updateProgressBar(budgets[0])
            }

            binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedBudget = budgetList[position]
                    updateProgressBar(selectedBudget)

                    expenseViewModel.loadTotalExpensesForBudget(selectedBudget.name)

                    expenseViewModel.totalExpenseLiveData.observe(viewLifecycleOwner) { totalExpense ->
                        val formatter = NumberFormat.getNumberInstance(Locale("id", "ID"))
                        val budgetAmount = selectedBudget.amount.toIntOrNull()

                        val totalExpenseFormatted = "IDR " + formatter.format(totalExpense)
                        val formattedBudget = "IDR " + formatter.format(budgetAmount)

                        binding.txtExpenseTotal.text = totalExpenseFormatted
                        binding.txtBudgetTotal.text = formattedBudget
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }
    }

    private fun updateProgressBar(budget: Budget) {
        val budgetAmount = budget.amount.toIntOrNull()?:0

        expenseViewModel.loadTotalExpensesForBudget(budget.name)

        expenseViewModel.totalExpenseLiveData.observe(viewLifecycleOwner) { totalExpense ->
            val used = totalExpense
            Log.d("DEBUG", "Total used: $used")
            Log.d("DEBUG", "Budget amount: $budgetAmount")
            val percentRemaining = if (budgetAmount > 0) {
                ((budgetAmount - used) * 100 / budgetAmount).coerceIn(0, 100)
            } else {
                0
            }
            binding.progressBar.max = 100
            binding.progressBar.progress = percentRemaining
        }
    }
}
