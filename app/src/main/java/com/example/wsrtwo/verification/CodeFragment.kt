package com.example.wsrtwo.verification

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.renderscript.ScriptGroup.Input
import android.text.Editable
import android.text.TextWatcher
import android.view.ContentInfo
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.wsrtwo.Api.RetrofitClient
import com.example.wsrtwo.Api.SignIn
import com.example.wsrtwo.R
import com.example.wsrtwo.SignViewModel
import com.example.wsrtwo.databinding.FragmentCodeBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback
import kotlin.math.sign

class CodeFragment : Fragment() {

    private var _binding:FragmentCodeBinding? = null
    private val binding get() = _binding!!

    private lateinit var timerTextView: TextView
    private lateinit var editTextList: List<EditText>
    private lateinit var countDownTimer: CountDownTimer

    private var timeLeftInMillis = 6000L

    private val apiService = RetrofitClient.apiService
    private lateinit var signViewModel: SignViewModel

    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCodeBinding.inflate(layoutInflater,container,false)
        binding.backToEmail.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        showKeyBoardForFirstEditText()
        signViewModel = ViewModelProvider(requireActivity()).get(SignViewModel::class.java)
    }
    private fun showKeyBoardForFirstEditText(){
        val firstEditText = editTextList.firstOrNull()
        firstEditText?.requestFocus()
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(firstEditText,InputMethodManager.SHOW_IMPLICIT)
    }

    private fun initViews(){
        timerTextView = binding!!.time
        editTextList = listOf(binding!!.OTP1,binding!!.OTP2,binding!!.OTP3,binding!!.OTP4)
    }
    private fun initListeners(){
        editTextList.forEachIndexed { index, editText ->
            editText.addTextChangedListener(
                CodeTextWatcher(
                    editTextList.getOrNull(index + 1),
                    editTextList.getOrNull(index - 1),
                editText)
            )
        }
    }

    inner class CodeTextWatcher(private val nextEditText: EditText?, private val prevEditText:EditText?, private val currentEditText:EditText):TextWatcher{
        private var timerStarted = false
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if(s?.length == 1){
                nextEditText?.let {
                    it.requestFocus()
                    it.setSelection(it.text.length)
                }?:run{
                    navigationToNextFragment()
                }
            }else if ((s?.length?:0) > 1){
                prevEditText?.let {
                    it.requestFocus()
                    it.setSelection(it.text.length)
                }
            }
        }
        @SuppressLint("SuspiciousIndentation")
        override fun afterTextChanged(s: Editable?) {
            if(s?.length == 4 && !timerStarted){
                if(s?.length == 4 && !timerStarted && editTextList.all { it.text.isNotEmpty() && it.text.isDigitsOnly() })
                navigationToNextFragment()
            }else{
                Toast.makeText(requireContext(),"Incorrect Verification Code",Toast.LENGTH_SHORT).show()
                //Таймер
                countDownTimer = object : CountDownTimer(6000,1000){
                    override fun onTick(millisUntilFinished: Long) {
                        timerStarted = true
                        timerTextView.text = "Try again in ${millisUntilFinished/1000}seconds"
                    }
                    override fun onFinish() {
                        timerStarted = false
                        timerTextView.text = ""
                        editTextList.forEach{it.isEnabled = true}
                    }
                }.start()
                editTextList.forEach { it.isEnabled = true }
            }
        }

        private fun navigationToNextFragment() {
            val code = editTextList.joinToString(""){it.text.toString()}
            val email = arguments?.getString("email")?: ""
            GlobalScope.launch {
                apiService.signIn(email,code).enqueue(object:retrofit2.Callback<SignIn>{
                    override fun onResponse(call: Call<SignIn>, response: Response<SignIn>) {
                        if(response.isSuccessful){
                            val token = response.body()?.token?:""
                            signViewModel.saveToken(token)
                            Toast.makeText(requireContext(),token,Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(requireContext(),"Ошибка",Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<SignIn>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(email: String): CodeFragment {
            val fragment = CodeFragment()
            val args = Bundle()
            args.putString("email",email)
            fragment.arguments = args
            return fragment
        }
    }
}