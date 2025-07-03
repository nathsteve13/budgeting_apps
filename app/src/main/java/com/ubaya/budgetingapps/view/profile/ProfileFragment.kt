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
import com.ubaya.budgetingapps.databinding.FragmentProfileBinding
import com.ubaya.budgetingapps.util.SessionManager
import com.ubaya.budgetingapps.view.auth.AuthActivity
import com.ubaya.budgetingapps.viewmodel.UserViewModel

class ProfileFragment : Fragment(), ProfileListener {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.listener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Ambil username & password dari session dan lakukan login
        val username = sessionManager.getUsername()
        val password = sessionManager.getPassword()

        if (username != null && password != null) {
            viewModel.login(username, password)
        } else {
            Toast.makeText(context, "User session not found", Toast.LENGTH_SHORT).show()
        }

        // Observe user object dan ikat ke layout
        viewModel.loginResultLD.observe(viewLifecycleOwner) { user ->
            binding.user = user
        }
    }

    override fun onChangepasswordClick(v: View) {
        val oldPassword = binding.txtOldPass.text.toString()
        val newPassword = binding.txtNewPass.text.toString()
        val repeatPassword = binding.txtRepeat.text.toString()
        val current = sessionManager.getPassword()
        val username = sessionManager.getUsername()

        if (oldPassword != current) {
            Toast.makeText(context, "Wrong old password", Toast.LENGTH_SHORT).show()
        } else if (newPassword != repeatPassword) {
            Toast.makeText(context, "New Password and Repeat Password must match", Toast.LENGTH_SHORT).show()
        } else {
            username?.let {
                viewModel.updatePassword(it, newPassword)
                sessionManager.savePassBaru(newPassword)
                Toast.makeText(context, "Password changed", Toast.LENGTH_SHORT).show()
                Log.d("DEBUG", "Password updated to: $newPassword")
            }
        }
    }
    override fun onLogoutClick(v: View) {
        sessionManager.clearSession()
        val intent = Intent(requireContext(), AuthActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

}