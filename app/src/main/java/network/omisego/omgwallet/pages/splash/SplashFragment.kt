package network.omisego.omgwallet.pages.splash

/*
 * OmiseGO
 *
 * Created by Phuchit Sirimongkolsathien on 21/9/2018 AD.
 * Copyright © 2017-2018 OmiseGO. All rights reserved.
 */

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import co.omisego.omisego.model.APIError
import co.omisego.omisego.model.WalletList
import network.omisego.omgwallet.R
import network.omisego.omgwallet.databinding.FragmentSplashBinding
import network.omisego.omgwallet.extension.provideViewModel
import network.omisego.omgwallet.extension.toast

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    private lateinit var viewModel: PreloadResourceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadWallets()
        viewModel.liveResult.observe(this, Observer {
            it.handle(this::handleLoadWalletSuccess, this::handleLoadWalletFail)
            findNavController().navigateUp()
        })
    }

    private fun handleLoadWalletSuccess(data: WalletList) {
        viewModel.saveWallet(data)
    }

    private fun handleLoadWalletFail(error: APIError) {
        context?.toast(error.description)
    }
}
