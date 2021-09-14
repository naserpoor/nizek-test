package ir.alireza.naserpour.nizektest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ir.alireza.naserpour.nizektest.R
import ir.alireza.naserpour.nizektest.databinding.FragmentSignupBinding
import ir.alireza.naserpour.nizektest.viewmodels.LoginViewModel
import ir.alireza.naserpour.nizektest.viewmodels.SignupViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception

class SignupFragment : Fragment(R.layout.fragment_signup) {

    lateinit var databinding:FragmentSignupBinding
    val viewModel by viewModel<SignupViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databinding = FragmentSignupBinding.bind(super.onCreateView(inflater, container, savedInstanceState) ?: return null)
        databinding.lifecycleOwner = viewLifecycleOwner
        databinding.viewModel = viewModel

        databinding.registerBtn.setOnClickListener {
            val result = viewModel.registerUser(databinding.fullName.text?.toString(),databinding.userName.text?.toString(), databinding.password.text?.toString())
            if(result){
                findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
            }
        }
        return databinding.root
    }
}