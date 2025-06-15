package com.ubaya.budgetingapps.view.budgeting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ubaya.budgetingapps.databinding.FragmentBudgetEditBinding
import com.ubaya.budgetingapps.model.budget.Budget
import com.ubaya.budgetingapps.viewmodel.BudgetViewModel

class BudgetEditFragment : Fragment(), BudgetEditListener {
    private lateinit var binding: FragmentBudgetEditBinding
    private lateinit var viewModel: BudgetViewModel
    private var uuid = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBudgetEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(BudgetViewModel::class.java)
        binding.listener = this

        uuid = BudgetEditFragmentArgs.fromBundle(requireArguments()).uuid
        Log.d("BudgetEditFragment", "UUID received: $uuid")

        if (uuid == -1) {
            binding.txtJudul.text = "Tambah Budget"
            binding.btnSave.text = "Tambah"
            binding.budget = Budget(name = "", amount = "")
        } else {
            binding.txtJudul.text = "Edit Budget"
            binding.btnSave.text = "Simpan Perubahan"
            viewModel.load(uuid)
            observeViewModel()
        }
    }


    private fun observeViewModel() {
        viewModel.budgetSingleLD.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.budget = it
            } else {
                Toast.makeText(requireContext(), "Budget tidak ditemukan", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(requireView()).popBackStack()
            }
        })
    }


    override fun onClick(v: View) {
        val budget = binding.budget
        if (budget != null) {
            if (budget.name.isBlank()) {
                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (budget.amount.isBlank() || budget.amount.toIntOrNull() == null || budget.amount.toInt() <= 0) {
                Toast.makeText(context, "Nominal harus >= 0", Toast.LENGTH_SHORT).show()
            } else {
                if (uuid == -1) {
                    viewModel.insert(budget)
                    Toast.makeText(context, "Budget ditambahkan", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.update(budget)
                    Toast.makeText(context, "Budget diperbarui", Toast.LENGTH_SHORT).show()
                }
                Navigation.findNavController(v).popBackStack()
            }
        }
    }

}
