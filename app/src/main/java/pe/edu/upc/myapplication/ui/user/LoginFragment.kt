package pe.edu.upc.myapplication.ui.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import pe.edu.upc.myapplication.databinding.FragmentLoginBinding
import pe.edu.upc.myapplication.viewmodel.AuthViewModel
import android.content.SharedPreferences

class LoginFragment : Fragment() {

    private var _binding:FragmentLoginBinding? =null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()
    private var message: String = "no funciona"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentLoginBinding.inflate(inflater,container,false)

        val sharedPreferences = activity?.getSharedPreferences("db_local",0)

        Log.i("LOGIN: ", sharedPreferences?.getString("code","no hay nada")!!)

        if (sharedPreferences.contains("code")){
            val action = LoginFragmentDirections.navigateTohome()
            NavHostFragment.findNavController(this)
                .navigate(action)
        }else {
            binding.btRegister.setOnClickListener {
                val action = LoginFragmentDirections.navigateToRegisterFragment()

                NavHostFragment.findNavController(this)
                    .navigate(action)
            }

            viewModel.user.observe(viewLifecycleOwner,{ user ->
                saveData(user.code,user.name,user.lastName)
            })

            viewModel.isCorrect.observe(viewLifecycleOwner, { correct ->
                Log.i("HEY: ", correct.toString())

                val action = LoginFragmentDirections.navigateTohome()
                NavHostFragment.findNavController(this)
                    .navigate(action)
            })

            viewModel.message.observe(viewLifecycleOwner, { msg ->
                Log.i("HEY2: ", msg.toString())
                this.message = msg
                validateTransition(message)
                Log.i("FRAGMENTLOGIN: ", message)
            })


            binding.btLogin.setOnClickListener {
                Log.i("ETCODE", binding.etCode.text.toString())
                viewModel.auth(
                    binding.etCode.text.toString(),
                    binding.etPassword.text.toString()
                )
            }
        }
        return binding.root

    }

    private fun validateTransition(message:String) {
        Toast.makeText(this.context,message,Toast.LENGTH_LONG).show()
    }

    private fun saveData(code:String, name:String,lastName:String){
        val editor:SharedPreferences.Editor = activity?.getSharedPreferences("db_local",0)!!.edit()
        editor.putString("code",code)
        editor.putString("nombre", name)
        editor.putString("lastName", lastName)

        editor.apply()

    }


}