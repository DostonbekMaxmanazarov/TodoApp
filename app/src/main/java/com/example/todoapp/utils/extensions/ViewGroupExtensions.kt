package com.example.todoapp.utils.extensions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes resId:Int)=LayoutInflater.from(context).inflate(resId,this,false)