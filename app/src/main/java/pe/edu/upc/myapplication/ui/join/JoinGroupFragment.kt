package pe.edu.upc.myapplication.ui.join

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pe.edu.upc.myapplication.databinding.FragmentJoinGroupBinding

class JoinGroupFragment: Fragment() {

    private var _binding:FragmentJoinGroupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentJoinGroupBinding.inflate(inflater,container,false)

        return binding.root
    }
}