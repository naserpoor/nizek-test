package ir.alireza.naserpour.nizektest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ir.alireza.naserpour.nizektest.R
import ir.alireza.naserpour.nizektest.databinding.FragmentLoginBinding
import ir.alireza.naserpour.nizektest.databinding.FragmentSignupBinding
import ir.alireza.naserpour.nizektest.viewmodels.LoginViewModel
import ir.alireza.naserpour.nizektest.viewmodels.SignupViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login){

    lateinit var databinding:FragmentLoginBinding
    val viewModel by viewModel<LoginViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databinding = FragmentLoginBinding.bind(super.onCreateView(inflater, container, savedInstanceState) ?: return null)
        databinding.lifecycleOwner = viewLifecycleOwner
        databinding.viewModel = viewModel

        databinding.registerBtn.setOnClickListener {
            val result = viewModel.loginUser(databinding.userName.text?.toString(),databinding.password.text?.toString())
            if(result){
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment(databinding.userName.text.toString()))
            }
        }

        return databinding.root
    }
}