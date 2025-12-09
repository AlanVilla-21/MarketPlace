import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.marketplace.R

class MainActivity : AppCompatActivity (){
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate (savedInstanceState: Bundle?) {
    super.onCreate(saved)InstancceState)
    setContentView(R.layout.activity_main)

    drawerLayout= findViewById(R.id.drawerLayout)
    val txtMenu: TextView = findViewById(R.id.txtMenu)

    txtMenu.setOnClickListener{
        drawerLayout.openDrawer(GravityCompat.START)
    }
}

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        } else{
            super.OnBackPressed()
        }
    }
}