package com.ruhlanusubov.laza.api

import com.ruhlanusubov.laza.service.Service
import com.ruhlanusubov.laza.utils.BASE_URL

class ApiUtil {
    companion object{
        fun getService(): Service {
           return RetrofitClient.getClient(BASE_URL).create(Service::class.java)
        }
    }
}