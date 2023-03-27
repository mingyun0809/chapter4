package fastcampus.part0.chapter4

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.widget.Toast
import androidx.core.view.isVisible
import fastcampus.part0.chapter4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goInputActivityButton.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent) //명시적 intent(갈곳이 확실히 정해져 있음)
        }

        binding.deleteButton.setOnClickListener {
            deleteData()
        }

        binding.emergencyContactLayer.setOnClickListener {
            with(Intent(Intent.ACTION_VIEW)) {  //암시적 intent(어플안에서 전화동작을 할수있는 앱을 실행해줌)
                val phoneNumber = binding.emergencyValueTextView.text.toString()
                    .replace("-", "")
                data = Uri.parse("tel:$phoneNumber")
                startActivity(this)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getDateUiUpdate()
    }
    private fun getDateUiUpdate(){
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE )){
            binding.nameValueTextView.text = getString(NAME, "미정")
            binding.birthdateValueTextView.text = getString(BIRTHDATE, "미정")
            binding.bloodValueTextView.text = getString(BLOOD_TYPE, "미정")
            binding.emergencyValueTextView.text = getString(EMERGENCY_CONTACT, "미정")
            val caution = getString(CAUTION, "")

            binding.cautionTextView.isVisible = caution.isNullOrEmpty().not()
            binding.cautionValueTextView.isVisible = caution.isNullOrEmpty().not()
            if(!caution.isNullOrEmpty()){
                binding.cautionValueTextView.text = caution
            }
        }
    }

    private fun deleteData() {
        with(getSharedPreferences(USER_INFORMATION, MODE_PRIVATE).edit()) {
            clear()
            apply()
            getDateUiUpdate()
        }
        Toast.makeText(this, "초기화를 완료했습니다.", Toast.LENGTH_SHORT).show()
    }

}