package br.pro.mateus.authnotify

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.pro.mateus.authnotify.databinding.FragmentInfoBinding
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder

/**
 * Este fragment apenas mostra as informações
 * dos dados e um botão: me enviar uma notificação!
 * que chamará o serviço de envio da mensagem.
 */
class InfoFragment : Fragment() {

    private lateinit var functions: FirebaseFunctions;
    private var _binding: FragmentInfoBinding? = null
    private lateinit var db: FirebaseFirestore
    private val gson = GsonBuilder().enableComplexMapKeySerialization().create()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun hideKeyboard(){
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnEnviarMensagem.setOnClickListener {
            sendMessage(binding.etMensagem.text.toString(),(activity as MainActivity).getFcmToken())
                .addOnCompleteListener(requireActivity()) { res ->
                    // conta criada com sucesso.
                    if(res.result.status == "SUCCESS"){
                    hideKeyboard()
                    Snackbar.make(requireView(),"Mensagem Enviada!", Snackbar.LENGTH_LONG).show()
                    binding.etMensagem.text!!.clear()
                }
            }
        }
    }

    fun sendMessage(textContent: String, fcmToken: String) : Task<CustomResponse> {
        val data = hashMapOf(
            "textContent" to textContent,
            "fcmToken" to fcmToken
        )

        // enviar a mensagem, invocando a function...
        functions = Firebase.functions("southamerica-east1")
        return functions.getHttpsCallable("sendFcmMessage")
            .call(data)
            .continueWith { task ->
                val result =
                    gson.fromJson((task.result?.data as String), CustomResponse::class.java)
                result
            }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}