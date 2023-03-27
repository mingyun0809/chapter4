package fastcampus.part0.chapter4

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import fastcampus.part0.chapter4.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {  //리스트로 나오는 UI로 연결할때 사용
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bloodTypeSpinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.blood_types,
            android.R.layout.simple_list_item_1
        )

        binding.birthdateLayer.setOnClickListener{
            val listener = OnDateSetListener{date, year, month, dayOfMonth ->
                binding.birthdateTextView.text = "$year-${month.inc()}-$dayOfMonth"
            }
            DatePickerDialog(
                this,
                listener,
                2000,
                1,
                1
            ).show()
        }

        binding.cautionCheckBox.setOnCheckedChangeListener { _, isChecked ->
            binding.cautionEditText.isVisible = isChecked //체크가 되면 주의사항이 보이고 그렇지 않으면 보이지 않는다.
        }

        binding.cautionEditText.isVisible = binding.cautionCheckBox.isChecked

        binding.saveButton.setOnClickListener{
            saveData()
            finish()
        }
    }
    private fun saveData(){ //MODE_PRIVATE: 이 파일을 생성한 앱에서만 접근이 가능함
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE).edit()) {
            putString(NAME, binding.nameEditText.text.toString())
            putString(BLOOD_TYPE, getBloodType())
            putString(EMERGENCY_CONTACT, binding.emergencyEditText.text.toString())
            putString(BIRTHDATE, binding.birthdateValueTextView.text.toString())
            putString(CAUTION, getCaution())
            apply()
        }
        Toast.makeText(this, "저장을 완료했습니다", Toast.LENGTH_SHORT).show()
    }

    private fun getBloodType() : String{
        val bloodAlphabet = binding.bloodTypeSpinner.selectedItem.toString()
        val bloodSign = if(binding.bloodTypePlus.isChecked) "+" else "-"
        return "$bloodSign$bloodAlphabet"
    }

    private fun getCaution(): String{
        return if(binding.cautionCheckBox.isChecked) binding.cautionEditText.text.toString() else ""
    }
}