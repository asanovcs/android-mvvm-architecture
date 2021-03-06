package com.deveem.core.network.result

/**Created by Ernis Asanov on 06/11/2020.*/

internal open class ApiException : Exception {
    var errorCode = 500
    var errorMessage: String? = null

    constructor(message: String?) : super(message) {
        this.errorMessage = message
    }

    constructor(message: String, cause: Throwable) : super(message, cause) {
        this.errorMessage = message
    }

    constructor(cause: Throwable) : super(cause) {}

    constructor(message: String?, errorCode: Int) : super(message) {
        this.errorCode = errorCode
        if (errorCode == 429) {
            this.errorMessage = "Cloudflare to restrict access"
        }else{
            this.errorMessage = message
        }
    }

}