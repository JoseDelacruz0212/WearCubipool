package pe.edu.upc.myapplication.ui
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import pe.edu.upc.myapplication.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)

        val sharedPreferences = getSharedPreferences("db_local",0)

        val code = sharedPreferences.getString("code","u202120211")
        val name = sharedPreferences.getString("nombre","error")
        val lastName = sharedPreferences.getString("lastName","error")

        binding.tvHeaderCodigo.text = code
        binding.tvHeaderName.text = name +" "+ lastName


        Glide.with(this).load(
            "https://intranet.upc.edu.pe/programas/Imagen/Fotos/Upc/0540${
                code?.removeRange(
                    0,
                    1
                )
            }.jpg"
        )
            .error(binding.ivIconUser.drawable)
            .fallback(binding.ivIconUser.drawable)
            .transform(CenterCrop(),RoundedCorners(25))
            .into(binding.ivIconUser)

        setContentView(binding.root)


        val host:NavHostFragment = supportFragmentManager
            .findFragmentById(binding.fragmentHome.id) as NavHostFragment? ?: return

        val navController = host.navController

        NavigationUI.setupWithNavController(binding.activityMainBottomNavigationView,navController)

    }

}