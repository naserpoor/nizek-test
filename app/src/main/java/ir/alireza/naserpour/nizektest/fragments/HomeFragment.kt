package ir.alireza.naserpour.nizektest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ir.alireza.naserpour.nizektest.R
import ir.alireza.naserpour.nizektest.databinding.FragmentHomeBinding
import ir.alireza.naserpour.nizektest.databinding.FragmentLoginBinding
import ir.alireza.naserpour.nizektest.viewmodels.HomeViewModel
import ir.alireza.naserpour.nizektest.viewmodels.LoginViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var databinding:FragmentHomeBinding
    val args by navArgs<HomeFragmentArgs>()
    val viewModel by viewModel<HomeViewModel>{
        parametersOf(args.userName)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databinding = FragmentHomeBinding.bind(super.onCreateView(inflater, container, savedInstanceState) ?: return null)
        databinding.lifecycleOwner = viewLifecycleOwner
        databinding.fullName = viewModel.fullName

        viewModel.loginTimerScreenOn.observe(viewLifecycleOwner){
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        databinding.logoutBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        return databinding.root
    }

    override fun onStart() {
        super.onStart()
        if(viewModel.shouldCloseNow()){
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.appMovedToBackground()
    }
}