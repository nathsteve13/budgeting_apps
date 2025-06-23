package com.ubaya.budgetingapps.view.expense

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.budgetingapps.databinding.BudgetItemLayoutBinding
import com.ubaya.budgetingapps.databinding.ExpenseItemLayoutBinding
import com.ubaya.budgetingapps.databinding.FragmentExpenseTrackerBinding
import com.ubaya.budgetingapps.model.expense.Expense

class ExpenseAdapter (
    private val expenseList: ArrayList<Expense>,
    private val onClick: (Expense) -> Unit
    ) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

        class ExpenseViewHolder(val binding: ExpenseItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ExpenseItemLayoutBinding.inflate(inflater, parent, false)
            return ExpenseViewHolder(binding)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
            val expense = expenseList[position]
            with(holder.binding) {
                txtExpenseDate.text = formatDate(expense.date)
                txtExpenseAmount.text = "IDR ${formatCurrency(expense.amount)}"
                chipExpenseBudget.text = expense.budgetName

                root.setOnClickListener {
                    onClick(expense)
                }
            }
        }

        override fun getItemCount(): Int = expenseList.size

        @SuppressLint("NotifyDataSetChanged")
        fun updateExpenseList(newList: List<Expense>) {
            expenseList.clear()
            expenseList.addAll(newList)
            notifyDataSetChanged()
        }

        private fun formatDate(timestamp: Long): String {
            val sdf = java.text.SimpleDateFormat("dd MMM yyyy, HH:mm", java.util.Locale("id", "ID"))
            return sdf.format(java.util.Date(timestamp))
        }

        private fun formatCurrency(amount: Int): String {
            val formatter = java.text.NumberFormat.getInstance(java.util.Locale("id", "ID"))
            return formatter.format(amount)
        }
    }