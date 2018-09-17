package com.progressor.progressor.views.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.services.ApiRequestor
import com.progressor.progressor.services.AuthenticationManager
import com.progressor.progressor.services.FragmentNavigator
import com.progressor.progressor.services.UserManager
import javax.inject.Inject

abstract class BaseFragment : Fragment() {
    @Inject
    protected lateinit var apiRequestor: ApiRequestor
    @Inject
    protected lateinit var fragmentNavigator: FragmentNavigator
    @Inject
    protected lateinit var authenticationManager: AuthenticationManager
    @Inject
    protected lateinit var userManager: UserManager

    protected abstract fun getFragmentLayout(): Int
    protected abstract fun injectDependencies()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(getFragmentLayout(), container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        injectBaseDependency()
    }

    private fun injectBaseDependency() {
        (activity as MainComponentInterface).mainComponent.inject(this)
    }
}