package com.bravoborja.citiboxdemo.presentation.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<VM : ViewModel, VB : ViewBinding> : DaggerAppCompatActivity() {

    @Inject
    protected lateinit var viewModelProvider: ViewModelProvider.Factory

    protected val viewModel by lazy { createViewModel() }

    protected lateinit var viewBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = createViewBinding()
    }

    abstract fun createViewBinding(): VB

    abstract fun createViewModel(): VM

    inline fun <reified VM : ViewModel> AppCompatActivity.viewModelOf(factory: ViewModelProvider.Factory) =
        ViewModelProvider(this, factory).get(VM::class.java)

}