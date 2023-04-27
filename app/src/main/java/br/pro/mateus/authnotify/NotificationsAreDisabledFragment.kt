package br.pro.mateus.authnotify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.pro.mateus.authnotify.databinding.FragmentNotificationsAreDisabledBinding

/**
 * Este fragment mostra uma mensagem amigável
 * que as notificações estão desabilitadas e é
 * necessário habilitá-las antes de continuar.
 */
class NotificationsAreDisabledFragment : Fragment() {

    private var _binding: FragmentNotificationsAreDisabledBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationsAreDisabledBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}