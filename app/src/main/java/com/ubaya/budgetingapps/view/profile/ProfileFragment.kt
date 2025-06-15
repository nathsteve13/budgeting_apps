package com.ubaya.budgetingapps.view.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ubaya.budgetingapps.R
import com.ubaya.budgetingapps.databinding.FragmentProfileBinding
import com.ubaya.budgetingapps.util.SessionManager
import com.ubaya.budgetingapps.view.auth.AuthActivity
import com.ubaya.budgetingapps.viewmodel.UserViewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        viewModel.loginResultLD.observe(viewLifecycleOwner) { user ->
            binding.user = user
        }

        binding.btnChangePass.setOnClickListener {
            val current = sessionManager.getPassword()

            val oldPassword = binding.txtOldPass.text.toString()
            val newPassword = binding.txtNewPass.text.toString()
            val repeatPassword = binding.txtRepeat.text.toString()

            sessionManager.getUsername()?.let { username ->
                viewModel.updatePassword(username, newPassword)
            } ?: run {
                Toast.makeText(context, "Username not found in session", Toast.LENGTH_SHORT).show()
            }

            val allPrefs = sessionManager.getAllPrefs()
            Log.d("DebugTag", "Semua isi prefs: $allPrefs")

            if(oldPassword != current) {
                Toast.makeText(context, "Wrong old password", Toast.LENGTH_SHORT).show()
            }
            else if (newPassword != repeatPassword) {
                Toast.makeText(context, "New Password and Repeat Password must be same", Toast.LENGTH_SHORT).show()
            }
            else {
                sessionManager.savePassBaru(newPassword)

                Toast.makeText(context, "Password changed", Toast.LENGTH_SHORT).show()
                Log.d("DEBUG", "Password baru berhasil disimpan: $newPassword")
            }
        }

        binding.btnSignOut.setOnClickListener {
            sessionManager.clearSession()
            val intent = Intent(requireContext(), AuthActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}
